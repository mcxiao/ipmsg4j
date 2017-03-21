package com.github.mcxiao.ipmsg.util;

/**
 *
 */

public final class ObjectUtil {
    
    public static void requiredNonNull(Object object, String message) {
        if (object == null)
            throw new NullPointerException(message);
    }
    
}
