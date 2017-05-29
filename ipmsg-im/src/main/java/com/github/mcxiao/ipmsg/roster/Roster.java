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

import com.github.mcxiao.ipmsg.AbstractConnection;
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
import com.github.mcxiao.ipmsg.packet.Presence;
import com.github.mcxiao.ipmsg.roster.packet.RosterPacket;
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
public final class Roster extends Manager {

    private final static String TAG = LogUtil.createTag(Roster.class.getSimpleName(), null);
    
    static {
        ConnectionRegistry.addConnectionCreationListner(new ConnectCreationListener() {
            @Override
            public void connectionCreated(AbstractConnection connection) {
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

    public static Roster getInstanceFor(AbstractConnection connection) {
        Roster roster = INSTANCES.get(connection);
        if (roster == null) {
            roster = new Roster(connection);
            INSTANCES.put(connection, roster);
        }
        return roster;
    }
    
    private Roster(AbstractConnection connection) {
        super(connection);

        connection.addSyncPacketListener(presencePacketListener, PRESENCE_PACKET_FILTER);
        connection.addConnectionListener(new AbstractConnectionListener() {
            @Override
            public void connected(IPMsgConnection connection) {
                try {
                    sendRosterPacket(Presence.TYPE_BR_ENTRY, new BroadcastAddress(connection.getPort()), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void connectionClosed() {
            }
        });
        
    }

    public void reload() throws IPMsgException, InterruptedException {
        RosterPacket packet = new RosterPacket(RosterPacket.TYPE_BR_ENTRY);
        packet.setTo(new BroadcastAddress(connection().getPort()));
        packet.setSupportUtf8(connection().isSupportUtf8());
        packet.setSupportFileAttach(connection().isSupportFileAttach());
    
        packet.setUserName(connection().getSenderName());
        packet.setUserHost(connection().getSenderHost());
        // TODO support NickName and Group
        
        connection().sendPacket(packet);
    }

    public boolean isLoaded() {
        return loaded;
    }

    public RosterEntry getEntry(Address address) {
        if (address == null) {
            return null;
        }
        return entries.get(address);
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

    private RosterEntry buildRosterEntryByPacket(Presence presence) {
        RosterEntry entry = new RosterEntry(presence.getHostSub(), presence.getFrom());
        entry.setSupportFileAttach(presence.isSupportFileAttach());
        entry.setSupportUtf8(presence.isSupportUtf8());
        entry.setStatus(presence.isAbsence() ? RosterEntry.STATUS_ABSENCE : RosterEntry.STATUS_NORMAL);

        return entry;
    }

    /**
     * @return If true when already added roster entry packet.
     */
    private boolean addOrUpdateEntry(Presence presence) {
        Address from = presence.getFrom();
        RosterEntry entry = buildRosterEntryByPacket(presence);

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

    private void sendRosterPacket(@NotNull int type, @NotNull Address to,
                                  @Nullable String extString)
            throws NotConnectedException, InterruptedException, ClientUnavailableException {
        Presence presence = buildPresence(type, to);
//        presence.setExtString(extString);
        
        connection().sendPacket(presence);
    }
    
    private Presence buildPresence(int type, Address to) {
        Presence presence = new Presence(type);
        presence.setSupportFileAttach(connection().isSupportFileAttach());
        presence.setSupportUtf8(connection().isSupportUtf8());
        presence.setTo(to);
        
        return presence;
    }

    private class PresencePacketListener implements PacketListener {

        @Override
        public void processPacket(Packet packet) throws NotConnectedException, InterruptedException {
            if (!(packet instanceof Presence)) {
                return;
            }
            
            Presence presence = (Presence) packet;
            Command command = presence.getCommand();
            Address addedEntry = null;
            Address updatedEntry = null;
            Address deletedEntry = null;

            switch (command.getMode()) {
                case IPMsgProtocol.IPMSG_BR_ENTRY:
                    if (addOrUpdateEntry(presence)) {
                        updatedEntry = presence.getFrom();
                    } else {
                        addedEntry = presence.getFrom();
                    }

                    try {
                        sendRosterPacket(Presence.TYPE_BR_ANSENTRY, presence.getFrom(), null);
                    } catch (Exception e) {
                        LogUtil.warn(TAG, "PresencePacketListener.processPacket send IPMSG_ANSENTRY fail.", e);
                    }
                    break;
                case IPMsgProtocol.IPMSG_ANSENTRY:
                    if (addOrUpdateEntry(presence)) {
                        updatedEntry = presence.getFrom();
                    } else {
                        addedEntry = presence.getFrom();
                    }
                    break;
                case IPMsgProtocol.IPMSG_BR_ABSENCE:
                    
                    break;
                case IPMsgProtocol.IPMSG_BR_EXIT:
                    deletedEntry = presence.getFrom();
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
