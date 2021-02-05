package com.zht.emqdemo.common.Enum;

/**
 * 通用载荷类型
 *
 * @author zht
 * @date 2021/1/18
 */
public enum PayloadType {

    DEFAULT("common");

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    PayloadType(String code) {
        this.code = code;
    }

    public static PayloadType get(String code) {
        if (code == null) {
            return null;
        }
        for (PayloadType payloadType : PayloadType.values()) {
            if (payloadType.getCode().equals(code)) {
                return payloadType;
            }
        }
        return null;
    }

}
