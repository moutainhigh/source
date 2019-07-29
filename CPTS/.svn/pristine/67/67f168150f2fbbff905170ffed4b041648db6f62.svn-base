package com.dchip.cloudparking.wechat.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dchip.cloudparking.wechat.iService.IParkingOperateService;
import com.dchip.cloudparking.wechat.iService.IRoadWayService;
import com.dchip.cloudparking.wechat.utils.RetKit;
import com.dchip.cloudparking.wechat.utils.StrKit;

@Controller
public class ParkingOperateController {
	
	@Autowired
	private IParkingOperateService parkingOperateService;
	static Log log = LogFactory.getLog(ParkingOperateController.class);
	@Autowired
	private IRoadWayService roadWayService;
	
	/**
	 * 有牌出场界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/haveACardPayPage")
	public String haveACardPayPage(HttpServletRequest request, Model model) {
		
		String parkingInfoIdStr = request.getParameter("parkingInfoId");
		String typeStr = request.getParameter("type");
		Integer parkingInfoId = Integer.parseInt(parkingInfoIdStr);
		Integer type = Integer.parseInt(typeStr);
		
		Map<String,Object> returnObj = parkingOperateService.haveACardPayPage(parkingInfoId, type);
		model.addAttribute("payFlag", returnObj.get("payFlag"));   
		model.addAttribute("licensePlate", returnObj.get("licensePlate"));
		model.addAttribute("outDate", returnObj.get("outDate"));
		model.addAttribute("totalFee", returnObj.get("totalFee"));
		model.addAttribute("parkingLen", returnObj.get("parkingLen"));
		model.addAttribute("showParkingLen", returnObj.get("showParkingLen"));
		model.addAttribute("companyName", returnObj.get("companyName"));
		return "haveCardPay"; 
	}
	
	/**
	 * 根据车牌结单
	 * @param request
	 * @return
	 */
	@RequestMapping("/openByLicensePlate")
	public String openByLicensePlate(HttpServletRequest request, Model model) {
		String parkingIdStr = request.getParameter("parkingId");
		String companyName = parkingOperateService.findCompanyNameByParkingId(parkingIdStr);
		model.addAttribute("companyName", companyName);
		return "openByLicensePlate";
	}
	
	/**
	 * 支付宝结账操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/alipayRechoningOpearte")
	@ResponseBody
	public RetKit alipayRechoningOpearte(HttpServletRequest request) {
		String parkingId  = request.getParameter("parkingId");
		String licensePlate = request.getParameter("licensePlate");
		return parkingOperateService.alipayRechoningOpearte(parkingId, licensePlate);
	}
	
	/**
	 * 微信结账操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/rechoningOpearte")
	@ResponseBody
	public RetKit rechoningOpearte(HttpServletRequest request) {
		String parkingId  = request.getParameter("parkingId");
		String licensePlate = request.getParameter("licensePlate");
		return parkingOperateService.rechoningOpearte(parkingId, licensePlate);
	}
	
	/**
	 * 无牌入场页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/unlicensedEntry")
	public String unlicensedEntry(HttpServletRequest request, Model model) {
		//String openid = request.getParameter("openid");
		String roadWayId = request.getParameter("roadWayId");
		Map<String,Object> returnObj = roadWayService.findRoadWay(roadWayId);
		model.addAttribute("freeTimeLength",returnObj.get("freeTimeLength"));
		model.addAttribute("hourCost", returnObj.get("hourCost"));
		model.addAttribute("parkDate", returnObj.get("parkDate"));
		model.addAttribute("companyName", returnObj.get("companyName"));
		model.addAttribute("parkName", returnObj.get("parkName"));
		model.addAttribute("parkinginfoId", returnObj.get("parkinginfoId"));
		model.addAttribute("roadWayId",roadWayId);
		return "unlicensedEntry";
	}
	
	@RequestMapping("/unlicensedOut")
	public String unlicensedOut(HttpServletRequest request, Model model) {
		String openid = request.getParameter("openid");
		String roadWayId = request.getParameter("roadWayId");
		Map<String,Object> returnObj = roadWayService.outAction(roadWayId,openid);
		model.addAttribute("fee", returnObj.get("fee"));
		model.addAttribute("parkingTime", returnObj.get("parkingTime"));
		model.addAttribute("outDate", returnObj.get("outDate"));
		model.addAttribute("parkDate", returnObj.get("parkDate"));
		model.addAttribute("parkName", returnObj.get("parkName"));
		model.addAttribute("companyName", returnObj.get("companyName"));
		model.addAttribute("parkingInfoId", returnObj.get("parkingInfoId"));
		model.addAttribute("payFlag", returnObj.get("payFlag"));
		model.addAttribute("openid",openid);
		return "unlicensedOut";
	}
	
}
