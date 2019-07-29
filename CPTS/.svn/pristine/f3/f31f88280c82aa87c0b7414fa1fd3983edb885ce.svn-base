package com.dchip.cloudparking.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.IParkingLotManageService;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;

/**
 * 车位管理
 * 2019/2/26
 * @author zyy
 */
@RestController
@RequestMapping("/parkingLotManage")
public class ParkingLotManageController {
	@Autowired
	private IParkingLotManageService parkingLotManageService;
	
	/**
	 * 保存设备信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveParkingLotInfo")
	public RetKit macBindGeomagnetism(HttpServletRequest request) {
		String mac = request.getParameter("mac");
		String uid = request.getParameter("uid");
		String version = request.getParameter("version");
		String onlineStr = request.getParameter("online");
		String initStateStr = request.getParameter("initState");
//		String pointX = request.getParameter("pointX");
//		String pointY = request.getParameter("pointY");
//		String pointZ = request.getParameter("pointZ");
		int online = 3;  //初始值 
		if(StrKit.notBlank(onlineStr)) {
			online = Integer.parseInt(onlineStr);
		}
		int initState = 0;//未初始化
		if(StrKit.notBlank(initStateStr)) {
			initState = Integer.parseInt(initStateStr);
		}
		if (StrKit.isBlank(mac)) {
			return RetKit.fail("mac不能为空");
		}
		if (StrKit.isBlank(uid)) {
			return RetKit.fail("uid不能为空");
		}
		if (StrKit.isBlank(version)) {
			return RetKit.fail("version不能为空");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("mac", mac);
		params.put("uid", uid);
		params.put("version", version);
		params.put("online", online);
		params.put("initState", initState);
		return parkingLotManageService.saveParkingLot(params);
	}
	
	/**
	 * 更改车位状态
	 * @param request
	 * @return
	 */
	@RequestMapping("/changeParkingLotStatus")
	public RetKit changeParkingLotStatus(HttpServletRequest request) {
		String uid = request.getParameter("uid");
		String status = request.getParameter("status");
		if (StrKit.isBlank(uid)) {
			return RetKit.fail("uid不能为空");
		}
		if (StrKit.isBlank(status)) {
			return RetKit.fail("status不能为空");
		}
		return parkingLotManageService.changeStatus(uid, status);
	}

}
