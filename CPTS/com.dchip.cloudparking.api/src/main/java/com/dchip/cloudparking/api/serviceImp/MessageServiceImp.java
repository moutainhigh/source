package com.dchip.cloudparking.api.serviceImp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.iRepository.IMessageRepository;
import com.dchip.cloudparking.api.iRepository.IUserMessageRepository;
import com.dchip.cloudparking.api.iService.IMessageService;
import com.dchip.cloudparking.api.model.po.UserMessage;
import com.dchip.cloudparking.api.utils.RetKit;

@Service
public class MessageServiceImp extends BaseService implements IMessageService,BaseConstant{

	@Autowired
	IMessageRepository messageRepository;
	@Autowired
	IUserMessageRepository userMessageRepository;
	
	@Override
	public RetKit getMessage(String userId,Integer pageSize,Integer pageNum) {
		Integer totalElements = messageRepository.findMessageCount(userId);
		if(totalElements == null) {
			totalElements = 0;
		}
		Integer totalPages = (int) Math.ceil(totalElements / pageSize);
		Map<String, Object> result = new HashMap<>();
		result.put("content", messageRepository.findMessage(userId, pageSize, pageNum*pageSize));
		result.put("totalElements", totalElements);
		result.put("totalPages", totalPages);
		return RetKit.okData(result);
	}

	@Override
	public RetKit changeToRead(String userId, String messageId) {
		UserMessage userMessage = userMessageRepository.findByUserId(Integer.parseInt(userId));
		if(userMessage == null) {
			userMessage = new UserMessage();
			userMessage.setUserId(Integer.parseInt(userId));
			userMessage.setHasRead(messageId);
		}else {
			StringBuilder hasRead = new StringBuilder(userMessage.getHasRead());
			hasRead = hasRead.append(","+messageId);
			userMessage.setHasRead(hasRead.toString());
		}
		userMessageRepository.save(userMessage);
		return RetKit.ok();
	}

}
