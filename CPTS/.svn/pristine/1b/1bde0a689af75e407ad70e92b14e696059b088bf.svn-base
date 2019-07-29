package com.dchip.cloudparking.web.iRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.MonthlyCardPay;

@Repository
public interface IMonthlyCardPayRepository extends JpaRepository<MonthlyCardPay, Integer>{
	
	/**
	 * 寻找未生成Finance的数据
	 * by 小梁
	 */
	@Query(value = "SELECT mcp.*" + 
			" FROM monthly_card_pay mcp" + 
			" LEFT JOIN monthly_card mc ON (mcp.monthly_card_id = mc.id)" + 
			" WHERE mc.parking_id = :parkingId AND mcp.`status` <> 1 and mcp.is_transfer = 0", nativeQuery = true)
	List<MonthlyCardPay> findNoPay(@Param("parkingId") Integer parkingId);

}
