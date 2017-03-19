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
import com.github.mcxiao.ipmsg.IPMsgException.NotConnectedException;
import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.address.BroadcastAddress;
import com.github.mcxiao.ipmsg.packet.Command;
import com.github.mcxiao.ipmsg.packet.HostSub;
import com.github.mcxiao.ipmsg.packet.Packet;
import com.github.mcxiao.ipmsg.util.IPMsgThreadFactory;
import com.github.mcxiao.ipmsg.util.LogUtil;
import com.github.mcxiao.ipmsg.util.PacketParseUtil;
import com.github.mcxiao.ipmsg.util.StringUtil;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.jivesoftware.smack.util.Async;
import org.jivesoftware.smack.util.BoundedThreadPoolExecutor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 */
public abstract class AbstractConnection implements IPMsgConnection {

    private final static String TAG = LogUtil.createTag(AbstractConnection.class.getSimpleName(), null);

    private final IPMsgConfiguration config;

    private HostSub hostSub;
    private Address localHost;

    private final BoundedThreadPoolExecutor executorService =
            new BoundedThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
                    100, new IPMsgThreadFactory("Incoming Processor"));

    private final ExecutorService singleThreadExecutorService =
            Executors.newSingleThreadExecutor(new IPMsgThreadFactory("Single thread executor"));

    private final Set<ConnectionListener> connectionListeners = new CopyOnWriteArraySet<>();

    /**
     * List of PacketListeners that will be notified synchronously when a new packet was received.
     */
    private final Map<PacketListener, ListenerWrapper> syncRecvListeners = new LinkedHashMap<>();

    /**
     * List of PacketListeners that will be notified asynchronously when a new packet was received.
     */
    private final Map<PacketListener, ListenerWrapper> asyncRecvListeners = new LinkedHashMap<>();

    /**
     * List of PacketListeners that will be notified when a new packet was sent.
     */
    private final Map<PacketListener, ListenerWrapper> sendListeners = new HashMap<>();

    /**
     * This PacketListeners will be notified when a new packet is about to be sent to the server.
     * These interceptors may modify the stanza before it is being actually sent to the server.
     */
    private final Map<PacketListener, InterceptorWrapper> interceptors = new LinkedHashMap<>();


    protected boolean connected = false;

    private Lock connectionLock = new ReentrantLock();

    private long packetReplyTimeout = 5 * 1000;

    protected AbstractConnection(IPMsgConfiguration configuration) {
        this.config = configuration;
        hostSub = new HostSub(config.getSenderName(), config.getSenderHost());
    }

    @Override
    public String getVersion() {
        return IPMsgProperties.VERSION_STRING;
    }

    @Override
    public String getSenderHost() {
        return hostSub.getSenderHost();
    }

    @Override
    public String getSenderName() {
        return hostSub.getSenderName();
    }

    @Override
    public HostSub getHostSub() {
        return hostSub;
    }

    @Override
    public Address getLocalhostAddress() {
        return localHost;
    }

    @Override
    public int getPort() {
        return localHost.getPort();
    }

    @Override
    public void sendPacket(Packet packet) throws NotConnectedException, ClientUnavailableException, InterruptedException {
        if (packet == null) {
            throw new NullPointerException("Packet must not be null");
        }

        checkNotConnectedOrThrow(null);

        // Interceptors may modify the content of the packet.
        firePacketInterceptors(packet);
        sendInternal(packet);
    }

    @Override
    public void addPacketListener(PacketListener listener, PacketFilter filter) {
        addAsyncPacketListener(listener, filter);
    }

    @Override
    public boolean removePacketListener(PacketListener listener) {
        return removeAsyncPacketListener(listener);
    }

    @Override
    public void addAsyncPacketListener(PacketListener listener, PacketFilter filter) {
        if (listener == null) {
            throw new NullPointerException("Packet listener is null.");
        }
        ListenerWrapper wrapper = new ListenerWrapper(listener, filter);
        synchronized (asyncRecvListeners) {
            asyncRecvListeners.put(listener, wrapper);
        }
    }

    @Override
    public boolean removeAsyncPacketListener(PacketListener listener) {
        synchronized (asyncRecvListeners) {
            return asyncRecvListeners.remove(listener) != null;
        }
    }

    @Override
    public void addSyncPacketListener(PacketListener listener, PacketFilter filter) {
        if (listener == null) {
            throw new NullPointerException("Packet listener is null.");
        }
        ListenerWrapper wrapper = new ListenerWrapper(listener, filter);
        synchronized (syncRecvListeners) {
            syncRecvListeners.put(listener, wrapper);
        }
    }

    @Override
    public boolean removeSyncPacketListener(PacketListener listener) {
        synchronized (syncRecvListeners) {
            return syncRecvListeners.remove(listener) != null;
        }
    }

    @Override
    public void addPacketSendingListener(PacketListener listener, PacketFilter filter) {
        if (listener == null) {
            throw new NullPointerException("Packet listener is null.");
        }
        ListenerWrapper wrapper = new ListenerWrapper(listener, filter);
        synchronized (sendListeners) {
            sendListeners.put(listener, wrapper);
        }
    }

    @Override
    public void removePacketSendingListener(PacketListener listener) {
        synchronized (sendListeners) {
            sendListeners.remove(listener);
        }
    }

    @Override
    public void addPacketInterceptor(PacketListener interceptor, PacketFilter filter) {
        if (interceptor == null) {
            throw new NullPointerException("Packet interceptor is null.");
        }
        InterceptorWrapper wrapper = new InterceptorWrapper(interceptor, filter);
        synchronized (interceptors) {
            interceptors.put(interceptor, wrapper);
        }
    }

    @Override
    public boolean removePacketInterceptor(PacketListener interceptor) {
        synchronized (interceptors) {
            return interceptors.remove(interceptor) != null;
        }
    }

    @Override
    public long getPacketReplyTimeout() {
        return packetReplyTimeout;
    }

    @Override
    public boolean isSupportUtf8() {
        return this.config.isSupportUtf8();
    }

    @Override
    public boolean isSupportFileAttach() {
        return this.config.isSupportFileAttach();
    }

    public synchronized AbstractConnection connect() throws IPMsgException, InterruptedException {
        checkAlreadyConnectedOrThrow();

        setupLocalHostAndPort(this.config);

        connectionInternal();

        connected = true;
        notifyConnectionConnected();

        return this;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    public void disconnect() {
        try {
            disconnect(null);       // XXX: 2017/3/6 Broadcast absence packet.
        } catch (IPMsgException e) {
            LogUtil.fine(TAG, "Connection already disconnected.", e);
        }
    }

    public synchronized void disconnect(Packet unavailablePacket) throws IPMsgException {
        try {
            sendInternal(unavailablePacket);
        } catch (Exception e) {
            LogUtil.fine(TAG, "Interrupted when disconnect the connection. Continuing.", e);
        }

        shutdown();
        notifyConnectionClosed();
        this.hostSub = null;
    }

    protected abstract void connectionInternal() throws InterruptedException, IPMsgException;

    protected abstract void shutdown();

    protected abstract void sendInternal(Packet packet)
            throws NotConnectedException, ClientUnavailableException, InterruptedException;

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

    private void setupLocalHostAndPort(IPMsgConfiguration config) throws IPMsgException.ConnectException {
        InetAddress localHost;
        String localHostString = config.getLocalHost();
        if (StringUtil.isNullOrEmpty(localHostString)) {
            throw new IPMsgException.ConnectException("Localhost must set.");
        }
        try {
            localHost = InetAddress.getByName(localHostString);
        } catch (UnknownHostException e) {
            throw new IPMsgException.ConnectException("Localhost unavailable.", e);
        }

        this.localHost = new Address(config.getLocalHost(), config.getPort());
    }

    protected void notifyConnectionConnected() {
        for (ConnectionListener listener : connectionListeners) {
            try {
                listener.connected(AbstractConnection.this);
            } catch (Exception e) {
                LogUtil.warn(TAG, "Error in listener while connect.", e);
            }
        }
    }

    protected void notifyConnectionClosed() {
        for (ConnectionListener listener : connectionListeners) {
            try {
                listener.connectionClosed();
            } catch (Exception e) {
                LogUtil.warn(TAG, "Error in listener while closing connection.", e);
            }
        }
    }

    protected void notifyConnectionClosedOnError(Exception e) {
        for (ConnectionListener listener : connectionListeners) {
            try {
                listener.connectionClosedOnError(e);
            } catch (Exception ex) {
                LogUtil.warn(TAG, "Error in listener while closing connection on error.", ex);
            }
        }
    }

    private void notifyRecvListeners(Packet packet) {
        final Collection<PacketListener> listenersToNotify = new LinkedList<>();

        synchronized (asyncRecvListeners) {
            for (ListenerWrapper wrapper : asyncRecvListeners.values()) {
                if (wrapper.filterMatches(packet)) {
                    listenersToNotify.add(wrapper.getListener());
                }
            }
        }

        for (PacketListener listener : listenersToNotify) {
            Async.go(new Runnable() {
                @Override
                public void run() {
                    try {
                        listener.processPacket(packet);
                    } catch (NotConnectedException ne) {
                        LogUtil.warn(TAG, "NotConnectedException catch.", ne);
                    } catch (Exception e) {
                        LogUtil.warn(TAG, "Exception in async packet listener", e);
                    }
                }
            });
        }

        listenersToNotify.clear();
        synchronized (syncRecvListeners) {
            for (ListenerWrapper wrapper : syncRecvListeners.values()) {
                if (wrapper.filterMatches(packet)) {
                    listenersToNotify.add(wrapper.getListener());
                }
            }
        }

        for (PacketListener listener : listenersToNotify) {
            singleThreadExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        listener.processPacket(packet);
                    } catch (NotConnectedException ne) {
                        LogUtil.warn(TAG, "NotConnectedException catch.", ne);
                    } catch (Exception e) {
                        LogUtil.warn(TAG, "Exception in sync packet listener", e);
                    }
                }
            });
        }
    }

    private void firePacketInterceptors(Packet packet) {
        List<PacketListener> interceptorsToFire = new LinkedList<>();
        synchronized (interceptors) {
            for (InterceptorWrapper wrapper : interceptors.values()) {
                if (wrapper.filterMatches(packet)) {
                    interceptorsToFire.add(wrapper.getInterceptor());
                }
            }
        }
        for (PacketListener interceptor : interceptorsToFire) {
            try {
                interceptor.processPacket(packet);
            } catch (Exception e) {
                LogUtil.warn(TAG, "Exception in packet interceptor", e);
            }
        }
    }

    protected void firePacketSendingListener(Packet packet) {
        LinkedList<PacketListener> listenersToFire = new LinkedList<>();
        synchronized (sendListeners) {
            for (ListenerWrapper wrapper : sendListeners.values()) {
                if (wrapper.filterMatches(packet)) {
                    listenersToFire.add(wrapper.getListener());
                }
            }
        }
        for (PacketListener listener : listenersToFire) {
            try {
                listener.processPacket(packet);
            } catch (Exception e) {
                LogUtil.warn(TAG, "Exception in packet sending listener", e);
            }
        }
    }

    @Override
    public void addConnectionListener(ConnectionListener listener) {
        if (listener == null)
            return;
        connectionListeners.add(listener);
    }

    @Override
    public void removeConnectionListener(ConnectionListener listener) {
        connectionListeners.remove(listener);
    }

    protected void checkNotConnectedOrThrow(@Nullable String message) throws NotConnectedException {
        if (!isConnected())
            throw new NotConnectedException(message);
    }

    protected void checkAlreadyConnectedOrThrow() throws IPMsgException.AlreadyConnectException {
        if (isConnected())
            throw new IPMsgException.AlreadyConnectException(null);
    }

    protected static class ListenerWrapper {
        private final PacketListener listener;
        private final PacketFilter filter;

        ListenerWrapper(@NotNull PacketListener listener, @NotNull PacketFilter filter) {
            this.listener = listener;
            this.filter = filter;
        }

        boolean filterMatches(Packet packet) {
            return filter.accept(packet);
        }

        PacketListener getListener() {
            return listener;
        }
    }

    protected Lock getConnectionLock() {
        return connectionLock;
    }

    protected static class InterceptorWrapper {
        private final PacketListener interceptor;
        private final PacketFilter filter;

        InterceptorWrapper(@NotNull PacketListener interceptor, @NotNull PacketFilter filter) {
            this.interceptor = interceptor;
            this.filter = filter;
        }

        boolean filterMatches(Packet packet) {
            return filter.accept(packet);
        }

        PacketListener getInterceptor() {
            return interceptor;
        }
    }
}
