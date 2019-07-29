package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.model.po.Account;
import com.dchip.cloudparking.web.model.vo.AccountVo;
import com.dchip.cloudparking.web.utils.RetKit;

public interface IAccountService {
	
	public Object getAccountList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para);

	public RetKit save(AccountVo accountVo);
	
	public RetKit changeAccountStatus(Integer accountId, Integer accountStatus);
	
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
	Account getAccountById(Integer aId);

    RetKit softlyDel(Integer accountId,String userName,Integer accountType);
    
    /**
	 * 检查管理员名称是否重复
	 * @param roleName
	 * @return
	 */
	RetKit checkAccountName(String accountName);

	/**
	 * 给用户重置密码
	 * @param accountId
	 * @return
	 */
	RetKit reSetCode(Integer accountId);

	RetKit checkParkingUserName(Integer roleType, String parkingUserName);
}
