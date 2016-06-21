package org.base.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * VM args:-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * Created by wangwr on 2016/6/20.
 */
public class HeapOOM {

    static class HeapOMObject{

    }

    public static void main(String[] args){
        List<HeapOMObject> list = new ArrayList<HeapOMObject>();
        while (true){
            list.add(new HeapOMObject());
        }
    }
}
