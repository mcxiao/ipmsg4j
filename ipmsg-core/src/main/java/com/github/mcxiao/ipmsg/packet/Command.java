/*
 * Copyright [2017] [$author]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mcxiao.ipmsg.packet;

import com.github.mcxiao.ipmsg.IPMsgProtocol;

/**
 */
public class Command {

    private int command;

    public Command() {
        this(0);
    }

    public Command(int command) {
        this.command = command;
    }

    public int setMode(int code) {
        code &= 0xff;
        command &= 0xffffff00;
        return command |= code;
    }

    public int addOption(int code) {
        code &= 0xffffff00;
        return command |= code;
    }

    public int removeOption(int code) {
        code &= 0xffffff00;
        return acceptOpt(code) ? command ^= code : command;
    }

    public int getCommand() {
        return command;
    }

    public int getMode() {
        return IPMsgProtocol.GET_MODE(command);
    }

    public int getOpt() {
        return IPMsgProtocol.GET_OPT(command);
    }

    public boolean acceptMode(int mode) {
        return IPMsgProtocol.ACCEPT_MODE(command, mode);
    }

    public boolean acceptOpt(int opt) {
        return IPMsgProtocol.ACCEPT_OPT(command, opt);
    }
    
    public int addOrRemoveOpt(boolean boo, int opt) {
        return boo ? addOption(opt) : removeOption(opt);
    }
    
    @Override
    public String toString() {
        return Integer.toHexString(command);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Command) {
            Command other = (Command) obj;
            return this.command == other.getCommand();
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return command;
    }
}
