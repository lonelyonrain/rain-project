package com.time.scenery.rain.space.quartzs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;

import com.time.scenery.rain.log.Log;

public class DataReportJobOfFTP implements Job {
	private static Logger log = Log.get("BackgroundServer"); 
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Log.info(log,"==========ftp图片上传开始=========");
		Log.info(log,"==========ftp图片上传结束=========");
	}
}
