package com.wly.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.mapper.OrderRecordsMapper;
import com.wly.entity.OrderRecords;
import com.wly.service.OrderRecordsService;
import org.springframework.stereotype.Service;

/**
 * 订单记录服务实现
 *
 * @author wly
 * @date 2021/10/27
 */
@Service
public class OrderRecordsServiceImpl extends ServiceImpl<OrderRecordsMapper, OrderRecords> implements OrderRecordsService {
}
