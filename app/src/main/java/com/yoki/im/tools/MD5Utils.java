package com.yoki.im.tools;

import java.security.MessageDigest;
//import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public class MD5Utils {
    public static String encrypt(String plaintext) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = plaintext.getBytes();
//            MessageDigest mdInst = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
//            mdInst.update(btInput);
//            byte[] md = mdInst.digest();
//            int j = md.length;
//            char[] str = new char[(j * 2)];
//            int k = 0;
//            for (byte byte0 : md) {
//                int k2 = k + 1;
//                str[k] = hexDigits[(byte0 >>> 4) & 15];
//                k = k2 + 1;
//                str[k2] = hexDigits[byte0 & 15];
//            }
//            return new String(str);
        } catch (Exception e) {

        }
        return null;
    }
}
