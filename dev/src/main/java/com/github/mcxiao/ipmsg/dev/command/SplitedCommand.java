package com.github.mcxiao.ipmsg.dev.command;

import com.github.mcxiao.ipmsg.packet.Command;

import static com.github.mcxiao.ipmsg.dev.command.IpmsgCommandMetaMap.metaMap;

/**
 *
 */

public class SplitedCommand extends Command {
    
    public SplitedCommand() {
    }
    
    public SplitedCommand(int command) {
        super(command);
    }
    
    public String toBinaryString() {
        return Integer.toBinaryString(getCommand());
    }
    
    public String appliedOpt() {
        StringBuilder builder = new StringBuilder();
        
        for (Integer integer : metaMap.keySet()) {
            if (acceptOpt(integer)) {
                builder.append(Integer.toHexString(integer));
                builder.append(" -> ");
                builder.append(metaMap.get(integer).getName());
                builder.append("\n");
            }
        }
        
        return builder.toString();
    }
    
    public String appliedMode() {
        StringBuilder builder = new StringBuilder();
        
        for (Integer integer : metaMap.keySet()) {
            if (acceptMode(integer)) {
                builder.append(Integer.toHexString(integer));
                builder.append(" -> ");
                builder.append(metaMap.get(integer).getName());
                builder.append("\n");
                break;
            }
        }
        
        return builder.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(toBinaryString());
        builder.append("\nMode:\n");
        builder.append(appliedMode());
        builder.append("\nOptions:\n");
        builder.append(appliedOpt());
        
        return builder.toString();
    }
}
