package com.wly.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wly.entity.Hospital;

/**
 * 医院服务
 *
 * @author wly
 * @date 2021/10/27
 */
public interface HospitalService extends IService<Hospital> {

    /**
     * 通过医院名称查找医院
     *
     * @param hospitalName 医院的名字
     * @return {@link Hospital}
     */
    Hospital findHosByName(String hospitalName);
}
