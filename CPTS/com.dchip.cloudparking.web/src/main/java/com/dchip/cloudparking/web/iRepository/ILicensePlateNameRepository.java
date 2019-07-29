package com.dchip.cloudparking.web.iRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.LicensePlateName;

@Repository
public interface ILicensePlateNameRepository extends JpaRepository<LicensePlateName, Integer>{
	
	@Query(value="SELECT * FROM license_plate_name lpn WHERE type = 1",nativeQuery=true)
	List<LicensePlateName> findLicensePlateNameA();
	
	@Query(value="SELECT * FROM license_plate_name lpn WHERE type = 2",nativeQuery=true)
	List<LicensePlateName> findLicensePlateNameB();
	
	@Query(value="SELECT * FROM license_plate_name lpn WHERE type = 3",nativeQuery=true)
	List<LicensePlateName> findLicensePlateNameC();

}
