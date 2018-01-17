package com.ztth.api.path.entity;

import javax.persistence.*;

@Table(name = "mobile_data")
public class MobileData {
    @Id
    private Long id;

    private Integer mobilehead;

    private Integer mobilemiddle;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 渠道商
     */
    private String channel;

    /**
     * 区号
     */
    private String areacode;

    /**
     * 邮编
     */
    private String zipcode;

    /**
     * 手机号码类型
     */
    private String mobiletyle;

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
     * @return mobilehead
     */
    public Integer getMobilehead() {
        return mobilehead;
    }

    /**
     * @param mobilehead
     */
    public void setMobilehead(Integer mobilehead) {
        this.mobilehead = mobilehead;
    }

    /**
     * @return mobilemiddle
     */
    public Integer getMobilemiddle() {
        return mobilemiddle;
    }

    /**
     * @param mobilemiddle
     */
    public void setMobilemiddle(Integer mobilemiddle) {
        this.mobilemiddle = mobilemiddle;
    }

    /**
     * 获取省份
     *
     * @return province - 省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省份
     *
     * @param province 省份
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取城市
     *
     * @return city - 城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置城市
     *
     * @param city 城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取渠道商
     *
     * @return channel - 渠道商
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 设置渠道商
     *
     * @param channel 渠道商
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * 获取区号
     *
     * @return areacode - 区号
     */
    public String getAreacode() {
        return areacode;
    }

    /**
     * 设置区号
     *
     * @param areacode 区号
     */
    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    /**
     * 获取邮编
     *
     * @return zipcode - 邮编
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * 设置邮编
     *
     * @param zipcode 邮编
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * 获取手机号码类型
     *
     * @return mobiletyle - 手机号码类型
     */
    public String getMobiletyle() {
        return mobiletyle;
    }

    /**
     * 设置手机号码类型
     *
     * @param mobiletyle 手机号码类型
     */
    public void setMobiletyle(String mobiletyle) {
        this.mobiletyle = mobiletyle;
    }
}