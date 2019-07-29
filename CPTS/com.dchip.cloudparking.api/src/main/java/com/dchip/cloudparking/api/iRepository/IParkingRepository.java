package com.dchip.cloudparking.api.iRepository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.Parking;

@Repository
public interface IParkingRepository extends JpaRepository<Parking, Integer> {
	
	/**
	 * 地图图钉： 点击图钉弹出显示停车场名称，地址，车位信息情况。可直接导航
	 */
	@Query(value = "SELECT s.parking_id, s.total_count, s.enter_count," + 
			" p.park_name, p.province_name, p.city_name, p.area_name, p.location, p.latitude, p.longitude, p.`status`" + 
			" FROM parking p" + 
			" LEFT JOIN stock s ON s.parking_id = p.id " + 
			" WHERE p.id = :parkingId AND p.status <> -2 ", nativeQuery = true)
	List<Map<String, Object>> findParkingByParkingId(@Param("parkingId") Integer parkingId);
	
	/**
	 * 根据停车场ID查找出管理服务中心电话
	 * @param parkingId
	 * @return
	 */
	@Query(value = "SELECT manager_phone FROM parking WHERE id = :parkingId AND status <> -2",nativeQuery = true)
	Map<String, Object> findPhoneByParkingId(@Param("parkingId") Integer parkingId);
	
	/**
	 * 根据停车场编号查找出管理服务中心电话
	 * @param parkingCode
	 * @return
	 */
	@Query(value = "SELECT manager_phone FROM parking WHERE park_code = :parkingCode AND status <> -2",nativeQuery = true)
	String findPhoneByParkingCode(@Param("parkingCode") Integer parkingCode);
	
	/**
	 * 根据停车场编号查找停车场
	 * @param 
	 * @return
	 */
	@Query(value = "SELECT * FROM parking WHERE park_code = :parkingCode AND status <> -2 limit 1",nativeQuery = true)
	Parking findByParkingCode(@Param("parkingCode") Integer parkingCode);
	
	/**
	 * 查找指定出库信息
	 */
	
	
	/**
	 * 查找附近的停车场
	 * @param minLongi  最小经度
	 * @param maxLongi 最大纬度
	 * @param minLati 最小纬度
	 * @param maxLati 最大纬度
	 * @param longitude 当前经度
	 * @param latitude 当前纬度
	 * @return
	 */
	@Query(value = "SELECT  " + 
			"	p.id,  " + 
			"	p. STATUS,  " + 
			"	p.park_name AS parkName,  " + 
			"	p.province_name AS province,  " + 
			"	p.city_name AS city,  " + 
			"	p.area_name AS area,  " + 
			"	p.location,  " + 
			"	p.comcat_name AS contactName,  " + 
			"	p.comcat_phone AS contactPhone,  " + 
			"	CAST(p.longitude AS DECIMAL(10, 6)) AS longitude,  " + 
			"	CAST(p.latitude AS DECIMAL(10, 6)) AS latitude,  " + 
			"	fs.free_time_length AS freeTimeLength,  " + 
			"	fs.hour_cost AS hourCost,  " + 
			"	fs.most_cost AS mostCost,  " + 
			"	s.total_count AS totalCarport,  " + 
			"	s.enter_count AS enterCount,  " + 
			"	ROUND(  " + 
			"		6378.137 * 2 * ASIN(  " + 
			"			SQRT(  " + 
			"				POW( SIN((:latitude - latitude) * PI() / 360), 2 ) " +
			"				+ COS(:latitude * PI() / 180) " +
			"				* COS((latitude + 0.0) * PI() / 180) " +
			"				* POW( SIN((:longitude - longitude) * PI() / 360), 2 ) " +
			"				) ) * 1000  " + 
			"	) AS distance "+
			" FROM  " + 
			"	parking p  " + 
			" LEFT JOIN free_standard fs ON (fs.id = p.free_standard_id)  " + 
			" LEFT JOIN stock s ON (s.parking_id = p.id) " + 
			" WHERE  " + 
			"	 ( p.longitude BETWEEN :minLongi AND :maxLongi )  " + 
			" AND (p.latitude BETWEEN :minLati AND :maxLati)  " +
			" AND p.`status` <> -2 " +
			" ORDER BY distance ASC",nativeQuery = true)
	List<Map<String, Object>>findneighborhoodParking(@Param("minLongi")Object minLongi,
			@Param("maxLongi")Object maxLongi,
			@Param("minLati")Object minLati,@Param("maxLati")Object maxLati,
			@Param("longitude")Object longitude,@Param("latitude")Object latitude);
	
