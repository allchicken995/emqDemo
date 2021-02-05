package com.zht.emqdemo.mqtt.payload.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @describe 设备向服务器响应的消息载荷
 * @author zht
 * @date 2021/1/18
 */
@Data
public class ForwardPayload {

    @NotNull(message = "响应码不能为空")
    private Integer code;

    @NotBlank(message = "客户端令牌不能为空")
    @JSONField(name = "client_token")
    private String clientToken;

    @NotNull(message = "毫秒级时间戳不能为空")
    private Long timestamp;

}
