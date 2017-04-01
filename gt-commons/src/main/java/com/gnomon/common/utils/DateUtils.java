package com.gnomon.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtils extends org.apache.commons.lang.time.DateUtils{

	public static final String FORMAT_PATTEN_DATETIME="yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_PATTEN_DATE="yyyy-MM-dd";
	public static final String FORMAT_PATTEN_DATE_CN="yyyy/MM/dd";

	// 取得指定日期N天后的日期
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}
	
	// 取得指定日期N个月后的日期
	public static Date addMonths(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	// 计算两个日期之间相差的天数
	public static int daysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
//		if (between_days == 0) {
//			between_days = 1;
//		}
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	// 计算两个日期之间相差的时间（分钟）
	public static int timeBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_times = (time2 - time1) / (1000 * 60);
//		if (between_times == 0) {
//			between_times = 1;
//		}
		return Integer.parseInt(String.valueOf(between_times));
	}

	// 获得周一的日期
	public static Date getMonday(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return c.getTime();
	}

	// 获得周日的日期
	public static Date getSunDay(Date currentDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}

	// 获取下一周的日期
	public static Date nextWeek(Date currentDate) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(GregorianCalendar.DATE, 7);// 在日期上加7天
		return cal.getTime();
	}

	@Deprecated 
	/**
	 * 本方法不再使用，会在下一个版本删除
	 * @see getCurrentDate()
	 * @return
	 */
	public static String getCurrentDay(){
		return getCurrentDate();
	}
	// 获取系统当前时间 返回字符串
	public static String getCurrentDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String day = simpleDateFormat.format(date);
		return day;
	}
	// 获取系统当前时间 返回字符串
	public static String getCurrentDateTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String day = simpleDateFormat.format(date);
		return day;
	}

	// 转换日期的格式
	public static String formate(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String day = simpleDateFormat.format(date);
		return day;
	}

	public static String formate(Date date, String formate) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate);
		String day = simpleDateFormat.format(date);
		return day;
	}

	public static String change(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String day = simpleDateFormat.format(date);
		return day;
	}
	public static void main(String args[]) {
		Date date = new Date();
		System.out.println(change(date));
	}
	
	public static Date strToDate(String strDate) {
		if (strDate == null || strDate.length() == 0) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_PATTEN_DATE);
		Date retDate = null;
		try {
			retDate = simpleDateFormat.parse(strDate);
		} catch (ParseException e) {
			simpleDateFormat = new SimpleDateFormat(FORMAT_PATTEN_DATE_CN);
			try {
				retDate = simpleDateFormat.parse(strDate);
			} catch (ParseException e1) {
				simpleDateFormat = new SimpleDateFormat("HH:mm");  
				try{
					retDate = simpleDateFormat.parse(strDate);
				}catch(Exception e2){
					e.printStackTrace();
					throw new RuntimeException("只支持yyyy-MM-dd、yyyy/MM/dd和HH:mm三种形式");
				}
	                   
				
			}
		}
		return retDate;
	}
	
	public static Date strToDate(String strDate,String formate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate);
		Date retDate = null;
		try {
			retDate = simpleDateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return retDate;
	}
	
	public static Date getStartTimeOfDate(Date date){
		Date newdate = DateUtils.setHours(date, 0);
		newdate = DateUtils.setMinutes(newdate, 0);
		newdate = DateUtils.setSeconds(newdate, 0);
		newdate = DateUtils.setMilliseconds(newdate, 0);
		return newdate;
	}
	
	public static Date getEndTimeOfDate(Date date){
		Date newdate = DateUtils.setHours(date, 23);
		newdate = DateUtils.setMinutes(newdate, 59);
		newdate = DateUtils.setSeconds(newdate, 59);
		newdate = DateUtils.setMilliseconds(newdate, 999);
		return newdate;
	}
	
}
