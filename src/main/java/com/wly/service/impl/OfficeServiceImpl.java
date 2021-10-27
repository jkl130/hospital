package com.wly.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.entity.Doctor;
import com.wly.entity.Office;
import com.wly.entity.OrderRecords;
import com.wly.exception.BizException;
import com.wly.mapper.DoctorMapper;
import com.wly.mapper.OfficeMapper;
import com.wly.mapper.OrderRecordsMapper;
import com.wly.service.OfficeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 科室服务实现
 *
 * @author wly
 * @date 2021/10/27
 */
@Service
public class OfficeServiceImpl extends ServiceImpl<OfficeMapper, Office> implements OfficeService {

    @Resource
    private DoctorMapper doctorMapper;

    @Resource
    private OrderRecordsMapper orderRecordsMapper;

    @Override
    public String findNameById(Integer id) {
        return Optional.ofNullable(baseMapper.selectOne(Wrappers.lambdaQuery(Office.class).eq(Office::getId, id).select(Office::getOfficesName)))
                .map(Office::getOfficesName)
                .orElseThrow(() -> new BizException("科室" + id + "不存在"));
    }

    @Override
    public List<Office> findByHosIdAndName(Integer hosId, String officesName) {
        return baseMapper.selectList(Wrappers.lambdaQuery(Office.class)
                .eq(Office::getHosId, hosId)
                // 根据科室名称 前缀模糊查询
                .likeRight(Office::getOfficesName, officesName)
                .select(Office::getId).select(Office::getOfficesName));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        List<Integer> doctorIds = doctorMapper.selectList(Wrappers.lambdaQuery(Doctor.class).eq(Doctor::getOfficeId, id).select(Doctor::getId)).stream().map(Doctor::getId).collect(Collectors.toList());
        if (!doctorIds.isEmpty()) {
            // 医生
            doctorMapper.deleteBatchIds(doctorIds);
            // 订单
            orderRecordsMapper.delete(Wrappers.lambdaQuery(OrderRecords.class).in(OrderRecords::getDoctorId, doctorIds));
        }

        // 科室
        baseMapper.deleteById(id);
    }
}
