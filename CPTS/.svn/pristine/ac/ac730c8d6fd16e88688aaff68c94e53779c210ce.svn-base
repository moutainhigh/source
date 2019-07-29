package com.dchip.cloudparking.web.iRepository;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.utils.RetKit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.Parking;

@Repository
public interface IParkingRepository extends JpaRepository<Parking, Integer> {
	
	@Query(value="SELECT count(*) as count ,date_format(create_time,'%Y/%c/%e') as date FROM parking  WHERE parking.status = 0  GROUP BY date ORDER BY create_time ASC ",nativeQuery=true)
	List<Map<String, Object>> getChartData();

	@Query(value="SELECT * FROM parking WHERE company_id =:companyId and status = 0",nativeQuery=true)
	List<Parking> findAllByCompanyId(@Param("companyId") Integer companyId);

	@Query(value="update parking set status = :status WHERE id =:parkingId",nativeQuery=true)
	@Modifying
    Integer softlyDelete(@Param("status") Integer status,@Param("parkingId") Integer parkingId);

	@Query(value="SELECT * FROM parking WHERE status = 0",nativeQuery=true)
	List<Parking> findAllParking();
	
	@Query(value="SELECT COUNT(*) FROM parking p  " + 
			"WHERE p.company_id =:companyId AND p.park_name =:parkSpaceName ",nativeQuery=true)
	Integer checkRepeat(@Param("companyId")String companyId,@Param("parkSpaceName") String parkSpaceName);
	
	@Query(value="select p.* from parking p where p.id = :parkingId",nativeQuery=true)
	Parking findParkingById(@Param("parkingId") String parkingId);
	
	@Query(value="select p.* from parking p where p.id = :parkingId",nativeQuery=true)
	Parking findParkingById(@Param("parkingId") Integer parkingId);
	
}
