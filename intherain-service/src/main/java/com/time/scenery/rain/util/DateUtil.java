package com.time.scenery.rain.util;

import java.text.SimpleDateFormat;

public class DateUtil {
    /**
     * 将Java指定格式日期转换成Unix时间戳
     *
     * @return
     */
    public static Long Date2TimeStamp(Long dateLong) {
    	if (dateLong==null || dateLong==0) {
    		return null;
		}
    	String dateStr=String.valueOf(dateLong);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            return sdf.parse(dateStr).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
//    public static void main(String[] args) {
//    	System.err.println(DateUtil.Date2TimeStamp(20170221163536L));
//	}
}
