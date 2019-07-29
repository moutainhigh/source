package com.dchip.cloudparking.wechat.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.wechat.iRepository.IUserRepository;
import com.dchip.cloudparking.wechat.iService.IUserService;
import com.dchip.cloudparking.wechat.model.po.User;

@Service
public class UserServiceImp extends BaseService implements IUserService {

	@Autowired
	private IUserRepository userRepository;
	
	/**
	 * 查询openid是否存在
	 */
	@Override
	public Boolean selectOpenid(String openid) {
		String selOpenid = userRepository.findByOpenid(openid);
		if((selOpenid == null) || (selOpenid.length() == 0)) {
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * 根据openid，返回User对象
	 */
	@Override
	public User findUserByOpenid(String openid) {
		return  userRepository.findUserByOpenid(openid);
	}

	/**
	 * 保存用户信息
	 */
	public User saveUser(User user) {
		User newUser = new User();
		try {
			newUser = userRepository.save(user);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return newUser;
	}
}
