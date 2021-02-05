package com.zht.emqdemo.common.Enum;

/**
 *
 * @describe 在线状态
 * @author zht
 * @date 2021/1/18
 */
public enum Online {

    ONLINE(1, "connected", "在线"),
    OFFLINE(0, "disconnected", "离线");

    private Integer code;

    private String action;

    private String message;

    Online(Integer code, String action, String message) {
        this.code = code;
        this.action = action;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 根据连接动作获取在线状态枚举类
     *
     * @param action 连接动作
     * @return 线状态枚举类
     */
    public static Online getByAction(String action) {
        if (action == null) {
            return null;
        }
        for (Online online : Online.values()) {
            if (online.getAction().equals(action)) {
                return online;
            }
        }
        return null;
    }
}
