package com.anjsh.web;

import cn.hutool.core.bean.BeanUtil;
import com.anjsh.entity.CommonUser;
import com.anjsh.utils.JwtTokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            Map<String, Object> parseToken = JwtTokenUtils.parseToken(request.getHeader(JwtTokenUtils.AUTHORIZE_TOKEN));
            request.setAttribute("userInfo", BeanUtil.mapToBean((Map<?, ?>) parseToken.get("userInfo"), CommonUser.class,false,null));
            return true;
        } catch (Exception e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
    }
}
