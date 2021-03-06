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

package com.github.mcxiao.ipmsg.util;

import org.junit.Assert;
import org.junit.Test;

/**
 */
public class TestIpAddressUtil {

    @Test
    public void testIpAddressConverter() {
        String ipAddress = "192.168.1.1";

        byte[] bytes = new byte[] {(byte) 0xc0, (byte) 0xa8, 1, 1};

        byte[] bytes1 = IpAddressConverter.toBytesIp(ipAddress);
        Assert.assertArrayEquals(bytes, bytes1);
    }

}
