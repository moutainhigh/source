package com.dchip.cloudparking.web.controller;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.IActivityRepository;
import com.dchip.cloudparking.web.iService.IActivityManageService;
import com.dchip.cloudparking.web.model.po.Activity;
import com.dchip.cloudparking.web.model.vo.ActivityVo;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/activityManage")
public class ActivityManageController {
	
	@Autowired
	private IActivityManageService activityManageService;
	@Autowired
	private IActivityRepository activityRepository;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		return "activityManage/index";
	}
	
	@RequestMapping("/rendering")
	@ResponseBody
	public Object rendering(HttpServletRequest request) {
		Integer pageSize = Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = Integer.parseInt(request.getParameter("page"))-1;
		//排序条件
		String sortName =  request.getParameter("sortName");
		String direction = request.getParameter("direction");
		return JSON.toJSON(activityManageService.getActivityList(pageSize, pageNum, sortName, direction));
	}
	
	/**
	 * 检查角色名称
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkActivityName")
	@ResponseBody
	public RetKit checkActivityName(HttpServletRequest request) {
		String activityName = request.getParameter("activityName");
		return activityManageService.checkActivityName(activityName);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public RetKit add(ActivityVo activityVo){
		/*Activity byActivityRemark = activityManageService.findByActivityRemark(activityVo.getActivityRemark());*/
		Activity findByRemark = activityRepository.findStatusByRemark(activityVo.getActivityRemark());
		if(findByRemark != null){
			return RetKit.fail("活动备注已存在!");
		}
		return activityManageService.save(activityVo);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public RetKit edit(ActivityVo activityVo){
		return activityManageService.save(activityVo);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public RetKit delete(HttpServletRequest request){
		Integer activityId = Integer.parseInt(request.getParameter("activityId"));
//		return activityManageService.del(activityId);
		return activityManageService.softlyDel(activityId);
	}


	@RequestMapping("/record")
	public String record(HttpServletRequest request) {
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		return "activityManage/record";
	}
	
	@RequestMapping("/recordList")
	@ResponseBody
	public Object recordList(HttpServletRequest request) {
		Integer pageSize = Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = Integer.parseInt(request.getParameter("page"))-1;
		//排序条件
		String direction = request.getParameter("direction");
		String sortName =  request.getParameter("sortName");

		// 动态搜索参数
		String userName = request.getParameter("userName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");


		List<Map<String, Object>> para = new ArrayList<>();

		if (StrKit.notBlank(userName)) {
			Map<String, Object> map = new HashMap<>();
			map.put("userName", userName);
			para.add(map);
		}

		if (StrKit.notBlank(startTime)) {
			Map<String, Object> map = new HashMap<>();
			map.put("startTime", startTime);
			para.add(map);
		}

		if (StrKit.notBlank(endTime)) {
			Map<String, Object> map = new HashMap<>();
			map.put("endTime", endTime);
			para.add(map);
		}

		return JSON.toJSON(activityManageService.getRecordList(pageSize,pageNum,para, direction, sortName));
	}
}
