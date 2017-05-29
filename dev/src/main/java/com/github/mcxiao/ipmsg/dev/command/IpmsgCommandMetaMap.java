package com.github.mcxiao.ipmsg.dev.command;

import com.github.mcxiao.ipmsg.IPMsgProtocol;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */

public class IpmsgCommandMetaMap {
    
    public static final Map<Integer, CommandMeta> metaMap;
    
    static {
        CommandHolder IPMSG_NOOPERATION = new CommandHolder("IPMSG_NOOPERATION", IPMsgProtocol.IPMSG_NOOPERATION);
        CommandHolder IPMSG_BR_ENTRY = new CommandHolder("IPMSG_BR_ENTRY", IPMsgProtocol.IPMSG_BR_ENTRY);
        CommandHolder IPMSG_BR_EXIT = new CommandHolder("IPMSG_BR_EXIT", IPMsgProtocol.IPMSG_BR_EXIT);
        CommandHolder IPMSG_ANSENTRY = new CommandHolder("IPMSG_ANSENTRY", IPMsgProtocol.IPMSG_ANSENTRY);
        CommandHolder IPMSG_BR_ABSENCE = new CommandHolder("IPMSG_BR_ABSENCE", IPMsgProtocol.IPMSG_BR_ABSENCE);
        //    CommandHolder IPMSG_BR_NOTIFY = new CommandHolder("IPMSG_BR_NOTIFY", IPMsgProtocol.IPMSG_BR_NOTIFY);
        CommandHolder IPMSG_BR_ISGETLIST = new CommandHolder("IPMSG_BR_ISGETLIST", IPMsgProtocol.IPMSG_BR_ISGETLIST);
        CommandHolder IPMSG_OKGETLIST = new CommandHolder("IPMSG_OKGETLIST", IPMsgProtocol.IPMSG_OKGETLIST);
        CommandHolder IPMSG_GETLIST = new CommandHolder("IPMSG_GETLIST", IPMsgProtocol.IPMSG_GETLIST);
        CommandHolder IPMSG_ANSLIST = new CommandHolder("IPMSG_ANSLIST", IPMsgProtocol.IPMSG_ANSLIST);
        CommandHolder IPMSG_BR_ISGETLIST2 = new CommandHolder("IPMSG_BR_ISGETLIST2", IPMsgProtocol.IPMSG_BR_ISGETLIST2);
        CommandHolder IPMSG_SENDMSG = new CommandHolder("IPMSG_SENDMSG", IPMsgProtocol.IPMSG_SENDMSG);
        CommandHolder IPMSG_RECVMSG = new CommandHolder("IPMSG_RECVMSG", IPMsgProtocol.IPMSG_RECVMSG);
        CommandHolder IPMSG_READMSG = new CommandHolder("IPMSG_READMSG", IPMsgProtocol.IPMSG_READMSG);
        CommandHolder IPMSG_DELMSG = new CommandHolder("IPMSG_DELMSG", IPMsgProtocol.IPMSG_DELMSG);
        CommandHolder IPMSG_ANSREADMSG = new CommandHolder("IPMSG_ANSREADMSG", IPMsgProtocol.IPMSG_ANSREADMSG);
        CommandHolder IPMSG_GETINFO = new CommandHolder("IPMSG_GETINFO", IPMsgProtocol.IPMSG_GETINFO);
        CommandHolder IPMSG_SENDINFO = new CommandHolder("IPMSG_SENDINFO", IPMsgProtocol.IPMSG_SENDINFO);
        CommandHolder IPMSG_GETABSENCEINFO = new CommandHolder("IPMSG_GETABSENCEINFO", IPMsgProtocol.IPMSG_GETABSENCEINFO);
        CommandHolder IPMSG_SENDABSENCEINFO = new CommandHolder("IPMSG_SENDABSENCEINFO", IPMsgProtocol.IPMSG_SENDABSENCEINFO);
        CommandHolder IPMSG_GETFILEDATA = new CommandHolder("IPMSG_GETFILEDATA", IPMsgProtocol.IPMSG_GETFILEDATA);
        CommandHolder IPMSG_RELEASEFILES = new CommandHolder("IPMSG_RELEASEFILES", IPMsgProtocol.IPMSG_RELEASEFILES);
        CommandHolder IPMSG_GETDIRFILES = new CommandHolder("IPMSG_GETDIRFILES", IPMsgProtocol.IPMSG_GETDIRFILES);
        CommandHolder IPMSG_GETPUBKEY = new CommandHolder("IPMSG_GETPUBKEY", IPMsgProtocol.IPMSG_GETPUBKEY);
        CommandHolder IPMSG_ANSPUBKEY = new CommandHolder("IPMSG_ANSPUBKEY", IPMsgProtocol.IPMSG_ANSPUBKEY);
        CommandHolder IPMSG_ABSENCEOPT = new CommandHolder("IPMSG_ABSENCEOPT", IPMsgProtocol.IPMSG_ABSENCEOPT);
        CommandHolder IPMSG_SERVEROPT = new CommandHolder("IPMSG_SERVEROPT", IPMsgProtocol.IPMSG_SERVEROPT);
        CommandHolder IPMSG_DIALUPOPT = new CommandHolder("IPMSG_DIALUPOPT", IPMsgProtocol.IPMSG_DIALUPOPT);
        CommandHolder IPMSG_FILEATTACHOPT = new CommandHolder("IPMSG_FILEATTACHOPT", IPMsgProtocol.IPMSG_FILEATTACHOPT);
        CommandHolder IPMSG_ENCRYPTOPT = new CommandHolder("IPMSG_ENCRYPTOPT", IPMsgProtocol.IPMSG_ENCRYPTOPT);
        CommandHolder IPMSG_UTF8OPT = new CommandHolder("IPMSG_UTF8OPT", IPMsgProtocol.IPMSG_UTF8OPT);
        CommandHolder IPMSG_CAPUTF8OPT = new CommandHolder("IPMSG_CAPUTF8OPT", IPMsgProtocol.IPMSG_CAPUTF8OPT);
        CommandHolder IPMSG_ENCEXTMSGOPT = new CommandHolder("IPMSG_ENCEXTMSGOPT", IPMsgProtocol.IPMSG_ENCEXTMSGOPT);
        CommandHolder IPMSG_CLIPBOARDOPT = new CommandHolder("IPMSG_CLIPBOARDOPT", IPMsgProtocol.IPMSG_CLIPBOARDOPT);
        CommandHolder IPMSG_CAPFILEENC_OBSLT = new CommandHolder("IPMSG_CAPFILEENC_OBSLT", IPMsgProtocol.IPMSG_CAPFILEENC_OBSLT);
        CommandHolder IPMSG_CAPFILEENCOPT = new CommandHolder("IPMSG_CAPFILEENCOPT", IPMsgProtocol.IPMSG_CAPFILEENCOPT);
        CommandHolder IPMSG_SENDCHECKOPT = new CommandHolder("IPMSG_SENDCHECKOPT", IPMsgProtocol.IPMSG_SENDCHECKOPT);
        CommandHolder IPMSG_SECRETOPT = new CommandHolder("IPMSG_SECRETOPT", IPMsgProtocol.IPMSG_SECRETOPT);
        CommandHolder IPMSG_BROADCASTOPT = new CommandHolder("IPMSG_BROADCASTOPT", IPMsgProtocol.IPMSG_BROADCASTOPT);
        CommandHolder IPMSG_MULTICASTOPT = new CommandHolder("IPMSG_MULTICASTOPT", IPMsgProtocol.IPMSG_MULTICASTOPT);
        CommandHolder IPMSG_AUTORETOPT = new CommandHolder("IPMSG_AUTORETOPT", IPMsgProtocol.IPMSG_AUTORETOPT);
        CommandHolder IPMSG_RETRYOPT = new CommandHolder("IPMSG_RETRYOPT", IPMsgProtocol.IPMSG_RETRYOPT);
        CommandHolder IPMSG_PASSWORDOPT = new CommandHolder("IPMSG_PASSWORDOPT", IPMsgProtocol.IPMSG_PASSWORDOPT);
        CommandHolder IPMSG_NOLOGOPT = new CommandHolder("IPMSG_NOLOGOPT", IPMsgProtocol.IPMSG_NOLOGOPT);
        CommandHolder IPMSG_NOADDLISTOPT = new CommandHolder("IPMSG_NOADDLISTOPT", IPMsgProtocol.IPMSG_NOADDLISTOPT);
        CommandHolder IPMSG_READCHECKOPT = new CommandHolder("IPMSG_READCHECKOPT", IPMsgProtocol.IPMSG_READCHECKOPT);
        CommandHolder IPMSG_SECRETEXOPT = new CommandHolder("IPMSG_SECRETEXOPT", IPMsgProtocol.IPMSG_SECRETEXOPT);
        CommandHolder IPMSG_ENCFILE_OBSLT = new CommandHolder("IPMSG_ENCFILE_OBSLT", IPMsgProtocol.IPMSG_ENCFILE_OBSLT);
        CommandHolder IPMSG_ENCFILEOPT = new CommandHolder("IPMSG_ENCFILEOPT", IPMsgProtocol.IPMSG_ENCFILEOPT);
        CommandHolder IPMSG_NEWMULTI_OBSLT = new CommandHolder("IPMSG_NEWMULTI_OBSLT", IPMsgProtocol.IPMSG_NEWMULTI_OBSLT);
        CommandHolder IPMSG_RSA_512 = new CommandHolder("IPMSG_RSA_512", IPMsgProtocol.IPMSG_RSA_512);
        CommandHolder IPMSG_RSA_1024 = new CommandHolder("IPMSG_RSA_1024", IPMsgProtocol.IPMSG_RSA_1024);
        CommandHolder IPMSG_RSA_2048 = new CommandHolder("IPMSG_RSA_2048", IPMsgProtocol.IPMSG_RSA_2048);
        CommandHolder IPMSG_RSA_4096 = new CommandHolder("IPMSG_RSA_4096", IPMsgProtocol.IPMSG_RSA_4096);
        CommandHolder IPMSG_RC2_40 = new CommandHolder("IPMSG_RC2_40", IPMsgProtocol.IPMSG_RC2_40);
        CommandHolder IPMSG_BLOWFISH_128 = new CommandHolder("IPMSG_BLOWFISH_128", IPMsgProtocol.IPMSG_BLOWFISH_128);
        CommandHolder IPMSG_AES_256 = new CommandHolder("IPMSG_AES_256", IPMsgProtocol.IPMSG_AES_256);
        CommandHolder IPMSG_PACKETNO_IV = new CommandHolder("IPMSG_PACKETNO_IV", IPMsgProtocol.IPMSG_PACKETNO_IV);
        CommandHolder IPMSG_ENCODE_BASE64 = new CommandHolder("IPMSG_ENCODE_BASE64", IPMsgProtocol.IPMSG_ENCODE_BASE64);
        CommandHolder IPMSG_SIGN_SHA1 = new CommandHolder("IPMSG_SIGN_SHA1", IPMsgProtocol.IPMSG_SIGN_SHA1);
        CommandHolder IPMSG_SIGN_SHA256 = new CommandHolder("IPMSG_SIGN_SHA256", IPMsgProtocol.IPMSG_SIGN_SHA256);
        CommandHolder IPMSG_RC2_40OLD = new CommandHolder("IPMSG_RC2_40OLD", IPMsgProtocol.IPMSG_RC2_40OLD);
        CommandHolder IPMSG_RC2_128OLD = new CommandHolder("IPMSG_RC2_128OLD", IPMsgProtocol.IPMSG_RC2_128OLD);
        CommandHolder IPMSG_BLOWFISH_128OLD = new CommandHolder("IPMSG_BLOWFISH_128OLD", IPMsgProtocol.IPMSG_BLOWFISH_128OLD);
        CommandHolder IPMSG_RC2_128OBSOLETE = new CommandHolder("IPMSG_RC2_128OBSOLETE", IPMsgProtocol.IPMSG_RC2_128OBSOLETE);
        CommandHolder IPMSG_RC2_256OBSOLETE = new CommandHolder("IPMSG_RC2_256OBSOLETE", IPMsgProtocol.IPMSG_RC2_256OBSOLETE);
        CommandHolder IPMSG_BLOWFISH_256OBSOL = new CommandHolder("IPMSG_BLOWFISH_256OBSOL", IPMsgProtocol.IPMSG_BLOWFISH_256OBSOL);
        CommandHolder IPMSG_AES_128OBSOLETE = new CommandHolder("IPMSG_AES_128OBSOLETE", IPMsgProtocol.IPMSG_AES_128OBSOLETE);
        CommandHolder IPMSG_SIGN_MD5OBSOLETE = new CommandHolder("IPMSG_SIGN_MD5OBSOLETE", IPMsgProtocol.IPMSG_SIGN_MD5OBSOLETE);
        CommandHolder IPMSG_UNAMEEXTOPT_OBSLT = new CommandHolder("IPMSG_UNAMEEXTOPT_OBSLT", IPMsgProtocol.IPMSG_UNAMEEXTOPT_OBSLT);
        CommandHolder IPMSG_FILE_REGULAR = new CommandHolder("IPMSG_FILE_REGULAR", IPMsgProtocol.IPMSG_FILE_REGULAR);
        CommandHolder IPMSG_FILE_DIR = new CommandHolder("IPMSG_FILE_DIR", IPMsgProtocol.IPMSG_FILE_DIR);
        CommandHolder IPMSG_FILE_RETPARENT = new CommandHolder("IPMSG_FILE_RETPARENT", IPMsgProtocol.IPMSG_FILE_RETPARENT);
        CommandHolder IPMSG_FILE_SYMLINK = new CommandHolder("IPMSG_FILE_SYMLINK", IPMsgProtocol.IPMSG_FILE_SYMLINK);
        CommandHolder IPMSG_FILE_CDEV = new CommandHolder("IPMSG_FILE_CDEV", IPMsgProtocol.IPMSG_FILE_CDEV);
        CommandHolder IPMSG_FILE_BDEV = new CommandHolder("IPMSG_FILE_BDEV", IPMsgProtocol.IPMSG_FILE_BDEV);
        CommandHolder IPMSG_FILE_FIFO = new CommandHolder("IPMSG_FILE_FIFO", IPMsgProtocol.IPMSG_FILE_FIFO);
        CommandHolder IPMSG_FILE_RESFORK = new CommandHolder("IPMSG_FILE_RESFORK", IPMsgProtocol.IPMSG_FILE_RESFORK);
        CommandHolder IPMSG_FILE_CLIPBOARD = new CommandHolder("IPMSG_FILE_CLIPBOARD", IPMsgProtocol.IPMSG_FILE_CLIPBOARD);
        CommandHolder IPMSG_FILE_RONLYOPT = new CommandHolder("IPMSG_FILE_RONLYOPT", IPMsgProtocol.IPMSG_FILE_RONLYOPT);
        CommandHolder IPMSG_FILE_HIDDENOPT = new CommandHolder("IPMSG_FILE_HIDDENOPT", IPMsgProtocol.IPMSG_FILE_HIDDENOPT);
        CommandHolder IPMSG_FILE_EXHIDDENOPT = new CommandHolder("IPMSG_FILE_EXHIDDENOPT", IPMsgProtocol.IPMSG_FILE_EXHIDDENOPT);
        CommandHolder IPMSG_FILE_ARCHIVEOPT = new CommandHolder("IPMSG_FILE_ARCHIVEOPT", IPMsgProtocol.IPMSG_FILE_ARCHIVEOPT);
        CommandHolder IPMSG_FILE_SYSTEMOPT = new CommandHolder("IPMSG_FILE_SYSTEMOPT", IPMsgProtocol.IPMSG_FILE_SYSTEMOPT);
        CommandHolder IPMSG_FILE_UID = new CommandHolder("IPMSG_FILE_UID", IPMsgProtocol.IPMSG_FILE_UID);
        CommandHolder IPMSG_FILE_USERNAME = new CommandHolder("IPMSG_FILE_USERNAME", IPMsgProtocol.IPMSG_FILE_USERNAME);
        CommandHolder IPMSG_FILE_GID = new CommandHolder("IPMSG_FILE_GID", IPMsgProtocol.IPMSG_FILE_GID);
        CommandHolder IPMSG_FILE_GROUPNAME = new CommandHolder("IPMSG_FILE_GROUPNAME", IPMsgProtocol.IPMSG_FILE_GROUPNAME);
        CommandHolder IPMSG_FILE_CLIPBOARDPOS = new CommandHolder("IPMSG_FILE_CLIPBOARDPOS", IPMsgProtocol.IPMSG_FILE_CLIPBOARDPOS);
        CommandHolder IPMSG_FILE_PERM = new CommandHolder("IPMSG_FILE_PERM", IPMsgProtocol.IPMSG_FILE_PERM);
        CommandHolder IPMSG_FILE_MAJORNO = new CommandHolder("IPMSG_FILE_MAJORNO", IPMsgProtocol.IPMSG_FILE_MAJORNO);
        CommandHolder IPMSG_FILE_MINORNO = new CommandHolder("IPMSG_FILE_MINORNO", IPMsgProtocol.IPMSG_FILE_MINORNO);
        CommandHolder IPMSG_FILE_CTIME = new CommandHolder("IPMSG_FILE_CTIME", IPMsgProtocol.IPMSG_FILE_CTIME);
        CommandHolder IPMSG_FILE_MTIME = new CommandHolder("IPMSG_FILE_MTIME", IPMsgProtocol.IPMSG_FILE_MTIME);
        CommandHolder IPMSG_FILE_ATIME = new CommandHolder("IPMSG_FILE_ATIME", IPMsgProtocol.IPMSG_FILE_ATIME);
        CommandHolder IPMSG_FILE_CREATETIME = new CommandHolder("IPMSG_FILE_CREATETIME", IPMsgProtocol.IPMSG_FILE_CREATETIME);
        CommandHolder IPMSG_FILE_CREATOR = new CommandHolder("IPMSG_FILE_CREATOR", IPMsgProtocol.IPMSG_FILE_CREATOR);
        CommandHolder IPMSG_FILE_FILETYPE = new CommandHolder("IPMSG_FILE_FILETYPE", IPMsgProtocol.IPMSG_FILE_FILETYPE);
        CommandHolder IPMSG_FILE_FINDERINFO = new CommandHolder("IPMSG_FILE_FINDERINFO", IPMsgProtocol.IPMSG_FILE_FINDERINFO);
        CommandHolder IPMSG_FILE_ACL = new CommandHolder("IPMSG_FILE_ACL", IPMsgProtocol.IPMSG_FILE_ACL);
        CommandHolder IPMSG_FILE_ALIASFNAME = new CommandHolder("IPMSG_FILE_ALIASFNAME", IPMsgProtocol.IPMSG_FILE_ALIASFNAME);
    
        Map<Integer, String> map = new HashMap<>();
    
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_NOOPERATION, "IPMSG_NOOPERATION");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_BR_ENTRY, "IPMSG_BR_ENTRY");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_BR_EXIT, "IPMSG_BR_EXIT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_ANSENTRY, "IPMSG_ANSENTRY");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_BR_ABSENCE, "IPMSG_BR_ABSENCE");
//        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_BR_NOTIFY, "IPMSG_BR_NOTIFY");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_BR_ISGETLIST, "IPMSG_BR_ISGETLIST");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_OKGETLIST, "IPMSG_OKGETLIST");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_GETLIST, "IPMSG_GETLIST");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_ANSLIST, "IPMSG_ANSLIST");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_BR_ISGETLIST2, "IPMSG_BR_ISGETLIST2");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_SENDMSG, "IPMSG_SENDMSG");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_RECVMSG, "IPMSG_RECVMSG");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_READMSG, "IPMSG_READMSG");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_DELMSG, "IPMSG_DELMSG");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_ANSREADMSG, "IPMSG_ANSREADMSG");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_GETINFO, "IPMSG_GETINFO");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_SENDINFO, "IPMSG_SENDINFO");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_GETABSENCEINFO, "IPMSG_GETABSENCEINFO");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_SENDABSENCEINFO, "IPMSG_SENDABSENCEINFO");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_GETFILEDATA, "IPMSG_GETFILEDATA");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_RELEASEFILES, "IPMSG_RELEASEFILES");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_GETDIRFILES, "IPMSG_GETDIRFILES");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_GETPUBKEY, "IPMSG_GETPUBKEY");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_ANSPUBKEY, "IPMSG_ANSPUBKEY");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_ABSENCEOPT, "IPMSG_ABSENCEOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_SERVEROPT, "IPMSG_SERVEROPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_DIALUPOPT, "IPMSG_DIALUPOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILEATTACHOPT, "IPMSG_FILEATTACHOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_ENCRYPTOPT, "IPMSG_ENCRYPTOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_UTF8OPT, "IPMSG_UTF8OPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_CAPUTF8OPT, "IPMSG_CAPUTF8OPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_ENCEXTMSGOPT, "IPMSG_ENCEXTMSGOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_CLIPBOARDOPT, "IPMSG_CLIPBOARDOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_CAPFILEENC_OBSLT, "IPMSG_CAPFILEENC_OBSLT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_CAPFILEENCOPT, "IPMSG_CAPFILEENCOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_SENDCHECKOPT, "IPMSG_SENDCHECKOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_SECRETOPT, "IPMSG_SECRETOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_BROADCASTOPT, "IPMSG_BROADCASTOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_MULTICASTOPT, "IPMSG_MULTICASTOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_AUTORETOPT, "IPMSG_AUTORETOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_RETRYOPT, "IPMSG_RETRYOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_PASSWORDOPT, "IPMSG_PASSWORDOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_NOLOGOPT, "IPMSG_NOLOGOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_NOADDLISTOPT, "IPMSG_NOADDLISTOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_READCHECKOPT, "IPMSG_READCHECKOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_SECRETEXOPT, "IPMSG_SECRETEXOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_ENCFILE_OBSLT, "IPMSG_ENCFILE_OBSLT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_ENCFILEOPT, "IPMSG_ENCFILEOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_NEWMULTI_OBSLT, "IPMSG_NEWMULTI_OBSLT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_RSA_512, "IPMSG_RSA_512");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_RSA_1024, "IPMSG_RSA_1024");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_RSA_2048, "IPMSG_RSA_2048");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_RSA_4096, "IPMSG_RSA_4096");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_RC2_40, "IPMSG_RC2_40");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_BLOWFISH_128, "IPMSG_BLOWFISH_128");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_AES_256, "IPMSG_AES_256");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_PACKETNO_IV, "IPMSG_PACKETNO_IV");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_ENCODE_BASE64, "IPMSG_ENCODE_BASE64");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_SIGN_SHA1, "IPMSG_SIGN_SHA1");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_SIGN_SHA256, "IPMSG_SIGN_SHA256");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_RC2_40OLD, "IPMSG_RC2_40OLD");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_RC2_128OLD, "IPMSG_RC2_128OLD");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_BLOWFISH_128OLD, "IPMSG_BLOWFISH_128OLD");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_RC2_128OBSOLETE, "IPMSG_RC2_128OBSOLETE");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_RC2_256OBSOLETE, "IPMSG_RC2_256OBSOLETE");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_BLOWFISH_256OBSOL, "IPMSG_BLOWFISH_256OBSOL");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_AES_128OBSOLETE, "IPMSG_AES_128OBSOLETE");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_SIGN_MD5OBSOLETE, "IPMSG_SIGN_MD5OBSOLETE");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_UNAMEEXTOPT_OBSLT, "IPMSG_UNAMEEXTOPT_OBSLT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_REGULAR, "IPMSG_FILE_REGULAR");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_DIR, "IPMSG_FILE_DIR");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_RETPARENT, "IPMSG_FILE_RETPARENT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_SYMLINK, "IPMSG_FILE_SYMLINK");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_CDEV, "IPMSG_FILE_CDEV");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_BDEV, "IPMSG_FILE_BDEV");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_FIFO, "IPMSG_FILE_FIFO");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_RESFORK, "IPMSG_FILE_RESFORK");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_CLIPBOARD, "IPMSG_FILE_CLIPBOARD");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_RONLYOPT, "IPMSG_FILE_RONLYOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_HIDDENOPT, "IPMSG_FILE_HIDDENOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_EXHIDDENOPT, "IPMSG_FILE_EXHIDDENOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_ARCHIVEOPT, "IPMSG_FILE_ARCHIVEOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_SYSTEMOPT, "IPMSG_FILE_SYSTEMOPT");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_UID, "IPMSG_FILE_UID");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_USERNAME, "IPMSG_FILE_USERNAME");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_GID, "IPMSG_FILE_GID");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_GROUPNAME, "IPMSG_FILE_GROUPNAME");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_CLIPBOARDPOS, "IPMSG_FILE_CLIPBOARDPOS");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_PERM, "IPMSG_FILE_PERM");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_MAJORNO, "IPMSG_FILE_MAJORNO");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_MINORNO, "IPMSG_FILE_MINORNO");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_CTIME, "IPMSG_FILE_CTIME");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_MTIME, "IPMSG_FILE_MTIME");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_ATIME, "IPMSG_FILE_ATIME");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_CREATETIME, "IPMSG_FILE_CREATETIME");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_CREATOR, "IPMSG_FILE_CREATOR");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_FILETYPE, "IPMSG_FILE_FILETYPE");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_FINDERINFO, "IPMSG_FILE_FINDERINFO");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_ACL, "IPMSG_FILE_ACL");
        addOrUpdateHolder(map, IPMsgProtocol.IPMSG_FILE_ALIASFNAME, "IPMSG_FILE_ALIASFNAME");
    
        Map<Integer, CommandMeta> tempMap = new HashMap<>();
    
        for (Integer integer : map.keySet()) {
            tempMap.put(integer, new CommandMeta(map.get(integer), integer));
        }
    
        metaMap = Collections.unmodifiableMap(tempMap);
    }
    
    
    private static void addOrUpdateHolder(Map<Integer, String> map, int command, String name) {
        String holder = map.get(command);
        if (holder != null) {
            holder += "; ";
            holder += name;
        } else {
            holder = name;
        }
        
        map.put(command, holder);
    }
    
    public static class CommandHolder {
        public int command;
        public String name;
    
        public CommandHolder(String name, int command) {
            this.command = command;
            this.name = name;
        }
    }
    
}

