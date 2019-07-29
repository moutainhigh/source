package com.dchip.cloudparking.web.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dchip.cloudparking.web.model.po.FreeStandard;


public interface IFreeStandardRepository extends JpaRepository<FreeStandard, Integer> {
	
	@Query(value="select f.* from free_standard f where f.id = :id limit 1",nativeQuery=true)
	FreeStandard findFreeStandard(@Param("id") Integer id);
	
	/**
	 * 查询指定停车场收费标准
	 */
	@Query(value = "SELECT f.* FROM free_standard f  " + 
			"LEFT JOIN parking p ON p.free_standard_id = f.id  " + 
			"WHERE p.id =:parkingId", nativeQuery = true)
	FreeStandard findFreeStandardByParkingId(@Param("parkingId") Integer parkingId);

}
