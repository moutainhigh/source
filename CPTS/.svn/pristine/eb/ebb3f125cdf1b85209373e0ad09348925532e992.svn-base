package com.dchip.cloudparking.wechat.serviceImp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.dchip.cloudparking.wechat.common.AliPayConfig;
import com.dchip.cloudparking.wechat.constant.BaseConstant;
import com.dchip.cloudparking.wechat.iRepository.IOrderRepository;
import com.dchip.cloudparking.wechat.iRepository.IParkingInfoRepository;
import com.dchip.cloudparking.wechat.iRepository.IParkingRepository;
import com.dchip.cloudparking.wechat.iRepository.IUserRepository;
import com.dchip.cloudparking.wechat.iService.IPayService;
import com.dchip.cloudparking.wechat.model.po.MainControl;
import com.dchip.cloudparking.wechat.model.po.MainRoadway;
import com.dchip.cloudparking.wechat.model.po.Order;
import com.dchip.cloudparking.wechat.model.po.Parking;
import com.dchip.cloudparking.wechat.model.po.ParkingInfo;
import com.dchip.cloudparking.wechat.model.po.Roadway;
import com.dchip.cloudparking.wechat.model.po.User;
import com.dchip.cloudparking.wechat.model.qpo.QMainControl;
import com.dchip.cloudparking.wechat.model.qpo.QMainRoadway;
import com.dchip.cloudparking.wechat.model.qpo.QParkingInfo;
import com.dchip.cloudparking.wechat.model.qpo.QRoadway;
import com.dchip.cloudparking.wechat.pay.base.SystemConstant;
import com.dchip.cloudparking.wechat.pay.rep.UnifiedOrderParams;
import com.dchip.cloudparking.wechat.pay.resp.JsPayResult;
import com.dchip.cloudparking.wechat.pay.util.HttpReqUtil;
import com.dchip.cloudparking.wechat.pay.util.MsgUtil;
import com.dchip.cloudparking.wechat.pay.util.PayUtil;
import com.dchip.cloudparking.wechat.pay.util.SignatureUtil;
import com.dchip.cloudparking.wechat.pay.util.XmlUtil;
import com.dchip.cloudparking.wechat.po.qpo.QOrder;
import com.dchip.cloudparking.wechat.po.qpo.QUser;
import com.dchip.cloudparking.wechat.utils.RetKit;
import com.dchip.cloudparking.wechat.utils.WeChatUtil;


@Service
public class PayServiceImp extends BaseService implements IPayService {

	@Autowired
	private IOrderRepository orderRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IParkingInfoRepository parkingInfoRepository;
	
	@Autowired
	private IParkingRepository parkingRepository;
	
	Log log = LogFactory.getLog(PayServiceImp.class);
	
