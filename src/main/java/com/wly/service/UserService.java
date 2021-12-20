package com.wly.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wly.entity.User;

import java.util.Map;

/**
 * @author anjsh
 * @date 2021/11/18 14:07
 */
public interface UserService extends IService<User> {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return {@link User}
     */
    User login(String username, String password);

    /**
     * 信息
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> info();

    /**
     * 信息
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> info2();
}
