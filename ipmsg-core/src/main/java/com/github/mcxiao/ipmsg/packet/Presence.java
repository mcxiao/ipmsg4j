package com.github.mcxiao.ipmsg.packet;


import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.filter.PresenceFilter;
import com.github.mcxiao.ipmsg.util.StringUtil;

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
    
    public Presence(int type) {
        super();
        setType(type);
    }
    
    public Presence(String version, String packetNo, HostSub hostSub, Command command) {
        super(version, packetNo, hostSub, command);
        // TODO Don't use the command.mode value to switch this.Type value.
        setType(command.getMode());
    }
    
    @Override
    protected byte[] extensionElementToBytes() {
        if (StringUtil.isNullOrEmpty(extString)) {
            return new byte[0];
        }
        
        byte[] extBytes;
        if (isSupportUtf8()) {
            // FIXME Specify the Utf8 charset
            extBytes = extString.getBytes();
        } else {
            // XXX Default charset is CP932
            extBytes = extString.getBytes();
        }
    
        return extBytes;
    }
    
    public int getType() {
        return type;
    }
    
    private void setType(int type) {
        switch (type) {
            case TYPE_BR_ENTRY:
            case TYPE_BR_EXIT:
            case TYPE_BR_ANSENTRY:
            case TYPE_BR_ABSENCE:
                command.setMode(type);
                break;
            default:
                // FIXME Illegal presence command
                break;
        }
        this.type = type;
    }
    
    public boolean isSupportFileAttach() {
        return command.acceptOpt(IPMsgProtocol.IPMSG_FILEATTACHOPT);
    }
    
    public void setSupportFileAttach(boolean supportFileAttach) {
        command.addOrRemoveOpt(supportFileAttach, IPMsgProtocol.IPMSG_FILEATTACHOPT);
    }
    
    public boolean isSupportUtf8() {
        return command.acceptOpt(IPMsgProtocol.IPMSG_CAPUTF8OPT);
    }
    
    public void setSupportUtf8(boolean supportUtf8) {
        // Apply the protocol Chapter.5 UTF-8 extension
        // So use CAPUTF8OPT in IPMSG_BR_ENTRY/IPMSG_ANS_ENTRY/IPMSG_BR_ABSENCE
        // and must not use UTF8OPT, for backward compatibility.
        command.addOrRemoveOpt(supportUtf8, IPMsgProtocol.IPMSG_CAPUTF8OPT);
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
