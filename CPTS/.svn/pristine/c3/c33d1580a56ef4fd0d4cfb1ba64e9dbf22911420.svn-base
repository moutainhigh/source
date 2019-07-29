package com.dchip.cloudparking.api.utils.parkingfee;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkTimeRange {

    private Date fromDateTime = new Date();
    private Date toDateTime = new Date();
    private long diffsTime = 0;

    public WorkTimeRange(Date thatDate,String fromTimeStr,String toTimeStr){
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        Date fromWorkTime= null,toWorkTime= null;
        try {
            fromWorkTime = formatTime.parse(fromTimeStr);//工作开始时间
            toWorkTime = formatTime.parse(toTimeStr);//工作结束时间
        } catch (Exception e) {
            System.out.println("时间格式错误");
        }

        fromDateTime.setTime(thatDate.getTime());
        fromDateTime.setHours(fromWorkTime.getHours());
        fromDateTime.setMinutes(fromWorkTime.getMinutes());
        fromDateTime.setSeconds(fromWorkTime.getSeconds());

        toDateTime.setTime(thatDate.getTime());
        toDateTime.setHours(toWorkTime.getHours());
        toDateTime.setMinutes(toWorkTime.getMinutes());
        toDateTime.setSeconds(toWorkTime.getSeconds());

        diffsTime = toDateTime.getTime() - fromDateTime.getTime();
    }

    public Date getDateTimeBegin() {
        return fromDateTime;
    }

    public Date getDateTimeEnd() {
        return toDateTime;
    }
    public long getDiffsTime() {
        return diffsTime;
    }

    @Override
    public String toString() {
        return diffsTime+"";
    }
}
