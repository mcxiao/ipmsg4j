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

package com.github.mcxiao.ipmsg.roster;

import com.github.mcxiao.ipmsg.AbstractConnectionListener;
import com.github.mcxiao.ipmsg.ConnectCreationListener;
import com.github.mcxiao.ipmsg.ConnectionRegistry;
import com.github.mcxiao.ipmsg.IPMsgConnection;
import com.github.mcxiao.ipmsg.IPMsgException;
import com.github.mcxiao.ipmsg.IPMsgException.ClientUnavailableException;
import com.github.mcxiao.ipmsg.IPMsgException.NotConnectedException;
import com.github.mcxiao.ipmsg.IPMsgProtocol;
import com.github.mcxiao.ipmsg.Manager;
import com.github.mcxiao.ipmsg.PacketFilter;
import com.github.mcxiao.ipmsg.PacketListener;
import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.address.BroadcastAddress;
import com.github.mcxiao.ipmsg.packet.Command;
import com.github.mcxiao.ipmsg.packet.Packet;
import com.github.mcxiao.ipmsg.util.LogUtil;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 */
public class Roster extends Manager {

    private final static String TAG = LogUtil.createTag(Roster.class.getSimpleName(), null);
    
    static {
        ConnectionRegistry.addConnectionCreationListner(new ConnectCreationListener() {
            @Override
            public void connectionCreated(IPMsgConnection connection) {
                getInstanceFor(connection);
            }
        });
    }
    
    private static final Map<IPMsgConnection, Roster> INSTANCES = new WeakHashMap<>();

    private final static PacketFilter PRESENCE_PACKET_FILTER =
            new RosterPacketFilter();

    private PresencePacketListener presencePacketListener =
            new PresencePacketListener();

    private RosterChangeListener rosterChangeListener =
            new RosterChangeListener();

    private final Set<RosterListener> rosterListeners =
            new LinkedHashSet<>();

    private boolean loaded = false;
    
    private final Object rosterListenersAndEntriesLock = new Object();
    
    /**
     *
     */
    private final Map<Address, RosterEntry> entries = new ConcurrentHashMap<>();

    public static Roster getInstanceFor(IPMsgConnection connection) {
        Roster roster = INSTANCES.get(connection);
        if (roster == null) {
            roster = new Roster(connection);
            INSTANCES.put(connection, roster);
        }
        return roster;
    }
    
