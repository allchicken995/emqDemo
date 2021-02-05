package com.zht.emqdemo.mqtt.event;

import cn.hutool.core.collection.CollectionUtil;
import com.zht.emqdemo.common.config.emq.DeviceMqttProperties;
import com.zht.emqdemo.common.Enum.ResultCode;
import com.zht.emqdemo.common.util.ValidateUtil;
import com.zht.emqdemo.mqtt.payload.request.RequestPayload;
import com.zht.emqdemo.mqtt.receiver.common.ChangePushService;
import com.zht.emqdemo.mqtt.receiver.common.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.Resource;
import javax.validation.ValidationException;

/**
 * 设备事件基类
 *
 * @author zht
 * @date 2021/1/18
 */
public abstract class BaseDeviceEvent implements DeviceEvent {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    EventRespPublisher eventRespPublisher;

    @Resource
    ChangePushService changePushService;

    @Resource
    CommonService commonService;

    @Resource
    DeviceMqttProperties properties;

    /**
     * 获取事件类型
     *
     * @return 事件类型
     */
    public abstract String getEventType();

    /**
     * 事件业务处理方法
     * 仅执行业务处理，主要用于代理方法的内部条用
     *
     * @param eventContext 上下文
     */
    @Override
    public abstract void doEvent(EventContext eventContext);

    @Override
    public boolean match(EventContext eventContext) {

        String eventType = eventContext.getEventType();

        return eventType != null && eventType.equals(getEventType());
    }

    @Override
    @Async("asyncEventExecutor")
    public void eventHandle(EventContext eventContext) {

        try {
            if (preEvent(eventContext)) {

                logger.info("事件业务处理开始...");

                doEvent(eventContext);

                if (!eventContext.isHasSubEx()) {
                    eventRespPublisher.ok(eventContext);
                } else {
                    eventRespPublisher.error(ResultCode.EXCEPTION, eventContext);
                }

                logger.info("事件业务处理完成...");

                afterEvent(eventContext);
            }
        } catch (Throwable ex) {
            logger.error("事件处理异常", ex);
            eventRespPublisher.error(ResultCode.EXCEPTION, eventContext);
        }

    }

    protected void afterEvent(EventContext eventContext) {
        //推送状态变更
        if (CollectionUtil.isNotEmpty(eventContext.getPushData())) {
            changePushService.publish(eventContext.getClientId(), eventContext.getPushData(), eventContext.getRequestPayload().getClientToken());
        }
    }

    protected boolean preEvent(EventContext eventContext) {

        RequestPayload payload;

        logger.info("主题: " + eventContext.getTopic() + ",消息载荷: " + new String(eventContext.getMqttMessage().getPayload()));

        try {
            eventContext.parseRequestPayload();
            payload = eventContext.getRequestPayload();
        } catch (Throwable ex) {
            logger.error("事件载荷解析异常：" + eventContext.getTopic(), ex);
            eventRespPublisher.error(ResultCode.PARAM_ERROR, eventContext);
            return false;
        }

        try {
            ValidateUtil.validate(payload);
        } catch (ValidationException ex) {
            logger.error("载荷格式校验异常：" + eventContext.getTopic(), ex);
            eventRespPublisher.error(ResultCode.PARAM_ERROR, eventContext);
            return false;
        }

        //判断消息校验开关是否打开
        if (properties.isCheck()) {
            if (commonService.isExpire(payload.getTimestamp())) {
                logger.error("事件消息已过期终止.");
                eventRespPublisher.error(ResultCode.MESSAGE_EXPIRE, eventContext);
                return false;
            }

            if (commonService.isNotUnique(payload.getClientToken())) {
                logger.error("事件消息重复终止.");
                return false;
            }
        }

        return true;
    }

}

