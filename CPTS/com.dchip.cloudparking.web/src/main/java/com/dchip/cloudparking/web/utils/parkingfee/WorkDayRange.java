package com.dchip.cloudparking.web.utils.parkingfee;

import com.dchip.cloudparking.web.utils.DateUtil;

import java.util.Date;

public class WorkDayRange {

    private Date workDayBegin = new Date();
    private Date workDayEnd = new Date();
    private int daysAweek;

    public WorkDayRange(Date date,int fromDay,int toDay){
        workDayBegin.setTime(DateUtil.getDayBeginTime(DateUtil.addDate(DateUtil.getFirstDayOfWeek(date),0,0,fromDay - 1)).getTime());
        workDayEnd.setTime(DateUtil.getDayEndTime(DateUtil.addDate(DateUtil.getFirstDayOfWeek(date),0,0,toDay - 1)).getTime());
    }

    public Date getWorkDayBegin() {
        return workDayBegin;
    }

    public Date getWorkDayEnd(){
        return workDayEnd;
    }
}
