package com.dchip.cloudparking.wechat.utils.parkingfee;

import com.dchip.cloudparking.wechat.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;


public class FeeServiceUtil {

    public static final int ONE_MINUTE_TIME = 60 * 1000;
    public static final int ONE_HOUR_TIME = 60 * ONE_MINUTE_TIME;
    public static final int ONE_DAY = 24 * ONE_HOUR_TIME;

    /**
     * 临时车：使用会员计费规则
     * @param feeModel
     * @param dateInStr
     * @param dateOutStr
     */
    public static double getFeeWithMemberRule(FeeModel feeModel, String dateInStr, String dateOutStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateIn = null;
        Date dateOut = null;
        try {
            dateIn = format.parse(dateInStr);//进场时间
            dateOut = format.parse(dateOutStr);//出场时间
        } catch (Exception e) {
            throw new RuntimeException("时间格式错误");
        }

        // 出场时间 在免费时间范围  内免费
        if (dateOut.getTime() - dateIn.getTime() <= feeModel.getFreeMinute() * ONE_MINUTE_TIME) {
        	return 0;
        }
        
        Date dateFeeBegin = new Date();//声明开始计费时间
        if (feeModel != null && feeModel.getFreeMinute() != 0) {
        	dateFeeBegin.setTime(dateIn.getTime() + (feeModel.getFreeMinute() * ONE_MINUTE_TIME));//计算开始计费时间
		}else {
			dateFeeBegin.setTime(dateIn.getTime());
		}

        //----------------- 时间超过免费截止时间,假定免费分钟不跨天，跨天保持和原来相同
        /**
         * 同天进出场
         */
        if (DateUtil.isSameDate(dateIn, dateOut)) {
            long hours = (dateOut.getTime() - dateFeeBegin.getTime()) / (ONE_HOUR_TIME);
            if ((dateOut.getTime() - dateFeeBegin.getTime()) % (ONE_HOUR_TIME) == 0) {
                return (int)(hours) * feeModel.getHourCost();
            }
            if((int)(hours + 1) * feeModel.getHourCost() > feeModel.getMostCostDaily()){
                return feeModel.getMostCostDaily();
            }
            return (int)(hours + 1) * feeModel.getHourCost();
        }
        /**
         * 跨天进出场
         */
        else {
            int stayDays = DateUtil.getDiffDays(dateFeeBegin, dateOut);//停车 日期间隔天数 , 这里肯定 大于等于 1

            double fee1 = getFee1(feeModel, dateFeeBegin);//第一天费用
            double fee2 = 0;
            if (!isInFreeRange(DateUtil.getDayBeginTime(dateOut), dateOut, feeModel)) {
                fee2 = getFee2(feeModel, dateOut);//跨天出场当天的计费
            }
            //跨1天出场
            if (stayDays == 1) {
                return (fee1 + fee2);
            }
            //跨多天出场
            else if (stayDays > 1) {
                return (fee1 + fee2 + getFee3(feeModel, (stayDays - 1)));
            }else {
                return (fee1 + fee2 + getFee3(feeModel, (stayDays - 1)));
            }
        }
    }

    /**
     * 跨1或多天  入场开始当天的计费
     *
     * @param feeModel
     * @param dateFeeBegin
     * @return
     */
    public static double getFee1(FeeModel feeModel, Date dateFeeBegin) {
        double fee = feeModel.getHourCost() * (DateUtil.getDiffHours(dateFeeBegin, DateUtil.getDayEndTime(dateFeeBegin))) ;
        if (fee > feeModel.getMostCostDaily()) {
            return feeModel.getMostCostDaily();
        }
        return fee;
    }

    /**
     * 跨天出场当天的计费
     *
     * @param feeModel
     * @param dateOut
     * @return
     */
    public static double getFee2(FeeModel feeModel, Date dateOut) {
        Date dateFeeBegin = new Date();
        dateFeeBegin.setTime(DateUtil.getDayBeginTime(dateOut).getTime());
        double hourCost = feeModel.getHourCost() * (DateUtil.getDiffHours(dateFeeBegin, dateOut));
        if (hourCost > feeModel.getMostCostDaily()) {
            return feeModel.getMostCostDaily();
        }
        return hourCost;
    }

