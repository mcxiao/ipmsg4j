package com.github.mcxiao.util;

import java.util.concurrent.ThreadFactory;

/**
 */
public class IPMsgThreadFactory implements ThreadFactory {

    private final String name;

    public IPMsgThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("IPMsg-" + name);
        thread.setDaemon(true);
        return thread;
    }
}
