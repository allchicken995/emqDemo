package com.zht.emqdemo.mqtt.receiver;

import com.zht.emqdemo.common.constant.SubscribeTopic;
import com.zht.emqdemo.mqtt.receiver.system.BaseDeviceReceiver;
import com.zht.emqdemo.mqtt.receiver.system.ConnectedListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @describe Emq客户端上下线监听器
 * @author zht
 * @date 2021/1/18
 */
@Component
public class ConnectedReceiver extends BaseDeviceReceiver {

    @Resource
    private ConnectedListener connectedListener;

    @Override
    public int getQos() {
        return 0;
    }

    @Override
    public String getTopicFilter() {
        return properties.getShare() + SubscribeTopic.CLIENT_LISTENER_TOPIC;
    }

    @Override
    public void receive(String topic, MqttMessage message) {
        connectedListener.connect(topic, message);
    }
}
