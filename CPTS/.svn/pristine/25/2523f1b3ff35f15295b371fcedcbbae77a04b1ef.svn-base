package com.dchip.cloudparking.web.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.Coupon;

@Repository
public interface ICouponRepository extends JpaRepository<Coupon, Integer>{

    @Query(value = "update coupon set status = :softlyDelStatus where id = :couponId",nativeQuery = true)
    @Modifying
    Integer softlyDel(@Param("softlyDelStatus")Integer softlyDelStatus,@Param("couponId") Integer couponId);
}
