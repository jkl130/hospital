package com.wly.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wly.entity.Doctor;

import java.util.List;

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

    /**
     * 通过id找到名字
     *
     * @param doctorId 医生id
     * @return {@link String}
     */
    String findNameById(int doctorId);

    /**
     * 发现通过医院id和科室id
     *
     * @param hosId    居屋计划id
     * @param officeId 办公室id
     * @return {@link List}<{@link Doctor}>
     */
    List<Doctor> findByHosIdAndOfficeId(Integer hosId, Integer officeId);
}
