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
    protected byte[] extensionElementToBytes() {
        return new byte[0];
    }
    
    @Override
    public void setExtString(String extString) {
        super.setExtString(extString);
    }
    
    @Override
    public String getExtString() {
        return super.getExtString();
    }
}
