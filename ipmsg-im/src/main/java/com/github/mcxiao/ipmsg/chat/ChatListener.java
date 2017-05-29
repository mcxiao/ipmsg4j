package com.github.mcxiao.ipmsg.chat;

/**
 *
 */

public interface ChatListener {
    
    void onChatCreate(Chat chat, boolean createdLocally);
    
}
