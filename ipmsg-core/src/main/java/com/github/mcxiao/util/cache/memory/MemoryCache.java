package com.github.mcxiao.util.cache.memory;

import java.util.Collection;

/**
 * Created by mangoo on 2015/9/24.
 */
public interface MemoryCache<K, V> {

    boolean put(K key, V value);

    V get(K key);

    V remove(K key);

    Collection<K> keys();

    void clear();

}
