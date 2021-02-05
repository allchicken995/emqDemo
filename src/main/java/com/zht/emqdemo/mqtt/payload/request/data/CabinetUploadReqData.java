package com.zht.emqdemo.mqtt.payload.request.data;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CabinetUploadReqData {

    private Integer status;

    private Integer powerOne;

    private Integer powerTwo;

    private Integer powerThree;

    private Integer powerFour;

    private BigDecimal temperatureOne;

    private BigDecimal temperatureTwo;

    private BigDecimal temperatureThree;

    private BigDecimal temperatureFour;

    private Integer valueOne;

    private Integer valueTwo;

    private Integer alarmValueOne;

    private Integer alarmValueTwo;

}
