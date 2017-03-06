package com.github.mcxiao;

import com.github.mcxiao.IPMsgException.ClientUnavailableException;
import com.github.mcxiao.packet.Packet;
import com.github.mcxiao.util.LogUtil;
import com.github.mcxiao.util.PacketParseUtil;
import com.github.mcxiao.util.StringUtil;
import com.github.mcxiao.util.cache.memory.impl.LruINetAddressMemoryCache;
import org.jivesoftware.smack.util.ArrayBlockingQueueWithShutdown;
import org.jivesoftware.smack.util.Async;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 */
public class IPMsgTCPConnection extends AbstractConnection {

    private static final int QUEUE_SIZE = 500;
    private static final String TAG = LogUtil.createTag(IPMsgTCPConnection.class.getSimpleName(), null);

    private IPMsgConfiguration config;

    // XXX Some improve: Dynamic create(according network users)
    private static final LruINetAddressMemoryCache INETADDRES_CACHE = new LruINetAddressMemoryCache(20);

    private DatagramSocket readerSocket;
    private DatagramSocket writerSocket;

    private DatagramPacketReader datagramReader;
    private DatagramPacketWriter datagramWriter;

    protected IPMsgTCPConnection(IPMsgConfiguration configuration) {
        super(configuration);
        this.config = configuration;
    }

    @Override
    protected void sendInternal(Packet packet) throws InterruptedException, ClientUnavailableException {
        InetAddress toAddress = null;
        try {
            toAddress = getInetAddress(packet.getTo());
        } catch (UnknownHostException e) {
            throw new ClientUnavailableException("Address not available", e);
        }
        PacketEnvelope packetEnvelope = new PacketEnvelope(packet, toAddress, packet.getPort());
        datagramWriter.sendPacketEnvelope(packetEnvelope);
    }

    private void initConnection() {
        datagramWriter.init();
    }

    private void shutdown(boolean instant) {
        datagramWriter.shutdown(instant);
    }

    protected InetAddress getInetAddress(String address) throws UnknownHostException {
        if (StringUtil.isNullOrEmpty(address))
            throw new UnknownHostException("Address can't be null or empty.");
        return INETADDRES_CACHE.getOrCreate(address);
    }

    private void notifyConnectionException(Exception e) {
        // TODO: 2017/2/28 impl method
        LogUtil.warn(TAG, "Notify Connection Exception", e);
    }

    protected class DatagramPacketWriter {
        private static final int QUEUE_SIZE = IPMsgTCPConnection.QUEUE_SIZE;

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
            PacketEnvelope element = null;
            try {
                element = queue.take();
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
                while (!done()) {

                    PacketEnvelope envelope = nextPacketEnvelope();
                    if (envelope == null)
                        continue;

                    sendDatagramPacketByEnvelope(envelope);
                }

                if (!instantShutdown) {
                    try {
                        while (!queue.isEmpty()) {
                            PacketEnvelope envelope = queue.remove();
                            sendDatagramPacketByEnvelope(envelope);
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
                notifyConnectionException(writerException);
            }

        }

        private void sendDatagramPacketByEnvelope(PacketEnvelope envelope) throws IOException {
            Packet packet = envelope.packet;
            InetAddress toAddress = envelope.toAddress;
            int port = envelope.port;
            byte[] msgBuf = packet.getMsgBuf();

            DatagramPacket datagramPacket = new DatagramPacket(msgBuf, msgBuf.length, toAddress, port);
            writerSocket.send(datagramPacket);
        }

        void sendPacketEnvelope(PacketEnvelope envelope) throws InterruptedException {
            // TODO: 2017/2/28 check or throw network unavailable exception
            try {
                queue.put(envelope);
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
        private volatile  boolean done;
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
            while (!done()) {
                byte[] bytes = new byte[bufferSize];
                DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
                try {
                    readerSocket.receive(datagramPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }

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
