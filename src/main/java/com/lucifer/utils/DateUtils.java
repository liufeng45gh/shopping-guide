package com.lucifer.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	/**
	 * 今天
	 * @return
	 */
	public static Date now(){
		return new Date();
	}
	
	/**
	 * 昨天
	 * @return
	 */
	public static Date yesterday(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}
	
	/**
	* 是否是今天
	* 
	* @param date
	* @return
	*/
	public static boolean isToday(final Date date) {
	        return isTheDay(date, DateUtils.now());
	}
	
	/**
	* 是否是昨天
	* 
	* @param date
	* @return
	*/
	public static boolean isYesTerday(final Date date) {
	        return isTheDay(date, DateUtils.yesterday());
	}
	
	/**
	* 是否是昨天
	* 
	* @param date
	* @return
	*/
	public static boolean isYesTerday(final Date date,Date yesterday) {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.setTime(date);
		c.add(Calendar.DATE, -1);
	    return isTheDay(c.getTime(), yesterday);
	}
	
	/**
	* 是否是指定日期
	* 
	* @param date
	* @param day
	* @return
	*/
	public static boolean isTheDay(final Date date, final Date day) {
	        return date.getTime() >= DateUtils.dayBegin(day).getTime()
	                        && date.getTime() <= DateUtils.dayEnd(day).getTime();
	}
	/**
	* 获取指定时间的那天 00:00:00.000 的时间
	* 
	* @param date
	* @return
	*/
	public static Date dayBegin(final Date date) {
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.set(Calendar.HOUR_OF_DAY, 0);
	        c.set(Calendar.MINUTE, 0);
	        c.set(Calendar.SECOND, 0);
	        c.set(Calendar.MILLISECOND, 0);
	        return c.getTime();
	}
	/**
	* 获取指定时间的那天 23:59:59.999 的时间
	* 
	* @param date
	* @return
	*/
	public static Date dayEnd(final Date date) {
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.set(Calendar.HOUR_OF_DAY, 23);
	        c.set(Calendar.MINUTE, 59);
	        c.set(Calendar.SECOND, 59);
	        c.set(Calendar.MILLISECOND, 999);
	        return c.getTime();
	}

}
