package com.github.mcxiao.ipmsg.provider;

import com.github.mcxiao.ipmsg.packet.Command;
import com.github.mcxiao.ipmsg.packet.ExtensionElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */

public final class ProviderManager {
    
    private static final Map<Command, ExtensionElementProvider<? extends ExtensionElement>> extensionProviders = new ConcurrentHashMap<>();
    
    public static ExtensionElementProvider<? extends ExtensionElement> getExtensionProvider(Command command) {
        return extensionProviders.get(command);
    }
    
    public static <EE extends ExtensionElement> void addExtensionProvider(Command command, ExtensionElementProvider<EE> provider) {
        Command key = removeExtensionProvider(command);
        extensionProviders.put(key, provider);
    }
    
    public static Command removeExtensionProvider(Command command) {
        extensionProviders.remove(command);
        return command;
    }
    
    public static List<ExtensionElementProvider<? extends ExtensionElement>> getExtensionProviders() {
        List<ExtensionElementProvider<? extends ExtensionElement>> providers = new ArrayList<>(extensionProviders.size());
        providers.addAll(extensionProviders.values());
        return providers;
    }
    
}
