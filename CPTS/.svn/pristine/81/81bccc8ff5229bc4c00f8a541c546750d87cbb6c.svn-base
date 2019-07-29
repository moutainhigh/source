package com.dchip.cloudparking.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.IParkingService;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;
/**
 * 停车
 * @author 鼎芯科技
 *
 */
@RestController
@RequestMapping("/parking")
public class ParkingController {

	@Autowired
	private IParkingService parkingService;
	
	/**
	 * 地图图钉： 点击图钉弹出显示停车场名称，地址，车位信息情况。可直接导航
	 * by 小梁
	 */
	@RequestMapping("/findParkingByParkingId")
	public RetKit findAllParking(HttpServletRequest request) {
		try {
			Integer parkingId = Integer.parseInt(request.getParameter("parkingId"));
			return parkingService.findParkingByParkingId(parkingId);
		} catch (Exception e) {
			return RetKit.fail();
		}
	}
	/**
	 * 扫码生成临时车牌
	 * sjh
	 */
	@RequestMapping("/scanToGeneratLicensePlate")
	public RetKit scanToGeneratLicensePlate() {
		return parkingService.scanToGeneratLicensePlate();
	}
	
	/**
	 * 扫码结算(无牌车)
	 * sjh
	 */
	@RequestMapping("/scanToSettlement")
	public RetKit scanToSettlement(HttpServletRequest req) {
		return parkingService.scanToSettlement(req);
	}
	
	/**
	 * 根据停车场ID查找出管理服务中心电话  zyy
	 * @param req
	 * @return
	 */
	@RequestMapping("/getManagerPhone")
	public RetKit findPhoneByParkingId(HttpServletRequest req) {
		if (StrKit.isBlank(req.getParameter("parkingId"))) {
			return RetKit.fail("parkingId不能为空");
		}
		Integer parkingId = Integer.parseInt(req.getParameter("parkingId"));
		return parkingService.findPhoneByParkingId(parkingId);
	}
	
	/**
	 * 查找附近的停车场
	 * @param request
	 * @return
	 */
	@RequestMapping("/findNeighborhoodParking")
	public RetKit findNeighborhoodParking(HttpServletRequest request) {
		Double longitude = new Double(0);
		Double latitude = new Double(0);
		Double distance = new Double(0);
		try {
			longitude = new Double(request.getParameter("longitude"));
			latitude = new Double(request.getParameter("latitude"));
			distance = new Double(request.getParameter("distance"));
		}catch (Exception e) {
			e.printStackTrace();
			return RetKit.fail("数据格式错误");
		}
		return parkingService.findneighborhoodParking(longitude, latitude, distance);
	}
	
	/**
	 * 按停车场名称模糊匹配
	 * @param request
	 * @return
	 */
	@RequestMapping("/search")
	public RetKit search(HttpServletRequest request) {
		String name = request.getParameter("name");
		return parkingService.search(name);
	}
	
	/**
	 * 定位停车场
	 * @param request
	 * @return
	 */
	@RequestMapping("/findParking")
	public RetKit findParking(HttpServletRequest request) {
		String parkingId = request.getParameter("parkingId");
		return parkingService.findParking(parkingId);
	}

}