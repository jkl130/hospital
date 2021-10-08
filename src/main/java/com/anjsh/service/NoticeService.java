package com.anjsh.service;

import java.util.List;

import com.anjsh.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

public interface NoticeService extends IService<Notice> {

	// 查询所有的须知
	public List<Notice> findNoticeByType(int start, int size);

	// 查询所有的须知的数量
	public int findNoticeByTypeNum();

	// 通过id查询通知
	public Notice findNoticeById(int id);
	

}
