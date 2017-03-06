package com.github.mcxiao;

import com.github.mcxiao.packet.Packet;

/**
 */
public interface PacketInterceptor {

    void processPacket(Packet packet);

}
