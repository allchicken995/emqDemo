package com.zht.emqdemo.mqtt.payload.sys;

import lombok.Data;

/**
 * @describe mqtt通用响应消息载荷
 * @author zht
 * @date 2021/1/18
 */
@Data
public class DisConnectPayload {

    /**
     * 设备IP地址
     */
    String username;
    /**
     * 离线时间戳
     */
    Long ts;
    /**
     *
     */
    String reason;
    /**
     * 离线时间戳（秒）
     */
    Long disconnected_at;

    /**
     * 客户端ID
     */
    String clientid;
}
