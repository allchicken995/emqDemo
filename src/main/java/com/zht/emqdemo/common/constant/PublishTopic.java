package com.zht.emqdemo.common.constant;

/**
 *
 * @describe 服务器发布的主题
 * @author zht
 * @date 2021/1/18
 */
public class PublishTopic {

    /**
     * 状态变更主题
     * 主题前缀：clientId/common/event_response/{设备类型}
     * 服务器---->APP
     */
    public final static String CHANGE = "+/app/event/change";
}
