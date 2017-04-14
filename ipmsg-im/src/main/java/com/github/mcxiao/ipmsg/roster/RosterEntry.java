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

import com.github.mcxiao.ipmsg.address.Address;
import com.github.mcxiao.ipmsg.packet.HostSub;
import com.sun.istack.internal.Nullable;

/**
 */
public class RosterEntry {
    
    public final static int STATUS_NORMAL = 0;
    public final static int STATUS_ABSENCE = 1;

    private HostSub hostSub;

    private Address address;

    private int status = STATUS_NORMAL;
    
    private String statusSummary;
    
    private boolean supportUtf8;

    private boolean supportFileAttach;

    protected RosterEntry(HostSub hostSub, Address address) {
        this.hostSub = hostSub;
        this.address = address;
    }

    @Nullable
    public String getName() {
        return hostSub != null ? hostSub.getSenderName() : null;
    }

    @Nullable
    public String getHostName() {
        return hostSub != null ? hostSub.getSenderHost() : null;
    }

    public Address getAddress() {
        return address;
    }

    public boolean isSupportUtf8() {
        return supportUtf8;
    }

    protected void setSupportUtf8(boolean supportUtf8) {
        this.supportUtf8 = supportUtf8;
    }

    public boolean isSupportFileAttach() {
        return supportFileAttach;
    }

    protected void setSupportFileAttach(boolean supportFileAttach) {
        this.supportFileAttach = supportFileAttach;
    }
    
    public int getStatus() {
        return status;
    }
    
    protected void setStatus(int status) {
        this.status = status;
    }
    
    public String getStatusSummary() {
        return statusSummary;
    }
    
    protected void setStatusSummary(String statusSummary) {
        this.statusSummary = statusSummary;
    }
}
