package com.wly.utils;


import com.wly.dto.Response;

import java.lang.annotation.*;

/**
 * 如果不希望返回结果被{@link Response} 封装,在类或者方法上添加该注解
 *
 * @author anjsh
 * @date 2021/4/12 16:03
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAware {
}
