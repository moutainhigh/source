package com.dchip.cloudparking.web.controller;

import java.util.ArrayList;
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
import com.dchip.cloudparking.web.iService.IMemberRuleService;
import com.dchip.cloudparking.web.utils.RetKit;

@Controller
@RequestMapping("/memberRule")
public class MemberRuleController {
	
	@Autowired
	private IMemberRuleService memberRuleService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {	
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		return "memberRule/index";
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
	
		List<Map<String, Object>> para = new ArrayList<>();
		
		return JSON.toJSON(memberRuleService.getMemberRuleList(pageSize, pageNum, sortName, direction, para));
	}
	
	/**
	 * 保存会员规则
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public RetKit edit(HttpServletRequest request) {
		Integer mrId = Integer.parseInt(request.getParameter("mrId"));
		String moneyInp = request.getParameter("moneyInp");
		String gradeDescriptionInp = request.getParameter("gradeDescriptionInp");
		String maximumTimesInp = request.getParameter("maximumTimesInp");
		String maximumArrearsInp = request.getParameter("maximumArrearsInp");
		return memberRuleService.save(mrId, moneyInp, gradeDescriptionInp, maximumTimesInp, maximumArrearsInp);
	}
}
