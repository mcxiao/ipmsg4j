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

/**
 */
public final class IPMsgProperties {

    /**
     * The version of this IPMsg protocol version.
     */
    public final static String VERSION_STRING = String.valueOf(IPMsgProtocol.IPMSG_VERSION);

    /**
     * 64k.According UDP rules.<br/>
     */
    public final static int DATAGRAM_BODY_MAX_SIZE = 65536;

    /**
     * Safest message limit.
     * <a href="http://stackoverflow.com/questions/9203403/java-datagrampacket-udp-maximum-send-recv-buffer-size?answertab=votes#9235558">see more</a>
     */
    public final static int DATAGRAM_BODY_SAFE_SIZE = 1400;

    public static String BROADCAST_ADDRESS = "255.255.255.255";
    
    public final static boolean DEFAULT_UTF8_OPT = true;
    
    public final static String DEFAULT_SENDER_NAME = "IPMessenger";
    
    public final static String DEFAULT_SENDER_HOST = "IPMessengerHost";

}
