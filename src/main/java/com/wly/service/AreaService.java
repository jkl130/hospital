package com.wly.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wly.entity.Area;

import java.util.List;

/**
 * 区域服务
 *
 * @author anjsh
 * @date 2021/10/27 15:22
 */
public interface AreaService extends IService<Area> {

    /**
     * 根据省名称查找市
     *
     * @param areaName 省
     * @return {@link List}<{@link Area}>
     */
    List<Area> findAreaByLevel2(String areaName);


    /**
     * 根据省和市名称查找区
     *
     * @param areaName 省
     * @param cityName 市
     * @return {@link List}<{@link Area}>
     */
    List<Area> findAreaByLevel3(String areaName, String cityName);
}
