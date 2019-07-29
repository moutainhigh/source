package com.dchip.cloudparking.wechat.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.wechat.model.po.Roadway;

@Repository
public interface IRoadWayRepository extends JpaRepository<Roadway, Integer>{
	
	@Query(value="select r.id from roadway r where r.parking_id =:parkingId and r.in_out_marker = 2", nativeQuery=true)
	Integer findMacByParkingIdAndMarker(@Param("parkingId")Integer parkingId);

}
