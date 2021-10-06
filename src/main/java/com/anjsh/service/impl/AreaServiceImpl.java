package com.anjsh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjsh.dao.AreaDao;
import com.anjsh.entity.Area;
import com.anjsh.service.AreaService;
@Service
public class AreaServiceImpl implements AreaService {
	@Autowired
	private AreaDao areaDao;
	@Override
	public List<Area> findAreaByLevel() {
		// TODO Auto-generated method stub
		return areaDao.findAreaByLevel1();
	}

	@Override
	public List<Area> findAreaByLevel(String areaName) {
		// TODO Auto-generated method stub
		return areaDao.findAreaByLevel2(areaName);
	}

	@Override
	public List<Area> findAreaByLevel(String areaName, String cityName) {
		// TODO Auto-generated method stub
		return areaDao.findAreaByLevel3(areaName, cityName);
	}

}
