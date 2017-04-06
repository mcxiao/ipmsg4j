package com.github.mcxiao.ipmsg.packet;

/**
 *
 */

public class UndefinedPacket extends Packet {
    
    public UndefinedPacket() {
    }
    
    public UndefinedPacket(Command command) {
        super(command);
    }
    
    public UndefinedPacket(String version, String packetNo, HostSub hostSub) {
        super(version, packetNo, hostSub);
    }
    
    public UndefinedPacket(String version, String packetNo, HostSub hostSub, Command command) {
        super(version, packetNo, hostSub, command);
    }
    
    @Override
    public ExtensionElement getExtension() {
        return null;
    }

}
