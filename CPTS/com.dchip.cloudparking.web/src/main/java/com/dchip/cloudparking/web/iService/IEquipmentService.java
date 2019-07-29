package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.utils.RetKit;

public interface IEquipmentService {

	public Map<String, Object> getEquipmentList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para);

	public RetKit delete(Integer mainControlId);
	
	RetKit getRoadways(String mainControlId);
	
	RetKit binding(String mainControlId , List<Object> roadwaysData, Integer parkingId, String actionType);

	public RetKit checkCameraMac(String cameraMac);

	RetKit getEntryRoadways(String mainControlId);

	RetKit getExitRoadways(String mainControlId);

}
