package com.anjsh.utils;

import com.anjsh.entity.CommonUser;
import com.anjsh.exception.BizException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

public class UserContext {

    private UserContext() {
    }

    public static CommonUser getCommonUser() {
        return (CommonUser) Optional.ofNullable(((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest().getAttribute("userInfo")).orElseThrow(() -> new BizException("用户未登录"));
    }

}
