package com.zht.emqdemo.common.Enum;

/**
 * @describe 使用状态
 * @author zht
 * @date 2021/1/18
 */
public enum Status {

    USED(0, "使用"),
    NOT_USED(1, "未使用");

    private Integer code;

    private String message;

    Status(Integer code, String message) {
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
