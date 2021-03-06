package com.github.mcxiao.ipmsg.filter;

import com.github.mcxiao.ipmsg.PacketFilter;
import com.github.mcxiao.ipmsg.packet.Packet;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 *
 */

public abstract class PacketTypeFilter<P extends Packet> implements PacketFilter {
    
    protected final Class<P> packetType;
    
    public PacketTypeFilter(Class<P> packetType) {
        this.packetType = Objects.requireNonNull(packetType, "PacketType can't be null.");
    }
    
    @SuppressWarnings("unchecked")
    public PacketTypeFilter() {
        packetType = (Class<P>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    @Override
    public boolean accept(Packet packet) {
        return packetType.isInstance(packet) && acceptType((P) packet);
    }
    
    protected abstract boolean acceptType(P packet);
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + packetType.toString();
    }
}
