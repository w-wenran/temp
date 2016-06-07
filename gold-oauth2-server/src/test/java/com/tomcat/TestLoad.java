package com.tomcat;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * Created by wangwr on 2016/5/25.
 */
public class TestLoad {

    @org.junit.Test
    public void test() throws URISyntaxException {
        System.out.println(getClass().getCanonicalName());
        ProtectionDomain domain = getClass().getProtectionDomain();
        CodeSource codeSource = domain.getCodeSource();
        URI location = codeSource == null?null:codeSource.getLocation().toURI();
        String path  = location ==null?null:location.getSchemeSpecificPart();
        if(path == null){
            throw new IllegalStateException("unable to determine code resource archive");
        }

        File root = new File(path);
        if(!root.exists()){
            throw new IllegalStateException(String.format("unable to determine code resource archive from %s",path));
        }

        System.out.println(ManagementFactory.getRuntimeMXBean().getName());
    }

}
