package com.ztth.api.path.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mobile_channel")
public class MobileChannel {
    @Id
    private Long id;

    /**
     * 渠道名称
     */
    @Column(name = "channelName")
    private String channelname;

    /**
     * 企业代码
     */
    @Column(name = "enterpriseCode")
    private String enterprisecode;

    /**
     * 账户名称
     */
    @Column(name = "accountName")
    private String accountname;

    /**
     * 账户密码
     */
    @Column(name = "accountPassword")
    private String accountpassword;

    /**
     * 网关ip
     */
    @Column(name = "gatewayIP")
    private String gatewayip;

    /**
     * 连接端口
     */
    @Column(name = "connectPort")
    private String connectport;

    /**
     * 渠道有效开始时间
     */
    @Column(name = "channelStartDate")
    private Date channelstartdate;

    /**
     * 渠道有效 结束时间
     */
    @Column(name = "channelEndDate")
    private Date channelenddate;

    /**
     * 短信发送数量
     */
    @Column(name = "msgCount")
    private Integer msgcount;

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
     * 获取渠道名称
     *
     * @return channelName - 渠道名称
     */
    public String getChannelname() {
        return channelname;
    }

    /**
     * 设置渠道名称
     *
     * @param channelname 渠道名称
     */
    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    /**
     * 获取企业代码
     *
     * @return enterpriseCode - 企业代码
     */
    public String getEnterprisecode() {
        return enterprisecode;
    }

    /**
     * 设置企业代码
     *
     * @param enterprisecode 企业代码
     */
    public void setEnterprisecode(String enterprisecode) {
        this.enterprisecode = enterprisecode;
    }

    /**
     * 获取账户名称
     *
     * @return accountName - 账户名称
     */
    public String getAccountname() {
        return accountname;
    }

    /**
     * 设置账户名称
     *
     * @param accountname 账户名称
     */
    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    /**
     * 获取账户密码
     *
     * @return accountPassword - 账户密码
     */
    public String getAccountpassword() {
        return accountpassword;
    }

    /**
     * 设置账户密码
     *
     * @param accountpassword 账户密码
     */
    public void setAccountpassword(String accountpassword) {
        this.accountpassword = accountpassword;
    }

    /**
     * 获取网关ip
     *
     * @return gatewayIP - 网关ip
     */
    public String getGatewayip() {
        return gatewayip;
    }

    /**
     * 设置网关ip
     *
     * @param gatewayip 网关ip
     */
    public void setGatewayip(String gatewayip) {
        this.gatewayip = gatewayip;
    }

    /**
     * 获取连接端口
     *
     * @return connectPort - 连接端口
     */
    public String getConnectport() {
        return connectport;
    }

    /**
     * 设置连接端口
     *
     * @param connectport 连接端口
     */
    public void setConnectport(String connectport) {
        this.connectport = connectport;
    }

    /**
     * 获取渠道有效开始时间
     *
     * @return channelStartDate - 渠道有效开始时间
     */
    public Date getChannelstartdate() {
        return channelstartdate;
    }

    /**
     * 设置渠道有效开始时间
     *
     * @param channelstartdate 渠道有效开始时间
     */
    public void setChannelstartdate(Date channelstartdate) {
        this.channelstartdate = channelstartdate;
    }

    /**
     * 获取渠道有效 结束时间
     *
     * @return channelEndDate - 渠道有效 结束时间
     */
    public Date getChannelenddate() {
        return channelenddate;
    }

    /**
     * 设置渠道有效 结束时间
     *
     * @param channelenddate 渠道有效 结束时间
     */
    public void setChannelenddate(Date channelenddate) {
        this.channelenddate = channelenddate;
    }

    /**
     * 获取短信发送数量
     *
     * @return msgCount - 短信发送数量
     */
    public Integer getMsgcount() {
        return msgcount;
    }

    /**
     * 设置短信发送数量
     *
     * @param msgcount 短信发送数量
     */
    public void setMsgcount(Integer msgcount) {
        this.msgcount = msgcount;
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