package com.dchip.cloudparking.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/cloudParkingWeb")
public class WebConsumeController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/login")
	public String sendVerificationCode(HttpServletRequest request) {
	    return restTemplate.getForObject("http://web/cloudParkingWeb/login",String.class);
	}
}
