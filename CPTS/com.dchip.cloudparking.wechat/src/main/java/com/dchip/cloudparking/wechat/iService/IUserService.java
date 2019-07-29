package com.dchip.cloudparking.wechat.iService;

import com.dchip.cloudparking.wechat.model.po.User;

public interface IUserService {

	/**
	 * 查询openid是否存在
	 * @param openId
	 * @return
	 */
	Boolean selectOpenid(String openid);
	
	/**
	 * 根据openid，查询返回user对象
	 * @param openid
	 * @return
	 */
	User findUserByOpenid(String openid);
	
	/**
	 * 保存用户信息
	 * @param user
	 * @return
	 */
	User saveUser(User user);
}
