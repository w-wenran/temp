package org.base.algorithm.sort;

/**
 * Created by wangwr on 2016/6/2.
 */
public abstract class Sort {

    void run(){
        int[] arys = Arrays.random(15);
        P.show(String.format("original array = %s",Arrays.toStr(arys)));
        this.sort(arys);
        P.show(String.format("sorted array = %s",Arrays.toStr(arys)));
    }

    abstract int[] sort(int[] args);
}
