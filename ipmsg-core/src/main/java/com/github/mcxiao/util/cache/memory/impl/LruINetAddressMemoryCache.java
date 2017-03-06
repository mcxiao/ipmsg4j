package com.github.mcxiao.util.cache.memory.impl;

import com.github.mcxiao.util.cache.memory.LRULimitedMemoryCache;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 */
public class LruINetAddressMemoryCache extends LRULimitedMemoryCache<String, InetAddress> {
    /**
     * @param maxSize Maximum sum of the sizes of the value in this cache
     */
    public LruINetAddressMemoryCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int getSize(InetAddress value) {
        return 1;
    }

    /**
     * Not thread-safe.
     */
    public InetAddress getOrCreate(String key) throws UnknownHostException {
        InetAddress inetAddress = get(key);
        if (inetAddress == null) {
            inetAddress = createInetAddress(key);
            put(key, inetAddress);
        }

        return inetAddress;
    }

    protected InetAddress createInetAddress(String ipAddress) throws UnknownHostException {
        InetAddress address = InetAddress.getByName(ipAddress);
        return address;
    }

}
