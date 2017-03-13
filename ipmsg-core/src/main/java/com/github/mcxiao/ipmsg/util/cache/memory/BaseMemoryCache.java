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

import java.lang.ref.Reference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by mangoo on 2015/9/24.
 */
public abstract class BaseMemoryCache<K, V> implements MemoryCache<K, V> {

    private final Map<K, Reference<V>> softMap = Collections.synchronizedMap(new HashMap<K, Reference<V>>());

    @Override
    public V get(K key) {
        V value = null;
        Reference<V> reference = softMap.get(key);
        if (reference != null) {
            value = reference.get();
        }
        return value;
    }

    @Override
    public boolean put(K key, V value) {
        softMap.put(key, createReference(value));
        return true;
    }

    @Override
    public V remove(K key) {
        Reference<V> remove = softMap.remove(key);
        return remove == null ? null : remove.get();
    }

    @Override
    public Collection<K> keys() {
        synchronized (softMap) {
            return new HashSet<>(softMap.keySet());
        }
    }

    @Override
    public void clear() {
        softMap.clear();
    }

    protected abstract Reference<V> createReference(V value);

}
