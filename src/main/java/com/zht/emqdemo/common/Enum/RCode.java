package com.zht.emqdemo.common.Enum;

/**
 * @describe 统一响应码
 * @author zht
 * @date 2021/1/18
 */
public enum RCode {

    /**
     * 成功状态码
     */
    SUCCESS(0, "success"),
    /**
     * 错误状态码
     */
    ERROR(500000, "系统内部异常"),
    /**
     * 错误状态码
     */
    LOST(404000, "URL不存在"),
    /**
     * 错误状态码
     */
    PARAMETER(400000, "入参有误"),
    /**
     * 错误状态码
     */
    NO_DEVICE(450001, "设备不存在");



    private Integer code;
    private String message;

    RCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
