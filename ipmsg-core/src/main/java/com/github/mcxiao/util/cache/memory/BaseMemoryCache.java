package com.github.mcxiao.util.cache.memory;

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