	@Query(value = "SELECT " + 
			"	p.id, " + 
			"	p. STATUS,  " + 
			"	p.park_name AS parkName,  " + 
			"	p.province_name AS province,  " + 
			"	p.city_name AS city,  " + 
			"	p.area_name AS area,  " + 
			"	p.location,  " + 
			"	p.comcat_name AS contactName,  " + 
			"	p.comcat_phone AS contactPhone,  " + 
			"	CAST(p.longitude AS DECIMAL(10, 6)) AS longitude,  " + 
			"	CAST(p.latitude AS DECIMAL(10, 6)) AS latitude,  " + 
			"	fs.free_time_length AS freeTimeLength,  " + 
			"	fs.hour_cost AS hourCost,  " + 
			"	fs.most_cost AS mostCost,  " + 
			"	s.total_count AS totalCarport,  " + 
			"	s.enter_count AS enterCount  " + 
			"FROM  " + 
			"	parking p  " + 
			"LEFT JOIN free_standard fs ON (fs.id = p.free_standard_id)  " + 
			"LEFT JOIN stock s ON (s.parking_id = p.id)  " + 
			"WHERE  " + 
			"	p.park_name LIKE %:name% or p.city_name LIKE %:name% " +
			"   AND p.`status` <> -2 ",nativeQuery = true)
	List<Map<String, Object>>findByNameLike(@Param("name")Object name);
	
	@Query(value = "SELECT  " + 
			"	p.id,  " + 
			"	p. STATUS,  " + 
			"	p.park_name AS parkName,  " + 
			"	p.province_name AS province,  " + 
			"	p.city_name AS city,  " + 
			"	p.area_name AS area,  " + 
			"	p.location,  " + 
			"	p.comcat_name AS contactName,  " + 
			"	p.comcat_phone AS contactPhone,  " + 
			"	CAST(p.longitude AS DECIMAL(10, 6)) AS longitude,  " + 
			"	CAST(p.latitude AS DECIMAL(10, 6)) AS latitude,  " + 
			"	fs.free_time_length AS freeTimeLength,  " + 
			"	fs.hour_cost AS hourCost,  " + 
			"	fs.most_cost AS mostCost,  " + 
			"	s.total_count AS totalCarport,  " + 
			"	s.enter_count AS enterCount  " + 
			"FROM  " + 
			"	parking p  " + 
			"LEFT JOIN free_standard fs ON (fs.id = p.free_standard_id)  " + 
			"LEFT JOIN stock s ON (s.parking_id = p.id)  " + 
			"WHERE  " + 
			"	p.id = :parkingId" +
			" AND p.status <> -2",nativeQuery = true)
	Map<String, Object>findByPakingId(@Param("parkingId")Object parkingId);

	@Query(value="SELECT id,park_name FROM parking WHERE company_id =:companyId",nativeQuery=true)
	List<Map<String, Object>> findParkByComoanyId(@Param("companyId")String companyId);

	@Query(value="SELECT spn.*,p.park_name FROM second_plate_name spn  " + 
			"LEFT JOIN parking p ON(p.id = spn.parking_id)  " + 
			"WHERE p.id =:parkingId",nativeQuery=true)
	List<Map<String, Object>> findWhiteListByParkingId(@Param("parkingId")String parkingId);
}
