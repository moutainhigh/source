package com.dchip.cloudparking.api.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dchip.cloudparking.api.model.po.DeductionModel;

public interface IDeductionModelRepository extends JpaRepository<DeductionModel, Integer> {
	/**
	 * 查询商户(含parkingId) 所在的停车场是否可用的抵扣券模板
	 * @param parkingId
	 * @return
	 */
	@Query(value = "SELECT * from deduction_model dm where dm.`status` = 0 "
			+ "and dm.parking_id = :parkingId and (dm.due_time > now() or dm.due_time IS NULL) limit 1", nativeQuery = true)
	DeductionModel getDeductionModelByParkingId(@Param("parkingId") Integer parkingId);
	
	
}
