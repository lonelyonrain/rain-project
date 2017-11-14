package com.time.scenery.rain.space.restfulapi.service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.time.scenery.rain.space.restfulapi.dao.RestFulUserServerDao;
import com.time.scenery.rain.space.restfulapi.daoentity.RentEnterpriseStoreMap;


@Service("restFulUserService")
public class RestFulUserService {
	
	@Resource
	private RestFulUserServerDao restFulUserServerDao;//注入dao
	
	public List<RentEnterpriseStoreMap> getAllShopAndUnitInfo(){
		return restFulUserServerDao.getAllShopAndUnitInfo();
	}
	
	/**
	 * 
	* @Title: getSystemTime
	* @Description: 获得当前系统时间
	* @author Songhui.Wang
	* @return
	* @throws Exception
	* @throws
	* @date 2014年5月10日 上午10:37:15
	 */
	public Date getSystemTime(){
			return restFulUserServerDao.getSystemTime();
	}
}
