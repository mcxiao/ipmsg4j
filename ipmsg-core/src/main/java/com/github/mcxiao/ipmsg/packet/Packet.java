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
import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.util.PacketIdUtil;
import com.github.mcxiao.ipmsg.util.StringUtil;

/**
 */
public abstract class Packet implements Element {

    protected final String packetNo;
    protected final String version;
    protected HostSub hostSub;
    protected Command command;
    protected ExtensionElement extension;

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
        ExtensionElement extension = getExtension();
        String extString = extension == null ? null : extension.toExtensionString();
        
        String bufString = StringUtil.format(PacketParseUtil.FORMATTER, version, packetNo,
                hostSub.getSenderName(), hostSub.getSenderHost(), command.getCommand(), extString);
        
        return bufString.getBytes();
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
    
    @SuppressWarnings("unchecked")
    public <EE extends ExtensionElement> EE getExtension() {
        return (EE) extension;
    }
    
    protected void setExtension(ExtensionElement extension) {
        this.extension = extension;
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
    
    @Override
    public String toString() {
        return new String(toBytes());
    }
    
    public static String generatePacketNo() {
        return PacketIdUtil.newPacketId();
    }
}
