package com.dchip.cloudparking.web.iRepository;


import com.dchip.cloudparking.web.model.po.Deduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeductionRepository extends JpaRepository<Deduction, Integer> {

    @Query(value = "update deduction set status=:status where id =:deductionId",nativeQuery = true)
    @Modifying
    Integer changeStatus(@Param("status") Integer status,@Param("deductionId") Integer deductionId);
    /**
	 * 查找这个车牌在这个停车场是否有未被使用并且还没过期的抵扣券
	 * @param licensePlat
	 * @param parkingId
	 * @return
	 */
	@Query(value = "SELECT * from deduction d where d.`status` = 1  and d.license_plat = :licensePlat "
			+ "and d.parking_id = :parkingId  ORDER BY d.receive_time DESC LIMIT 1", nativeQuery = true)
	Deduction getCanUseDeduction(@Param("licensePlat") String licensePlat,@Param("parkingId") Integer parkingId);
}
