package com.dchip.cloudparking.api.iService;

import com.dchip.cloudparking.api.model.po.Session;
import com.dchip.cloudparking.api.utils.RetKit;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface IUserService {
	
	/**
	 * 普通用户登录操作
	 * @param phone
	 * @return
	 */
	RetKit userLoginOperation(String phone,String ip,String registrationId,Integer platform, String licensePlat);
	
	/**
	 * 演示帐号登录操作
	 * @return
	 */
	RetKit DemoUserLoginOperation(String phone,String ip,String registrationId,Integer platform, String licensePlat);
	
	//获取总积分
	RetKit getTotalScore(String userId);	
	
	//获取积分明细
	RetKit getScoreDetail(String userId ,String year,String month);	

	/**
	 * 查看登录token是否过期
	 */
	Session findSessionByToken(String token);

	/**
	 * 查看登录token是否过期
	 */
	Session findSessionByTokenAndType(String token,int type);

	/**
	 * 查看优惠
	 * @param userId
	 * @return
	 */
	RetKit checkDiscounts(String userId,String amount);

	/**
	 * 用户退出登录操作
	 * @param session
	 * @return
	 */
	RetKit userlogOut(Session session);

	/**
	 * 账户余额查询接口
	 * by 小梁
	 */
	RetKit getBalance(Integer userId);
	
	/**
	 * 获取用户详情
	 * @param userId
	 * @return
	 */
	RetKit getUserDetails(String userId);
	
	RetKit changePhone(String userId,String newPhone);
	
	/**
	 * 获取用户是否认证的flag   0-未认证  1-认证通过   2-认证不通过
	 * @param userId
	 * @return
	 */
	RetKit getAuthenticationFlag(String userId);
	
	/**
	 * 余额到账
	 */
	RetKit recharge(String out_trade_no);
	
	/**
	 * 获取用户的优惠券
	 * @param userId
	 * @return
	 */
	RetKit getUserCoupons(String userId,String status);

	/**
	 * 根据车牌查找用户id
	 * @param licensePlate
	 * @return
	 */
    Integer findIdByLicensePlate(String licensePlate);

	/**
	 * 活动优惠券列表
	 * hrd
	 * @return
	 */
	Object getActivityCoupons(long userId,Integer pageSize, Integer pageNum, List<Map<String,Object>> para);
	/**
	 * 绑定活动优惠券
	 * hrd
	 * @param userId
	 * @return
	 * @throws ParseException 
	 */
    RetKit bindActivityCoupon(Integer userId,Integer activityId) throws ParseException;

}
