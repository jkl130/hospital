package com.wly.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.entity.Doctor;
import com.wly.entity.OrderRecords;
import com.wly.exception.BizException;
import com.wly.mapper.DoctorMapper;
import com.wly.mapper.OrderRecordsMapper;
import com.wly.service.DoctorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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

    @Override
    public String findNameById(int doctorId) {
        return Optional.ofNullable(baseMapper.selectOne(Wrappers.lambdaQuery(Doctor.class).eq(Doctor::getId, doctorId).select(Doctor::getDoctorName)))
                .map(Doctor::getDoctorName)
                .orElseThrow(() -> new BizException("医生" + doctorId + "不存在"));
    }

    @Override
    public List<Doctor> findByHosIdAndOfficeId(Integer hosId, Integer officeId) {
        return baseMapper.selectList(Wrappers.lambdaQuery(Doctor.class).eq(Doctor::getHosId, hosId).eq(Doctor::getOfficeId, officeId));
    }
}
