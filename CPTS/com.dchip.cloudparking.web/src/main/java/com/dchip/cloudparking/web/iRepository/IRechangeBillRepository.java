package com.dchip.cloudparking.web.iRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.RechangeBill;


@Repository
public interface IRechangeBillRepository extends JpaRepository<RechangeBill, Integer>{

	/**
	 * 按月查询充值总额接口
	 * by 小梁
	 */
	@Query(value="SELECT sum(amount) AS monthAmount" + 
			" FROM rechange_bill" + 
			" WHERE type = 1 AND `status` = 1 AND user_id = :userId" + 
			" AND date_format(create_time, '%Y-%m') = date_format(:createTime, '%Y-%m') ", nativeQuery=true)
	BigDecimal findMonthAmount(@Param("userId") Integer userId, @Param("createTime") String createTime);

	/**
	 * 按月查询充值明细接口
	 * by 小梁
	 */
	@Query(value="SELECT *" + 
			" FROM rechange_bill" + 
			" WHERE type = 1 AND `status` = 1 AND user_id = :userId" + 
			" AND date_format(create_time, '%Y-%m') = date_format(:createTime, '%Y-%m') ", nativeQuery=true)
	List<Map<String, Object>> getAmountByCreateTime(@Param("userId") Integer userId, @Param("createTime") String createTime);

	/**
	 * 用户充值的总金额
	 * by 小梁
	 */
	@Query(value="SELECT sum(amount) AS monthAmount" + 
			" FROM rechange_bill" + 
			" WHERE type = 1 AND `status` = 1 AND user_id = :userId", nativeQuery=true)
	BigDecimal findAllAmountByUserId(@Param("userId") Integer userId);
}
