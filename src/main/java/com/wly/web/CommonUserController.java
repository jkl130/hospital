package com.wly.web;

import com.wly.entity.Doctor;
import com.wly.entity.Office;
import com.wly.entity.Role;
import com.wly.entity.User;
import com.wly.exception.BizException;
import com.wly.service.DoctorService;
import com.wly.service.OfficeService;
import com.wly.service.UserService;
import com.wly.utils.JwtTokenUtils;
import com.wly.utils.UserContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


/**
 * 用户控制器
 *
 * @author wly
 * @date 2021/10/27
 */
@RestController
@RequestMapping("user")
public class CommonUserController {

    @Resource
    private UserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private OfficeService officeService;

    @Resource
    private DoctorService doctorService;

    /**
     * 用户登陆验证
     */
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> dataMap) {
        Map<String, Object> payload = new HashMap<>(4);
        // 放入用户信息
        payload.put("userInfo", userService.login(dataMap.get("username"), dataMap.get("password")));
        // 生成token
        return JwtTokenUtils.generateTokenUser(UUID.randomUUID().toString(), payload, 604800000L);
    }

    @GetMapping("info")
    public Map<String, Integer> info() {
        Map<String, Integer> idMap = new HashMap<>(4);
        User user = UserContext.getUser();

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

    @GetMapping("gpw")
    public String gpw(String rp) {
        return passwordEncoder.encode(rp);
    }
}
