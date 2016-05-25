package com.classloader;

import java.io.InputStream;

/**
 * Created by wangwr on 2016/5/24.
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".")+1)+".class";
                    //String fileName = "ClassLoaderTest"+".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if(is==null){
                        System.out.println(String.format("classLoader load class %s",name));
                        return super.loadClass(name);
                    }
                    byte[] b=new byte[is.available()];
                    is.read(b);
                    System.out.println(String.format("myClassLoader load class %s",name));
                    return defineClass(name,b,0,b.length);
                }catch (Exception e){
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = myLoader.loadClass("com.classloader.ClassLoaderTest").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof com.classloader.ClassLoaderTest);
        System.err.println(obj.getClass().getClassLoader().toString());
        System.err.println(ClassLoaderTest.class.getClassLoader().toString());
    }
}
