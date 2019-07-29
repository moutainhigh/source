package com.dchip.cloudparking.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dchip.cloudparking.web.model.po.FirstWhiteList;
import com.dchip.cloudparking.web.model.po.LicensePlateName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iRepository.ILicensePlateNameRepository;
import com.dchip.cloudparking.web.iService.IFirstWhiteListService;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;

@Controller
@RequestMapping("/firstWhiteList")
public class FirstWhiteListController {
	
	@Autowired
	private IFirstWhiteListService firstWhiteListService;
	@Autowired
	private ILicensePlateNameRepository licensePlateNameRepository;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		List<LicensePlateName> licensePlateName = licensePlateNameRepository.findAll();
		List<LicensePlateName> licensePlateNameA = licensePlateNameRepository.findLicensePlateNameA();
		List<LicensePlateName> licensePlateNameB = licensePlateNameRepository.findLicensePlateNameB();
		List<LicensePlateName> licensePlateNameC = licensePlateNameRepository.findLicensePlateNameC();
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		request.setAttribute("licensePlateName", licensePlateName);
		request.setAttribute("licensePlateNameA", licensePlateNameA);
		request.setAttribute("licensePlateNameB", licensePlateNameB);
		request.setAttribute("licensePlateNameC", licensePlateNameC);
		return "firstWhiteList/index";
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
		String searchLicensePlateName = request.getParameter("searchLicensePlateName");
		List<Map<String, Object>> para = new ArrayList<>();
		if (StrKit.notBlank(searchLicensePlateName)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchLicensePlateName", searchLicensePlateName);
			para.add(map);
		}
		return JSON.toJSON(firstWhiteListService.getFirstWhiteList(pageSize, pageNum, sortName, direction, para));
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public RetKit addLicensePlate(HttpServletRequest request) {
		Integer firstWhiteListId = Integer.parseInt(request.getParameter("firstWhiteListId"));
		Integer licensePlateTypeId = Integer.parseInt(request.getParameter("licensePlateTypeId"));
		FirstWhiteList firstWhiteList = firstWhiteListService.findByLicensePlateId(licensePlateTypeId);
		if(firstWhiteList != null){
			return RetKit.fail("该白名单已存在");
		}
		return firstWhiteListService.save(firstWhiteListId, licensePlateTypeId);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public RetKit editLicensePlate(HttpServletRequest request) {
		Integer firstWhiteListId = Integer.parseInt(request.getParameter("firstWhiteListId"));
		Integer licensePlateTypeId = Integer.parseInt(request.getParameter("licensePlateTypeId"));
		Integer firstWhiteList = firstWhiteListService.findFirstWhiteList(licensePlateTypeId);
		if(firstWhiteList > 0){
			return RetKit.fail("该白名单已存在");
		}
		return firstWhiteListService.save(firstWhiteListId, licensePlateTypeId);
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public RetKit save(HttpServletRequest request) {
		Integer firstWhiteListId = Integer.parseInt(request.getParameter("firstWhiteListId"));
		Integer licensePlateTypeId = Integer.parseInt(request.getParameter("licensePlateTypeId"));
		return firstWhiteListService.save(firstWhiteListId, licensePlateTypeId);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public RetKit delete(HttpServletRequest request) {
		Integer firstWhiteListId = Integer.parseInt(request.getParameter("firstWhiteListId"));
		return firstWhiteListService.delete(firstWhiteListId);
	}

}
