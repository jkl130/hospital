package com.wly.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.mapper.NoticeMapper;
import com.wly.entity.Notice;
import com.wly.service.NoticeService;
import org.springframework.stereotype.Service;

/**
 * 通知服务实现
 *
 * @author wly
 * @date 2021/10/27
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
}
