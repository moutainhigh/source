package com.dchip.cloudparking.api.iService;

import com.dchip.cloudparking.api.utils.RetKit;

public interface IHelpService {
	/**
	 * 获取帮助
	 * @return
	 */
	RetKit getHelp() ;
	/**
	 * 关于积分
	 * @return
	 */
	RetKit aboutPoint() ;
	

}
