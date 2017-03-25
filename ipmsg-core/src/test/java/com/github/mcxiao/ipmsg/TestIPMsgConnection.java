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

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 */
public class TestIPMsgConnection {

    boolean done = false;
    final Object mutex = new Object();

    @Test
    public void testSessionCreate() throws UnknownHostException, IPMsgException, InterruptedException {
        IPMsgConfiguration configuration = new IPMsgConfiguration.Builder()
                .setLocalHost(InetAddress.getLocalHost().getHostAddress()).build();
        IPMsgConnectionImpl session = new IPMsgConnectionImpl(configuration);
        session.addConnectionListener(new AbstractConnectionListener() {
            @Override
            public void connected(IPMsgConnection connection) {
                super.connected(connection);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3 * 1000);
                            done = true;
                            synchronized (mutex) {
                                mutex.notifyAll();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        session.connect();

        while (!done) {
            synchronized (mutex) {
                mutex.wait();
            }
        }

    }

}
