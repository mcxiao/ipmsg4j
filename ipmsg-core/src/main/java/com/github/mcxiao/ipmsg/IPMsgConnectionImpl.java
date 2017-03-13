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

import com.github.mcxiao.ipmsg.packet.Command;
import com.github.mcxiao.ipmsg.packet.HostSub;
import com.github.mcxiao.ipmsg.packet.Packet;
import com.github.mcxiao.ipmsg.util.LogUtil;
import com.github.mcxiao.ipmsg.util.StringUtil;
import com.github.mcxiao.ipmsg.util.cache.memory.impl.LruINetAddressMemoryCache;
import org.jivesoftware.smack.util.ArrayBlockingQueueWithShutdown;
import org.jivesoftware.smack.util.Async;

import java.io.IOException;
import java.net.*;

/**
 */
public class IPMsgConnectionImpl extends AbstractConnection {

    private static final int QUEUE_SIZE = 500;
    private static final String TAG = LogUtil.createTag(IPMsgConnectionImpl.class.getSimpleName(), null);

    private IPMsgConfiguration config;

    // XXX Some improve: Dynamic create(according network users)
    private static final LruINetAddressMemoryCache INETADDRES_CACHE = new LruINetAddressMemoryCache(20);

    private DatagramSocket readerSocket;
    private DatagramSocket writerSocket;

    private DatagramPacketReader datagramReader;
    private DatagramPacketWriter datagramWriter;

    private SimpleSynchronizationPoint<Exception> initialSocketComplete = new
            SimpleSynchronizationPoint<>(IPMsgConnectionImpl.this, "initial socket complete");

    public IPMsgConnectionImpl(IPMsgConfiguration configuration) {
        super(configuration);
        this.config = configuration;
    }

    @Override
    protected void connectionInternal() throws InterruptedException, IPMsgException {
        // Initialize connection and setup the reader and writer.
        connectUsingConfiguration();

        // Sockets setup successfully.
        initConnection();

//        // XXX Broadcast available packets
        initialSocketComplete.checkIfSuccessOrWait();
        brEntry("0");
    }

    @Override
    protected void shutdown() {
        shutdown(false);
    }

    @Override
    protected void sendInternal(Packet packet) throws InterruptedException, IPMsgException {
        InetAddress toAddress = null;
        try {
            toAddress = getInetAddress(packet.getTo());
        } catch (UnknownHostException e) {
            throw new IPMsgException.ClientUnavailableException("Address not available", e);
        }
        PacketEnvelope packetEnvelope = new PacketEnvelope(packet, toAddress, packet.getPort());
        datagramWriter.sendPacketEnvelope(packetEnvelope);
    }

