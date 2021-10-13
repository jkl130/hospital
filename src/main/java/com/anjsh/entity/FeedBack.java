package com.anjsh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("feed_back")
@Data
public class FeedBack {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private int userId;

    private String content;

    private String createTime;
}
