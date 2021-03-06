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
 * IPMsg protocol(Draft-10) variable definition.
 */
public final class IPMsgProtocol {

    /*  IP Messenger Communication Protocol version 3.0 define  */
    /*  macro  */
    public static int GET_MODE(int command) {
        return command & 0x000000ff;
    }
    public static int GET_OPT(int command) {
        return command & 0xffffff00;
    }

    public static boolean ACCEPT_MODE(int command, int mode) {
        return (command & mode) != 0;
    }

    public static boolean ACCEPT_OPT(int command, int opt) {
        return (command & opt) != 0;
    }

    /*  header  */
    public static final int IPMSG_VERSION = 0x0001;
    public static final int IPMSG_DEFAULT_PORT = 0x0979;

    /*  command  */
    // Presence
    public static final int IPMSG_NOOPERATION = 0x00000000;

    public static final int IPMSG_BR_ENTRY = 0x00000001;
    public static final int IPMSG_BR_EXIT = 0x00000002;
    public static final int IPMSG_ANSENTRY = 0x00000003;
    public static final int IPMSG_BR_ABSENCE = 0x00000004;
//    public static final int IPMSG_BR_NOTIFY = IPMSG_BR_ABSENCE;

    public static final int IPMSG_BR_ISGETLIST = 0x00000010;
    public static final int IPMSG_OKGETLIST = 0x00000011;
    public static final int IPMSG_GETLIST = 0x00000012;
    public static final int IPMSG_ANSLIST = 0x00000013;
    public static final int IPMSG_BR_ISGETLIST2 = 0x00000018;

    // Message
    public static final int IPMSG_SENDMSG = 0x00000020;
    public static final int IPMSG_RECVMSG = 0x00000021;
    public static final int IPMSG_READMSG = 0x00000030;
    public static final int IPMSG_DELMSG = 0x00000031;
    public static final int IPMSG_ANSREADMSG = 0x00000032;

    // IQ
    public static final int IPMSG_GETINFO = 0x00000040;
    public static final int IPMSG_SENDINFO = 0x00000041;

    public static final int IPMSG_GETABSENCEINFO = 0x00000050;
    public static final int IPMSG_SENDABSENCEINFO = 0x00000051;

    public static final int IPMSG_GETFILEDATA = 0x00000060;
    public static final int IPMSG_RELEASEFILES = 0x00000061;
    public static final int IPMSG_GETDIRFILES = 0x00000062;

    public static final int IPMSG_GETPUBKEY = 0x00000072;
    public static final int IPMSG_ANSPUBKEY = 0x00000073;

    /*  option for all command  */
    public static final int IPMSG_ABSENCEOPT = 0x00000100;
    public static final int IPMSG_SERVEROPT = 0x00000200;
    public static final int IPMSG_DIALUPOPT = 0x00010000;
    public static final int IPMSG_FILEATTACHOPT = 0x00200000;
    public static final int IPMSG_ENCRYPTOPT = 0x00400000;
    public static final int IPMSG_UTF8OPT = 0x00800000;
    public static final int IPMSG_CAPUTF8OPT = 0x01000000;
    public static final int IPMSG_ENCEXTMSGOPT = 0x04000000;
    public static final int IPMSG_CLIPBOARDOPT = 0x08000000;
    public static final int IPMSG_CAPFILEENC_OBSLT = 0x00001000;
    public static final int IPMSG_CAPFILEENCOPT = 0x00040000;

    /*  option for SENDMSG command  */
    public static final int IPMSG_SENDCHECKOPT = 0x00000100;
    public static final int IPMSG_SECRETOPT = 0x00000200;
    public static final int IPMSG_BROADCASTOPT = 0x00000400;
    public static final int IPMSG_MULTICASTOPT = 0x00000800;
    public static final int IPMSG_AUTORETOPT = 0x00002000;
    public static final int IPMSG_RETRYOPT = 0x00004000;
    public static final int IPMSG_PASSWORDOPT = 0x00008000;
    public static final int IPMSG_NOLOGOPT = 0x00020000;
    public static final int IPMSG_NOADDLISTOPT = 0x00080000;
    public static final int IPMSG_READCHECKOPT = 0x00100000;
    public static final int IPMSG_SECRETEXOPT = (IPMSG_READCHECKOPT | IPMSG_SECRETOPT);

    /*  option for GETDIRFILES/GETFILEDATA command  */
    public static final int IPMSG_ENCFILE_OBSLT = 0x00000400;
    public static final int IPMSG_ENCFILEOPT = 0x00000800;

    /*  obsolete option for send command  */
    public static final int IPMSG_NEWMULTI_OBSLT = 0x00040000;

