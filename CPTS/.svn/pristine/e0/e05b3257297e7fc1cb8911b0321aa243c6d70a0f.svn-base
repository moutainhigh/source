package com.dchip.cloudparking.api.iRepository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dchip.cloudparking.api.model.po.Appointment;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Integer> {

	/**
	 * 获取用户的预约数（没有更新最新状态）
	 * 0-正常预约时间内
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT count(*) FROM appointment a WHERE user_id=:userId AND a.status = 0",nativeQuery=true)
	Integer appointmentCount(@Param("userId") String userId);
	

	/**
	 * 获取上一次预约的信息，如果没有则返回null
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT * FROM appointment a WHERE user_id=:userId ORDER BY a.create_time DESC LIMIT 1",nativeQuery=true)
	Appointment findLastAppointmentInfo(@Param("userId") String userId);
	
	/**
	 * 获取上一次预约的信息，如果没有则返回null
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT * FROM appointment  WHERE user_id=:userId and status=0 LIMIT 1",nativeQuery=true)
	Appointment findByUserId(@Param("userId") String userId);

	/**
	 * 更指定预约过期状态
	 * @param userId
	 */
	@Transactional
	@Modifying
	@Query(value="UPDATE  appointment SET status=2 WHERE  id=:appointmentId",nativeQuery=true)
	Integer updateOverdueStatus(@Param("appointmentId") Integer appointmentId);
	
	
	/**
	 * 获取过期的预约
	 * @param userId
	 */
	@Query(value="select * from appointment WHERE  status = 0 AND appoint_start_time <= now() and user_id=:userId limit 1",nativeQuery=true)
	Appointment findOverdue(@Param("userId") String userId);
	
	/**
	 * 获取全部过期的预约
	 * @param userId
	 */
	@Query(value="select * from appointment WHERE  status = 0 AND appoint_start_time <= now()",nativeQuery=true)
	List<Appointment> findAllOverdue();
	
	/**
	 * 获取预约列表
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT\r\n" + 
			"	a.appoint_start_time AS appointDate,\r\n" + 
			"	a.create_time AS createDate, " + 
			"	a. STATUS,\r\n" + 
			"	p.park_name AS parkName,\r\n" + 
			"	p.longitude,\r\n" + 
			"	p.latitude,\r\n" + 
			"	p.province_name AS province,\r\n" + 
			"	p.city_name AS city,\r\n" + 
			"	p.area_name AS area,\r\n" + 
			"	p.location,\r\n" + 
			"	p.id AS parkingId,\r\n" + 
			"	ulp.license_plat AS license\r\n" + 
			"FROM\r\n" + 
			"	appointment a\r\n" + 
			"LEFT JOIN parking p ON (a.parking_id = p.id)\r\n" + 
			"LEFT JOIN user_license_plat ulp ON (ulp.user_id = a.user_id)\r\n" + 
			"WHERE\r\n" + 
			"	a. STATUS = 0\r\n" + 
			"AND a.user_id = :userId",nativeQuery=true)
	List<Map<String, Object>> findAppointments(@Param("userId") String userId);
	
	@Query(value="SELECT free_standard_id FROM parking WHERE id=:parkingId",nativeQuery=true)
	Integer findFreeStandardId(@Param("parkingId") String parkingId);
	
	@Query(value=" SELECT a.id as appointmentId ,a.parking_id,u.phone,a.create_time,a.appoint_start_time,a.license_plate,a.`status`,"
			+ " p.park_name,concat_ws(p.province_name,p.city_name,p.area_name,p.location) AS locations, " 
			+ " p.latitude,p.longitude,o.fee,fs.hour_cost FROM appointment a " 
			+ " LEFT JOIN parking p ON (p.id = a.parking_id) " 
			+ " LEFT JOIN `user` u ON (u.id=a.user_id) " 
			+ " LEFT JOIN parking_info pi ON (pi.appointment_id = a.id) " 
			+ " LEFT JOIN `order` o ON (o.parking_info_id = pi.id) "
			+ " LEFT JOIN free_standard fs ON (fs.id = p.free_standard_id)" 
			+ " WHERE a.user_id =:userId ORDER BY create_time DESC   "
			+ " LIMIT :pageSize OFFSET :offset ",nativeQuery=true)
	List<Map<String, Object>> findUserAppointmentInfo(@Param("userId") Integer userId,@Param("pageSize")Integer pageSize,@Param("offset")Integer offset);
	
	/**
	 * 查询用户的预约次数
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT COUNT(*) FROM appointment WHERE user_id =:userId ",nativeQuery=true)
	Integer findUserAppointmentsCount(@Param("userId") Integer userId);
}
