package com.dchip.cloudparking.web.iRepository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.Roadway;


@Repository
public interface IRoadwayRepository extends JpaRepository<Roadway, Integer>{
	
	@Query(value="SELECT r.* from main_roadway mr LEFT JOIN roadway r ON(mr.roadway_id=r.id) WHERE mr.main_id =:mainControlId",nativeQuery=true)
	List<Roadway> findRoadways(@Param("mainControlId")Object mainControlId);
	
	@Query(value="SELECT r.id,r.road_name FROM roadway r  " + 
			"WHERE r.parking_id =:parkingId AND r.in_out_marker = 2",nativeQuery=true)
	List<Map<String, Object>> getOutRoadName(@Param("parkingId")Integer parkingId);

	@Query(value="SELECT COUNT(*) FROM roadway r  " + 
			"WHERE r.camera_mac = :cameraMac",nativeQuery=true)
	Integer checkCameraMac(@Param("cameraMac")String cameraMac);

	@Query(value="SELECT r.* FROM main_roadway mr  " + 
			"LEFT JOIN roadway r ON (mr.roadway_id = r.id)  " + 
			"WHERE mr.main_id =:mainControlId AND r.in_out_marker = 1",nativeQuery=true)
	List<Roadway> findEntryRoadways(@Param("mainControlId")String mainControlId);

	@Query(value="SELECT r.* FROM main_roadway mr  " + 
			"LEFT JOIN roadway r ON (mr.roadway_id = r.id)  " + 
			"WHERE mr.main_id =:mainControlId AND r.in_out_marker = 2",nativeQuery=true)
	List<Roadway> findExitRoadways(@Param("mainControlId")String mainControlId);
}
