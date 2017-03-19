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

import com.github.mcxiao.ipmsg.IPMsgConnection;
import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.util.PacketIdUtil;
import com.github.mcxiao.ipmsg.util.PacketParseUtil;

/**
 */
public class Packet implements Element {

    private final String packetNo;
    private final String version;
    private final Command command;
    private final HostSub hostSub;
    private byte[] msgBuf;
    private Long timestamp;

    private Address to;
    private Address from;

    public Packet(String version, String packetNo, Command command, HostSub hostSub) {
        this(version, packetNo, command, hostSub, null);
    }

    public Packet(String version, String packetNo, Command command, HostSub hostSub, byte[] msgBuf) {
        this(version, packetNo, command, hostSub, msgBuf, null);
    }

    public Packet(String version, String packetNo, Command command, HostSub hostSub, byte[] msgBuf, Long timestamp) {
        this.version = version;
        this.packetNo = packetNo;
        this.command = command;
        this.hostSub = hostSub;
        this.msgBuf = msgBuf;
        this.timestamp = timestamp;
    }

    public byte[] toBytes() {
        // XXX 避免多次 array copy
        String bufString = String.format(PacketParseUtil.FORMATTER, version, packetNo,
                hostSub.getSenderName(), hostSub.getSenderHost(), command.getCommand());
//        System.out.println(bufString);
        byte[] buf = bufString.getBytes();
        if (msgBuf == null) {
            msgBuf = new byte[0];
        }
//        msgBuf = msgBuf == null ? new byte[] {IPMsgProtocol.EOL_BYTE} : msgBuf;

        byte[] bytes = new byte[buf.length + msgBuf.length];

        System.arraycopy(buf, 0, bytes, 0, buf.length);
        System.arraycopy(msgBuf, 0, bytes, buf.length, msgBuf.length);

        // Add the eol byte
//        bytes[bytes.length - 1] = IPMsgProtocol.EOL_BYTE;

        return bytes;
    }

    public String getPacketNo() {
        return packetNo;
    }

    public String getVersion() {
        return version;
    }

    public Command getCommand() {
        return command;
    }

    public HostSub getHostSub() {
        return hostSub;
    }

    public byte[] getMsgBuf() {
        return msgBuf;
    }

    public void setMsgBuf(byte[] msgBuf) {
        this.msgBuf = msgBuf;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Address getTo() {
        return to;
    }

    public void setTo(Address to) {
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public void setFrom(Address from) {
        this.from = from;
    }

    public static Packet createByConnection(IPMsgConnection connection,
                                            String packetNo, Command command) {
        return createByConnection(connection, packetNo, command, null);
    }

    public static Packet createByConnection(IPMsgConnection connection,
                                            String packetNo, Command command, byte[] msgBuf) {
        return new Packet(connection.getVersion(), packetNo, command, connection.getHostSub(), msgBuf);
    }

    public static String generatePacketNo() {
        return PacketIdUtil.newPacketId();
    }

}
