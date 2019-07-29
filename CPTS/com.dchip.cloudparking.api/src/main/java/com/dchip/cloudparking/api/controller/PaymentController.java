package com.dchip.cloudparking.api.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iRepository.IRechargeCouponRepository;
import com.dchip.cloudparking.api.iRepository.IRechargeLogRepository;
import com.dchip.cloudparking.api.iService.IPaymentService;
import com.dchip.cloudparking.api.iService.IUserService;
import com.dchip.cloudparking.api.model.po.RechargeCoupon;
import com.dchip.cloudparking.api.model.po.RechargeLog;
import com.dchip.cloudparking.api.utils.IpKit;
import com.dchip.cloudparking.api.utils.PayUtil;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;
import com.jfinal.weixin.sdk.kit.PaymentKit;

/**
 * 支付
 * @author 鼎芯科技
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private IPaymentService payApiService;
	@Autowired
	private IRechargeCouponRepository rechargeCouponRepository;
	@Autowired
	private IRechargeLogRepository rechargeLogRepository;
	@Autowired
	private IUserService userService;
	Log log  = LogFactory.getLog(PaymentController.class);
	
	/**
	 * 用户充值
	 */
	@RequestMapping("/recharge")
	public RetKit recharge(HttpServletRequest request) { 
		try {
			String strUserId = request.getParameter("userId");// 用户ID
			String strType = request.getParameter("type"); // 1-支付宝 2-微信
			String strRcId = request.getParameter("rcId"); //recharge_coupon表ID：充值多少可以获得的优惠卷
			if(StrKit.isBlank(strUserId) || StrKit.isBlank(strType) || StrKit.isBlank(strRcId)) {
				return RetKit.fail("参数缺少");
			}
			Integer rechargeCouponId = Integer.parseInt(strRcId);
			RechargeCoupon rechargeCoupon =  rechargeCouponRepository.findRechargeCoupon(rechargeCouponId);
			if(rechargeCoupon == null) {
				return RetKit.fail("充值面值信息不存在");
			}
			BigDecimal inMoney = rechargeCoupon.getInMoney(); 
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("body", "云停车充值");
			map.put("total_fee", String.valueOf(changeFenToYuan(inMoney)));
			map.put("total_amount", String.valueOf(inMoney));
			map.put("ip", IpKit.getRealIp(request));
			map.put("out_trade_no", StrKit.getRandomUUID());
			
			return payApiService.recharge(Integer.parseInt(strUserId), Integer.parseInt(strType), inMoney, inMoney, map);
		} catch (Exception e) {
			return RetKit.fail();
		}
	}
	
	 /**
     * 金额乘100
     * */
    public Integer changeFenToYuan(BigDecimal fen) {
        BigDecimal yuan = fen.multiply(new BigDecimal(100));  
		return yuan.intValue();
	}
    
    /**
	 * 微信回调
	 */
	@RequestMapping(value = "/weixin_notify")
	public void weixin_notify(HttpServletRequest request, HttpServletResponse response) {
		String xmlMsg = request.getParameter("xmlMsg");
		Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
		Map<String, String> xml = new HashMap<String, String>();

		String result_code = params.get("result_code");
		String out_trade_no = params.get("out_trade_no");
		if (PaymentKit.verifyNotify(params, PayUtil.APIKEY)) {
			if (("SUCCESS").equals(result_code)) {
				xml.put("return_code", "SUCCESS");
				xml.put("return_msg", "OK");
				// 更新订单信息
				RechargeLog rechargeLog = rechargeLogRepository.findByOutTradeNo(out_trade_no);
				// 判断订单的 status 是否为 0
				if (!rechargeLog.isStatus()) {
					RetKit flag = userService.recharge(out_trade_no);
					if (!flag.success()) {
						xml.put("return_code", "FAIL");
						xml.put("return_msg", "支付失败");
					}
				}
			}
		} else {
			xml.put("return_code", "FAIL");
			xml.put("return_msg", "签名验证错误");
		}
		
		try {
			response.getWriter().write(PaymentKit.toXml(xml));
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 支付宝回调
	 */
	@RequestMapping(value = "/ali_notify")
	public void ali_notify(HttpServletRequest request, HttpServletResponse response) {
		String tradeStatus = request.getParameter("trade_status");
		if ("TRADE_SUCCESS".equals(tradeStatus)) {
			String out_trade_no = request.getParameter("out_trade_no");
			// 更新订单信息
			RechargeLog rechargeLog = rechargeLogRepository.findByOutTradeNo(out_trade_no);
			// 判断订单的 status 是否为 0
			if (!rechargeLog.isStatus()) {
				userService.recharge(out_trade_no);
			}
		}
	}
}
