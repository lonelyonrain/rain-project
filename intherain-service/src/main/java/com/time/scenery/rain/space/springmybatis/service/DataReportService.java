package com.time.scenery.rain.space.springmybatis.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.time.scenery.rain.space.springmybatis.dao.DataReportServerDao;
import com.time.scenery.rain.space.springmybatis.daoentity.RentEnterpriseStoreMap;

@Service("dataReportService")
public class DataReportService {

	@Resource
	private DataReportServerDao dataReportServerDao;//注入dao
	
	public List<RentEnterpriseStoreMap> getAllShopAndUnitInfo(){
		return dataReportServerDao.getAllShopAndUnitInfo();
	}
}
