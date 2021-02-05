package com.zht.emqdemo.mqtt.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 设备事件代理
 *
 * @author zht
 * @date 2021/1/18
 */
@Component
public class DeviceEventProxy {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private List<DeviceEvent> events;

    /**
     * 代理事件执行
     * 执行整个事件流程，参数校验，业务处理，事项响应
     *
     * @param eventContext 上下文
     */
    public void eventHandle(EventContext eventContext) {
        DeviceEvent event = getEvent(eventContext);
        if (event != null) {
            logger.info("匹配事件处理器成功: " + event.getClass().getSimpleName());
            event.eventHandle(eventContext);
        } else {
            throw new RuntimeException("匹配事件处理器失败: topic=" + eventContext.getTopic());
        }
    }

    private DeviceEvent getEvent(EventContext eventContext) {
        for (DeviceEvent event : events) {
            if (event.match(eventContext)) {
                return event;
            }
        }
        return null;
    }

}
