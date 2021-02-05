package com.zht.emqdemo.common.constant;

/**
 *
 * @describe 服务器订阅的主题
 * @author zht
 * @date 2021/1/18
 */
public class SubscribeTopic {

    /**
     * 设备上下线监听主题
     * $SYS/brokers/{mqtt节点}/clients/{client id }/{上下线状态}
     * EMQ---->服务器
     * 共享订阅
     */
    public final static String CLIENT_LISTENER_TOPIC = "$SYS/brokers/+/clients/+/+";

    /**
     * 设备响应控制指令
     * 主题前缀：clientId/common/service_response/{serviceType}
     * 设备---->服务器
     * 共享订阅
     */
    public final static String DEVICE_SERVICE_RESPONSE = "+/common/service_response/+";

    /**
     * 设备主动上报
     * 主题前缀：clientId/common/event/{eventType}
     * 设备---->服务器
     * 共享订阅
     */
    public final static String DEVICE_EVENT = "+/common/event/+";
}
