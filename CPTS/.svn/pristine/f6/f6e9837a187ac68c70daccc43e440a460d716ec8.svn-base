package com.dchip.cloudparking.web.serviceImp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.iRepository.IUserRepository;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.utils.BeanKit;
import com.dchip.cloudparking.web.utils.MessageUtil;

/**
 * 实现security的用户详情接口
 * @author d-chip
 */
@Service(value="userDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		try {
			//返回实现了UserDetails接口的对象	
			return BeanKit.changeRecordToBean(userRepository.findUserAuthentic(name), UserAuthentic.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(MessageUtil.loadMessage("noUser", name));
		}
	}

}
