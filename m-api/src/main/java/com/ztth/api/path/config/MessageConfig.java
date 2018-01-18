package com.ztth.api.path.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "message")
public class MessageConfig {

    private String  httpUrl;

    private String queueIn;

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    private int maxLength;

    public String getQueueIn() {
        return queueIn;
    }

    public void setQueueIn(String queueIn) {
        this.queueIn = queueIn;
    }

    public String getQueueOut() {
        return queueOut;
    }

    public void setQueueOut(String queueOut) {
        this.queueOut = queueOut;
    }

    private String queueOut;

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    private String key;


}
