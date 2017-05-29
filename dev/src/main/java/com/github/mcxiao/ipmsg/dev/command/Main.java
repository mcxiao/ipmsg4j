package com.github.mcxiao.ipmsg.dev.command;

/**
 *
 */

public class Main {
    
    public static void main(String[] args) {
        SplitedCommand splitedCommand = new SplitedCommand(0x1900320);
        System.out.println(splitedCommand.toString());
    }
    
}
