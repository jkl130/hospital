package com.anjsh.service;

import java.util.List;

import com.anjsh.entity.HelpQA;
import com.baomidou.mybatisplus.extension.service.IService;

public interface HelpQAService extends IService<HelpQA> {
	//根据类型查找问题
		public List<HelpQA> findQAByType(String questionType);

}
