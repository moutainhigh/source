package com.dchip.cloudparking.wechat.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.wechat.model.po.ParkingInfo;

@Repository
public interface IParkingInfoRepository extends JpaRepository<ParkingInfo, Integer> {

	/**
	 * 根据停车场id和车牌查找停车记录
	 * @param psrkingId
	 * @param licensePlate
	 * @return
	 */
	@Query(value="select pi.* from parking_info pi "
			+ " where pi.park_code = :parkcode "
			+ " and pi.license_plate = :licensePlate "
			+ " and pi.`status` = 1 ORDER BY pi.id desc LIMIT 1",nativeQuery=true)
	ParkingInfo findParkingInfoByParkCodeAndLicensePlate(@Param("parkcode") String parkcode, 
			@Param("licensePlate") String licensePlate);

	@Query(value="SELECT * FROM parking_info WHERE user_id =:userId AND park_code =:parkCode ORDER BY park_date DESC LIMIT 1",nativeQuery=true)
	ParkingInfo findByUserId(@Param("userId")Integer userId,@Param("parkCode")Integer parkCode);
}
