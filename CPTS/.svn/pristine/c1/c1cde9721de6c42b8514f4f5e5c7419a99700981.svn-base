package com.dchip.cloudparking.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.IHardwareService;
import com.dchip.cloudparking.api.utils.RetKit;

@RestController
@RequestMapping("/hardware")
public class HardwareController {

	@Autowired
	private IHardwareService hardwareService;
	
	@RequestMapping("/getCameraInfo")
	public RetKit getCameraInfo(HttpServletRequest request) {
		return hardwareService.getCameraInfo(request.getParameter("mac"));
	}
	
	@RequestMapping("/changeCarportNum")
	public RetKit changeCarportNum(HttpServletRequest request) {
		String mac = request.getParameter("mac");
		return hardwareService.changeCarportNum(mac); 
	}
	
	@RequestMapping("/changeStatus")
	public RetKit changeStatus(HttpServletRequest request) {
		String mac = request.getParameter("mac");
		String onLine = request.getParameter("onLine");
		return hardwareService.changeStatus(mac,onLine); 
	}

}
