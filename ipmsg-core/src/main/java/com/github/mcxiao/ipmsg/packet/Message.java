package com.github.mcxiao.ipmsg.packet;

import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.filter.MessageFilter;
import com.github.mcxiao.ipmsg.packet.extension.MessageBodyExtension;
import com.github.mcxiao.ipmsg.util.StringUtil;

/**
 *
 */

public class Message extends Packet {
    
    public final static int TYPE_SEND_MSG = IPMsgProtocol.IPMSG_SENDMSG;
    public final static int TYPE_RECV_MSG = IPMsgProtocol.IPMSG_RECVMSG;
    
    public final static int CHECK_NONE = 0;
    public final static int CHECK_SEND = IPMsgProtocol.IPMSG_SENDCHECKOPT;
    public final static int CHECK_READ = IPMsgProtocol.IPMSG_READCHECKOPT;
    
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
    
    public Message(int type, String body) {
        super();
        setType(type);
        setExtension(new MessageBodyExtension(body));
    }
    
    public Message(int type, int checkMode) {
        this(type, checkMode, null);
    }
    
    public Message(int type, int checkMode, String body) {
        super();
        setType(type);
        setCheckMode(checkMode);
        setExtension(new MessageBodyExtension(body));
    }
    
    public Message(String version, String packetNo, HostSub hostSub, Command command) {
        super(version, packetNo, hostSub, command);
        // TODO Don't use the command.mode value to switch this.Type value.
        setType(command.getMode());
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
                throw new IllegalArgumentException(StringUtil.format(
                        "Message.Type setting failure.(actual:%s)", Integer.toHexString(type)));
        }
        this.type = type;
    }
    
    @Override
    public MessageBodyExtension getExtension() {
        return super.getExtension();
    }
    
    public int getType() {
        return this.type;
    }
    
    public void setCheckMode(int checkMode) {
        switch (checkMode) {
            case CHECK_NONE:
                command.removeOption(IPMsgProtocol.IPMSG_SENDCHECKOPT);
                command.removeOption(IPMsgProtocol.IPMSG_READCHECKOPT);
                break;
            case CHECK_SEND:
                command.addOption(IPMsgProtocol.IPMSG_SENDCHECKOPT);
                break;
            case CHECK_READ:
                command.addOption(IPMsgProtocol.IPMSG_READCHECKOPT);
                break;
            default:
                throw new IllegalArgumentException(StringUtil.format(
                        "Message.CheckMode setting failure.(actual:%s)", Integer.toHexString(checkMode)));
        }
        this.checkMode = checkMode;
    }
    
    public int getCheckMode() {
        return checkMode;
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
    
    public String getBody() {
        MessageBodyExtension extension = getExtension();
        return extension == null ? null : extension.toExtensionString();
    }
    
    public void setBody(String body) {
        MessageBodyExtension extension = getExtension();
        if (extension == null) {
            setExtension(new MessageBodyExtension(body));
        } else {
            extension.setExtString(body);
        }
    }
    
}
