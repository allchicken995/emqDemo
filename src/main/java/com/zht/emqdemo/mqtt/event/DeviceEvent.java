package com.zht.emqdemo.mqtt.event;

/**
 * 设备事件接口
 *
 * @author zht
 * @date 2021/1/18
 */
public interface DeviceEvent {

    /**
     * 事件执行方法
     * 执行整个事件流程，参数校验，业务处理，事项响应
     *
     * @param context 上下文
     */
    void eventHandle(EventContext context);

    /**
     * 事件业务处理方法
     * 仅执行业务处理，主要用于代理方法的内部条用
     *
     * @param context 上下文
     */
    void doEvent(EventContext context);

    /**
     * 事件匹配处理
     *
     * @param context 上下文
     * @return true 匹配成功 false 匹配失败
     */
    boolean match(EventContext context);
}
