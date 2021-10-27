package com.wly.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wly.entity.Hospital;

import java.util.List;

/**
 * 医院服务
 *
 * @author wly
 * @date 2021/10/27
 */
public interface HospitalService extends IService<Hospital> {

    /**
     * 找到医院通过名字
     *
     * @param hospitalName 医院的名字
     * @return {@link List}<{@link Hospital}>
     */
    List<Hospital> findByName(String hospitalName);

    /**
     * 通过id找到名字
     *
     * @param id id
     * @return {@link String}
     */
    String findNameById(Integer id);

    /**
     * 删除
     *
     * @param id id
     */
    void delete(Integer id);
}
