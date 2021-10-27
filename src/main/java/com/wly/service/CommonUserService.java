package com.wly.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wly.entity.CommonUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 常见的用户服务
 *
 * @author wly
 * @date 2021/10/27
 */
public interface CommonUserService extends IService<CommonUser> {

    /**
     * 登录
     *
     * @param userEmail    用户的电子邮件
     * @param userPassword 用户密码
     * @param request      请求
     * @return {@link CommonUser}
     */
    CommonUser login(String userEmail, String userPassword, HttpServletRequest request);
}
