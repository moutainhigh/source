package com.dchip.cloudparking.socket.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.WebSocket;
import com.dchip.cloudparking.socket.constant.Constant;
import com.dchip.cloudparking.socket.utils.MessageUtil;
//import com.dchip.cloudparking.socket.controller.WebSocketController;
import com.dchip.cloudparking.socket.utils.RetKit;
import com.dchip.cloudparking.socket.utils.StrKit;

/**
 * 长连接发送信息
 */
@RestController
@RequestMapping("/socketController")
public class WebSocketController {
	
	static Log log = LogFactory.getLog(WebSocketController.class);
	
	/**
	 * 发送消息   目前包括开闸、绑定、更新主板
	 */
	@RequestMapping("/sendMessage")
	public RetKit sendMessage(HttpServletRequest request) {
		String controlMac = request.getParameter("controlMac");
		String cameraMac = request.getParameter("cameraMac");
		String command = request.getParameter("command");
		if(StrKit.isBlank(controlMac)) {
			return RetKit.fail(MessageUtil.loadMessage("param.null", "主板MAC"));
		}		
		
		if(StrKit.isBlank(command)) {
			return RetKit.fail(MessageUtil.loadMessage("param.null", "控制指令"));
		}
		
		if(command.equals(Constant.CommandType.OpenGate.getValue())) {
			if(StrKit.isBlank(cameraMac)) {
				return RetKit.fail(MessageUtil.loadMessage("param.null", "相机MAC"));
			}
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("controlMac", controlMac);
		param.put("cameraMac", cameraMac);
		param.put("command", command);
		return WebSocket.sendMsgToUser(controlMac,param);
	}
	
	@RequestMapping("/sendGroundLockMessage")
	public RetKit sendGroundLockMessage(HttpServletRequest request) {
		String mac = request.getParameter("mac");
		String groundUid = request.getParameter("groundUid");
		String type = request.getParameter("type");
		String groundCommond = request.getParameter("groundCommond");
		System.out.println("socket："+groundCommond);
		if(StrKit.isBlank(mac)) {
			return RetKit.fail(MessageUtil.loadMessage("param.null", "主板MAC"));
		}
		
		if(StrKit.isBlank(groundUid)) {
			return RetKit.fail(MessageUtil.loadMessage("param.null", "地锁唯一识别码"));
		}
		
		if(StrKit.isBlank(type)) {
			return RetKit.fail(MessageUtil.loadMessage("param.null", "开关类型"));
		}
		if (StrKit.isBlank(groundCommond)) {
			return RetKit.fail(MessageUtil.loadMessage("param.null", "指令"));
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("groundUid", groundUid);
		param.put("type", type);
		param.put("groundCommond", groundCommond);
		return WebSocket.sendGroundLockMsgToUser(mac, param);
	}
	

}
