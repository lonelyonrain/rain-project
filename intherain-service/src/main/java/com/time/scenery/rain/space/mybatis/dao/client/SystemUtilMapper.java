package com.time.scenery.rain.space.mybatis.dao.client;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SystemUtilMapper {
	
	/**
	 * 
	* @Title: getSystemTime
	* @Description: 获得系统时间
	* @author Songhui.Wang
	* @return
	* @throws
	* @date 2014年5月10日 上午10:36:20
	 */
	@Select("select sysdate from dual")
	Date getSystemTime();
	
	/**
	 * 
	* @Title: getNextSequence
	* @Description: 获得序列的下一个值
	* @author Songhui.Wang
	* @param seq
	* @return
	* @throws
	* @date 2014年5月10日 上午10:36:32
	 */
	@Select("select ${seq}.NEXTVAL from dual")
	Long getNextSequence(@Param("seq") String seq);
	
	/**
	 * 
	* @Title: getCurrSequence
	* @Description: 获得序列的当前值
	* @author Songhui.Wang
	* @param seq
	* @return
	* @throws
	* @date 2014年5月10日 上午10:36:50
	 */
	@Select("select ${seq}.CURRVAL from dual")
	Long getCurrSequence(@Param("seq") String seq);
}
