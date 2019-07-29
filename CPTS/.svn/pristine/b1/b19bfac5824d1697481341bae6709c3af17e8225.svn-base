package com.dchip.cloudparking.api.iService;

import com.dchip.cloudparking.api.utils.RetKit;

public interface ILicensePlatService {

	/**
	 * 通过用户输入的车牌号码查找该车牌是否已注册
	 * by zyy
	 * @param licensePlate
	 * @return
	 */
	public RetKit validateLicensePlate(String licensePlat);
	
	/**
	 * 
	 * @param userId  用户id 
	 * @param plateLicense 车牌号
	 * @param realName  真实姓名
	 * @param drivingLicenseCode 行驶证编号
	 * @param idCard  身份证号码
	 * @param driverLicenseCode 驾驶证编号
	 * @return
	 */
	RetKit binding(String userId,String plateLicense,String realName,String drivingLicenseCode,String idCard,String driverLicenseCode);
	
	/**
	 * 
	 * @param userId  用户id 
	 * @return
	 */
	RetKit getBindingInfo(String userId);
	
}
