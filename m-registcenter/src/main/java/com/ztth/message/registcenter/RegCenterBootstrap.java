package com.ztth.message.registcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer  //启动一个服务注册中心
@SpringBootApplication
public class RegCenterBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(RegCenterBootstrap.class, args);
    }
}

