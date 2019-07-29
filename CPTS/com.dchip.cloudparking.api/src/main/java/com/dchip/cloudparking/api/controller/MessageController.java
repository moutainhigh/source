package com.dchip.cloudparking.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.IMessageService;
import com.dchip.cloudparking.api.utils.RetKit;
/**
 * 消息
 * @author 鼎芯科技
 *
 */
@RestController
@RequestMapping("/message")
public class MessageController {
	@Autowired
	private IMessageService iMessageService;
	/**
	 * 获取消息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMessage")
	public RetKit getMessage(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));
		return iMessageService.getMessage(userId, pageSize, pageNum); 
	}
	
	/**
	 * 改变消息状态
	 * @param request
	 * @return
	 */
	@RequestMapping("/changeToRead")
	public RetKit changeToRead(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String messageId = request.getParameter("messageId");
		return iMessageService.changeToRead(userId,messageId); 
	}
}
