package com.dchip.cloudparking.api.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.stereotype.Component;

@Component

public class DateUtil {


	/**
	 * 日期时间转yyyy-MM-dd HH:mm:ss 格式字符串
	 * @param date
	 * @return
	 */
	public static String getDateTimeStr(Date date){
		SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatDateTime.format(date);
	}

	/**
	 * 获取指定时间的那天 00:00:00.000 的时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getDayBeginTime(final Date date) {
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
	public static Date getDayEndTime(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);

		Date retDate = new Date();
		retDate.setTime(c.getTime().getTime()+1);
		return retDate;
	}

	/**
	 * 判断是否同一天
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDate(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
				.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear
				&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth
				&& cal1.get(Calendar.DAY_OF_MONTH) == cal2
				.get(Calendar.DAY_OF_MONTH);

		return isSameDate;
	}

	/**
	 * 按照日期 间隔 天数
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getDiffWeeks(Date beginDate, Date endDate) {
		return getDayWeekOfDate2(endDate) - getDayWeekOfDate2(beginDate);
	}
	/**
	 * 按照日期 间隔 天数
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getDiffDays(Date beginDate, Date endDate) {
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		long beginTime = beginCalendar.getTime().getTime();
		long endTime = endCalendar.getTime().getTime();
		int betweenDays = (int) ((endTime - beginTime) / (1000 * 60 * 60 * 24));//先算出两时间的毫秒数之差大于一天的天数

		endCalendar.add(Calendar.DAY_OF_MONTH, -betweenDays);//使endCalendar减去这些天数，将问题转换为两时间的毫秒数之差不足一天的情况
		endCalendar.add(Calendar.DAY_OF_MONTH, -1);//再使endCalendar减去1天
		if (beginCalendar.get(Calendar.DAY_OF_MONTH) == endCalendar.get(Calendar.DAY_OF_MONTH))//比较两日期的DAY_OF_MONTH是否相等
			return betweenDays + 1;    //相等说明确实跨天了
		else
			return betweenDays + 0;
	}

	/**
	 * 间隔小时
	 *
	 * @param from
	 * @param to
	 * @return
	 */
	public static int getDiffHours(Date from, Date to) {
		int hour = (int) ((to.getTime() - from.getTime()) / (60 * 60 * 1000));
		long millis =  ((to.getTime() - from.getTime()) % (60 * 60 * 1000 ));
		if(millis != 0){
			hour+=1;
		}
		return hour;
	}

	/**
	 * 日期加上年月日 得到结果日期
	 *
	 * @param date
	 * @param addYears
	 * @param addMonths
	 * @param addDays
	 * @return
	 */
	public static Date addDate(Date date, int addYears, int addMonths, int addDays) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.YEAR, addYears);//日期减1年
		rightNow.add(Calendar.MONTH, addMonths);//日期加3个月
		rightNow.add(Calendar.DAY_OF_YEAR, addDays);//日期加10天
		return rightNow.getTime();
	}

	/**
	 * 获取指定日期所在周的第一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/**
	 * 获取指定日期所在周的最后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	/**
	 * 获得指定日期的星期几数
	 *
	 * @param date
	 * @return int
	 */
	public static Integer getDayWeekOfDate2(Date date) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(date);
		int weekDay = aCalendar.get(Calendar.DAY_OF_WEEK);

		if (weekDay == 1) {
			return 7;
		} else {
			return weekDay - 1;
		}
	}

	/**
	 * 获取某个时间是一年中的第几周
	 *
	 * @param date
	 * @return
	 */
	public static int getWeekNumOfYear(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}


	/*
	 * 将时间转换为时间戳
	 */
	public static Date dateToStamp(String s, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			return simpleDateFormat.parse(s);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 时间搓转成date
	 * @param timeStamp
	 * @return
	 */
	public static Date timestampToDate(long timeStamp) {
		Timestamp ts = new Timestamp(timeStamp);
		Date date = ts;
		// 很简单，但是此刻date对象指向的实体却是一个Timestamp，即date拥有Date类的方法，但被覆盖的方法的执行实体在Timestamp中。

		date = new Date(ts.getTime());
		return date;
	}

	/*
	 * 将时间戳转换为时间
	 */
	public static String stampToDate(String s){
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	/*
	 * 计算两个时间相差多少分钟
	 */
	public static Integer calculateMinute(Date startDate, Date endDate) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int totalMinute = 0;
		long between = (endDate.getTime() - startDate.getTime()) / 1000;// 除以1000是为了转换成秒
		long min = between / 60;
		totalMinute = Math.toIntExact(min);

		return Math.toIntExact(totalMinute);
	}

	public static String format(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return simpleDateFormat.format(date);
	}
	/*
	 * 将时间转换为时间戳
	 */
	public static String dateToStamp(String s) throws ParseException {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = simpleDateFormat.parse(s);
		long ts = date.getTime();
		res = String.valueOf(ts);
		return res;
	}

}
