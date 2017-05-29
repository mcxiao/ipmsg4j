package com.github.mcxiao.ipmsg.sample;

import com.github.mcxiao.ipmsg.AbstractConnection;
import com.github.mcxiao.ipmsg.AbstractConnectionListener;
import com.github.mcxiao.ipmsg.IPMsgConfiguration;
import com.github.mcxiao.ipmsg.IPMsgConnection;
import com.github.mcxiao.ipmsg.IPMsgConnectionImpl;
import com.github.mcxiao.ipmsg.IPMsgException;
import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.chat.Chat;
import com.github.mcxiao.ipmsg.chat.ChatManager;
import com.github.mcxiao.ipmsg.chat.ChatMessageListener;
import com.github.mcxiao.ipmsg.packet.Message;
import com.github.mcxiao.ipmsg.roster.Roster;
import com.github.mcxiao.ipmsg.roster.RosterEntry;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CreateConnection {
    
    static final Object MUTEX = new Object();
    static boolean done = true;
    
    public static void main(String[] args) throws UnknownHostException, InterruptedException, IPMsgException {
        done = false;
        
        InetAddress localHost = InetAddress.getLocalHost();
        IPMsgConfiguration config = new IPMsgConfiguration.Builder()
                .setLocalHost(localHost.getHostAddress())
                .setNickName("Frank").build();
    
        AbstractConnection connection = new IPMsgConnectionImpl(config);
        connection.addConnectionListener(new AbstractConnectionListener(){
            @Override
            public void connected(IPMsgConnection connection) {
                System.out.println("Connected!");
            }
    
            @Override
            public void connectionClosed() {
                System.out.println("Connection closed.");
            }
    
            @Override
            public void connectionClosedOnError(Exception e) {
                e.printStackTrace();
            }
        });
    
        // Initialize ChatManager to manage chat session and we will use it later.
        ChatManager chatManager = ChatManager.getInstanceFor(connection);
        // Initialize Roster to manage user contacts.
        Roster roster = Roster.getInstanceFor(connection);
        roster.addRosterListener(new AbstractRosterListener(){
            @Override
            public void addEntry(Address address) {
                // Get RosterEntry to use more detail information.
                RosterEntry entry = roster.getEntry(address);
                System.out.printf("Somebody online -> %s:%s \n", entry.getName(), entry.getAddress().toString());
    
                // Create the chat session proxy.
                Chat chat = chatManager.createChat(address);
                chat.addMessageListener(new ChatMessageListener() {
                    @Override
                    public void processMessage(Chat chat, Message message) {
                        // Received a message.
                        System.out.printf("Received message -> %s: %s\n", chat.getParticipant(), message.getBody());
                    }
                });
                
                try {
                    // Send message by Chat.
                    chat.sendMessage(new Message(Message.TYPE_SEND_MSG, "Hi!"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        // Create connection.
        connection.connect();
        
        waitWhenNotDone();
        
        connection.disconnect();
    }
    
    static void waitWhenNotDone() throws InterruptedException {
        while (!done) {
            synchronized (MUTEX) {
                MUTEX.wait();
            }
        }
    }
    
    static void done() {
        done = true;
        synchronized (MUTEX) {
            MUTEX.notify();
        }
    }
    
}
