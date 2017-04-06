package com.github.mcxiao.ipmsg.packet;

import com.github.mcxiao.ipmsg.util.ExtensionParseUtil;

/**
 *
 */

public abstract class PacketExtension implements ExtensionElement {
    
    public PacketExtension() {
        
    }
    
    @Override
    public byte[] toBytes() {
        return toExtensionString().getBytes();
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + toExtensionString();
    }
    
    protected String encodeEscapeDelimiter(String string) {
        return ExtensionParseUtil.escapeDelimiter(string, true);
    }
}
