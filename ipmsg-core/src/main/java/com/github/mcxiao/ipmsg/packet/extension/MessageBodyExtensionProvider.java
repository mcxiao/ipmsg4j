package com.github.mcxiao.ipmsg.packet.extension;

import com.github.mcxiao.ipmsg.packet.Command;

/**
 *
 */

public class MessageBodyExtensionProvider extends OriginExtensionProvider<MessageBodyExtension> {
    @Override
    public MessageBodyExtension parse(Command command, String extString) throws Exception {
        // Return a extString with trim.
        return new MessageBodyExtension(extString.trim());
    }
}
