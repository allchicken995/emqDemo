package com.zht.emqdemo.mqtt.event;

import com.alibaba.fastjson.JSONObject;
import com.zht.emqdemo.common.Enum.PayloadType;
import com.zht.emqdemo.mqtt.payload.request.RequestPayload;
import com.zht.emqdemo.mqtt.payload.request.data.PushData;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备事件上下文
 *
 * @author zht
 * @date 2021/1/18
 */
public class EventContext {

    /**
     * clientId
     */
    @Getter
    private final String clientId;

    /**
     * 主题
     */
    @Getter
    private final String topic;

    /**
     * MQTTMessage
     */
    @Getter
    private MqttMessage mqttMessage;

    /**
     * 通用载荷
     */
    @Getter
    private RequestPayload requestPayload;

    /**
     * push数据
     */
    @Setter
    @Getter
    private List<PushData> pushData = new ArrayList<>();

    /**
     * 是否存在代理子事件异常
     */
    @Setter
    @Getter
    private boolean hasSubEx = false;

    /**
     * 构造函数
     *
     * @param topic   主题
     * @param message 消息
     */
    public EventContext(String topic, MqttMessage message) {
        this.topic = topic;
        this.mqttMessage = message;
        this.clientId = getDeviceKey();
    }

    /**
     * 构造函数
     *
     * @param topic          主题
     * @param clientId       客户端ID
     * @param requestPayload 通用请求载荷
     */
    public EventContext(String topic, String clientId, RequestPayload requestPayload) {
        this.topic = topic;
        this.clientId = clientId;
        this.requestPayload = requestPayload;
    }

    public String getEventType() {
        String[] topics = topic.split("/");
        return topics.length == 4 ? topics[3] : null;
    }

    public String getDeviceKey() {
        String[] topics = topic.split("/");
        return topics.length == 4 ? topics[0] : null;
    }

    public PayloadType getPayloadType() {
        String[] topics = topic.split("/");
        return PayloadType.get(topics[1]);
    }

    /**
     * 解析通用请求载荷
     */
    public void parseRequestPayload() {
        if (requestPayload == null) {
            requestPayload = JSONObject.parseObject(mqttMessage.getPayload(), RequestPayload.class);
        }
    }

    public <T> T getEventData(Class<T> clazz) {
        try {
            return JSONObject.parseObject(getRequestPayload().getData(), clazz);
        } catch (Exception e) {
            throw new RuntimeException(String.format("主题：%s，Json解析失败", topic), e);
        }
    }

    public <T> List<T> getEventDataList(Class<T> clazz) {
        try {
            return JSONObject.parseArray(getRequestPayload().getData(), clazz);
        } catch (Exception e) {
            throw new RuntimeException(String.format("主题：%s，Json解析失败", topic), e);
        }
    }
}
