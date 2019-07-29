package com.dchip.cloudparking.web.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 跨域访问
 * @author yhj
 * @date 2018年7月27日
 */
public class HttpClientUtil {

	public RetKit get(String userId, String sendMessage) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(MessageUtil.loadMessage("parking.domain")+":8094/automat/socket/sendMessage");
			System.out.println("executing request " + httpget.getURI());
			httpget.setHeader("userId", userId);
			httpget.setHeader("message", sendMessage);
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {

				// 获取响应实体
				HttpEntity entity = response.getEntity();
				String content = "" + EntityUtils.toString(entity);
				// 打印响应内容
				System.out.println("Response content: " + content);
				JSONObject sobj = JSONObject.parseObject(content);
				if (response.getStatusLine().getStatusCode() == 200) {
					// 打印响应内容长度
					// System.out.println("Response content length: " + entity.getContentLength());
					return RetKit.ok(sobj.get("msg"));
				}
				return RetKit.fail(sobj.get("msg"));
			} finally {
				response.close();
			}

		} catch (Exception e) {
			return RetKit.fail();
			// TODO: handle exception
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
