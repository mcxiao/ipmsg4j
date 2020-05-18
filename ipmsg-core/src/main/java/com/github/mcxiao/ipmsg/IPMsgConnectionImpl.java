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

import com.github.mcxiao.ipmsg.IPMsgException.ClientUnavailableException;
import com.github.mcxiao.ipmsg.IPMsgException.NoResponseException;
import com.github.mcxiao.ipmsg.IPMsgException.NotConnectedException;
import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.address.IPAddressCache;
import com.github.mcxiao.ipmsg.packet.Packet;
import com.github.mcxiao.ipmsg.util.LogUtil;
import com.github.mcxiao.ipmsg.util.StringUtil;

import org.jivesoftware.smack.util.ArrayBlockingQueueWithShutdown;
import org.jivesoftware.smack.util.Async;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 */
public class IPMsgConnectionImpl extends AbstractConnection {

    private static final int QUEUE_SIZE = 500;
    private static final String TAG = LogUtil.createTag(IPMsgConnectionImpl.class.getSimpleName(), null);

    private IPMsgConfiguration config;

    private DatagramSocket datagramSocket;

    private DatagramPacketReader datagramReader;
    private DatagramPacketWriter datagramWriter;

    private SimpleSynchronizationPoint<Exception> initialSocketComplete =
            new SimpleSynchronizationPoint<>(IPMsgConnectionImpl.this, "initial socket complete");
    
//    private SimpleSynchronizationPoint<Exception> closeSocketReceived =
//            new SimpleSynchronizationPoint<>(IPMsgConnectionImpl.this, "close socket received");

    public IPMsgConnectionImpl(IPMsgConfiguration configuration) {
        super(configuration);
        this.config = configuration;
    }

    @Override
    protected void connectInternal() throws InterruptedException, IPMsgException {
//        closeSocketReceived.init();
        
        // Initialize connection and setup the reader and writer.
        connectUsingConfiguration();

        // Sockets setup successfully.
        initConnection();

//        // XXX Broadcast available packets
        initialSocketComplete.checkIfSuccessOrWait();
        
        afterSuccessfulConnect(false);
    }

    @Override
    protected void shutdown() {
        shutdown(false);
    }

    @Override
    protected void sendInternal(Packet packet) throws NotConnectedException, ClientUnavailableException, InterruptedException {
        InetAddress toAddress = null;
        try {
            toAddress = packet.getTo().getInetAddress();
        } catch (UnknownHostException e) {
            throw new ClientUnavailableException("Address not available.", e);
        }
        datagramWriter.sendPacket(packet);
    }

    private void connectUsingConfiguration() throws IPMsgException.ConnectException {
        try {
            // XXX: 2017/3/7 Is there both setBroadcast(true)?
            datagramSocket = new DatagramSocket(getPort());
            datagramSocket.setBroadcast(true);
        } catch (SocketException e) {
            throw new IPMsgException.ConnectException("Can't create DatagramSocket.", e);
        }
    }

    private void initConnection() {
        boolean isFirstInitialization = datagramReader == null || datagramWriter == null;

        if (isFirstInitialization) {
            datagramReader = new DatagramPacketReader();
            datagramWriter = new DatagramPacketWriter();
        }

        datagramReader.init(config.getDatagramBodySize());
        datagramWriter.init();
    }

    private void shutdown(boolean instant) {
        if (datagramWriter != null) {
            LogUtil.fine(TAG, "DatagramWriter shutdown()", null);
            datagramWriter.shutdown(instant);
        }
        LogUtil.fine(TAG, "DatagramWriter has been shutdown.", null);
    
        if (datagramReader != null) {
            LogUtil.fine(TAG, "DatagramReader shutdown()", null);
            datagramReader.shutdown();
        }
        LogUtil.fine(TAG, "DatagramReader has been shutdown.", null);
    
//        try {
//            closeSocketReceived.checkIfSuccessOrWait();
//        } catch (NoResponseException | InterruptedException e) {
//            LogUtil.warn(TAG, "Exception while waiting for close socket.", e);
//        }
    
        LogUtil.fine(TAG, "WriterSocket has been shutdown.", null);
        
        if (datagramSocket != null) {
            datagramSocket.close();
        }
        LogUtil.fine(TAG, "ReaderSocket has been shutdown.", null);

        connected = false;
        initialSocketComplete.init();
    }

    @Deprecated
    protected InetAddress getInetAddress(String address) throws UnknownHostException {
        if (StringUtil.isNullOrEmpty(address))
            throw new UnknownHostException("Address can't be null or empty.");
        return IPAddressCache.getInstance().getInetAddress(address);
    }

    protected class DatagramPacketWriter {
        private static final int QUEUE_SIZE = IPMsgConnectionImpl.QUEUE_SIZE;

        private final ArrayBlockingQueueWithShutdown<Packet> queue =
                new ArrayBlockingQueueWithShutdown<>(QUEUE_SIZE, true);

