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

import com.github.mcxiao.ipmsg.IPMsgProperties;
import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.util.PacketIdUtil;
import com.github.mcxiao.ipmsg.util.PacketParseUtil;

/**
 */
public abstract class Packet implements Element {

    protected final String packetNo;
    protected final String version;
    protected HostSub hostSub;
    protected Command command;
    protected String extString;

    protected Address to;
    protected Address from;
    
    public Packet() {
        this(IPMsgProperties.VERSION_STRING, generatePacketNo(), null);
    }
    
    public Packet(Command command) {
        this(IPMsgProperties.VERSION_STRING, generatePacketNo(), null);
        this.command = command;
    }
    
    public Packet(String version, String packetNo, HostSub hostSub) {
        this(version, packetNo, hostSub, new Command());
    }
    
    public Packet(String version, String packetNo, HostSub hostSub, Command command) {
        this.version = version;
        this.packetNo = packetNo;
        this.hostSub = hostSub;
        this.command = command;
    }
    
    public byte[] toBytes() {
        // XXX 避免多次 array copy
        String bufString = String.format(PacketParseUtil.FORMATTER, version, packetNo,
                hostSub.getSenderName(), hostSub.getSenderHost());
//        System.out.println(bufString);
        byte[] buf = bufString.getBytes();
        byte[] commandElementBytes = commandElementBytes();
//        msgBuf = msgBuf == null ? new byte[] {IPMsgProtocol.EOL_BYTE} : msgBuf;

        byte[] bytes = new byte[buf.length + commandElementBytes.length];

        System.arraycopy(buf, 0, bytes, 0, buf.length);
        System.arraycopy(commandElementBytes, 0, bytes, buf.length, commandElementBytes.length);

        // Add the eol byte
//        bytes[bytes.length - 1] = IPMsgProtocol.EOL_BYTE;

        return bytes;
    }
    
    protected abstract byte[] commandElementBytes();
    
    @Override
    public String toString() {
        return new String(toBytes());
    }
    
    public String getPacketNo() {
        return packetNo;
    }

    public String getVersion() {
        return version;
    }
    
    public HostSub getHostSub() {
        return this.hostSub;
    }
    
    public void setHostSub(HostSub hostSub) {
        this.hostSub = hostSub;
    }
    
    public Command getCommand() {
        return this.command;
    }
    
    protected void setCommand(Command command) {
        this.command = command;
    }
    
    protected String getExtString() {
        return extString;
    }
    
    protected void setExtString(String extString) {
        this.extString = extString;
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
    
//    public static Packet createByConnection(IPMsgConnection connection,
//                                            String packetNo, Command command) {
//        return createByConnection(connection, packetNo, command, null);
//    }
//
//    public static Packet createByConnection(IPMsgConnection connection,
//                                            String packetNo, Command command, byte[] msgBuf) {
//        return new Packet(connection.getVersion(), packetNo, command, connection.getHostSub(), msgBuf);
//    }

    public static String generatePacketNo() {
        return PacketIdUtil.newPacketId();
    }

}
