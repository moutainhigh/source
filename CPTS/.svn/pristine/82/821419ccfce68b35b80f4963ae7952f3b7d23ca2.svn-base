package com.dchip.cloudparking.api.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.Activity;

import java.util.List;
import java.util.Map;

@Repository
public interface IActivityRepository extends JpaRepository<Activity, Integer> {

    @Query(value = "SELECT  " + 
    		"	COUNT(a.id)  " + 
    		"FROM  " + 
    		"	activity a  " + 
    		"LEFT JOIN coupon c ON c.id = a.coupon_id  " + 
    		"WHERE  " + 
    		"	a.`status` = 0  " + 
    		"AND a.id NOT IN (  " + 
    		"	SELECT  " + 
    		"		uc.activity_id  " + 
    		"	FROM  " + 
    		"		user_coupon uc  " + 
    		"	WHERE  " + 
    		"		uc.user_id =:userId  " + 
    		")  " + 
    		"AND c.`status` = 0  " + 
    		"AND locate((select u.member_id from `user` u where u.id=:userId),c.member_ids) >0 "//用户在会员等级内
    		+ "AND (a.effective_time > NOW() OR a.effective_time IS NULL) "
    		+ "AND (c.end_time >NOW() OR c.end_time IS NULL)", 
                    nativeQuery = true)
    long getActivityCouponsCount(@Param("userId") long userId);

    @Query(value = "select \n" +
            "A.*,B.count AS couponCount, \n" +
            "B.remark as couponRemark,B.end_time AS couponEndTime \n" +
            "from activity A\n" +
            "left join coupon B on B.id = A.coupon_id \n" +
            "where A.status = 0 \n" +
            "and A.id not in (select activity_id from user_coupon where user_id=:userId)\n" + //排除已参与的活动
            "and B.status = 0\n" +
            "AND locate((select u.member_id from `user` u where u.id=:userId),B.member_ids) >0 \n"//用户在会员等级内
            + "AND (A.effective_time > NOW() OR A.effective_time IS NULL) "
            + "AND (B.end_time >NOW() OR B.end_time IS NULL) "+ 
            "limit :fromRow,:toRow", nativeQuery = true)
    List<Map<String, Object>> getActivityCoupons(@Param("userId") long userId, @Param("fromRow") Integer fromRow, @Param("toRow") Integer toRow);

}