    /**
     * 间隔天 按天数计费
     *
     * @param feeModel
     * @param days
     * @return
     */
    public static double getFee3(FeeModel feeModel, int days) {
        if(24 * feeModel.getHourCost() > feeModel.getMostCostDaily()){
            return feeModel.getMostCostDaily() * days;
        }else {
            return (24 * feeModel.getHourCost()) * days;
        }
    }

    /**
     * 出场时间是否在免费范围
     *
     * @param dateIn
     * @param dateOut
     * @param feeModel
     * @return
     */
    public static boolean isInFreeRange(Date dateIn, Date dateOut, FeeModel feeModel) {
        if (dateOut.getTime() - dateIn.getTime() <= feeModel.getFreeMinute() * 1000) {
            return true;
        }
        return false;
    }

    /**
     * 计算工作日-月卡费用
     * @param feeModel
     * @param workDayFrom
     * @param workDayTo
     * @param dateInStr
     * @param dateOutStr
     * @return
     */
    public static double getFeeWithWorkDay(FeeModel feeModel,int workDayFrom,int workDayTo,String dateInStr,String dateOutStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateIn = null;
        Date dateOut = null;
        try {
            dateIn = format.parse(dateInStr);//进场时间
            dateOut = format.parse(dateOutStr);//出场时间
        } catch (Exception e) {
            throw new RuntimeException("时间格式错误");
        }

        int diffDays = DateUtil.getDiffDays(dateIn,dateOut);
        int fee = 0;
        if(diffDays == 0){
            if(!isInWorkDay(dateInStr,workDayFrom,workDayTo)){
                return getFeeWithMemberRule(feeModel,dateInStr,dateOutStr);
            }
        }
        else{
            if(!isInWorkDay(dateInStr,workDayFrom,workDayTo)){
                fee+=getFeeWithMemberRule(feeModel,dateInStr,DateUtil.getDateTimeStr(DateUtil.getDayEndTime(dateIn)));
            }
            if(!isInWorkDay(dateOutStr,workDayFrom,workDayTo)){
                fee+=getFeeWithMemberRule(feeModel,DateUtil.getDateTimeStr(DateUtil.getDayBeginTime(dateOut)),dateOutStr);
            }
        }
        //getDaysNotInWorkDay会除去进场天和出场天
        int notInWorkDays = FeeServiceUtil.getDaysNotInWorkDay(workDayFrom,workDayTo,dateIn,dateOut);
        if(24 * feeModel.getHourCost() > feeModel.getMostCostDaily()){
            fee+= notInWorkDays* feeModel.getMostCostDaily();
        }else {
            fee+= notInWorkDays * 24 * feeModel.getHourCost();
        }
        return fee;
    }

