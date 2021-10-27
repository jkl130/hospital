package com.wly.dto;

import lombok.Data;

/**
 * 医生查询条件
 *
 * @author wly
 * @date 2021/10/27
 */
@Data
public class DoctorPageQuery extends PageQuery {

    /**
     * 医院的名字
     */
    private String hospitalName;

    /**
     * 科室名称
     */
    private String officesName;

    /**
     * 医生的名字
     */
    private String doctorName;

    /**
     * 医生职称
     */
    private String doctorTitle;

    /**
     * 行政职位
     */
    private String doctorAdministrative;

    /**
     * 学位
     */
    private String doctorDegree;
}
