package org.base.algorithm.sort;

/**
 * Created by wangwr on 2016/6/2.
 */
public class Arrays {

    public static int[] random(int len){
        int[] rs = new int[len];
        for(int i=0;i<len;i++){
            rs[i]= (int) (Math.random()*100);
        }
        return rs;
    }

    public static String toStr(int[] objs){
        return java.util.Arrays.toString(objs);
    }
}
