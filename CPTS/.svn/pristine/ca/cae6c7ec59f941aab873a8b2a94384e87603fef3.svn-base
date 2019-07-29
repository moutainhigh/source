package com.dchip.cloudparking.api.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.model.po.FreeStandard;
import com.dchip.cloudparking.api.model.po.ParkingInfo;

public class ParkingFeeUtil implements BaseConstant{

    /**
     * 按计费标准计算停车费
     * @param milliSecond
     * @param hourCircle
     * @param dayCircle
     * @param freeTimeLeng
     * @return
     */
	public static Map<String,Object> getParkingFee(Long milliSecond,BigDecimal hourCircle,BigDecimal dayCircle,Integer freeTimeLeng) {
		Map<String, Object> rsData = new HashMap<String, Object>();
		BigDecimal total = new BigDecimal(0);
		StringBuffer timeLenText = new StringBuffer();
		
		String[] timeLen = TimeUtils.formatTime(milliSecond).split("-");
		Integer day = Integer.parseInt(timeLen[0]);
		Integer hour = Integer.parseInt(timeLen[1]);
		Integer munit = Integer.parseInt(timeLen[2]);
		
		if(day == 0 && hour == 0 && munit <= freeTimeLeng) {
			rsData.put("total", 0);
			rsData.put("timeLen", timeLenText.append(0).append("天").append(0).append("小时").append(munit).append("分钟").toString());
			return rsData;
		}else {
			rsData.put("timeLen",  timeLenText.append(day).append("天").append(hour).append("小时").append(munit).append("分钟").toString());
			if(day >0 ) {
				BigDecimal dayCount = new BigDecimal(day);
				total = total.add(dayCircle.multiply(dayCount));
			}
			if(munit > 0) {
				hour = hour +1;
			}
			
			BigDecimal hourCont = new BigDecimal(hour);
			total = total.add(hourCircle.multiply(hourCont));
			rsData.put("total", total);
			return rsData;
		}
	}
	
