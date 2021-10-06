package com.anjsh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;


/**
 * @author sfturing
 * @date 2017年4月28日
 */
@TableName("commonuser")
@Data
public class CommonUser {
    //userid
    @TableId(type = IdType.AUTO)
    private Integer userId;
    //user身份证号
    private String userIdenf;
    //user姓名
    private String userName;
    //user性别
    private String userSex;
    //user密码
    private String userPassword;
    //user邮箱
    private String userEmail;
    //user手机
    private String userMobile;
    //user注册时间
    private Timestamp regTime;
    //user注册ip
    private String regIp;
    //user最后登录时间
    private String lastLoginTime;
    //user最后登录的ip
    private String lastLoginIp;
    //user验证码发送时间
    private String updateTime;
    //验证码
    private int verificationCode;
    //User是否激活状态。0：表示未激活，1：表示以激活，可以挂号
    private int status;
    //user是否存在。0：修改密码校验码，1：完善信息校验码
    private int isdelete;
}
