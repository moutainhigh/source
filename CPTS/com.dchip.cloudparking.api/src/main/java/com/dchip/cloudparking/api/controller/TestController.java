package com.dchip.cloudparking.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.ITestService;

@RestController
public class TestController {

	@Autowired
	private ITestService iTestService;
	
	@RequestMapping("/test")
	String test(@RequestParam String name) {
		return iTestService.test(name);
	}
}
