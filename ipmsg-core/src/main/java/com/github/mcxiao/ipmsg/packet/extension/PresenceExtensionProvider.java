package com.github.mcxiao.ipmsg.packet.extension;

import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.packet.Command;
import com.github.mcxiao.ipmsg.provider.ExtensionElementProvider;
import com.github.mcxiao.ipmsg.util.ExtensionParseUtil;
import com.github.mcxiao.ipmsg.util.StringUtil;

/**
 *
 */

public class PresenceExtensionProvider extends ExtensionElementProvider<AbsenceExtension> {
    
    @Override
    public AbsenceExtension parse(Command command, String extString) throws Exception {
        if (command.acceptOpt(IPMsgProtocol.IPMSG_ABSENCEOPT)) {
            AbsenceExtension extension = new AbsenceExtension();
            String[] strings = extString.split("\n");
            for (String string : strings) {
                String[] split = ExtensionParseUtil.splitBySeparator(string);
                if (split.length == 2) {
                    String key = split[0];
                    String value = split[1];
                    switch (key) {
                        case "UN":
                            extension.setUserName(value);
                            break;
                        case "HN":
                            extension.setHostName(value);
                            break;
                        case "NN":
                            if (!StringUtil.isNullOrEmpty(value)) {
                                int start = value.indexOf("[");
                                int end = value.indexOf("]");
                                if (start >= 0) {
                                    extension.setNickName(value.substring(0, start));
                                    if (end > 0) {
                                        extension.setAbsenceStatus(value.substring(start + 1, end));
                                    }
                                } else {
                                    extension.setNickName(value);
                                }
                            }
                            break;
                        case "GN":
                            extension.setGroupName(value);
                            break;
                        default:
                            // Unknown value.
                            break;
                    }
                }
            }
            return extension;
        }
        
        return null;
    }
}
