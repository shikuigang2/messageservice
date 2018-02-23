package com.ztth.api.path.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_messagelog")
public class MessageLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "messageId")
    private Long messageid;

    /**
     * 发送手机号
     */
    private String mobile;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Date sendtime;

    /**
     * 回调时间
     */
    @Column(name = "backTime")
    private Date backtime;

    /**
     * 发送渠道
     */
    private String channel;

    private String attr1;

    private String attr2;

    private String attr3;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return messageId
     */
    public Long getMessageid() {
        return messageid;
    }

    /**
     * @param messageid
     */
    public void setMessageid(Long messageid) {
        this.messageid = messageid;
    }

    /**
     * 获取发送手机号
     *
     * @return mobile - 发送手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置发送手机号
     *
     * @param mobile 发送手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取发送内容
     *
     * @return content - 发送内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置发送内容
     *
     * @param content 发送内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取发送时间
     *
     * @return sendtime - 发送时间
     */
    public Date getSendtime() {
        return sendtime;
    }

    /**
     * 设置发送时间
     *
     * @param sendtime 发送时间
     */
    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    /**
     * 获取回调时间
     *
     * @return backTime - 回调时间
     */
    public Date getBacktime() {
        return backtime;
    }

    /**
     * 设置回调时间
     *
     * @param backtime 回调时间
     */
    public void setBacktime(Date backtime) {
        this.backtime = backtime;
    }

    /**
     * 获取发送渠道
     *
     * @return channel - 发送渠道
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 设置发送渠道
     *
     * @param channel 发送渠道
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * @return attr1
     */
    public String getAttr1() {
        return attr1;
    }

    /**
     * @param attr1
     */
    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    /**
     * @return attr2
     */
    public String getAttr2() {
        return attr2;
    }

    /**
     * @param attr2
     */
    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    /**
     * @return attr3
     */
    public String getAttr3() {
        return attr3;
    }

    /**
     * @param attr3
     */
    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }
}