package com.github.mcxiao.ipmsg.packet;

import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.packet.extension.BareExtension;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */

public class PresenceTest {
    
    Address from = new Address("192.168.0.1", IPMsgProtocol.PORT);
    
    String version = "1";
    String packetNo = "1000";
    int commandCode = 0x1000001;
    Command command = new Command(commandCode);
    String senderName = "senderName";
    String senderHost = "senderHost";
    HostSub hostSub = new HostSub(senderName, senderHost);
    String extString = "Hello World";
    
    String result = "1:1000:senderName:senderHost:16777217:Hello World\0";
    
    private static void assertPresenceStatus(Presence presence) {
        Assert.assertEquals(Presence.TYPE_BR_ENTRY, presence.getType());
        Assert.assertEquals(true, presence.isSupportUtf8());
        Assert.assertEquals(false, presence.isSupportFileAttach());
    }
    
    @Test
    public void testPresenceParse() throws Exception {
        Presence presence = new Presence(version, packetNo, hostSub, command);
        presence.setExtension(new BareExtension(extString));
        presence.setFrom(from);
        // Assert presence status
        assertPresenceStatus(presence);
    
        // Assert presence to byte array
        byte[] bytes = presence.toBytes();
        String s = new String(bytes);
        Assert.assertEquals(result, s);
    
        // Assert byte array to presence
        Presence packet = (Presence) PacketParseUtil.parsePacket(from, bytes);
        assertPresenceStatus(packet);
        PacketTest.assertPacketStatus(presence, packet);
    }
    
}
