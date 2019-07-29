package com.dchip.cloudparking.api.iRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.ParkingInfo;

@Repository
public interface IParkingInfoRepository extends JpaRepository<ParkingInfo, Integer> {

	/**
	 * 显示用户停车信息，包括停车场名称、地址、车位区域、收费标准，当前产生的费用。 by 小梁
	 */
	@Query(value = "SELECT pi.id AS parkingInfoId, pi.license_plate, pi.park_date, pi.is_lock, a.id AS appointmentId, a.`status` AS appointmentStatus,"
			+ " p.id AS parkingId, p.park_name, p.province_name, p.city_name, p.area_name, p.location,"
			+ " fs.free_time_length, fs.hour_cost, fs.most_cost" 
			+ " FROM parking_info pi"
			+ " LEFT JOIN parking p ON p.park_code = pi.park_code"
			+ " LEFT JOIN free_standard fs ON fs.id = p.free_standard_id"
			+ " LEFT JOIN appointment a ON a.id = pi.appointment_id"
			+ " WHERE pi.user_id = :userId AND pi.`status` = 1 order by pi.id desc", nativeQuery = true)
	List<Map<String, Object>> getParkingInfoByUserId(@Param("userId") Integer userId);

	/**
	 * 个人的无牌车出库显示某个停车场的停车记录
	 */
	@Query(value = "SELECT pi.license_plate, pi.park_date, pi.is_lock,"
			+ " p.id AS parkingId, p.park_name, p.province_name, p.city_name, p.area_name, p.location,"
			+ " fs.free_time_length, fs.hour_cost, fs.most_cost" + " FROM parking_info pi"
			+ " LEFT JOIN parking p ON p.park_code = pi.park_code"
			+ " LEFT JOIN free_standard fs ON fs.parking_id = p.id"
			+ " WHERE pi.park_code = :parkCode AND pi.user_id = :userId AND pi.`status` = 1", nativeQuery = true)
	List<Map<String, Object>> findParkingInfoByParkingCode(@Param("parkCode") String parkCode,@Param("userId") Integer userId);

	/**
	 * 根据停车id获取停车信息
	 */
	@Query(value = "SELECT * from parking_info pi where pi.id = :parkingInfoId", nativeQuery = true)
	ParkingInfo findParkingInfoById(@Param("parkingInfoId") Integer parkingInfoId);
	
	
	/**
	 * 根据停车id获取停车计费标准信息
	 */
	@Query(value="SELECT  fs.*,fs.id as 'freeStandardId'  from parking_info pi LEFT JOIN parking p on p.park_code = pi.park_code left join free_standard fs on fs.parking_id = p.id where pi.id = ?",nativeQuery=true)
	Map<String, Object> findParkingFree(@Param("parkingInfoId")Integer parkingInfoId);

	
	/**
	 * 查询个人扣费失败
	 */
	@Query(value="SELECT\r\n" + 
			"	*\r\n" + 
			"FROM\r\n" + 
			"`order` o \r\n" + 
			"WHERE\r\n" + 
			"	o.`status` = 2\r\n" + 
			"AND o.user_id = :userId ",nativeQuery=true)
	List<Map<String, Object>> findUserUnPayRecord(@Param("userId")Integer userId);
	
	/**
	 * 根据用户id查询状态为1的停车详情信息，状态为1说明还没结束订单
	 * @param userId
	 * @return
	 */
	@Query(value ="select * from parking_info p where p.status = 1 and p.user_id = :userId",nativeQuery=true)
	List<ParkingInfo> findParkingInfoByUserId(@Param("userId") Integer userId);
	
	/**
	 * 查询是否存在套牌车
	 * @param licensePlate
	 * @return
	 */
	@Query(value ="SELECT COUNT(*) FROM parking_info pi WHERE pi.license_plate =:licensePlate AND pi.`status` = 1",nativeQuery=true)
	Integer findCloneCar(@Param("licensePlate") String licensePlate);
	
	/**
	 * 通过userId查找出用户的停车信息ID
	 * @param userId
	 * @return
	 */
	@Query(value ="SELECT pi.id FROM parking_info pi WHERE pi.user_id =:userId ORDER BY pi.park_date DESC LIMIT 1",nativeQuery=true)
	Integer findIdByUserId(@Param("userId") Integer userId);
	
	/**
	 * 模糊搜索车牌前5位的入场停车记录   粤A12345 中的A1234
	 * @param parkCode
	 * @param status
	 * @param carNum
	 * @return
	 */
	@Query(value="select pi.* from parking_info pi where pi.park_code=:parkCode "
			+ " and pi.`status` = 1 "
			+ " and substring(pi.license_plate, 2,5) = :carNum "
			+ " and LENGTH(pi.license_plate) = :carNumLength "
			+ " ORDER BY pi.id desc limit 1", nativeQuery=true)
	ParkingInfo findParkingInfoVagueTwoFive(@Param("parkCode") int parkCode, @Param("carNum") String carNum, @Param("carNumLength") int carNumLength);
	
	/**
	 * 模糊搜索车牌后5位的入场停车记录   粤A12345 中的12345
	 * @param parkCode
	 * @param status
	 * @param carNum
	 * @return
	 */
	@Query(value="select pi.* from parking_info pi where pi.park_code=:parkCode "
			+ " and pi.`status` = 1 "
			+ " and substring(pi.license_plate, 3,5) = :carNum "
			+ " and LENGTH(pi.license_plate) = :carNumLength "
			+ " ORDER BY pi.id desc limit 1", nativeQuery=true)
	ParkingInfo findParkingInfoVagueThreeFive(@Param("parkCode") int parkCode, @Param("carNum") String carNum, @Param("carNumLength") int carNumLength);
	
	/**
	 * 根据车牌与当前时间，提前一分钟显示parkingInfo
	 * @param licensePlate
	 * @param firstDate
	 * @param nowDate
	 * @return
	 */
	@Query(value="select pi.* from parking_info pi "
			+ " where pi.`status` = 1 "
			+ " and pi.license_plate = :licensePlate "
			+ " and pi.park_date BETWEEN  :firstDate and :nowDate limit 1", nativeQuery =true)
	ParkingInfo findParkingInfoByBetweenFirstDate (@Param("licensePlate") String licensePlate, @Param("firstDate") Date firstDate, @Param("nowDate") Date nowDate);
	
}
