package org.base.algorithm.hash;

import org.base.algorithm.sort.P;

import java.security.MessageDigest;

/**
 * 一致性hash算法
 * Created by wangwr on 2016/6/21.
 */
public class MD5Hash {

    MessageDigest ms;

    public MD5Hash(){
        try {
            ms = MessageDigest.getInstance("MD5");
        }catch (Exception e){

        }
    }

    /**
     * 均匀分布在2^32-1的还上
     */
    long hash(String key){
        ms.reset();
        ms.update(key.getBytes());
        byte[] bytes = ms.digest();

        long h = 0;
        for(int i=0;i<4;i++){//4*8=32
            h <<= 8;
            h |= ((int)bytes[i]) & 0xff;
        }
        return h;
    }


    public static void main(String[] args){
        MD5Hash md5Hash=new MD5Hash();

        P.show(md5Hash.hash("string-1")+"");
        P.show(md5Hash.hash("string-1")+"");
        P.show(md5Hash.hash("string-2")+"");

        P.show(md5Hash.hash("host-1")+"");
        P.show(md5Hash.hash("host-2")+"");
    }
}