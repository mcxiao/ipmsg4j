package com.github.mcxiao.util;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class TestByteArrayUtil {

    byte[] pattern = new byte[]{0x1, 0x2};

    byte[] a = new byte[]{0x9, 0x23, 0x33, 0x43, 0x1, 0x2, 0x20};
    byte[] b = new byte[]{0x3, 0x43, 0x8, 0x1, 0x2};
    byte[] c = new byte[]{0x1, 0x2, 0x46};
    byte[] d = new byte[]{0x4, 0x8, 0x32, 0x1, 0x30};
    byte[] e = new byte[]{0x4, 0x8, 0x32, 0x1};
    byte[] f = new byte[]{0x9, 0x23, 0x33, 0x43, 0x1, 0x2, 0x20, 0x3, 0x43, 0x8, 0x1, 0x2};

    List<byte[]> aList = new ArrayList<>();
    List<byte[]> bList = new ArrayList<>();
    List<byte[]> cList = new ArrayList<>();
    List<byte[]> dList = new ArrayList<>();
    List<byte[]> fList = new ArrayList<>();

    @Before
    public void init() {
        aList.add(new byte[]{0x9, 0x23, 0x33, 0x43});
        aList.add(new byte[]{0x20});
        bList.add(new byte[]{0x3, 0x43, 0x8});
        cList.add(new byte[]{});
        cList.add(new byte[]{0x46});
        dList.add(d);

        fList.add(new byte[]{0x9, 0x23, 0x33, 0x43});
        fList.add(new byte[]{0x20, 0x3, 0x43, 0x8});
    }

    @Test
    public void isMatch() throws Exception {
        Assert.assertFalse(ByteArrayUtil.isMatch(pattern, a, 0));
        Assert.assertFalse(ByteArrayUtil.isMatch(pattern, b, 0));
        Assert.assertTrue(ByteArrayUtil.isMatch(pattern, c, 0));
        Assert.assertFalse(ByteArrayUtil.isMatch(pattern, d, 0));
        Assert.assertFalse(ByteArrayUtil.isMatch(pattern, e, 0));
    }

    @Test
    public void firstMatch() throws Exception {
        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, a, 0), 4);
        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, a, 3), 4);
        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, a, 4), 4);
        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, a, 5), -1);

        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, b, 0), 3);
        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, b, 1), 3);
        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, b, 3), 3);
        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, b, 4), -1);

        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, c, 0), 0);
        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, c, 1), -1);

        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, d, 0), -1);
        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, d, d.length), -1);
        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, d, d.length - 1), -1);

        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, e, 0), -1);
        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, e, e.length), -1);
        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, e, e.length - 1), -1);

        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, f, 0), 4);
        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, f, 3), 4);
        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, f, 4), 4);
        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, f, 5), 10);
        Assert.assertEquals(ByteArrayUtil.firstMatch(pattern, f, f.length - 1), -1);
    }

    @Test
    public void split() throws Exception {
        List<byte[]> splitA = ByteArrayUtil.split(pattern, a);
        List<byte[]> splitB = ByteArrayUtil.split(pattern, b);
        List<byte[]> splitC = ByteArrayUtil.split(pattern, c);
        List<byte[]> splitF = ByteArrayUtil.split(pattern, f);

        Assert.assertArrayEquals(aList.toArray(), splitA.toArray());
        Assert.assertArrayEquals(bList.toArray(), splitB.toArray());
        Assert.assertArrayEquals(cList.toArray(), splitC.toArray());
        Assert.assertArrayEquals(dList.toArray(), ByteArrayUtil.split(pattern, d).toArray());
        Assert.assertArrayEquals(fList.toArray(), splitF.toArray());
    }

}