	public static BigDecimal getNewParkingFee(Date outDate, ParkingInfo parkingInfo, FreeStandard freeStandard) {
		BigDecimal currentDayFree = new BigDecimal(0);
		Date parkDate = parkingInfo.getParkDate();
		Date parkDateEndTime = new Date();// 停车当天的最大时间
		parkDateEndTime.setYear(parkDate.getYear());
		parkDateEndTime.setMonth(parkDate.getMonth());
		parkDateEndTime.setDate(parkDate.getDate());
		parkDateEndTime.setHours(24);
		parkDateEndTime.setMinutes(0);
		parkDateEndTime.setSeconds(0);
		if (outDate.getTime() > parkDateEndTime.getTime()) {// 停车超过凌晨24点重新计算
			int parkingFirstDayMinute = calculateMinute(parkDate, parkDateEndTime); // 停车超过24点
			int firstTotalHour = parkingFirstDayMinute / 60;// 停了多少小时
			int remainderMinute = parkingFirstDayMinute - firstTotalHour * 60; // 剩余的分钟
			if (remainderMinute >= 0) {
				firstTotalHour += 1;
			}
			/**
			 * 停车当天
			 */
			if (parkingFirstDayMinute < freeStandard.getFreeTimeLength()) {// 还没超过免费时长
				currentDayFree = new BigDecimal(0);
			} else if (freeStandard.getMostCost() != null
					&& new BigDecimal(firstTotalHour).multiply(freeStandard.getHourCost())
							.doubleValue() < freeStandard.getMostCost().doubleValue()) {
				currentDayFree = new BigDecimal(firstTotalHour).multiply(freeStandard.getHourCost());
			} else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
				currentDayFree = new BigDecimal(firstTotalHour).multiply(freeStandard.getHourCost());
			} else {
				currentDayFree = freeStandard.getMostCost();
			}
			/**
			 * 停车当天之后的时间
			 */
			int parkingFirstDayOtherMinute = calculateMinute(parkDateEndTime, outDate); // 停车当天之后的时间
			int afterFirstDayDays = parkingFirstDayOtherMinute / 60 / 24;// 第一天停车之后 后面停了多少天
			int afterFirstDayMinutes = parkingFirstDayOtherMinute - afterFirstDayDays * 24 * 60; // 剩余的分钟
			if (afterFirstDayDays > 0) {
				// 超出停车当日的天数
				if (freeStandard.getMostCost() != null
						&& new BigDecimal(24).multiply(freeStandard.getHourCost()).doubleValue() < freeStandard
								.getMostCost().doubleValue()) {
					currentDayFree = currentDayFree
							.add(new BigDecimal(24 * afterFirstDayDays).multiply(freeStandard.getHourCost()));
				} else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
					currentDayFree = currentDayFree
							.add(new BigDecimal(24 * afterFirstDayDays).multiply(freeStandard.getHourCost()));
				} else {
					currentDayFree = currentDayFree
							.add(freeStandard.getMostCost().multiply(new BigDecimal(afterFirstDayDays)));
				}
				// 还要加上余数分钟
				int endDayHour = afterFirstDayMinutes / 60;// 最后不到一天的小时数
				int endDayHourMinute = afterFirstDayMinutes - endDayHour * 60;// 最后不到一天的小时数-剩余的余数分钟数
				if (endDayHourMinute >= 0) {
					endDayHour += 1;
				}
				// 超出停车当日的天数-但是剩余的不够一天的
				if (freeStandard.getMostCost() != null
						&& new BigDecimal(endDayHour).multiply(freeStandard.getHourCost())
								.doubleValue() < freeStandard.getMostCost().doubleValue()) {
					currentDayFree = currentDayFree
							.add(new BigDecimal(endDayHour).multiply(freeStandard.getHourCost()));
				} else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
					currentDayFree = currentDayFree
							.add(new BigDecimal(endDayHour).multiply(freeStandard.getHourCost()));
				} else {
					currentDayFree = currentDayFree.add(freeStandard.getMostCost());
				}
			} else {
				int secondDayHours = parkingFirstDayOtherMinute / 60; // 只超了停车当天但是又不满一天
				int secondDayMinutes = parkingFirstDayOtherMinute - secondDayHours * 60; // 只超了停车当天但是又不满一天-剩余的分钟的余数
				if (secondDayMinutes >= 0) {
					secondDayHours += 1;
				}
				if (freeStandard.getMostCost() != null
						&& new BigDecimal(secondDayHours).multiply(freeStandard.getHourCost())
								.doubleValue() < freeStandard.getMostCost().doubleValue()) {
					currentDayFree = currentDayFree
							.add(new BigDecimal(secondDayHours).multiply(freeStandard.getHourCost()));
				} else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
					currentDayFree = currentDayFree
							.add(new BigDecimal(secondDayHours).multiply(freeStandard.getHourCost()));
				} else {
					currentDayFree = currentDayFree.add(freeStandard.getMostCost());
				}
			}
		} else {
			int parkingFirstDayMinute = calculateMinute(parkDate, outDate); // 停车不超过停车当天
			int totalHour = parkingFirstDayMinute / 60;
			int remainderMinute = (int) parkingFirstDayMinute - totalHour * 60 - freeStandard.getFreeTimeLength(); // 剩余的分钟的余数
			if (remainderMinute >= 0) {
				totalHour += 1;
			}
			if (parkingFirstDayMinute < freeStandard.getFreeTimeLength()) {// 还没超过免费时长
				currentDayFree = new BigDecimal(0);
			} else if (freeStandard.getMostCost() != null
					&& new BigDecimal(totalHour).multiply(freeStandard.getHourCost())
							.doubleValue() < freeStandard.getMostCost().doubleValue()) {
				currentDayFree = new BigDecimal(totalHour).multiply(freeStandard.getHourCost());
			} else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
				currentDayFree = new BigDecimal(totalHour).multiply(freeStandard.getHourCost());
			} else {
				currentDayFree = freeStandard.getMostCost();
			}
		}
		return currentDayFree;
	}
	
	/*
	 * 计算两个时间相差多少分钟
	 */
	private static  Integer calculateMinute(Date startDate, Date endDate) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int totalMinute = 0;
		long between = (endDate.getTime() - startDate.getTime()) / 1000;// 除以1000是为了转换成秒
		long min = between / 60;
		totalMinute = Math.toIntExact(min);

		return Math.toIntExact(totalMinute);
	}
	
	public static String getParkingLen(Long milliSecond) {
		StringBuffer timeLenText = new StringBuffer();
		
		String[] timeLen = TimeUtils.formatTime(milliSecond).split("-");
		Integer day = Integer.parseInt(timeLen[0]);
		Integer hour = Integer.parseInt(timeLen[1]);
		Integer munit = Integer.parseInt(timeLen[2]);
		return timeLenText.append(day).append("天").append(hour).append("小时").append(munit).append("分钟").toString();
	}
	
	
}
