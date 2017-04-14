package com.github.mcxiao.ipmsg.packet.extension;

import com.github.mcxiao.ipmsg.packet.Command;
import com.github.mcxiao.ipmsg.provider.ExtensionElementProvider;

/**
 *
 */

public class OriginExtensionProvider extends ExtensionElementProvider<OriginExtension> {
    @Override
    public OriginExtension parse(Command command, String extString) throws Exception {
        return new OriginExtension(extString);
    }
}
