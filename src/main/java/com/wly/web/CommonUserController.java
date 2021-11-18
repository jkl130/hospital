package com.wly.web;

import com.wly.service.UserService;
import com.wly.utils.JwtTokenUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 用户登陆验证
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody Map<String, String> dataMap) {
        Map<String, Object> payload = new HashMap<>(4);
        // 放入用户信息
        payload.put("userInfo", userService.login(dataMap.get("username"), dataMap.get("password")));
        // 生成token
        return JwtTokenUtils.generateTokenUser(UUID.randomUUID().toString(), payload, 604800000L);
    }
}
