package com.dchip.cloudparking.web.serviceImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.ICouponRepository;
import com.dchip.cloudparking.web.iRepository.IMemberRuleRepository;
import com.dchip.cloudparking.web.iService.ICouponManageService;
import com.dchip.cloudparking.web.model.po.Coupon;
import com.dchip.cloudparking.web.model.po.MemberRule;
import com.dchip.cloudparking.web.po.qpo.QCoupon;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class CouponManageServiceImp extends BaseService implements ICouponManageService {

    @Autowired
    private ICouponRepository couponRepository;

    @Autowired
    private IMemberRuleRepository memberRuleRepository;

    @Override
    public Object getCouponList(Integer pageSize, Integer pageNum , String sortName, String direction) {
        List<Map<String, Object>> data = new ArrayList<>();
        QCoupon qCoupon = QCoupon.coupon; //优惠卷
        /**
         * 查询条件的谓语集合(where条件)
         */
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(qCoupon.status.notIn(BaseConstant.SoftDelete.delete.getTypeValue()));//不显示软删除的数据

        List<MemberRule> all = memberRuleRepository.findAll();
        Map<String,String> gradeNameMap = new HashMap<>();
        if(all != null){
            for(MemberRule rule : all){
                gradeNameMap.put(rule.getId()+"",rule.getGradeDescription());
            }
        }
        
		//动态排序,需要使用自定义的Sort类
		Sort sort = null;
		switch (sortName) {
		case "couponId":		
			sort = new Sort("couponId",direction.toString(),qCoupon);
			break;
		case "count":		
			sort = new Sort("count",direction.toString(),qCoupon);
			break;
		default:
			sort = new Sort(sortName,direction,qCoupon);
			break;
		}

        //查询结果
        JPAQuery<Tuple> jPAQuery = jpaQueryFactory
                .select(qCoupon.id,
                        qCoupon.count,
                        qCoupon.endNum,
                        qCoupon.deductionType,
                        qCoupon.partDeduction,
                        qCoupon.endType,
                        qCoupon.endTime,
                        qCoupon.createTime,
                        qCoupon.remark,
                        qCoupon.memberIds,
                        qCoupon.status)
                .from(qCoupon)
                .where(predicates.toArray(new Predicate[predicates.size()]))
                .orderBy(sort.getOrderSpecifier())
                .offset(pageNum * pageSize)
                .limit(pageSize);
        QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
        //将元组集合转成map集合
        for (Tuple tuple : queryResults.getResults()) {
            Map<String, Object> map = new HashMap<>();
            map.put("couponId", tuple.get(qCoupon.id));
            map.put("count", tuple.get(qCoupon.count));
            map.put("endNum", tuple.get(qCoupon.endNum));
            map.put("deductionType", tuple.get(qCoupon.deductionType));
            map.put("partDeduction", tuple.get(qCoupon.partDeduction));
            map.put("endType", tuple.get(qCoupon.endType));
            map.put("endTime", tuple.get(qCoupon.endTime));
            map.put("createTime", tuple.get(qCoupon.createTime));
            map.put("couponRemark", tuple.get(qCoupon.remark));
            map.put("memberIds", tuple.get(qCoupon.memberIds));

            StringBuilder memberIdNames = new StringBuilder();
            String memberIdsString =  tuple.get(qCoupon.memberIds);
            if(memberIdsString != null && !memberIdsString.trim().equals("")){
                String[] memberIds = memberIdsString.split(",");
                for(String id : memberIds){
                    memberIdNames.append(gradeNameMap.get(id)+" ");
                }
            }
            map.put("memberIdNames",memberIdNames.toString().trim());

            map.put("status", tuple.get(qCoupon.status));
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
    public Object getActivityCouponList(Integer pageSize, Integer pageNum) {
        List<Map<String, Object>> data = new ArrayList<>();
        QCoupon qCoupon = QCoupon.coupon; //优惠卷
        /**
         * 查询条件的谓语集合(where条件)
         */
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(qCoupon.status.notIn(BaseConstant.SoftDelete.delete.getTypeValue()));//不现实软删除的数据

        List<MemberRule> all = memberRuleRepository.findAll();
        Map<String,String> gradeNameMap = new HashMap<>();
        if(all != null){
            for(MemberRule rule : all){
                gradeNameMap.put(rule.getId()+"",rule.getGradeDescription());
            }
        }

        //查询结果
        JPAQuery<Tuple> jPAQuery = jpaQueryFactory
                .select(qCoupon.id,
                        qCoupon.count,
                        qCoupon.endNum,
                        qCoupon.deductionType,
                        qCoupon.partDeduction,
                        qCoupon.endType,
                        qCoupon.endTime,
                        qCoupon.createTime,
                        qCoupon.remark,
                        qCoupon.memberIds,
                        qCoupon.status)
                .from(qCoupon)
                .where(predicates.toArray(new Predicate[predicates.size()]))
                .offset(pageNum * pageSize)
                .limit(pageSize);
        QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
        //将元组集合转成map集合
        for (Tuple tuple : queryResults.getResults()) {
            Map<String, Object> map = new HashMap<>();
            map.put("couponId", tuple.get(qCoupon.id));
            map.put("count", tuple.get(qCoupon.count));
            map.put("endNum", tuple.get(qCoupon.endNum));
            map.put("deductionType", tuple.get(qCoupon.deductionType));
            map.put("partDeduction", tuple.get(qCoupon.partDeduction));
            map.put("endType", tuple.get(qCoupon.endType));
            map.put("endTime", tuple.get(qCoupon.endTime));
            map.put("createTime", tuple.get(qCoupon.createTime));
            map.put("couponRemark", tuple.get(qCoupon.remark));
            map.put("memberIds", tuple.get(qCoupon.memberIds));

            StringBuilder memberIdNames = new StringBuilder();
            String memberIdsString =  tuple.get(qCoupon.memberIds);
            if(memberIdsString != null && !memberIdsString.trim().equals("")){
                String[] memberIds = memberIdsString.split(",");
                for(String id : memberIds){
                    memberIdNames.append(gradeNameMap.get(id)+" ");
                }
            }
            map.put("memberIdNames",memberIdNames.toString().trim());

            map.put("status", tuple.get(qCoupon.status));
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
    public RetKit save(Coupon vo) {
        couponRepository.save(vo);
        return RetKit.ok();
    }

    @Override
    public RetKit del(Integer couponId) {
        if (couponId == null) {
            return RetKit.fail("优惠券ID为空,删除失败");
        }
        couponRepository.deleteById(couponId);
        return RetKit.ok("删除成功");
    }

    @Override
    public RetKit softlyDel(Integer couponId) {
        try{
            couponRepository.softlyDel(BaseConstant.SoftDelete.delete.getTypeValue(),couponId);
            return RetKit.ok("删除成功");
        }catch (Exception e){
            return RetKit.ok("删除失败");
        }
    }

    /**
     * 优惠券禁用/解禁
     * @param couponId
     * @param couponStatus
     * @return
     */
    @Override
    public RetKit changeCouponStatus(Integer couponId, Integer couponStatus) {
        try {
            Coupon coupon = couponRepository.findById(couponId).get();
            if (BaseConstant.CouponStatus.EnabledStatus.getTypeValue() == (couponStatus)) {//当前端传过来的一项数据状态为 正常，则设置为禁用
                coupon.setStatus(BaseConstant.CouponStatus.DisableStatus.getTypeValue());
            } else if (BaseConstant.CouponStatus.DisableStatus.getTypeValue() == (couponStatus)) {//当前端传过来的一项数据状态为 禁用，则设置为正常
                coupon.setStatus(BaseConstant.CouponStatus.EnabledStatus.getTypeValue());
            } else {
                return RetKit.fail();
            }
            couponRepository.save(coupon);
            return RetKit.ok();
        } catch (Exception e) {
            return RetKit.fail();
        }
    }


}
