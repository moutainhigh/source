package com.dchip.cloudparking.web.iRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer>{
	/**
	 * 根据停车信息id查找订单
	 * sjh
	 * @return
	 */
	@Query(value = "select o.* from `order` o where o.parking_info_id = :parkingInfoId limit 1", nativeQuery = true)
	Order findOrder(@Param("parkingInfoId") Integer parkingInfoId);
	
	@Query(value=" SELECT o.fee,pi.park_code, pi.license_plate, " + 
				 " pi.park_date, pi.out_date FROM `order` o " + 
				 " LEFT JOIN `user` u ON (u.id = o.user_id AND u.state = 0) " + 
				 " LEFT JOIN parking_info pi ON (pi.id = o.parking_info_id) " + 
				 " WHERE u.id = :userId AND o.`status` = 0",
				 nativeQuery=true)
	List<Map<String,Object>> findBlackUserInfoByUserId(@Param("userId") Integer userId);
	
	/**
	 * 按月查询总消费接口
	 * by 小梁
	 */
	@Query(value=" SELECT sum(fee) as monthFee" + 
			" FROM `order`" + 
			" WHERE user_id = :userId AND `status` = 1" + 
			" AND date_format(pay_time, '%Y-%m') = date_format(:payTime, '%Y-%m')", nativeQuery=true)
	BigDecimal findMonthFee(@Param("userId") Integer userId, @Param("payTime") String payTime);
	
	/**
	 * 按月查询消费明细接口
	 * by 小梁
	 */
	@Query(value=" SELECT o.fee, o.type, o.pay_time," + 
			" pi.license_plate, pi.park_date, pi.out_date," + 
			" p.park_name, p.province_name, p.city_name, p.area_name" + 
			" FROM `order` o" + 
			" LEFT JOIN parking_info pi ON pi.id = o.parking_info_id" + 
			" LEFT JOIN parking p ON p.park_code = pi.park_code" + 
			" WHERE o.user_id = :userId AND o.`status` = 1" + 
			" AND date_format(o.pay_time, '%Y-%m') = date_format(:payTime, '%Y-%m')", nativeQuery=true)
	List<Map<String, Object>> findOrderByUserIdAndPayTime(@Param("userId") Integer userId, @Param("payTime") String payTime);
	
	/**
	 * 按月维度查询当前年份消费柱状图数据接口
	 * by 小梁
	 */
	@Query(value=" select month(pay_time) month, count(1) count, sum(fee) monthFee" + 
			" from `order`" + 
			" where year(pay_time) = :year and user_id = :userId and `status` = 1" + 
			" group by month(pay_time)", nativeQuery=true)
	List<Map<String, Object>> findFeeByUserIdAndYear(@Param("userId") Integer userId, @Param("year") String year);
	
	@Query(value="SELECT" + 
			"	o.id AS orderId, " + 
			"	o.fee, " + 
			"	IF(o.type=1,'支付宝',IF(o.type=2,'微信','余额')) as type, " + 
			"IF(o.`status`=0,'未支付',IF(o.type=1,'已支付','支付失败')) as status, " + 
			"	o.pay_time AS payTime, " + 
			"	p.park_name " + 
			"FROM " + 
			"	`order` o " + 
			"LEFT JOIN `user` u ON (o.user_id = u.id AND u.state = 0) " + 
			"LEFT JOIN parking_info pi ON (o.parking_info_id = pi.id) " + 
			"LEFT JOIN parking p ON (pi.park_code = p.park_code) " + 
			"WHERE " + 
			"	u.phone = :phone " + 
			"ORDER BY " + 
			"	pay_time DESC ",
			 nativeQuery=true)
	List<Map<String, Object>> findOrders(@Param("phone") String phone);
	
	@Query(value="SELECT" + 
			"	o.fee," + 
			"	IF(o.type=1,'支付宝',IF(o.type=2,'微信','余额')) as type, " + 
			"   IF(o.`status`=0,'未支付',IF(o.type=1,'已支付','支付失败')) as status, " + 
			"	o.discount_amount AS discountFee, " + 
			"	o.coupon_id as couponId, " + 
			"	p.park_name, " + 
			"	pi.park_date AS parkDate, " + 
			"	pi.license_plate AS license, " + 
			"	pi.out_date AS outDate, " + 
			"	fs.free_time_length AS freeTime, " + 
			"	fs.hour_cost AS hourCost, " + 
			"	fs.most_cost AS mostCost " + 
			"FROM " + 
			"	`order` o " + 
			"LEFT JOIN `user` u ON (o.user_id = u.id AND u.state = 0) " + 
			"LEFT JOIN parking_info pi ON (o.parking_info_id = pi.id) " + 
			"LEFT JOIN parking p ON (pi.park_code = p.park_code) " + 
			"LEFT JOIN free_standard fs ON (fs.parking_id = p.id) " + 
			"WHERE " + 
			"	o.id = :orderId ",
			 nativeQuery=true)
	List<Map<String, Object>> findOrderDetail(@Param("orderId") String orderId);
	
	@Query(value=" SELECT o.* " + 
			" FROM `order` o " + 
			" LEFT JOIN parking_info pi ON (pi.id = o.parking_info_id) " + 
			" LEFT JOIN parking p ON (p.park_code = pi.park_code) " + 
			" WHERE o.`status` = 1 AND o.is_transfer = 0 AND p.id = :parkingId ", nativeQuery=true)
	List<Order> orderLiquidation(@Param("parkingId") Integer parkingId);
	
	@Query(value="SELECT o.* FROM `order` o " + 
			"LEFT JOIN parking_info pi ON (pi.id=o.parking_info_id) " + 
			"LEFT JOIN parking p ON (p.park_code = pi.park_code) " + 
			"LEFT JOIN company c ON (c.id = p.company_id) " + 
			"WHERE (o.pay_time BETWEEN :startTime AND :endTime) AND (o.is_transfer <> 1 or o.is_transfer is null ) " + 
			"AND c.id = :companyId  AND o.`status` = 1 ",nativeQuery=true)
	List<Order> temporaryOrderData(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("companyId") Integer companyId);
	
	@Query(value="SELECT  " + 
			"	IFNULL(SUM(final_fee),0) as fee,COUNT(*) as count,group_concat(o.id) as orderIds  " + 
			"FROM  " + 
			"	`order` o  " + 
			"LEFT JOIN appointment a ON (a.id = o.appointment_id)  " + 
			"LEFT JOIN parking_info pi ON (pi.id = o.parking_info_id)  " + 
			"LEFT JOIN parking p ON (  " + 
			"	pi.park_code = p.park_code  " + 
			"	OR a.parking_id = p.id  " + 
			")  " + 
			"WHERE  " + 
			"	p.company_id = :companyId  " + 
			"AND locate(  " + 
			"	o.id,  " + 
			"	(  " + 
			"		SELECT  " + 
			"			IFNULL(group_concat(c.order_ids),0) " + 
			"		FROM  " + 
			"			cash_apply_record c  " + 
			"		WHERE  " + 
			"			c.company_id = :companyId  " + 
			"	)  " + 
			") = 0",nativeQuery=true)
	Map<String, Object> getData(@Param("companyId")Integer companyId);
	
	@Query(value="SELECT sum(o.final_fee) as count ,date_format(o.pay_time,'%Y/%c/%e') as date  " + 
			"FROM `order` o  " + 
			"			LEFT JOIN parking_info pi ON (pi.id=o.parking_info_id)   " + 
			"			LEFT JOIN parking p ON (p.park_code=pi.park_code)    " + 
			"			WHERE p.company_id =:companyId AND o.`status` = 1 GROUP BY date ORDER BY o.pay_time ASC", nativeQuery=true)
	List<Map<String, Object>> getOrderData(@Param("companyId")String companyId);

	@Query(value="SELECT sum(mp.payment_money) as count ,date_format(mp.payment_time,'%Y/%c/%e') as date  " + 
			"FROM monthly_card_pay mp " + 
			"			LEFT JOIN card c ON (c.id=mp.monthly_card_id)   " + 
			"			LEFT JOIN parking p ON (p.id=c.parking_id)    " + 
			"			WHERE p.company_id =:companyId AND mp.`status` = 1 GROUP BY date ORDER BY mp.payment_time ASC",nativeQuery=true)
	List<Map<String, Object>> getCardData(@Param("companyId")String companyId);
	
	
	@Query(value="select o.* from `order` o where o.parking_info_id = :parkingInfoId and o.status = 1", nativeQuery=true)
	Order findOrderByInfoId(@Param("parkingInfoId") Integer parkingInfoId);

	/**
	 * 
	 * @param parkingInfoId
	 * @return
	 */
	@Query(value="select o.* from `order` o where o.parking_info_id = :parkingInfoId and o.status = 4", nativeQuery=true)
	Order findOrderByParkingInfoId(@Param("parkingInfoId") Integer parkingInfoId);

}
