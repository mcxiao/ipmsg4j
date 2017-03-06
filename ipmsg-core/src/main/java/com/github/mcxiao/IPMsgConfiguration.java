package com.github.mcxiao;

/**
 */
public class IPMsgConfiguration {

    private int port;
    private int datagramBodySize;

    public static IPMsgConfiguration create() {
        return new IPMsgConfiguration();
    }

    private IPMsgConfiguration() {
        initDefaultValue();
    }

    private void initDefaultValue() {
        setPort(IPMsgProtocol.PORT);
        setDatagramBodySize(IPMsgProperties.DATAGRAM_BODY_MAX_SIZE);
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
}
