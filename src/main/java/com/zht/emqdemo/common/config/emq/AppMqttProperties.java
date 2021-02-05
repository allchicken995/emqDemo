package com.zht.emqdemo.common.config.emq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @describe APP EMQ配置
 * @author zht
 * @date 2021/1/18
 */
@Configuration
@ConfigurationProperties(prefix = "mqtt.app")
@Data
public class AppMqttProperties {

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
}
