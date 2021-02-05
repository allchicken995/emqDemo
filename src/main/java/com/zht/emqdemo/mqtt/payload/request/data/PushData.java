package com.zht.emqdemo.mqtt.payload.request.data;

import com.zht.emqdemo.common.Enum.SnapshotType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PushData {

    /**
     * 推送设备类型
     */
    private SnapshotType type;

    /**
     * 推送载荷
     */
    private Object payload;

}
