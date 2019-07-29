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
import com.dchip.cloudparking.web.iService.IUserService;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	/**
	 * 显示用户列表
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		return "user/index";
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
		String searchPhone = request.getParameter("searchPhone");
		String searchLicence = request.getParameter("searchLicence");
		String blackSelect = request.getParameter("blackSelect");

		List<Map<String, Object>> para = new ArrayList<>();
		if (StrKit.notBlank(searchPhone)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchPhone", searchPhone);
			para.add(map);
		}
		if (StrKit.notBlank(searchLicence)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchLicence", searchLicence);
			para.add(map);
		}
		if (StrKit.notBlank(blackSelect)) {
			Map<String, Object> map = new HashMap<>();
			map.put("blackSelect", blackSelect);
			para.add(map);
		}

		return JSON.toJSON(userService.getUserList(pageSize, pageNum, sortName, direction, para));
	}

	@RequestMapping("/getArrearagelist")
	@ResponseBody
	public RetKit getArrearagelist(HttpServletRequest request) {
		Integer userId = Integer.parseInt(request.getParameter("userId"));
		return userService.getArrearagelist(userId);
	}

	@RequestMapping("/changeUserStatus")
	@ResponseBody
	public RetKit changeUserStatus(HttpServletRequest request) {
		Integer userId = Integer.parseInt(request.getParameter("userId"));
		String state = request.getParameter("state");
		return userService.changeUserStatus(userId, state);
	}
	
	@RequestMapping("/getChartData")
	@ResponseBody
	public RetKit getChartData() {
		return userService.getChartData();
	}
}