    public static double getFeeWithWorkTime(FeeModel feeModel,int workDayFrom,int workDayTo,String fromWorkTimeStr,String toWorkTimeStr,String dateInStr,String dateOutStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            final Date dateIn = format.parse(dateInStr);//进场时间
            final Date dateOut = format.parse(dateOutStr);//出场时间

            int diffDays = DateUtil.getDiffDays(dateIn,dateOut);
            int fee = 0;
            for(int i = 0 ; i <= diffDays; i++){
                Date currentDay = DateUtil.addDate(dateIn,0,0,i);
                WorkTimeRange workTimeRange = new WorkTimeRange(currentDay,fromWorkTimeStr,toWorkTimeStr);

                if(diffDays == 0){
                    //第一天进出场要给 feeModel.getFreeMinute() 分钟的免费时间
                    if(isInFreeRange(dateIn,dateOut,feeModel)){
                        return 0;
                    }else {
                        return FeeServiceUtil.getFeeWithWorkTimeFirstDay(feeModel,workTimeRange,currentDay,dateOut);
                    }
                }
                if(isInWorkDay(currentDay,workDayFrom,workDayTo)){//在工作日
                    if( i == 0){
                        fee += FeeServiceUtil.getFeeWithWorkTimeThatDay(feeModel,workTimeRange,currentDay,DateUtil.getDayEndTime(currentDay));
                    }else  if(i == diffDays){//如果第 (i+1) 天不是出场时间
                        fee += FeeServiceUtil.getFeeWithWorkTimeThatDay(feeModel,workTimeRange,DateUtil.getDayBeginTime(currentDay),dateOut);
                    }else{//如果第 (i+1) 天是出场时间
                        fee += FeeServiceUtil.getFeeWithWorkTimeThatDay(feeModel,workTimeRange,DateUtil.getDayBeginTime(currentDay),DateUtil.getDayEndTime(currentDay));
                    }
                }else{//不在工作日
                    if( i == 0){
//                        fee += FeeServiceUtil.getFeeWithWorkTimeThatDay(feeModel,null,currentDay,DateUtil.getDayEndTime(currentDay));
                        fee += FeeServiceUtil.getFeeWithMemberRule(feeModel,DateUtil.getDateTimeStr(currentDay),DateUtil.getDateTimeStr(DateUtil.getDayEndTime(currentDay)));
                    }else if( i == diffDays ){//如果第 (i+1) 天不是出场时间
                        fee += FeeServiceUtil.getFeeWithMemberRule(feeModel,DateUtil.getDateTimeStr(DateUtil.getDayBeginTime(currentDay)),DateUtil.getDateTimeStr(dateOut));
                    }else{//如果第 (i+1) 天是出场时间
                        fee += FeeServiceUtil.getFeeWithMemberRule(feeModel,DateUtil.getDateTimeStr(DateUtil.getDayBeginTime(currentDay)),DateUtil.getDateTimeStr(DateUtil.getDayEndTime(currentDay)));
                    }
                }
            }
            return fee;
        } catch (Exception e) {
            throw new RuntimeException("时间格式错误");
        }
    }

    public static boolean isInWorkDay(String dateStr,int workDayFrom,int workDayTo){
        int weekday = DateUtil.getDayWeekOfDate2(DateUtil.dateToStamp(dateStr,"yyyy-MM-dd HH:mm:ss"));
        if(weekday < workDayFrom || weekday > workDayTo){
            return false;
        }
        return true;
    }
    public static boolean isInWorkDay(Date date,int workDayFrom,int workDayTo){
        int weekday = DateUtil.getDayWeekOfDate2(date);
        if(weekday < workDayFrom || weekday > workDayTo){
            return false;
        }
        return true;
    }

    /**
     * 免费的15分钟是跨工作时的，所以要单独处理
     * @param feeModel
     * @param workTimeRange
     * @param timeBegin
     * @param timeEnd
     * @return
     */
    public static double getFeeWithWorkTimeFirstDay(FeeModel feeModel,WorkTimeRange workTimeRange,Date timeBegin,Date timeEnd){
        long diffTimes;
        long hours;
        long restMinute;
        //完全不在工作时
        if(timeEnd.before(workTimeRange.getDateTimeBegin()) || timeBegin.after(workTimeRange.getDateTimeEnd())){
            diffTimes = (timeEnd.getTime() - timeBegin.getTime() - feeModel.getFreeMinute() * ONE_MINUTE_TIME);
            hours =  diffTimes / ONE_HOUR_TIME;
            restMinute = diffTimes % ONE_HOUR_TIME;
        }
        //进场不在工作时，出场在工作时
        else if(timeBegin.getTime() < workTimeRange.getDateTimeBegin().getTime()
                && timeEnd.getTime() >= workTimeRange.getDateTimeBegin().getTime()
                && timeEnd.getTime() <= workTimeRange.getDateTimeEnd().getTime()){
            diffTimes = (workTimeRange.getDateTimeBegin().getTime() - timeBegin.getTime()) - feeModel.getFreeMinute() * ONE_MINUTE_TIME;
            hours =  diffTimes / ONE_HOUR_TIME;
            restMinute = diffTimes % ONE_HOUR_TIME;
        }
        //进场在工作时，出场不在工作时
        else if(timeBegin.getTime() >= (workTimeRange.getDateTimeBegin()).getTime()
                && timeBegin.getTime() <= workTimeRange.getDateTimeEnd().getTime()
                && timeEnd.getTime() > workTimeRange.getDateTimeEnd().getTime()){
            diffTimes = (timeEnd.getTime() - workTimeRange.getDateTimeEnd().getTime()) - feeModel.getFreeMinute() * ONE_MINUTE_TIME;
            hours =  diffTimes / ONE_HOUR_TIME;
            restMinute = diffTimes % ONE_HOUR_TIME;
        }
        //中间跨工作时
        else if(timeBegin.before(workTimeRange.getDateTimeBegin()) && timeEnd.after(workTimeRange.getDateTimeEnd())){
            diffTimes = (timeEnd.getTime() - timeBegin.getTime() - workTimeRange.getDiffsTime() )  - feeModel.getFreeMinute() * ONE_MINUTE_TIME;
            if(diffTimes < 0 ){
                return 0;
            }
            hours =  diffTimes / ONE_HOUR_TIME;
            restMinute = diffTimes % ONE_HOUR_TIME;
        } else {
            return 0;
        }

        if(restMinute != 0){
            hours++;
        }
        if(hours* feeModel.getHourCost() > feeModel.getMostCostDaily()){
            return feeModel.getMostCostDaily();
        }else{
            return hours * feeModel.getHourCost();
        }
    }
    /**
     * 这里的workTimeRange 日期要求是同一天，只是工作开始和结束时间不同
     * @param feeModel
     * @param workTimeRange  //用于空（完全不在工作时）与非空（在工作时）判断
     * @param timeBegin
     * @param timeEnd
     * @return
     */
    public static double getFeeWithWorkTimeThatDay(FeeModel feeModel,WorkTimeRange workTimeRange,Date timeBegin,Date timeEnd){
        long diffTimes;
        long hours;
        long restMinute;
        //完全不在工作时，workTimeRange为空表示不在工作日
        if(workTimeRange == null || timeEnd.before(workTimeRange.getDateTimeBegin()) || timeBegin.after(workTimeRange.getDateTimeEnd())){
            diffTimes = (timeEnd.getTime() - timeBegin.getTime());
            hours =  diffTimes / ONE_HOUR_TIME;
            restMinute = diffTimes % ONE_HOUR_TIME;
        }
        //进场不在工作时，出场在工作时
        else if(timeBegin.getTime() < workTimeRange.getDateTimeBegin().getTime()
                && timeEnd.getTime() >= workTimeRange.getDateTimeBegin().getTime()
                && timeEnd.getTime() <= workTimeRange.getDateTimeEnd().getTime()){
            diffTimes = (workTimeRange.getDateTimeBegin().getTime() - timeBegin.getTime()) ;
            hours =  diffTimes / ONE_HOUR_TIME;
            restMinute = diffTimes % ONE_HOUR_TIME;
        }
        //进场在工作时，出场不在工作时
        else if(timeBegin.getTime() >= (workTimeRange.getDateTimeBegin()).getTime()
                && timeBegin.getTime() <= workTimeRange.getDateTimeEnd().getTime()
                && timeEnd.getTime() > workTimeRange.getDateTimeEnd().getTime()){
            diffTimes = (timeEnd.getTime() - workTimeRange.getDateTimeEnd().getTime()) ;
            hours =  diffTimes / ONE_HOUR_TIME;
            restMinute = diffTimes % ONE_HOUR_TIME;
        }
        //中间跨工作时
        else if(timeBegin.before(workTimeRange.getDateTimeBegin()) && timeEnd.after(workTimeRange.getDateTimeEnd())){
            diffTimes = (timeEnd.getTime() - timeBegin.getTime() - (workTimeRange.getDiffsTime())) ;
            hours =  diffTimes / ONE_HOUR_TIME;
            restMinute = diffTimes % ONE_HOUR_TIME;
        } else {
            return 0;
        }

        if(restMinute != 0){
            hours++;
        }
        if(hours* feeModel.getHourCost() > feeModel.getMostCostDaily()){
            return feeModel.getMostCostDaily();
        }else{
            return hours * feeModel.getHourCost();
        }
    }

    /**
     * 判断当天是否完全在工作时
     * @param workTimeRange
     * @param timeBegin
     * @param timeEnd
     * @return
     */
    public static boolean isNotInWorkTimeRangeThatDay(WorkTimeRange workTimeRange,Date timeBegin,Date timeEnd){
        if(workTimeRange.getDateTimeBegin().after(timeEnd) || workTimeRange.getDateTimeEnd().before(timeBegin)){
            return true;
        }
        return false;
    }

    /**
     * 计算dateIn到dateOut期间内非工作的天数，注意此时是在dateIn和dateOut跨1或者多天条件下
     * @param workDayFrom
     * @param workDayTo
     * @param dateIn
     * @param dateOut
     * @return
     */
    public static int getDaysNotInWorkDay(int workDayFrom,int workDayTo,Date dateIn,Date dateOut){
        int days = 0;
        int diffDays = DateUtil.getDiffDays(dateIn,dateOut);
        int week1 = DateUtil.getDayWeekOfDate2(dateIn);

        for(int i = 1; i < diffDays - 1 ;i++ ){
            int curDay = (week1 + i ) % 7;
            if(workDayFrom > curDay || workDayTo <curDay){
                days++;
            }
        }
        return days;
    }

}
