package com.zht.emqdemo.mqtt.receiver;

import com.zht.emqdemo.common.constant.SubscribeTopic;
import com.zht.emqdemo.mqtt.event.DeviceEventProxy;
import com.zht.emqdemo.mqtt.event.EventContext;
import com.zht.emqdemo.mqtt.receiver.system.BaseDeviceReceiver;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @describe 设备事件监听器
 * @author zht
 * @date 2021/1/18
 */
@Component
public class EventReceiver extends BaseDeviceReceiver {

    @Resource
    DeviceEventProxy deviceEventProxy;

    @Override
    public int getQos() {
        return 0;
    }

    @Override
    public String getTopicFilter() {
        return properties.getQueue() + SubscribeTopic.DEVICE_EVENT;
    }

    @Override
    public void receive(String topic, MqttMessage message) {
        //业务事件处理
        deviceEventProxy.eventHandle(new EventContext(topic, message));
    }

}
