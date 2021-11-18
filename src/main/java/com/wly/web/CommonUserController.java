package com.wly.web;

import com.wly.service.UserService;
import com.wly.utils.JwtTokenUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
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

    @GetMapping("gpw")
    public String gpw(String rp) {
        return passwordEncoder.encode(rp);
    }
}
