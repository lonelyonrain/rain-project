package com.time.scenery.rain.space.quartzs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;

import com.time.scenery.rain.log.Log;
import com.time.scenery.rain.request.RentReportServiceAPI;

public class DataReportJob extends RentReportServiceAPI implements Job {
	private static Logger log = Log.get("BackgroundServer"); 
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Log.info(log,"===========>主业务DataReportJob<=======");
		Log.info(log,"===========>主业务DataReportJob<=======");
	}
}
