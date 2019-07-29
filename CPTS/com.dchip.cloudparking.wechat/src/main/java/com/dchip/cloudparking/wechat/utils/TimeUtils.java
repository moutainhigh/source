package com.dchip.cloudparking.wechat.utils;

public class TimeUtils {

    /*
     * 毫秒转化时分秒毫秒
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;
 
        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;
        
        StringBuffer sb = new StringBuffer();
        if(day > 0) {
            sb.append(day+"-");//天
        }else {
        	sb.append("0-");
        }
        if(hour > 0) {
            sb.append(hour+"-");//时
        }else {
        	sb.append("0-");
        }
        if(minute > 0) {
            sb.append(minute+"-");//分
        }else {
        	sb.append("0-");
        }
        if(second > 0) {
            sb.append(second+"-");//秒
        }else {
        	sb.append("0-");
        }
        if(milliSecond > 0) {
            sb.append(milliSecond+"-");//毫秒
        }else {
        	sb.append("0-");
        }
        return sb.toString();
    }

    public static Long getMillScondByMunites(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;
 
        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
       // Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        //Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;
        
        return day*24*60+hour*60+minute;
    }
}
