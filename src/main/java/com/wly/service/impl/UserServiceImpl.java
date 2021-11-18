package com.wly.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.entity.User;
import com.wly.exception.BizException;
import com.wly.mapper.UserMapper;
import com.wly.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author anjsh
 * @date 2021/11/18 14:07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public User login(String username, String password) {
        User user = baseMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getUsername, username));
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new BizException("用户名或密码错误");
    }
}
