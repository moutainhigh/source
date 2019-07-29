package com.dchip.cloudparking.api.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.RechargeCoupon;

@Repository
public interface IRechargeCouponRepository extends JpaRepository<RechargeCoupon, Integer> {
	
	/**
	 * 获取充值赠送的优惠券
	 */
	@Query(value ="SELECT * from recharge_coupon where id = :Id",nativeQuery = true)
	RechargeCoupon findRechargeCoupon(@Param("Id") Integer Id);
	
}
