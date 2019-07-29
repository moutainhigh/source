package com.dchip.cloudparking.api.utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class SocketKit {
	
	//线上域名
	public static final String DOMAIN_NAME_URL = MessageUtil.loadMessage("parking.domain");
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(SocketKit.class);
	
	/**
	 * 给socket发送信息
	 * @param controlMac
	 * @param cameraMac
	 * @param command
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@SuppressWarnings("static-access")
	public static RetKit sendMessage(String controlMac, String cameraMac, String command) throws ClientProtocolException, IOException {
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			StringBuffer url = new StringBuffer();
	
			url.append(DOMAIN_NAME_URL + "/websocket");
			url.append("/socketController/sendMessage");
			
			HttpPost post = new HttpPost(url.toString());
			String parms = "controlMac="+controlMac+"&cameraMac="+cameraMac+"&command="+command;
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
	        		return RetKit.ok("发送成功！");
	        	}else {
	        		return RetKit.fail("发送失败！");
	        	}
	        }
	        return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail();
		}
        
	}
	
	public static RetKit sendGroundLockMessage(String mac, String groundUid, String type, String groundCommond) throws ClientProtocolException, IOException {
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			StringBuffer url = new StringBuffer();
			url.append(DOMAIN_NAME_URL + "/websocket");
//			url.append("http://localhost/websocket");
			url.append("/socketController/sendGroundLockMessage");
			
			HttpPost post = new HttpPost(url.toString());
			String parms = "mac="+mac+"&groundUid="+groundUid+"&type="+type+"&groundCommond="+groundCommond;
			StringEntity s = new StringEntity(parms);
	        s.setContentEncoding("UTF-8");
	        s.setContentType("application/x-www-form-urlencoded");//发送json数据需要设置contentType
	        post.setEntity(s);
	        
	        HttpResponse response = httpclient.execute(post);
	        if(response.getStatusLine().getStatusCode() == 200) {
	        	String conResult = EntityUtils.toString(response.getEntity());
	        	RetKit websocketRs = JSONObject.parseObject(conResult, RetKit.class);
	        	if(websocketRs.getBoolean("success")) {
	        		return RetKit.ok("发送成功！");
	        	}else {
	        		return websocketRs;
	        	}
	        }
	        return RetKit.fail("发送失败！");
		} catch (Exception e) {
			return RetKit.fail();
		}
        
	}
	
}