    /* encryption/capability flags for encrypt command */
    public static final int IPMSG_RSA_512 = 0x00000001;
    public static final int IPMSG_RSA_1024 = 0x00000002;
    public static final int IPMSG_RSA_2048 = 0x00000004;
    public static final int IPMSG_RSA_4096 = 0x00000008;
    public static final int IPMSG_RC2_40 = 0x00001000;
    public static final int IPMSG_BLOWFISH_128 = 0x00020000;
    public static final int IPMSG_AES_256 = 0x00100000;
    public static final int IPMSG_PACKETNO_IV = 0x00800000;
    public static final int IPMSG_ENCODE_BASE64 = 0x01000000;
    public static final int IPMSG_SIGN_SHA1 = 0x20000000;
    public static final int IPMSG_SIGN_SHA256 = 0x40000000;

    /* compatibilty for Win beta version */
    public static final int IPMSG_RC2_40OLD = 0x00000010;    // for beta1-4 only
    public static final int IPMSG_RC2_128OLD = 0x00000040;    // for beta1-4 only
    public static final int IPMSG_BLOWFISH_128OLD = 0x00000400;    // for beta1-4 only
    public static final int IPMSG_RC2_128OBSOLETE = 0x00004000;
    public static final int IPMSG_RC2_256OBSOLETE = 0x00008000;
    public static final int IPMSG_BLOWFISH_256OBSOL = 0x00040000;
    public static final int IPMSG_AES_128OBSOLETE = 0x00080000;
    public static final int IPMSG_SIGN_MD5OBSOLETE = 0x10000000;
    public static final int IPMSG_UNAMEEXTOPT_OBSLT = 0x02000000;

    /* file types for fileattach command */
    public static final int IPMSG_FILE_REGULAR = 0x00000001;
    public static final int IPMSG_FILE_DIR = 0x00000002;
    public static final int IPMSG_FILE_RETPARENT = 0x00000003;    // return parent directory
    public static final int IPMSG_FILE_SYMLINK = 0x00000004;
    public static final int IPMSG_FILE_CDEV = 0x00000005;    // for UNIX
    public static final int IPMSG_FILE_BDEV = 0x00000006;    // for UNIX
    public static final int IPMSG_FILE_FIFO = 0x00000007;    // for UNIX
    public static final int IPMSG_FILE_RESFORK = 0x00000010;    // for Mac
    public static final int IPMSG_FILE_CLIPBOARD = 0x00000020;    // for Windows Clipboard

    /* file attribute options for fileattach command */
    public static final int IPMSG_FILE_RONLYOPT = 0x00000100;
    public static final int IPMSG_FILE_HIDDENOPT = 0x00001000;
    public static final int IPMSG_FILE_EXHIDDENOPT = 0x00002000;	/* for MacOS X */
    public static final int IPMSG_FILE_ARCHIVEOPT = 0x00004000;
    public static final int IPMSG_FILE_SYSTEMOPT = 0x00008000;

    /* extend attribute types for fileattach command */
    public static final int IPMSG_FILE_UID = 0x00000001;
    public static final int IPMSG_FILE_USERNAME = 0x00000002;    // uid by string
    public static final int IPMSG_FILE_GID = 0x00000003;
    public static final int IPMSG_FILE_GROUPNAME = 0x00000004;    // gid by string
    public static final int IPMSG_FILE_CLIPBOARDPOS = 0x00000008;    //
    public static final int IPMSG_FILE_PERM = 0x00000010;    // for UNIX
    public static final int IPMSG_FILE_MAJORNO = 0x00000011;    // for UNIX devfile
    public static final int IPMSG_FILE_MINORNO = 0x00000012;    // for UNIX devfile
    public static final int IPMSG_FILE_CTIME = 0x00000013;    // for UNIX
    public static final int IPMSG_FILE_MTIME = 0x00000014;
    public static final int IPMSG_FILE_ATIME = 0x00000015;
    public static final int IPMSG_FILE_CREATETIME = 0x00000016;
    public static final int IPMSG_FILE_CREATOR = 0x00000020;    // for Mac
    public static final int IPMSG_FILE_FILETYPE = 0x00000021;    // for Mac
    public static final int IPMSG_FILE_FINDERINFO = 0x00000022;    // for Mac
    public static final int IPMSG_FILE_ACL = 0x00000030;
    public static final int IPMSG_FILE_ALIASFNAME = 0x00000040;    // alias fname

    public static final String SEPARATOR = ":";
    public static final byte SEPARATOR_BYTE = ':';
    public static final String END = "\0";
    public static final byte END_BYTE = '\0';
    public static final String EOL = "\n";
    public static final byte EOL_BYTE = '\n';
    public static final int PORT = 2425;

    public static final int MAX_PACKET_ITEM_COUNT = 6;
    public static final int ITEM_VERSION_POSITION = 0;
    public static final int ITEM_PACKET_NO_POSITION = 1;
    public static final int ITEM_SENDER_NAME_POSITION = 2;
    public static final int ITEM_SENDER_HOST_POSITION = 3;
    public static final int ITEM_COMMAND_POSITION = 4;
    public static final int ITEM_EXTENSION_POSITION = 5;
    
}
