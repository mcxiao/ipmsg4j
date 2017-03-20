package com.github.mcxiao.ipmsg;/*
 * Copyright [2017] [xiao]
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

import com.github.mcxiao.ipmsg.AbstractConnection;
import com.github.mcxiao.ipmsg.IPMsgConfiguration;
import com.github.mcxiao.ipmsg.IPMsgException;
import com.github.mcxiao.ipmsg.packet.Element;
import com.github.mcxiao.ipmsg.packet.Packet;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 */
public class DummyConnection extends AbstractConnection {

    private final BlockingQueue<Element> queue = new LinkedBlockingQueue<>();

    public static IPMsgConfiguration buildConfiguration() {
        return IPMsgConfiguration.create()
                .setSenderHost("DummyHost")
                .setSenderName("DummyName")
                .setSupportFileAttach(true)
                .setSupportUtf8(true);
    }

    public DummyConnection() {
        this(buildConfiguration());
    }

    public DummyConnection(IPMsgConfiguration configuration) {
        super(configuration);
    }

    @Override
    protected void connectInternal() throws InterruptedException, IPMsgException {
        connected = true;
    }

    @Override
    protected void shutdown() {
        connected = false;
    }

    @Override
    protected void sendInternal(Packet packet) throws IPMsgException.NotConnectedException, IPMsgException.ClientUnavailableException, InterruptedException {
        queue.add(packet);
    }

    public void processPacket(Packet packet) {
        try {
            super.processPacket(packet);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public <P extends Element> P getSentPacket() {
        return getSentPacket( 5 * 60);
    }

    @SuppressWarnings("unchecked")
    public <P extends Element> P getSentPacket(int wait) {
        try {
            return (P) queue.poll(wait, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}
