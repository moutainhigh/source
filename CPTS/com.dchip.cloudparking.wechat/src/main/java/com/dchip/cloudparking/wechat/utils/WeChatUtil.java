package com.dchip.cloudparking.wechat.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class WeChatUtil {
	
	//鼎芯科技服务号APPID
	public static final String APPID = "wxc84679c3bcc8d70f";
	public static final String APPSECRET = "6fd2655e8cfc19de314f3551513fe137";
	
	//线上域名
	public static final String DOMAIN_NAME_URL = "http://cloudparking.d-chip.com";

	public static final String MCHID = "1507760871";// 商户号
	public static final String APIKEY = "zhuhaidchip2018companycontacters";// API密钥
	
	// 异步通知URL
	public static final String NOTIFY_URL = "http://cloudparking.d-chip.com/cloudParkingWechat/payment/notify";

	// 微信支付统一接口(POST)
	public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	public static JSONObject doGetJson(String url) throws ClientProtocolException, IOException {
		JSONObject jsonObject = null;
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if(entity != null) {
			String result = EntityUtils.toString(entity,"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		httpGet.releaseConnection();
		return jsonObject;
	}
	
	public static JSONObject doPostStr(String url,String OutStr) {
		JSONObject jsonObject = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setEntity(new StringEntity(OutStr,"UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(),"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * 获取access_token
	 * @param getTokenUrl
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getAccess_Token(String getTokenUrl) throws ClientProtocolException, IOException {
		JSONObject tokenObject = WeChatUtil.doGetJson(getTokenUrl);
		String access_token = tokenObject.getString("access_token");
		return access_token;
	}
	
	/**
	 * 获取jsapi_ticket
	 * @param jsapiUrl
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getJsapi_Ticket(String jsapiUrl) throws ClientProtocolException, IOException {
		JSONObject jsapiInfo = WeChatUtil.doGetJson(jsapiUrl);
		String jsapi_ticket = jsapiInfo.getString("ticket");
		return jsapi_ticket;
	}
	
	
	
}
