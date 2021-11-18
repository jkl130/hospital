package com.wly.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    ADMIN("0", Collections.emptyList()),

    /**
     * 院长
     */
    DIRECTOR("1",
            Arrays.asList(
                    "/doctor",
                    "/office",
                    "/hos/find",
                    "/hos/update",
                    "/order",
                    "/comment",
                    "/notice/list",
                    "/notice/add")),

    /**
     * 主任
     */
    CHIEF("2", Arrays.asList(
            "/doctor",
            "/office/find",
            "/office/update",
            "/hos/find",
            "/order",
            "/comment",
            "/notice/list"
    )),

    /**
     * 医生
     */
    DOCTOR("3", Arrays.asList(
            "/doctor/find",
            "/office/find",
            "/hos/find",
            "/order",
            "/comment",
            "/notice/list"
    ));

    @EnumValue
    private final String code;

    private final List<String> paths;

    @Override
    public String toString() {
        return code;
    }

    public boolean isAllow(String path) {
        if (this == Role.ADMIN) {
            return true;
        }
        return paths.stream().anyMatch(path::startsWith);
    }

}
