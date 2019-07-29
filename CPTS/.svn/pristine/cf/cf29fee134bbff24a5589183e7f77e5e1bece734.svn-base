package com.dchip.cloudparking.api.serviceImp;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.api.iRepository.IRechargeLogRepository;
import com.dchip.cloudparking.api.iService.IRechargeLogService;
import com.dchip.cloudparking.api.utils.RetKit;
@Service
public class RechargeLogServiceImp extends BaseService implements IRechargeLogService{

	@Autowired
	private IRechargeLogRepository rechargeLogRepository;
	
	/**
	 * 查询用户充值记录
	 * by 小梁
	 */
	public RetKit findRechargeLog(Integer userId, Integer pageSize, Integer pageNum) {
		try {
			Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
			Page<Map<String, Object>> page =  rechargeLogRepository.findRechargeLog(userId, pageable);
			return RetKit.okData(page);
		} catch (Exception e) {
			return RetKit.fail();
		}
	}
	
	/**
	 * 用户充值的总金额
	 * by 小梁
	 */
	public BigDecimal findAllMoneyByUserId(Integer userId) {
		return rechargeLogRepository.findAllMoneyByUserId(userId);
	}

}
