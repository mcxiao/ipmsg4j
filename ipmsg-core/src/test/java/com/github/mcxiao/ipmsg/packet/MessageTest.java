package com.github.mcxiao.ipmsg.packet;

import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.packet.extension.BareExtension;
import com.github.mcxiao.ipmsg.packet.extension.BareExtensionProvider;
import com.github.mcxiao.ipmsg.provider.ProviderManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */

public class MessageTest {
    
    Address from = new Address("192.168.0.1", IPMsgProtocol.PORT);
    
    String version = "1";
    String packetNo = "1001";
    int commandCode = 0x800020;
    Command command = new Command(commandCode);
    String senderName = "senderName";
    String senderHost = "senderHost";
    HostSub hostSub = new HostSub(senderName, senderHost);
    String extString = "Hello World";
    
    String result = "1:1001:senderName:senderHost:8388640:Hello World\0";
    
    @Before
    public void setup() {
        ProviderManager.addExtensionProvider(new Command(0x20), new BareExtensionProvider());
    }
    
    private static void assertMessageStatus(Message message) {
        Assert.assertEquals(Message.TYPE_SEND_MSG, message.getType());
        Assert.assertEquals(true, message.isUtf8Codec());
        Assert.assertEquals(false, message.isBroadcast());
    }
    
    @Test
    public void testMessageParse() throws Exception {
        Message message = new Message(version, packetNo, hostSub, command);
        message.setFrom(from);
        message.setExtension(new BareExtension(extString));
        // Assert message status
        assertMessageStatus(message);
        
        // Assert message to byte array
        byte[] bytes = message.toBytes();
        String s = new String(bytes);
        Assert.assertEquals(result, s);
        
        // Assert byte array to message
        Message packet = (Message) PacketParseUtil.parsePacket(from, bytes);
        assertMessageStatus(packet);
        PacketTest.assertPacketStatus(message, packet);
    }
    
}
