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

    public static final class ConnectException extends IPMsgException {

        public ConnectException(String message) {
            super(message);
        }

        public ConnectException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static final class NotConnectedException extends IPMsgException {

        public NotConnectedException(String message) {
            super(message);
        }

        public NotConnectedException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static final class AlreadyConnectException extends IPMsgException {

        public AlreadyConnectException(String message) {
            super(message);
        }

        public AlreadyConnectException(String message, Throwable cause) {
            super(message, cause);
        }
    }


}
