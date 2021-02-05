package com.zht.emqdemo.mqtt.receiver.system;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象的主题消息接收器
 * 主要封装了一些通用方法
 *
 * @author zht
 * @date 2021/1/18
 */
public abstract class AbstractReceiver implements IMqttMessageListener {

    protected Logger logger;

    /**
     * 构造函数
     */
    public AbstractReceiver() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * 获取主题筛选器
     * 此方法交由子类实现, 方法返回的字符串将被用于订阅时指定主题筛选器
     *
     * @return 主题筛选器
     */
    public abstract String getTopicFilter();

    /**
     * 对MQTT Client接收回调的友好封装。当收到MQTT消息后会回调该方法
     *
     * @param topic   消息主题
     * @param message 消息本体
     */
    public abstract void receive(String topic, MqttMessage message) throws Exception;

    /**
     * 指定订阅时的服务质量。
     * 默认为: 1
     * 通过重写此方法可以调整服务质量
     *
     * @return QoS级别
     */
    public int getQos() {
        return 1;
    }

    /**
     * 实现MQTT原生回调
     * 主要屏蔽了异常。此方法异常一旦抛出, MQTT Client连接就会断开, 所以需要屏蔽掉处理异常。
     * 目前只是简单打印异常堆栈。后期有更具体的需求, 可以扩展此方法。
     *
     * @param topic   主题
     * @param message 消息
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {
            receive(topic, message);
        } catch (Throwable e) {
            logger.error(String.format("主题: %s, 处理异常", topic), e);
        }
    }

}
