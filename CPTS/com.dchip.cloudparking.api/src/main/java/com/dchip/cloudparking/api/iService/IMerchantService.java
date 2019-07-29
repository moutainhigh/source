package com.dchip.cloudparking.api.iService;

import com.dchip.cloudparking.api.utils.RetKit;

import javax.servlet.http.HttpServletRequest;

public interface IMerchantService {
	
	/**
	 * 判断是否有这个用户名
	 * @param LoginName
	 * @return
	 */
	Boolean hasLoginName(String LoginName);
	
	/**
	 * 校验是否用户名密码合法
	 * @return
	 */
	Boolean pwdIsCorrect(String loginName, String pwd);

	RetKit getParkingUser(HttpServletRequest request, String userName, String pwd);

	RetKit getParkingList(String userName);
	
}
