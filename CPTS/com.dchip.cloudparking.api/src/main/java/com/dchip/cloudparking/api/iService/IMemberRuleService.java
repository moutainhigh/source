package com.dchip.cloudparking.api.iService;

public interface IMemberRuleService {
	
	/**
	 * 查找对应会员等级所需充值金额
	 * by 小梁
	 */
	public Integer findMonthFee(Integer grade);
}
