package com.zht.emqdemo.mqtt.receiver.system;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSONObject;
import com.zht.emqdemo.common.Enum.Online;
import com.zht.emqdemo.common.Enum.SnapshotType;
import com.zht.emqdemo.mqtt.payload.request.data.CabinetUploadReqData;
import com.zht.emqdemo.mqtt.payload.request.data.PushData;
import com.zht.emqdemo.mqtt.payload.sys.ConnectPayload;
import com.zht.emqdemo.mqtt.payload.sys.DisConnectPayload;
import com.zht.emqdemo.mqtt.receiver.common.ChangePushService;
import com.zht.emqdemo.rds.model.CabinetInfo;
import com.zht.emqdemo.rds.service.CabinetInfoService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


/**
 * @author zht
 * @describe Emq客户端上下线监听处理类
 * @date 2021/1/18
 */
@Service
public class ConnectedListener {

    private final static Logger logger = LoggerFactory.getLogger(ConnectedListener.class);

    @Autowired
    CabinetInfoService cabinetInfoService;

    @Autowired
    ChangePushService changePushService;

    /**
     * 监听客户端上下线事件
     *
     * @param topic   主题
     * @param message 消息体
     */
    @Async("asyncConnectedExecutor")
    public void connect(String topic, MqttMessage message) {
        logger.info("设备上下线主题接收成功: {}", topic);
        try {
            //解析主题
            String[] item = topic.split("/");
            //连接动作 连接/断开
            String action = item[item.length - 1];
            //获取连接状态枚举
            Online online = Online.getByAction(action);

            //解析获取用户名
            String username = null;
            if (online != null) {
                switch (online) {
                    case ONLINE:
                        ConnectPayload connectPayload = JSONObject.parseObject(new String(message.getPayload()), ConnectPayload.class);
                        username = connectPayload.getUsername();
                        break;
                    case OFFLINE:
                        DisConnectPayload disConnectPayload = JSONObject.parseObject(new String(message.getPayload()), DisConnectPayload.class);
                        username = disConnectPayload.getUsername();
                        break;
                    default:
                        return;
                }
            }

            logger.info("username: {}", username);
            //校验用户名,必须以固定前缀开头
            if (username == null) {
                //return;
            }

            //clientId
            String clientId = item[item.length - 2];
            //设备类型
            String type = clientId.split(":")[0];


            //处理数据库部分
            if (type.equals(SnapshotType.CABINET.getUpperCase())) {
                handleRds(clientId, online);
            }

        } catch (Throwable e) {
            logger.error("设备上下线主题处理异常: ", e);
        }
    }



    /**
     * 处理rds逻辑
     *
     * @param clientId 网关设备 客户端ID
     * @param online   网关设备 在线状态
     */
    private void handleRds(String clientId, Online online) {
        CabinetInfo byId = cabinetInfoService.findById(clientId.split(":")[1]);
        if (byId != null) {
            byId.setStatus(online.getCode());
            cabinetInfoService.update(byId);
            //TODO 在线状态改变也要推送
            CabinetUploadReqData cabinetUploadReqData = new CabinetUploadReqData();
            BeanUtil.copyProperties(byId, cabinetUploadReqData, CopyOptions.create().setIgnoreNullValue(true));
            changePushService.publish(clientId, Collections.singletonList(PushData.builder().type(SnapshotType.CABINET).payload(cabinetUploadReqData).build()), null);
        }
    }
}
