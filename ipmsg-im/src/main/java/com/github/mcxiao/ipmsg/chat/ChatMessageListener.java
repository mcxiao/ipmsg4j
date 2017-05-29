package com.github.mcxiao.ipmsg.chat;

import com.github.mcxiao.ipmsg.packet.Message;

/**
 *
 */

public interface ChatMessageListener {
    
    void processMessage(Chat chat, Message message);
    
}
