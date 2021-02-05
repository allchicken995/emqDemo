package com.zht.emqdemo.common.Enum;


/**
 * @describe 统一响应码
 * @author zht
 * @date 2021/1/18
 */
public enum ResultCode {

    /**
     * 成功状态码
     */
    SUCCESS(0, "成功"),

    /**
     * 错误状态码
     */
    PARAM_ERROR(400000, "入参校验失败"),

    /**
     * 处理异常
     */
    EXCEPTION(500000, "处理异常"),


    /**
     * MQTT消息过期
     */
    MESSAGE_EXPIRE(500002, "MQTT消息过期");


    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
