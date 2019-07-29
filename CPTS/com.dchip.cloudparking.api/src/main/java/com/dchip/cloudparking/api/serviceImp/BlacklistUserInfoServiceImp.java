package com.dchip.cloudparking.api.serviceImp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.api.constant.BaseConstant.MessageType;
import com.dchip.cloudparking.api.constant.BaseConstant.UserIsBlack;
import com.dchip.cloudparking.api.iRepository.IMessageRepository;
import com.dchip.cloudparking.api.iRepository.IOrderRepository;
import com.dchip.cloudparking.api.iRepository.IUserRepository;
import com.dchip.cloudparking.api.iService.IBlacklistUserInfoService;
import com.dchip.cloudparking.api.model.po.Message;
import com.dchip.cloudparking.api.model.po.User;
import com.dchip.cloudparking.api.utils.MessageUtil;
import com.dchip.cloudparking.api.utils.RetKit;

@Service
public class BlacklistUserInfoServiceImp extends BaseService implements IBlacklistUserInfoService {
	
	@Autowired
	private IOrderRepository orderRepository;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IMessageRepository messageRepository;
	
	private static final Logger log = LoggerFactory.getLogger(BlacklistUserInfoServiceImp.class);

	@Override
	@Transactional
	public RetKit getBlacklistInfo(Integer userId) {
		List<Map<String,Object>> data = orderRepository.findBlackUserInfoByUserId(userId);
		if (data.size()==0) {
			return RetKit.ok("无数据");
		}
		return RetKit.okData(data);
	}
	
	/**
	 * 将用户改为黑名单
	 */
	@Override
	public RetKit changeToBlackUser(Integer userId) {
		if (userId == null) {
			return RetKit.fail("userId is not null");
		}
		User user = userRepository.findUserById(userId);
		user.setIsBlack(UserIsBlack.BlacklistUser.getTypeValue());//黑名单用户
		userRepository.save(user);
		Message message = new Message();
		String uId = String.valueOf(userId);
		message.setTitle(MessageUtil.loadMessage("parking.black.user.notice"));
		message.setContent(MessageUtil.loadMessage("parking.black.user.messages"));
		message.setType(MessageType.Specify.getTypeValue());//指定用户
		message.setTarget(uId);
		message.setCreateTime(new Date());
		messageRepository.save(message);
		return RetKit.ok();
	}

}
