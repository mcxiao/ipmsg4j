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
