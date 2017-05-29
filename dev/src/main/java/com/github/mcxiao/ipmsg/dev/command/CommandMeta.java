package com.github.mcxiao.ipmsg.dev.command;

/**
 *
 */

public class CommandMeta {
    
    private final String name;
    
    private final int command;
    
    public CommandMeta(String name, int command) {
        this.name = name;
        this.command = command;
    }
    
    public String getName() {
        return name;
    }
    
    public int getCommand() {
        return command;
    }
}
