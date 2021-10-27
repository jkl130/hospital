package com.wly.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 区域
 *
 * @author wly
 * @date 2021/10/27
 */
@Data
@TableName("area")
public class Area {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 城市名称
     */
    private String areaName;

    /**
     * 父级城市id
     */
    private Integer parentId;

    /**
     * 城市等级
     */
    private Integer level;
}
