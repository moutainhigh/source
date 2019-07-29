package com.dchip.cloudparking.api.iService;

import com.dchip.cloudparking.api.utils.RetKit;

public interface IMainControlService {

	/**
	 * 上传主控板
	 * @param mac 
	 * @param networkType   1-wifi 2-2g 3-3g 4-4g 5-有线
	 * @param type 类型   100
	 * @return
	 */
	RetKit uploadMainControl(String mac,String networkType,String type);
	
	/**
	 * 获取最新的版本
	 */
	RetKit latestVersion(String type);
	/**
	 * 上传主控板版本
	 */
	RetKit uploadMainControlVersion(String mac,String version,String type);
}
