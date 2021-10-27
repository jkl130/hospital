package com.wly.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.mapper.HelpQAMapper;
import com.wly.entity.HelpQA;
import com.wly.service.HelpQAService;
import org.springframework.stereotype.Service;

/**
 * 帮助qa服务实现
 *
 * @author wly
 * @date 2021/10/27
 */
@Service
public class HelpQAServiceImpl extends ServiceImpl<HelpQAMapper, HelpQA> implements HelpQAService {
}
