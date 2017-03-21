package com.github.mcxiao.ipmsg.filter;

import com.github.mcxiao.ipmsg.packet.Command;

/**
 *
 */

public class CommandGreaterFilter extends AbstractPacketCommandFilter {
    
    private final int mode;
    private final boolean acceptEqual;
    
    /**
     * @param mode The Command.mode used to compare with Packet.Command.Mode
     *             by greater(>) or greater equal(>=) condition.
     * @param acceptEqual If true then comparison use greater-equal(>=) condition.
     */
    public CommandGreaterFilter(int mode, boolean acceptEqual) {
        this.mode = mode;
        this.acceptEqual = acceptEqual;
    }
    
    @Override
    protected boolean accept(Command command) {
        return acceptEqual ? mode >= command.getMode() : mode > command.getMode();
    }
}
