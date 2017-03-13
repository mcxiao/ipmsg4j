/*
 * Copyright [2017] [$author]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mcxiao.ipmsg.util.cache.memory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by mangoo on 2015/9/24.
 */
public abstract class LimitedMemoryCache<K, V> extends BaseMemoryCache<K, V> {

    private static final int MAX_NORMAL_CACHE_SIZE_IN_MB = 16;
    private static final int MAX_NORMAL_CACHE_SIZE = MAX_NORMAL_CACHE_SIZE_IN_MB * 1024 * 1024;

    private final int sizeLimit;

    private final AtomicInteger cacheSize;

    private Logger LOGGER = Logger.getLogger(LimitedMemoryCache.class.getName());

    /**
     * Contains strong references to stored objects. Each next object is added last. If hard cache size will exceed
     * limit then first object is deleted (but it continue exist at {@link #softMap} and can be collected by GC at any
     * time)
     */
    private final List<V> hardCache = Collections.synchronizedList(new LinkedList<V>());

    public LimitedMemoryCache(int sizeLimit) {
        this.sizeLimit = sizeLimit;
        cacheSize = new AtomicInteger();
        if (sizeLimit > MAX_NORMAL_CACHE_SIZE) {
            LOGGER.log(Level.WARNING, "You set too large com.github.mcxiao.util.cache.memory cache size (more than %1$d Mb)",
                    MAX_NORMAL_CACHE_SIZE_IN_MB);
        }
    }

    @Override
    public boolean put(K key, V value) {
        boolean success = false;
        // Try to add value to hard cache
        int valueSize = getSize(value);
        int sizeLimit = getSizeLimit();
        int curCacheSize = this.cacheSize.get();
        if (valueSize < sizeLimit) {
            while (curCacheSize + valueSize > sizeLimit) {
                V removedValue = removeNext();
                if (hardCache.remove(removedValue)) {
                    curCacheSize = cacheSize.addAndGet(-getSize(removedValue));
                }
            }
            hardCache.add(value);
            cacheSize.addAndGet(valueSize);

            success = true;
        }
        // Add value to soft cache
        super.put(key, value);
        return success;
    }

    @Override
    public V remove(K key) {
        V value = super.get(key);
        if (value != null) {
            if (hardCache.remove(value)) {
                cacheSize.addAndGet(-getSize(value));
            }
        }
        return super.remove(key);
    }

    @Override
    public void clear() {
        hardCache.clear();
        cacheSize.set(0);
        super.clear();
    }

    protected int getSizeLimit() {
        return sizeLimit;
    }

    protected abstract int getSize(V value);

    protected abstract V removeNext();

}
