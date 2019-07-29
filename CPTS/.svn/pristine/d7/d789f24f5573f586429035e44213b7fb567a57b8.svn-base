package com.dchip.cloudparking.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.IMainControlService;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;

@RestController
@RequestMapping("/mainControl")
public class MainController {
	@Autowired
	private IMainControlService mainControlService;
	
	@RequestMapping("/uploadMainControl")
	public RetKit uploadMainControl(HttpServletRequest request) {
		String mac = request.getParameter("mac");
		String networkType = request.getParameter("networkType");
		String type =request.getParameter("type");
		if (StrKit.isBlank(mac)) {
			return RetKit.fail("Mac不能为空");
		}
		if (StrKit.isBlank(networkType)) {
			return RetKit.fail("networkType不能为空");
		}
		if (StrKit.isBlank(type)) {
			return RetKit.fail("type不能为空");
		}
		return mainControlService.uploadMainControl(mac, networkType,type);
	}
	
	/**
	 * 获取某类型主控版最新版本号
	 * sjh
	 */
	@RequestMapping("/latestVersion")
	public RetKit latestVersion(HttpServletRequest request) {
		String type =request.getParameter("type");
		
		return mainControlService.latestVersion(type);
	}
	
	/**
	 * 上传主控板版本号
	 * sjh
	 */
	@RequestMapping("/uploadMainControlVersion")
	public RetKit uploadMainControlVersion(HttpServletRequest request) {
		String mac = request.getParameter("mac");
		String version =request.getParameter("version");
		String type =request.getParameter("type");
		
		return mainControlService.uploadMainControlVersion(mac, version,type);
	}
}
