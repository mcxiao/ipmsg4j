package com.github.mcxiao.ipmsg.sample;

import com.github.mcxiao.ipmsg.AbstractConnection;
import com.github.mcxiao.ipmsg.AbstractConnectionListener;
import com.github.mcxiao.ipmsg.IPMsgConfiguration;
import com.github.mcxiao.ipmsg.IPMsgConnection;
import com.github.mcxiao.ipmsg.IPMsgConnectionImpl;
import com.github.mcxiao.ipmsg.IPMsgException;
import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.roster.Roster;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    
    static final Object MUTEX = new Object();
    static boolean done = true;
    
    public static void main(String[] args) throws UnknownHostException, InterruptedException, IPMsgException {
        done = false;
        
        InetAddress localHost = InetAddress.getLocalHost();
        IPMsgConfiguration config = IPMsgConfiguration.create()
                .setLocalHost(localHost.getHostAddress());
    
        AbstractConnection connection = new IPMsgConnectionImpl(config);
        connection.addConnectionListener(new AbstractConnectionListener(){
            @Override
            public void connected(IPMsgConnection connection) {
                System.out.println("Connected!");
            }
        });
    
        Roster roster = Roster.getInstanceFor(connection);
        roster.addRosterListener(new AbstractRosterListener(){
            @Override
            public void addEntry(Address address) {
                System.out.printf("Somebody online -> %s \n", address.getAddress());
            }
        });
        
        connection.connect();
        
        waitWhenNotDone();
    }
    
    static void waitWhenNotDone() throws InterruptedException {
        while (!done) {
            synchronized (MUTEX) {
                MUTEX.wait();
            }
        }
    }
    
    static void done() {
        done = false;
        MUTEX.notify();
    }
    
}
