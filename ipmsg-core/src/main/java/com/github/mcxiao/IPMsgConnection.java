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

    String getSenderHost();

    String getSenderName();

    String getLocalhostAddress();

    int getPort();

    void sendPacket(Packet packet) throws NoResponseException, InterruptedException, IPMsgException.ClientUnavailableException;

    void addPacketListener(PacketListener listener, PacketFilter filter);

    boolean removePacketListener(PacketListener listener);
    
    void addAsyncPacketListener(PacketListener listener, PacketFilter filter);
    
    boolean removeAsyncPacketListener(PacketListener listener);
    
    void addSyncPacketListener(PacketListener listener, PacketFilter filter);
    
    boolean removeSyncPacketListener(PacketListener listener);
    
    void addPacketSendingListener(PacketListener listener, PacketFilter filter);
    
    void removePacketSendingListener(PacketListener listener);
    
    void addPacketInterceptor(PacketListener interceptor, PacketFilter filter);

    boolean removePacketInterceptor(PacketListener interceptor);

    void addConnectionListener(ConnectionListener listener);

    void removeConnectionListener(ConnectionListener listener);

    long getPacketReplyTimeout();

}
