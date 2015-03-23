package com.cf.fuer.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author sunke
 * 
 */
public class DateUtil {
	private DateUtil() {
	};

	private static DateFormat dateFormat;

	private static DateFormat dateTimeFormat;
	
	private static DateFormat birthdayFormat;

	/**
	 * 获取不带时间的今天的日期.
	 */
	public static Date getToday() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 是否是服务器维护时间4－7点
	 */
	public static boolean isMaintainTime(){
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		return hour>=4 && hour < 7;
	}
	
	/**
	 * 获取订单作废日期.
	 */
	public static Date getAbandonDate(int abandonPeriod) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.DAY_OF_YEAR, abandonPeriod);
		return c.getTime();
	}
	
	/**
	 * 获取明日零时的毫秒数
	 */
	public static Long getTomorrowInMillis() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.DAY_OF_YEAR, 1);
		return c.getTimeInMillis();
	}
	
	/**
	 * 获取带时间的日期格式 yyyy-MM-dd HH:mm
	 * 
	 * @return
	 */
	public static DateFormat getBirthdayFormat() {
		if (birthdayFormat == null) {
			birthdayFormat = new SimpleDateFormat("yyyyMMdd");
		}
		return birthdayFormat;
	}
	
	/**
	 * 获取Token过期时间,当前时间+1个小时
	 */
	public static Date getTokenExpireDate(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, 1);
		return c.getTime();
	}
	
	/**
	 * 根据身份证号获取年龄,身份证号不正确或为空时返回0
	 */
	public static int getAge(String idcard){
		if(idcard != null && (idcard.length() == 15 || idcard.length() == 18)){//正确的身份证号
			String birthday = "";
			if (idcard.length() == 15) {
				birthday = idcard.substring(6, 12);
			} else {
				birthday = idcard.substring(6, 14);
			}
			String today = getBirthdayFormat().format(new Date());
			Long longAge = (Long.valueOf(today.toString()) - Long.valueOf(birthday))/10000;
			return longAge.intValue();
		}
		return 0;
	}

	/**
	 * 根据系统定义的格式，将字符串转换为日期
	 */
	public static Date parse(String str) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		Date date = null;
		try {
			date = getDateFormat().parse(str);
		} catch (ParseException e) {
			date = null;
		}
		return date;
	}

	/**
	 * 注意，如果jianquyitian=true，在begin加上一定的时间之后，最后会减去一天。
	 * 例如：1月1号开始，使用30天。那么应该在1+30-1=1月30号结束。 又如：1月1号开始，使用3个月，应该在3月31日结束，而不是4月1日。
	 */
	public static Date calculateDate(Date begin, int calendarInt, int amount, boolean jianquyitian) {
		Calendar c = Calendar.getInstance();
		c.setTime(begin == null ? new Date() : begin);
		c.add(calendarInt, amount);
		if (jianquyitian)
			c.add(Calendar.DAY_OF_MONTH, -1);
		return c.getTime();
	}

	/**
	 * 获取日期格式YYYY-MM-dd
	 */
	public static DateFormat getDateFormat() {
		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		}
		return dateFormat;
	}

	/**
	 * 获取带时间的日期格式 yyyy-MM-dd HH:mm
	 * 
	 * @return
	 */
	public static DateFormat getDateTimeFormat() {
		if (dateTimeFormat == null) {
			dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}
		return dateTimeFormat;
	}

	/**
	 * 将日期转换为字符串yyyy-MM-dd HH:mm，如果传入的Date为null返回一个空的字符串
	 * 
	 * @param date
	 *            　需要转换的日期
	 * @return　转换后的字符串
	 */
	public static String toDateString(Date date) {
		if (date == null) {
			return "";
		}
		return getDateTimeFormat().format(date);
	}
}
