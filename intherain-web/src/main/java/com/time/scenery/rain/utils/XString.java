package com.time.scenery.rain.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XString {
	 /*-----------------------------------
    笨方法：String s = "你要去除的字符串";
            1.去除空格：s = s.replace('\\s','');
            2.去除回车：s = s.replace('\n','');
    这样也可以把空格和回车去掉，其他也可以照这样做。
    注：\n 回车(\u000a) 
    \t 水平制表符(\u0009) 
    \s 空格(\u0008) 
    \r 换行(\u000d)*/
	
	
	
	/**
	 * 
	 * @Title: getStringContent
	 * @Description: 去掉字符串两边的"
	 * @author Wei.Wu
	 * @param str
	 * @return
	 * @throws
	 * @date 2014年8月22日 下午5:26:46
	 */
	public static String getStringContent(String str){
		Pattern p=Pattern.compile("^\"(.+)\"$");
		Matcher m=p.matcher(str);
		if(m.find()){
			str=m.group(1);
		}
		
		return str;
	}
	
	/**
	 * 
	 * @Title: getStringContent2
	 * @Description: 去掉字符串两边的'
	 * @author Wei.Wu
	 * @param str
	 * @return
	 * @throws
	 * @date 2015年1月7日 下午4:06:18
	 */
	public static String getStringContent2(String str){
		Pattern p=Pattern.compile("^\'(.+)\'$");
		Matcher m=p.matcher(str);
		if(m.find()){
			str=m.group(1);
		}
		
		return str;
	}
	/**
	 * 
	 * @Title: replaceBlank 
	 * @Description:去除字符串中的空格、回车、换行符、制表符 
	 * @param str
	 * @return String
	 * @author Suqh
	 * @date 2017年2月13日
	 * @throws
	 */
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
   
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static Boolean isNullOrBrank(String str){
		if (str==null || "".equals(str)) {
			return true;
		}
		return false;
	}
}
