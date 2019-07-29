package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.utils.RetKit;

public interface IUserService {
	
	/**
	 * 判断是否存在用户名
	 * @return
	 */
	Boolean hasUserName(String userName);
	
	
	/**
	 * 校验是否用户名密码合法
	 * @return
	 */
	Boolean pwdIsCorrect(String userName, String pwd);
	
	Object getUserList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para);
	
	RetKit getArrearagelist(Integer userId);
	
	RetKit getChartData();
	
	RetKit changeUserStatus(Integer userId, String state);
}
