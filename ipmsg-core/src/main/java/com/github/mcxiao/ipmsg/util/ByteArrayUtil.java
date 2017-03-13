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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 */
public final class ByteArrayUtil {

    /**
     * @param pattern
     * @param src
     * @param offset
     * @return
     */
    public static boolean isMatch(byte[] pattern, byte[] src, int offset) {
        int distance = src.length - pattern.length - offset;
        if (distance < 0)
            return false;

        for (int i = 0; i < pattern.length; i++) {
            if (pattern[i] != src[i + offset])
                return false;
        }

        return true;
    }

    /**
     * @param pattern
     * @param src
     * @param offset
     * @return
     */
    public static int firstMatch(byte[] pattern, byte[] src, int offset) {
        for (int i = 0; i < src.length; i++) {
            int pos = i + offset;
            if (isMatch(pattern, src, pos)) {
                return pos;
            }
        }
        return -1;
    }

    /**
     * @param pattern
     * @param src
     * @param offset
     * @return
     */
    public static int lastMatch(byte[] pattern, byte[] src, int offset) {
        for (int i = src.length - 1; i >= 0; i--) {
            int pos = i - offset;
            if (isMatch(pattern, src, pos)) {
                return pos;
            }
        }
        return -1;
    }

    public static byte[] splitOrderByFirst(byte[] pattern, byte[] src, int offset) {
        byte[] result;
        int match = firstMatch(pattern, src, offset);

        if (match < 0) {
            result = null;
        } else {
            if (match > 0) {
                result = Arrays.copyOfRange(src, offset, match);
            } else {
                result = new byte[0];
            }
        }

        return result;
    }

    /**
     * @param pattern
     * @param src
     * @return
     */
    public static List<byte[]> split(byte[] pattern, byte[] src) {
        List<byte[]> list = new ArrayList<>();
        int startPos = 0;

        for (int i = startPos; i < src.length; ) {
            int match = firstMatch(pattern, src, i);

            if (match < 0) {
                list.add(Arrays.copyOfRange(src, startPos, src.length));
                break;
            } else {
                list.add(Arrays.copyOfRange(src, startPos, match));
                startPos = match + pattern.length;
                i = startPos;
            }
        }
        return list;
    }

}
