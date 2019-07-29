package com.dchip.cloudparking.wechat.serviceImp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.wechat.iRepository.IWechatConfigRepository;
import com.dchip.cloudparking.wechat.iService.IWechatConfigService;
import com.dchip.cloudparking.wechat.model.po.WechatConfig;

@Service
public class WechatConfigServiceImp extends BaseService implements IWechatConfigService {

	@Autowired
	private IWechatConfigRepository wechatConfigRepository;
	
	/**
	 * 保存微信配置信息
	 */
	public void saveWeChatConfig(WechatConfig wechatConfig, String access_token,String jsapi_ticket) {
		String nowTimestamp = String.valueOf((System.currentTimeMillis() / 1000));//当前时间;
		
		wechatConfig.setAccessToken(access_token);
		wechatConfig.setJsapiTicket(jsapi_ticket);
		wechatConfig.setTimeStamp(nowTimestamp);
		wechatConfigRepository.save(wechatConfig);
	}
	
	/**
	 * 获取配置信息
	 */
	public WechatConfig getWeChatConfig(){
		WechatConfig wechatConfig = new WechatConfig();
		Optional<WechatConfig> wechatConfigOpt = wechatConfigRepository.findById(1);
		if(wechatConfigOpt.isPresent()) {
			wechatConfig = wechatConfigOpt.get();
		}
		return wechatConfig;
	}
}
