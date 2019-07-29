package com.dchip.cloudparking.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iService.IFinanceService;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;

@Controller
@RequestMapping("/finance")
public class FinanceController {

	@Autowired
	private IFinanceService financeService;

	/**
	 * 显示财务列表
	 */
	@RequestMapping("/index")
	public String index() {
		return "finance/index";
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
		String searchParking = request.getParameter("searchParking");
		String searchCompany = request.getParameter("searchCompany");
		String financeType = request.getParameter("financeType");

		List<Map<String, Object>> para = new ArrayList<>();
		if (StrKit.notBlank(financeType)) {
			Map<String, Object> map = new HashMap<>();
			map.put("financeType", financeType);
			para.add(map);
		}
		if (StrKit.notBlank(searchParking)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchParking", searchParking);
			para.add(map);
		}
		if (StrKit.notBlank(searchCompany)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchCompany", searchCompany);
			para.add(map);
		}

		return JSON.toJSON(financeService.getFinanceList(pageSize, pageNum, sortName, direction, para));
	}

	@RequestMapping("/financeExcel")
	@ResponseBody
	public Integer financeExcel(HttpServletRequest request, HttpServletResponse response) {
		String searchParking = request.getParameter("searchParking");
		String searchCompany = request.getParameter("searchCompany");
		String financeType = request.getParameter("financeType");

		List<Map<String, Object>> para = new ArrayList<>();
		if (StrKit.notBlank(financeType)) {
			Map<String, Object> map = new HashMap<>();
			map.put("financeType", financeType);
			para.add(map);
		}
		if (StrKit.notBlank(searchParking)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchParking", searchParking);
			para.add(map);
		}
		if (StrKit.notBlank(searchCompany)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchCompany", searchCompany);
			para.add(map);
		}
		financeService.financeExcel(para, request, response);
		return null;
	}

	@RequestMapping("/changeFinanceStatus")
	@ResponseBody
	public RetKit changeFinanceStatus(HttpServletRequest request) {
		Integer financeId = Integer.parseInt(request.getParameter("financeId"));
		String name = request.getParameter("name");
		String person = request.getParameter("person");
		String account = request.getParameter("account");
		return financeService.changeFinanceStatus(financeId, name, person, account);
	}

	@RequestMapping("/clearingLogExport")
	@ResponseBody
	public Integer clearingLogExport(HttpServletRequest request, HttpServletResponse response) {
		String searchParking = request.getParameter("searchParking");
		String clearingLogChargeTimeAfter = request.getParameter("clearingLogChargeTimeAfter");
		String clearingLogChargeTimeBefore = request.getParameter("clearingLogChargeTimeBefore");
		String financeType = request.getParameter("financeType");

		List<Map<String, Object>> para = new ArrayList<>();
		if (StrKit.notBlank(financeType)) {
			Map<String, Object> map = new HashMap<>();
			map.put("financeType", financeType);
			para.add(map);
		}
		if (StrKit.notBlank(searchParking)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchParking", searchParking);
			para.add(map);
		}
		if (StrKit.notBlank(clearingLogChargeTimeAfter)) {
			Map<String, Object> map = new HashMap<>();
			map.put("clearingLogChargeTimeAfter", clearingLogChargeTimeAfter);
			para.add(map);
		}
		if (StrKit.notBlank(clearingLogChargeTimeBefore)) {
			Map<String, Object> map = new HashMap<>();
			map.put("clearingLogChargeTimeBefore", clearingLogChargeTimeBefore);
			para.add(map);
		}
		financeService.clearingLogExport(para, request, response);
		return null;
	}

	/**
	 * 显示结算日志
	 */
	@RequestMapping("/clearingLog")
	public String clearingLog(HttpServletRequest request) {
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		return "finance/clearingLog";
	}

	@RequestMapping("/rendClearingLog")
	@ResponseBody
	public Object rendClearingLog(HttpServletRequest request) {

		// 分页条件
		Integer pageSize = Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = Integer.parseInt(request.getParameter("page")) - 1;

		// 排序条件
		String sortName = request.getParameter("sortName");
		String direction = request.getParameter("direction");

		// 动态搜索参数
		String searchParking = request.getParameter("searchParking");
		String clearingLogChargeTimeAfter = request.getParameter("clearingLogChargeTimeAfter");
		String clearingLogChargeTimeBefore = request.getParameter("clearingLogChargeTimeBefore");
		String financeType = request.getParameter("financeType");

		List<Map<String, Object>> para = new ArrayList<>();
		if (StrKit.notBlank(financeType)) {
			Map<String, Object> map = new HashMap<>();
			map.put("financeType", financeType);
			para.add(map);
		}
		if (StrKit.notBlank(searchParking)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchParking", searchParking);
			para.add(map);
		}
		if (StrKit.notBlank(clearingLogChargeTimeAfter)) {
			Map<String, Object> map = new HashMap<>();
			map.put("clearingLogChargeTimeAfter", clearingLogChargeTimeAfter);
			para.add(map);
		}
		if (StrKit.notBlank(clearingLogChargeTimeBefore)) {
			Map<String, Object> map = new HashMap<>();
			map.put("clearingLogChargeTimeBefore", clearingLogChargeTimeBefore);
			para.add(map);
		}

		return JSON.toJSON(financeService.getClearingLogList(pageSize, pageNum, sortName, direction, para));
	}

}
