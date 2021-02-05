package com.zht.emqdemo.mqtt.payload.response;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.zht.emqdemo.common.Enum.ResultCode;
import com.zht.emqdemo.mqtt.payload.request.ForwardPayload;
import lombok.Data;

/**
 * @describe mqtt通用响应消息载荷
 * @author zht
 * @date 2021/1/18
 */
@Data
public class ResponsePayload {

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 消息令牌
     */
    @JSONField(name = "client_token")
    private String clientToken;

    /**
     * 毫秒级时间戳
     */
    private Long timestamp;

    /**
     * 私有无参构造
     */
    private ResponsePayload() {

    }

    /**
     * 私有有参构造
     *
     * @param code        响应码
     * @param clientToken 消息令牌
     * @param timestamp   毫秒级时间戳
     */
    private ResponsePayload(Integer code, String clientToken, Long timestamp) {
        this.code = code;
        this.clientToken = clientToken;
        this.timestamp = timestamp;
    }

    /**
     * 构造一个成功响应
     *
     * @param clientToken 消息令牌
     * @param timestamp   毫秒级时间戳
     * @return 成功响应类
     */
    public static ResponsePayload ok(String clientToken, Long timestamp) {
        return new ResponsePayload(ResultCode.SUCCESS.getCode(), clientToken, timestamp);
    }

    /**
     * 构造一个失败响应
     *
     * @param code        失败响应码
     * @param clientToken 消息令牌
     * @param timestamp   毫秒级时间戳
     * @return 失败响应类
     */
    public static ResponsePayload fail(Integer code, String clientToken, Long timestamp) {
        return new ResponsePayload(code, clientToken, timestamp);
    }

    /**
     * 将转发响应类转换为通用响应类
     *
     * @param forwardPayload 转发响应类
     * @return 通用响应类
     */
    public static ResponsePayload getInstance(ForwardPayload forwardPayload) {
        ResponsePayload payload = new ResponsePayload();
        BeanUtil.copyProperties(forwardPayload, payload);
        return payload;
    }
}
