package com.wly.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wly.entity.Doctor;

/**
 * 医生服务
 *
 * @author wly
 * @date 2021/10/27
 */
public interface DoctorService extends IService<Doctor> {

    /**
     * 删除
     *
     * @param id id
     */
    void delete(Integer id);
}