    private Roster(IPMsgConnection connection) {
        super(connection);

        connection.addSyncPacketListener(presencePacketListener, PRESENCE_PACKET_FILTER);
        connection.addConnectionListener(new AbstractConnectionListener() {
            @Override
            public void connected(IPMsgConnection connection) {
                try {
                    broadcastEntry();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void connectionClosed() {
            }
        });
        
        if (connection.isConnected()) {
            // XXX load roster
        }
    }

    public void reload() throws IPMsgException, InterruptedException {
        broadcastEntry();
    }

    public boolean isLoaded() {
        return loaded;
    }

    public Set<RosterEntry> getEntries() {
        Set<RosterEntry> invokeEntries;
        synchronized (rosterListenersAndEntriesLock) {
            invokeEntries = new HashSet<>(entries.size());
            for (RosterEntry entry : entries.values()) {
                invokeEntries.add(entry);
            }
        }
        return invokeEntries;
    }

    public boolean addRosterListener(RosterListener listener) {
        synchronized (rosterListenersAndEntriesLock) {
            return rosterListeners.add(listener);
        }
    }

    public boolean removeRosterListener(RosterListener listener) {
        synchronized (rosterListenersAndEntriesLock) {
            return rosterListeners.remove(listener);
        }
    }
    
    public void absence(String status) {
        // TODO: 2017/3/19 Impl method
    }
    
    public void entry(String name, String host) {
        // TODO: 2017/3/19 Impl method
    }

    private void fireRosterListener(Address addedEntry, Address updatedEntry,
                                    Address deletedEntry) {
        synchronized (rosterListenersAndEntriesLock) {
            for (RosterListener listener : rosterListeners) {
                if (addedEntry != null) {
                    listener.addEntry(addedEntry);
                }
                if (updatedEntry != null) {
                    listener.updateEntry(updatedEntry);
                }
                if (deletedEntry != null) {
                    listener.deletedEntry(deletedEntry);
                }
            }
        }
    }

    private RosterEntry buildRosterEntryByPacket(Packet packet) {
        RosterEntry entry = new RosterEntry(packet.getHostSub(), packet.getFrom());
        Command command = packet.getCommand();
        entry.setSupportFileAttach(command.acceptOpt(IPMsgProtocol.IPMSG_FILEATTACHOPT));
        entry.setSupportUtf8(command.acceptOpt(IPMsgProtocol.IPMSG_CAPUTF8OPT));

        return entry;
    }

    private Command buildRosterCommand(int mode) {
        Command command = new Command(mode);
        if (connection().isSupportFileAttach()) {
            command.addOption(IPMsgProtocol.IPMSG_FILEATTACHOPT);
        }
        if (connection().isSupportUtf8()) {
            command.addOption(IPMsgProtocol.IPMSG_CAPUTF8OPT);
        }

        return command;
    }

    private void broadcastEntry() throws NotConnectedException, InterruptedException, ClientUnavailableException {
        Command command = buildRosterCommand(IPMsgProtocol.IPMSG_BR_ENTRY);
        sendRosterPacket(new BroadcastAddress(connection().getPort()), command, null);
        // TODO: 2017/3/15 some operate
    }

    /**
     * @return If true when already added roster entry packet.
     */
    private boolean addOrUpdateEntry(Packet packet) {
        Address from = packet.getFrom();
        RosterEntry entry = buildRosterEntryByPacket(packet);

        synchronized (rosterListenersAndEntriesLock) {
            if (entries.get(from) != null) {
                entries.put(from, entry);
                return true;
            } else {
                entries.put(from, entry);
                return false;
            }
        }

    }

    private void sendRosterPacket(@NotNull Address to, @NotNull Command command,
                                  @Nullable byte[] msgBuf)
            throws NotConnectedException, InterruptedException, ClientUnavailableException {

        Packet packet = Packet.createByConnection(connection(), Packet.generatePacketNo(), command, msgBuf);
        packet.setTo(to);
        connection().sendPacket(packet);
    }

    private class PresencePacketListener implements PacketListener {

        @Override
        public void processPacket(Packet packet) throws NotConnectedException, InterruptedException {
            Command command = packet.getCommand();
            Address addedEntry = null;
            Address updatedEntry = null;
            Address deletedEntry = null;

            switch (command.getMode()) {
                case IPMsgProtocol.IPMSG_BR_ENTRY:
                    if (addOrUpdateEntry(packet)) {
                        updatedEntry = packet.getFrom();
                    } else {
                        addedEntry = packet.getFrom();
                    }

                    try {
                        sendRosterPacket(packet.getFrom(), buildRosterCommand(IPMsgProtocol.IPMSG_ANSENTRY), null);
                    } catch (Exception e) {
                        LogUtil.warn(TAG, "PresencePacketListener.processPacket send IPMSG_ANSENTRY fail.", e);
                    }
                    break;
                case IPMsgProtocol.IPMSG_ANSENTRY:
                    if (addOrUpdateEntry(packet)) {
                        updatedEntry = packet.getFrom();
                    } else {
                        addedEntry = packet.getFrom();
                    }
                    break;
                case IPMsgProtocol.IPMSG_BR_ABSENCE:

                    break;
                case IPMsgProtocol.IPMSG_BR_EXIT:

                    break;
            }

            fireRosterListener(addedEntry, updatedEntry, deletedEntry);
        }
    }

    @Deprecated
    private class RosterChangeListener implements PacketListener {

        @Override
        public void processPacket(Packet packet) throws NotConnectedException, InterruptedException {
            // TODO: 2017/3/14 Impl method
        }
    }

}
