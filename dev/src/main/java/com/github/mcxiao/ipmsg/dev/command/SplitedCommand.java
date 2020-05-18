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
    
    public String toReadableBinary() {
        String string = toBinaryString();
        int length = string.length();
        int offset = length % 4;
        
        StringBuilder builder = new StringBuilder();
        if (offset != 0) {
            for (int i = 0; i < (4 - offset); i++) {
                builder.append("0");
            }
            
            builder.append(string.substring(0, offset));
            builder.append(" ");
        }
        
        while (offset < length) {
            String substring = string.substring(offset, offset + 4);
            builder.append(substring);
            builder.append(" ");
            offset += 4;
        }
        
        return builder.toString();
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
        builder.append(toReadableBinary());
        builder.append("\nMode:\n");
        builder.append(appliedMode());
        builder.append("\nOptions:\n");
        builder.append(appliedOpt());
        
        return builder.toString();
    }
}
