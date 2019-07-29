package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.model.po.FreeStandard;
import com.dchip.cloudparking.web.utils.RetKit;

public interface IParkingInfoManageService {

	Object getParkingInfoList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para);

	Object getParkingInfoDetail(String licensePlate);

	RetKit getOutRoadname(String parkingId);

	RetKit manualSettlement(Integer parkingInfoId,String inTime, String outTime, String licensePlate);

	RetKit getFreeStandardList();

	FreeStandard getFreeStandardById(String id);

	RetKit save(String parkingInfoId, String outTime, String roadWayId, String cost);

}
