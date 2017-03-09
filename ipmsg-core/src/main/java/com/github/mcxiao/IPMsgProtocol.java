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

package com.github.mcxiao;

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
    public final static int IPMSG_VERSION = 0x0001;
    public final static int IPMSG_DEFAULT_PORT = 0x0979;

    /*  command  */
    public final static int IPMSG_NOOPERATION = 0x00000000;

    public final static int IPMSG_BR_ENTRY = 0x00000001;
    public final static int IPMSG_BR_EXIT = 0x00000002;
    public final static int IPMSG_ANSENTRY = 0x00000003;
    public final static int IPMSG_BR_ABSENCE = 0x00000004;
    public final static int IPMSG_BR_NOTIFY = IPMSG_BR_ABSENCE;

    public final static int IPMSG_BR_ISGETLIST = 0x00000010;
    public final static int IPMSG_OKGETLIST = 0x00000011;
    public final static int IPMSG_GETLIST = 0x00000012;
    public final static int IPMSG_ANSLIST = 0x00000013;
    public final static int IPMSG_BR_ISGETLIST2 = 0x00000018;

    public final static int IPMSG_SENDMSG = 0x00000020;
    public final static int IPMSG_RECVMSG = 0x00000021;
    public final static int IPMSG_READMSG = 0x00000030;
    public final static int IPMSG_DELMSG = 0x00000031;
    public final static int IPMSG_ANSREADMSG = 0x00000032;

    public final static int IPMSG_GETINFO = 0x00000040;
    public final static int IPMSG_SENDINFO = 0x00000041;

    public final static int IPMSG_GETABSENCEINFO = 0x00000050;
    public final static int IPMSG_SENDABSENCEINFO = 0x00000051;

    public final static int IPMSG_GETFILEDATA = 0x00000060;
    public final static int IPMSG_RELEASEFILES = 0x00000061;
    public final static int IPMSG_GETDIRFILES = 0x00000062;

    public final static int IPMSG_GETPUBKEY = 0x00000072;
    public final static int IPMSG_ANSPUBKEY = 0x00000073;

    /*  option for all command  */
    public final static int IPMSG_ABSENCEOPT = 0x00000100;
    public final static int IPMSG_SERVEROPT = 0x00000200;
    public final static int IPMSG_DIALUPOPT = 0x00010000;
    public final static int IPMSG_FILEATTACHOPT = 0x00200000;
    public final static int IPMSG_ENCRYPTOPT = 0x00400000;
    public final static int IPMSG_UTF8OPT = 0x00800000;
    public final static int IPMSG_CAPUTF8OPT = 0x01000000;
    public final static int IPMSG_ENCEXTMSGOPT = 0x04000000;
    public final static int IPMSG_CLIPBOARDOPT = 0x08000000;
    public final static int IPMSG_CAPFILEENC_OBSLT = 0x00001000;
    public final static int IPMSG_CAPFILEENCOPT = 0x00040000;

    /*  option for SENDMSG command  */
    public final static int IPMSG_SENDCHECKOPT = 0x00000100;
    public final static int IPMSG_SECRETOPT = 0x00000200;
    public final static int IPMSG_BROADCASTOPT = 0x00000400;
    public final static int IPMSG_MULTICASTOPT = 0x00000800;
    public final static int IPMSG_AUTORETOPT = 0x00002000;
    public final static int IPMSG_RETRYOPT = 0x00004000;
    public final static int IPMSG_PASSWORDOPT = 0x00008000;
    public final static int IPMSG_NOLOGOPT = 0x00020000;
    public final static int IPMSG_NOADDLISTOPT = 0x00080000;
    public final static int IPMSG_READCHECKOPT = 0x00100000;
    public final static int IPMSG_SECRETEXOPT = (IPMSG_READCHECKOPT | IPMSG_SECRETOPT);

    /*  option for GETDIRFILES/GETFILEDATA command  */
    public final static int IPMSG_ENCFILE_OBSLT = 0x00000400;
    public final static int IPMSG_ENCFILEOPT = 0x00000800;

    /*  obsolete option for send command  */
    public final static int IPMSG_NEWMULTI_OBSLT = 0x00040000;

    /* encryption/capability flags for encrypt command */
    public final static int IPMSG_RSA_512 = 0x00000001;
    public final static int IPMSG_RSA_1024 = 0x00000002;
    public final static int IPMSG_RSA_2048 = 0x00000004;
    public final static int IPMSG_RSA_4096 = 0x00000008;
    public final static int IPMSG_RC2_40 = 0x00001000;
    public final static int IPMSG_BLOWFISH_128 = 0x00020000;
    public final static int IPMSG_AES_256 = 0x00100000;
    public final static int IPMSG_PACKETNO_IV = 0x00800000;
    public final static int IPMSG_ENCODE_BASE64 = 0x01000000;
    public final static int IPMSG_SIGN_SHA1 = 0x20000000;
    public final static int IPMSG_SIGN_SHA256 = 0x40000000;

    /* compatibilty for Win beta version */
    public final static int IPMSG_RC2_40OLD = 0x00000010;    // for beta1-4 only
    public final static int IPMSG_RC2_128OLD = 0x00000040;    // for beta1-4 only
    public final static int IPMSG_BLOWFISH_128OLD = 0x00000400;    // for beta1-4 only
    public final static int IPMSG_RC2_128OBSOLETE = 0x00004000;
    public final static int IPMSG_RC2_256OBSOLETE = 0x00008000;
    public final static int IPMSG_BLOWFISH_256OBSOL = 0x00040000;
    public final static int IPMSG_AES_128OBSOLETE = 0x00080000;
    public final static int IPMSG_SIGN_MD5OBSOLETE = 0x10000000;
    public final static int IPMSG_UNAMEEXTOPT_OBSLT = 0x02000000;

    /* file types for fileattach command */
    public final static int IPMSG_FILE_REGULAR = 0x00000001;
    public final static int IPMSG_FILE_DIR = 0x00000002;
    public final static int IPMSG_FILE_RETPARENT = 0x00000003;    // return parent directory
    public final static int IPMSG_FILE_SYMLINK = 0x00000004;
    public final static int IPMSG_FILE_CDEV = 0x00000005;    // for UNIX
    public final static int IPMSG_FILE_BDEV = 0x00000006;    // for UNIX
    public final static int IPMSG_FILE_FIFO = 0x00000007;    // for UNIX
    public final static int IPMSG_FILE_RESFORK = 0x00000010;    // for Mac
    public final static int IPMSG_FILE_CLIPBOARD = 0x00000020;    // for Windows Clipboard

    /* file attribute options for fileattach command */
    public final static int IPMSG_FILE_RONLYOPT = 0x00000100;
    public final static int IPMSG_FILE_HIDDENOPT = 0x00001000;
    public final static int IPMSG_FILE_EXHIDDENOPT = 0x00002000;	/* for MacOS X */
    public final static int IPMSG_FILE_ARCHIVEOPT = 0x00004000;
    public final static int IPMSG_FILE_SYSTEMOPT = 0x00008000;

    /* extend attribute types for fileattach command */
    public final static int IPMSG_FILE_UID = 0x00000001;
    public final static int IPMSG_FILE_USERNAME = 0x00000002;    // uid by string
    public final static int IPMSG_FILE_GID = 0x00000003;
    public final static int IPMSG_FILE_GROUPNAME = 0x00000004;    // gid by string
    public final static int IPMSG_FILE_CLIPBOARDPOS = 0x00000008;    //
    public final static int IPMSG_FILE_PERM = 0x00000010;    // for UNIX
    public final static int IPMSG_FILE_MAJORNO = 0x00000011;    // for UNIX devfile
    public final static int IPMSG_FILE_MINORNO = 0x00000012;    // for UNIX devfile
    public final static int IPMSG_FILE_CTIME = 0x00000013;    // for UNIX
    public final static int IPMSG_FILE_MTIME = 0x00000014;
    public final static int IPMSG_FILE_ATIME = 0x00000015;
    public final static int IPMSG_FILE_CREATETIME = 0x00000016;
    public final static int IPMSG_FILE_CREATOR = 0x00000020;    // for Mac
    public final static int IPMSG_FILE_FILETYPE = 0x00000021;    // for Mac
    public final static int IPMSG_FILE_FINDERINFO = 0x00000022;    // for Mac
    public final static int IPMSG_FILE_ACL = 0x00000030;
    public final static int IPMSG_FILE_ALIASFNAME = 0x00000040;    // alias fname

    public final static String SEPARATOR = ":";
    public final static byte SEPARATOR_BYTE = ':';
    public final static String EOL = "\n";
    public final static byte EOL_BYTE = '\n';
    public final static int PORT = 2425;

    public final static int IPMSG_MESSAGE_FORMAT_ITEM_COUNT = 5;
}
