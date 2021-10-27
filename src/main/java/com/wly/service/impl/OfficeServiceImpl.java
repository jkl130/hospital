package com.wly.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.mapper.OfficeMapper;
import com.wly.entity.Office;
import com.wly.service.OfficeService;
import org.springframework.stereotype.Service;

/**
 * 科室服务实现
 *
 * @author wly
 * @date 2021/10/27
 */
@Service
public class OfficeServiceImpl extends ServiceImpl<OfficeMapper, Office> implements OfficeService {
}
