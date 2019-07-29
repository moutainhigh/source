package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.utils.RetKit;

public interface IParkingWhiteListService {
	
	Object getFreeParkingLicensePlateList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para);

	RetKit add(String parkingId,String licensePlate);

	Object getParkName();

	RetKit delete(Integer secondWhiteListId);

	RetKit edit(Integer parkingId, String licensePlate,Integer secondWhiteListId);

	RetKit changestatus(Integer secondWhiteListId, Integer status);

	RetKit checkLicensePlate(String licensePlate, String parkingId);

}
