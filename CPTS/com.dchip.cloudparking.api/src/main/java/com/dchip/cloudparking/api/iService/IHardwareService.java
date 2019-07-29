package com.dchip.cloudparking.api.iService;

import com.dchip.cloudparking.api.utils.RetKit;

public interface IHardwareService {

	/**
	 * 
	 * @param mac 主控板mac
	 * @return
	 */
	RetKit getCameraInfo(String mac);
	
	/**
	 * 改变车位数量
	 * @param mac 摄像机mac
	 * @return
	 */
	RetKit changeCarportNum(String mac);
	
	/**
	 * 改变在线离线状态
	 * @param mac 摄像机mac
	 * @return
	 */
	RetKit changeStatus(String mac,String onLine);
}
