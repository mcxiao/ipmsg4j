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

import com.github.mcxiao.IPMsgException.ClientUnavailableException;
import com.github.mcxiao.IPMsgException.NoResponseException;
import com.github.mcxiao.packet.Packet;
import com.github.mcxiao.util.IPMsgThreadFactory;
import com.github.mcxiao.util.LogUtil;
import com.github.mcxiao.util.PacketParseUtil;
import org.jivesoftware.smack.util.BoundedThreadPoolExecutor;

import java.util.concurrent.TimeUnit;

/**
 */
public abstract class AbstractConnection implements IPMsgConnection {

    private final static String TAG = LogUtil.createTag(AbstractConnection.class.getSimpleName(), null);
    private final IPMsgConfiguration config;

    private final BoundedThreadPoolExecutor executorService =
            new BoundedThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
            100, new IPMsgThreadFactory("Incoming Processor"));

    protected AbstractConnection(IPMsgConfiguration configuration) {
        this.config = configuration;
    }

    @Override
    public String getHostName() {
        return null;
    }

    @Override
    public String getUser() {
        return null;
    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public void sendPacket(Packet packet)
            throws NoResponseException, ClientUnavailableException, InterruptedException {
        sendInternal(packet);
    }

    protected abstract void sendInternal(Packet packet)
            throws InterruptedException, ClientUnavailableException;

    protected void parseAndProcessPacket(String address, int port, byte[] msgBuf) throws Exception {
        Packet packet = null;
        try {
            packet = PacketParseUtil.parsePacket(address, port, msgBuf);
        } catch (Exception e) {
            // TODO: 2017/3/6 Handle unparsable packet
            LogUtil.warn(TAG, "Unparsable packet, ignored.", e);
        }
        if (packet != null) {
            processPacket(packet);
        }
    }

    protected void processPacket(final Packet packet) throws InterruptedException {
        assert (packet != null);

        executorService.executeBlocking(new Runnable() {
            @Override
            public void run() {
                notifyRecvListeners(packet);
            }
        });
    }

    private void notifyRecvListeners(Packet packet) {
        // TODO: 2017/3/6 Impl method.
    }

    public void addPacketListener(PacketListener listener) {

    }

    public void removePacketListener(PacketListener listener) {

    }

    public void addPacketInterceptor(PacketInterceptor interceptor) {

    }

    public void removePacketInterceptor(PacketInterceptor interceptor) {

    }

    protected static class ListenerWrapper {
    }

    protected static class InterceptorWrapper {
    }
}
