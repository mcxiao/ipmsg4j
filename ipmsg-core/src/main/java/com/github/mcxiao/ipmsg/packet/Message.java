package com.github.mcxiao.ipmsg.packet;

import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.filter.MessageFilter;

import java.nio.ByteBuffer;

/**
 *
 */

public class Message extends Packet {
    
    public final static int TYPE_SEND_MSG = IPMsgProtocol.IPMSG_SENDMSG;
    public final static int TYPE_RECV_MSG = IPMsgProtocol.IPMSG_RECVMSG;
    
    public final static int CHECK_NONE = 0;
    public final static int CHECK_SEND = IPMsgProtocol.IPMSG_SENDCHECKOPT;
    public final static int CHECK_RECV = IPMsgProtocol.IPMSG_READCHECKOPT;
    
    public final static MessageFilter MESSAGE_FILTER = new MessageFilter();
    
    private int type;
    private int checkMode = CHECK_NONE;
    
    public Message(Command command) {
        // Check type available.
        // TODO Don't use the command.mode value to switch this.Type value.
        super(command);
        setType(command.getMode());
    }
    
    public Message(int type) {
        super();
        setType(type);
    }
    
    public Message(String version, String packetNo, HostSub hostSub, Command command) {
        super(version, packetNo, hostSub, command);
        // TODO Don't use the command.mode value to switch this.Type value.
        setType(command.getMode());
    }
    
    @Override
    protected byte[] commandElementBytes() {
        byte[] extBytes;
        if (isUtf8Codec()) {
            // FIXME Specify the Utf8 charset
            extBytes = extString.getBytes();
        } else {
            // XXX Default charset is CP932
            extBytes = extString.getBytes();
        }
        
        // command is a int and take up 4 byte.
        ByteBuffer buffer = ByteBuffer.allocate(4 + extBytes.length);
        buffer.putInt(command.getCommand());
        buffer.put(extBytes);
        return buffer.array();
    }
    
    public void setType(int type) {
        switch (type) {
            case TYPE_SEND_MSG:
                command.setMode(IPMsgProtocol.IPMSG_SENDMSG);
                break;
            case TYPE_RECV_MSG:
                command.setMode(this.type = IPMsgProtocol.IPMSG_RECVMSG);
                break;
            default:
                // FIXME Illegal message command.
                break;
        }
        this.type = type;
    }
    
    public int getType() {
        return this.type;
    }
    
    public boolean isBroadcast() {
        return command.acceptOpt(IPMsgProtocol.IPMSG_BROADCASTOPT);
    }
    
    public void setBroadcast(boolean broadcast) {
        command.addOrRemoveOpt(broadcast, IPMsgProtocol.IPMSG_BROADCASTOPT);
    }
    
    public boolean isFileAttach() {
        return command.acceptOpt(IPMsgProtocol.IPMSG_FILEATTACHOPT);
    }
    
    public void setFileAttach(boolean fileAttach) {
        command.addOrRemoveOpt(fileAttach, IPMsgProtocol.IPMSG_FILEATTACHOPT);
    }
    
    public boolean isUtf8Codec() {
        return command.acceptOpt(IPMsgProtocol.IPMSG_UTF8OPT);
    }
    
    public void setUtf8Codec(boolean utf8Codec) {
        command.addOrRemoveOpt(utf8Codec, IPMsgProtocol.IPMSG_UTF8OPT);
    }
    
    public boolean isSecretMessage() {
        return command.acceptOpt(IPMsgProtocol.IPMSG_SECRETOPT);
    }
    
    public void setSecretMessage(boolean isSecret) {
        command.addOrRemoveOpt(isSecret, IPMsgProtocol.IPMSG_SECRETOPT);
    }
    
    @Override
    public String getExtString() {
        return extString;
    }
    
    @Override
    public void setExtString(String extString) {
        this.extString = extString;
    }
    
}
