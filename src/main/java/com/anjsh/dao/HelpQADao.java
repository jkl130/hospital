package com.anjsh.dao;

import java.util.List;

import com.anjsh.entity.HelpQA;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface HelpQADao extends BaseMapper<HelpQA> {
	//根据类型查找问题
	public List<HelpQA> findQAByType(String questionType);

}
