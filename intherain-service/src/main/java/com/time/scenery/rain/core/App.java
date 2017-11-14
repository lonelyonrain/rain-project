package com.time.scenery.rain.core;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.time.scenery.rain.config.GlobalConfig;
import com.time.scenery.rain.log.Log;
import com.time.scenery.rain.space.jt808protocol.simulation.server.Jt808protocolSimulationApp;
import com.time.scenery.rain.space.mybatis.dao.DataReportServerDao;
import com.time.scenery.rain.space.quartzs.DataReportJob;
import com.time.scenery.rain.space.quartzs.DataReportJobOfFTP;
import com.time.scenery.rain.space.quartzs.Jt808protocolSimulationJob;
import com.time.scenery.rain.space.springmybatis.daoentity.RentEnterpriseStoreMap;
import com.time.scenery.rain.space.springmybatis.service.DataReportService;
import com.time.scenery.rain.util.QuartzManager;
import com.time.scenery.rain.util.StringUtils;
import com.time.scenery.rain.util.XDate;

public class App {
	private static Logger log = Log.get("BackgroundServer");  
	public static void main(String[] args){
		try {
			Log.info(log,"===========开始启动服务=========");
			Log.info(log,XDate.getCurrentTime14() + "【系统启动】");
			//读取配置文件
			if (!GlobalConfig.readConfig()) {
				Log.info(log,"===========服务启动失败=========");
				return ;
			}
			//==================================================
			//定时器模块
			if (GlobalConfig.prop_map.containsKey("quartzs_switch") && "true".equals(GlobalConfig.prop_map.get("quartzs_switch"))) {
				//定时器
				Log.info(log,"定时器模块启动==============================");
				String quartzsA=GlobalConfig.prop_map.get("quartzsA");
				String quartzsB=GlobalConfig.prop_map.get("quartzsB");
				Log.info(log,"quartzsA:" +quartzsA);
				Log.info(log,"quartzsB:" +quartzsB);
				if (!StringUtils.isNullOrBrank(quartzsA)) {
					QuartzManager.addJob("11", new DataReportJob(),quartzsA); // 定时任务A				
				}
				if (!StringUtils.isNullOrBrank(quartzsB)) {
					QuartzManager.addJob("12", new DataReportJobOfFTP(),quartzsB); // 定时任务B				
				}
			}
			//==================================================
			//spring4-mybatis3-dao独立模块
			if (GlobalConfig.prop_map.containsKey("spring.mybatis.dao_switch") && "true".equals(GlobalConfig.prop_map.get("spring.mybatis.dao_switch"))) {
				Log.info(log,"spring4-mybatis3-dao模块启动==============================");
				ApplicationContext factory = new ClassPathXmlApplicationContext("spring.xml");
				//从Spring容器中根据bean的id取出我们要使用的userService对象
				DataReportService dataReportService = (DataReportService) factory.getBean("dataReportService");
				List<RentEnterpriseStoreMap> his=dataReportService.getAllShopAndUnitInfo();
				if (his!=null && his.size()>0) {
					for (RentEnterpriseStoreMap hi : his) {
						Log.info(log,hi.getEnterprise_store_name());						
					}
				}else {
					Log.info(log, "空数据");
				}
			}
			//==================================================
			//mybatis3-独立框架，独立模块
			if (GlobalConfig.prop_map.containsKey("mybatis.dao_switch") && "true".equals(GlobalConfig.prop_map.get("mybatis.dao_switch"))) {
				Log.info(log,"mybatis3-dao模块启动==============================");
				List<RentEnterpriseStoreMap> his=DataReportServerDao.getAllShopAndUnitInfo();
				if (his!=null && his.size()>0) {
					for (RentEnterpriseStoreMap hi : his) {
						Log.info(log,hi.getEnterprise_store_name());						
					}
				}else {
					Log.info(log, "空数据");
				}
			}
			//==================================================
			//定时器模块+mina框架
			if (GlobalConfig.prop_map.containsKey("quartzs_switch") && "true".equals(GlobalConfig.prop_map.get("quartzs_switch"))) {
				//定时器
				Log.info(log,"定时器模块启动==============================");
				String quartzsA=GlobalConfig.prop_map.get("quartzsA");
				Log.info(log,"quartzsA:" +quartzsA);
				if (!StringUtils.isNullOrBrank(quartzsA)) {
					QuartzManager.addJob("11", new Jt808protocolSimulationJob(Jt808protocolSimulationApp.getInst().setup()),quartzsA); // 定时任务A				
				}
			}
			Log.info(log,"===========服务开启成功=========");
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(log,e,"error:");
			Log.info(log,"===========服务启动失败=========");
		}
	}
}
