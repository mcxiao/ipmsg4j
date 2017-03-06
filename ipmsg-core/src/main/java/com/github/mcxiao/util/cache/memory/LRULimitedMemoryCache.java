package com.github.mcxiao.util.cache.memory;

import com.github.mcxiao.util.cache.memory.LimitedMemoryCache;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;



public abstract class LRULimitedMemoryCache<K, V> extends LimitedMemoryCache<K, V> {

    private static final int INITIAL_CAPACITY = 10;
    private static final float LOAD_FACTOR = 1.1f;

    /** Cache providing Least-Recently-Used logic */
    private final Map<K, V> lruCache = Collections.synchronizedMap(
            new LinkedHashMap<K, V>(INITIAL_CAPACITY, LOAD_FACTOR, true));

    /** @param maxSize Maximum sum of the sizes of the value in this cache */
    public LRULimitedMemoryCache(int maxSize) {
        super(maxSize);
    }

    @Override
    public boolean put(K key, V value) {
        if (super.put(key, value)) {
            lruCache.put(key, value);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public V get(K key) {
        lruCache.get(key);  // call "get" for LRU logic
        return super.get(key);
    }

    @Override
    public V remove(K key) {
        lruCache.remove(key);
        return super.remove(key);
    }

    @Override
    public void clear() {
        lruCache.clear();
        super.clear();
    }

    @Override
    protected V removeNext() {
        V mostLongUsedValue = null;
        synchronized (lruCache) {
            Iterator<Map.Entry<K, V>> iterator = lruCache.entrySet().iterator();
            if (iterator.hasNext()) {
                Map.Entry<K, V> entry = iterator.next();
                mostLongUsedValue = entry.getValue();
                iterator.remove();
            }
        }
        return mostLongUsedValue;
    }

    @Override
    protected Reference<V> createReference(V value) {
        return new WeakReference<>(value);
    }
}
