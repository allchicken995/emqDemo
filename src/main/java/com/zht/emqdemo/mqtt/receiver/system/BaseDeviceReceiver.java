package com.zht.emqdemo.mqtt.receiver.system;


import com.zht.emqdemo.common.config.emq.DeviceMqttProperties;

import javax.annotation.Resource;

public abstract class BaseDeviceReceiver extends AbstractReceiver {

    @Resource
    public DeviceMqttProperties properties;
}
