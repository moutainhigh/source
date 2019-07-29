package com.dchip.cloudparking.wechat.iService;

import com.dchip.cloudparking.wechat.model.po.WechatConfig;

public interface IWechatConfigService {
	
	/**
	 * 保存微信配置
	 * @param wechatConfig
	 * @param access_token
	 * @param jsapi_ticket
	 */
	void saveWeChatConfig(WechatConfig wechatConfig, String access_token,String jsapi_ticket);
	
	/**
	 * 获取微信配置
	 * @return
	 */
	WechatConfig getWeChatConfig();
}
