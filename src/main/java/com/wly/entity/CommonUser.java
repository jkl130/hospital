package com.wly.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;


/**
 * 用户
 *
 * @author wly
 * @date 2021/10/27
 */
@TableName("commonuser")
@Data
public class CommonUser {

    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户身份证号
     */
    private String userIdenf;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户性别
     */
    private String userSex;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户的电子邮件
     */
    private String userEmail;

    /**
     * 用户手机
     */
    private String userMobile;

    /**
     * 注册时间
     */
    private Timestamp regTime;

    /**
     * 注册ip
     */
    private String regIp;

    /**
     * 最后登录时间
     */
    private String lastLoginTime;

    /**
     * 最后一次登录的ip
     */
    private String lastLoginIp;

    /**
     * 验证码发送时间
     */
    private String updateTime;

    /**
     * 验证码
     */
    private int verificationCode;
    /**
     * 是否激活状态。0：表示未激活，1：表示以激活，可以挂号
     */
    private int status;

    /**
     * 是否存在。0：修改密码校验码，1：完善信息校验码
     */
    @TableField("isDelete")
    private int isDelete;
}
