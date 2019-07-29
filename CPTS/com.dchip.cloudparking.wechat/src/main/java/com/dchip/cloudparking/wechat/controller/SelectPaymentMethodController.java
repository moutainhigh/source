package com.dchip.cloudparking.wechat.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dchip.cloudparking.wechat.common.AliPayConfig;
import com.dchip.cloudparking.wechat.constant.BaseConstant;
import com.dchip.cloudparking.wechat.iRepository.IOrderRepository;
import com.dchip.cloudparking.wechat.iRepository.IParkingInfoRepository;
import com.dchip.cloudparking.wechat.iRepository.IParkingRepository;
import com.dchip.cloudparking.wechat.iRepository.IRoadWayRepository;
import com.dchip.cloudparking.wechat.iService.IParkingOperateService;
import com.dchip.cloudparking.wechat.iService.IPayService;
import com.dchip.cloudparking.wechat.model.po.MainControl;
import com.dchip.cloudparking.wechat.model.po.Order;
import com.dchip.cloudparking.wechat.model.po.Parking;
import com.dchip.cloudparking.wechat.model.po.ParkingInfo;
import com.dchip.cloudparking.wechat.pay.util.IpKit;
import com.dchip.cloudparking.wechat.utils.RetKit;



@RestController
@RequestMapping("/Alipay")
public class SelectPaymentMethodController {
	
	@Autowired
	IParkingOperateService parkingOperateService;
	@Autowired
	IPayService payService;
	@Autowired
	IOrderRepository orderRepository;
	@Autowired
	IParkingInfoRepository parkingInfoRepository;
	@Autowired
	IParkingRepository parkingRepository;
	@Autowired
	IRoadWayRepository roadWayRepository;
	@Autowired
	private IPayService payApiService;
	
	Log log  = LogFactory.getLog(SelectPaymentMethodController.class);

	/**
	 * 根据车牌结单
	 * @param request
	 * @return
	 */
	@RequestMapping("/alipayOpenByLicensePlate")
	public ModelAndView alipayOpenByLicensePlate(HttpServletRequest request, Model model) {
		 ModelAndView mv = new ModelAndView("selectPaymentMethod");
		String parkingIdStr = request.getParameter("parkingId");
		String companyName = parkingOperateService.findCompanyNameByParkingId(parkingIdStr);
		String authCode = request.getParameter("auth_code");
		model.addAttribute("companyName", companyName);
		model.addAttribute("parkingId", parkingIdStr);
		model.addAttribute("authCode", authCode);
		return mv;
	}
	
	
	@RequestMapping("/AlipayMethod")
	public RetKit AlipayMethod(HttpServletRequest request) {
		String parkingInfoIdStr = request.getParameter("parkingInfoId");
		String authCode = request.getParameter("authCode");
		String totalFee = request.getParameter("totalFee");
		Integer parkingInfoId = Integer.parseInt(parkingInfoIdStr);
		log.warn("---------------------------------------------------");
		log.warn("authCode="+authCode+"totalFee="+totalFee+"parkingInfoId="+parkingInfoId);
		log.warn("---------------------------------------------------");
		try {
			Map<String, Object> returnRs = new HashMap<String, Object>();
			Map<String, String> map = new HashMap<String, String>();
			Order order = orderRepository.findOrderByinfoIsAdvanceUnPay(parkingInfoId);
			if (order == null) {
				return RetKit.fail("找不到订单信息");
			}
			Optional<ParkingInfo> parkingInfo = parkingInfoRepository.findById(parkingInfoId);
			ParkingInfo getParkingInfo = parkingInfo.get();
			if (getParkingInfo == null) {
				return RetKit.fail("找不到进场信息");
			}
			log.warn("---------------------------------------------------");
			log.warn("order="+order);
			log.warn("---------------------------------------------------");
			log.warn("---------------------------------------------------");
			log.warn("parkingInfo="+parkingInfo);
			log.warn("---------------------------------------------------");
			Long time = getParkingInfo.getOutDate().getTime() - getParkingInfo.getParkDate().getTime();
			Long parktime = time/(1000*60*60);
			map.put("licensePlate",getParkingInfo.getLicensePlate());
			map.put("total_fee", totalFee);
			map.put("showParkingTime",Long.toString(parktime));
			map.put("out_trade_no", order.getOutTradeNo());
			map.put("ip", IpKit.getRealIp(request));
			map.put("authCode", authCode);
			log.warn("---------------------------------------------------");
			log.warn("parkingInfo="+map);
			log.warn("---------------------------------------------------");
			RetKit TradeNo = payService.payment(map, AliPayConfig.notifyUrl);
			
			returnRs.put("TradeNo", TradeNo);
			
			return RetKit.okData(returnRs.get("TradeNo"));
		}catch (Exception e) {
			e.printStackTrace();
			return RetKit.fail("支付失败！");
		}
	}
	
	@RequestMapping("/alipayNotify")
	public RetKit alipayNotify(HttpServletRequest request) {
		Integer parkingInfoId = Integer.parseInt(request.getParameter("parkingInfoId"));
		Integer outRoadId = roadWayRepository.findMacByParkingIdAndMarker(parkingInfoId);
		ParkingInfo  parkinginfo = parkingInfoRepository.findById(parkingInfoId).get();
		parkinginfo.setOutRoadWayId(outRoadId);
		parkinginfo.setStatus(BaseConstant.parkingInfoStatus.finishedStatus.getValue());
		parkingInfoRepository.save(parkinginfo);
		Order order = orderRepository.findOrderByinfoIsAdvanceUnPay(parkingInfoId);
		if (order == null) {
			return RetKit.fail("找不到付款订单信息");
		}
		log.warn("---------------------------------------------------");
		log.warn("支付成功");
		log.warn("---------------------------------------------------");
		//保存订单信息
		order.setStatus(BaseConstant.OrderStatus.AlreadyPay.getTypeValue());
		order.setPayTime(new Date());
		orderRepository.save(order);
		//保存好信息之后开闸
		payApiService.openBrake(parkingInfoId.toString());
		return RetKit.ok();
	}
}
