package com.ztth.api.path.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "message")
public class MessageConfig {

    private String  httpUrl;

    private String queueIn;

    public String getReponseType() {
        return reponseType;
    }

    public void setReponseType(String reponseType) {
        this.reponseType = reponseType;
    }

    private String reponseType;

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String uid;

    private String key;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    private String channel;//发送渠道

    public String getSingleSendUrl() {
        return singleSendUrl;
    }

    public void setSingleSendUrl(String singleSendUrl) {
        this.singleSendUrl = singleSendUrl;
    }
    private String  singleSendUrl;

    private String batchSendUrl;
    public String getBatchSendUrl() {
        return batchSendUrl;
    }

    public void setBatchSendUrl(String batchSendUrl) {
        this.batchSendUrl = batchSendUrl;
    }




}
