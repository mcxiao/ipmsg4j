package com.github.mcxiao;

/**
 */
public interface ConnectionListener {

    void connected(IPMsgConnection connection);

    void connectionClosed();

    void connectionClosedOnError(Exception e);

}
