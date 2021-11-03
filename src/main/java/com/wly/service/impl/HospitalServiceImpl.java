package com.wly.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.entity.Doctor;
import com.wly.entity.Hospital;
import com.wly.entity.Office;
import com.wly.entity.OrderRecords;
import com.wly.exception.BizException;
import com.wly.mapper.DoctorMapper;
import com.wly.mapper.HospitalMapper;
import com.wly.mapper.OfficeMapper;
import com.wly.mapper.OrderRecordsMapper;
import com.wly.service.HospitalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


/**
 * 医院服务实现
 *
 * @author wly
 * @date 2021/10/27
 */
@Service
public class HospitalServiceImpl extends ServiceImpl<HospitalMapper, Hospital> implements HospitalService {

    @Resource
    private OfficeMapper officeMapper;

    @Resource
    private DoctorMapper doctorMapper;

    @Resource
    private OrderRecordsMapper orderRecordsMapper;


    @Override
    public List<Hospital> findByName(String hospitalName) {
        // 根据医院名称 前缀模糊查询
        return baseMapper.selectList(Wrappers.lambdaQuery(Hospital.class)
                .likeRight(Hospital::getHospitalName, hospitalName)
                .select(Hospital::getId, Hospital::getHospitalName)
                .last("limit 5"));
    }

    @Override
    public String findNameById(Integer id) {
        return Optional.ofNullable(baseMapper.selectOne(Wrappers.lambdaQuery(Hospital.class).eq(Hospital::getId, id).select(Hospital::getHospitalName)))
                .map(Hospital::getHospitalName)
                .orElseThrow(() -> new BizException("医院" + id + "不存在"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        // 科室
        officeMapper.delete(Wrappers.lambdaQuery(Office.class).eq(Office::getHosId, id));
        // 医生
        doctorMapper.delete(Wrappers.lambdaQuery(Doctor.class).eq(Doctor::getHosId, id));
        // 订单
        orderRecordsMapper.delete(Wrappers.lambdaQuery(OrderRecords.class).eq(OrderRecords::getHosId, id));
        // 医院
        baseMapper.deleteById(id);
    }
}
