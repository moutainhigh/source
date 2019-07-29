package com.dchip.cloudparking.api.serviceImp;

import com.dchip.cloudparking.api.iRepository.IRoadwayRepository;
import com.dchip.cloudparking.api.iService.IRoadwayService;
import com.dchip.cloudparking.api.model.po.Roadway;
import com.dchip.cloudparking.api.utils.RetKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoadwayServiceImp extends BaseService implements IRoadwayService {

	@Autowired
	private IRoadwayRepository roadwayRepository;
	
	/**
	 * 根据车道mac(相机mac)修改在线状态
	 */
	public RetKit changeRoadwayOnline(String cameraMac, String online) {
		try {
			Roadway roadway  = roadwayRepository.findByCameraMac(cameraMac);
			roadway.setIsOnline(Integer.parseInt(online));
			roadwayRepository.save(roadway);
		} catch (Exception e) {
			return RetKit.fail();
		}
		
		return RetKit.ok();
	}

	/**
	 * @author zyy
	 * 获取出入口车道信息
	 */
	@Override
	public RetKit getRoadWayInfo(String parkingId) {
		//入口车道信息
		List<Map<String, Object>> inRoad = roadwayRepository.findInRoadInfo(parkingId);
		//出口车道信息
		List<Map<String, Object>> outRoad = roadwayRepository.findOutRoadInfo(parkingId);
		Map<String, Object> data = new HashMap<String,Object>();
		data.put("inRoad", inRoad);
		data.put("outRoad", outRoad);
		return RetKit.okData(data);
	}
}
