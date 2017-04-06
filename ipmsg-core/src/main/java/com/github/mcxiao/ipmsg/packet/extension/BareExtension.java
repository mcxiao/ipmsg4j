package com.github.mcxiao.ipmsg.packet.extension;

import com.github.mcxiao.ipmsg.packet.PacketExtension;

/**
 *
 */

public class BareExtension extends PacketExtension {
    
    protected String extString;
    
    public BareExtension() {
        
    }
    
    public BareExtension(String extString) {
        this.extString = extString;
    }
    
    public String getExtString() {
        return extString;
    }
    
    public void setExtString(String extString) {
        this.extString = extString;
    }
    
    @Override
    public String toExtensionString() {
        return extString;
    }
}
