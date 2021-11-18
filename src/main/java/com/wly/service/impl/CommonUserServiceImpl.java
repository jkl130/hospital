package com.wly.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.entity.CommonUser;
import com.wly.mapper.CommonUserMapper;
import com.wly.service.CommonUserService;
import org.springframework.stereotype.Service;


/**
 * 普通用户服务实现类
 *
 * @author wly
 * @date 2021/10/27
 */
@Service
public class CommonUserServiceImpl extends ServiceImpl<CommonUserMapper, CommonUser> implements CommonUserService {
}
