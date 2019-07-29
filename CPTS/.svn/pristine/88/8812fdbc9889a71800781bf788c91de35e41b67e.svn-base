package com.dchip.cloudparking.api.iRepository;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dchip.cloudparking.api.model.po.RechargeLog;


@Repository
public interface IRechargeLogRepository extends JpaRepository<RechargeLog, Integer>{

	/**
	 * 查询用户充值记录
	 * by 小梁
	 */
	@Query(value="SELECT *" + 
			" FROM recharge_log" + 
			" WHERE user_id = :userId AND `status` = 1",
			countQuery="select count(*)" + 
					" FROM recharge_log" + 
					" WHERE user_id = :userId AND `status` = 1", nativeQuery=true)
	Page<Map<String, Object>> findRechargeLog(@Param("userId") Integer userId, @Param("pageable") Pageable pageable);

	/**
	 * 用户充值的总金额
	 * by 小梁
	 */
	@Query(value="SELECT SUM(in_money + discount_money) AS rechargeMoney" + 
			" FROM recharge_log" + 
			" WHERE user_id = :userId AND `status` = 1 ", nativeQuery=true)
	BigDecimal findAllMoneyByUserId(@Param("userId") Integer userId);
	
	/**
	 * 通过 OutTradeNo 寻找充值记录
	 * @param out_trade_no
	 * @return
	 * sjh
	 */
	@Query(value = "select * from recharge_log where out_trade_no =:out_trade_no ", nativeQuery = true)
	RechargeLog findByOutTradeNo(@Param("out_trade_no") String out_trade_no);
	
	// 更新余额
    @Query(value = "update recharge_log set status = 1 where out_trade_no =:out_trade_no ", nativeQuery = true)
    @Modifying
    @Transactional
	void changeRechargeLog(@Param("out_trade_no") String out_trade_no);
}
