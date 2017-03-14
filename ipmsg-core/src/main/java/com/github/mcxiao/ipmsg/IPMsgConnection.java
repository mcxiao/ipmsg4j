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

package com.github.mcxiao.ipmsg;

import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.packet.HostSub;
import com.github.mcxiao.ipmsg.packet.Packet;

/**
 */
public interface IPMsgConnection {

    String getSenderHost();

    String getSenderName();

    HostSub getHostSub();

    Address getLocalhostAddress();

    int getPort();

    void sendPacket(Packet packet) throws InterruptedException, IPMsgException;

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

    boolean isConnected();

    long getPacketReplyTimeout();

    boolean isSupportUtf8();

    boolean isSupportFileAttach();

}
