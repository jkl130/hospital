package com.wly.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.mapper.CommonUserMapper;
import com.wly.entity.CommonUser;
import com.wly.exception.BizException;
import com.wly.service.CommonUserService;
import com.wly.utils.GetIP;
import com.wly.utils.MD5;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


/**
 * 普通用户服务实现类
 *
 * @author wly
 * @date 2021/10/27
 */
@Service
public class CommonUserServiceImpl extends ServiceImpl<CommonUserMapper, CommonUser> implements CommonUserService {

    /**
     * 登录
     *
     * @param userEmail    用户的电子邮件
     * @param userPassword 用户密码
     * @param request      请求
     * @return {@link CommonUser}
     */
    @Override
    public CommonUser login(String userEmail, String userPassword, HttpServletRequest request) {
        // 1.根据用户邮箱查找用户
        CommonUser commonUser = baseMapper.selectOne(Wrappers.lambdaQuery(CommonUser.class).eq(CommonUser::getUserEmail, userEmail));

        if (commonUser == null || !MD5.getMD5(userPassword).equals(commonUser.getUserPassword())) {
            // 用户不存在或密码不正确
            throw new BizException("用户名或密码错误");
        }

        // 2.记录用户的登录时间和ip
        commonUser = new CommonUser();
        commonUser.setUserId(commonUser.getUserId());
        commonUser.setLastLoginTime(LocalDateTimeUtil.formatNormal(LocalDateTime.now()));
        commonUser.setLastLoginIp(GetIP.getIpAddr(request));
        baseMapper.updateById(commonUser);

        // 3.返回结果
        return commonUser;
    }
}
