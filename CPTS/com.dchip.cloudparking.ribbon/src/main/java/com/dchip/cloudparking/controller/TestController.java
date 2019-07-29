package com.dchip.cloudparking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.service.TestService;

@RestController
public class TestController {
	
	   @Autowired
	   TestService test;

	   @RequestMapping("/test")
	   public String test(@RequestParam(value = "name", defaultValue = "hh") String name) {
	       return test.test( name);
	   }
	   
	   @RequestMapping("/webTest")
	   public String test() {
	       return test.webTest();
	   }
}
