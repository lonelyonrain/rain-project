package com.time.scenery.rain.space.mybatis.dao.client;

import java.util.List;

import com.time.scenery.rain.space.mybatis.daoentity.TabCarinfo;
import com.time.scenery.rain.space.springmybatis.daoentity.RentEnterpriseStoreMap;

public interface DataReportServerDaoMapper {
	List<RentEnterpriseStoreMap>  getAllShopAndUnitInfo();
	List<RentEnterpriseStoreMap>  getRentEnterpriseInfoOfAll();
	List<TabCarinfo>  getCarinfoOfDelete();
}
