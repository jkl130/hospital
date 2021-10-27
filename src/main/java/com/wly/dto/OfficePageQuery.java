package com.wly.dto;

import lombok.Data;

/**
 * 科室查询条件
 *
 * @author wly
 * @date 2021/10/27
 */
@Data
public class OfficePageQuery extends PageQuery {

    /**
     * 科室名称
     */
    private String officesName;

    /**
     * 医院名称
     */
    private String hospitalName;
}
