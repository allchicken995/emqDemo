package com.zht.emqdemo.mqtt.event;

import com.alibaba.fastjson.JSONObject;
import com.zht.emqdemo.common.Enum.ResultCode;
import com.zht.emqdemo.mqtt.payload.response.ResponsePayload;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 事件响应发布器
 *
 * @author zht
 * @date 2021/1/18
 */
@Component
public class EventRespPublisher {

    private final static Logger logger = LoggerFactory.getLogger(EventRespPublisher.class);

    @Resource(name = "deviceMqttClient")
    private MqttClient deviceMqttClient;

    /**
     * 发布事件处理正常响应
     *
     * @param context 上下文
     */
    public void ok(EventContext context) {
        publish(ResponsePayload.ok(context.getRequestPayload().getClientToken(), System.currentTimeMillis()), context);
    }

    /**
     * 发布事件处理错误响应
     *
     * @param code    错误码
     * @param context 上下文
     */
    public void error(ResultCode code, EventContext context) {
        publish(ResponsePayload.fail(code.getCode(), context.getRequestPayload().getClientToken(), System.currentTimeMillis()), context);
    }

    private void publish(ResponsePayload payload, EventContext context) {

        String resTopic = context.getTopic().replace("/event/", "/event_response/");

        try {
            deviceMqttClient.publish(resTopic, new MqttMessage(JSONObject.toJSONString(payload).getBytes()));

            logger.info("事件响应主题推送成功: " + resTopic + ",消息载荷: " + JSONObject.toJSONString(payload));
        } catch (Throwable ex) {
            logger.error("事件响应主题推送失败: " + resTopic + ",消息载荷: " + JSONObject.toJSONString(payload), ex);
        }
    }

}
