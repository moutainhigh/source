package com.dchip.cloudparking.api.iRepository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.Roadway;

@Repository
public interface IRoadwayRepository extends JpaRepository<Roadway, Integer>{

	@Query(value="SELECT  " + 
			"	r.camera_ip AS cameraIp,  " + 
			"	r.camera_type AS cameraType,  " + 
			"	r.camera_mac AS cameraMac,  " + 
			"	r.in_out_marker AS turnover,  " + 
			"	s.total_count AS totalCount,  " + 
			"	s.enter_count AS enterCount, p.`status` " + 
			"FROM  " + 
			"	main_roadway mr  " + 
			"LEFT JOIN main_control m ON (m.id = mr.main_id)  " + 
			"LEFT JOIN roadway r ON (r.id = mr.roadway_id) LEFT JOIN stock s on(s.parking_id = r.parking_id)  "
			+ " LEFT JOIN parking p on (p.id = r.parking_id) " + 
			" WHERE  " + 
			"	m.mac =:mac" , nativeQuery=true)
	List<Map<String, Object>> findCameraInfo(@Param("mac")Object mac);
	
	@Query(value="SELECT * FROM roadway WHERE camera_mac =:mac LIMIT 1" , nativeQuery=true)
	Roadway findByCameraMac(@Param("mac")Object mac);

	/**
	 * 获取入口车道信息
	 * @author zyy
	 * @param parkingId
	 * @return
	 */
	@Query(value="SELECT r.parking_id,r.in_out_marker,r.road_name,r.camera_mac,r.`status`,r.is_online,mc.mac FROM roadway r  " + 
			"LEFT JOIN main_roadway mr ON(mr.roadway_id = r.id)  " + 
			"LEFT JOIN main_control mc ON(mc.id = mr.main_id)  " + 
			"WHERE r.parking_id =:parkingId AND r.in_out_marker = 1 AND mc.mac IS NOT NULL AND mc.`status` <> -2",nativeQuery=true)
	List<Map<String, Object>> findInRoadInfo(@Param("parkingId")String parkingId);

	/**
	 * 获取出口车道信息
	 * @author zyy
	 * @param parkingId
	 * @return
	 */
	@Query(value="SELECT r.parking_id,r.in_out_marker,r.road_name,r.camera_mac,r.`status`,r.is_online,mc.mac FROM roadway r  " + 
			"LEFT JOIN main_roadway mr ON(mr.roadway_id = r.id)  " + 
			"LEFT JOIN main_control mc ON(mc.id = mr.main_id)  " + 
			"WHERE r.parking_id =:parkingId AND r.in_out_marker = 2 AND mc.mac IS NOT NULL AND mc.`status` <> -2",nativeQuery=true)
	List<Map<String, Object>> findOutRoadInfo(@Param("parkingId")String parkingId);
	
}
