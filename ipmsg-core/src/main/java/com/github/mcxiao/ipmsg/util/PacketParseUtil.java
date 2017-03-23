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
import com.github.mcxiao.ipmsg.packet.HostSub;
import com.github.mcxiao.ipmsg.packet.Message;
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
    
    
        String version = parseVersion(decodeData.get(IPMsgProtocol.ITEM_VERSION_POSITION));
        String packetNo = parsePacketNo(decodeData.get(IPMsgProtocol.ITEM_PACKET_NO_POSITION));
        HostSub hostSub = parseHostSub(decodeData.get(IPMsgProtocol.ITEM_SENDER_NAME_POSITION),
                decodeData.get(IPMsgProtocol.ITEM_SENDER_HOST_POSITION));
        String extString = null;
        if (IPMsgProtocol.ITEM_EXTENSION_POSITION < decodeData.size()) {
            extString = parseExtensionString(decodeData.get(IPMsgProtocol.ITEM_EXTENSION_POSITION));
        }
        
        Command command = parseCommand(decodeData.get(IPMsgProtocol.ITEM_COMMAND_POSITION));
        if (Presence.PRESENCE_FILTER.accept(command)) {
            Presence presence = new Presence(version, packetNo, hostSub, command);
            presence.setExtString(extString);
            presence.setFrom(from);
            return presence;
        } else if (Message.MESSAGE_FILTER.accept(command)) {
            Message message = new Message(version, packetNo, hostSub, command);
            message.setExtString(extString);
            message.setFrom(from);
            return message;
//        } else if () {    // TODO: Parse IQ
        }
        
        throw new IllegalPacketFormatException("Can't parse data.");
    }
    
    public static String parseVersion(byte[] version) {
        ObjectUtil.requiredNonNull(version, "Params can't be null.");
    
        // XXX Character codes ?
        return new String(version);
    }
    
    public static String parsePacketNo(byte[] packetNo) {
        ObjectUtil.requiredNonNull(packetNo, "Params can't be null.");
   
        // XXX Character codes ?
        return new String(packetNo);
    }
    
    public static HostSub parseHostSub(byte[] nameBytes, byte[] hostBytes) {
        ObjectUtil.requiredNonNull(nameBytes, "Params can't be null.");
        ObjectUtil.requiredNonNull(hostBytes, "Params can't be null.");
        
        // XXX Character codes ?
        return new HostSub(new String(nameBytes), new String(hostBytes));
    }
    
    public static Command parseCommand(byte[] bytes) throws Exception {
        ObjectUtil.requiredNonNull(bytes,  "Params can't be null.");
        
        if (bytes.length > 4) {
            throw new IllegalPacketFormatException("Command format error.");
        }
        
        int code = ByteArrayUtil.toInteger(bytes);
        return new Command(code);
    }
    
    public static String parseExtensionString(byte[] ext) {
        // XXX Character codes ?
        return new String(ext);
    }

}
