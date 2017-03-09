package com.github.mcxiao;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 */
public class TestIPMsgConnection {


    @Test
    public void testSessionCreate() throws UnknownHostException, IPMsgException.ConnectException, IPMsgException.ClientUnavailableException, InterruptedException, IPMsgException.NoResponseException {
        IPMsgConfiguration configuration = IPMsgConfiguration.create()
                .setLocalHost(InetAddress.getLocalHost().getHostAddress());
        IPMsgTCPConnection session = new IPMsgTCPConnection(configuration);
        session.connect();
    }

}
