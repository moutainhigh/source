package com.dchip.cloudparking.api.iRepository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.GroundLock;

@Repository
public interface IGroundLockRepository extends JpaRepository<GroundLock,Integer> {

	@Query(value="select gl.* from ground_lock gl where gl.ground_uid =:uniqueID", nativeQuery = true)
	GroundLock findByUid (@Param("uniqueID") String uniqueID);
	
	@Query(value="SELECT gl.* FROM ground_lock gl WHERE gl.ground_uid =:groundUid limit 1",nativeQuery=true)
	GroundLock findGroundLockByGroundUid(@Param("groundUid") String groundUid);

	@Query(value="SELECT COUNT(*) FROM ground_lock gl WHERE gl.ground_uid =:groundUid AND gl.license_plate is NULL",nativeQuery=true)
	Integer checkGroundLockIsUsable(@Param("groundUid") String groundUid);

	@Query(value="SELECT gl.*,p.park_name FROM ground_lock gl LEFT JOIN parking p ON(p.id=gl.parking_id) WHERE gl.license_plate =:licensePlate",nativeQuery=true)
	List<Map<String, Object>> findGroundLock(@Param("licensePlate")String licensePlate);

}
