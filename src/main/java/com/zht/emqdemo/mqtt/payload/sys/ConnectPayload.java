package com.zht.emqdemo.mqtt.payload.sys;

import lombok.Data;

/**
 * @describe mqtt通用响应消息载荷
 * @author zht
 * @date 2021/1/18
 */
@Data
public class ConnectPayload {

    /**
     * 设备IP地址
     */
    String username;
    /**
     * 上线时间戳
     */
    Long ts;
    /**
     * 端口
     */
    Integer sockport;
    /**
     *
     */
    Integer proto_ver;
    /**
     *
     */
    String proto_name;
    /**
     *
     */
    Integer keepalive;
    /**
     * 设备IP地址
     */
    String ipaddress;
    /**
     *
     */
    Integer expiry_interval;
    /**
     * 连接时间戳（秒）
     */
    Long connected_at;
    /**
     *
     */
    Integer connack;
    /**
     * 客户端ID
     */
    String clientid;
    /**
     * 设备IP地址
     */
    Boolean clean_start;
}
