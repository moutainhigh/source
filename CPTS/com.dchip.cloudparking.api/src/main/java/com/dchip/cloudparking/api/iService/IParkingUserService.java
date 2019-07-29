package com.dchip.cloudparking.api.iService;

import com.dchip.cloudparking.api.model.po.ParkingUser;
import com.dchip.cloudparking.api.utils.RetKit;

import java.util.List;
import java.util.Map;

public interface IParkingUserService {
	
	public Object getParkingUserList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para);

	public RetKit save(ParkingUser accountVo);
	
	public RetKit changeParkingUserStatus(Integer accountId, Integer accountStatus);
	
	public RetKit del(Integer accountId);
	
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
	
	/**
	 * 修改密码
	 * @param sysOldPWInp
	 * @param sysNewPWInp
	 * @return
	 */
	RetKit updatePassWord(String sysOldPWInp, String sysNewPWInp);
	
	/**
	 * 根据id返回登录用户信息
	 * @param aId
	 * @return
	 */
	ParkingUser getParkingUserById(Integer aId);

    RetKit softlyDel(Integer accountId);
    
    /**
	 * 检查管理员名称是否重复
	 * @param accountName
	 * @return
	 */
	RetKit checkParkingUserName(String accountName);
}
