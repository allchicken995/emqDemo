package com.zht.emqdemo.mqtt.receiver.common;

import com.zht.emqdemo.common.config.emq.DeviceMqttProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @describe 设备的通用方法
 * @author zht
 * @date 2021/1/18
 */
@Service
public class CommonService {

    @Resource
    DeviceMqttProperties deviceMqttProperties;

    @Resource
    RedisTemplate<String, String> redisTemplate;

    /**
     * 验证mqtt消息唯一性
     *
     * @param clientToken 消息唯一标识
     * @return true 唯一 false 不唯一
     */
    public boolean isNotUnique(String clientToken) {
        String key = "clientToken:" + clientToken;
        String value = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(value)) {
            //不存在,消息唯一,返回false
            redisTemplate.opsForValue().set(key, clientToken, 1, TimeUnit.HOURS);
            return false;
        } else {
            //存在,消息不唯一,返回true
            return true;
        }
    }

    /**
     * 校验mqtt消息是否过期
     *
     * @param timestamp 消息发送时间
     * @return true 过期 false 未过期
     */
    public boolean isExpire(Long timestamp) {
        return System.currentTimeMillis() - timestamp >= deviceMqttProperties.getExpire() * 1000;
    }
}
