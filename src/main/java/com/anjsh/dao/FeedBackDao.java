package com.anjsh.dao;

import com.anjsh.entity.FeedBack;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface FeedBackDao extends BaseMapper<FeedBack> {
	
	//插入反馈
	public int inserFeedBack(FeedBack feedBack);

}
