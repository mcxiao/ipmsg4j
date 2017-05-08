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

package com.github.mcxiao.ipmsg.packet;

import com.github.mcxiao.ipmsg.IPMsgException.IllegalPacketFormatException;
import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.provider.ExtensionElementProvider;
import com.github.mcxiao.ipmsg.provider.ProviderManager;
import com.github.mcxiao.ipmsg.util.ByteArrayUtil;
import com.github.mcxiao.ipmsg.util.LogUtil;
import com.github.mcxiao.ipmsg.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 */
public final class PacketParseUtil {
    private final static String TAG = LogUtil.createTag(PacketParseUtil.class.getSimpleName(), null);

    public final static String FORMATTER = "%s:%s:%s:%s:%d:%s\0";
    
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
    
        // Parse the base Packet fields.
        String version = parseVersion(decodeData.get(IPMsgProtocol.ITEM_VERSION_POSITION));
        String packetNo = parsePacketNo(decodeData.get(IPMsgProtocol.ITEM_PACKET_NO_POSITION));
        HostSub hostSub = parseHostSub(decodeData.get(IPMsgProtocol.ITEM_SENDER_NAME_POSITION),
                decodeData.get(IPMsgProtocol.ITEM_SENDER_HOST_POSITION));
        // Command used to filter packets.
        Command command = parseCommand(decodeData.get(IPMsgProtocol.ITEM_COMMAND_POSITION));
        // Parse extension String by the specific character set(utf8 or default: cp932).
        String extString = null;
        if (IPMsgProtocol.ITEM_EXTENSION_POSITION < decodeData.size()) {
            extString = parseExtensionString(isUseUtf8(command), decodeData.get(IPMsgProtocol.ITEM_EXTENSION_POSITION));
        }
        ExtensionElement extensionElement = null;
        if (!StringUtil.isNullOrEmpty(extString)) {
            extensionElement = parseExtensionElement(command, extString);
        }
        
        if (Presence.PRESENCE_FILTER.accept(command)) {
            Presence presence = new Presence(version, packetNo, hostSub, command);
            presence.setFrom(from);
            presence.setExtension(extensionElement);
            return presence;
        } else if (Message.MESSAGE_FILTER.accept(command)) {
            Message message = new Message(version, packetNo, hostSub, command);
            message.setFrom(from);
            message.setExtension(extensionElement);
            return message;
//        } else if () {    // TODO: Parse IQ
        } else {
            UndefinedPacket packet = new UndefinedPacket(version, packetNo, hostSub, command);
            packet.setFrom(from);
            packet.setExtension(extensionElement);
            LogUtil.infoWithParams(TAG, "Undefined packet received ->%s", packet.toString());
            return packet;
        }
    }
    
    public static String parseVersion(byte[] version) {
        Objects.requireNonNull(version, "Params can't be null.");
    
        // XXX Character codes ?
        return new String(version);
    }
    
    public static String parsePacketNo(byte[] packetNo) {
        Objects.requireNonNull(packetNo, "Params can't be null.");
   
        // XXX Character codes ?
        return new String(packetNo);
    }
    
    public static HostSub parseHostSub(byte[] nameBytes, byte[] hostBytes) {
        Objects.requireNonNull(nameBytes, "Params can't be null.");
        Objects.requireNonNull(hostBytes, "Params can't be null.");
        
        // XXX Character codes ?
        return new HostSub(new String(nameBytes), new String(hostBytes));
    }
    
    /**
     * IPMsg parse command use it String type to byte array.
     */
    public static Command parseCommand(byte[] bytes) throws Exception {
        Objects.requireNonNull(bytes,  "Params can't be null.");
        
        int code = Integer.valueOf(new String(bytes));
        return new Command(code);
    }
    
    public static boolean isUseUtf8(Command command) {
        return command.acceptOpt(IPMsgProtocol.IPMSG_UTF8OPT)
                || command.acceptOpt(IPMsgProtocol.IPMSG_CAPUTF8OPT);
    }
    
    public static String parseExtensionString(boolean supportUtf8, byte[] ext) {
        // XXX Character codes ?
        return new String(ext);
    }
    
    public static ExtensionElement parseExtensionElement(Command command, String extString) throws Exception {
        ExtensionElementProvider<? extends ExtensionElement> extensionProvider = ProviderManager.getExtensionProvider(new Command(command.getMode()));
        
        if (extensionProvider != null) {
            return extensionProvider.parse(command, extString);
        }
        return null;
    }

}
