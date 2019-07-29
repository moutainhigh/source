package com.dchip.cloudparking.web.controller;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iService.ILoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/loginLog")
public class LoginLogController {
	@Autowired ILoginLogService loginLogService;
	
	@RequestMapping("/index")
	public String index() {		
		return "loginLog/index";
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
	
		return JSON.toJSON(loginLogService.getloginLogList(pageSize, pageNum, sortName, direction));
	}

}
