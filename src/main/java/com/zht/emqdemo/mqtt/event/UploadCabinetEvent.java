package com.zht.emqdemo.mqtt.event;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.zht.emqdemo.common.Enum.SnapshotType;
import com.zht.emqdemo.common.constant.EventType;
import com.zht.emqdemo.mqtt.payload.request.data.CabinetUploadReqData;
import com.zht.emqdemo.mqtt.payload.request.data.PushData;
import com.zht.emqdemo.rds.model.CabinetInfo;
import com.zht.emqdemo.rds.model.UploadRecord;
import com.zht.emqdemo.rds.service.CabinetInfoService;
import com.zht.emqdemo.rds.service.UploadRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class UploadCabinetEvent extends BaseDeviceEvent {

    @Resource
    CabinetInfoService cabinetInfoService;

    @Resource
    UploadRecordService uploadRecordService;

    @Override
    public String getEventType() {
        return EventType.UPLOAD_CABINET;
    }

    @Override
    public void doEvent(EventContext eventContext) {

        //数据解析
        CabinetUploadReqData reqData = eventContext.getEventData(CabinetUploadReqData.class);

        String deviceKey = eventContext.getDeviceKey().split(":")[1];

        CabinetInfo cabinetInfo = new CabinetInfo();
        BeanUtil.copyProperties(reqData, cabinetInfo, CopyOptions.create().setIgnoreNullValue(true));
        cabinetInfo.setId(deviceKey);

        cabinetInfoService.update(cabinetInfo);

        UploadRecord uploadRecord = new UploadRecord();
        BeanUtil.copyProperties(reqData, uploadRecord, CopyOptions.create().setIgnoreNullValue(true));
        uploadRecord.setUploadDate(LocalDateTime.now());
        uploadRecord.setCabinetId(deviceKey);

        int first = new Random().nextInt(9);
        int rnd = UUID.randomUUID().toString().hashCode();
        if(rnd < 0){
            rnd = -rnd;
        }

        uploadRecord.setId(first+String.format("%010d", rnd));
        uploadRecordService.insert(uploadRecord);

        eventContext.getPushData().add(PushData.builder().type(SnapshotType.CABINET).payload(reqData).build());

    }
}
