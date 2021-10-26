package com.anjsh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author sfturing
 * @date 2017年5月23日
 */
@TableName("hos_office")
@Data
public class Office {
    //科室id
    @TableId(type = IdType.AUTO)
    private Integer id;
    //科室姓名
    private String officesName;
    //医院名称
    private String hospitalName;
    //医生数量
    private String doctorNum;
    //科室荣誉
    private String officesHonor;
    //科室设备
    private String officesEquipment;
    //科室简介
    private String officesAbout;
    //科室诊疗范围
    private String officesDiagnosisScope;

    private int hosId;

    private int rec;
}
