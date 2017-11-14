package com.time.scenery.rain.util;

import java.io.InputStreamReader;
import java.util.Properties;
import com.alibaba.fastjson.JSON;
/**
 * 
 * @ClassName: ConfigFile 
 * @Description:读取配置文件
 * @author suqh 
 * @date 2017年1月13日 下午5:09:57 
 *
 */
public class ConfigFile { 
	/**
	 * 
	 * @Title: readConfig 
	 * @Description: 从Properties获取json
	 * @param cofigFilePath
	 * @return
	 * @throws Exception String
	 * @author Suqh
	 * @date 2017年1月13日
	 * @throws
	 */
	public static String readConfig(String cofigFilePath) throws Exception {
		Properties properties=new Properties();
		properties.load(new InputStreamReader(Object.class.getResourceAsStream("/files/"+cofigFilePath), "UTF-8"));
		String json=JSON.toJSONString(properties);
		return json;
	}
/**
 * 
 * @Title: test 
 * @Description:实例
 * @author Suqh
 * @date 2017年1月13日
 * @throws
 */
//	public static void main(String[] args) {
//		try {
//			BaseInfoCompany baseInfoCompany = JSON.parseObject(ConfigFile.readConfig("baseInfoCompany.properties"), BaseInfoCompany.class);  
//			System.err.println(baseInfoCompany.getLegalname());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
