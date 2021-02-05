package com.zht.emqdemo.mqtt.payload.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.zht.emqdemo.mqtt.payload.request.data.PushData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @describe 状态变更推送消息体
 * @author zht
 * @date 2021/1/18
 */
@Data
@AllArgsConstructor
public class ChangePayload {

    /**
     * 推送消息体
     */
    private Map<String, List<Object>> data;

    /**
     * 消息唯一token
     */
    @JSONField(name = "client_token")
    private String clientToken;

    /**
     * 毫秒级时间戳
     */
    private Long timestamp;

    /**
     * 构造快照状态变更推送载荷
     *
     * @param devices 请求体
     * @return 推送体
     */
    public static Map<String, List<Object>> getData(List<PushData> devices) {

        Map<String, List<Object>> resData = new HashMap<>(2);

        devices.forEach(e -> {
            List<Object> snapshots = resData.get(e.getType().getLowerCase());
            if (snapshots == null) {
                snapshots = new ArrayList<>();
            }
            snapshots.add(e.getPayload());
            resData.put(e.getType().getLowerCase(), snapshots);
        });
        return resData;
    }
}
