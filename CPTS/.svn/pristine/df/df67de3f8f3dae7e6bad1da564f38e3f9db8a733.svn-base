package com.dchip.cloudparking.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.utils.RetKit;
import com.dchip.cloudparking.utils.StrKit;

@RestController
@RequestMapping("/websocket")
public class WebSocketController {
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/socketController/sendMessage")
	public Object sendVerificationCode(HttpServletRequest request) {
		String controllMac = request.getParameter("controlMac");
		String cameraMac = request.getParameter("cameraMac");
		String command = request.getParameter("command");
		String OpenGate = "1"; //开闸指令
		if (StrKit.isBlank(controllMac)) {
			return RetKit.fail("主控板Mac不能为空");
		}

		if (StrKit.isBlank(command)) {
			return RetKit.fail("指令不能为空");
		}
		
		if(command.equals(OpenGate)) {
			if(StrKit.isBlank(cameraMac)) {
				return RetKit.fail("相机Mac不能为空");
			}
		}
		return JSON.parse(restTemplate.getForObject("http://socket/socketController/sendMessage?controlMac="
				+ controllMac + "&cameraMac=" + cameraMac + "&command=" + command, String.class));
	}
	
	@RequestMapping("/socketController/sendGroundLockMessage")
	public Object sendGroundLockMessage(HttpServletRequest request) {
		String mac = request.getParameter("mac");
		String groundUid = request.getParameter("groundUid");
		String type = request.getParameter("type");
		String groundCommond = request.getParameter("groundCommond");
		if (StrKit.isBlank(mac)) {
			return RetKit.fail("主控板Mac不能为空");   
		}
		if (StrKit.isBlank(groundUid)) {
			return RetKit.fail("地锁唯一识别码不能为空");
		}
		if (StrKit.isBlank(type)) {
			return RetKit.fail("type不能为空");
		}
		if (StrKit.isBlank(groundCommond)) {
			return RetKit.fail("groundCommond不能为空");
		}
		return JSON.parse(restTemplate.getForObject("http://socket/socketController/sendGroundLockMessage?mac="
				+ mac + "&groundUid=" + groundUid + "&type=" + type + "&groundCommond=" + groundCommond, String.class));
	}
	
}
