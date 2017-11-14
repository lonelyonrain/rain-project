package com.time.scenery.rain.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class XDate {

	/**
	 * 
	 * @Title: getTodyZero
	 * @Description: 返回当天的零时时间对象
	 * @author Songhui.Wang
	 * @return Date
	 * @throws Exception
	 * @date 2014年3月14日 上午9:54:01
	 */
	public static Date getTodyZero() throws Exception {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR); // 获取年份
		int month = cal.get(Calendar.MONTH) + 1;// 获取月份
		int day = cal.get(Calendar.DATE);// 获取日

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(String.format("%04d-%02d-%02d 00:00:00", year, month, day));
	}

	/**
	 * 
	 * @Title: getTodyLatest
	 * @Description: 返回当天的最后时间对象
	 * @author Songhui.Wang
	 * @param @return
	 *            ‘2014-02-02 23:59:59’
	 * @param @throws
	 *            Exception
	 * @return Date
	 * @throws @date
	 *             2014年3月14日 上午9:54:54
	 */
	public static Date getTodyLatest() throws Exception {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);// 获取年份
		int month = cal.get(Calendar.MONTH) + 1;// 获取月份
		int day = cal.get(Calendar.DATE);// 获取日

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(String.format("%04d-%02d-%02d 23:59:59", year, month, day));
	}

	public static Date paseLongDate(String str) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(str);
	}

	public static Date paseDayDate(String str) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(str);
	}

	public static Date paseDateOfSelf(String datestr, String form) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat(form);
		return df.parse(datestr);
	}

	/**
	 * 
	 * @Title: addDays
	 * @Description: 当前时间增加天数
	 * @author Songhui.Wang
	 * @param @param
	 *            dt
	 * @param @param
	 *            days
	 * @param @return
	 * @param @throws
	 *            Exception
	 * @return Date
	 * @throws @date
	 *             2014年3月14日 上午9:57:05
	 */
	public static Date addDays(Date dt, int days) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	/**
	 * 
	 * @Title: addHourds
	 * @Description: TODO
	 * @author Songhui.Wang
	 * @param dt
	 * @param hours
	 * @param
	 * @throws Exception
	 * @return Date
	 * @date 2014年3月14日 上午10:04:09
	 */
	public static Date addHourds(Date dt, int hours) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.HOUR, hours);
		return cal.getTime();
	}

	/**
	 * 
	 * @Title: addMinutes
	 * @Description: TODO
	 * @author chenqc
	 * @param dt
	 * @param minutes
	 * @param
	 * @throws Exception
	 * @return Date
	 * @date 2015年3月3日 上午10:04:09
	 */
	public static Date addMinutes(Date dt, int Minutes) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.MINUTE, Minutes);
		return cal.getTime();
	}

	/**
	 * 
	 * @Title: formateLong
	 * @Description: 格式化时间
	 * @author Songhui.Wang
	 * @param @param
	 *            dt
	 * @param @return
	 * @return String
	 * @throws @date
	 *             2014年3月14日 上午10:08:18
	 */
	public static String formate(Date dt, String form) {
		if (null == dt) {
			return "";
		}
		return new SimpleDateFormat(form).format(dt);
	}

	/**
	 * 
	 * @Title: formateLong
	 * @Description: 格式化时间
	 * @author Songhui.Wang
	 * @param @param
	 *            dt
	 * @param @return
	 * @return String
	 * @throws @date
	 *             2014年3月14日 上午10:08:18
	 */
	public static String formateLong(Date dt) {
		return formate(dt, "yyyy-MM-dd HH:mm:ss");
	}

	public static String formateDay(Date dt) {
		return formate(dt, "yyyy-MM-dd");
	}

	/**
	 * 
	 * @Title: comp
	 * @Description: 比较两个时间的大小
	 * @author Songhui.Wang
	 * @param @param
	 *            b
	 * @param @param
	 *            e
	 * @param @return
	 * @return 返回大于0 表示前者时间比后者时间大；返回小于0表示前者时间比后者时间小；否则相等。
	 * @throws @date
	 *             2014年3月14日 下午8:39:46
	 */
	public static long comp(Date b, Date e) {
		long t_b = b.getTime();
		long t_e = e.getTime();
		return t_b - t_e;
	}

	/**
	 * 取得时间14位
	 * 
	 * @return
	 */
	public static String getCurrentTime14() {
		String date = null;
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			date = sdf.format(new Date());
		} catch (Exception e) {
			return null;
		}
		return date;
	}

	/**
	 * 
	 * @Title: parseString
	 * @Description: string转化为Date
	 * @author wq.L
	 * @param @param
	 *            str
	 * @param @param
	 *            date
	 * @param @return
	 * @return Date
	 * @throws @date
	 *             2014年3月14日 下午8:39:46
	 */
	public static Date parseString(String str) {
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Date beforeOneHourZeroMMSS(int addhour) throws Exception {
			Calendar calendar = Calendar.getInstance();
			/* HOUR_OF_DAY 指示一天中的小时 */
//			calendar.setTime(XDate.paseLongDate("2017-12-12 12:00:00"));		
			calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - addhour);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");		
			String date_str = df.format(calendar.getTime()) + ":00:00";	
			Date fdDate= XDate.paseLongDate(date_str);
			return fdDate;
	}
}
