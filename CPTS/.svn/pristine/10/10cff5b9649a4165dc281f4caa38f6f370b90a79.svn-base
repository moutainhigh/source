package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.utils.RetKit;

public interface IParkingConcessionService {
	
	
	Object getParkingConcessionList(Integer pageSize, Integer pageNum, String sortName, String direction,List<Map<String, Object>> para);

	RetKit del(String parkingConcessionId);

	RetKit pass(String parkingConcessionId);
	RetKit notPass(String parkingConcessionId);
}
