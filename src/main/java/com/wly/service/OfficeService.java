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
     * 通过id找到名字
     *
     * @param id id
     * @return {@link String}
     */
    String findNameById(Integer id);

    /**
     * 找到科室通过医院id
     *
     * @param hosId 医院id
     * @return {@link Integer}
     */
    List<Office> findByHosId(Integer hosId);

    /**
     * 删除
     *
     * @param id id
     */
    void delete(Integer id);
}
