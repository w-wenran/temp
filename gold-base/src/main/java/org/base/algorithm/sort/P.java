package org.base.algorithm.sort;

/**
 * Created by wangwr on 2016/6/2.
 */
public class P {

    public static void show(String format){
        System.out.println(format);
    }

    public static void show(String format,Object... objs){
        System.out.println(String.format(format,objs));
    }
}
