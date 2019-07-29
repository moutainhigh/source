package com.dchip.cloudparking.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iService.ICloneCarService;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;

@Controller
@RequestMapping("/cloneCar")
public class CloneCarController {
	
	@Autowired
	private ICloneCarService cloneCarService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		return "cloneCar/index";
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
		String searchLicensePlate = request.getParameter("searchLicensePlate");
		String searchStartTime = request.getParameter("searchStartTime");
		String searchEndTime = request.getParameter("searchEndTime");
		String searchStatus = request.getParameter("searchStatus");

		List<Map<String, Object>> para = new ArrayList<>();
		if(StrKit.notBlank(searchLicensePlate)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchLicensePlate", searchLicensePlate);
			para.add(map);
		}
		if(StrKit.notBlank(searchStartTime) && StrKit.notBlank(searchEndTime)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchStartTime", searchStartTime);
			map.put("searchEndTime", searchEndTime);
			para.add(map);
		}
		if(StrKit.notBlank(searchStatus)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchStatus", searchStatus);
			para.add(map);
		}
		
		return JSON.toJSON(cloneCarService.getCloneCarList(pageSize, pageNum, sortName, direction, para));
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public RetKit delete(HttpServletRequest request) {
		Integer cloneCarId = Integer.parseInt(request.getParameter("cloneCarId"));
		return cloneCarService.delete(cloneCarId);
	}
	
	@RequestMapping("/getCloneCarDetail")
	@ResponseBody
	public RetKit findCloneCarDetailInfo(HttpServletRequest request) {
		String licensePlate = request.getParameter("licensePlate");
		return cloneCarService.findCloneCarDetailInfo(licensePlate);
	}
	
}
