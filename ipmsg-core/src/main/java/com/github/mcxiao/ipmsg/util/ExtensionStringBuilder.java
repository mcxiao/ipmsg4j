package com.github.mcxiao.ipmsg.util;

import com.github.mcxiao.ipmsg.IPMsgProtocol;

/**
 *
 */

public class ExtensionStringBuilder {
    
    private StringBuilder stringBuilder;
    
    public ExtensionStringBuilder() {
        stringBuilder = new StringBuilder();
    }
    
    public ExtensionStringBuilder(String string) {
        this.stringBuilder = new StringBuilder(string);
    }
    
    public ExtensionStringBuilder appendTheEND() {
        stringBuilder.append(IPMsgProtocol.END);
        return this;
    }
    
    public ExtensionStringBuilder appendTheEOL() {
        stringBuilder.append(IPMsgProtocol.EOL);
        return this;
    }
    
    public ExtensionStringBuilder append(String value) {
        stringBuilder.append(value);
        return this;
    }
    
    public ExtensionStringBuilder appendIfAble(String value) {
        if (!StringUtil.isNullOrEmpty(value)) {
            stringBuilder.append(value);
        }
        return this;
    }
    
    public ExtensionStringBuilder formatAppendIfAble(String regex, String value) {
        if (!StringUtil.isNullOrEmpty(value)) {
            stringBuilder.append(StringUtil.format(regex, value));
        }
        return this;
    }
    
    @Override
    public String toString() {
        return stringBuilder.toString();
    }
    
}