        private SimpleSynchronizationPoint<NoResponseException> shutdownDone =
                new SimpleSynchronizationPoint<>(IPMsgConnectionImpl.this, "PacketWriter shutdown completed");
        protected volatile Long shutdownTimestamp = null;
        protected volatile boolean instantShutdown = false;

        void init() {
            shutdownDone.init();
            shutdownTimestamp = null;

            queue.start();
            Async.go(new Runnable() {
                @Override
                public void run() {
                    writePacket();
                }
            }, "Datagram packet writer");
        }

        boolean done() {
            return shutdownTimestamp != null;
        }

        private Packet nextPacket() {
            Packet element = null;
            try {
                element = queue.take();
            } catch (InterruptedException e) {
                if (!queue.isShutdown()) {
                    LogUtil.warn(TAG, "Datagram packet thread was interrupted. Use disconnect() instead.", null);
                }
            }
            return element;
        }

        private void writePacket() {
            Exception writerException = null;
            try {
                initialSocketComplete.reportSuccess();
                while (!done()) {

                    Packet packet = nextPacket();
                    if (packet == null)
                        continue;

                    sendDatagramPacket(packet);
                    firePacketSendingListener(packet);
                }

                if (!instantShutdown) {
                    try {
                        while (!queue.isEmpty()) {
                            Packet packet = queue.remove();
                            sendDatagramPacket(packet);
                            // Won't fire the packet sending listener while shutdown.
                        }
                    } catch (Exception e) {
                        LogUtil.warn(TAG, "Exception during queue shutdown, ignored.", e);
                    }

                    // TODO: 2017/3/1 send exit command
                    try {
                        datagramSocket.close();
                    } catch (Exception e) {
                        LogUtil.warn(TAG, "Exception during close DatagramWriter, ignored.", e);
                    }
                    queue.clear();
                } else {
                    // TODO: 2017/2/28 Some operation when not a instant shutdown
                }
            } catch (Exception e) {
                if (!done() || queue.isShutdown()) {
                    writerException = e;
                } else {
                    LogUtil.fine(TAG, "Ignoring Exception in writePacket()", e);
                }
            } finally {
                shutdownDone.reportSuccess();
            }

            if (writerException != null) {
                notifyConnectionClosedOnError(writerException);
            }

        }

        private void sendDatagramPacket(Packet packet) throws IOException {
            Address to = packet.getTo();
            byte[] msgBuf = packet.toBytes();

            DatagramPacket datagramPacket = new DatagramPacket(msgBuf, msgBuf.length,
                    to.getInetAddress(), to.getPort());
            datagramSocket.send(datagramPacket);
        }

        void sendPacket(Packet packet) throws InterruptedException {
            // TODO: 2017/2/28 check or throw network unavailable exception
            try {
                queue.put(packet);
            } catch (InterruptedException e) {
                // TODO: 2017/2/28 check or throw network unavailable exception
                throw e;
            }
        }

        void shutdown(boolean instant) {
            instantShutdown = instant;
            queue.shutdown();
            shutdownTimestamp = System.currentTimeMillis();
            try {
                shutdownDone.checkIfSuccessOrWait();
            } catch (NoResponseException | InterruptedException e) {
                LogUtil.warn(TAG, "Shutdown throws exception in packet writer", e);
            }
        }

    }


    /**
     * What happens when UDP packet arrives, if I'm not currently running socket.receive()
     * <a href="http://stackoverflow.com/questions/7016612/are-packets-dropped-if-i-am-not-actively-receiving-from-a-datagramsocket?answertab=votes#7016909">see more</a>
     */
    protected class DatagramPacketReader {
        private volatile boolean done;
        private int bufferSize;

        void init(int bufferSize) {
            done = false;
            this.bufferSize = bufferSize;

            Async.go(new Runnable() {
                @Override
                public void run() {
                    receivePackets();
                }
            }, "Datagram packet reader");
        }

        boolean done() {
            return done;
        }

        void shutdown() {
            done = false;
        }

        private void receivePackets() {
            try {
                initialSocketComplete.checkIfSuccessOrWait();
                while (!done()) {
                    byte[] bytes = new byte[bufferSize];
                    DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
                    try {
                        datagramSocket.receive(datagramPacket);
                    } catch (IOException e) {
                        if (!(done() || datagramWriter.queue.isShutdown())) {
                            throw new IPMsgException.ConnectException(
                                    "DatagramSocket throws exception when wait for receive packet.", e);
                        }
                        break;
                    }

                    // XXX Parse packets in another block-queue.
                    try {
                        Address from = new Address(datagramPacket.getAddress().getHostAddress(),
                                datagramPacket.getPort());
                        
                        parseAndProcessPacket(from, datagramPacket.getData());
                    } catch (Exception e) {
                        LogUtil.warn(TAG, "Exception in parseAndProcessPacket, ignored.", e);
                    }
                }
                
//                closeSocketReceived.reportSuccess();
            } catch (Exception e) {
//                closeSocketReceived.reportFailure(e);
                
                if (!(done() || datagramWriter.queue.isShutdown())) {
                    notifyConnectionClosedOnError(e);
                }
            }
        }

    }

}
