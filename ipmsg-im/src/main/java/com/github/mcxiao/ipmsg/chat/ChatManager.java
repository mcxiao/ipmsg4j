package com.github.mcxiao.ipmsg.chat;

import com.github.mcxiao.ipmsg.AbstractConnection;
import com.github.mcxiao.ipmsg.IPMsgConnection;
import com.github.mcxiao.ipmsg.IPMsgException.ClientUnavailableException;
import com.github.mcxiao.ipmsg.IPMsgException.NotConnectedException;
import com.github.mcxiao.ipmsg.Manager;
import com.github.mcxiao.ipmsg.PacketFilter;
import com.github.mcxiao.ipmsg.PacketListener;
import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.filter.PacketTypeFilter;
import com.github.mcxiao.ipmsg.packet.Message;
import com.github.mcxiao.ipmsg.packet.Packet;
import com.github.mcxiao.ipmsg.util.LogUtil;

import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * TODO Implement Message interceptors.
 */
public final class ChatManager extends Manager {
    
    private final static String TAG = LogUtil.createTag(ChatManager.class.getSimpleName(), null);
    
    private final static Map<IPMsgConnection, ChatManager> INSTANCES = new WeakHashMap<>();
    
    public static ChatManager getInstanceFor(AbstractConnection connection) {
        ChatManager chatManager = INSTANCES.get(connection);
        if (chatManager == null) {
            chatManager = new ChatManager(connection);
            INSTANCES.put(connection, chatManager);
        }
        
        return chatManager;
    }
    
    private final PacketFilter packetFilter = new PacketTypeFilter<Message>() {
        @Override
        protected boolean acceptType(Message packet) {
            return packet.getType() == Message.TYPE_SEND_MSG
                    && packet.getCheckMode() == Message.CHECK_NONE;
        }
    };
    
    /**
     * Maps Address to chat.
     */
    private Map<Address, Chat> addressChats = new ConcurrentHashMap<>();
    
    private Set<ChatListener> chatListeners = new CopyOnWriteArraySet<>();
    
    private ChatManager(AbstractConnection connection) {
        super(connection);
        connection.addSyncPacketListener(new PacketListener() {
            @Override
            public void processPacket(Packet packet) {
                Message message = (Message) packet;
                Chat chat = getAddressChat(message.getFrom());
                if (chat == null) {
                    chat = createChat(message);
                }
                
                if (chat == null) {
                    // Lose necessary information.
                    return;
                }
                
                deliverMessage(chat, message);
            }
        }, packetFilter);
        INSTANCES.put(connection, ChatManager.this);
    }
    
    private void deliverMessage(Chat chat, Message message) {
        chat.deliver(message);
    }
    
    private Chat createChat(Message message) {
        Address from = message.getFrom();
        
        return createChat(from, false);
    }
    
    public Chat createChat(Address from) {
        return createChat(from, true);
    }
    
    private Chat createChat(Address from, boolean createdLocally) {
        if (from == null) {
            return null;
        }
        Chat chat = addressChats.get(from);
        if (chat == null) {
            chat = new Chat(ChatManager.this, from);
            addressChats.put(from, chat);
            
            LogUtil.infoWithParams(TAG, "Create new Chat. From: %s - createdLocally: %b", from.toString(), createdLocally);
        }
        
        fireChatListener(chat, createdLocally);
        
        return chat;
    }
    
    private Chat getAddressChat(Address from) {
        if (from == null) {
            return null;
        }
    
        return addressChats.get(from);
    }
    
    void sendMessage(Chat chat, Message message) throws NotConnectedException, InterruptedException, ClientUnavailableException {
        // TODO: 2017/3/28 Intercept outgoing messages here.
        connection().sendPacket(message);
    }
    
    public void closeChat(Chat chat) {
        Address participant = chat.getParticipant();
        addressChats.remove(participant);
    }
    
    public void addChatListener(ChatListener listener) {
        if (listener != null) {
            chatListeners.add(listener);
        }
    }
    
    public void removeChatListener(ChatListener listener) {
        if (listener != null) {
            chatListeners.remove(listener);
        }
    }
    
    private void fireChatListener(Chat chat, boolean createdLocally) {
        for (ChatListener listener : chatListeners) {
            listener.onChatCreate(chat, createdLocally);
        }
    }
}
