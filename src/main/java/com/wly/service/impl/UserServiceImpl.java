package com.wly.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.entity.Doctor;
import com.wly.entity.Office;
import com.wly.entity.Role;
import com.wly.entity.User;
import com.wly.exception.BizException;
import com.wly.mapper.UserMapper;
import com.wly.service.DoctorService;
import com.wly.service.OfficeService;
import com.wly.service.UserService;
import com.wly.utils.UserContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author anjsh
 * @date 2021/11/18 14:07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private OfficeService officeService;

    @Resource
    private DoctorService doctorService;

    @Override
    public User login(String username, String password) {
        User user = baseMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getUsername, username));
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new BizException("用户名或密码错误");
    }

    @Override
    public Map<String, Object> info() {
        Map<String, Object> idMap = new HashMap<>(4);

        User user = UserContext.getUser();
        idMap.put("role", user.getRole());
        if (user.getRole() == Role.DIRECTOR) {
            idMap.put("hosId", user.getOutId());
        }

        if (user.getRole() == Role.CHIEF) {
            Integer officeId = user.getOutId();
            Office office = Optional.ofNullable(officeService.getById(officeId)).orElseThrow(() -> new BizException("用户信息不存在"));
            idMap.put("hosId", office.getHosId());
            idMap.put("officeId", officeId);
        }

        if (user.getRole() == Role.DOCTOR) {
            Integer doctorId = user.getOutId();
            Doctor doctor = Optional.ofNullable(doctorService.getById(doctorId)).orElseThrow(() -> new BizException("用户信息不存在"));
            idMap.put("hosId", doctor.getHosId());
            idMap.put("officeId", doctor.getOfficeId());
            idMap.put("doctorId", doctorId);
        }

        return idMap;
    }

    @Override
    public Map<String, Object> info2() {
        Map<String, Object> idMap = new HashMap<>(4);

        User user = UserContext.getUser();
        if (user.getRole() == Role.DIRECTOR) {
            idMap.put("hos_id", user.getOutId());
        }

        if (user.getRole() == Role.CHIEF) {
            Integer officeId = user.getOutId();
            Office office = Optional.ofNullable(officeService.getById(officeId)).orElseThrow(() -> new BizException("用户信息不存在"));
            idMap.put("hos_id", office.getHosId());
            idMap.put("office_id", officeId);
        }

        if (user.getRole() == Role.DOCTOR) {
            Integer doctorId = user.getOutId();
            Doctor doctor = Optional.ofNullable(doctorService.getById(doctorId)).orElseThrow(() -> new BizException("用户信息不存在"));
            idMap.put("hos_id", doctor.getHosId());
            idMap.put("office_id", doctor.getOfficeId());
            idMap.put("doctor_id", doctorId);
        }

        return idMap;
    }
}
