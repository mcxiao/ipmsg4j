package com.github.mcxiao.ipmsg.packet.extension;

import com.github.mcxiao.ipmsg.packet.PacketExtension;
import com.github.mcxiao.ipmsg.util.ExtensionStringBuilder;
import com.github.mcxiao.ipmsg.util.StringUtil;

/**
 *
 */

public class AbsenceExtension extends PacketExtension {
    
    private String userName;
    
    private String hostName;
    
    private String nickName;
    
    private String absenceStatus;
    
    private String groupName;
    
    public AbsenceExtension() {
    }
    
    public AbsenceExtension(String userName, String hostName, String nickName, String absenceStatus, String groupName) {
        this.userName = userName;
        this.hostName = hostName;
        this.nickName = nickName;
        this.absenceStatus = absenceStatus;
        this.groupName = groupName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    public void setAbsenceStatus(String absenceStatus) {
        this.absenceStatus = absenceStatus;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public String getHostName() {
        return hostName;
    }
    
    public String getNickName() {
        return nickName;
    }
    
    public String getAbsenceStatus() {
        return absenceStatus;
    }
    
    public String getGroupName() {
        return groupName;
    }
    
    @Override
    public String toString() {
        return toExtensionString();
    }
    
    @Override
    public String toExtensionString() {
        String encodedNickName = encodeEscapeDelimiter(nickName);
        String encodedAbsenceStatus = encodeEscapeDelimiter(absenceStatus);
        String encodedUserName = encodeEscapeDelimiter(userName);
        String encodedHostName = encodeEscapeDelimiter(hostName);
        String encodedGroupName = encodeEscapeDelimiter(groupName);
    
        ExtensionStringBuilder builder = new ExtensionStringBuilder();
        builder.appendIfAble(encodedNickName);
        builder.formatAppendIfAble("[%s]", encodedAbsenceStatus);
        builder.appendIfAble(encodedGroupName);
        builder.append("\0\n");
        builder.formatAppendIfAble("UN:%s\n", encodedUserName);
        builder.formatAppendIfAble("HN:%s\n", encodedHostName);
        if (!StringUtil.isNullOrEmpty(encodedNickName)) {
            builder.formatAppendIfAble("NN:%s", encodedNickName);
            builder.formatAppendIfAble("[%s]\n", encodedAbsenceStatus);
        }
        builder.formatAppendIfAble("GN:%s\n", encodedGroupName);
    
        builder.appendTheEND();
        return builder.toString();
    }
}
