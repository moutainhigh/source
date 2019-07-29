package com.dchip.cloudparking.wechat.controller;

import java.io.BufferedOutputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.wechat.iService.IPayService;
import com.dchip.cloudparking.wechat.pay.base.BaseController;
import com.dchip.cloudparking.wechat.pay.rep.UnifiedOrderParams;
import com.dchip.cloudparking.wechat.pay.util.SignatureUtil;
import com.dchip.cloudparking.wechat.pay.util.XmlUtil;
import com.dchip.cloudparking.wechat.utils.RetKit;
import com.dchip.cloudparking.wechat.utils.WeChatUtil;

/**
 * 支付
 * @author 鼎芯科技
 *
 */
@RestController
@RequestMapping("/payment")
public class PaymentController extends BaseController{
	
	@Autowired
	private IPayService payApiService;

	Log log  = LogFactory.getLog(PaymentController.class);

	/**
     * 微信公众号支付
     * */
	@PostMapping(value = "/jspay")
	@ResponseBody
	public RetKit jsPay(@ModelAttribute UnifiedOrderParams payParams) throws Exception {
		
		if (StringUtils.isEmpty(payParams) || StringUtils.isEmpty(payParams.getOpenid())) {
			return RetKit.fail("-1", "支付数据错误");
		}
		return payApiService.weixinPay(payParams);
	}   
	
	/**
	 * 开闸页面--收费为0的情况下
	 * @param request
	 * @return
	 */
	@RequestMapping("/openBrake")
	@ResponseBody
	public RetKit openBrake(HttpServletRequest request) {
		String parkingInfoId = request.getParameter("parkinginfoId");
		return payApiService.openBrake(parkingInfoId);
	}
	
	/**
	 * 无牌车入场开闸
	 * @param request
	 * @return
	 */
	@RequestMapping("/unlicensedEntryOpenBrake")
	@ResponseBody
	public RetKit unlicensedEntryOpenBrake(HttpServletRequest request) {
		String licensePlate = request.getParameter("licensePlate");
		String roadWayId = request.getParameter("roadWayId");
		return payApiService.unlicensedEntryOpenBrake(licensePlate,roadWayId);
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
    /**
     * 微信公众号支付成功后的异步回调
     * */
    @RequestMapping(value = "/notify")  
    @ResponseBody  
	public RetKit payCallback(HttpServletRequest request) throws Exception {
		String resXml = ""; // 反馈给微信服务器
		String notifyXml = request.getParameter("notifyXml");// 微信支付系统发送的数据（<![CDATA[product_001]]>格式）
		log.info("-----------------------notifyXml----------------------------");
		log.info(notifyXml);
		log.info("---------------------------------------------------");
		RetKit kit = RetKit.create();
		// 验证签名
		if (SignatureUtil.checkIsSignValidFromWeiXin(notifyXml, WeChatUtil.APIKEY, null)) {
			Map<String, String> map = XmlUtil.xmlToMap(notifyXml);
			if (Objects.equals("SUCCESS", map.get("result_code"))) {
				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
						+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
				
				try {
					Integer outTradeId = payApiService.findOutTradeId(map.get("out_trade_no"));
					log.info("--------outTradeId-"+outTradeId+"----------------------------");
					kit =  payApiService.writePayResult(outTradeId);
					log.info("--------kit.code-"+kit.toString()+"----------------------------");
					if (!kit.success()) {
						log.info("----------------------rechargeLog.isStatus()-----------------------------");
						// 支付失败
						kit.setCode(-1);
						kit.setMsg(map.get("err_code_des"));
						resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA["
								+ map.get("err_code_des") + "]]></return_msg>" + "</xml> ";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else {
				// 支付失败
				kit.setCode(-1);
				kit.setMsg(map.get("err_code_des"));
				resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA["
						+ map.get("err_code_des") + "]]></return_msg>" + "</xml> ";
			}
		} else {
			kit.setCode(-1);
			kit.setMsg("签名验证错误");
			// 支付失败
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
					+ "<return_msg><![CDATA[签名验证错误]]></return_msg>" + "</xml> ";
		}
		log.info("----------------------resXml-----------------------------");
		log.info(resXml);
		log.info("---------------------------------------------------");
		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(this.getResponse().getOutputStream());
			out.write(resXml.getBytes());
			out.flush();
		} catch (Exception e) {
			log.info("------------------------Exception e---------------------------");
			log.info(e.toString());
			log.info("---------------------------------------------------");
			e.printStackTrace();
		}
		return kit;
	}
    
    /**
     * 根据微信支付订单id删除指定order
     * @param request
     * @return
     */
    @RequestMapping("/delPayLog")
    @ResponseBody
    public RetKit delPayLog(HttpServletRequest request) {
    	String outTradeNo = request.getParameter("outTradeNo");
    	return payApiService.deleteOrderByOutTradeNo(outTradeNo);
    }
}
