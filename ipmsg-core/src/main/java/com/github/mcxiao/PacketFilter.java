package com.github.mcxiao;

import com.github.mcxiao.packet.Packet;

/**
 */
public interface PacketFilter {

    boolean accept(Packet packet);

}
