package com.classloader;

/**
 * Created by wangwr on 2016/5/24.
 */
public class HotSwapClassLoader  extends ClassLoader{

    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public  Class loadByte(byte[] classByte){
        return defineClass(null,classByte,0,classByte.length);
    }
}
