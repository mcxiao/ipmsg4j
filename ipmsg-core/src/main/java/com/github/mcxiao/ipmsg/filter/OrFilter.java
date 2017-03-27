package com.github.mcxiao.ipmsg.filter;

import com.github.mcxiao.ipmsg.PacketFilter;
import com.github.mcxiao.ipmsg.packet.Packet;

/**
 *
 */

public class OrFilter extends AbstractListFilter {
    
    public OrFilter() {
    }
    
    public OrFilter(PacketFilter... filters) {
        super(filters);
    }
    
    @Override
    public boolean accept(Packet packet) {
        for (PacketFilter filter : filters) {
            if (filter.accept(packet)) {
                return true;
            }
        }
        
        return false;
    }
}
