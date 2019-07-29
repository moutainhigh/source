package com.dchip.cloudparking.web.controller;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iService.IParkingWhiteListService;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/parkingWhiteList")
public class ParkingWhiteListController {
	@Autowired
	private IParkingWhiteListService parkingWhiteListService;
	
	@RequestMapping("/index")
	public String index() {
		return "parkingWhiteList/index";
	}
	
	@RequestMapping("/rendTable")
	@ResponseBody
	public Object rendTable(HttpServletRequest request) {
		// 分页条件
		Integer pageSize = Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = Integer.parseInt(request.getParameter("page")) - 1;
		// 排序条件
		String sortName = request.getParameter("sortName");
		String direction = request.getParameter("direction");
		// 动态搜索参数
		String searchUserName = request.getParameter("searchUserName");
		List<Map<String, Object>> para = new ArrayList<>();
		if (StrKit.notBlank(searchUserName)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchUserName", searchUserName);
			para.add(map);
		}
		return JSON.toJSON(parkingWhiteListService.getFreeParkingLicensePlateList(pageSize, pageNum, sortName, direction, para));
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public RetKit add(HttpServletRequest request) {
		String parkingId = request.getParameter("parkingId");
		String licensePlate = request.getParameter("licensePlate");
		String pattern = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(licensePlate);
		if (!m.matches()) {
			return RetKit.fail("请输入正确的车牌号码");
		}
//		String pattern = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}(([A-HJ-Z]{1}[A-HJ-NP-Z0-9]{5})|([A-HJ-Z]{1}(([DF]{1}[A-HJ-NP-Z0-9]{1}[0-9]{4})|([0-9]{5}[DF]{1})))|([A-HJ-Z]{1}[A-D0-9]{1}[0-9]{3}警)))|([0-9]{6}使)|((([沪粤川云桂鄂陕蒙藏黑辽渝]{1}A)|鲁B|闽D|蒙E|蒙H)[0-9]{4}领)|(WJ[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼·•]{1}[0-9]{4}[TDSHBXJ0-9]{1})|([VKHBSLJNGCE]{1}[A-DJ-PR-TVY]{1}[0-9]{5})";
//		if (!Pattern.matches(pattern,licensePlate)) {
//			return RetKit.fail("请输入正确的车牌号码");
//		}
		return parkingWhiteListService.add(parkingId,licensePlate);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public RetKit delete(HttpServletRequest request) {
		Integer secondWhiteListId = Integer.parseInt(request.getParameter("secondWhiteListId"));
		return parkingWhiteListService.delete(secondWhiteListId);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public RetKit edit(HttpServletRequest request) {
		Integer parkingId = Integer.parseInt(request.getParameter("parkingId"));
		String licensePlate = request.getParameter("licensePlate");
		Integer secondWhiteListId = Integer.parseInt(request.getParameter("secondWhiteListId"));
		String pattern = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}(([A-HJ-Z]{1}[A-HJ-NP-Z0-9]{5})|([A-HJ-Z]{1}(([DF]{1}[A-HJ-NP-Z0-9]{1}[0-9]{4})|([0-9]{5}[DF]{1})))|([A-HJ-Z]{1}[A-D0-9]{1}[0-9]{3}警)))|([0-9]{6}使)|((([沪粤川云桂鄂陕蒙藏黑辽渝]{1}A)|鲁B|闽D|蒙E|蒙H)[0-9]{4}领)|(WJ[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼·•]{1}[0-9]{4}[TDSHBXJ0-9]{1})|([VKHBSLJNGCE]{1}[A-DJ-PR-TVY]{1}[0-9]{5})";
		if (!Pattern.matches(pattern,licensePlate)) {
			return RetKit.fail("请输入正确的车牌号码");
		}
		return parkingWhiteListService.edit(parkingId,licensePlate,secondWhiteListId);
	}
	
	@RequestMapping("/getParking")
	@ResponseBody
	public Object getParkName(){
		return parkingWhiteListService.getParkName();
	}
	
	@RequestMapping("/changestatus")
	@ResponseBody
	public RetKit changestatus(HttpServletRequest request) {
		Integer secondWhiteListId = Integer.parseInt(request.getParameter("secondWhiteListId"));
		Integer status = Integer.parseInt(request.getParameter("status"));
		return parkingWhiteListService.changestatus(secondWhiteListId,status);
	}

	@RequestMapping("checkRepeat")
	@ResponseBody
	public RetKit checkRepeat(HttpServletRequest request) {
		String parkingId = request.getParameter("parkingId");
		String licensePlate = request.getParameter("licensePlate");
		if (StrKit.isBlank(parkingId)) {
			return RetKit.fail("请选择停车场"); 
		}
		if (StrKit.isBlank(licensePlate)) {
			return RetKit.fail("车牌号码不能为空");
		}
		return parkingWhiteListService.checkLicensePlate(licensePlate,parkingId);
	}
}
