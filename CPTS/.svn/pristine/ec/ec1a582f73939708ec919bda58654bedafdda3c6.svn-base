package com.dchip.cloudparking.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iRepository.IMemberRuleRepository;
import com.dchip.cloudparking.api.utils.RetKit;
/**
 * 会员规则
 * @author 鼎芯科技
 *
 */
@RestController
@RequestMapping("/memberRule")
public class MemberRuleController {

	@Autowired
	private IMemberRuleRepository memberRuleRepository;

	/**
	 * 获取会员等级列表
	 * by 小梁
	 */
	@RequestMapping("/getRule")
	public RetKit getRule(HttpServletRequest request) {
		return RetKit.okData(memberRuleRepository.findAll()); 
	}
}
