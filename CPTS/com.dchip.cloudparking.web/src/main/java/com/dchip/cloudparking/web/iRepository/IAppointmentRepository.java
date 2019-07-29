package com.dchip.cloudparking.web.iRepository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.Appointment;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Integer> {

	/**
	 * 获取当前的预约数
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT count(*) FROM appointment a WHERE user_id=:userId AND a.status = 0",nativeQuery=true)
	Integer appointmentCount(@Param("userId") String userId);
	
	/**
	 * 获取预约列表
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT\r\n" + 
			"	a.appoint_start_time AS date,\r\n" + 
			"\r\n" + 
			"IF (\r\n" + 
			"	a. STATUS = 0,\r\n" + 
			"	'成功预约',\r\n" + 
			"\r\n" + 
			"IF (\r\n" + 
			"	a. STATUS = 3,\r\n" + 
			"	'取消预约',\r\n" + 
			"	'已完成预约'\r\n" + 
			")\r\n" + 
			") STATUS,\r\n" + 
			" p.park_name AS parkName,\r\n" + 
			" ulp.license_plat AS license\r\n" + 
			"FROM\r\n" + 
			"	appointment a\r\n" + 
			"LEFT JOIN parking p ON (a.parking_id = p.id)\r\n" + 
			"LEFT JOIN user_license_plat ulp ON (ulp.user_id = a.user_id)\r\n" + 
			"WHERE\r\n" + 
			"	a.user_id = :userId",nativeQuery=true)
	List<Map<String, Object>> findAppointments(@Param("userId") String userId);
	
	@Query(value="SELECT id FROM free_standard WHERE parking_id=:parkingId",nativeQuery=true)
	Integer findFreeStandardId(@Param("parkingId") String parkingId);
}
