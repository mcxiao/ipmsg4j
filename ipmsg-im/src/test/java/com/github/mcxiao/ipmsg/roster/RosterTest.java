package com.github.mcxiao.ipmsg.roster;

import com.github.mcxiao.ipmsg.DummyConnection;

import org.junit.Before;

/**
 */
public class RosterTest {

    private DummyConnection connection;
    private Roster roster;
    
    @Before
    public void setup() throws Exception {
        connection = new DummyConnection();
        connection.connect();
    }
    
}
