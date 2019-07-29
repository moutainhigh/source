package com.dchip.cloudparking.web.iRepository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.SecondPlateName;

@Repository
public interface ISecondPlateNameRepository extends JpaRepository<SecondPlateName, Integer>{
	
	@Query(value="SELECT id,park_name FROM parking WHERE company_id =:companyId and status = 0",nativeQuery=true)
	List<Map<String, Object>> getCompanyParkName(@Param("companyId") Integer companyId);

	@Query(value="SELECT COUNT(*) FROM second_plate_name spn  " + 
			"WHERE spn.license_plate =:licensePlate AND spn.parking_id =:parkingId",nativeQuery=true)
	Integer checkRepeat(@Param("licensePlate")String licensePlate, @Param("parkingId")String parkingId);

}
