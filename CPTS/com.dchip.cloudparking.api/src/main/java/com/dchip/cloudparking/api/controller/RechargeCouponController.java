package com.dchip.cloudparking.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.IRechargeCouponService;
import com.dchip.cloudparking.api.utils.RetKit;

@RestController
@RequestMapping("/rechargeCoupon")
public class RechargeCouponController {
	@Autowired
	private IRechargeCouponService rechargeCouponService;
	
	/**
	 * 查询充值钱数
	 * by ZYY
	 */
	@RequestMapping("/findRechargeCoupon")
	public RetKit findRechargeCoupon() {
		try {
			return rechargeCouponService.findRechargeCoupon();
		} catch (Exception e) {
			return RetKit.fail();
		}
	}

}
