package com.wly.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 帮助qa
 *
 * @author wly
 * @date 2021/10/27
 */
@TableName("help_q_a")
@Data
public class HelpQA {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 问题类型
     */
    private String questionType;

    /**
     * 问题
     */
    private String question;

    /**
     * 回答
     */
    private String answer;
}
