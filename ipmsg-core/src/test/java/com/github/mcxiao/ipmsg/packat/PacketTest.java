/*
 * Copyright [2017] [$author]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mcxiao.ipmsg.packat;

import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.packet.Command;
import com.github.mcxiao.ipmsg.packet.HostSub;
import com.github.mcxiao.ipmsg.util.PacketParseUtil;
import com.github.mcxiao.ipmsg.packet.Packet;
import org.junit.Assert;
import org.junit.Test;

public class PacketTest {

    byte[] result = new byte[]{49, 58, 49, 48, 48, 48, 58, 115, 101, 110, 100, 101, 114, 78, 97, 109, 101, 58, 115,
            101, 110, 100, 101, 114, 72, 111, 115, 116, 58, 49, 58, 0, 1};
    byte[] result2 = new byte[]{49, 58, 49, 48, 48, 48, 58, 115, 101, 110, 100, 101, 114, 78, 97, 109, 101, 58, 115,
            101, 110, 100, 101, 114, 72, 111, 115, 116, 58, 49, 58};

    String version = "1";
    String packetNo = "1000";
    int command = 1;
    String senderName = "senderName";
    String senderHost = "senderHost";
    byte[] msgBuf = new byte[]{0, 1};

//    @Test
//    public void testPacketToBytes() {
//
//        Packet packet = new Packet(version, packetNo,
//                new Command(command),
//                new HostSub(senderName, senderHost));
//        byte[] bytes = packet.toBytes();
//        Assert.assertArrayEquals(result2, bytes);
//        packet.setMsgBuf(msgBuf);
//        bytes = packet.toBytes();
//
//        Assert.assertArrayEquals(result, bytes);
//    }
//
//    @Test
//    public void testBytesToPacket() throws Exception {
//        Packet packet = PacketParseUtil.parsePacket("192.168.0.1", IPMsgProtocol.PORT, result);
//
//        Assert.assertEquals(version, packet.getVersion());
//        Assert.assertEquals(packetNo, packet.getPacketNo());
//        Assert.assertEquals(command, packet.getCommand().getCommand());
//        Assert.assertEquals(senderName, packet.getHostSub().getSenderName());
//        Assert.assertEquals(senderHost, packet.getHostSub().getSenderHost());
//        Assert.assertArrayEquals(msgBuf, packet.getMsgBuf());
//    }

}
