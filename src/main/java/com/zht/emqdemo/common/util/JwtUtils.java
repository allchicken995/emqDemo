package com.zht.emqdemo.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 */
@Configuration
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expire}")
    private long expire;

    /**
     * 生成设备jwt
     */
    public String createToken(String username) {
        //签发时间
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        Map<String, Object> map = new HashMap<>(2);
        //加密算法
        map.put("alg", "HS256");
        //加密类型
        map.put("typ", "JWT");

        return JWT.create()
                .withHeader(map)
                .withClaim("username", username)
                .withIssuedAt(nowDate)
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 刷新设备token
     *
     * @param token 旧token
     * @return 新token
     */
    public String refreshToken(String token) {
        DecodedJWT jwt;
        try {
            jwt = JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        } catch (Exception e) {
            //token过期或格式错误，返回为空
            return null;
        }
        String username = jwt.getClaim("username").asString();
        return createToken(username);
    }
}