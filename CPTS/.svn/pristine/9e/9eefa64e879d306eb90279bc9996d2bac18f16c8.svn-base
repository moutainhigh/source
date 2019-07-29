package com.dchip.cloudparking.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dchip.cloudparking.web.model.po.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iService.ICompanyManageService;
import com.dchip.cloudparking.web.model.vo.CompanyVo;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;

@Controller
@RequestMapping("/companyManage")
public class CompanyManageController {
	@Autowired
	private ICompanyManageService companyManageService;

	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		return "company/index";
	}

	@RequestMapping("/rendering")
	@ResponseBody
	public Object rendering(HttpServletRequest request) {
		// 分页条件
		Integer pageSize = Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = Integer.parseInt(request.getParameter("page")) - 1;
		// 排序条件
		String sortName = request.getParameter("sortName");
		String direction = request.getParameter("direction");
		// 动态搜索参数
		String searchCompanyName = request.getParameter("searchCompanyName");

		List<Map<String, Object>> para = new ArrayList<>();
		if (StrKit.notBlank(searchCompanyName)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchCompanyName", searchCompanyName);
			para.add(map);
		}

		return JSON.toJSON(companyManageService.getCompanyManageList(pageSize, pageNum, sortName, direction, para));
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public RetKit delete(HttpServletRequest request) {
		Integer companyId = Integer.parseInt(request.getParameter("companyId"));
		Integer companyBalanceWayId = Integer.parseInt(request.getParameter("companyBalanceWayId"));
		return companyManageService.delete(companyId,companyBalanceWayId);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public RetKit edit(CompanyVo companyVo) {
		return companyManageService.save(companyVo);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public RetKit add(CompanyVo companyVo) {
		Company company = companyManageService.findByName(companyVo.getCompanyName());
		if(company != null){
			return RetKit.fail("该公司已存在");
		}
		return companyManageService.save(companyVo);
	}
	
	@RequestMapping("/getAllCompany")
	@ResponseBody
	public Object getAllCompany() {
		return JSON.toJSON(companyManageService.findAllCompany());
	}

}
