package com.dchip.cloudparking.api.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class QiniuUtil {
	// #七牛云
	private static String access_key = "DICBmZWLklcxycI_NXnhAp5Rq-uJHpOfH_f7j-Np";
	private static String secret_key = "mbVczuIhqz0ketPM3Iz6twhDiqFB0HqiIQJv5jkS";
	private static String domain = "cloudparking.z-chip.com";
	// 地区：http://up-z2.qiniu.com/
	static String bucketZone = "http://up-z2.qiniu.com/";
	// 要上传的空间
	static String bucketname = "dchip-cloudparking";
	// 访问ip或域名地址
	static String url = "http://cloudparking.z-chip.com/";

	static UploadManager uploadManager = new UploadManager(new Configuration());

	// 密钥配置
	static Auth auth = Auth.create(access_key, secret_key);

	// 获取domain
	public static String getDomain() {
		return domain;
	}

	// 获取bucketZone
	public static String getBucketZone() {
		return bucketZone;
	}

	// 获取上传token
	public static String getUpToken() {
		return auth.uploadToken(bucketname);
	}

	// 删除：key为文件名
	public static void delete(String key) throws QiniuException {
		BucketManager bucketManager = new BucketManager(auth, new Configuration());
		bucketManager.delete(bucketname, key);
	}

	public static String upload(File fileName) throws IOException {
		try {
			// 调用put方法上传
			String f = fileName.getName();
			String randomName = StrKit.getRandomUUID();
			String name = randomName + "." + f.substring(f.lastIndexOf(".") + 1);
			uploadManager.put(fileName, name, getUpToken());
			return name;
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
		return null;
	}

	// 访问ip或域名地址
	public static String getUrl() {
		return url;
	}
}
