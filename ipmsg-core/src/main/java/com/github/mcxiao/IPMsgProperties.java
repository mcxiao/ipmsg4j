package com.github.mcxiao;

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

}
