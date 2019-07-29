package com.dchip.cloudparking.wechat.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.wechat.model.po.Parking;


@Repository
public interface IParkingRepository extends JpaRepository<Parking, Integer> {
	
	@Query(value="select p.* from parking p where p.id = :parkingId",nativeQuery=true)
	Parking findParkingById(@Param("parkingId") String parkingId);
	
	@Query(value="select p.* from parking p where p.park_code =:parkCode", nativeQuery=true)
	Parking findParkingByParkCode(@Param("parkCode") Integer packCode);
}
