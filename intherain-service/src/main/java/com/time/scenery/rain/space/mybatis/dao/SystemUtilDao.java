package com.time.scenery.rain.space.mybatis.dao;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;

import com.time.scenery.rain.space.mybatis.dao.client.SystemUtilMapper;

public class SystemUtilDao{
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
	public static Date getSystemTime() throws Exception{
		SqlSession session = MybatisSessionFactory.openSession_oracle();
		if (session==null) {
			return null;
		}
		try{
			SystemUtilMapper mapper = session.getMapper(SystemUtilMapper.class);
			return mapper.getSystemTime();
		}
		finally{
			MybatisSessionFactory.closeSession(session);
		}
	}
	
	/**
	 * 
	* @Title: getNextSequence
	* @Description: 获得序列的下一个值
	* @author Songhui.Wang
	* @param seq	:自增序列名称
	* @return
	* @throws Exception
	* @throws
	* @date 2014年5月10日 上午10:37:27
	 */
	public static Long getNextSequence(String seq) throws Exception
	{
		SqlSession session = MybatisSessionFactory.openSession_oracle();
		try{
			SystemUtilMapper mapper = session.getMapper(SystemUtilMapper.class);
			return mapper.getNextSequence(seq);
		}
		finally{
			MybatisSessionFactory.closeSession(session);
		}
	}
	
	/**
	 * 
	* @Title: getCurrSequence
	* @Description: 获得序列的当前值
	* @author Songhui.Wang
	* @param seq	:自增序列名称
	* @return
	* @throws Exception
	* @throws
	* @date 2014年5月10日 上午10:38:07
	 */
	public static Long getCurrSequence(String seq) throws Exception
	{
		SqlSession session = MybatisSessionFactory.openSession_oracle();
		try{
			SystemUtilMapper mapper = session.getMapper(SystemUtilMapper.class);
			return mapper.getCurrSequence(seq);
		}
		finally{
			MybatisSessionFactory.closeSession(session);
		}
	}
}
