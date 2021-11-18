package com.wly.utils;

import cn.hutool.core.bean.BeanUtil;
import com.wly.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登录处理程序拦截器
 *
 * @author wly
 * @date 2021/10/27
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            // 从header中提取token并解析
            Map<String, Object> parseToken = JwtTokenUtils.parseToken(request.getHeader(JwtTokenUtils.AUTHORIZE_TOKEN));
            // 将用户信息放入request中
            request.setAttribute("userInfo", BeanUtil.mapToBean((Map<?, ?>) parseToken.get("userInfo"), User.class, false, null));
            return true;
        } catch (Exception e) {
            // 解析失败->未登录 返回403
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
    }
}
