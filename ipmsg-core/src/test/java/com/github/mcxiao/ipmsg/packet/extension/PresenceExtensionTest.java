package com.github.mcxiao.ipmsg.packet.extension;

import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.packet.Command;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */

public class PresenceExtensionTest {
    
    @Test
    public void testAbsenceExtension() throws Exception {
        Command command = new Command(IPMsgProtocol.IPMSG_ABSENCEOPT);
    
        AbsenceExtension extension = new AbsenceExtension();
        extension.setAbsenceStatus("busy");
        extension.setGroupName("group");
        extension.setHostName("hostName");
        extension.setNickName("nickName");
        extension.setUserName("userName");
    
        String string = extension.toExtensionString();
        System.out.println(string);
    
        PresenceExtensionProvider presenceExtensionProvider = new PresenceExtensionProvider();
        AbsenceExtension parse = presenceExtensionProvider.parse(command, string);
    
        Assert.assertEquals(extension.getAbsenceStatus(), parse.getAbsenceStatus());
    }
    
}
