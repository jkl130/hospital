package com.wly.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wly.entity.Office;

import java.util.List;

/**
 * 科室服务
 *
 * @author wly
 * @date 2021/10/27
 */
public interface OfficeService extends IService<Office> {

    /**
     * 找到id通过名字
     *
     * @param officesName 科室的名字
     * @return {@link List}<{@link Integer}>
     */
    List<Integer> findIdsByName(String officesName);

    /**
     * 通过id找到名字
     *
     * @param id id
     * @return {@link String}
     */
    String findNameById(Integer id);

    /**
     * 找到id通过医院id和名称
     *
     * @param hosId       医院id
     * @param officesName 科室的名字
     * @return {@link Integer}
     */
    Integer findIdByHosIdAndName(Integer hosId, String officesName);
}
