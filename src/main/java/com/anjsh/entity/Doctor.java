package com.anjsh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("doctor")
@Data
public class Doctor {

    // id
    @TableId(type = IdType.AUTO)
    private Integer id;
    // 医生姓名
    private String doctorName;
    // 医生性别
    private String doctorSex;
    // 医院名称
    private String hospitalName;
    // 科室名称
    private String officesName;
    // 医生照片
    private String doctorImg;
    // 医生职称
    private String doctorTitle;
    // 教学支职称
    private String teachTitle;
    // 行政职位
    private String doctorAdministrative;
    // 学位
    private String doctorDegree;
    // 医生特长
    private String doctorForte;
    // 医生关于
    private String doctorAbout;

    private int hosId;

    private int officeId;

    private int rec;
}
