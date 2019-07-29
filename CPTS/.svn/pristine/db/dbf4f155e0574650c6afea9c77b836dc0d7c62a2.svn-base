package com.dchip.cloudparking.api.iRepository;

import com.dchip.cloudparking.api.model.po.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserCouponRepository extends JpaRepository<UserCoupon,Integer> {

    @Query(value = "select * from user_coupon where user_id=:userId and activity_id=:activityId limit 1",nativeQuery = true)
    UserCoupon findActCoupon(@Param("userId") Integer userId,@Param("activityId") Integer activityId);
    
    @Query(value = "select * from user_coupon where user_id=:userId and coupon_id=:couponId limit 1",nativeQuery = true)
	UserCoupon findUserCouponByUserIdAndCounponId(@Param("userId") Integer userId,@Param("couponId") Integer couponId);
    
    @Query(value = "select * from user_coupon where user_id=:userId and coupon_id=:couponId and status=0 limit 1",nativeQuery = true)
	UserCoupon findUserCouponByUserIdAndCounponIdAndStatus(@Param("userId") Integer userId,@Param("couponId") Integer couponId);

}
