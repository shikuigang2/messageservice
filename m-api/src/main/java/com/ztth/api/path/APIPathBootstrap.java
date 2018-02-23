package com.ztth.api.path;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
@EnableFeignClients
public class APIPathBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder(APIPathBootstrap.class).web(true).run(args);
    }
}
