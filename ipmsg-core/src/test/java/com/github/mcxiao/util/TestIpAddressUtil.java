package com.github.mcxiao.util;

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
