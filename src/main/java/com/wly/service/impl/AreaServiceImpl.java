package com.wly.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.entity.Area;
import com.wly.mapper.AreaMapper;
import com.wly.service.AreaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域服务实现
 *
 * @author anjsh
 * @date 2021/10/27 15:23
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

    @Override
    public List<Area> findAreaByLevel2(String areaName) {
        return baseMapper.selectList(Wrappers.lambdaQuery(Area.class)
                // 等级为2
                .eq(Area::getLevel, 2)
                // 父级id是指定的名称
                .eq(Area::getParentId,
                        baseMapper.selectOne(Wrappers.lambdaQuery(Area.class)
                                .eq(Area::getAreaName, areaName)
                                .select(Area::getId)).getId()));
    }

    @Override
    public List<Area> findAreaByLevel3(String areaName, String cityName) {
        return baseMapper.selectList(Wrappers.lambdaQuery(Area.class)
                // 等级为3
                .eq(Area::getLevel, 3)
                .eq(Area::getParentId,
                        baseMapper.selectOne(Wrappers.lambdaQuery(Area.class)
                                // 等级为2
                                .eq(Area::getLevel, 2)
                                // 指定名称
                                .eq(Area::getAreaName, cityName)
                                // 父级id是指定的名称
                                .eq(Area::getParentId,
                                        baseMapper.selectOne(Wrappers.lambdaQuery(Area.class)
                                                .eq(Area::getAreaName, areaName)
                                                .select(Area::getId)))
                                .select(Area::getId)
                        ).getId()));
    }
}
