package com.github.mcxiao.ipmsg.filter;

import com.github.mcxiao.ipmsg.PacketFilter;
import com.github.mcxiao.ipmsg.packet.Command;
import com.github.mcxiao.ipmsg.packet.Packet;

/**
 *
 */
public abstract class AbstractPacketCommandFilter implements PacketFilter {
    @Override
    public boolean accept(Packet packet) {
        Command command = packet.getCommand();
        return command != null && accept(command);
    }
    
    protected abstract boolean accept(Command command);
    
}
