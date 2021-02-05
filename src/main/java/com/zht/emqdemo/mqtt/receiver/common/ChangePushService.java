package com.zht.emqdemo.mqtt.receiver.common;

import com.alibaba.fastjson.JSONObject;
import com.zht.emqdemo.common.constant.PublishTopic;
import com.zht.emqdemo.mqtt.payload.request.data.PushData;
import com.zht.emqdemo.mqtt.payload.response.ChangePayload;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @describe 状态变更发布器
 * @author zht
 * @date 2021/1/18
 */
@Service
public class ChangePushService {

    private final static Logger logger = LoggerFactory.getLogger(ChangePushService.class);

    @Resource(name = "appMqttClient")
    private MqttClient appMqttClient;

    /**
     * 推送状态变更接口
     *
     * @param clientId    客户端ID
     * @param pushData    变更的设备列表
     * @param clientToken 事件消息的唯一token
     */
    public void publish(String clientId, List<PushData> pushData, String clientToken) {
        try {
            String resTopic = PublishTopic.CHANGE.replace("+", clientId);
            ChangePayload changePayload = new ChangePayload(ChangePayload.getData(pushData), clientToken, System.currentTimeMillis());
            MqttMessage resMessage = new MqttMessage(JSONObject.toJSONString(changePayload).getBytes());
            appMqttClient.publish(resTopic, resMessage);
            logger.info("状态变更主题发送成功: " + resTopic + ",消息载荷: " + new String(resMessage.getPayload()));

        } catch (Throwable ex) {
            logger.error("状态变更主题发送异常.", ex);
        }
    }
}