    private void connectUsingConfiguration() throws IPMsgException.ConnectException {
        try {
            // XXX: 2017/3/7 Is there both setBroadcast(true)?
            readerSocket = new DatagramSocket(getPort());
            readerSocket.setBroadcast(true);
            writerSocket = new DatagramSocket();
            writerSocket.setBroadcast(true);
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

    @Deprecated
    private void brEntry(String packetNo) throws IPMsgException, InterruptedException {
        Command command = new Command(IPMsgProtocol.IPMSG_BR_ENTRY);
        Packet packet = new Packet(IPMsgProperties.VERSION_STRING, packetNo,
                command, new HostSub(getSenderName(), getSenderHost()));
        packet.setTo("255.255.255.255");
        packet.setFrom(getLocalhostAddress());
        sendPacket(packet);
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

        if (writerSocket != null) {
            writerSocket.close();
        }
        if (readerSocket != null) {
            readerSocket.close();
        }
        LogUtil.fine(TAG, "WriterSocket and ReaderSocket has been shutdown.", null);

        connected = false;
        initialSocketComplete.init();
    }

    protected InetAddress getInetAddress(String address) throws UnknownHostException {
        if (StringUtil.isNullOrEmpty(address))
            throw new UnknownHostException("Address can't be null or empty.");
        return INETADDRES_CACHE.getOrCreate(address);
    }

    protected class DatagramPacketWriter {
        private static final int QUEUE_SIZE = IPMsgConnectionImpl.QUEUE_SIZE;

        private final ArrayBlockingQueueWithShutdown<PacketEnvelope> queue =
                new ArrayBlockingQueueWithShutdown<>(QUEUE_SIZE, true);

        protected volatile Long shutdownTimestamp = null;
        protected volatile boolean instantShutdown = false;

        void init() {
            shutdownTimestamp = null;

            queue.start();
            Async.go(new Runnable() {
                @Override
                public void run() {
                    writeEnvelopes();
                }
            }, "Datagram packet writer");
        }

        boolean done() {
            return shutdownTimestamp != null;
        }

        private PacketEnvelope nextPacketEnvelope() {
            LogUtil.warn(TAG, Thread.currentThread().getName() + " -> nextPacketEnvelope", null);
            PacketEnvelope element = null;
            try {
                element = queue.take();
                LogUtil.warn(TAG, Thread.currentThread().getName() + " -> queue take done", null);
            } catch (InterruptedException e) {
                if (!queue.isShutdown()) {
                    LogUtil.warn(TAG, "Datagram packet thread was interrupted. Use disconnect() instead.", null);
                }
            }
            return element;
        }

        private void writeEnvelopes() {
            Exception writerException = null;
            try {
                initialSocketComplete.reportSuccess();
                while (!done()) {

                    PacketEnvelope envelope = nextPacketEnvelope();
                    if (envelope == null)
                        continue;

                    sendDatagramPacketByEnvelope(envelope);
                    firePacketSendingListener(envelope.packet);
                }

                if (!instantShutdown) {
                    try {
                        while (!queue.isEmpty()) {
                            PacketEnvelope envelope = queue.remove();
                            sendDatagramPacketByEnvelope(envelope);
                            // Won't fire the packet sending listener while shutdown.
                        }
                    } catch (Exception e) {
                        LogUtil.warn(TAG, "Exception during queue shutdown, ignored.", e);
                    }

                    // TODO: 2017/3/1 send exit command
                    try {
                        writerSocket.close();
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
                    LogUtil.fine(TAG, "Ignoring Exception in writeEnvelopes()", e);
                }
            } finally {
                // TODO: 2017/2/28
            }

            if (writerException != null) {
                notifyConnectionClosedOnError(writerException);
            }

        }

        private void sendDatagramPacketByEnvelope(PacketEnvelope envelope) throws IOException {
            Packet packet = envelope.packet;
            InetAddress toAddress = envelope.toAddress;
            int port = envelope.port;
            byte[] msgBuf = packet.toBytes();

            DatagramPacket datagramPacket = new DatagramPacket(msgBuf, msgBuf.length, toAddress, port);
            writerSocket.send(datagramPacket);
        }

        void sendPacketEnvelope(PacketEnvelope envelope) throws InterruptedException {
            // TODO: 2017/2/28 check or throw network unavailable exception
            try {
                LogUtil.warn(TAG, Thread.currentThread().getName() + " -> sendPacketEnvelope", null);
                queue.put(envelope);
                LogUtil.warn(TAG, Thread.currentThread().getName() + " -> queue put done", null);
            } catch (InterruptedException e) {
                // TODO: 2017/2/28 check or throw network unavailable exception
                throw e;
            }
        }

        void shutdown(boolean instant) {
            instantShutdown = instant;
            queue.shutdown();
            shutdownTimestamp = System.currentTimeMillis();
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
//                    try {
                    readerSocket.receive(datagramPacket);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    // XXX Parse packets in another block-queue.
                    try {
                        parseAndProcessPacket(
                                datagramPacket.getAddress().getHostAddress(),
                                datagramPacket.getPort(),
                                datagramPacket.getData());
                    } catch (Exception e) {
                        LogUtil.warn(TAG, "Exception in parseAndProcessPacket, ignored.", e);
                    }
                }
            } catch (Exception e) {
                if (!(done() || datagramWriter.queue.isShutdown())) {
                    notifyConnectionClosedOnError(e);
                }
            }
        }

    }

    private static class PacketEnvelope {
        Packet packet;
        InetAddress toAddress;
        int port;

        PacketEnvelope(Packet packet, InetAddress toAddress, int port) {
            this.packet = packet;
            this.toAddress = toAddress;
            this.port = port;
        }
    }

}
