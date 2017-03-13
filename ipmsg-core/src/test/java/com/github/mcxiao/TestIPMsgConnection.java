package com.github.mcxiao;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 */
public class TestIPMsgConnection {

    @Test
    public void testSessionCreate() throws UnknownHostException, IPMsgException, InterruptedException {
        IPMsgConfiguration configuration = IPMsgConfiguration.create()
                .setLocalHost(InetAddress.getLocalHost().getHostAddress());
        IPMsgConnectionImpl session = new IPMsgConnectionImpl(configuration);
        session.connect();
    }

}
