package com.dchip.cloudparking.api.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.Coupon;

@Repository
public interface ICouponRepository extends JpaRepository<Coupon, Integer>{
	
	@Query(value="SELECT * from coupon where id = :couponId",nativeQuery = true)
	Coupon findCouponById(@Param("couponId") Integer couponId);
}
