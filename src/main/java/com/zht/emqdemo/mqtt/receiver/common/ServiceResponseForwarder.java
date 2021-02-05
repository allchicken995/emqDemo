package com.zht.emqdemo.mqtt.receiver.common;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSONObject;
import com.zht.emqdemo.common.config.emq.DeviceMqttProperties;
import com.zht.emqdemo.common.util.ValidateUtil;
import com.zht.emqdemo.mqtt.payload.request.ForwardPayload;
import com.zht.emqdemo.mqtt.payload.response.ResponsePayload;
import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.ValidationException;

/**
 * 设备服务响应转发处理
 *
 * @author zht
 * @date 2021/1/18
 */
@Service
public class ServiceResponseForwarder {

    private final Logger logger = LoggerFactory.getLogger(ServiceResponseForwarder.class);

    @Lazy
    @Resource(name = "appMqttClient")
    private MqttClient appMqttClient;

    @Resource
    DeviceMqttProperties properties;

    @Resource
    CommonService commonService;

    /**
     * 设备服务响应转发处理
     *
     * @param topic   主题
     * @param message 消息
     */
    @Async("asyncExecutor")
    public void forward(String topic, MqttMessage message) {

        logger.info("设备控制响应处理开始: {}", topic);
        logger.info("消息载荷: {}", new String(message.getPayload()));

        ForwardPayload payload;

        //载荷解析
        try {
            payload = JSONObject.parseObject(message.getPayload(), ForwardPayload.class);
        } catch (Exception ex) {
            logger.error("控制响应消息载荷解析失败: ", ex);
            return;
        }

        //载荷校验
        try {
            ValidateUtil.validate(payload);
        } catch (ValidationException ex) {
            logger.error("控制响应消息载荷校验失败: ", ex);
            return;
        }

        //判断消息校验开关是否打开
        if (properties.isCheck()) {
            //校验消息是否过期
            if (commonService.isExpire(payload.getTimestamp())) {
                logger.error("控制响应消息已过期");
                return;
            }

            //校验消息是否唯一
            if (commonService.isNotUnique(payload.getClientToken())) {
                logger.error("控制响应消息重复");
                return;
            }
        }


        //是返回给APP的响应，则转发
        forward2App(topic, message, payload);

        logger.info("设备控制响应处理完成...");
    }

    /**
     * APP转发响应转发处理
     */
    private void forward2App(String topic, MqttMessage message, ForwardPayload payload) {

        try {
            String resTopic = topic.replace("/common/", "/app/").concat("/");
            ResponsePayload responsePayload = ResponsePayload.getInstance(payload);
            MqttMessage resMessage = new MqttMessage(JSONObject.toJSONString(responsePayload).getBytes());
            resMessage.setQos(message.getQos());
            appMqttClient.publish(resTopic, resMessage);
            logger.info("APP响应主题发送成功: " + resTopic + ",消息载荷: " + new String(resMessage.getPayload()));
        } catch (Throwable ex) {
            logger.error("APP响应主题发送异常.", ex);
        }
    }

    /**
     * Token解析器
     */
    @Data
    private class ForwardToken {

        /**
         * APP端认证token
         */
        private String accessToken;

        /**
         * 来源
         */
        private String fromType;

        /**
         * 客户端唯一标识
         */
        private String clientKey;

        /**
         * nlcId
         */
        private String nlcId;

        public ForwardToken(String accessToken) {
            this.accessToken = accessToken;
            init();
        }

        private void init() {

            try {
                //解析token,由四部分组成 {来源}:{客户端唯一标识}:{nlcId}:{时间戳}
                String token = Base64.decodeStr(accessToken, "UTF-8");
                String[] tokenSplit = token.split(":");

                if (tokenSplit.length >= 3) {
                    fromType = tokenSplit[0];
                    clientKey = tokenSplit[1];
                    nlcId = tokenSplit[2];
                }
            } catch (Throwable ex) {
                logger.error("accessToken解析失败.", ex);
            }
        }

    }
}
