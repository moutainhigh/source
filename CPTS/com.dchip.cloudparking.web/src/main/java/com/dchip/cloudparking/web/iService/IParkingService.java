package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.model.po.Parking;
import com.dchip.cloudparking.web.utils.RetKit;

public interface IParkingService {

	List<Map<String, Object>> getAllParkingCoordinate();
	
	RetKit getChartData();
	
	List<Parking> getAllParking();

	List<Parking> getAllParkName();
}
