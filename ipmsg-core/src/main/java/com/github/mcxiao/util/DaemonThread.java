package com.github.mcxiao.util;

/**
 */
public class DaemonThread extends Thread {

    public DaemonThread() {
        super();
        setDaemon(true);
    }

    public DaemonThread(String name) {
        super(name);
        setDaemon(true);
    }

    public DaemonThread(Runnable target) {
        super(target);
        setDaemon(true);
    }

    public DaemonThread(Runnable target, String name) {
        super(target, name);
        setDaemon(true);
    }

}
