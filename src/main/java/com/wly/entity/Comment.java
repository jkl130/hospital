package com.wly.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论
 *
 * @author wly
 * @date 2021/10/13 14:25
 */
@TableName("comment")
@Data
public class Comment {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 医生id
     */
    private Integer doctorId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 回答
     */
    private String answer;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 回答时间
     */
    private String atime;

    /**
     * 提问时间
     */
    private LocalDateTime ctime;
}
