package com.dchip.cloudparking.api.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.IParkingInfoService;
import com.dchip.cloudparking.api.utils.MessageUtil;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;

/**
 * 停车信息
 * @author 鼎芯科技
 *
 */
@RestController
@RequestMapping("/parkingInfo")
public class ParkingInfoController {

	@Autowired
	private IParkingInfoService parkingInfoService;
	
	/**
	 * 显示用户停车信息，包括停车场名称、地址、车位区域、收费标准，当前产生的费用。
	 * by 小梁
	 */
	@RequestMapping("/getParkingInfoByUserId")
	public RetKit getParkingInfoByUserId(HttpServletRequest request) {
		try {
			Integer userId = Integer.valueOf(request.getParameter("userId"));
			return parkingInfoService.getParkingInfoByUserId(userId);
		} catch (Exception e) {
			return RetKit.fail();
		}
	}
	
	/**改变车辆状态 0-不锁车，1-锁车
	 * by zyy
	 * */
	@RequestMapping("/changeCarStatus")
	public RetKit changeCarStatus(HttpServletRequest req) {
		String islock = req.getParameter("isLock");
		String parkingInfoid = req.getParameter("parkingInfoId");
		if (StrKit.isBlank(islock)) {
			return RetKit.fail("isLock不能为空");
		}
		if (StrKit.isBlank(parkingInfoid)) {
			return RetKit.fail("parkingInfoId不能为空");
		}
		Integer isLock = Integer.parseInt(islock);
		Integer parkingInfoId = Integer.parseInt(parkingInfoid);
		return parkingInfoService.changeCarStatus(isLock,parkingInfoId);
	}
	
	/**
	 * 有牌车进出场
	 * @param req
	 * @return
	 */
	@RequestMapping("/parking")
	public RetKit parking(HttpServletRequest req) {
		//主板mac
		String mac = req.getParameter("mac");
		//车牌号
		String carNum = req.getParameter("carNum");
		//车牌类型
		String plateType = req.getParameter("plateType");
		//车辆类型
		String carType = req.getParameter("carType");
		//车照片
		String img = req.getParameter("imgUrl");
		//入场时间
		String dateStr = req.getParameter("inDate");
		if (StrKit.isBlank(mac)) {
			return RetKit.fail(MessageUtil.loadMessage("parking.param.error.null", "MAC"));
		}
		if (StrKit.isBlank(carNum)) {
			return RetKit.fail(MessageUtil.loadMessage("parking.param.error.null", "车牌号"));
		}
		if (StrKit.isBlank(dateStr)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateStr = sdf.format(new Date());
		}
		carNum = carNum.toUpperCase();//小写字母变大写
		return parkingInfoService.parking(mac, carNum, img, dateStr, plateType,carType );
	}
	
	/**
	 * 返回停车场车辆出入记录数据
	 * @author ZYY
	 * @param request
	 * @return
	 */
	@RequestMapping("/getParkingInfoByParkingId")
	public RetKit getAllParkingInfo(HttpServletRequest request) {
		String parkingId = request.getParameter("parkingId");
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		Integer pageNum = Integer.parseInt(request.getParameter("pageNum")) - 1;
		String licensePlate = request.getParameter("licensePlate");
		return parkingInfoService.getParkingInfoList(parkingId, pageSize, pageNum, licensePlate);
	}
	
	@RequestMapping("/getParkingInfoList")
	public RetKit getParkingInfoList(HttpServletRequest request) {
		//分页
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		Integer pageNum = Integer.parseInt(request.getParameter("pageNum")) - 1;
		Integer companyId = Integer.parseInt(request.getParameter("companyId"));
		//动态搜索参数
		String searchParkName = request.getParameter("searchParkName");
		String searchParkingLicensePlate = request.getParameter("searchParkingLicensePlate");
		String searchUserPhone = request.getParameter("searchUserPhone");
	
		List<Map<String, Object>> para = new ArrayList<>();
		if(StrKit.notBlank(searchParkName)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchParkName", searchParkName);
			para.add(map);
		}
		if(StrKit.notBlank(searchParkingLicensePlate)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchParkingLicensePlate", searchParkingLicensePlate);
			para.add(map);
		}
		if(StrKit.notBlank(searchUserPhone)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchUserPhone", searchUserPhone);
			para.add(map);
		}
		
		return parkingInfoService.getParkingInfoList(pageSize, pageNum, companyId, para);
	}
}