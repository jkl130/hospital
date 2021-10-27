package com.wly.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.mapper.DoctorMapper;
import com.wly.entity.Doctor;
import com.wly.service.DoctorService;
import org.springframework.stereotype.Service;

/**
 * 医生服务实现
 *
 * @author wly
 * @date 2021/10/27
 */
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements DoctorService {
}
