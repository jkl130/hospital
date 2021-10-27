package com.wly.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.entity.Doctor;
import com.wly.entity.OrderRecords;
import com.wly.mapper.DoctorMapper;
import com.wly.mapper.OrderRecordsMapper;
import com.wly.service.DoctorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 医生服务实现
 *
 * @author wly
 * @date 2021/10/27
 */
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements DoctorService {

    @Resource
    private OrderRecordsMapper orderRecordsMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {

        // 订单
        orderRecordsMapper.delete(Wrappers.lambdaQuery(OrderRecords.class).eq(OrderRecords::getDoctorId, id));
        // 医生
        baseMapper.deleteById(id);
    }
}
