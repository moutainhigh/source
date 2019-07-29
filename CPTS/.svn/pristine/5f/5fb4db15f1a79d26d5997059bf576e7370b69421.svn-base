package com.dchip.cloudparking.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.IAppointmentService;
import com.dchip.cloudparking.api.utils.RetKit;

/**
 * 预约功能
 * @author 鼎芯科技
 *
 */
@RestController
@RequestMapping("/appointment")
public class AppointmentController {
	
	@Autowired
	private IAppointmentService iAppointmentService;
	
	/**
	 * 提交预约
	 * @param request
	 * @return
	 */
	@RequestMapping("/submit")
	public RetKit appointmentSubmit(HttpServletRequest request) {		

		String userId = request.getParameter("userId");
		String parkingId = request.getParameter("parkingId");
		String date = request.getParameter("date");//预约入场的时间
		String licensePlat = request.getParameter("licensePlat");
		return iAppointmentService.appointmentSubmit(userId, parkingId, date, licensePlat);
	}
	
	/**
	 * 查询预约列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAppointments")
	public RetKit getAppointments(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		return iAppointmentService.getAppointments(userId);
	}
	
	/**
	 * 获取费用模板
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFee")
	public RetKit getFee(HttpServletRequest request) {
		String parkingId = request.getParameter("parkingId");
		String date = request.getParameter("date");
		return iAppointmentService.getFee(parkingId, date);
	}
	
	/**
	 * 取消预约
	 * @param request
	 * @return
	 */
	@RequestMapping("/cancel")
	public RetKit cancel(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		return iAppointmentService.cancel(userId);
	}
	
	/**
	 * 获取用户的预约信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getUserAppointmentInfo")
	public RetKit getUserAppointmentInfo(HttpServletRequest request) {
		Integer userId = Integer.parseInt(request.getParameter("userId"));
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));
		return iAppointmentService.getUserAppointmentInfo(userId, pageSize, pageNum);
	}

}
