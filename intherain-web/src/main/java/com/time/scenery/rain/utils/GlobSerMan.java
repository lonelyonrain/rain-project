package com.time.scenery.rain.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/**
 * 
 * @ClassName: GlobSerMan 
 * @Description:generic-config配置文件 
 * @author suqh 
 * @date 2016年8月12日 上午8:42:51 
 *
 */
public class GlobSerMan { 
	private final static String configurationFile = "generic-config.properties";	
	public static Map<String, String> map=new HashMap<String, String>();
	static Properties prop = new Properties();
/**
 * 
 * @Title: readConfig 
 * @Description: 读取配置文件
 * @return
 * @throws Exception boolean
 * @author Suqh
 * @date 2016年8月12日
 * @throws
 */
	public static boolean readConfig() throws Exception {
		InputStream in = GlobSerMan.class.getResourceAsStream("/"+configurationFile);
		if (null==in) {
			return false;
		}
		prop.load(new InputStreamReader(in,"UTF-8"));
		try {
		map.clear();
	    Enumeration<?> en=prop.propertyNames();
        while (en.hasMoreElements()) {
            String key=(String) en.nextElement();
            String property=prop.getProperty(key);
            if(property!=null){
            	property=property.trim();
            }
            map.put(key, property);
        }
		return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean modifyConfig(String AT) {
		//修改值
        prop.setProperty("LOCAL_START", "uuuuu");
        //保存文件
        try {
        	URI uri = Utility.getResourceUri(configurationFile);
//            URL fileUrl = ClassLoader.class.getResource(configurationFile);//得到文件路径
            FileOutputStream fos = new FileOutputStream(new File(uri));
            prop.store(fos, "prop修改成功");
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
	}
}
