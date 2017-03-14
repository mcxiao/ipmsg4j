/*
 * Copyright [2017] [xiao]
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

package com.github.mcxiao.ipmsg.address;

import com.github.mcxiao.ipmsg.util.cache.memory.impl.LruINetAddressMemoryCache;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 */
public class IPAddressCache {

    // XXX Some improve: Dynamic create(according network users)
    private static final int MAX_CACHE_SIZE = 20;

    private static final LruINetAddressMemoryCache INETADDRES_CACHE = new LruINetAddressMemoryCache(MAX_CACHE_SIZE);

    private static IPAddressCache instance;

    static {
        instance = new IPAddressCache();
    }

    public static IPAddressCache getInstance() {
        return instance;
    }

    private IPAddressCache() {
    }

    public InetAddress getInetAddress(String key) throws UnknownHostException {
        return INETADDRES_CACHE.getOrCreate(key);
    }

}
