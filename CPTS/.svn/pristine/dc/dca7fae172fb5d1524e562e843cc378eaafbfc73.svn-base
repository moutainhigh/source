package com.dchip.cloudparking.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.IHelpService;
import com.dchip.cloudparking.api.utils.RetKit;


@RestController
@RequestMapping("/help")
public class HelpController {
	@Autowired
	private IHelpService helpService;
	
	@RequestMapping("/getHelp")	
	public RetKit getHelp(HttpServletRequest request) {
		return helpService.getHelp();
		
	}
	
	@RequestMapping("/aboutPoint")	
	public RetKit aboutPoint() {
		return helpService.aboutPoint();
		
	}

}
