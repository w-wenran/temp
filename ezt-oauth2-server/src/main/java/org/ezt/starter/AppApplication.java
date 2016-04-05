package org.ezt.starter;

import org.ezt.starter.config.DataBaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by wangwr on 2016.3.18.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan({"org.ezt.controller","org.ezt.service","org.ezt.handler"})
@ImportResource({"classpath:spring-context.xml"})
public class AppApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(HttpMessageConverterConfigs.class, ContentNegotiationConfigs.class);
    }

    public static void main(String[] args){
        SpringApplication.run(AppApplication.class,args);
    }

}
