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
public final class IPMsgConfiguration {

    private String localHost;
    private int port;
    private int datagramBodySize;
    private boolean supportFileAttach;
    private boolean supportUtf8;

    private String senderName;
    private String senderHost;

    public String getLocalHost() {
        return localHost;
    }
    
    public int getPort() {
        return port;
    }
    
    public int getDatagramBodySize() {
        return datagramBodySize;
    }
    
    public boolean isSupportFileAttach() {
        return supportFileAttach;
    }
    
    public boolean isSupportUtf8() {
        return supportUtf8;
    }
    
    public String getSenderName() {
        return senderName;
    }
    
    public String getSenderHost() {
        return senderHost;
    }
    
    public static class Builder {
    
        private String localHost;
        private int port;
        private int datagramBodySize;
        private boolean supportFileAttach;
        private boolean supportUtf8;
    
        private String senderName;
        private String senderHost;
        
        public Builder() {
            initDefaultValue();
        }
    
        private void initDefaultValue() {
            setLocalHost(null);
            setPort(IPMsgProtocol.PORT);
            setDatagramBodySize(IPMsgProperties.DATAGRAM_BODY_MAX_SIZE);
            setSupportFileAttach(false);
            setSupportUtf8(IPMsgProperties.DEFAULT_UTF8_OPT);
            setSenderName(IPMsgProperties.DEFAULT_SENDER_NAME);
            setSenderHost(IPMsgProperties.DEFAULT_SENDER_HOST);
        }
        
        public IPMsgConfiguration build() {
            IPMsgConfiguration configuration = new IPMsgConfiguration();
            configuration.localHost = localHost;
            configuration.port = port;
            configuration.datagramBodySize = datagramBodySize;
            configuration.supportFileAttach = supportFileAttach;
            configuration.supportUtf8 = supportUtf8;
            configuration.senderName = senderName;
            configuration.senderHost = senderHost;
            
            return configuration;
        }
    
        public int getPort() {
            return port;
        }
    
        public Builder setPort(int port) {
            this.port = port;
            return IPMsgConfiguration.Builder.this;
        }
    
        public int getDatagramBodySize() {
            return datagramBodySize;
        }
    
        public Builder setDatagramBodySize(int maxDatagramBody) {
            this.datagramBodySize = maxDatagramBody;
            return IPMsgConfiguration.Builder.this;
        }
    
        public String getLocalHost() {
            return localHost;
        }
    
        public Builder setLocalHost(String localHost) {
            this.localHost = localHost;
            return IPMsgConfiguration.Builder.this;
        }
    
        public boolean isSupportFileAttach() {
            return supportFileAttach;
        }
    
        public Builder setSupportFileAttach(boolean supportFileAttach) {
            this.supportFileAttach = supportFileAttach;
            return IPMsgConfiguration.Builder.this;
        }
    
        public boolean isSupportUtf8() {
            return supportUtf8;
        }
    
        public Builder setSupportUtf8(boolean supportUtf8) {
            this.supportUtf8 = supportUtf8;
            return IPMsgConfiguration.Builder.this;
        }
    
        public String getSenderName() {
            return senderName;
        }
    
        public Builder setSenderName(String senderName) {
            this.senderName = senderName;
            return IPMsgConfiguration.Builder.this;
        }
    
        public String getSenderHost() {
            return senderHost;
        }
    
        public Builder setSenderHost(String senderHost) {
            this.senderHost = senderHost;
            return IPMsgConfiguration.Builder.this;
        }
    }
    
}
