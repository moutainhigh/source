package com.dchip.cloudparking.wechat.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dchip.cloudparking.wechat.constant.BaseConstant;
import com.dchip.cloudparking.wechat.iRepository.IRoadWayRepository;
import com.dchip.cloudparking.wechat.iService.IParkingOperateService;
import com.dchip.cloudparking.wechat.iService.IUserService;
import com.dchip.cloudparking.wechat.model.po.User;
import com.dchip.cloudparking.wechat.model.po.WechatConfig;
import com.dchip.cloudparking.wechat.utils.SignUtil;
import com.dchip.cloudparking.wechat.utils.StrKit;
import com.dchip.cloudparking.wechat.utils.WeChatUtil;

import net.sf.json.JSONObject;

/*微信获取授权的第一步*/
@Controller
public class UserAccreditController {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private IRoadWayRepository roadWayRepository;

	
	private WechatConfig wechatConfig = new WechatConfig();
	static Log log = LogFactory.getLog(UserAccreditController.class);
	
	//注册回拨
	@RequestMapping("/accreditCallBack")
	protected void accreditCallBack(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.getSession().setAttribute("jumpLinkUrl",null);
		String code = req.getParameter("code");
		String showFlag = req.getParameter("showFlag");
		String parkingInfoId = req.getParameter("parkingInfoId");
		String type = req.getParameter("type");
		String parkingId = req.getParameter("parkingId");
		String roadWayId = req.getParameter("roadWayId");
		String UrlParam = "";
		
		User user = new User();
		//请求用户openid
		String getOpenidUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WeChatUtil.APPID
				+ "&secret=" + WeChatUtil.APPSECRET
				+ "&code=" + code
				+ "&grant_type=authorization_code";
		JSONObject openidJsonObject = WeChatUtil.doGetJson(getOpenidUrl);
		String openid = openidJsonObject.getString("openid");
		log.info(openidJsonObject);
		
		wechatConfig = getWeChatConfig();
		
		String access_token = null;
		String jsapi_ticket = "";
		
		//new Date().getTime()也可以获取时间戳。之所以减去7000就是不想卡7200这么准时
		String nowTimestamp = String.valueOf((System.currentTimeMillis() / 1000) - 7000); 
		
		//请求调用接口的access_token,有限制,不能超过2000次
		String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
				+ "&appid=" + WeChatUtil.APPID
				+ "&secret=" + WeChatUtil.APPSECRET;
		
		if(StrKit.notBlank(wechatConfig.getTimeStamp())) {//不为空的情况下，判断时间戳是否过期，不过期就用，过期就重新获取
			int compareResult = (nowTimestamp).compareTo(wechatConfig.getTimeStamp());
			if(compareResult < 0) {
				//微信配置信息未过期，直接使用数据库相应配置
				access_token = wechatConfig.getAccessToken();
				jsapi_ticket = wechatConfig.getJsapiTicket();
			}else {
				//微信配置信息已过期，需重新获取配置
				access_token = WeChatUtil.getAccess_Token(getTokenUrl);
				//获取jsapi_ticket的接口，请求不能超过1000000次
				String jsapiUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token
						+ "&type=jsapi";
				jsapi_ticket = WeChatUtil.getJsapi_Ticket(jsapiUrl);
				saveWeChatConfig(access_token, jsapi_ticket);//保存新的微信配置信息
			}
		}else {//为空的情况下，重新获取
			//数据库无微信配置信息，系首次获取配置
			access_token = WeChatUtil.getAccess_Token(getTokenUrl);
			//获取jsapi_ticket的接口，请求不能超过1000000次
			String jsapiUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token
					+ "&type=jsapi";
			jsapi_ticket = WeChatUtil.getJsapi_Ticket(jsapiUrl);
			
			saveWeChatConfig(access_token, jsapi_ticket);//保存新的微信配置信息			
		}
		
		
		
		
		if(showFlag.equals(BaseConstant.wechatShowFlag.HaveACardOut.getTypeValue())) {  
			//有牌出场
			UrlParam = "&showFlag=" + showFlag + "&parkingInfoId=" + parkingInfoId + "&type=" + type;
			resp.sendRedirect(WeChatUtil.DOMAIN_NAME_URL + "/cloudParkingWechat/showHomeIndex?openid="+ openid + UrlParam);
		}else if(showFlag.equals(BaseConstant.wechatShowFlag.ScanCodeCheckout.getTypeValue())) {
			//扫码结账
			UrlParam = "&showFlag=" + showFlag + "&parkingId=" + parkingId;
			resp.sendRedirect(WeChatUtil.DOMAIN_NAME_URL + "/cloudParkingWechat/showHomeIndex?openid="+ openid + UrlParam);
			
		}else if(showFlag.equals(BaseConstant.wechatShowFlag.UnlicensedEnter.getTypeValue())){//无牌入场
			UrlParam = "&showFlag=" + showFlag + "&roadWayId=" + roadWayId;
			if(selectOpenid(openid)) {//数据库有openid
				resp.sendRedirect(WeChatUtil.DOMAIN_NAME_URL + "/cloudParkingWechat/showHomeIndex?openid="+ openid + UrlParam);
			}else { //数据库没有这个openid，需要保存用户信息
				user.setOpenid(openid);
				user.setType(BaseConstant.UserType.FrontUser.getTypeValue());  //保存用户类型：普通用户
				user.setCreateTime(new Date()); //注册时间
				user.setState(BaseConstant.UserState.EnabledState.getTypeValue());  //用户启用状态：正常
				user.setFalseReportNumber(0);   //谎报次数	
//				int balanceInt = 0;
//				BigDecimal balance = new BigDecimal(balanceInt);
				user.setBalance(BigDecimal.valueOf(0));
				user.setMemberId(BaseConstant.UserGrade.initMember.getTypeValue());  //用户会员等级 --初始为普通会员
				user.setIsBlack(BaseConstant.UserIsBlack.NormalUser.getTypeValue());   //用户是否为黑名单 -- 初始为正常用户
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");// 转换格式
				String randomName = StrKit.getRandomUUID();
				randomName = sdf.format(date) + randomName.substring(30, randomName.length());
				user.setLicensePlat(randomName);   //临时车牌
				user.setIsAuthentication(BaseConstant.UserAuthenticationStatus.initState.getTypeValue());  //初始为未认证
				user = userService.saveUser(user);
				resp.sendRedirect(WeChatUtil.DOMAIN_NAME_URL + "/cloudParkingWechat/showHomeIndex?openid="+ openid + UrlParam);
			}
		}else if (showFlag.equals(BaseConstant.wechatShowFlag.UnlicensedOut.getTypeValue())) {//无牌出场
			UrlParam = "&showFlag=" + showFlag + "&roadWayId=" + roadWayId;
			if(selectOpenid(openid)) {//数据库有openid
				resp.sendRedirect(WeChatUtil.DOMAIN_NAME_URL + "/cloudParkingWechat/showHomeIndex?openid="+ openid + UrlParam);
			}
		}
	}
	
