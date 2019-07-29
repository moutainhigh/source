package com.dchip.cloudparking.wechat.utils.parkingfee;
import com.dchip.cloudparking.wechat.constant.BaseConstant;
import com.dchip.cloudparking.wechat.utils.DateUtil;

import java.util.Date;

public class FeeTest {

    public static void main(String[] args){

//        // 跨多天 + 过期 + 计费
//        String dateInStr = "2018-12-20 08:00:00";//入场时间
//        String dateOutStr = "2018-12-21 01:00:00";//出场时间
//        String expireDateStr = "2018-12-20 20:00:00";//过期时间

        // 跨1周 + 过期 + 计费
//        String dateInStr = "2018-12-23 08:00:00";//入场时间
//        String dateOutStr = "2018-12-24 20:00:00";//出场时间
//        String expireDateStr = "2018-12-25 14:00:00";//过期时间


//        String dateInStr = "2018-12-22 08:00:00";//入场时间
//        String dateOutStr = "2018-12-23 20:00:00";//出场时间
//        String expireDateStr = "2018-12-23 10:00:01";//过期时间//300

        /**
         * 工作时，9点 到 18点，20
         */
//        String dateInStr = "2018-12-20 08:00:00";//入场时间
//        String dateOutStr = "2018-12-20 19:00:00";//出场时间
//        String expireDateStr = "2018-12-23 19:00:00";//过期时间

        /**
         * 工作时月卡，30
         */
//        String dateInStr = "2018-12-20 05:00:00";//入场时间
//        String dateOutStr = "2018-12-20 08:00:00";//出场时间
//        String expireDateStr = "2018-12-23 19:00:00";//过期时间

        /**
         * 工作时月卡，100
         */
//        String dateInStr = "2018-12-20 08:00:00";//入场时间
//        String dateOutStr = "2018-12-20 24:00:00";//出场时间
//        String expireDateStr = "2018-12-20 10:00:00";//过期时间

        /**
         * 工作时月卡，370
         */
//        String dateInStr = "2018-12-20 08:00:00";//入场时间
//        String dateOutStr = "2018-12-23 24:00:00";//出场时间
//        String expireDateStr = "2018-12-21 12:00:00";//过期时间

        String rangeStart = "2017-06-01 00:00:00",rangeEnd = "2018-12-12 23:59:59";
        Date testDateIn = DateUtil.randomDate(rangeStart, "2018-06-01 23:59:59");
        Date testDateOut = DateUtil.randomDate("2018-06-02 00:00:00", rangeEnd);
        Date testDateExpire = DateUtil.randomDate(rangeStart, rangeEnd);
        String dateInStr = DateUtil.getDateTimeStr(testDateIn);
        String dateOutStr = DateUtil.getDateTimeStr(testDateOut);
        String expireDateStr = DateUtil.getDateTimeStr(testDateExpire);
        /**
         * 收费模板
         */
        FeeModel feeModel = new FeeModel();
        feeModel.setFreeMinute(15 + 1 * 60);
        feeModel.setHourCost(10);
        feeModel.setMostCostDaily(100);
        //工作日 参数
        int workDayFrom = 1;//代表周二
        int workDayTo = 5;//代表周五
        //工作时 参数
        String fromWorkTimeStr = "09:00:00", toWorkTimeStr = "18:00:00";

        System.out.println("首先,工作时间范围:"+fromWorkTimeStr+" ~ "+toWorkTimeStr);
        System.out.println("从 "+dateInStr+" 入场到 "+dateOutStr+" 出场,跨越 "+DateUtil.getDiffDays(dateInStr,dateOutStr)+" 天");
        System.out.println("  月卡过期时间: "+expireDateStr);
        System.out.println("入场与过期时间相差:"+DateUtil.getDiffDays(dateInStr,expireDateStr)+"天/"+DateUtil.getDiffWeeks(dateInStr,expireDateStr)+"周");
        System.out.println("出场与过期时间相差:"+DateUtil.getDiffDays(dateOutStr,expireDateStr)+"天/"+DateUtil.getDiffWeeks(dateOutStr,expireDateStr)+"周");
        System.out.println("  入场为周"+DateUtil.getDayWeekOfDate2(DateUtil.dateToStamp(dateInStr,"yyyy-MM-dd HH:mm:ss"))+",出场为周"+DateUtil.getDayWeekOfDate2(DateUtil.dateToStamp(dateOutStr,"yyyy-MM-dd HH:mm:ss")));

        FeeServiceKit memberFeeService = new FeeServiceKit( feeModel, dateInStr, dateOutStr, null,null);
        FeeServiceKit monthFeeService = new FeeServiceKit( feeModel, dateInStr, dateOutStr, expireDateStr, BaseConstant.CardType.Month.getTypeValue());
        FeeServiceKit workDayFeeService = new FeeServiceKit( feeModel, dateInStr, dateOutStr, expireDateStr,BaseConstant.CardType.WorkDay.getTypeValue(),workDayFrom,workDayTo);
        FeeServiceKit workTimeFeeService = new FeeServiceKit( feeModel, dateInStr, dateOutStr, expireDateStr,BaseConstant.CardType.WorkTime.getTypeValue(),workDayFrom,workDayTo,fromWorkTimeStr,toWorkTimeStr);

        /**
         * 元
         */
        OutPrintln("  普通会员费用:"+memberFeeService.getResultFee());
        OutPrintln("  全天月卡费用:"+monthFeeService.getResultFee());
        OutPrintln("工作日月卡费用:"+workDayFeeService.getResultFee());
        OutPrintln("工作时月卡费用:"+workTimeFeeService.getResultFee());
    }

    private static void OutPrintln(Object obj){
        System.out.println(obj);
    }
}
