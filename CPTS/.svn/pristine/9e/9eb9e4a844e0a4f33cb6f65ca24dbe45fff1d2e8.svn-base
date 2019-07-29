package com.dchip.cloudparking.wechat.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dchip.cloudparking.wechat.model.po.FreeStandard;



public interface IFreeStandardRepository extends JpaRepository<FreeStandard, Integer> {
	
	
	/**
	 * 查询指定停车场收费标准
	 */
	@Query(value = "SELECT " + 
			"	fs.* " + 
			"FROM " + 
			"	free_standard fs " + 
			"LEFT JOIN parking p ON (fs.id = p.free_standard_id) " + 
			"WHERE " + 
			"	p.id = :parkingId", nativeQuery = true)
	FreeStandard findFreeStandardByParkingId(@Param("parkingId") Integer parkingId);
}
