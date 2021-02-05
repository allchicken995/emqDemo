package com.zht.emqdemo.common.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * RestTemplate配置类
 *
 * @author 黄祖珏
 * @date 2020-06-16 9:22
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .additionalMessageConverters(
                        new StringHttpMessageConverter(StandardCharsets.UTF_8),
                        new FormHttpMessageConverter())
                // 读超时时间（毫秒）
                .setReadTimeout(Duration.ofMillis(10000))
                // 连接超时时间（毫秒）
                .setConnectTimeout(Duration.ofMillis(30000))
                .build();
    }
}
