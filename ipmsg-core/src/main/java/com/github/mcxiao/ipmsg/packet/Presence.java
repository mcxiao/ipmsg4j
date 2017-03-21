package com.github.mcxiao.ipmsg.packet;


import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.filter.PresenceFilter;
import com.github.mcxiao.ipmsg.util.ByteArrayUtil;

/**
 *
 */

public final class Presence extends Packet {
    
    public final static int TYPE_BR_ENTRY = IPMsgProtocol.IPMSG_BR_ENTRY;
    public final static int TYPE_BR_EXIT = IPMsgProtocol.IPMSG_BR_EXIT;
    public final static int TYPE_BR_ANSENTRY = IPMsgProtocol.IPMSG_ANSENTRY;
    public final static int TYPE_BR_ABSENCE = IPMsgProtocol.IPMSG_BR_ABSENCE;
    
    public final static PresenceFilter PRESENCE_FILTER = new PresenceFilter();
    
    private int type = TYPE_BR_ENTRY;
    private boolean supportFileAttach;
    private boolean supportUtf8;
    
    public Presence(int type) {
        super();
        setType(type);
    }
    
    public Presence(String version, String packetNo, HostSub hostSub, int type) {
        super(version, packetNo, hostSub);
        setType(type);
    }
    
    @Override
    protected byte[] commandElementBytes() {
        byte[] commandBytes = ByteArrayUtil.toByteArray(command.getCommand());
        return commandBytes;
    }
    
    public int getType() {
        return type;
    }
    
    private void setType(int type) {
        this.type = type;
    }
    
    public boolean isSupportFileAttach() {
        return supportFileAttach;
    }
    
    public void setSupportFileAttach(boolean supportFileAttach) {
        if (supportFileAttach) {
            command.acceptOpt(IPMsgProtocol.IPMSG_FILEATTACHOPT);
        } else {
            command.removeOption(IPMsgProtocol.IPMSG_FILEATTACHOPT);
        }
        this.supportFileAttach = supportFileAttach;
    }
    
    public boolean isSupportUtf8() {
        return supportUtf8;
    }
    
    public void setSupportUtf8(boolean supportUtf8) {
        if (supportUtf8) {
            command.acceptOpt(IPMsgProtocol.IPMSG_UTF8OPT);
        } else {
            command.removeOption(IPMsgProtocol.IPMSG_UTF8OPT);
        }
        this.supportUtf8 = supportUtf8;
    }
    
}
