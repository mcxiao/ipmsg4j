package com.github.mcxiao.ipmsg.provider;

import com.github.mcxiao.ipmsg.packet.Command;
import com.github.mcxiao.ipmsg.packet.Element;

/**
 *
 */

public abstract class Provider<E extends Element> {
    
    public final E parse(Command commands, byte[] extBytes, boolean useUtf8) throws Exception {
        // FIXME Parse utf8 or cp932
        String extString = new String(extBytes);
    
        E e = parse(commands, extString);
        
        return e;
    }
    
    public abstract E parse(Command command, String extString) throws Exception;
    
}
