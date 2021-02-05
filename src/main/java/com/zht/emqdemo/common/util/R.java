package com.zht.emqdemo.common.util;

import com.zht.emqdemo.common.Enum.RCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @describe 通用响应体
 * @author zht
 * @date 2021/1/18
 */
public class R extends HashMap<String, Object> {

    private R() {
        put("code", RCode.SUCCESS.getCode());
        put("msg", RCode.SUCCESS.getMessage());
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R error(RCode result) {
        R r = new R();
        r.put("code", result.getCode());
        r.put("msg", result.getMessage());
        return r;
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public R putData(Object value) {
        super.put("data", value);
        return this;
    }
}