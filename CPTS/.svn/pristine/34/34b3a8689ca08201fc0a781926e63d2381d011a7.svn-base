package com.dchip.cloudparking.web.iRepository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.ParkingInfo;

@Repository
public interface IParkingInfoRepository extends JpaRepository<ParkingInfo, Integer> {
	
	@Query(value="SELECT pi.park_date,pi.out_date,p.park_name, "
			+ " concat_ws(p.province_name,p.city_name,p.area_name,p.location) AS locations "
			+ " FROM parking_info pi "  
			+ " LEFT JOIN parking p ON (p.park_code = pi.park_code) "
			+ " where pi.license_plate =:licensePlate "
			+ " ORDER BY pi.park_date DESC LIMIT 2",nativeQuery=true)
	List<Map<String, Object>> findCloneCarInfo(@Param("licensePlate") String licensePlate);

		@Query(value="select pi.* from parking_info pi "
			+ " where pi.park_code = :parkcode "
			+ " and pi.license_plate = :licensePlate "
			+ " and pi.`status` = 1 ORDER BY pi.id desc LIMIT 1",nativeQuery=true)
	ParkingInfo findParkingInfoByParkCodeAndLicensePlate(@Param("parkcode") String parkcode, 
			@Param("licensePlate") String licensePlate);
	
}
