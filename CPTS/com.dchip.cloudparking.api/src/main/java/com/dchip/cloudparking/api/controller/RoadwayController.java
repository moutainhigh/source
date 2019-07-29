package com.dchip.cloudparking.api.controller;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.IRoadwayService;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;

@RestController
@RequestMapping("/roadway")
public class RoadwayController {
	@Autowired
	private IRoadwayService roadwayService;
	
	/**
	 * 根据车道mac(相机mac)修改在线状态
	 * @param request
	 * @return
	 */
	@RequestMapping("/changeRoadwayOnline")
	public RetKit changeRoadwayOnline(HttpServletRequest request) {
		String cameraMac = request.getParameter("cameraMac");
		String online = request.getParameter("online");   //0-离线   1-在线
		
		if (StrKit.isBlank(cameraMac)) {
			return RetKit.fail("cameraMac不能为空");
		}
		if (StrKit.isBlank(online)) {
			return RetKit.fail("online不能为空");
		}
		return roadwayService.changeRoadwayOnline(cameraMac, online);
	}
	
	/**
	 * 返回系统当前时间
	 * @return
	 */
	@RequestMapping("/getServerTime")
	public RetKit getServerTime() {
		Date nowDate = new Date();
		return RetKit.okData(nowDate.getTime()/1000);		
	}
	
	/**
	 * 获取出入口车道信息
	 * @author ZYY
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRoadWayInfo")
	public RetKit getRoadWayInfo(HttpServletRequest request) {
		String parkingId = request.getParameter("parkingId");
		if (StrKit.isBlank(parkingId)) {
			return RetKit.fail("parkingId不能为空！");
		}
		return roadwayService.getRoadWayInfo(parkingId);
	}

}
