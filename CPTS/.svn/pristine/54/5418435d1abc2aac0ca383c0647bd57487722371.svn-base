package com.dchip.cloudparking.web.controller;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iService.ICompanyManageService;
import com.dchip.cloudparking.web.iService.IParkingService;
import com.dchip.cloudparking.web.iService.IParkingSpaceService;
import com.dchip.cloudparking.web.model.po.Company;
import com.dchip.cloudparking.web.model.po.Parking;
import com.dchip.cloudparking.web.model.vo.ParkingVo;
import com.dchip.cloudparking.web.model.vo.SettingRuleVo;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/parkingSpace")
public class ParkingSpaceController {
	
	@Autowired
	private IParkingSpaceService parkingSpaceService;
	@Autowired
	private ICompanyManageService companyManageService;
	@Autowired
	private IParkingService parkingService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		UserAuthentic loginUser = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Company> companyList = companyManageService.findAllCompany();
		List<Parking> pakingList = parkingService.getAllParkName();
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		request.setAttribute("companyList", companyList);
		request.setAttribute("pakingList", pakingList);
		request.setAttribute("roleType", loginUser.getRoleType());
		return "parkingSpace/index";
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
		String searchParking = request.getParameter("searchParking");
		String searchCompany = request.getParameter("searchCompany");
	
		List<Map<String, Object>> para = new ArrayList<>();
		if(StrKit.notBlank(searchParking)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchParking", searchParking);
			para.add(map);
		}
		if(StrKit.notBlank(searchCompany)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchCompany", searchCompany);
			para.add(map);
		}
		
		return JSON.toJSON(parkingSpaceService.getParkingSpaceList(pageSize, pageNum, sortName, direction, para));
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public RetKit save(ParkingVo parkingVo) {
		return parkingSpaceService.save(parkingVo);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public RetKit add(ParkingVo parkingVo) {
		return save(parkingVo);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public RetKit edit(ParkingVo parkingVo) {
		return save(parkingVo);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public RetKit delete(HttpServletRequest request) {
		Integer parkingId = Integer.parseInt(request.getParameter("parkingId"));
//		return parkingSpaceService.delete(parkingId);//硬删
		return parkingSpaceService.softlyDelete(parkingId);//软删
	}
	
	/**
	 * 禁用或启用
	 * @param parkingVo
	 * @return
	 */
	@RequestMapping("/changestatus")
	@ResponseBody
	public RetKit changeStatus(ParkingVo parkingVo) {
		return parkingSpaceService.changeStatus(parkingVo);
	}
	
	@RequestMapping("/getSettingRuleData")
	@ResponseBody
	public RetKit getSettingRuleData(HttpServletRequest request) {
		String parkingId = request.getParameter("parkingId");
		return parkingSpaceService.getSettingRuleData(parkingId);
	}
	
	@RequestMapping("/saveSettingRuleData")
	@ResponseBody
	public RetKit saveSettingRuleData(SettingRuleVo settingRuleVo) {
		return parkingSpaceService.saveSettingRuleData(settingRuleVo);
	}
	
	@RequestMapping("/checkRepeat")
	@ResponseBody
	public RetKit checkRepeat(HttpServletRequest request) {
		String companyId = request.getParameter("companyId");
		String parkSpaceName = request.getParameter("parkSpaceName");
		if (StrKit.isBlank(companyId)) {
			return RetKit.fail("公司ID不能为空！");
		}
		if (StrKit.isBlank(parkSpaceName)) {
			return RetKit.fail("停车场名称不能为空！");
		}
		return parkingSpaceService.checkRepeat(companyId,parkSpaceName);
	}
	
	@RequestMapping("/openGate")
	@ResponseBody
	public RetKit openGate(HttpServletRequest request) {
		String controlMac = request.getParameter("controlMac");
		String cameraMac = request.getParameter("cameraMac");
		if (StrKit.isBlank(controlMac)) {
			return RetKit.fail("主控板信息丢失,请重新绑定主控板");
		}
		if (StrKit.isBlank(cameraMac)) {
			return RetKit.fail("请先选择开闸车道");
		}
		return parkingSpaceService.openGate(controlMac, cameraMac);
	}
	
}
