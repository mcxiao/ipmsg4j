package com.github.mcxiao.ipmsg.filter;

import com.github.mcxiao.ipmsg.PacketFilter;
import com.github.mcxiao.ipmsg.packet.Packet;
import com.github.mcxiao.ipmsg.util.StringUtil;

/**
 *
 */
public class CommandModeRangeFilter implements PacketFilter {
    
    private AndFilter rangeFilter;
    protected final CommandLowerFilter lowerFilter;
    protected final CommandGreaterFilter greaterFilter;
    
    public CommandModeRangeFilter(int le, int ge) {
        if (le < ge) {
            throw new IllegalStateException(
                    StringUtil.format("ge(%d) can't lower than le(%d)", le, ge));
        }
        
        lowerFilter = new CommandLowerFilter(le, true);
        greaterFilter = new CommandGreaterFilter(ge, true);
        rangeFilter = new AndFilter(lowerFilter, greaterFilter);
    }
    
    @Override
    public boolean accept(Packet packet) {
        return rangeFilter.accept(packet);
    }
    
}
