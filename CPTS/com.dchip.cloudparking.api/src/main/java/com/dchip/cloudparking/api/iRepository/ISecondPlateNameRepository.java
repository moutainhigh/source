package com.dchip.cloudparking.api.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.SecondPlateName;

@Repository
public interface ISecondPlateNameRepository extends JpaRepository<SecondPlateName, Integer>{
	
	/**
	 * 根据车牌号返回白名单信息
	 * @param licensePlate
	 * @return
	 */
	@Query(value="select spn.* from second_plate_name spn where spn.license_plate = :licensePlate",nativeQuery=true)
	SecondPlateName getSecondPlateNameByLicensePlate(@Param("licensePlate") String licensePlate);
}
