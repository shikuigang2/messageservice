package com.ztth.api.path.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_message")
public class Message {
    @Id
    private Long id;

    private Integer userid;

    private String mobile;

    private String content;

    @Column(name = "createTime")
    private Date createtime;

    /**
     * 0创建1发送成功2发送失败
     */
    private Integer flag;

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
     * @return userid
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return createTime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取0创建1发送成功2发送失败
     *
     * @return flag - 0创建1发送成功2发送失败
     */
    public Integer getFlag() {
        return flag;
    }

    /**
     * 设置0创建1发送成功2发送失败
     *
     * @param flag 0创建1发送成功2发送失败
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
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