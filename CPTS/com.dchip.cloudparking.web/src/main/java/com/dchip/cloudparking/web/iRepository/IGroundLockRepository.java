package com.dchip.cloudparking.web.iRepository;

import com.dchip.cloudparking.web.model.po.GroundLock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroundLockRepository extends JpaRepository<GroundLock, Integer>{

	@Query(value="select * from ground_lock gl where gl.ground_uid = :groundUid LIMIT 1", nativeQuery = true)
	GroundLock findByUid(@Param("groundUid")String groundUid);

	@Query(value="SELECT COUNT(*) FROM ground_lock gl where license_plate = :licensePlate", nativeQuery = true)
	Integer checkRepeat(@Param("licensePlate")String licensePlate);
	
	


}
