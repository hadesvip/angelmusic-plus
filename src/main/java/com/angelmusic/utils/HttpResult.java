package com.angelmusic.utils;

/**
 * 响应结果
 * Created by wangyong on 16-12-13.
 */
public class HttpResult {

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应码描述
     */
    private String description;

    /**
     * 响应对象数据
     */
    private Object detail;

    public String getCode() {
        return code;
    }

    public HttpResult setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public HttpResult setDescription(String description) {
        this.description = description;
        return this;
    }

    public Object getDetail() {
        return detail;
    }

    public HttpResult setDetail(Object detail) {
        this.detail = detail;
        return this;
    }

    public HttpResult(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public HttpResult() {
    }

    public HttpResult(String code, String description, Object detail) {
        this.code = code;
        this.description = description;
        this.detail = detail;
    }
}
