package com.dchip.cloudparking.web.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.UserLicensePlat;

@Repository
public interface IUserLicensePlatRepository extends JpaRepository<UserLicensePlat, Integer> {
	
	/**
	 * 通过车牌查找该条停车信息
	 * by zyy
	 * @param licensePlate
	 * @return
	 */
	@Query(value = "SELECT plt.* FROM user_license_plat plt WHERE plt.license_plat = :licensePlat ", nativeQuery = true)
	UserLicensePlat findByLicensePlate(@Param("licensePlat") String licensePlat);
}
