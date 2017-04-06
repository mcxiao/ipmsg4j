package com.github.mcxiao.ipmsg.util;

import com.sun.istack.internal.NotNull;

import java.util.Objects;

/**
 *
 */

public final class ExtensionParseUtil {
    
    public static String[] splitBySeparator(@NotNull String src) {
        Objects.requireNonNull(src, "Params can't be null.");
        
        return src.split("\\b:\\b");
    }
    
    public static String escapeDelimiter(@NotNull String src, boolean encode) {
        Objects.requireNonNull(src, "Params can't be null.");
     
        return encode ? src.replace(":", "::") : src.replace("::", ":");
    }
    
}
