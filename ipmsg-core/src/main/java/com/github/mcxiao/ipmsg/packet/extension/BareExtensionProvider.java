package com.github.mcxiao.ipmsg.packet.extension;

import com.github.mcxiao.ipmsg.packet.Command;
import com.github.mcxiao.ipmsg.provider.ExtensionElementProvider;

/**
 *
 */

public class BareExtensionProvider extends ExtensionElementProvider<BareExtension> {
    @Override
    public BareExtension parse(Command command, String extString) throws Exception {
        return new BareExtension(extString);
    }
    
}
