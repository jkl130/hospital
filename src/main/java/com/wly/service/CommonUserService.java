package com.wly.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wly.entity.CommonUser;

/**
 * 常见的用户服务
 *
 * @author wly
 * @date 2021/10/27
 */
public interface CommonUserService extends IService<CommonUser> {

    /**
     * 通过id获取用户名
     *
     * @param id id
     * @return {@link String}
     */
    String getUserNameById(Integer id);
}
