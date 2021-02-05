package com.zht.emqdemo.common.config.emq;

import com.zht.emqdemo.mqtt.receiver.system.BaseDeviceReceiver;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * 设备上线离线MQTT客户端配置
 *
 * @author zht
 * @date 2021/1/18
 */
@Configuration
public class DeviceMqttConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(DeviceMqttConfig.class);

    @Resource
    DeviceMqttProperties properties;

    /**
     * 实例化MQTT客户端，并加入IoC容器
     *
     * @param receivers 主题接收器
     * @return MQTT客户端对象
     * @throws MqttException 连接出现异常时抛出
     */
    @Bean(value = "deviceMqttClient")
    public MqttClient mqttConnClient(List<BaseDeviceReceiver> receivers) throws MqttException {
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
                LOGGER.info("Device服务端连接Broker连接成功, clientId: " + clientId);
                reTry[0] = false;
            }

            @Override
            public void connectionLost(Throwable cause) {
                LOGGER.error("Device服务端连接异常断开！", cause);
                reTry[0] = true;
                while (reTry[0]) {
                    try {
                        Thread.sleep(60000);
                        LOGGER.info("Device服务端断开重连中...");
                        start(client, connOpts, receivers);
                    } catch (Exception e) {
                        LOGGER.info("Device服务端重连失败,一分钟后再次尝试");
                    }
                }
                LOGGER.info("Device服务端重连成功");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                for (BaseDeviceReceiver receiver : receivers) {
                    if (MqttTopic.isMatched(getTopic(receiver.getTopicFilter()), topic)) {
                        receiver.messageArrived(topic, message);
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }

        });
        return start(client, connOpts, receivers);
    }


    /**
     * 处理带前缀的主题
     */
    private String getTopic(String topicFilter) {
        if (topicFilter.startsWith("$queue/")) {
            return topicFilter.replaceFirst("\\$queue/", "");
        } else if (topicFilter.startsWith("$share/")) {
            String replaceFirst = topicFilter.replaceFirst("\\$share/", "");
            return replaceFirst.substring(replaceFirst.indexOf("/") + 1);
        } else {
            return topicFilter;
        }
    }

    /**
     * 连接mqtt,并订阅所有的主题监听器
     */
    private MqttClient start(MqttClient client, MqttConnectOptions connOpts, List<BaseDeviceReceiver> receivers) throws MqttException {

        try {
            client.connect(connOpts);
            for (BaseDeviceReceiver receiver : receivers) {
                client.subscribe(receiver.getTopicFilter() , receiver.getQos(), receiver);
                LOGGER.info("订阅主题成功：topic=" + receiver.getTopicFilter()+ " ,Qos=" + receiver.getQos());
            }
        } catch (MqttException ex) {
            // MQTT连接
            release(client);
            throw ex;
        }
        return client;
    }

    private void release(MqttClient client) {
        try {
            if (client.isConnected()) {
                client.disconnect();
            }
            client.close();
        } catch (MqttException ex) {
            LOGGER.error("mqttClient close failed.", ex);
        }
    }
}
