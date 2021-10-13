package com.anjsh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("notice")
@Data
public class Notice {

    @TableId(type = IdType.AUTO)
    private Integer id;
    // 须知名称
    private String noticeName;
    // 须知内容
    private String noticeDescription;
    // 须知类型
    private String noticeType;
    // 创建时间
    private String createTime;
    // 创建时间
    private String updateTime;
    //是否显示
    private int isUseful;
}
