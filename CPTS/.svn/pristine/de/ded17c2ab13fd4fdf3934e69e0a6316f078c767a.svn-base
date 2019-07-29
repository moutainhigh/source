package com.dchip.cloudparking.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iService.IParkingService;
import com.dchip.cloudparking.web.utils.RetKit;

@RestController
@RequestMapping("parking")
public class ParkingController {
	@Autowired
	private IParkingService parkingService;
	
	@RequestMapping("/getChartData")
	public RetKit getChartData() {
		return parkingService.getChartData();
	}
	
	@RequestMapping("/getAllParking")
	public Object getAllParking() {
		return JSON.toJSON(parkingService.getAllParking());
	}
} 