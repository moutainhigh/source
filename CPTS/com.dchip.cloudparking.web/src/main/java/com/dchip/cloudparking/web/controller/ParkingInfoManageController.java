package com.dchip.cloudparking.web.controller;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iService.IParkingInfoManageService;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/parkingInfo")
public class ParkingInfoManageController{
	
	@Autowired
	private IParkingInfoManageService parkingInfoManageService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		return "parkingInfo/index";
	}
	
	@RequestMapping("/rendering")
	@ResponseBody
	public Object rendering(HttpServletRequest request) {
		//分页条件
		Integer pageSize = Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = Integer.parseInt(request.getParameter("page"))-1;
		//排序条件
		String sortName =  request.getParameter("sortName");
		String direction = request.getParameter("direction");
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
		
		return JSON.toJSON(parkingInfoManageService.getParkingInfoList(pageSize, pageNum, sortName, direction, para));
	}
	
	@RequestMapping("/getParkingInfoDetail")
	@ResponseBody
	public Object getDetail(HttpServletRequest request) {
		String licensePlate = request.getParameter("licensePlate");
		if (StrKit.isBlank(licensePlate)) {
			return RetKit.fail();
		}
		return parkingInfoManageService.getParkingInfoDetail(licensePlate);
	}


	/**
	 * 获取出口车道名称
	 * ZYY
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRoadName")
	@ResponseBody
	public RetKit getOutRoadname(HttpServletRequest request) {
		String parkingId = request.getParameter("parkingId");
		if (StrKit.isBlank(parkingId)) {
			return RetKit.fail("parkingId不能为空");
		}
		return parkingInfoManageService.getOutRoadname(parkingId);
	}

	/**
	 * 获取收费模板列表
	 * hrd
	 * @return
	 */
	@RequestMapping("/getFreeStandardList")
	@ResponseBody
	public Object getFreeStandardList() {
		return parkingInfoManageService.getFreeStandardList();
	}

	/**
	 * 结算停车费用
	 * ZYY
	 * @param request
	 * @return
	 */
	@RequestMapping("/settleCost")
	@ResponseBody
	public RetKit manualSettlement(HttpServletRequest request) {
		String inTime = request.getParameter("intime");
		String outTime = request.getParameter("outtime");
		String licensePlate = request.getParameter("licensePlate");
		Integer parkingInfoId = Integer.parseInt(request.getParameter("parkingInfoId"));

		if (StrKit.isBlank(inTime)||StrKit.isBlank(outTime)) {
			return RetKit.fail("入场时间或者出场时间不能为空！");
		}
		if (StrKit.isBlank(licensePlate)) {
			return RetKit.fail("车牌号码不能为空！");
		}
		return parkingInfoManageService.manualSettlement(parkingInfoId,inTime,outTime,licensePlate);
	}

	/**
	 * 保存人工结算操作的数据
	 * ZYY
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveManualSettleAction")
	@ResponseBody
	public RetKit saveManualSettleAction(HttpServletRequest request) {
		String parkingInfoId = request.getParameter("parkingInfoId");
		String outTime = request.getParameter("outTime");
		String roadWayId = request.getParameter("roadWayId");
		String cost = request.getParameter("cost");
		if (StrKit.isBlank(parkingInfoId)) {
			return RetKit.fail("停车信息Id为空！");
		}
		if (StrKit.isBlank(outTime)) {
			return RetKit.fail("出场时间为空！");
		}
		if (StrKit.isBlank(roadWayId)) {
			return RetKit.fail("车道Id为空！");
		}
		return parkingInfoManageService.save(parkingInfoId,outTime,roadWayId,cost);
	}

}
