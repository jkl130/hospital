package com.anjsh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author anjsh
 * @date 2021/10/13 14:25
 */
@TableName("comment")
@Data
public class Comment {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer doctorId;

    private Integer userId;

    private String title;

    private String content;

    private String answer;

    private LocalDateTime createTime;

    private LocalDateTime atime;

    private LocalDateTime ctime;
}
