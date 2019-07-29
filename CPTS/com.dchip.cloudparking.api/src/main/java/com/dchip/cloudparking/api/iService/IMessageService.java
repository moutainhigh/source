package com.dchip.cloudparking.api.iService;

import com.dchip.cloudparking.api.utils.RetKit;

public interface IMessageService {
	RetKit getMessage(String userId,Integer pageSize,Integer pageNum);
	
	RetKit changeToRead(String userId,String messageId);
}
