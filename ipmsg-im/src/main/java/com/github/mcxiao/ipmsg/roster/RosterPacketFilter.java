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

package com.github.mcxiao.ipmsg.roster;

import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.filter.PacketCommandFilter;

/**
 */
public class RosterPacketFilter extends PacketCommandFilter {

    @Override
    protected int[] getCommands() {
        int[] commands = new int[4];
        commands[0] = IPMsgProtocol.IPMSG_BR_ENTRY;
        commands[1] = IPMsgProtocol.IPMSG_BR_EXIT;
        commands[2] = IPMsgProtocol.IPMSG_BR_ABSENCE;
        commands[3] = IPMsgProtocol.IPMSG_ANSENTRY;
        return commands;
    }
}