	@RequestMapping("/showHomeIndex")
	public String showHomeIndex(HttpServletRequest req, Model model) throws ClientProtocolException, IOException {
		
		wechatConfig = getWeChatConfig();
		
		String access_token = null;
		String jsapi_ticket = "";
		String openid = req.getParameter("openid");
		String showFlag = req.getParameter("showFlag");
		//Integer userId = getSelUserId(openid);
		String parkingInfoId = req.getParameter("parkingInfoId");
		String type = req.getParameter("type");
		String parkingId = req.getParameter("parkingId");  //扫码结账
		String roadWayId = req.getParameter("roadWayId");   //车道id
		//new Date().getTime()也可以获取时间戳。之所以减去7000就是不想卡7200这么准时
		String nowTimestamp = String.valueOf((System.currentTimeMillis() / 1000) - 7000); 
		//请求调用接口的access_token,有限制,不能超过2000次
		String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
				+ "&appid=" + WeChatUtil.APPID
				+ "&secret=" + WeChatUtil.APPSECRET;
						
		if(StrKit.notBlank(wechatConfig.getTimeStamp())) {//不为空的情况下，判断时间戳是否过期，不过期就用，过期就重新获取
			int compareResult = (nowTimestamp).compareTo(wechatConfig.getTimeStamp());
			if(compareResult < 0) {
				log.info("微信配置信息未过期，直接使用数据库相应配置");
				access_token = wechatConfig.getAccessToken();
				jsapi_ticket = wechatConfig.getJsapiTicket();
			}else {
				log.info("微信配置信息已过期，需重新获取配置");
				access_token = WeChatUtil.getAccess_Token(getTokenUrl);
				//获取jsapi_ticket的接口，请求不能超过1000000次
				String jsapiUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token
						+ "&type=jsapi";
				jsapi_ticket = WeChatUtil.getJsapi_Ticket(jsapiUrl);
				saveWeChatConfig(access_token, jsapi_ticket);//保存新的微信配置信息
			}
		}
		
		String addOpenidUrl = "";  //路径一定要对，不然微信无法获取定位信息
		if(showFlag.equals(BaseConstant.wechatShowFlag.HaveACardOut.getTypeValue())) { //有牌出场
			addOpenidUrl = "?openid="+ openid +"&showFlag=" + showFlag
					+ "&parkingInfoId=" + parkingInfoId + "&type=" + type;
			model.addAttribute("parkingInfoId", parkingInfoId);
			model.addAttribute("type", type);
		}else if(showFlag.equals(BaseConstant.wechatShowFlag.ScanCodeCheckout.getTypeValue())) {//扫码离场
			addOpenidUrl = "?openid="+ openid +"&showFlag=" + showFlag
					+ "&parkingId=" + parkingId;
			model.addAttribute("parkingId", parkingId);
		}else if (showFlag.equals(BaseConstant.wechatShowFlag.UnlicensedEnter.getTypeValue())) {//无牌入场
				addOpenidUrl = "?openid="+ openid +"&showFlag=" + showFlag
						+ "&roadWayId=" + roadWayId;
				model.addAttribute("roadWayId", roadWayId);
		}else if (showFlag.equals(BaseConstant.wechatShowFlag.UnlicensedOut.getTypeValue())) {//无牌出场
			addOpenidUrl = "?openid="+ openid +"&showFlag=" + showFlag
					+ "&roadWayId=" + roadWayId;
			model.addAttribute("roadWayId", roadWayId);
		}else {
			addOpenidUrl = "?openid="+ openid +"&showFlag=" + showFlag
					+ "&parkingInfoId=" + parkingInfoId + "&type=" + type;
			model.addAttribute("parkingInfoId", parkingInfoId);
			model.addAttribute("type", type);
		}
		SignUtil signNew = new SignUtil();
		String serviceUrl = WeChatUtil.DOMAIN_NAME_URL   
        + req.getContextPath()      //项目名称  
        + req.getServletPath()    //请求页面或其他地址
		+ addOpenidUrl;
		
		Map<String, String> ret = signNew.sign(jsapi_ticket, serviceUrl);

        for (Map.Entry entry : ret.entrySet()) {
            log.info(entry.getKey() + ", " + entry.getValue());
        }
        JSONObject jsonArray = JSONObject.fromObject(ret); 
        model.addAttribute("mapInfo", jsonArray);//调用微信js所需配置信息
        
        model.addAttribute("openid", openid);
        model.addAttribute("showFlag", showFlag);
        //model.addAttribute("userId", userId);
        
		return "homeIndex";
	}
	
