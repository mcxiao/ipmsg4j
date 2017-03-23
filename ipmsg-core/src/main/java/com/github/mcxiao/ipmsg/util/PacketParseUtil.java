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

package com.github.mcxiao.ipmsg.util;

import com.github.mcxiao.ipmsg.IPMsgException.IllegalPacketFormatException;
import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.packet.Command;
import com.github.mcxiao.ipmsg.packet.Packet;
import com.github.mcxiao.ipmsg.packet.Presence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 */
public final class PacketParseUtil {
    private final static String TAG = LogUtil.createTag(PacketParseUtil.class.getSimpleName(), null);

    public final static String FORMATTER = "%s:%s:%s:%s:";
    
    private final static int MAX_PACKET_ITEM_COUNT = IPMsgProtocol.MAX_PACKET_ITEM_COUNT;

    public static Packet parsePacket(Address from, byte[] data) throws Exception {
        // XXX Need a performance update
        String version = null;
        String packetNo = null;
        String senderName = null;
        String senderHost = null;
        int commandCode = -1;
        byte[] ext = null;

        byte[] pattern = new byte[]{IPMsgProtocol.SEPARATOR_BYTE};
        List<byte[]> decodeData = new ArrayList<>(MAX_PACKET_ITEM_COUNT);
    
        for (int offset = 0; (offset < data.length && decodeData.size() <= MAX_PACKET_ITEM_COUNT); ) {
            byte[] bytes = ByteArrayUtil.splitOrderByFirst(pattern, data, offset);
            if (bytes != null) {
                decodeData.add(bytes);
                offset = offset + bytes.length + pattern.length;
            } else if (decodeData.size() >= MAX_PACKET_ITEM_COUNT - 1) {
                decodeData.add(Arrays.copyOfRange(data, offset, data.length));
                break;
            } else {
                throw new IllegalPacketFormatException(
                        StringUtil.format("Packet(%s) format error.", new String(data)));
            }
        }
    
        // TODO: 2017/3/21 Parse packet according commandCode.
        Command command = parseCommand(decodeData.get(IPMsgProtocol.ITEM_COMMAND_POSITION));
        if (Presence.PRESENCE_FILTER.accept(command)) {
            return parsePresence(command, decodeData);
//        } else if () {    // TODO: Parse IQ and Message
        }
        
        throw new IllegalPacketFormatException("Can't parse data.");
    }
    
    public static Command parseCommand(byte[] bytes) throws Exception {
        ObjectUtil.requiredNonNull(bytes,  "Params can't be null.");
        
        if (bytes.length > 4) {
            throw new IllegalPacketFormatException("Command format error.");
        }
        
        int code = ByteArrayUtil.toInteger(bytes);
        return new Command(code);
    }
    
    public static Presence parsePresence(Command command, List<byte[]> bytes) {
        // XXX assert command is a Presence Command
        ObjectUtil.requiredNonNull(command, "Params can't be null.");
        ObjectUtil.requiredNonNull(bytes, "Params can't be null.");
    
        Presence presence = new Presence(command.getMode());
        presence.setSupportFileAttach(command.acceptOpt(IPMsgProtocol.IPMSG_FILEATTACHOPT));
        presence.setSupportUtf8(command.acceptOpt(IPMsgProtocol.IPMSG_UTF8OPT));
        // XXX Other Presence.field extension by future.
        
        return presence;
    }

}
