package com.github.mcxiao.ipmsg.roster.packet;

import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.packet.Command;
import com.github.mcxiao.ipmsg.packet.HostSub;
import com.github.mcxiao.ipmsg.packet.Presence;
import com.github.mcxiao.ipmsg.packet.extension.AbsenceExtension;

/**
 *
 */

public class RosterPacket extends Presence {
    
    public final static int TYPE_BR_ENTRY = IPMsgProtocol.IPMSG_BR_ENTRY;
    public final static int TYPE_BR_EXIT = IPMsgProtocol.IPMSG_BR_EXIT;
    public final static int TYPE_BR_ANSENTRY = IPMsgProtocol.IPMSG_ANSENTRY;
    public final static int TYPE_BR_ABSENCE = IPMsgProtocol.IPMSG_BR_ABSENCE;
    
    private AbsenceExtension extension;
    
    public RosterPacket(int type) {
        super(type);
        extension = new AbsenceExtension();
    }
    
    public RosterPacket(String version, String packetNo, HostSub hostSub, Command command) {
        super(version, packetNo, hostSub, command);
        extension = new AbsenceExtension();
    }
    
    public void setUserName(String name) {
        extension.setUserName(name);
    }
    
    public String getUserName() {
        return extension != null ? extension.getUserName() : null;
    }
    
    public void setUserHost(String host) {
        extension.setHostName(host);
    }
    
    public String getUserHost() {
        return extension != null ? extension.getHostName() : null;
    }
    
    public void setGroupName(String name) {
        extension.setGroupName(name);
    }
    
    public String getGroupName() {
        return extension != null ? extension.getGroupName() : null;
    }
    
    public void setNickName(String name) {
        extension.setNickName(name);
    }
    
    public String getNickName() {
        return extension != null ? extension.getNickName() : null;
    }
    
}
