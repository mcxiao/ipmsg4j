package com.github.mcxiao;

/**
 */
public class IPMsgConfiguration {

    private String localHost;
    private int port;
    private int datagramBodySize;
    private boolean supportFileAttach;
    private boolean supportUtf8;

    private String senderName;
    private String senderHost;

    public static IPMsgConfiguration create() {
        return new IPMsgConfiguration();
    }

    private IPMsgConfiguration() {
        initDefaultValue();
    }

    private void initDefaultValue() {
        setLocalHost(null);
        setPort(IPMsgProtocol.PORT);
        setDatagramBodySize(IPMsgProperties.DATAGRAM_BODY_MAX_SIZE);
        setSupportFileAttach(false);
        setSupportUtf8(false);
        setSenderName("IPMessenger");
        setSenderHost("IPMessageHost");
    }

    public int getPort() {
        return port;
    }

    public IPMsgConfiguration setPort(int port) {
        this.port = port;
        return IPMsgConfiguration.this;
    }

    public int getDatagramBodySize() {
        return datagramBodySize;
    }

    public IPMsgConfiguration setDatagramBodySize(int maxDatagramBody) {
        this.datagramBodySize = maxDatagramBody;
        return IPMsgConfiguration.this;
    }

    public String getLocalHost() {
        return localHost;
    }

    public IPMsgConfiguration setLocalHost(String localHost) {
        this.localHost = localHost;
        return IPMsgConfiguration.this;
    }

    public boolean isSupportFileAttach() {
        return supportFileAttach;
    }

    public IPMsgConfiguration setSupportFileAttach(boolean supportFileAttach) {
        this.supportFileAttach = supportFileAttach;
        return IPMsgConfiguration.this;
    }

    public boolean isSupportUtf8() {
        return supportUtf8;
    }

    public IPMsgConfiguration setSupportUtf8(boolean supportUtf8) {
        this.supportUtf8 = supportUtf8;
        return IPMsgConfiguration.this;
    }

    public String getSenderName() {
        return senderName;
    }

    public IPMsgConfiguration setSenderName(String senderName) {
        this.senderName = senderName;
        return IPMsgConfiguration.this;
    }

    public String getSenderHost() {
        return senderHost;
    }

    public IPMsgConfiguration setSenderHost(String senderHost) {
        this.senderHost = senderHost;
        return IPMsgConfiguration.this;
    }
}
