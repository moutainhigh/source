package com.dchip.cloudparking.web.iRepository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.DeductionModel;
import com.dchip.cloudparking.web.model.po.FirstWhiteList;

@Repository
public interface IDeductionModelRepository extends JpaRepository<DeductionModel, Integer> {

    @Query(value = "update deduction_model set status=:status where id =:id",nativeQuery = true)
    @Modifying
    Integer changeStatus(@Param("status") Integer status,@Param("id") Integer id);
    
    /**
     * 判断该停车场只有一个抵扣券模板可用
     */
    @Query(value = "SELECT * from deduction_model dm where dm.parking_id = :parkingId and dm.`status` = 0",nativeQuery = true)
    DeductionModel isOnlyOne(@Param("parkingId") Integer parkingId);
    /**
	 * 查询商户(含parkingId) 所在的停车场是否可用的抵扣券模板
	 * @param parkingId
	 * @return
	 */
	@Query(value = "SELECT * from deduction_model dm where dm.`status` = 0 "
			+ "and dm.parking_id = :parkingId and (dm.due_time > now() or dm.due_time IS NULL) limit 1", nativeQuery = true)
	DeductionModel getDeductionModelByParkingId(@Param("parkingId") Integer parkingId);
}