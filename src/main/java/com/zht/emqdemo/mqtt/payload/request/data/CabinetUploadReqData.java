package com.zht.emqdemo.mqtt.payload.request.data;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CabinetUploadReqData {

    private String id;

    private Integer status;

    private Integer powerOne;

    private Integer powerTwo;

    private Integer powerThree;

    private BigDecimal temperatureOne;

    private BigDecimal temperatureTwo;

    private BigDecimal temperatureThree;

    private Integer valueOne;

    private Integer valueTwo;

    private BigDecimal valueThree;

    private BigDecimal valueFour;

    private String deviceName;

    private String deviceKey;

    private String tenantId;

    private Integer alarmValueOne;

    private Integer alarmValueTwo;

}
