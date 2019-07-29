package com.dchip.cloudparking.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iService.IDailyRecordService;
import com.dchip.cloudparking.web.utils.StrKit;

@Controller
@RequestMapping("/daliyRecord")
public class DailyRecordController {
	@Autowired IDailyRecordService dailyRecordService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		return "daliyRecord/index";
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
		String searchActType = request.getParameter("searchActType");
		String searchActUserName = request.getParameter("searchActUserName");

		List<Map<String, Object>> para = new ArrayList<>();
		if(StrKit.notBlank(searchActType)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchActType", searchActType);
			para.add(map);
		}
		if(StrKit.notBlank(searchActUserName)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchActUserName", searchActUserName);
			para.add(map);
		}
		return JSON.toJSON(dailyRecordService.getdailyRecordList(pageSize, pageNum, sortName, direction, para));
	}

}
