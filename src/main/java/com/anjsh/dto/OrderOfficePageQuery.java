package com.anjsh.dto;

import lombok.Data;

@Data
public class OrderOfficePageQuery extends PageQuery {

    //科室姓名
    private String officesName;

    //医院名称
    private String hospitalName;
}
