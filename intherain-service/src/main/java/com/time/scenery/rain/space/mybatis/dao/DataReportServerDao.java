package com.time.scenery.rain.space.mybatis.dao;

import java.util.List;

import com.time.scenery.rain.space.mybatis.daoentity.RentReportConfigLog;
import com.time.scenery.rain.space.mybatis.daoentity.TabCarinfo;
import org.apache.ibatis.session.SqlSession;

import com.time.scenery.rain.space.mybatis.dao.client.DataReportServerDaoMapper;
import com.time.scenery.rain.space.springmybatis.daoentity.RentEnterpriseStoreMap;

public class DataReportServerDao {
	//
	public static List<RentEnterpriseStoreMap> getAllShopAndUnitInfo() throws Exception {
		SqlSession session = MybatisSessionFactory.openSession_sqlserver();
		try {
			DataReportServerDaoMapper mapper = session.getMapper(DataReportServerDaoMapper.class);
			return mapper.getAllShopAndUnitInfo();
		} finally {
			MybatisSessionFactory.closeSession(session);
		}
	}
	//
	public static List<TabCarinfo> getCarinfoOfDelete() throws Exception {
		SqlSession session = MybatisSessionFactory.openSession_sqlserver();
		try {
			DataReportServerDaoMapper mapper = session.getMapper(DataReportServerDaoMapper.class);
			return mapper.getCarinfoOfDelete();
		} finally {
			MybatisSessionFactory.closeSession(session);
		}
	}
	//
	public static List<TabCarinfo> getRentEnterpriseInfoOfAll() throws Exception {
		SqlSession session = MybatisSessionFactory.openSession_sqlserver();
		try {
			DataReportServerDaoMapper mapper = session.getMapper(DataReportServerDaoMapper.class);
			return mapper.getCarinfoOfDelete();
		} finally {
			MybatisSessionFactory.closeSession(session);
		}
	}
	
}
