package com.dchip.cloudparking.wechat.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.wechat.model.po.Order;

@Repository
public interface IOrderRepository extends  JpaRepository<Order, Integer>  {
	
	/**
	 * 根据微信支付订单编号查找order
	 * @param outTradeNo
	 * @return
	 */
	@Query(value="select o.* from `order` o where o.out_trade_no = :outTradeNo", nativeQuery=true)
	Order findOrderByOutTradeNo(@Param("outTradeNo") String outTradeNo);
	
	/**
	 * 
	 * @param parkingInfoId
	 * @return
	 */
	@Query(value="select o.* from `order` o where o.parking_info_id = :parkingInfoId and o.status = 4", nativeQuery=true)
	Order findOrderByParkingInfoId(@Param("parkingInfoId") Integer parkingInfoId);
	
	@Query(value="select o.* from `order` o where o.parking_info_id = :parkingInfoId and o.status = 1", nativeQuery=true)
	Order findOrderByInfoId(@Param("parkingInfoId") Integer parkingInfoId);

	@Query(value="select o.* from `order` o where o.parking_info_id = :parkingInfoId and o.type = 1 and o.status = 3", nativeQuery=true)
	Order findOrderByinfoIsAdvanceUnPay(@Param("parkingInfoId") Integer parkingInfoId);
}
