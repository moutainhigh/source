package com.dchip.cloudparking.api.serviceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.api.iRepository.IMemberRuleRepository;
import com.dchip.cloudparking.api.iService.IMemberRuleService;
@Service
public class MemberRuleServiceImp extends BaseService implements IMemberRuleService{
	
	@Autowired
	private IMemberRuleRepository memberRuleRepository;
	
	/**
	 * 查找对应会员等级所需充值金额
	 * by 小梁
	 */
	public Integer findMonthFee(Integer grade) {
		return memberRuleRepository.findMoneyByGrade(grade).getMoney();
	}

}
