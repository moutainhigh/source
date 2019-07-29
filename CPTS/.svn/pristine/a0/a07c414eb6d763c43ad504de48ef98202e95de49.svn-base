package com.dchip.cloudparking.wechat.iService;

import java.util.Map;

import com.dchip.cloudparking.wechat.model.po.Parking;
import com.dchip.cloudparking.wechat.utils.RetKit;

public interface IParkingOperateService {
	/**
	 * 有牌出场
	 * @param parkingInfoId
	 * @param type
	 * @return
	 */
	Map<String,Object> haveACardPayPage(Integer parkingInfoId, Integer type);
	
	/**
	 * 结账操作
	 * @param parkingId
	 * @param licensePlate
	 * @return
	 */
	RetKit rechoningOpearte(String parkingId, String licensePlate);
	
	/**
	 * 根据停车场id返回公司名称
	 * @param parkingId
	 * @return
	 */
	String findCompanyNameByParkingId(String parkingId);

	RetKit alipayRechoningOpearte(String parkingId, String licensePlate);
}