	/**
	 * 根据openid,查询UserId
	 * @param openid
	 * @return
	 */
	public Integer getSelUserId(String openid) {
		User selUser = userService.findUserByOpenid(openid);
		if((selUser == null)) {
			return 0;
		}else {
			return selUser.getId();
		}
	}
	
	/**
	 * 查询openid是否存在
	 * @param Openid
	 * @return
	 */
	public Boolean selectOpenid(String openid) {
		return userService.selectOpenid(openid);
	}
	
	/**
	 * 在redis中取回微信配置
	 * @return
	 */
	public WechatConfig getWeChatConfig() {
		WechatConfig getWechatConfig = new WechatConfig();
		List paraList = new ArrayList(); 
		paraList.add("timeStamp");  
		paraList.add("accessToken");  
		paraList.add("jsapiTicket");  
		
		List<String> valueList = redisTemplate.opsForValue().multiGet(paraList); 
		getWechatConfig.setTimeStamp(valueList.get(0));
		getWechatConfig.setAccessToken(valueList.get(1));
		getWechatConfig.setJsapiTicket(valueList.get(2));
		return getWechatConfig;
	}
	
	/**
	 * 保存微信配置信息
	 * @param access_token
	 * @param jsapi_ticket
	 */
	public void saveWeChatConfig(String access_token,String jsapi_ticket) {
		String saveTimestamp = String.valueOf((System.currentTimeMillis() / 1000));//当前时间;
		Map valueMap = new HashMap();
		valueMap.put("timeStamp", saveTimestamp);  
		valueMap.put("accessToken", access_token);  
		valueMap.put("jsapiTicket", jsapi_ticket);  
		redisTemplate.opsForValue().multiSet(valueMap); 
	}
	
}
