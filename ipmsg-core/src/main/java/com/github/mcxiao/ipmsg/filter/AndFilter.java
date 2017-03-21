package com.github.mcxiao.ipmsg.filter;

import com.github.mcxiao.ipmsg.PacketFilter;
import com.github.mcxiao.ipmsg.packet.Packet;

/**
 *
 */

public class AndFilter extends AbstractListFilter {
    
    public AndFilter() {
        super();
    }
    
    public AndFilter(PacketFilter... filters) {
        super(filters);
    }
    
    @Override
    public boolean accept(Packet packet) {
        for (PacketFilter filter : filters) {
            if (!filter.accept(packet))
                return false;
        }
        return true;
    }
}
