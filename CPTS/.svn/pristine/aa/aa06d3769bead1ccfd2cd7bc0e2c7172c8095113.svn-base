package com.dchip.cloudparking.api.utils.parkingfee;

import com.dchip.cloudparking.api.constant.ParkingConstant;
import com.dchip.cloudparking.api.utils.DateUtil;


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

        /**
         * 工作时月卡，370
         */
        String dateInStr = "2019-01-20 08:00:00";//入场时间
        String dateOutStr = "2019-01-21 01:00:00";//出场时间
        String expireDateStr = "2019-01-30 20:00:00";//过期时间

        System.out.println("入场为周"+DateUtil.getDayWeekOfDate2(DateUtil.dateToStamp(dateInStr,"yyyy-MM-dd HH:mm:ss")));
        System.out.println("出场为周"+DateUtil.getDayWeekOfDate2(DateUtil.dateToStamp(dateOutStr,"yyyy-MM-dd HH:mm:ss")));
//        System.out.println(DateUtil.getDayEndTime(new Date()));

//通过
//        System.out.println(DateUtil.getDiffHours(DateUtil.dateToStamp(dateInStr,"yyyy-MM-dd HH:mm:ss"), DateUtil.dateToStamp(dateOutStr,"yyyy-MM-dd HH:mm:ss")));
//        if( null == null){
//            return;
//        }

        /**
         * 收费模板
         */
        FeeModel feeModel = new FeeModel();
        feeModel.setFreeMinute(15 + 1 * 60);
//        feeModel.setFreeMinute(15);
        feeModel.setHourCost(10);
        feeModel.setMostCostDaily(100);

        /**
         * 工作日
         */
        int workDayFrom = 1;//代表周二
        int workDayTo = 5;//代表周五
//        Integer cardType = ParkingConstant.CardType.Month.getTypeValue();

        /**
         * 工作时 参数
         */
        String fromWorkTimeStr = "09:00:00", toWorkTimeStr = "18:00:00";


        FeeServiceKit memberFeeService = new FeeServiceKit( feeModel, dateInStr, dateOutStr, null,null);
        FeeServiceKit monthFeeService = new FeeServiceKit( feeModel, dateInStr, dateOutStr, expireDateStr, ParkingConstant.CardType.Month.getTypeValue());
        FeeServiceKit workDayFeeService = new FeeServiceKit( feeModel, dateInStr, dateOutStr, expireDateStr, ParkingConstant.CardType.WorkDay.getTypeValue(),workDayFrom,workDayTo);
        FeeServiceKit workTimeFeeService = new FeeServiceKit( feeModel, dateInStr, dateOutStr, expireDateStr,ParkingConstant.CardType.WorkTime.getTypeValue(),workDayFrom,workDayTo,fromWorkTimeStr,toWorkTimeStr);

        /**
         * 元
         */
        OutPrintln(memberFeeService.getResultFee());
//        OutPrintln(monthFeeService.getResultFee());
//        OutPrintln(workDayFeeService.getResultFee());
//        OutPrintln(workTimeFeeService.getResultFee());
    }

    private static void OutPrintln(Object obj){
        System.out.println(obj);
    }
}
