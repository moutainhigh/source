package com.dchip.cloudparking.api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.smsweb.www.servlet.anna.AnnaHttpSendResponseObject;
import com.smsweb.www.servlet.anna.SDKProxy;

/**
 * 
 * @ClassName: CytSdkUtil
 * @Description: TODO( 维纳多短信接口)
 * @author 小徐同学
 * @date 2017年2月6日
 *
 */
@Component
@PropertySource("classpath:application-cytsdk.properties")
public class CytSdk {
public static final String SEND_SUCC = "0";
	
	@Value("${message.url}")
	private  String url ;
	@Value("${message.account}")
	private  String account;
	@Value("${message.pwd}")
	private  String pwd;
	public  RetKit sendMessage(String phone, String msg) {
		SDKProxy sdk2 = new SDKProxy();
		sdk2.init(url,account,pwd);
		AnnaHttpSendResponseObject oneresp = sdk2.send(phone, msg);
		if (oneresp.getErrid().equals(SEND_SUCC)) {
			return RetKit.ok("发送成功");
		}
		return RetKit.fail(getCode(Integer.parseInt(oneresp.getErrid())));
	}

	public  String getCode(int Code) {
		switch (Code) {
		case 6002:
			return "用户帐号不正确";
		case 6008:
			return "无效的手机号码";
		case 6009:
			return "手机号码是黑名单";
		case 6010:
			return "用户密码不正确";
		case 6011:
			return "短信内容超过了最大长度限制";
		case 6012:
			return "该企业用户设置了ip限制 ";
		case 6013:
			return "该企业用户余额不足";
		case 6014:
			return "发送短信内容不能为空";
		case 6015:
			return "发送内容中含非法字符";
		case 6019:
			return "账户已停机，请联系客服";
		case 6021:
			return "扩展号码未备案";
		case 6023:
			return "发送手机号码超过太长，已超过300个号码";
		case 6024:
			return "定制时间不正确";
		case 6025:
			return "扩展号码太长（总长度超过20位） ";
		case 6080:
			return "提交异常，请联系服务商解决 ";
		case 6085:
			return "短信内容为空";
		default:
			return "发送成功";
		}
	}
}
