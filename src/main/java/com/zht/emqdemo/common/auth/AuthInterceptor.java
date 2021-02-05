package com.zht.emqdemo.common.auth;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod method = (HandlerMethod) handler;
        if (!needLogin(method)) {
            return true;
        }

        String token = getToken(request);
        if (!StringUtils.hasText(token)) {
            throw new NoTokenAuthException();
        }

        //TODO 需要去redis判断一下token
        if (false) {
            throw new TokenAuthException();
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        
    }

    /**
     * 如果存放token的方式发生变更，修改此方法的逻辑就好
     *
     * @param request
     * @return
     */
    private String getToken(HttpServletRequest request) {
        return request.getHeader("token");
    }

    private boolean needLogin(HandlerMethod method) {
        // 方法上有注解以方法上的注解为准。匿名访问优先级最高
        if (method.getMethodAnnotation(Anonymity.class) != null) {
            return false;
        }

        if (method.getMethodAnnotation(MustLogin.class) != null) {
            return true;
        }

        // 方法上没有注解，看类上有没有注解。匿名访问优先级最高
        Class<?> beanType = method.getBeanType();
        if (beanType.getAnnotation(Anonymity.class) != null) {
            return false;
        }

        if (beanType.getAnnotation(MustLogin.class) != null) {
            return true;
        }

        // 如果什么注解都没有，就是无需登录
        return false;
    }
}
