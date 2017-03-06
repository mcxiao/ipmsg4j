package com.github.mcxiao;

import com.github.mcxiao.packet.Packet;

/**
 */
public interface PacketListener {

    void processPacket(Packet packet);

}
