package com.github.mcxiao.ipmsg.chat;

import com.github.mcxiao.ipmsg.IPMsgException.ClientUnavailableException;
import com.github.mcxiao.ipmsg.IPMsgException.NotConnectedException;
import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.packet.Message;
import com.github.mcxiao.ipmsg.util.StringUtil;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *
 */

public class Chat {
    
    private ChatManager chatManager;
    private Address participant;
    
    private final Set<ChatMessageListener> listeners = new CopyOnWriteArraySet<>();
    
    Chat(ChatManager chatManager, Address participant) {
        Objects.requireNonNull(chatManager);
        Objects.requireNonNull(participant);
        
        this.chatManager = chatManager;
        this.participant = participant;
    }
    
    public ChatManager getChatManager() {
        return chatManager;
    }
    
    public Address getParticipant() {
        return participant;
    }
    
    public void sendMessage(Message message) throws NotConnectedException, InterruptedException, ClientUnavailableException {
        message.setTo(participant);
        message.setType(Message.TYPE_SEND_MSG);
        chatManager.sendMessage(Chat.this, message);
    }
    
    public void addMessageListener(ChatMessageListener listener) {
        if (listener == null)
            return;
        
        listeners.add(listener);
    }
    
    public void removeMessageListener(ChatMessageListener listener) {
        listeners.remove(listener);
    }
    
    public Set<ChatMessageListener> getListeners() {
        return Collections.unmodifiableSet(listeners);
    }
    
    public void close() {
        chatManager.closeChat(Chat.this);
        listeners.clear();
    }
    
    void deliver(Message message) {
        for (ChatMessageListener listener : listeners) {
            listener.processMessage(Chat.this, message);
        }
    }
    
    @Override
    public String toString() {
        return StringUtil.format("Chat[(participant=%s)", participant.getAddress());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Chat) {
            Chat chat = (Chat) obj;
            return chat.getParticipant() != null
                    && chat.getParticipant().equals(this.participant);
        }
        
        return false;
    }
    
    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + participant.hashCode();
        return hash;
    }
}
