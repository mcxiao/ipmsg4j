/*
 * Copyright [2016] [MC.Xiao]
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

package com.github.mcxiao;

import com.github.mcxiao.IPMsgException.NoResponseException;
import com.github.mcxiao.packet.Packet;

/**
 */
public interface IPMsgConnection {

    String getHostName();

    String getUser();

    int getPort();

    void sendPacket(Packet packet) throws NoResponseException, InterruptedException, IPMsgException.ClientUnavailableException;

    void addPacketListener(PacketListener listener);

    void removePacketListener(PacketListener listener);

    void addPacketInterceptor(PacketInterceptor interceptor);

    void removePacketInterceptor(PacketInterceptor interceptor);

}
