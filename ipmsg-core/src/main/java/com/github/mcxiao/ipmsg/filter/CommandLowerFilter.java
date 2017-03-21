package com.github.mcxiao.ipmsg.filter;

import com.github.mcxiao.ipmsg.packet.Command;

/**
 *
 */

public class CommandLowerFilter extends AbstractPacketCommandFilter {
    
    private final int mode;
    private final boolean acceptEqual;
    
    /**
     * @param mode The Command.mode used to compare with Packet.Command.Mode
     *             by lower(<) or lower equal(<=) condition.
     * @param acceptEqual If true then comparison use lower-equal(<=) condition.
     */
    public CommandLowerFilter(int mode, boolean acceptEqual) {
        this.mode = mode;
        this.acceptEqual = acceptEqual;
    }
    
    @Override
    protected boolean accept(Command command) {
        return acceptEqual ? mode <= command.getMode() : mode < command.getMode();
    }
}
