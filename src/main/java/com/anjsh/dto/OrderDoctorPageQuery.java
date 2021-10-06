package com.anjsh.dto;

import lombok.Data;

@Data
public class OrderDoctorPageQuery extends PageQuery {

    // 医院名称
    private String hospitalName;
    // 科室名称
    private String officesName;
    // 医生姓名
    private String doctorName;
    // 医生职称
    private String doctorTitle;
    // 行政职位
    private String doctorAdministrative;
    // 学位
    private String doctorDegree;
}
