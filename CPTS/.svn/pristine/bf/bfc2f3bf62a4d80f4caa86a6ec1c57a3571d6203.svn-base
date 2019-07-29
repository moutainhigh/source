package com.dchip.cloudparking.api.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dchip.cloudparking.api.model.po.FreeStandard;


public interface IFreeStandardRepository extends JpaRepository<FreeStandard, Integer> {
	
	@Query(value="select f.* from free_standard f where f.id = :id limit 1",nativeQuery=true)
	FreeStandard findFreeStandard(@Param("id") Integer id);
	
	/**
	 * 查询指定停车场收费标准
	 */
	@Query(value = "SELECT\r\n" + 
			"	fs.*\r\n" + 
			"FROM\r\n" + 
			"	free_standard fs\r\n" + 
			"LEFT JOIN parking p ON (fs.id = p.free_standard_id)\r\n" + 
			"WHERE\r\n" + 
			"	p.id = :parkingId", nativeQuery = true)
	FreeStandard findFreeStandardByParkingId(@Param("parkingId") Integer parkingId);
}
