package com.zht.emqdemo.common.util;

import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * 参数校验工具类
 * @author zht
 * @date 2021/1/18
 */
public class ValidateUtil {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 参数校验
     *
     * @param object 待校验对象
     */
    public static void validate(Object object) {
        Assert.notNull(object, "请求对象不能为null");
        Set<ConstraintViolation<Object>> constraintViolations = VALIDATOR.validate(object);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
            //若有错误返回异常，此处也可直接抛出异常
            throw new ValidationException(constraint.getMessage());
        }
    }
}
