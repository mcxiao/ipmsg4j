package com.github.mcxiao.ipmsg.roster;

import com.github.mcxiao.ipmsg.DummyConnection;
import com.github.mcxiao.ipmsg.IPMsgException;
import com.github.mcxiao.ipmsg.address.Address;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class RosterTest {

    private DummyConnection connection;
    private Roster roster;
    
    private RosterListener rosterListener = new RosterListener() {
        @Override
        public void addEntry(Address address) {
        
        }
    
        @Override
        public void updateEntry(Address address) {
        
        }
    
        @Override
        public void deletedEntry(Address address) {
        
        }
    };
    
    @Before
    public void setup() throws Exception {
        connection = new DummyConnection();
        connection.connect();
        
        roster = Roster.getInstanceFor(connection);
        roster.addRosterListener(rosterListener);
    }
    
    @After
    public void end() {
        if (connection != null) {
            if (rosterListener != null) {
                roster.removeRosterListener(rosterListener);
            }
            connection.disconnect();
            connection = null;
        }
    }
    
    @Test
    public void testRosterInitialize() throws Exception {
        Assert.assertNotNull("Roster must initialize complete!", roster);
        Assert.assertFalse("Roster shouldn't be already loaded!", roster.isLoaded());
        
        initRoster();
    }
    
    private void initRoster() throws InterruptedException, IPMsgException {
        roster.reload();
        
        while (true) {
            
        }
    }
    
    
}
