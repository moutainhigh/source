package com.dchip.cloudparking.wechat.iService;

import java.util.Map;

import com.dchip.cloudparking.wechat.pay.rep.UnifiedOrderParams;
import com.dchip.cloudparking.wechat.utils.RetKit;

public interface IPayService {

	RetKit weixinPay(UnifiedOrderParams payParam);
	
	RetKit writePayResult(Integer outTradeId);
	
	RetKit openBrake(String parkingInfoId);
	
	/**
	 * 根据微信支付订单id返回指定orderId
	 * @param outTradeNo
	 * @return
	 */
	Integer findOutTradeId(String outTradeNo);
	
	/**
	 * 根据微信支付订单id删除指定order
	 * @param outTradeNo
	 * @return
	 */
	RetKit deleteOrderByOutTradeNo(String outTradeNo);

	RetKit payment(Map<String, String> map, String notifyUrl);

	RetKit unlicensedEntryOpenBrake(String licensePlate,String roadWayId);
}
