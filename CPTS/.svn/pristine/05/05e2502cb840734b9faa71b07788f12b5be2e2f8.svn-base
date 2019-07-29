package com.dchip.cloudparking.wechat.pay.util;

import com.dchip.cloudparking.wechat.pay.rep.AbstractPayParams;
import com.dchip.cloudparking.wechat.utils.WeChatUtil;

/**
 * 默认请求消息处理类
 * 
 * @author phil
 * 
 */
public class MsgUtil {
	
	/**
	 * 支付参数
	 * @param params
	 * @return
	 */
	public static String abstractPayToXml(AbstractPayParams params) {
		String sign = SignatureUtil.createSign(params, WeChatUtil.APIKEY, null);
		params.setSign(sign);
		return XmlUtil.toSplitXml(params);
	}
}