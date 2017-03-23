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

import org.junit.Assert;
import org.junit.Test;

/**
 */
public class CommandTest {

    private int function    = IPMsgProtocol.IPMSG_BR_ENTRY;
    private int functionB   = IPMsgProtocol.IPMSG_BR_ABSENCE;
    private int option      = IPMsgProtocol.IPMSG_UTF8OPT;
    private int optionB     = IPMsgProtocol.IPMSG_ABSENCEOPT;

    private int code        = function | option;
    private int codeB       = functionB | optionB;

    @Test
    public void testCommand() {
        Command command = new Command();
        command.addOption(option);
        command.setMode(function);
        Assert.assertEquals(code, command.getCommand());

        command.removeOption(option);
        command.addOption(optionB);
        command.setMode(functionB);
        Assert.assertEquals(codeB, command.getCommand());
     
        command = new Command(function);
        command.addOrRemoveOpt(false, option);
        Assert.assertEquals(function, command.getCommand());
        command.addOrRemoveOpt(true, option);
        Assert.assertEquals(code, command.getCommand());
        command.addOrRemoveOpt(false, option);
        Assert.assertEquals(function, command.getCommand());
    }

}
