package com.dchip.cloudparking.web.controller;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iService.IParkingConcessionService;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/parkingConcession")
public class ParkingConcessionController {
	
	@Autowired
	private IParkingConcessionService parkingConcesservice;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		return "parkingConcession/index";
	}
	
	@RequestMapping("/rendering")
	@ResponseBody
	public Object rendTable(HttpServletRequest request) {
		// 分页条件
		Integer pageSize = Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = Integer.parseInt(request.getParameter("page")) - 1;
		// 排序条件
		String sortName = request.getParameter("sortName");
		String direction = request.getParameter("direction");
		// 动态搜索参数
		String searchParking = request.getParameter("searchParking");

		List<Map<String, Object>> para = new ArrayList<>();
		if (StrKit.notBlank(searchParking)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchParking", searchParking);
			para.add(map);
		}
		return JSON.toJSON(parkingConcesservice.getParkingConcessionList(pageSize, pageNum, sortName, direction, para));
	}

	/**
	 * 审核通过
	 * hrd
	 * @param request
	 * @return
	 */
	@RequestMapping("/pass")
	@ResponseBody
	public RetKit pass(HttpServletRequest request) {
		String id = request.getParameter("parkingConcessionId");
		if (StrKit.isBlank(id)) {
			return RetKit.fail("ID为空");
		}
		return parkingConcesservice.pass(id);
	}

	/**
	 * 审核不通过
	 * hrd
	 * @param request
	 * @return
	 */
	@RequestMapping("/notPass")
	@ResponseBody
	public RetKit notPass(HttpServletRequest request) {
		String id = request.getParameter("parkingConcessionId");
		if (StrKit.isBlank(id)) {
			return RetKit.fail("ID为空");
		}
		return parkingConcesservice.notPass(id);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public RetKit delete(HttpServletRequest request) {
		String parkingConcessionId = request.getParameter("parkingConcessionId");
		if (StrKit.isBlank(parkingConcessionId)) {
			return RetKit.fail("ID为空");
		}
		return parkingConcesservice.del(parkingConcessionId);
	}
}
