package com.anjsh.web;

import com.anjsh.entity.CommonUser;
import com.anjsh.service.CommonUserService;
import com.anjsh.utils.JwtTokenUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @author sfturing
 * @date 2017年5月6日
 */
@RestController
@RequestMapping("user")
public class CommonUserController {
    @Resource
    private CommonUserService commonUserService;


    /**
     * 用户登陆验证
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody Map<String, String> dataMap, HttpServletRequest request) {
        CommonUser user = commonUserService.login(dataMap.get("username"), dataMap.get("password"), request);
        Map<String, Object> payload = new HashMap<>(4);
        payload.put("userInfo", user);
        return JwtTokenUtils.generateTokenUser(UUID.randomUUID().toString(), payload, 604800000L);
    }
}
