package com.wly.dto;

import lombok.Data;

/**
 * 医院查询条件
 *
 * @author DPP-1
 * @date 2021/10/27
 */
@Data
public class HosPageQuery extends PageQuery {

    /**
     * 省
     */
    public String province;

    /**
     * 市
     */
    public String city;

    /**
     * 区
     */
    public String district;

    /**
     * 医院类型
     */
    private String hospitalNature;

    /**
     * 医院等级
     */
    private String hospitalGrade;

    /**
     * 医院名称
     */
    private String hospitalName;
}
