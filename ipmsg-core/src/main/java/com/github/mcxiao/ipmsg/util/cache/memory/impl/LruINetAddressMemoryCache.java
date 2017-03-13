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

package com.github.mcxiao.ipmsg.util.cache.memory.impl;

import com.github.mcxiao.ipmsg.util.cache.memory.LRULimitedMemoryCache;

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
