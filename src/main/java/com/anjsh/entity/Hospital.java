package com.anjsh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author sfturing
 * @date 2017年4月29日
 */
@TableName("hospital")
@Data
public class Hospital {
    @TableId(type = IdType.AUTO)
    // 医院id
    private Integer id;
    // 医院名称
    private String hospitalName;
    // 医院的地区
    private String hospitalArea;
    // 医院图片
    private String hospitalImg;
    // 院长姓名
    private String hospitalDeanName;
    // 建院年份
    private String hospitalYear;
    // 医院类型
    private String hospitalNature;
    // 医院等级
    private String hospitalGrade;
    // 是否医保
    private String isMedicalInsurance;
    // 医院设备介绍
    private String hospitalEquipment;
    // 医院介绍
    private String hospitalAbout;
    // 医院荣誉
    private String hospitalHonor;
    // 医院网址
    private String hospitalUrl;
    // 医院电话
    private String hospitalPhone;
    //医院地址
    private String hospitalAddress;
    //医院邮编
    private String hospitalPostCode;
    //医院公交路线
    private String hospitalBusRoute;
    // 医院科室数量
    private int hospitalOfficesNum;
    // 医保人数
    private int medicalInsuranceNum;
    // 医院病床数量
    private int hospitalBedNum;
    // 年门诊量
    private int outpatientNum;
    //是否开放预约
    private int isOpen;

    private int rec;
}
