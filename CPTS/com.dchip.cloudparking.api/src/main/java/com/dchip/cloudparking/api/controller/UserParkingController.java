package com.dchip.cloudparking.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.IUserParkingService;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;

@RestController
@RequestMapping("/userParking")
public class UserParkingController {
	@Autowired
	private IUserParkingService userParkingService;
	
	//获取我的停车场信息--月卡信息
	@RequestMapping("/getInfo")
	public RetKit getMonthlyCardAndParkingInfo(HttpServletRequest request) {
	
		String licensePlate = request.getParameter("licensePlat");
		if (StrKit.isBlank(licensePlate)) {
			return RetKit.fail("licensePlate不能为空");
		}
		Integer statue = Integer.parseInt(request.getParameter("statue"));

		return userParkingService.findMonthlyCardAndParkingInfo(licensePlate,statue);
		
	}
	
	//月卡续费
	@RequestMapping("/renew")
	public RetKit renewMonthlyCard(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String monthlyCardId = request.getParameter("monthlyCardId");
		return userParkingService.renewMonthlyCard(userId,monthlyCardId);
	}

}
