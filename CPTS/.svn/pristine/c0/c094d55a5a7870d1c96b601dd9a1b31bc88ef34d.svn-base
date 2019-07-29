package com.dchip.cloudparking.api.utils.parkingfee;

import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.model.po.Deduction;
import com.dchip.cloudparking.api.model.qpo.QDeduction;
import com.dchip.cloudparking.api.utils.DateUtil;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeeServiceDeduction {

    public static final int ONE_MINUTE_TIME = 60 * 1000;
    public static final int ONE_HOUR_TIME = 60 * ONE_MINUTE_TIME;
    public static final int ONE_DAY = 24 * ONE_HOUR_TIME;

    // 1.查询商户是否存在
    // 2.查询商户(含parkingId) 所在的停车场是否可用的抵扣券模板
    // 3.（领券操作）将抵扣券、模板 和 车牌 保存到 2 个表格
    public static Deduction getDeduction(JPAQueryFactory jpaQueryFactory, String carNum, String outDate){
        QDeduction qDeduction = QDeduction.deduction;
        List<Predicate> predicates = new ArrayList<>();
        //条件：未领用
        predicates.add(qDeduction.status.eq(BaseConstant.DeductionStatus.NotUsedStatus.getTypeValue()));
        //停车时间在抵扣券过期时间 之前
        predicates.add(
                (qDeduction.dueTime.isNotNull().and(qDeduction.dueTime.after(DateUtil.dateToStamp(outDate,"yyyy-MM-dd HH:mm:ss"))))
                .or(qDeduction.dueTime.isNull()));
        predicates.add(qDeduction.licensePlat.eq(carNum));
        Deduction deduction = jpaQueryFactory
                .select(qDeduction)
                .from(qDeduction)
                .where(predicates.toArray(new Predicate[predicates.size()])).fetchFirst();
        if(deduction != null && deduction.getHourNum() != null){
            return deduction;
        }
        return null;
    }

    public static boolean disableDeduction(JPAQueryFactory jpaQueryFactory,Integer userId, Deduction deduction){
        if(deduction == null){
            return false;
        }
        QDeduction qDeduction = QDeduction.deduction;
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(qDeduction.id.eq(deduction.getId()));
        long effect = jpaQueryFactory.update(qDeduction)
                .set(qDeduction.userId,userId )
                .set(qDeduction.useTime,new Date())
                .set(qDeduction.status, BaseConstant.DeductionStatus.UsedStatus.getTypeValue())
                .where(predicates.toArray(new Predicate[predicates.size()])).execute();
        if(effect > 0){
            return true;
        }
        return false;
    }
}
