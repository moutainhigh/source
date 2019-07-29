package com.dchip.cloudparking.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.IBlacklistUserInfoService;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;
/**
 * 黑名单用户
 * @author 鼎芯科技
 *
 */
@RestController
@RequestMapping("/blackList")
public class BlacklistUserInfoController {
	
	@Autowired
	private IBlacklistUserInfoService blacklistUserInfoService;
	
	/**显示黑名单用户的未缴费信息，包括入库出库时间，未缴费金额,停车场编号，车牌号码
	 * by zyy
	 * */
	@RequestMapping("/getBlackUserInfo")
	public RetKit findBlackUserInfoByUserId(HttpServletRequest req) {
		if (StrKit.isBlank(req.getParameter("userId"))) {
			return RetKit.fail("userId不能为空");
		}
		Integer userId = Integer.parseInt(req.getParameter("userId"));
		return blacklistUserInfoService.getBlacklistInfo(userId);
	}
}
