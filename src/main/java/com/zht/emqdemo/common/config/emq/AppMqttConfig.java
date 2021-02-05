package com.zht.emqdemo.common.config.emq;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * App上线离线MQTT客户端配置
 *
 * @author zht
 * @date 2021/1/18
 */
@Configuration
public class AppMqttConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(AppMqttConfig.class);

    @Resource
    AppMqttProperties properties;

    /**
     * 实例化MQTT客户端，并加入IoC容器
     *
     * @return MQTT客户端对象
     * @throws MqttException 连接出现异常时抛出
     */
    @Bean(value = "appMqttClient")
    public MqttClient mqttConnClient() throws MqttException {
        // 客户端id生成方式为：客户端前缀-UUID
        String clientId = properties.getClientIdPrefix() + ":" + UUID.randomUUID();

        MqttClient client = new MqttClient(properties.getUrl(), clientId, new MemoryPersistence());

        // MQTT 连接选项
        MqttConnectOptions connOpts = new MqttConnectOptions();
        //用户名
        connOpts.setUserName(properties.getUsername());
        //密码
        connOpts.setPassword(properties.getPassword().toCharArray());
        //超时时间
        connOpts.setConnectionTimeout(30);
        //心跳时间,单位秒
        connOpts.setKeepAliveInterval(60);
        //自动重连
        connOpts.setAutomaticReconnect(true);
        //设置最大飞行窗口
        connOpts.setMaxInflight(properties.getMaxInflight());

        boolean[] reTry = {true};
        // 设置客户端事件回调
        client.setCallback(new MqttCallbackExtended() {

            @Override
            public void connectComplete(boolean reconnect, String serverUrl) {
                LOGGER.info("APP服务端连接Broker连接成功, clientId: " + clientId);
                reTry[0] = false;
            }

            @Override
            public void connectionLost(Throwable cause) {
                LOGGER.error("APP服务端连接异常断开！", cause);
                reTry[0] = true;
                while (reTry[0]) {
                    try {
                        Thread.sleep(60000);
                        LOGGER.info("APP服务端断开重连中...");
                        client.connect(connOpts);
                    } catch (Exception e) {
                        LOGGER.info("APP服务端重连失败,一分钟后再次尝试");
                    }
                }
                LOGGER.info("APP服务端重连成功");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }

        });
        client.connect(connOpts);
        return client;
    }
}
