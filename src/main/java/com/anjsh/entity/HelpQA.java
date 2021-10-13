package com.anjsh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @author sfturing
 *
 * @date 2017年6月2日
 */
@TableName("help_q_a")
@Data
public class HelpQA {

	// id
	@TableId(type = IdType.AUTO)
	private Integer id;

	// 问题类型
	private String questionType;
	// 问题
	private String question;
	// 回答
	private String answer;
}
