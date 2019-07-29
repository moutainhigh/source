package com.dchip.cloudparking.api.iService;

import com.dchip.cloudparking.api.utils.RetKit;

public interface IBlacklistUserInfoService {
	
	public RetKit getBlacklistInfo(Integer userId);

	public RetKit changeToBlackUser(Integer userId);
	
}