	@Override
	public RetKit weixinPay(UnifiedOrderParams payParam) {
		JsPayResult result = null;
		// 统一下单
		String out_trade_no = PayUtil.createOutTradeNo();
		String nonce_str = PayUtil.createNonceStr(); // 随机数据
		
		Integer parkingInfoId = payParam.getParkingInfoId();
		Integer parkingLen = payParam.getParkingLen();
		BigDecimal totalFee = payParam.getTotalFee();
		payParam.setParkingInfoId(null);
		payParam.setParkingLen(null);
		payParam.setTotalFee(null);
		
		// 参数组装
		//UnifiedOrderParams unifiedOrderParams = new UnifiedOrderParams();
		
		payParam.setTotal_fee(multiplyFen(totalFee));
		payParam.setAppid(WeChatUtil.APPID);// 必须
		payParam.setMch_id(WeChatUtil.MCHID);// 必须
		payParam.setOut_trade_no(out_trade_no);// 必须
		payParam.setNonce_str(nonce_str); // 必须
		//payParam.setSpbill_create_ip("127.0.0.1"); // 必须
		payParam.setTrade_type("JSAPI"); // 必须
		payParam.setNotify_url(WeChatUtil.NOTIFY_URL);// 异步通知url
		// 统一下单 请求的Xml(正常的xml格式)
		String unifiedXmL = MsgUtil.abstractPayToXml(payParam);// 签名并入util
		// 返回<![CDATA[SUCCESS]]>格式的XML
		String unifiedOrderResultXmL = HttpReqUtil.HttpsDefaultExecute(SystemConstant.POST_METHOD,
				WeChatUtil.UNIFIED_ORDER_URL, null, unifiedXmL, null);
		// 进行签名校验
		try {
			if (SignatureUtil.checkIsSignValidFromWeiXin(unifiedOrderResultXmL, WeChatUtil.APIKEY, null)) {
				Map<String, String> map = XmlUtil.xmlToMap(unifiedOrderResultXmL);
				String timeStamp = PayUtil.createTimeStamp();
				// 统一下单响应
				result = new JsPayResult();
				result.setAppId(WeChatUtil.APPID);
				result.setTimeStamp(timeStamp);
				result.setNonceStr(map.get("nonce_str"));// 直接用返回的
				/**** prepay_id 2小时内都有效，再次支付方法自己重写 ****/
				result.setPackageStr("prepay_id=" + map.get("prepay_id"));
				/**** 用对象进行签名 ****/
				String paySign = SignatureUtil.createSign(result, WeChatUtil.APIKEY, SystemConstant.DEFAULT_CHARACTER_ENCODING);
				result.setPaySign(paySign);
				result.setResultCode(map.get("result_code"));
				QUser userEntity = QUser.user;
				User userPO = this.jpaQueryFactory.select(userEntity).from(userEntity).where(userEntity.openid.eq(payParam.getOpenid())).fetchFirst();
				if(userPO == null) {
					//新增用户
					userPO = new User();
					userPO.setOpenid(payParam.getOpenid());
					userPO.setType(BaseConstant.UserType.FrontUser.getTypeValue());  //保存用户类型
					Date nowDate = new Date();
					userPO.setCreateTime(nowDate); //注册时间
					userPO.setState(BaseConstant.UserState.EnabledState.getTypeValue());  //用户启用状态
					userPO.setFalseReportNumber(0);   //谎报次数	
					int balanceInt = 0;
					BigDecimal balance = new BigDecimal(balanceInt);
					userPO.setBalance(balance);
					userPO.setMemberId(BaseConstant.UserGrade.initMember.getTypeValue());  //用户会员等级 --初始为普通会员
					userPO.setIsBlack(BaseConstant.UserIsBlack.NormalUser.getTypeValue());   //用户是否为黑名单 -- 初始为正常用户
					userPO.setIsAuthentication(BaseConstant.UserAuthenticationStatus.initState.getTypeValue());  //初始为未认证
					userRepository.save(userPO);
				}
				
				QOrder orderEntity = QOrder.order;
				Order orderPO = this.jpaQueryFactory.select(orderEntity).from(orderEntity)
						.where(orderEntity.parkingInfoId.eq(parkingInfoId)).fetchFirst();
				if(orderPO ==  null) {
					orderPO = new Order();
				}
				
				
				orderPO.setOutTradeNo(out_trade_no);
				orderPO.setFee(totalFee);
				orderPO.setType(BaseConstant.OrderType.WeChat.getTypeValue());
				orderPO.setUserId(userPO.getId());
				orderPO.setParkingInfoId(parkingInfoId);
				orderPO.setParkingTime(parkingLen);
				orderPO.setFinalFee(totalFee);
				if(orderPO.getStatus() != BaseConstant.OrderStatus.AdvanceUnPay.getTypeValue()) {
					orderPO.setStatus(BaseConstant.OrderStatus.UnPay.getTypeValue());
				}
				orderRepository.save(orderPO);
				
				return RetKit.okData(result).set("code", 0).set("msg", "支付成功").set("outTradeNo", out_trade_no);
			} else {
			 return RetKit.fail("-1", "支付签名验证错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RetKit.fail("-1", "支付失败");
		}
	}

	@Override
	public RetKit writePayResult(Integer outTradeId) {
		try {
			QOrder orderEntity = QOrder.order;
			
			/*this.jpaQueryFactory.update(orderEntity)
					.set(orderEntity.status, BaseConstant.OrderStatus.AlreadyPay.getTypeValue())
					.where(orderEntity.id.eq(outTradeId)).execute();*/

			QParkingInfo parkingInfoEntity = QParkingInfo.parkingInfo;
			
			Order order = this.jpaQueryFactory.select(orderEntity).from(orderEntity).where(orderEntity.id.eq(outTradeId)).fetchFirst();
			

			ParkingInfo parking = this.jpaQueryFactory.select(parkingInfoEntity).from(parkingInfoEntity).where(parkingInfoEntity.id.eq(order.getParkingInfoId())).fetchFirst();
			//parkingInfo要修改出场状态
			if(order.getStatus() == BaseConstant.OrderStatus.AdvanceUnPay.getTypeValue()) {
				order.setStatus(BaseConstant.OrderStatus.AdvanceAlreadyPay.getTypeValue());
				//修改支付时间
				order.setPayTime(new Date());
				order = orderRepository.save(order);
			}else {
				if((parking.getStatus()).equals(BaseConstant.parkingInfoStatus.unfinishedStatus.getValue())) {
					
					//发送websocket
					QRoadway roadWayEntity = QRoadway.roadway;
					Roadway roadWayPoO = this.jpaQueryFactory.select(roadWayEntity).from(roadWayEntity)
							.where(roadWayEntity.id.eq(parking.getOutRoadWayId())).fetchFirst();
					QMainRoadway mainRoadWayEntity = QMainRoadway.mainRoadway;
					MainRoadway mainRoadWay = this.jpaQueryFactory.select(mainRoadWayEntity).from(mainRoadWayEntity).where(mainRoadWayEntity.roadwayId.eq(parking.getOutRoadWayId())).fetchFirst();
					
					QMainControl mainControlEntity = QMainControl.mainControl;
					MainControl mainControll = this.jpaQueryFactory.select(mainControlEntity).from(mainControlEntity).where(mainControlEntity.id.eq(mainRoadWay.getMainId())).fetchFirst();
	
					CloseableHttpClient httpclient = HttpClients.createDefault();
					StringBuffer url = new StringBuffer();
			
					url.append(WeChatUtil.DOMAIN_NAME_URL + "/websocket");
					url.append("/socketController/sendMessage");
					
					HttpPost post = new HttpPost(url.toString());
					String parms = "controlMac="+mainControll.getMac()+"&cameraMac="+roadWayPoO.getCameraMac()+"&command=1";
					StringEntity s = new StringEntity(parms);
		            s.setContentEncoding("UTF-8");
		            s.setContentType("application/x-www-form-urlencoded");//发送json数据需要设置contentType
		            post.setEntity(s);
		            HttpResponse response = httpclient.execute(post);
	
	        		//修改停车信息状态
		            log.info("-----showParkingInfoStatus1----"+ parking.getStatus());
	        		parking.setStatus(BaseConstant.parkingInfoStatus.finishedStatus.getValue());
	        		log.info("-----showParkingInfoStatus2----"+ parking.getStatus());
					parkingInfoRepository.save(parking);
					log.info("-----showParkingInfoStatus3----"+ parking.getStatus());
					
					//修改支付时间
					order.setPayTime(new Date());
					order.setStatus(BaseConstant.OrderStatus.AlreadyPay.getTypeValue());
					order = orderRepository.save(order);
		            if(response.getStatusLine().getStatusCode() == 200) {
		            	String conResult = EntityUtils.toString(response
		                        .getEntity());
		            	JSONObject sobj = new JSONObject();
		            	RetKit websocketRs = sobj.parseObject(conResult, RetKit.class);
		            	if(websocketRs.getBoolean("success")) {
		            		return RetKit.ok("解锁成功！");
		            	}else {
		            		return RetKit.fail("解锁失败！");
		            	}
		            }
				}
			}
			return RetKit.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return RetKit.fail();
		}
	}
	
	/**
	 * 出场开闸
	 */
	public RetKit openBrake(String parkingInfoId) {
		try {
			QParkingInfo parkingInfoEntity = QParkingInfo.parkingInfo;
			ParkingInfo parking = this.jpaQueryFactory.select(parkingInfoEntity).from(parkingInfoEntity).where(parkingInfoEntity.id.eq(Integer.parseInt(parkingInfoId))).fetchFirst();
				if(parking.getStatus() != BaseConstant.parkingInfoStatus.finishedStatus.getValue()) {
				//发送websocket
				QRoadway roadWayEntity = QRoadway.roadway;
				Roadway roadWayPoO = this.jpaQueryFactory.select(roadWayEntity).from(roadWayEntity)
						.where(roadWayEntity.id.eq(parking.getOutRoadWayId())).fetchFirst();
				QMainRoadway mainRoadWayEntity = QMainRoadway.mainRoadway;
				MainRoadway mainRoadWay = this.jpaQueryFactory.select(mainRoadWayEntity).from(mainRoadWayEntity).where(mainRoadWayEntity.roadwayId.eq(parking.getOutRoadWayId())).fetchFirst();
				
				QMainControl mainControlEntity = QMainControl.mainControl;
				MainControl mainControll = this.jpaQueryFactory.select(mainControlEntity).from(mainControlEntity).where(mainControlEntity.id.eq(mainRoadWay.getMainId())).fetchFirst();

				CloseableHttpClient httpclient = HttpClients.createDefault();
				StringBuffer url = new StringBuffer();
		
				url.append(WeChatUtil.DOMAIN_NAME_URL + "/websocket");
				url.append("/socketController/sendMessage");
				
				HttpPost post = new HttpPost(url.toString());
				String parms = "controlMac="+mainControll.getMac()+"&cameraMac="+roadWayPoO.getCameraMac()+"&command=1";
				StringEntity s = new StringEntity(parms);
	            s.setContentEncoding("UTF-8");
	            s.setContentType("application/x-www-form-urlencoded");//发送json数据需要设置contentType
	            post.setEntity(s);
	            HttpResponse response = httpclient.execute(post);
	            if(response.getStatusLine().getStatusCode() == 200) {
	            	String conResult = EntityUtils.toString(response
	                        .getEntity());
	            	JSONObject sobj = new JSONObject();
	            	RetKit websocketRs = sobj.parseObject(conResult, RetKit.class);
	            	if(websocketRs.getBoolean("success")) {
	            		//修改停车信息状态
	            		parking.setStatus(BaseConstant.parkingInfoStatus.finishedStatus.getValue());
	    				parkingInfoRepository.save(parking);
	            		return RetKit.ok("解锁成功！");
	            	}else {
	            		return RetKit.fail("解锁失败！");
	            	}
	            }
			}
			return RetKit.ok();
		} catch (Exception e) {
			// TODO: handle exception
			return RetKit.fail();
		}
		
	}
	
	
	/**
	 * 入口开闸
	 */
	public RetKit unlicensedEntryOpenBrake(String licensePlate,String roadWayId) {
		try {
			//QParkingInfo parkingInfoEntity = QParkingInfo.parkingInfo;
			//ParkingInfo parking = this.jpaQueryFactory.select(parkingInfoEntity).from(parkingInfoEntity).where(parkingInfoEntity.id.eq(Integer.parseInt(parkingInfoId))).fetchFirst();
				//if(parking.getStatus() != BaseConstant.parkingInfoStatus.finishedStatus.getValue()) {
				//发送websocket
				QRoadway roadWayEntity = QRoadway.roadway;
				Roadway roadWayPoO = this.jpaQueryFactory.select(roadWayEntity).from(roadWayEntity)
						.where(roadWayEntity.id.eq(Integer.parseInt(roadWayId))).fetchFirst();
				
				Optional<Parking> parkingOptional = parkingRepository.findById(roadWayPoO.getParkingId());
				
				QMainRoadway mainRoadWayEntity = QMainRoadway.mainRoadway;
				MainRoadway mainRoadWay = this.jpaQueryFactory.select(mainRoadWayEntity).from(mainRoadWayEntity).where(mainRoadWayEntity.roadwayId.eq(Integer.parseInt(roadWayId))).fetchFirst();
				
				QMainControl mainControlEntity = QMainControl.mainControl;
				MainControl mainControll = this.jpaQueryFactory.select(mainControlEntity).from(mainControlEntity).where(mainControlEntity.id.eq(mainRoadWay.getMainId())).fetchFirst();

				CloseableHttpClient httpclient = HttpClients.createDefault();
				StringBuffer url = new StringBuffer();
		
				url.append(WeChatUtil.DOMAIN_NAME_URL + "/websocket");
				url.append("/socketController/sendMessage");
				
				HttpPost post = new HttpPost(url.toString());
				String parms = "controlMac="+mainControll.getMac()+"&cameraMac="+roadWayPoO.getCameraMac()+"&command=1";
				StringEntity s = new StringEntity(parms);
	            s.setContentEncoding("UTF-8");
	            s.setContentType("application/x-www-form-urlencoded");//发送json数据需要设置contentType
	            post.setEntity(s);
	            HttpResponse response = httpclient.execute(post);
	            if(response.getStatusLine().getStatusCode() == 200) {
	            	String conResult = EntityUtils.toString(response
	                        .getEntity());
	            	JSONObject sobj = new JSONObject();
	            	RetKit websocketRs = sobj.parseObject(conResult, RetKit.class);
	            	if(websocketRs.getBoolean("success")) {
	            		ParkingInfo parkingInfo = new ParkingInfo();
						parkingInfo.setParkCode(parkingOptional.get().getParkCode());
						parkingInfo.setLicensePlate(licensePlate);
						parkingInfo.setParkDate(new Date());
						//parkingInfo.setUserId(user.getId());
						parkingInfo.setInRoadWayId(Integer.parseInt(roadWayId));
						parkingInfo.setIsLock(0);//0-不锁车
						parkingInfo.setCarType(2);//默认小汽车
						parkingInfo.setPlateType(1);//默认蓝牌
						parkingInfo.setStatus(BaseConstant.parkingInfoStatus.unfinishedStatus.getValue());
	            		//修改停车信息状态
	            		//parking.setStatus(BaseConstant.parkingInfoStatus.unfinishedStatus.getValue());
	            		//parking.setLicensePlate(licensePlate);
	    				parkingInfoRepository.save(parkingInfo);
	            		return RetKit.ok("解锁成功！");
	            	}else {
	            		return RetKit.fail("解锁失败！");
	            	}
	            }else {
	            	return RetKit.fail("开闸失败");
	            }
			//}
			//return RetKit.ok();
		} catch (Exception e) {
			// TODO: handle exception
			return RetKit.fail();
		}
		
	}
	
	/**
	 * 根据微信支付订单id删除指定order
	 * @param outTradeNo
	 * @return
	 */
	public RetKit deleteOrderByOutTradeNo(String outTradeNo) {
		try {
			Integer orderId = findOutTradeId(outTradeNo);
			orderRepository.deleteById(orderId);
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail();
		}
	}
	
	/**
	 * 根据微信支付订单id返回指定orderId
	 */
	public Integer findOutTradeId(String outTradeNo) {
		Order order = orderRepository.findOrderByOutTradeNo(outTradeNo);
		if(order!=null) {
			return order.getId();
		}else {
			return null;
		}
		 
	}
	
	/**
     * 金额乘100
     * */
    public Integer multiplyFen(BigDecimal fen) {
        BigDecimal yuan = fen.multiply(new BigDecimal(100));  
		return yuan.intValue();
	}
    
    
    @Override
	public RetKit payment(Map<String, String> map, String notifyUrl) {
		AlipayClient alipayClient;
		alipayClient = new DefaultAlipayClient(AliPayConfig.apiUrl, AliPayConfig.appId, AliPayConfig.privateKey,"json",
				AliPayConfig.charset, AliPayConfig.alipayPublicKey, "RSA2");
		
		String total_amount = map.get("total_fee").toString();
		String out_trade_no = map.get("out_trade_no");
		String authCode = map.get("authCode");
		log.warn("---------------------------------------------------");
		log.warn("total_amount="+total_amount+"out_trade_no="+out_trade_no+"authCode="+authCode);
		log.warn("---------------------------------------------------");
		try {
			
        	AlipaySystemOauthTokenRequest authTokenrequest = new AlipaySystemOauthTokenRequest();
            //值为authorization_code时，代表用code换取；值为refresh_token时，代表用refresh_token换取
    		authTokenrequest.setGrantType("authorization_code");
            //授权码，用户对应用授权后得到。
    		authTokenrequest.setCode(authCode);
            //刷新令牌，上次换取访问令牌时得到,即：AccessToken
            //request.setRefreshToken("43e3bee07f354cc5a7780446412bSX75");
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(authTokenrequest);
            log.warn("---------------------------------------------------");
    		log.warn("oauthTokenResponse="+oauthTokenResponse);
    		log.warn("---------------------------------------------------");
            if(oauthTokenResponse.isSuccess()){
                AlipayUserInfoShareRequest requestUser = new AlipayUserInfoShareRequest();
                   AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(requestUser, oauthTokenResponse.getAccessToken());
                   System.out.println("userinfoShareResponse=" + userinfoShareResponse);
                   if(userinfoShareResponse!=null){
                     String userId= userinfoShareResponse.getUserId();
                     log.warn("---------------------------------------------------");
             		log.warn("userId="+userId);
             		log.warn("---------------------------------------------------");
                     AlipayTradeCreateRequest  request = new AlipayTradeCreateRequest ();
         			request.setNotifyUrl(notifyUrl);
         			String content = "{" + "    \"out_trade_no\":\" "+ out_trade_no + "\"," 
         					+ "    \"total_amount\":\"" + total_amount + "\","
         					+ "    \"subject\":\" 停车收费 \"," 
         					+ "     \"body\":\" 停车收费\","
         					+"      \"buyer_id\":\" "+ userId +"\" "
         					+ "    }";
         			request.setBizContent(content);
         			
         			AlipayTradeCreateResponse responseApp = alipayClient.execute(request);
                    log.warn("---------------------------------------------------");
            		log.warn("userId="+responseApp);
            		log.warn("---------------------------------------------------");
            		log.warn("---------------------------------------------------");
            		log.warn("TradeNo()="+responseApp.getTradeNo());
            		log.warn("---------------------------------------------------");
         			return RetKit.ok(responseApp.getTradeNo());
               }
           }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return RetKit.fail();
	}

}
