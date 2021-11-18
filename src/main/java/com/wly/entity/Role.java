package com.wly.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author anjsh
 * @date 2021/11/18 13:58
 */
@AllArgsConstructor
@Getter
public enum Role {

    /**
     * 管理员
     */
    ADMIN("0"),

    /**
     * 院长
     */
    DIRECTOR("1"),

    /**
     * 主任
     */
    CHIEF("2"),

    /**
     * 医生
     */
    DOCTOR("3");

    @EnumValue
    private final String code;

    @Override
    public String toString() {
        return code;
    }

}
