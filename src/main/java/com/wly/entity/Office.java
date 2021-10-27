package com.wly.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 科室
 *
 * @author wly
 * @date 2021/10/27
 */
@TableName("hos_office")
@Data
public class Office {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 科室名称
     */
    private String officesName;

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 医生数量
     */
    private String doctorNum;

    /**
     * 科室荣誉
     */
    private String officesHonor;

    /**
     * 科室设备
     */
    private String officesEquipment;

    /**
     * 科室简介
     */
    private String officesAbout;

    /**
     * 科室诊疗范围
     */
    private String officesDiagnosisScope;

    /**
     * 医院id
     */
    private int hosId;

    /**
     * 推荐
     */
    private int rec;
}
