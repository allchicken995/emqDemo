package com.zht.emqdemo.mqtt.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @describe mqtt通用请求消息载荷
 * @author zht
 * @date 2021/1/18
 */
@Data
public class RequestPayload {

    @NotBlank(message = "请求体不能为空")
    private String data;

    @NotBlank(message = "客户端令牌不能为空")
    private String clientToken;

    @NotNull(message = "毫秒级时间戳不能为空")
    private Long timestamp;
}
