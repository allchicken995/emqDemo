package com.zht.emqdemo.common.config.emq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @describe 设备EMQ配置
 * @author zht
 * @date 2021/1/18
 */
@Configuration
@ConfigurationProperties(prefix = "mqtt.device")
@Data
public class DeviceMqttProperties {

    /**
     * 连接EMQ的地址
     */
    private String url;
    /**
     * 连接EMQ的客户端ID前缀
     */
    private String clientIdPrefix;
    /**
     * 连接EMQ的用户名
     */
    private String username;
    /**
     * 连接EMQ的密码
     */
    private String password;
    /**
     * 最大飞行窗口
     */
    private Integer maxInflight;
    /**
     * queue类型的共享订阅前缀
     */
    private String queue;
    /**
     * share类型的共享订阅前缀
     */
    private String share;
    /**
     * 是否校验消息唯一性和时效性
     */
    private boolean check;
    /**
     * 消息有效时间S
     */
    private Long expire;
}
