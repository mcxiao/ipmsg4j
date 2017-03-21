package com.github.mcxiao.ipmsg.filter;

import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.packet.Command;

/**
 *
 */

public class PresenceFilter extends CommandModeRangeFilter {
    
    public PresenceFilter() {
        super(IPMsgProtocol.IPMSG_BR_ENTRY,
                IPMsgProtocol.IPMSG_BR_ISGETLIST2);
    }
    
    public boolean accept(Command command) {
        return lowerFilter.accept(command) && greaterFilter.accept(command);
    }
    
}
