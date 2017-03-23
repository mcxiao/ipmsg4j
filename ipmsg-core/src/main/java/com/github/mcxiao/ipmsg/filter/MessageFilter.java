package com.github.mcxiao.ipmsg.filter;

import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.packet.Command;

/**
 *
 */

public class MessageFilter extends CommandModeRangeFilter {
    
    public MessageFilter() {
        super(IPMsgProtocol.IPMSG_SENDMSG, IPMsgProtocol.IPMSG_ANSREADMSG);
    }
    
    public boolean accept(Command command) {
        return lowerFilter.accept(command) && greaterFilter.accept(command);
    }
}

