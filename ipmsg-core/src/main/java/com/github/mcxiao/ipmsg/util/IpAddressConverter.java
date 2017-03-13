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

package com.github.mcxiao.ipmsg.util;

import java.util.StringTokenizer;

public final class IpAddressConverter {


    /**
     * Convert a TCP/IP address string into a byte array
     *
     * @param addr String
     * @return byte[]
     */
    public static byte[] toBytesIp(String addr) {

        // Convert the TCP/IP address string to an integer value
        int ipInt = parseNumericAddress(addr);
        if (ipInt == 0)
            return null;

        // Convert to bytes
        byte[] ipByts = new byte[4];

        ipByts[3] = (byte) (ipInt & 0xFF);
        ipByts[2] = (byte) ((ipInt >> 8) & 0xFF);
        ipByts[1] = (byte) ((ipInt >> 16) & 0xFF);
        ipByts[0] = (byte) ((ipInt >> 24) & 0xFF);

        return ipByts;
    }

    /**
     * Check if the specified address is a valid numeric TCP/IP address and return as an integer value
     *
     * @param ipAddress String
     * @return int
     */
    private static int parseNumericAddress(String ipAddress) {

        //  Check if the string is valid
        if (ipAddress == null || ipAddress.length() < 7 || ipAddress.length() > 15)
            return 0;

        //  Check the address string, should be n.n.n.n format
        StringTokenizer token = new StringTokenizer(ipAddress, ".");
        if (token.countTokens() != 4)
            return 0;

        int ipInt = 0;

        while (token.hasMoreTokens()) {

            //  Get the current token and convert to an integer value
            String ipNum = token.nextToken();

            try {

                //  Validate the current address part
                int ipVal = Integer.valueOf(ipNum);
                if (ipVal < 0 || ipVal > 255)
                    return 0;

                //  Add to the integer address
                ipInt = (ipInt << 8) + ipVal;
            } catch (NumberFormatException ex) {
                return 0;
            }
        }

        return ipInt;
    }

}
