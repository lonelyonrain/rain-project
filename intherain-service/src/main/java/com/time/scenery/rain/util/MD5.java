package com.time.scenery.rain.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 
 * @ClassName: MD5
 * @Description:  MD5加密算法
 * @author suqh
 * @date 2016年6月16日 上午11:30:03
 * @since JDK 1.7
 */
public class MD5 {
	/**
	 * 
		 * @Title: toMD5
		 * @Description:  MD5加密
		 * @author Suqh
		 * @param s=加密字符串
		 * @param type(16/32)
		 * @param size(0或null大写/1小写)
		 * @return 
		 * @return String
		 * @date  2016年6月16日 上午11:30:28
		 * @since JDK 1.7
	 */
    public static String toMD5(String s,Integer type,Integer size) {  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            md.update(s.getBytes());  
            byte b[] = md.digest();  
  
            int i;  
  
            StringBuffer buf = new StringBuffer("");  
            for (int offset = 0; offset < b.length; offset++) {  
                i = b[offset];  
                if (i < 0)  
                    i += 256;  
                if (i < 16)  
                    buf.append("0");  
                buf.append(Integer.toHexString(i));  
            }  
            if (type==16) {
            	// 16位的加密  
            	if (size==1) {
            		return buf.toString().substring(8, 24);  									
				}else {					
					return buf.toString().substring(8, 24).toUpperCase();  									
				}
			}else {
				//32位加密  
				if (size==1) {
					return buf.toString();  
				}else {					
					return buf.toString().toUpperCase();  
				}
			}
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
      
    public static String xmgps2MD5(String userName,String passWard) {
    	String a=userName+"@xmgps@"+passWard;
		return toMD5(a,16,0);
	}
    
    
//    public static void main(String[] args) {      
//        //测试      
//        System.out.println(MD5.xmgps2MD5("ui","ui"));  
//    }  
}