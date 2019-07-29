package com.dchip.cloudparking.api.utils.parkingfee;

import com.dchip.cloudparking.api.constant.ParkingConstant;
import com.dchip.cloudparking.api.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FeeServiceKit {

    private String dateInStr;
    private String dateOutStr;
    private String expireDateStr;
    private int workDayFrom;
    private int workDayTo;
    private Integer cardType;
    private FeeModel feeModel;

    /**
     * 工作时 参数
     */
    private String fromWorkTimeStr;
    private String toWorkTimeStr;

    public FeeServiceKit(FeeModel feeModel, String dateInStr, String dateOutStr, String expireDateStr, Integer cardType) {
        this.feeModel = feeModel;
        this.dateInStr = dateInStr;
        this.dateOutStr = dateOutStr;
        this.expireDateStr = expireDateStr;
        this.cardType = cardType;
    }

    public FeeServiceKit(FeeModel feeModel, String dateInStr, String dateOutStr, String expireDateStr, Integer cardType, int workDayFrom, int workDayTo) {
        this( feeModel, dateInStr,  dateOutStr,  expireDateStr,cardType);
        this.workDayFrom = workDayFrom;
        this.workDayTo = workDayTo;
    }

    public FeeServiceKit(FeeModel feeModel, String dateInStr, String dateOutStr, String expireDateStr, Integer cardType, int workDayFrom, int workDayTo, String fromWorkTimeStr, String toWorkTimeStr) {
        this( feeModel, dateInStr,  dateOutStr,  expireDateStr, cardType, workDayFrom,  workDayTo);
        this.fromWorkTimeStr = fromWorkTimeStr;
        this.toWorkTimeStr = toWorkTimeStr;
    }

    public void setDateInStr(String dateInStr) {
        this.dateInStr = dateInStr;
    }

    public void setDateOutStr(String dateOutStr) {
        this.dateOutStr = dateOutStr;
    }

    public void setExpireDateStr(String expireDateStr) {
        this.expireDateStr = expireDateStr;
    }

    public void setWorkDayFrom(int workDayFrom) {
        this.workDayFrom = workDayFrom;
    }

    public void setWorkDayTo(int workDayTo) {
        this.workDayTo = workDayTo;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public void setFeeModel(FeeModel feeModel) {
        this.feeModel = feeModel;
    }

    public void setFromWorkTimeStr(String fromWorkTimeStr) {
        this.fromWorkTimeStr = fromWorkTimeStr;
    }

    public void setToWorkTimeStr(String toWorkTimeStr) {
        this.toWorkTimeStr = toWorkTimeStr;
    }

    public double getResultFee() {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateIn = null;
        Date dateOut = null;
        Date expireDate = null;
        try {
            dateIn = formatDate.parse(dateInStr);//进场日期时间
            dateOut = formatDate.parse(dateOutStr);//出场日期时间
            expireDate = formatDate.parse(expireDateStr);//过期日期时间
        } catch (Exception e) {
            if(expireDate != null){
                throw new RuntimeException("日期时间格式错误");
            }
        }

        //会员
        if (cardType == null) {
            return FeeServiceUtil.getFeeWithMemberRule(feeModel, dateInStr, dateOutStr);
        }

        //月卡
        if (cardType == ParkingConstant.CardType.Month.getTypeValue()) {//全天
            if (expireDate.after(dateOut)) {//过期时间在出场时间之后，放行
                return 0;
            } else {//过期
                if (expireDate.after(dateIn)) {//部分过期
                    return FeeServiceUtil.getFeeWithMemberRule(feeModel, expireDateStr, dateOutStr);
                } else {//完全过期
                    return FeeServiceUtil.getFeeWithMemberRule(feeModel, dateInStr, dateOutStr);
                }
            }
        } else if (cardType == ParkingConstant.CardType.WorkDay.getTypeValue()) {//工作日
            if (expireDate.after(dateOut)) {//放行
                return FeeServiceUtil.getFeeWithWorkDay(feeModel, workDayFrom, workDayTo, dateInStr, dateOutStr);
            } else {//过期
                if (expireDate.after(dateIn)) {//部分过期
                    //=============  判断过期当天最高费用，计算应带减去的多余计费 start
                    double beforeExpireFee = FeeServiceUtil.getFeeWithWorkDay(feeModel, workDayFrom, workDayTo, DateUtil.getDateTimeStr(DateUtil.getDayBeginTime(expireDate)), expireDateStr);
                    double afterExpireFee = 0;
                    if(DateUtil.getDiffDays(expireDate,dateOut) == 0 || (DateUtil.getDiffDays(expireDate,dateOut) == 1 && dateOut.getTime() == DateUtil.getDayBeginTime(dateOut).getTime())){
                        afterExpireFee = FeeServiceUtil.getFeeWithMemberRule(feeModel, dateInStr, expireDateStr);
                    }else{
                        afterExpireFee = FeeServiceUtil.getFeeWithMemberRule(feeModel, DateUtil.getDateTimeStr(DateUtil.getDayEndTime(expireDate)),expireDateStr);
                    }
                    double minusFee = 0;
                    if(beforeExpireFee + afterExpireFee > feeModel.getMostCostDaily()){
                        minusFee = beforeExpireFee + afterExpireFee - feeModel.getMostCostDaily();
                    }
                    //=============  判断过期当天最高费用，计算应带减去的多余计费 end
                    return FeeServiceUtil.getFeeWithWorkDay(feeModel, workDayFrom, workDayTo, dateInStr, expireDateStr) + FeeServiceUtil.getFeeWithMemberRule(feeModel, expireDateStr, dateOutStr) - minusFee;
                } else {//完全过期
                    return FeeServiceUtil.getFeeWithMemberRule(feeModel, dateInStr, dateOutStr);
                }
            }
        } else if (cardType == ParkingConstant.CardType.WorkTime.getTypeValue()) {//工作时
            if (expireDate.after(dateOut)) {//放行
                return FeeServiceUtil.getFeeWithWorkTime(feeModel, workDayFrom, workDayTo, fromWorkTimeStr, toWorkTimeStr, dateInStr, dateOutStr);
            } else {//过期
                if (expireDate.after(dateIn)) {//部分过期
                    //=============  判断过期当天最高费用，计算应带减去的多余计费 start
                    WorkTimeRange workTimeRange = new WorkTimeRange(expireDate,fromWorkTimeStr,toWorkTimeStr);
                    double beforeExpireFee = 0;
                    if(DateUtil.getDiffDays(dateIn,expireDate) == 0 || (DateUtil.getDiffDays(dateIn,expireDate) == 1  && expireDate.getTime() == DateUtil.getDayBeginTime(expireDate).getTime())){
                        beforeExpireFee = FeeServiceUtil.getFeeWithWorkTimeThatDay(feeModel, workTimeRange, dateIn , expireDate);
                    }else{
                        beforeExpireFee = FeeServiceUtil.getFeeWithWorkTimeThatDay(feeModel, workTimeRange, DateUtil.getDayBeginTime(expireDate), expireDate);
                    }
                    double afterExpireFee = 0;
                    if(DateUtil.getDiffDays(expireDate,dateOut) == 0 || (DateUtil.getDiffDays(expireDate,dateOut) == 1  && dateOut.getTime() == DateUtil.getDayBeginTime(dateOut).getTime())){
                        afterExpireFee = FeeServiceUtil.getFeeWithMemberRule(feeModel, expireDateStr, dateOutStr);
                    }else{
                        afterExpireFee = FeeServiceUtil.getFeeWithMemberRule(feeModel, expireDateStr, DateUtil.getDateTimeStr(DateUtil.getDayEndTime(expireDate)));
                    }
                    double minusFee = 0;
                    if(beforeExpireFee + afterExpireFee > feeModel.getMostCostDaily()){
                        minusFee = beforeExpireFee + afterExpireFee - feeModel.getMostCostDaily();
                    }
                    //=============  判断过期当天最高费用，计算应带减去的多余计费 end
                    return FeeServiceUtil.getFeeWithWorkTime(feeModel, workDayFrom, workDayTo, fromWorkTimeStr, toWorkTimeStr, dateInStr, expireDateStr) + FeeServiceUtil.getFeeWithMemberRule(feeModel, expireDateStr, dateOutStr) - minusFee;
                } else {//完全过期
                    return FeeServiceUtil.getFeeWithMemberRule(feeModel, dateInStr, dateOutStr);
                }
            }
        }else {
            return 0;
        }
    }

}
