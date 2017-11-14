package com.time.scenery.rain.spring.utils;
/** 
 *  
* ClassName: DynamicDataSourceInterceptor <br/>  
* Function: 数据源动态切换拦截器. <br/>  
* date: 2017年3月15日 下午10:28:17 <br/>  
*  
* @author JohnFNash  
* @version   
* @since JDK 1.6 
 */  
public class DynamicDataSourceInterceptor {
	    /** 切换到数据源1 */  
	    public void setDS1() {  
	    	DataSourceContextHolder.setDataSourceType("oracleDataSource"); 
	    }  
	    
	    /** 切换到数据源2 */  
	    public void setDS2() {   
	    	DataSourceContextHolder.setDataSourceType("mssqlDataSource");
	    }  
}
