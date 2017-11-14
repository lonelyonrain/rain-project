package com.xmgps.taxi.datareportserver.demo;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.time.scenery.rain.space.mybatis.dao.DataReportServerDao;
import com.time.scenery.rain.space.mybatis.dao.SystemUtilDao;
import com.time.scenery.rain.util.UuidUtil;
import com.time.scenery.rain.util.XDate;


public class AppTest {

	@Test
	public void test() {
		System.err.println("hello kitty");
		String  aString="";
		aString=null;
		String bb=(String)aString;
		System.err.println(bb);
	}
	
	@Test
	public void testPost(){
		try {
			System.err.println("aaaaaaaaaaaaaaaa:"+XDate.formateLong(SystemUtilDao.getSystemTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("end");
	}

}
