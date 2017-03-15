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

package com.github.mcxiao.ipmsg.util;

import com.github.mcxiao.ipmsg.IPMsgException;
import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.packet.Command;
import com.github.mcxiao.ipmsg.packet.HostSub;
import com.github.mcxiao.ipmsg.packet.Packet;

import java.util.Arrays;

/**
 */
public final class PacketParseUtil {
    private final static String TAG = LogUtil.createTag(PacketParseUtil.class.getSimpleName(), null);

    public final static String FORMATTER = "%s:%s:%s:%s:%d:";

    public static Packet parsePacket(String from, int port, byte[] data) throws Exception {
        // XXX Need a performance update
        String version = null;
        String packetNo = null;
        String senderName = null;
        String senderHost = null;
        int command = -1;
        byte[] ext = null;

        byte[] pattern = new byte[]{IPMsgProtocol.SEPARATOR_BYTE};
        int effect = 0;

        for (int offset = 0; offset < data.length; ) {
            byte[] bytes = ByteArrayUtil.splitOrderByFirst(pattern, data, offset);
            if (bytes != null) {
                try {
                    switch (effect) {
                        case 0:
                            version = new String(bytes);
                            break;
                        case 1:
                            packetNo = new String(bytes);
                            break;
                        case 2:
                            senderName = new String(bytes);
                            break;
                        case 3:
                            senderHost = new String(bytes);
                            break;
                        case 4:
                            command = Integer.valueOf(new String(bytes));
                            break;
                    }

                } catch (Exception e) {
                    LogUtil.warn(TAG, "Parse packet failure.", e);
                    throw new IPMsgException.IllegalPacketFormatException("Packet format error.");
                }
                offset = offset + bytes.length + pattern.length;
                effect++;
            } else if (effect >= 4) {
                ext = Arrays.copyOfRange(data, offset, data.length);
                break;
            } else {
                LogUtil.warn(TAG, "Packet params not enough.", null);
                throw new IPMsgException.IllegalPacketFormatException("Packet params not enough.");
            }
        }

        Packet packet = new Packet(version, packetNo,
                new Command(command),
                new HostSub(senderName, senderHost),
                ext,
                System.currentTimeMillis());

        packet.setFrom(new Address(from, port));
        return packet;
    }

}
