package com.github.mcxiao.ipmsg.packet;

import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.util.PacketParseUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */

public class PresenceTest {
    
//    final byte[] result = new byte[] {49, 58, 49, 48, 48, 48, 58, 115, 101, 110, 100, 101, 114, 78, 97,
//            109, 101, 58, 115, 101, 110, 100, 101, 114, 72, 111, 115, 116, 58, 0, -128, 0, 1};
    
    Address from = new Address("192.168.0.1", IPMsgProtocol.PORT);
    
    String version = "1";
    String packetNo = "1000";
    int commandCode = 0x800001;
    Command command = new Command(commandCode);
    String senderName = "senderName";
    String senderHost = "senderHost";
    HostSub hostSub = new HostSub(senderName, senderHost);
    String extString = "Hello World";
    
    String result = "1:1000:senderName:senderHost:8388609:Hello World";
    
    @Test
    public void testPresenceParse() throws Exception {
        Presence presence = new Presence(version, packetNo, hostSub, command);
        presence.setExtString(extString);
        presence.setFrom(from);
    
        byte[] bytes = presence.toBytes();
        String s = new String(bytes);
        Assert.assertEquals(result, s);
    
        Presence packet = (Presence) PacketParseUtil.parsePacket(from, bytes);
        Assert.assertEquals(presence.getType(), packet.getType());
    
        Assert.assertEquals(presence.getExtString(), packet.getExtString());
        Assert.assertEquals(presence.getCommand(), packet.getCommand());
        Assert.assertEquals(presence.getHostSub(), packet.getHostSub());
        Assert.assertEquals(presence.getPacketNo(), packet.getPacketNo());
        Assert.assertEquals(presence.getVersion(), packet.getVersion());
        Assert.assertEquals(presence.getFrom(), packet.getFrom());
    }
    
}
