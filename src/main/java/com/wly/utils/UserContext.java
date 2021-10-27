package com.wly.utils;

import com.wly.entity.CommonUser;
import com.wly.exception.BizException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * 用户上下文
 *
 * @author wly
 * @date 2021/10/27
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserContext {

    public static CommonUser getCommonUser() {
        // 从request中获取用户信息
        return (CommonUser) Optional.ofNullable(((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest().getAttribute("userInfo")).orElseThrow(() -> new BizException("用户未登录"));
    }
}
