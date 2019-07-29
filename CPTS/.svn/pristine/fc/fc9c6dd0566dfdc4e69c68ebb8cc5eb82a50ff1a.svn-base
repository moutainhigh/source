package com.dchip.cloudparking.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.IRechargeLogService;
import com.dchip.cloudparking.api.utils.RetKit;
/**
 * 付款日志
 * @author 鼎芯科技
 *
 */
@RestController
@RequestMapping("/rechargeLog")
public class RechargeLogController {

	@Autowired
	private IRechargeLogService rechargeLogService;
	
	/**
	 * 查询用户充值记录
	 * by 小梁
	 */
	@RequestMapping("/findRechargeLog")
	public RetKit findRechargeLog(HttpServletRequest request) {
		try {
			Integer userId = Integer.valueOf(request.getParameter("userId"));
			Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
			Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));
			return rechargeLogService.findRechargeLog(userId, pageSize, pageNum);
		} catch (Exception e) {
			return RetKit.fail();
		}
	}
}