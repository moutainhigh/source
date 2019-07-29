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
import com.dchip.cloudparking.web.iService.IHelpCenterService;
import com.dchip.cloudparking.web.model.vo.HelpVo;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;

@Controller
@RequestMapping("/helpCenter")
public class HelpCenterController {
	
	@Autowired
	public IHelpCenterService helpCenterService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		return "helpCenter/index";
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
	
		List<Map<String, Object>> para = new ArrayList<>();
		if(StrKit.notBlank(searchLicensePlate)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchLicensePlate", searchLicensePlate);
			para.add(map);
		}
		return JSON.toJSON(helpCenterService.getHelpCenterList(pageSize,pageNum,sortName,direction,para));
	}
	
	public RetKit save(HelpVo helpVo) {
		return helpCenterService.save(helpVo);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public RetKit edit(HelpVo helpVo) {
		return save(helpVo);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public RetKit add(HelpVo helpVo) {
		return save(helpVo);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public RetKit delete(HttpServletRequest request) {
		Integer helpId = Integer.parseInt(request.getParameter("helpId"));
		return helpCenterService.del(helpId);
	}

}
