package com.time.scenery.rain.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/**
 * 
 * @author RAIN
 *
 */
public class GlobalConfig { 
	private final static String configurationFile = "general-config.properties";	
	public static Map<String, String> prop_map=new HashMap<String, String>();
	/**
	 * 
	 * @Title: readConfig   
	 * @Description:读取配置文件   
	 * @return
	 * @throws Exception      
	 * @return boolean      
	 * @throws   
	 * @date 2017年8月22日 上午11:06:23
	 * @auth suqh
	 */
	public static boolean readConfig() throws Exception {
		InputStream in = GlobalConfig.class.getResourceAsStream("/"+configurationFile);
		if (null==in) {
			return false;
		}
		Properties prop = new Properties();
		prop.load(new InputStreamReader(in,"UTF-8"));
		try {
		prop_map.clear();
	    Enumeration<?> en=prop.propertyNames();
        while (en.hasMoreElements()) {
            String key=(String) en.nextElement();
            String property=prop.getProperty(key);
            if(property!=null){
            	property=property.trim();
            }
            prop_map.put(key, property);
        }
		return true;
		} catch (Exception e) {
			return false;
		}
	}
}
