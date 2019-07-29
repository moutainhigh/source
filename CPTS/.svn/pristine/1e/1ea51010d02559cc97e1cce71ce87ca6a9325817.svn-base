package com.dchip.cloudparking.socket.utils;

public class RedisKeyUtil {
	/**
	 * 获取字符串数组第一个元素
	 * @param redis
	 * @return
	 */
	public static String getFirstKey(String redis) {
		if(redis != null && redis.length() > 0) {
			String[] strings= redis.split("-");
			return strings[0];
		}else {
			return "";
		}
	}
	/**
	 * 获取字符串数组第二个元素
	 * @param redis
	 * @return
	 */
	public static String getSecondKey(String redis) {
		if(redis != null && redis.length() > 0) {
			String[] strings= redis.split("-");
			return strings[1];
		}else {
			return "";
		}
	}
}
