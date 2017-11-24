package com.ztth.message.gate.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:gate.properties")
public class GateProperties {

    private String allowPath; //允许通过的路径 用来验证路径的有效


}
