package com.github.mcxiao.ipmsg.dev;

import com.github.mcxiao.ipmsg.IPMsgProtocol;

public class MyClass {
    
    public static final String PREFIX = "public static final int ";
    public static final int PREFIX_LENGTH = PREFIX.length();
    public static final String SUFFIX = " = ";
    public static final int SUFFIX_LENGTH = SUFFIX.length();
    
    public static void main(String[] args) {
        String source = "\n" +
                "    /*  command  */\n" +
                "    // Presence\n" +
                "    public static final int IPMSG_NOOPERATION = 0x00000000;\n" +
                "    \n" +
                "    public static final int IPMSG_BR_ENTRY = 0x00000001;\n" +
                "    public static final int IPMSG_BR_EXIT = 0x00000002;\n" +
                "    public static final int IPMSG_ANSENTRY = 0x00000003;\n" +
                "    public static final int IPMSG_BR_ABSENCE = 0x00000004;\n" +
                "//    public static final int IPMSG_BR_NOTIFY = IPMSG_BR_ABSENCE;\n" +
                "    \n" +
                "    public static final int IPMSG_BR_ISGETLIST = 0x00000010;\n" +
                "    public static final int IPMSG_OKGETLIST = 0x00000011;\n" +
                "    public static final int IPMSG_GETLIST = 0x00000012;\n" +
                "    public static final int IPMSG_ANSLIST = 0x00000013;\n" +
                "    public static final int IPMSG_BR_ISGETLIST2 = 0x00000018;\n" +
                "    \n" +
                "    // Message\n" +
                "    public static final int IPMSG_SENDMSG = 0x00000020;\n" +
                "    public static final int IPMSG_RECVMSG = 0x00000021;\n" +
                "    public static final int IPMSG_READMSG = 0x00000030;\n" +
                "    public static final int IPMSG_DELMSG = 0x00000031;\n" +
                "    public static final int IPMSG_ANSREADMSG = 0x00000032;\n" +
                "    \n" +
                "    // IQ\n" +
                "    public static final int IPMSG_GETINFO = 0x00000040;\n" +
                "    public static final int IPMSG_SENDINFO = 0x00000041;\n" +
                "    \n" +
                "    public static final int IPMSG_GETABSENCEINFO = 0x00000050;\n" +
                "    public static final int IPMSG_SENDABSENCEINFO = 0x00000051;\n" +
                "    \n" +
                "    public static final int IPMSG_GETFILEDATA = 0x00000060;\n" +
                "    public static final int IPMSG_RELEASEFILES = 0x00000061;\n" +
                "    public static final int IPMSG_GETDIRFILES = 0x00000062;\n" +
                "    \n" +
                "    public static final int IPMSG_GETPUBKEY = 0x00000072;\n" +
                "    public static final int IPMSG_ANSPUBKEY = 0x00000073;\n" +
                "    \n" +
                "    /*  option for all command  */\n" +
                "    public static final int IPMSG_ABSENCEOPT = 0x00000100;\n" +
                "    public static final int IPMSG_SERVEROPT = 0x00000200;\n" +
                "    public static final int IPMSG_DIALUPOPT = 0x00010000;\n" +
                "    public static final int IPMSG_FILEATTACHOPT = 0x00200000;\n" +
                "    public static final int IPMSG_ENCRYPTOPT = 0x00400000;\n" +
                "    public static final int IPMSG_UTF8OPT = 0x00800000;\n" +
                "    public static final int IPMSG_CAPUTF8OPT = 0x01000000;\n" +
                "    public static final int IPMSG_ENCEXTMSGOPT = 0x04000000;\n" +
                "    public static final int IPMSG_CLIPBOARDOPT = 0x08000000;\n" +
                "    public static final int IPMSG_CAPFILEENC_OBSLT = 0x00001000;\n" +
                "    public static final int IPMSG_CAPFILEENCOPT = 0x00040000;\n" +
                "    \n" +
                "    /*  option for SENDMSG command  */\n" +
                "    public static final int IPMSG_SENDCHECKOPT = 0x00000100;\n" +
                "    public static final int IPMSG_SECRETOPT = 0x00000200;\n" +
                "    public static final int IPMSG_BROADCASTOPT = 0x00000400;\n" +
                "    public static final int IPMSG_MULTICASTOPT = 0x00000800;\n" +
                "    public static final int IPMSG_AUTORETOPT = 0x00002000;\n" +
                "    public static final int IPMSG_RETRYOPT = 0x00004000;\n" +
                "    public static final int IPMSG_PASSWORDOPT = 0x00008000;\n" +
                "    public static final int IPMSG_NOLOGOPT = 0x00020000;\n" +
                "    public static final int IPMSG_NOADDLISTOPT = 0x00080000;\n" +
                "    public static final int IPMSG_READCHECKOPT = 0x00100000;\n" +
                "    public static final int IPMSG_SECRETEXOPT = (IPMSG_READCHECKOPT | IPMSG_SECRETOPT);\n" +
                "    \n" +
                "    /*  option for GETDIRFILES/GETFILEDATA command  */\n" +
                "    public static final int IPMSG_ENCFILE_OBSLT = 0x00000400;\n" +
                "    public static final int IPMSG_ENCFILEOPT = 0x00000800;\n" +
                "    \n" +
                "    /*  obsolete option for send command  */\n" +
                "    public static final int IPMSG_NEWMULTI_OBSLT = 0x00040000;\n" +
                "    \n" +
                "    /* encryption/capability flags for encrypt command */\n" +
                "    public static final int IPMSG_RSA_512 = 0x00000001;\n" +
                "    public static final int IPMSG_RSA_1024 = 0x00000002;\n" +
                "    public static final int IPMSG_RSA_2048 = 0x00000004;\n" +
                "    public static final int IPMSG_RSA_4096 = 0x00000008;\n" +
                "    public static final int IPMSG_RC2_40 = 0x00001000;\n" +
                "    public static final int IPMSG_BLOWFISH_128 = 0x00020000;\n" +
                "    public static final int IPMSG_AES_256 = 0x00100000;\n" +
                "    public static final int IPMSG_PACKETNO_IV = 0x00800000;\n" +
                "    public static final int IPMSG_ENCODE_BASE64 = 0x01000000;\n" +
                "    public static final int IPMSG_SIGN_SHA1 = 0x20000000;\n" +
                "    public static final int IPMSG_SIGN_SHA256 = 0x40000000;\n" +
                "    \n" +
                "    /* compatibilty for Win beta version */\n" +
                "    public static final int IPMSG_RC2_40OLD = 0x00000010;    // for beta1-4 only\n" +
                "    public static final int IPMSG_RC2_128OLD = 0x00000040;    // for beta1-4 only\n" +
                "    public static final int IPMSG_BLOWFISH_128OLD = 0x00000400;    // for beta1-4 only\n" +
                "    public static final int IPMSG_RC2_128OBSOLETE = 0x00004000;\n" +
                "    public static final int IPMSG_RC2_256OBSOLETE = 0x00008000;\n" +
                "    public static final int IPMSG_BLOWFISH_256OBSOL = 0x00040000;\n" +
                "    public static final int IPMSG_AES_128OBSOLETE = 0x00080000;\n" +
                "    public static final int IPMSG_SIGN_MD5OBSOLETE = 0x10000000;\n" +
                "    public static final int IPMSG_UNAMEEXTOPT_OBSLT = 0x02000000;\n" +
                "    \n" +
                "    /* file types for fileattach command */\n" +
                "    public static final int IPMSG_FILE_REGULAR = 0x00000001;\n" +
                "    public static final int IPMSG_FILE_DIR = 0x00000002;\n" +
                "    public static final int IPMSG_FILE_RETPARENT = 0x00000003;    // return parent directory\n" +
                "    public static final int IPMSG_FILE_SYMLINK = 0x00000004;\n" +
                "    public static final int IPMSG_FILE_CDEV = 0x00000005;    // for UNIX\n" +
                "    public static final int IPMSG_FILE_BDEV = 0x00000006;    // for UNIX\n" +
                "    public static final int IPMSG_FILE_FIFO = 0x00000007;    // for UNIX\n" +
                "    public static final int IPMSG_FILE_RESFORK = 0x00000010;    // for Mac\n" +
                "    public static final int IPMSG_FILE_CLIPBOARD = 0x00000020;    // for Windows Clipboard\n" +
                "    \n" +
                "    /* file attribute options for fileattach command */\n" +
                "    public static final int IPMSG_FILE_RONLYOPT = 0x00000100;\n" +
                "    public static final int IPMSG_FILE_HIDDENOPT = 0x00001000;\n" +
                "    public static final int IPMSG_FILE_EXHIDDENOPT = 0x00002000;\t/* for MacOS X */\n" +
                "    public static final int IPMSG_FILE_ARCHIVEOPT = 0x00004000;\n" +
                "    public static final int IPMSG_FILE_SYSTEMOPT = 0x00008000;\n" +
                "    \n" +
                "    /* extend attribute types for fileattach command */\n" +
                "    public static final int IPMSG_FILE_UID = 0x00000001;\n" +
                "    public static final int IPMSG_FILE_USERNAME = 0x00000002;    // uid by string\n" +
                "    public static final int IPMSG_FILE_GID = 0x00000003;\n" +
                "    public static final int IPMSG_FILE_GROUPNAME = 0x00000004;    // gid by string\n" +
                "    public static final int IPMSG_FILE_CLIPBOARDPOS = 0x00000008;    //\n" +
                "    public static final int IPMSG_FILE_PERM = 0x00000010;    // for UNIX\n" +
                "    public static final int IPMSG_FILE_MAJORNO = 0x00000011;    // for UNIX devfile\n" +
                "    public static final int IPMSG_FILE_MINORNO = 0x00000012;    // for UNIX devfile\n" +
                "    public static final int IPMSG_FILE_CTIME = 0x00000013;    // for UNIX\n" +
                "    public static final int IPMSG_FILE_MTIME = 0x00000014;\n" +
                "    public static final int IPMSG_FILE_ATIME = 0x00000015;\n" +
                "    public static final int IPMSG_FILE_CREATETIME = 0x00000016;\n" +
                "    public static final int IPMSG_FILE_CREATOR = 0x00000020;    // for Mac\n" +
                "    public static final int IPMSG_FILE_FILETYPE = 0x00000021;    // for Mac\n" +
                "    public static final int IPMSG_FILE_FINDERINFO = 0x00000022;    // for Mac\n" +
                "    public static final int IPMSG_FILE_ACL = 0x00000030;\n" +
                "    public static final int IPMSG_FILE_ALIASFNAME = 0x00000040;    // alias fname\n" +
                "    ";
        
        StringBuilder builder = new StringBuilder();
        int offset = 0;
        while (offset < source.length()) {
            int i = source.indexOf(PREFIX, offset);
            if (i < 0) {
                break;
            }
    
            int j = source.indexOf(SUFFIX, i + PREFIX_LENGTH);
            if (j < 0) {
                break;
            }
    
            int k = source.indexOf(";", j + SUFFIX_LENGTH);
            if (k < 0) {
                break;
            }
    
            String name = source.substring(i + PREFIX_LENGTH, j);
            String value = source.substring(j + SUFFIX_LENGTH, k);
            
//            builder.append("public static final CommandMeta ");
//            builder.append(name);
//            builder.append(" = new CommandMeta(\"");
//            builder.append(name);
//            builder.append("\", IPMsgProtocol.");
//            builder.append(name);
//            builder.append(");\n");
            
//            builder.append("addOrUpdateHolder(map, IPMsgProtocol.");
//            builder.append(name);
//            builder.append(", \"");
//            builder.append(name);
//            builder.append("\");\n");
            
//
//            builder.append(name);
//            builder.append(";");
//            builder.append(name);
//            builder.append("=");
//            builder.append(value);
//            builder.append("\n");
            
            offset = k;
        }
    
        System.out.println(builder.toString());
    }
    
}
