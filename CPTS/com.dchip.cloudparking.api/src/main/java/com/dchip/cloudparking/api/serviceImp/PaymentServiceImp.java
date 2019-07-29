package com.dchip.cloudparking.api.serviceImp;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.dchip.cloudparking.api.iRepository.IRechargeLogRepository;
import com.dchip.cloudparking.api.iService.IPaymentService;
import com.dchip.cloudparking.api.model.po.RechargeLog;
import com.dchip.cloudparking.api.utils.PayUtil;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;
import com.jfinal.weixin.sdk.api.PaymentApi;
import com.jfinal.weixin.sdk.api.PaymentApi.TradeType;
import com.jfinal.weixin.sdk.kit.PaymentKit;
@Service
public class PaymentServiceImp extends BaseService implements IPaymentService {

	@Autowired
	private IRechargeLogRepository rechargeLogRepository;
	
	Log log  = LogFactory.getLog(PaymentServiceImp.class);

	@Override
	public RetKit recharge(Integer userId, Integer type, BigDecimal inMoney, BigDecimal totalAmount,
			Map<String, String> map) {
		try {
			// 保存充值记录
			RechargeLog rechargeLog = new RechargeLog();
			rechargeLog.setUserId(userId);
			rechargeLog.setInMoney(inMoney);
			rechargeLog.setDiscountMoney(totalAmount.subtract(inMoney));
			rechargeLog.setOutTradeNo(map.get("out_trade_no"));
			rechargeLog.setStatus(false);
			rechargeLog.setType(type); // 1-支付宝 2-微信
			rechargeLog.setRechargeDate(new Date());
			rechargeLogRepository.save(rechargeLog);

			Map<String, Object> data = new HashMap<String, Object>();
			// 生成返回二维码链接，微信 -支付宝
			data.put("content", "云停车充值");
			data.put("out_trade_no", map.get("out_trade_no"));
			data.put("cost", inMoney);
			data.put("pay", true);

			if (type == 1) {
				// 支付宝
				data.put("ali_sign", aliPay(map));
			} else if (type == 2) {
				// 微信
				RetKit wxRet = weixinPay(map);
				data.put("wx_appid", wxRet.getStr("wx_appid"));
				data.put("wx_mch_id", wxRet.getStr("wx_mch_id"));
				data.put("wx_nonce_str", wxRet.getStr("wx_nonce_str"));
				data.put("wx_sign", wxRet.getStr("wx_sign"));
				data.put("wx_prepay_id", wxRet.getStr("wx_prepay_id"));
				data.put("timestamp", wxRet.getStr("timestamp"));
			}

			return RetKit.okData(data);
		} catch (Exception e) {
			return RetKit.fail();
		}
	}

	// 支付宝充值
	public String aliPay(Map<String, String> map) {
		AlipayClient alipayClient = new DefaultAlipayClient(PayUtil.allipay_url, PayUtil.alipay_appId,
				PayUtil.alipay_private_key, PayUtil.allipay_return_type, PayUtil.charset, PayUtil.alipay_public_key,
				"RSA2");

		Double total_amount = Double.valueOf(map.get("total_amount"));
		String subject = map.get("body");
		String out_trade_no = map.get("out_trade_no");

		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setPassbackParams(URLEncoder.encode(subject));
		; // 描述信息 添加附加数据
		model.setSubject(subject); // 商品标题
		model.setOutTradeNo(out_trade_no); // 商家订单编号
		model.setTimeoutExpress("30m"); // 超时关闭该订单时间
		model.setTotalAmount(total_amount + ""); // 订单总金额
		model.setProductCode("QUICK_MSECURITY_PAY"); // 销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
		request.setBizModel(model);
		request.setNotifyUrl(PayUtil.alipay_notify_url); // 回调地址

		// 通过alipayClient调用API，获得对应的response类
		AlipayTradeAppPayResponse responseApp;
		try {
			responseApp = alipayClient.sdkExecute(request);
			System.out.println(responseApp.getBody());
			return responseApp.getBody();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 微信支付
	public RetKit weixinPay(Map<String, String> map) {
		long second = System.currentTimeMillis() / 1000L;// （转换成秒）
		String seconds = String.valueOf(second).substring(0, 10); // （截取前10位）
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", PayUtil.APPID);
		params.put("mch_id", PayUtil.MCHID);
		params.put("body", map.get("body"));
		params.put("out_trade_no", map.get("out_trade_no"));
		params.put("total_fee", map.get("total_fee"));
		params.put("fee_type", "CNY");
		String ip ="127.0.0.1";
		params.put("spbill_create_ip", ip);
		params.put("trade_type", TradeType.APP.name());// 原生支付
		params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
		params.put("notify_url", PayUtil.WEIXIN_NOTIFY_URL);
		

		String sign = PaymentKit.createSign(params, PayUtil.APIKEY);
		params.put("sign", sign);
		String xmlResult = PaymentApi.pushOrder(params);

		System.out.println(xmlResult);
		Map<String, String> result = PaymentKit.xmlToMap(xmlResult);

		String return_code = result.get("return_code");
		String return_msg = result.get("return_msg");
		if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code)) {
			return RetKit.fail(return_msg);
		}
		String result_code = result.get("result_code");
		if (StrKit.isBlank(result_code) || !"SUCCESS".equals(result_code)) {
			return RetKit.fail(return_msg);
		}

		// **再次签名
		Map<String, String> signParam = new HashMap<String, String>();
		signParam.put("appid", PayUtil.APPID);
		signParam.put("prepayid", result.get("prepay_id"));
		signParam.put("partnerid", PayUtil.MCHID);
		signParam.put("timestamp", seconds);
		signParam.put("noncestr", System.currentTimeMillis() / 1000 + "");
		signParam.put("package", "Sign=WXPay");

		String signAgain = PaymentKit.createSign(signParam, PayUtil.APIKEY);
		signParam.put("sign", signAgain);
		String code_url = result.get("code_url");
		return RetKit.ok().set("wx_code_url", code_url).set("wx_appid", result.get("appid"))
				.set("wx_mch_id", result.get("mch_id")).set("wx_nonce_str", System.currentTimeMillis() / 1000 + "")
				.set("wx_sign", signAgain).set("wx_prepay_id", result.get("prepay_id")).set("timestamp", seconds);
	}

}
