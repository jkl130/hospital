package com.wly.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.exception.Assert;
import com.wly.mapper.HospitalMapper;
import com.wly.entity.Hospital;
import com.wly.service.HospitalService;
import org.springframework.stereotype.Service;


/**
 * 医院服务实现
 *
 * @author wly
 * @date 2021/10/27
 */
@Service
public class HospitalServiceImpl extends ServiceImpl<HospitalMapper, Hospital> implements HospitalService {

    @Override
    public Hospital findHosByName(String hospitalName) {
        return Assert.notNull(baseMapper.selectOne(Wrappers.lambdaQuery(Hospital.class).eq(Hospital::getHospitalName, hospitalName)), "医院" + hospitalName + "不存在");
    }
}
