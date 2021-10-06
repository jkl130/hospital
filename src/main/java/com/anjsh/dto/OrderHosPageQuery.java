package com.anjsh.dto;

import com.anjsh.entity.Hospital;
import lombok.Data;

@Data
public class OrderHosPageQuery extends PageQuery {

    public String province;

    public String city;

    public String district;

    // 医院类型
    private String hospitalNature;

    // 医院等级
    private String hospitalGrade;

    // 医院名称
    private String hospitalName;
}
