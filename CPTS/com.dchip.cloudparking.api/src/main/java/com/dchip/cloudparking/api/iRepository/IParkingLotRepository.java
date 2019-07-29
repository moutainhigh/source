package com.dchip.cloudparking.api.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.ParkingLot;

@Repository
public interface IParkingLotRepository extends JpaRepository<ParkingLot, Integer>{

	@Query(value="SELECT pl.* FROM parking_lot pl WHERE pl.uid =:uid",nativeQuery=true)
	ParkingLot findByUid(@Param("uid")String uid);

}
