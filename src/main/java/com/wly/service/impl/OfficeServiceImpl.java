package com.wly.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.entity.Office;
import com.wly.exception.Assert;
import com.wly.exception.BizException;
import com.wly.mapper.OfficeMapper;
import com.wly.service.OfficeService;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Integer> findIdsByName(String officesName) {
        // 根据科室名称 前缀模糊查询
        return baseMapper.selectList(Wrappers.lambdaQuery(Office.class).likeRight(Office::getOfficesName, officesName).select(Office::getId)).stream().map(Office::getId).collect(Collectors.toList());
    }

    @Override
    public String findNameById(Integer id) {
        return Optional.ofNullable(baseMapper.selectOne(Wrappers.lambdaQuery(Office.class).eq(Office::getId, id).select(Office::getOfficesName)))
                .map(Office::getOfficesName)
                .orElseThrow(() -> new BizException("科室" + id + "不存在"));
    }

    @Override
    public Integer findIdByHosIdAndName(Integer hosId, String officesName) {
        return Assert.notNull(baseMapper.selectOne(Wrappers.lambdaQuery(Office.class).eq(Office::getHosId, hosId).eq(Office::getOfficesName, officesName)), "科室" + officesName + "不存在").getId();
    }
}
