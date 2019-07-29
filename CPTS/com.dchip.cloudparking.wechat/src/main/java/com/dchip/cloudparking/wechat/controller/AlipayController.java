package com.dchip.cloudparking.wechat.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.dchip.cloudparking.wechat.common.AliPayConfig;
import com.dchip.cloudparking.wechat.iService.IParkingOperateService;




@Controller
@RequestMapping("/alipay")
public class AlipayController {
	
	@Autowired
	private IParkingOperateService parkingOperateService;
	/**
	 * 根据车牌结单
	 * @param request
	 * @return
	 */
	@RequestMapping("/alipayOpenByLicensePlate")
	public String openByLicensePlate(HttpServletRequest request, Model model) {
		String parkingIdStr = request.getParameter("parkingId");
		String companyName = parkingOperateService.findCompanyNameByParkingId(parkingIdStr);
		model.addAttribute("companyName", companyName);
		return "openByLicensePlate";
	}
	
	@RequestMapping("/alipayOrder")
	private String Order(HttpServletRequest request) throws AlipayApiException {
		Integer strType = Integer.parseInt(request.getParameter("type")); // 1-支付宝 2-微信
		if (strType == 1) {
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AliPayConfig.appId, AliPayConfig.privateKey, "json", AliPayConfig.charset, AliPayConfig.alipayPublicKey, "RSA2");  //获得初始化的AlipayClient
			//创建API对应的request类
			AlipayTradePrecreateRequest Request = new AlipayTradePrecreateRequest();
			Request.setBizContent("{" +
			"    \"out_trade_no\":\"20150320010101001\"," +
			"    \"seller_id\":\"2088102146225135\"," +
			"    \"total_amount\":\"88.88\"," +
			"    \"discountable_amount\":\"8.88\"," +
			"    \"undiscountable_amount\":\"80\"," +
			"    \"buyer_logon_id\":\"15901825620\"," +
			"    \"subject\":\"Iphone6 16G\"," +
			"    \"store_id\":\"NJ_001\"" +
			"    }");
			//通过alipayClient调用API，获得对应的response类
			AlipayTradePrecreateResponse response = alipayClient.execute(Request);
			System.out.print(response.getBody());
			//根据response中的结果继续业务逻辑处理
		}
		return "selectPaymentMethod";
	}
	
	

}
