package com.github.mcxiao;

/**
 */
public class IPMsgException extends Exception {

    public IPMsgException() {
    }

    public IPMsgException(String message) {
        super(message);
    }

    public IPMsgException(String message, Throwable cause) {
        super(message, cause);
    }

    public IPMsgException(Throwable cause) {
        super(cause);
    }

    public static final class NoResponseException extends IPMsgException {
        public NoResponseException(String message) {
            super(message);
        }

        public NoResponseException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static final class ClientUnavailableException extends IPMsgException {
        public ClientUnavailableException(String message) {
            super(message);
        }

        public ClientUnavailableException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static final class IllegalPacketFormatException extends IPMsgException {
        public IllegalPacketFormatException(String message) {
            super(message);
        }

        public IllegalPacketFormatException(String message, Throwable cause) {
            super(message, cause);
        }
    }



}
