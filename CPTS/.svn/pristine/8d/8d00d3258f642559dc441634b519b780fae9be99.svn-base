package com.dchip.cloudparking.web.serviceImp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.po.qpo.QUser;
import com.dchip.cloudparking.web.po.qpo.QUserCoupon;
import com.dchip.cloudparking.web.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import com.dchip.cloudparking.web.iRepository.IActivityRepository;
import com.dchip.cloudparking.web.iRepository.ICouponRepository;
import com.dchip.cloudparking.web.iService.IActivityManageService;
import com.dchip.cloudparking.web.model.po.Activity;
import com.dchip.cloudparking.web.model.vo.ActivityVo;
import com.dchip.cloudparking.web.po.qpo.QActivity;
import com.dchip.cloudparking.web.po.qpo.QCoupon;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.dchip.cloudparking.web.utils.StrKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class ActivityManageServiceImp extends BaseService implements IActivityManageService {
    @Autowired
    private IActivityRepository activityRepository;
    @Autowired
    private ICouponRepository couponRepository;

    @Override
    public Object getActivityList(Integer pageSize, Integer pageNum, String sortName, String direction) {
        List<Map<String, Object>> data = new ArrayList<>();
        QActivity qActivity = QActivity.activity; //优惠活动
        QCoupon qCoupon = QCoupon.coupon; //优惠卷

        /**
         * 查询条件的谓语集合(where条件)
         */

        //动态排序,需要使用自定义的Sort类
        Sort sort = null;
        switch (sortName) {
            case "activityRemark":
                sort = new Sort("remark", direction.toString(), qActivity);
                break;
            case "couponStatus":
                sort = new Sort("status", direction.toString(), qCoupon);
                break;
            case "couponRemark":
                sort = new Sort("remark", direction.toString(), qCoupon);
                break;
            default:
                sort = new Sort(sortName, direction, qActivity);
                break;
        }
        //查询结果
        JPAQuery<Tuple> jPAQuery = jpaQueryFactory
                .select(qActivity.id, qActivity.remark, qActivity.effectiveType, qActivity.effectiveTime,
                        qActivity.createTime, qActivity.couponId, qActivity.status,
                        qCoupon.count, qCoupon.remark, qCoupon.status, qActivity.couponCount)
                .from(qActivity)
                .leftJoin(qCoupon).on(qCoupon.id.eq(qActivity.couponId))
                .where(qActivity.status.notIn(BaseConstant.SoftDelete.delete.getTypeValue()).and(qCoupon.status.notIn(BaseConstant.SoftDelete.delete.getTypeValue())))
                .orderBy(sort.getOrderSpecifier())
                .offset(pageNum * pageSize).limit(pageSize);
        QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
        //将元组集合转成map集合
        for (Tuple tuple : queryResults.getResults()) {
            Map<String, Object> map = new HashMap<>();
            map.put("activityId", tuple.get(qActivity.id));
            map.put("activityRemark", tuple.get(qActivity.remark));
            map.put("effectiveType", tuple.get(qActivity.effectiveType));
            map.put("effectiveTime", tuple.get(qActivity.effectiveTime));
            map.put("createTime", tuple.get(qActivity.createTime));
            map.put("couponId", tuple.get(qActivity.couponId));
            map.put("couponRemark", tuple.get(qCoupon.remark));
            map.put("couponStatus", tuple.get(qCoupon.status));
            map.put("couponCount", tuple.get(qActivity.couponCount));
            data.add(map);
        }
        //jqgrid数据拼装
        Map<String, Object> result = new HashMap<>();
        result.put("content", data);//添加主体数据
        result.put("totalElements", queryResults.getTotal());//添加总条数
        result.put("code", 0);
        return result;
    }

    @Override
    public RetKit save(ActivityVo activityVo) {
        Activity activity = new Activity();
        if (activityVo.getActivityId() != 0) {//编辑
            activity = activityRepository.findById(activityVo.getActivityId()).get();
        } else {
            activity.setCreateTime(new Date());
            activity.setStatus(BaseConstant.SoftDelete.nomal.getTypeValue());
        }

        activity.setRemark(activityVo.getActivityRemark());
        activity.setEffectiveType(activityVo.getEffectiveType());
        if (activity.getEffectiveType() == 2) {//时间内有效
            if (StrKit.notBlank(activityVo.getEffectiveTimeString())) {
                SimpleDateFormat spl = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    activity.setEffectiveTime(spl.parse(activityVo.getEffectiveTimeString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    return RetKit.fail("日期格式错误");
                }
            }
        } else {
            activity.setEffectiveTime(null);
        }
        activity.setCouponId(activityVo.getCouponId());
        activity.setCouponCount(activityVo.getCouponCount());
        try {
            activityRepository.save(activity);
        } catch (Exception e) {
            e.printStackTrace();
            return RetKit.fail("添加活动失败");
        }
        return RetKit.ok();
    }

    @Override
    public RetKit del(Integer activityId) {
        if (activityId == null) {
            return RetKit.fail("活动ID为空,删除失败");
        }
        activityRepository.deleteById(activityId);
        return RetKit.ok("删除成功");
    }

    @Override
    public RetKit softlyDel(Integer activityId) {
        if (activityId == null) {
            return RetKit.fail("活动ID为空,删除失败");
        }
        activityRepository.changeStatus(BaseConstant.SoftDelete.delete.getTypeValue(), activityId);
        return RetKit.ok("删除成功");
    }

    @Override
    public Activity findByActivityRemark(String remark) {
        Activity activity = new Activity();
        activity.setRemark(remark);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id", "effectiveType", "effectiveTime", "couponId", "createTime")
                .withMatcher("remark", new ExampleMatcher.GenericPropertyMatcher().contains());

        Example<Activity> example = Example.of(activity, exampleMatcher);
        try {
            Optional<Activity> one = activityRepository.findOne(example);
            if (one == null) {
                return null;
            }
            return one.get();
        } catch (Exception e) {
            return null;
        }
    }

    public RetKit checkActivityName(String activityName) {
        try {
			/*Integer ActivityName = activityRepository.getActivityNameNum(activityName);
			Integer ActivityStatu = activityRepository.findByName(activityName);*/
            Activity findByRemark = activityRepository.findStatusByRemark(activityName);
            if (findByRemark != null) {
                return RetKit.fail("活动名称不能重复");
            }
            return RetKit.ok();
        } catch (Exception e) {
            return RetKit.fail("检查重名失败");
        }
    }


    @Override
    public Object getRecordList(Integer pageSize, Integer pageNum, List<Map<String, Object>> para, String direction, String sortName) {
        List<Map<String, Object>> data = new ArrayList<>();
        QUserCoupon qUserCoupon = QUserCoupon.userCoupon; //优惠卷，主表
        QUser qUser = QUser.user; //优惠卷，主表
        QActivity qActivity = QActivity.activity; //优惠活动
        QCoupon qCoupon = QCoupon.coupon; //优惠卷
        /**
         * 查询条件的谓语集合(where条件)
         */
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(qUserCoupon.userId.eq(qUser.id));
        predicates.add(qUserCoupon.activityId.eq(qActivity.id));
        predicates.add(qUserCoupon.couponId.eq(qCoupon.id));
        predicates.add(qCoupon.id.eq(qActivity.couponId));

        //动态排序,需要使用自定义的Sort类
        Sort sort = null;
        switch (sortName) {
            case "id":
                sort = new Sort("id", direction.toString(), qUserCoupon);
                break;
            case "userId":
                sort = new Sort("userId", direction.toString(), qUserCoupon);
                break;
            case "userName":
                sort = new Sort("carOwnerName", direction.toString(), qUser);
                break;
            case "useTime":
                sort = new Sort("useTime", direction.toString(), qUserCoupon);
                break;
            case "createTime":
                sort = new Sort("createTime", direction.toString(), qUserCoupon);
                break;
            case "couponCount":
                sort = new Sort("count", direction.toString(), qCoupon);
                break;
            case "activityRemark":
                sort = new Sort("remark", direction.toString(), qActivity);
                break;
        }

        if (!para.isEmpty()) {
            for (Map<String, Object> map : para) {
                if (map.get("userName") != null) {
                    predicates.add(qUser.carOwnerName.like("%" + map.get("userName").toString() + "%"));
                }
                if (map.get("startTime") != null) {
                    Date startTime = DateUtil.dateToStamp(map.get("startTime").toString(), "yyyy-MM-dd");
                    predicates.add(qActivity.createTime.goe(startTime));
                }
                if (map.get("endTime") != null) {
                    Date endTime = DateUtil.dateToStamp(map.get("endTime").toString(), "yyyy-MM-dd");
                    predicates.add(qActivity.createTime.loe(endTime));
                }
            }
        }

        //查询结果
        JPAQuery<Tuple> jPAQuery = jpaQueryFactory
                .select(
                        qUserCoupon.id,
                        qUserCoupon.userId,
                        qUser.carOwnerName,
                        qActivity.remark,//活动名称
                        qUserCoupon.useTime,
                        qUserCoupon.createTime,
                        qCoupon.count,//面值
                        qCoupon.remark)
                .from(qUserCoupon, qUser, qActivity, qCoupon)
                .where(predicates.toArray(new Predicate[predicates.size()]))
                .orderBy(sort.getOrderSpecifier())
                .offset(pageNum * pageSize).limit(pageSize);
        QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
        //将元组集合转成map集合
        for (Tuple tuple : queryResults.getResults()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", tuple.get(qUserCoupon.id));
            map.put("userId", tuple.get(qUserCoupon.userId));
            map.put("userName", tuple.get(qUser.carOwnerName));
            map.put("useTime", tuple.get(qUserCoupon.useTime));
            map.put("createTime", tuple.get(qUserCoupon.createTime));
            map.put("couponCount", tuple.get(qCoupon.count));
            map.put("activityRemark", tuple.get(qActivity.remark));
            data.add(map);
        }
        //jqgrid数据拼装
        Map<String, Object> result = new HashMap<>();
        result.put("content", data);//添加主体数据
        result.put("totalElements", queryResults.getTotal());//添加总条数
        result.put("code", 0);
        return result;
    }
}
