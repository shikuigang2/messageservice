package com.ztth.api.path.entity;

import javax.persistence.*;

@Table(name = "t_path")
public class ApiPath {
    @Id
    private Long id;

    /**
     * 参数集合
     */
    private String uri;

    /**
     * 标记 是否启用
     */
    private String flag;

    private String paraset;

    /**
     * 记过集合
     */
    private String resultset;

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
     * 获取参数集合
     *
     * @return uri - 参数集合
     */
    public String getUri() {
        return uri;
    }

    /**
     * 设置参数集合
     *
     * @param uri 参数集合
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * 获取标记 是否启用
     *
     * @return flag - 标记 是否启用
     */
    public String getFlag() {
        return flag;
    }

    /**
     * 设置标记 是否启用
     *
     * @param flag 标记 是否启用
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * @return paraset
     */
    public String getParaset() {
        return paraset;
    }

    /**
     * @param paraset
     */
    public void setParaset(String paraset) {
        this.paraset = paraset;
    }

    /**
     * 获取记过集合
     *
     * @return resultset - 记过集合
     */
    public String getResultset() {
        return resultset;
    }

    /**
     * 设置记过集合
     *
     * @param resultset 记过集合
     */
    public void setResultset(String resultset) {
        this.resultset = resultset;
    }
}