package com.zht.emqdemo.mqtt.receiver;

import com.zht.emqdemo.common.constant.SubscribeTopic;
import com.zht.emqdemo.mqtt.receiver.common.ServiceResponseForwarder;
import com.zht.emqdemo.mqtt.receiver.system.BaseDeviceReceiver;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 监听APP控制设备的响应主题, 用来转发给应用端
 *
 * @author zht
 * @date 2021/1/18
 */
@Component
public class ForwardReceiver extends BaseDeviceReceiver {

    @Resource
    private ServiceResponseForwarder forwarder;

    @Override
    public int getQos() {
        return 0;
    }

    @Override
    public String getTopicFilter() {
        return properties.getQueue() + SubscribeTopic.DEVICE_SERVICE_RESPONSE;
    }

    @Override
    public void receive(String topic, MqttMessage message) {
        forwarder.forward(topic, message);
    }

}
