package com.wly.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.entity.Hospital;
import com.wly.exception.Assert;
import com.wly.exception.BizException;
import com.wly.mapper.HospitalMapper;
import com.wly.service.HospitalService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    @Override
    public List<Integer> findIdsByName(String hospitalName) {
        // 根据医院名称 前缀模糊查询
        return baseMapper.selectList(Wrappers.lambdaQuery(Hospital.class).likeRight(Hospital::getHospitalName, hospitalName).select(Hospital::getId)).stream().map(Hospital::getId).collect(Collectors.toList());
    }

    @Override
    public String findNameById(Integer id) {
        return Optional.ofNullable(baseMapper.selectOne(Wrappers.lambdaQuery(Hospital.class).eq(Hospital::getId, id).select(Hospital::getHospitalName)))
                .map(Hospital::getHospitalName)
                .orElseThrow(() -> new BizException("医院" + id + "不存在"));
    }
}
