package com.zyyydqpi.dpc.calc;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by strom on 2017/7/2.
 * rule:1.key1 + key2 + key3
 *      2.MD5
 *      3.取2-24位
 *      4.拼接时间戳
 *      5.MD5
 *      6.取7-12位
 */

public class DPMD5 implements IDynamicPasswd {
    @Override
    public String getPasswd(String[] keys, long time) {
        StringBuffer sb = new StringBuffer();
        for(String k : keys){
            sb.append(k);
        }
        String key = sb.toString();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(key.getBytes());
        key = new BigInteger(1, md.digest()).toString(16);
        key = key.substring(2, 24);
        key = key + time;

        md.update(key.getBytes());
        key = new BigInteger(1, md.digest()).toString(16);
        return key.substring(7,12);
    }
}
