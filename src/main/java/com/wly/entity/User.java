package com.wly.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author anjsh
 * @date 2021/11/18 13:55
 */
@TableName("user")
@Data
public class User {

    private Integer id;

    private String username;

    private String password;

    private Role role;

    private Integer outId;

}