package com.time.scenery.rain.space.webapi.service;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.time.scenery.rain.space.webapi.dao.UserServerDao;
import com.time.scenery.rain.space.webapi.daoentity.RentEnterpriseStoreMap;


@Service("userService")
public class UserService {
	
	@Resource
	private UserServerDao userServerDao;//注入dao
	
	public List<RentEnterpriseStoreMap> getAllShopAndUnitInfo(){
		return userServerDao.getAllShopAndUnitInfo();
	}
}
