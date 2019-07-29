package com.dchip.cloudparking.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.ILicensePlatService;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;
/**
 * 车牌
 * @author 鼎芯科技
 *
 */
@RestController
@RequestMapping("/LicensePlat")
public class LicensePlatController {
	
	@Autowired
	private ILicensePlatService licensePlatService;
	
	/**
	 * 查询用户要注册的车牌是否已存在  
	 * by zyy
	 * @param req
	 * @return
	 */
	@RequestMapping("/validateLicensePlate")
	public RetKit validateLicensePlate(HttpServletRequest req) {
		String licensePlat = req.getParameter("licensePlat");
		if (StrKit.isBlank(licensePlat)) {
			return RetKit.fail("licensePlat不能为空");
		}
		return licensePlatService.validateLicensePlate(licensePlat);
	}
	
	@RequestMapping("/binding")
	public RetKit binding(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String plateLicense = request.getParameter("plateLicense");
		String realName = request.getParameter("realName");
		String drivingLicenseCode = request.getParameter("drivingLicenseCode");
		String idCard = request.getParameter("idCard");
		String driverLicenseCode = request.getParameter("driverLicenseCode");
		return licensePlatService.binding(userId,plateLicense, realName, drivingLicenseCode, idCard, driverLicenseCode);
	}
	
	@RequestMapping("/getBindingInfo")
	public RetKit getBindingInfo(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		return licensePlatService.getBindingInfo(userId);
	}
}
