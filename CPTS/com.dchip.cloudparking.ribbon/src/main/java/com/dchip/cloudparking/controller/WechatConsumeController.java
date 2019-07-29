package com.dchip.cloudparking.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.service.ApiConsumeService;
import com.dchip.cloudparking.utils.AliPayConfig;
import com.dchip.cloudparking.utils.HttpReqUtil;
import com.dchip.cloudparking.utils.IOUtil;
import com.dchip.cloudparking.utils.RetKit;
import com.dchip.cloudparking.utils.StrKit;
import com.dchip.cloudparking.utils.WeChatUtil;

@RestController
@RequestMapping("/cloudParkingWechat")
public class WechatConsumeController {
	@Autowired
	ApiConsumeService apiConsumeService;
	@Autowired
	private RestTemplate restTemplate;
	static Log log = LogFactory.getLog(WechatConsumeController.class);
	
	//用户授权
	@RequestMapping("/userAccredit")
	public void useWechatLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String showFlag = request.getParameter("showFlag");
		String parkingInfoId = request.getParameter("parkingInfoId");
		String type = request.getParameter("type");
		String parkingId = request.getParameter("parkingId");
		String roadWayId = request.getParameter("roadWayId");
		String UrlParam = "";
		String backUrl = WeChatUtil.DOMAIN_NAME_URL + "/cloudParkingWechat/accreditCallBack";
		if(showFlag.equals("1")) {//无牌入场
			UrlParam = "?showFlag=" + showFlag + "&roadWayId=" + roadWayId;
		}else if(showFlag.equals("2")) {//无牌出场
			UrlParam = "?showFlag=" + showFlag + "&roadWayId=" + roadWayId;
		}else if(showFlag.equals("3")) {//有牌出场
			UrlParam = "?showFlag=" + showFlag + "&parkingInfoId=" + parkingInfoId + "&type=" + type;
		}else if(showFlag.equals("4")){ //扫固定码结账出场
			UrlParam = "?showFlag=" + showFlag + "&parkingId=" + parkingId;
		}
		backUrl += UrlParam;
		@SuppressWarnings("deprecation")
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeChatUtil.APPID
				+ "&redirect_uri=" + URLEncoder.encode(backUrl)
				+ "&response_type=code"
				+ "&scope=snsapi_base"
				/*+ "&scope=snsapi_userinfo"*/
				+ "&state=STATE#wechat_redirect";
		response.sendRedirect(url);
	}
	
	//支付宝用户授权   2019/4/3  hhx
	@RequestMapping("/alipayUserAccredit")
	public void useAlipayLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String showFlag = request.getParameter("showFlag");
		String parkingId = request.getParameter("parkingId");
		String authcode = request.getParameter("auth_code");
		String UrlParam = "";
		String backUrl = WeChatUtil.DOMAIN_NAME_URL +":9005/cloudParkingWechat/Alipay/alipayOpenByLicensePlate";
		if(showFlag.equals("5")){ //扫固定码结账出场
			UrlParam = "?showFlag=" + showFlag + "&parkingId=" + parkingId + "&authCode=" + authcode;
		}
		backUrl += UrlParam;
		
		response.sendRedirect(backUrl);
	}
	
	@RequestMapping("/accreditCallBack")
	public void accreditCallBack(HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");
		request.getSession().setAttribute("jumpLinkUrl",null);
		String showFlag = request.getParameter("showFlag");
		String parkingInfoId = request.getParameter("parkingInfoId");
		String type = request.getParameter("type");
		String parkingId = request.getParameter("parkingId");  //扫码结账
		String roadWayId = request.getParameter("roadWayId");
		String UrlParam = "";
		if(showFlag.equals("1")) {//无牌入场
			UrlParam = "&showFlag=" + showFlag + "&roadWayId=" + roadWayId;
		}else if(showFlag.equals("2")) {//无牌出场
			UrlParam = "&showFlag=" + showFlag + "&roadWayId=" + roadWayId;
		}else if(showFlag.equals("3")) {//有牌出场
			UrlParam = "&showFlag=" + showFlag + "&parkingInfoId=" + parkingInfoId + "&type=" + type;
		}else if(showFlag.equals("4")) {//扫固定码结账出场
			UrlParam = "&showFlag=" + showFlag + "&parkingId=" + parkingId;
		}
		String url = WeChatUtil.DOMAIN_NAME_URL + ":9005/cloudParkingWechat/accreditCallBack?code="
				+ code + UrlParam;
		response.sendRedirect(url);
	}
	
	/**
	 * 显示框架主页
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping("/showHomeIndex")
	public String showHomeIndex(HttpServletRequest request, HttpServletResponse response) {
		String openid = request.getParameter("openid");
		String showFlag = request.getParameter("showFlag");
		String parkingInfoId = request.getParameter("parkingInfoId");
		String type = request.getParameter("type");
		String parkingId = request.getParameter("parkingId");  //扫码结账
		String roadWayId = request.getParameter("roadWayId");
		String UrlParam = "";
		if (showFlag.equals("1")) {//无牌入场
			UrlParam = "&showFlag=" + showFlag + "&roadWayId=" + roadWayId;
		}else if (showFlag.equals("2")) {//无牌出场
			UrlParam = "&showFlag=" + showFlag + "&roadWayId=" + roadWayId;
		}else if(showFlag.equals("3")) {//有牌出场
			UrlParam = "&showFlag=" + showFlag + "&parkingInfoId=" + parkingInfoId + "&type=" + type;
		}else if(showFlag.equals("4")) {//扫码离场
			UrlParam = "&showFlag=" + showFlag + "&parkingId=" + parkingId;
		}
		else {
			UrlParam = "&showFlag=" + showFlag + "&parkingInfoId=" + parkingInfoId + "&type=" + type;
		}
		return restTemplate.getForObject("http://wechat/cloudParkingWechat/showHomeIndex?openid="+ openid + UrlParam,String.class);
	}
	
	/**
	 * 显示有牌出场页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/haveACardPayPage")
	public String haveACardPayPage(HttpServletRequest request, HttpServletResponse response) {
		String parkingInfoId = request.getParameter("parkingInfoId");
		String type = request.getParameter("type");
		return restTemplate.getForObject("http://wechat/cloudParkingWechat/haveACardPayPage?parkingInfoId=" +parkingInfoId + "&type=" + type, String.class);
	}
	
	/**
	 * 保存跳转的链接
	 * @param request
	 */
	@RequestMapping("/setLinkUrl")
	public void setLinkUrl(HttpServletRequest request) {
		log.info("linkUrl = " + request.getParameter("linkUrl"));
		request.getSession().setAttribute("jumpLinkUrl", request.getParameter("linkUrl"));
	}
	
	/**
	 * 返回跳转的链接
	 * @param request
	 * @return
	 */
	@RequestMapping("/getLinkUrl")
	public String getLinkUrl(HttpServletRequest request) {
		log.info("showLinkUrl = " + request.getSession().getAttribute("jumpLinkUrl"));
		return request.getSession().getAttribute("jumpLinkUrl") == null?"":request.getSession().getAttribute("jumpLinkUrl").toString();
	}
	
	
    /**
     * 支付
     */
	@RequestMapping("/payment/jspay")
	public Object pay(HttpServletRequest request, HttpServletResponse response) {
		String openId = request.getParameter("openid");
		String totalFee = request.getParameter("totalFee");
		String parkingLen = request.getParameter("parkingLen");
		String parkingInfoId = request.getParameter("parkingInfoId");
		String body = request.getParameter("body");
		String spbill_create_ip = HttpReqUtil.getRemortIP(request);
		 
		openId = (openId == null ? "":openId);
		totalFee = (totalFee == null ? "0":totalFee);
		body = (body == null ? "":body);
		return JSON.parse(restTemplate.postForObject("http://wechat/cloudParkingWechat/payment/jspay?openid="+openId+"&totalFee="+totalFee+"&body="+body
				+"&spbill_create_ip="+spbill_create_ip+"&parkingLen="+ parkingLen+ "&parkingInfoId="+parkingInfoId, apiConsumeService.getHttpEntityByRequst(request), String.class));
	}
	
	/**
	 * 免费车辆开闸
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/payment/openBrake")
	@ResponseBody
	public Object openBrake (HttpServletRequest request, HttpServletResponse response) {
		String parkingInfoId = request.getParameter("parkingInfoId");
		return JSON.parse(restTemplate.postForObject("http://wechat/cloudParkingWechat/payment/openBrake?parkingInfoId="+parkingInfoId, apiConsumeService.getHttpEntityByRequst(request), String.class));
	}
	
    /**
     * 微信回调
     * @throws IOException 
     */
	@RequestMapping("/payment/notify")
	@ResponseBody
	public Object payCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String notifyXml = IOUtil.inputStreamToString(request.getInputStream(), null);
		return JSON.parse(restTemplate.postForObject("http://wechat/cloudParkingWechat/payment/notify?notifyXml="+notifyXml, apiConsumeService.getHttpEntityByRequst(request), String.class));
	}
	
	/**
	 * 根据微信支付订单id删除指定order
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/payment/delPayLog")
	@ResponseBody
	public Object delPayLog(HttpServletRequest request, HttpServletResponse response) {
		String outTradeNo = request.getParameter("outTradeNo");
		if(StrKit.isBlank(outTradeNo)) {
			return RetKit.fail("outTradeNo不能为空");
		}
		return JSON.parse(restTemplate.postForObject("http://wechat/cloudParkingWechat/payment/delPayLog?outTradeNo="+outTradeNo, apiConsumeService.getHttpEntityByRequst(request), String.class));
	}
	
	/**
	 * 扫码结账
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/scanCodeCheckout")
	public void scanCodeCheckout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String parkingId = request.getParameter("parkingId");
		String backUrl = WeChatUtil.DOMAIN_NAME_URL + "/cloudParkingWechat/scanCodeCallBack?parkingId=" + parkingId;

		@SuppressWarnings("deprecation")
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeChatUtil.APPID
				+ "&redirect_uri=" + URLEncoder.encode(backUrl)
				+ "&response_type=code"
				+ "&scope=snsapi_base"
				/*+ "&scope=snsapi_userinfo"*/
				+ "&state=STATE#wechat_redirect";
		response.sendRedirect(url);
	}
	
	/**
	 * 扫码回调
	 * @param request
	 * @param response
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping("/scanCodeCallBack")
	public void scanCodeCallBack(HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");
		request.getSession().setAttribute("jumpLinkUrl",null);
		String showFlag = request.getParameter("showFlag");
		String parkingInfoId = request.getParameter("parkingInfoId");
		String type = request.getParameter("type");
		String UrlParam = "";
		if(showFlag.equals("3")) {//有牌出场
			UrlParam = "&showFlag=" + showFlag + "&parkingInfoId=" + parkingInfoId + "&type=" + type;
		}
		String url = WeChatUtil.DOMAIN_NAME_URL + ":9005/cloudParkingWechat/accreditCallBack?code="
				+ code + UrlParam;
		response.sendRedirect(url);
	}
	
	/**
	 * 显示微信扫码出场页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/openByLicensePlate")
	public String openByLicensePlate(HttpServletRequest request, HttpServletResponse response) {
		String parkingId = request.getParameter("parkingId");
		return restTemplate.getForObject("http://wechat/cloudParkingWechat/openByLicensePlate?parkingId=" +parkingId, String.class);
	}
	
	/**
	 * 显示支付宝扫码出场页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/alipayOpenByLicensePlate")
	public String alipayOpenByLicensePlate(HttpServletRequest request, HttpServletResponse response) {
		String parkingId = request.getParameter("parkingId");
		return restTemplate.getForObject("http://wechat/cloudParkingWechat/selectPaymentMethod?parkingId=" +parkingId, String.class);
	}
	
	/**
	 * 结账ajax
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/rechoningOpearte")
	@ResponseBody
	public Object rechoningOpearte(HttpServletRequest request, HttpServletResponse response) {
		String parkingId = request.getParameter("parkingId");
		String licensePlate = request.getParameter("licensePlate");
		return restTemplate.getForObject("http://wechat/cloudParkingWechat/rechoningOpearte?parkingId=" + parkingId +"&licensePlate=" +licensePlate, String.class);
	}
	
	/**
	 * 无牌入场
	 * @author ZYY
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/unlicensedEntry")
	public String unlicensedEntry(HttpServletRequest request, HttpServletResponse response) {
		String openid = request.getParameter("openid");
		String roadWayId = request.getParameter("roadWayId");
		return restTemplate.getForObject("http://wechat/cloudParkingWechat/unlicensedEntry?openid=" +openid + "&roadWayId=" + roadWayId, String.class);
	}
	
	/**
	 * 无牌出场
	 * @author ZYY
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/unlicensedOut")
	public String unlicensedOut(HttpServletRequest request, HttpServletResponse response) {
		String openid = request.getParameter("openid");
		String roadWayId = request.getParameter("roadWayId");
		return restTemplate.getForObject("http://wechat/cloudParkingWechat/unlicensedOut?openid=" +openid + "&roadWayId=" + roadWayId, String.class);
		
	}
	
	@RequestMapping("/selectPaymentMethod")
	public String selectPaymentMethod(HttpServletRequest request, HttpServletResponse response) {
		String parkingInfoId = request.getParameter("parkingInfoId");
		String type = request.getParameter("type");
		return restTemplate.getForObject("http://wechat/cloudParkingWechat/selectPaymentMethod?parkingInfoId=" +parkingInfoId + "&type=" + type, String.class);
		
	}
	
}
