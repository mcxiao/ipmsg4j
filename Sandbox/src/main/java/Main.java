/*
 * Copyright [2016] [MC.Xiao]
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

import java.util.Arrays;

/**
 */
public class Main {

    public static void main(String[] args) {
//        byte[] array = ByteBuffer.allocate(4).putInt(1).array();
//        System.out.println(array.length);
//        for (byte b :
//                array) {
//            System.out.println(b);
//        }

//        String formatter = "%d:%d:%s:%s:%d:";
//        String bufString = String.format(formatter, 1, 1000, "senderName", "senderHost", 1);
//        byte[] buf = bufString.getBytes();
//
//        System.out.println(buf.length);
//        for (byte b :
//                buf) {
//            System.out.print(b + ",");
//        }
//        System.out.println();
//        byte[] non = {0, 1};
////        byte[] array = ByteBuffer.wrap(buf).put(non).array();
////        System.out.println(array.length);
//
//        byte[] bytes = new byte[buf.length + non.length];
//
//        System.arraycopy(buf, 0, bytes, 0, buf.length);
//        System.arraycopy(non, 0, bytes, buf.length, non.length);
//
//        System.out.println(new String(bytes));

        byte[] bytes = "\n".getBytes();
        byte[] bytes1 = "\\n".getBytes();
        byte b = new Integer(10001).byteValue();
        byte[] bytes2 = String.valueOf(10001).getBytes();
        int s = Integer.valueOf(new String(bytes2));
        System.out.println();

        System.out.println(isMatch(new byte[]{0x1, 0x2}, new byte[]{0x33, 0x34, 0x1, 0x2}, 0));
        byte[] bytes3 = {0x0, 0x1, 0x2};
        System.out.println(Arrays.copyOfRange(bytes3, 0, 0).length);

        String[] split = ":fffdsa:".split(":");
        for (String s1 : split) {
            System.out.println("'" + s1 + "'");
        }

        "".getBytes();
    }



    public static boolean isMatch(byte[] pattern, byte[] array, int offset) {
        int distance = array.length - pattern.length - offset;
        for (int i = 0; i < distance; i++) {
            if (pattern[i] != array[i + offset])
                return false;
        }

        return true;
    }

    void testBufferCopy(byte[] bytesA, byte[] bytesB) {

        byte[] bytes = new byte[bytesA.length + bytesB.length];

        System.arraycopy(bytesA, 0, bytes, 0, bytesA.length);
        System.arraycopy(bytesB, 0, bytes, bytesA.length, bytesB.length);

    }

}
