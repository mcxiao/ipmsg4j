package com.github.mcxiao.ipmsg.packet.extension;

import com.github.mcxiao.ipmsg.packet.PacketExtension;

/**
 *
 */

public class OriginExtension extends PacketExtension {
    
    private String extString;
    
    public OriginExtension(String extString) {
        this.extString = extString;
    }
    
    protected void setExtString(String string) {
        extString = string;
    }
    
    public String getExtString() {
        return extString;
    }
    
    @Override
    public String toExtensionString() {
        return extString;
    }
}
