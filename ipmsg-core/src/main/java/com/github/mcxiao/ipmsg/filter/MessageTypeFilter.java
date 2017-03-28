package com.github.mcxiao.ipmsg.filter;

import com.github.mcxiao.ipmsg.packet.Message;

/**
 *
 */

public class MessageTypeFilter extends PacketTypeFilter<Message> {
    
    public final static MessageTypeFilter SEND = new MessageTypeFilter(Message.TYPE_SEND_MSG);
    public final static MessageTypeFilter RECV = new MessageTypeFilter(Message.TYPE_RECV_MSG);
    
    private final int messageType;
    
    public MessageTypeFilter(int messageType) {
        super(Message.class);
        
        this.messageType = messageType;
    }
    @Override
    protected boolean acceptType(Message packet) {
        return packet.getType() == messageType;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + ": type=" + messageType;
    }
}
