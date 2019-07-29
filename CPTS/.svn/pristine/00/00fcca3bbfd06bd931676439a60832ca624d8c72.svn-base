package com.dchip.cloudparking.api.iRepository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer>{
	
	/**
	 * 根据停车信息id查找订单
	 * sjh
	 * @return
	 */
	@Query(value = "select o.* from `order` o where o.parking_info_id = :parkingInfoId limit 1", nativeQuery = true)
	Order findOrder(@Param("parkingInfoId") Integer parkingInfoId);
	
	/**
	 * 根据用户ID查找黑名单用户的订单数据和停车场信息
	 * zyy
	 * @param userId
	 * @return
	 */
	@Query(value=" SELECT o.fee,o.`status`,pi.park_code, pi.license_plate, " + 
				 " pi.park_date, pi.out_date FROM `user` u " + 
				 " LEFT JOIN `order` o ON (o.user_id = u.id) " + 
				 " LEFT JOIN parking_info pi ON (pi.id = o.parking_info_id) " + 
				 " WHERE u.id = :userId AND u.is_black = 1 AND o.`status` <>1",nativeQuery=true)
	List<Map<String,Object>> findBlackUserInfoByUserId(@Param("userId") Integer userId);
	

	
	/**
	 * 按月查询总消费接口
	 */
	@Query(value = "SELECT  " + 
			"	(  " + 
			"		SELECT  " + 
			"			IFNULL(sum(o.final_fee) ,0)   " + 
			"		FROM  " + 
			"			`order` AS o  " + 
			"		LEFT JOIN `user` u ON (u.id = :userId AND o.user_id = u.id)  " + 
			"		WHERE  " + 
			"			o.user_id = :userId  " + 
			"		AND o. STATUS = 1  " + 
			"		AND o.fee IS NOT NULL  " + 
			"		AND o.type IS NOT NULL AND date_format(o.pay_time, '%Y-%m') = date_format(:payTime, '%Y-%m') " + 
			"	) + (  " + 
			"		SELECT  " + 
			"			IFNULL(SUM(mcp.payment_money),0) " + 
			"		FROM  " + 
			"			`user` u2  " + 
			"		LEFT JOIN monthly_card mc ON (  " + 
			"			mc.license_plate = u2.license_plat  " + 
			"		)  " + 
			"		LEFT JOIN monthly_card_pay mcp ON (  " + 
			"			mcp.monthly_card_id = mc.id  " + 
			"			AND mcp.`status` = 1  " + 
			"		)  " + 
			"		WHERE  " + 
			"			u2.id = :userId  AND date_format(mcp.payment_time, '%Y-%m') = date_format(:payTime, '%Y-%m') " + 
			"	) AS sumFee ", nativeQuery = true)
	Object findMonthFee(@Param("userId") Integer userId, @Param("payTime") String payTime);
	
	/**
	 * 按月查询消费明细接口
	 * by 小梁
	 */
	@Query(value=" SELECT o.id AS orderId, o.fee, o.type, o.pay_time, o.parking_time, o.`status`," + 
			" pi.license_plate, pi.park_date, pi.out_date," + 
			" p.park_name, p.province_name, p.city_name, p.area_name" + 
			" FROM `order` o" + 
			" LEFT JOIN parking_info pi ON pi.id = o.parking_info_id" + 
			" LEFT JOIN parking p ON p.park_code = pi.park_code" + 
			" WHERE o.user_id = :userId AND o.`status` = 1" + 
			" AND date_format(o.pay_time, '%Y-%m') = date_format(:payTime, '%Y-%m') ORDER BY o.pay_time DESC", nativeQuery=true)
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

	/**
	 * 根据 userId 查出该用户的缴费总额
	 */
	@Query(value = "SELECT  " + 
			"	(  " + 
			"		SELECT  " + 
			"			IFNULL(sum(o.fee) ,0)   " + 
			"		FROM  " + 
			"			`order` AS o  " + 
			"		LEFT JOIN `user` u ON (u.id = :userId AND o.user_id = u.id)  " + 
			"		WHERE  " + 
			"			o.user_id = :userId  " + 
			"		AND o. STATUS = 1  " + 
			"		AND o.fee IS NOT NULL  " + 
			"		AND o.type IS NOT NULL  " + 
			"	) + (  " + 
			"		SELECT  " + 
			"			IFNULL(SUM(mcp.payment_money),0) " + 
			"		FROM  " + 
			"			`user` u2  " + 
			"		LEFT JOIN monthly_card mc ON (  " + 
			"			mc.license_plate = u2.license_plat  " + 
			"		)  " + 
			"		LEFT JOIN monthly_card_pay mcp ON (  " + 
			"			mcp.monthly_card_id = mc.id  " + 
			"			AND mcp.`status` = 1  " + 
			"		)  " + 
			"		WHERE  " + 
			"			u2.id = :userId  " + 
			"	) AS sumFee ", nativeQuery = true)
	Map<String, Object> sumFee(@Param("userId") Integer userId);
	
	@Query(value="SELECT  " + 
			"	o.id AS orderId,  " + 
			"	o.appointment_id AS appointmentId,  " + 
			"	o.parking_info_id AS parkingInfoId,  " + 
			"	o.fee,  " + 
			"	o.type,  " + 
			"	o.parking_time AS parkingTime,  " + 
			"  " + 
			"IF (  " + 
			"	o.`status` = 0 ,- 1,  " + 
			"  " + 
			"IF (o. STATUS = 1, 1, 0)  " + 
			") AS STATUS,  " + 
			" o.pay_time AS payTime,  " + 
			" fs.hour_cost,  " + 
			" u.license_plat AS license_plate,  " + 
			" pi.park_date,  " + 
			" pi.out_date,  " + 
			" p.park_name,  " + 
			" p.province_name,  " + 
			" p.city_name,  " + 
			" p.area_name,  " + 
			" a.create_time,  " + 
			" a.appoint_start_time  " + 
			"FROM  " + 
			"	`order` o  " + 
			"LEFT JOIN `user` u ON (o.user_id = u.id AND u.state = 0)  " + 
			"LEFT JOIN parking_info pi ON (o.parking_info_id = pi.id)  " + 
			"LEFT JOIN appointment a ON (o.appointment_id = a.id)  " + 
			"LEFT JOIN parking p ON (  " + 
			"	pi.park_code = p.park_code  " + 
			"	OR a.parking_id = p.id  " + 
			")  " + 
			"LEFT JOIN free_standard fs ON (fs.id = p.free_standard_id)  " + 
			"WHERE  " + 
			"	u.id = :userId  " + 
			"	AND ISNULL(o.parking_concession_id)	 " +
			"ORDER BY  " + 
			"	pay_time DESC " + 
			"LIMIT :pageSize OFFSET :offset",nativeQuery=true)
	List<Map<String, Object>> findOrders(@Param("userId") Object userId,@Param("pageSize")Object pageSize,@Param("offset")Object offset);
	
	
	/**
	 * 查询消费记录
	 * @return
	 */
	
	@Query(value="SELECT * FROM (SELECT o.id,o.final_fee,o.type,o.pay_time,'a' AS mark FROM `order` o WHERE o.user_id =:userId AND ! ISNULL(o.final_fee) " + 
			"			UNION ALL SELECT mcp.id,mcp.payment_money,mcp.payment_way,mcp.payment_time,'b' AS b FROM monthly_card_pay mcp JOIN monthly_card mc ON mc.id = mcp.monthly_card_id WHERE mc.license_plate =:licensePlate AND ! ISNULL(mcp.payment_money) " + 
			"			 ) o  " + 
			"			 ORDER BY o.pay_time DESC limit :pageSize OFFSET :offset",nativeQuery = true)
	List<Map<String, Object>> findAllCousumption(@Param("userId")Integer userId,@Param("licensePlate")String licensePlate,@Param("pageSize")Integer pageSize,@Param("offset")Integer offset);
	
	
	/**
	 * 查询消费记录
	 * @return
	 */
	@Query(value="SELECT  " + 
			"	*  " + 
			"FROM  " + 
			"	(  " + 
			"		SELECT o.id,o.final_fee,o.type,o.pay_time,'a' AS mark  " + 
			"		FROM `order` o  " + 
			"		WHERE o.user_id =:userId  " + 
			"		AND ! ISNULL(o.final_fee)  " + 
			"		AND ISNULL(o.parking_concession_id)	 " +
			"		AND o.`status` = 1  " + 
			"		AND date_format(o.pay_time, '%Y-%m') = date_format(:payTime, '%Y-%m')  " + 
			"  " + 
			"		UNION ALL  " + 
			"			SELECT mcp.id,mcp.payment_money,mcp.payment_way,mcp.payment_time,'b' AS b  " + 
			"			FROM monthly_card_pay mcp  " + 
			"			JOIN monthly_card mc ON mc.id = mcp.monthly_card_id  " + 
			"			WHERE mc.license_plate =:licensePlate  " + 
			"			AND ! ISNULL(mcp.payment_money)  " + 
			"			AND date_format(mcp.payment_time, '%Y-%m') = date_format(:payTime, '%Y-%m')  " + 
			"		UNION ALL  " + 
			"			SELECT o.id,o.final_fee,o.type,o.pay_time,'c' AS c  " + 
			"			FROM `order` o  " + 
			"			WHERE o.user_id =:userId  " + 
			"			AND ! ISNULL(o.final_fee)  " + 
			"			AND ! ISNULL(o.parking_concession_id)  " + 
			"			AND o.`status` = 1  " + 
			"			AND date_format(o.pay_time, '%Y-%m') = date_format(:payTime, '%Y-%m')  " + 
			"	) o  " + 
			"ORDER BY  " + 
			"	o.pay_time DESC",nativeQuery = true)
	List<Map<String, Object>> findAllCousumption(@Param("userId")Integer userId,@Param("licensePlate")String licensePlate, @Param("payTime") String payTime);
	
	
	/**
	 * 
	 * 查询消费记录总数
	 * @return
	 */
	@Query(value="SELECT count(*) FROM (SELECT o.id,o.final_fee,o.type,o.pay_time,'a' AS mark FROM `order` o WHERE o.user_id =:userId AND ! ISNULL(o.final_fee) AND o.`status`=1" + 
			"			UNION ALL SELECT mcp.id,mcp.payment_money,mcp.payment_way,mcp.payment_time,'b' AS b FROM monthly_card_pay mcp JOIN monthly_card mc ON mc.id = mcp.monthly_card_id WHERE mc.license_plate =:licensePlate AND ! ISNULL(mcp.payment_money) " + 
			"			 ) o  " + 
			"			 ORDER BY o.pay_time DESC",nativeQuery = true)
	Integer findAllCousumptionCount(@Param("userId")Integer userId,@Param("licensePlate")String licensePlate);
	
	@Query(value="SELECT " + 
			"	count(*) " + 
			" FROM " +  
			"	`order` o " + 
			" LEFT JOIN `user` u ON (o.user_id = u.id AND u.state = 0) " + 
			" LEFT JOIN parking_info pi ON (o.parking_info_id = pi.id) " + 
			" LEFT JOIN parking p ON (pi.park_code = p.park_code) " + 
			" WHERE u.id =:userId AND ISNULL(o.parking_concession_id)" ,nativeQuery=true)
	Integer findOrdersCount(@Param("userId") Object userId);
	
	@Query(value="SELECT " + 
			"	o.fee, " + 
			"	IF(o.type=1,'支付宝',IF(o.type=2,'微信','余额')) as type, " + 
			"   IF(o.`status`=0,'未支付',IF(o.status=1,'已支付','支付失败')) as status, " + 
			"	o.discount_amount AS discountFee, " + 
			"	o.coupon_id as couponId, " + 
			"	p.park_name, " + 
			"	pi.park_date AS parkDate, " + 
			"	pi.license_plate AS license, " + 
			"	pi.out_date AS outDate, " + 
			"	fs.free_time_length AS freeTime, " + 
			"	fs.hour_cost AS hourCost, " + 
			"	fs.most_cost AS mostCost " + 
			"   FROM " + 
			"	`order` o" + 
			"   LEFT JOIN `user` u ON (o.user_id = u.id AND u.state = 0) " + 
			"   LEFT JOIN parking_info pi ON (o.parking_info_id = pi.id) " + 
			"   LEFT JOIN parking p ON (pi.park_code = p.park_code) " + 
			"   LEFT JOIN free_standard fs ON (fs.parking_id = p.id) " + 
			"   WHERE " + 
			"	o.id = :orderId ",nativeQuery=true)
	List<Map<String, Object>> findOrderDetail(@Param("orderId") String orderId);
	
	
	/**
	 * 根据停车id获取停车订单信息
	 */
	@Query(value = "SELECT o.* from `order` o join parking_info pi on pi.id = o.parking_info_id where o.parking_info_id = :parkingInfoId", nativeQuery = true)
	Order findOrderByParkingInfoId(@Param("parkingInfoId") Integer parkingInfoId);
	
	/**
	 * 获取预约的订单
	 */
	@Query(value="SELECT o.* FROM `order` o LEFT JOIN parking_info pi ON (o.parking_info_id = pi.id)" + 
			" WHERE pi.appointment_id = :appointmentId " + 
			" AND pi.`status` = 1",nativeQuery=true)
	Order findAppointmentOrder(@Param("appointmentId") Integer appointmentId);

	@Query(value="SELECT COUNT(*) FROM `order` WHERE user_id =:userId AND `status` <> 1",nativeQuery=true)
	Integer findUnpayCountByUserId(@Param("userId") Integer userId);

	@Query(value="SELECT o.* FROM `order` o WHERE o.parking_info_id =:parkingInfoId ORDER BY o.id DESC LIMIT 1",nativeQuery=true)
	Order findOrderByInfoAndType(@Param("parkingInfoId")Integer parkingInfoId);
	
}
