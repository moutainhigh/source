package com.dchip.cloudparking.api.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.Deduction;

@Repository
public interface IDeductionRepository extends JpaRepository<Deduction, Integer> {

	/**
	 * 查找这个车牌在这个停车场是否有未被使用并且还没过期的抵扣券
	 * @param licensePlat
	 * @param parkingId
	 * @return
	 */
	@Query(value = "SELECT * from deduction d where d.`status` = 0  and d.license_plat = :licensePlat "
			+ "and d.parking_id = :parkingId AND d.parking_user_id =:parkingUserId ORDER BY d.receive_time DESC LIMIT 1", nativeQuery = true)
	Deduction getCanUseDeduction(@Param("licensePlat") String licensePlat,@Param("parkingId") Integer parkingId,@Param("parkingUserId") Integer parkingUserId);
}
