package com.dchip.cloudparking.api.iService;

import com.dchip.cloudparking.api.utils.RetKit;

public interface IOrderService {

	// 获取订单列表
	RetKit getOrders(String userId, Integer pageSize, Integer pageNum);

	// 获取某张订单的明细
	RetKit getOrderDetail(String orderId);

	/**
	 * 按月查询总消费、消费明细接口 
	 * by 小梁
	 */
	public RetKit findMonthOrder(Integer userId, String payTime);

	/**
	 * 按月维度查询当前年份消费柱状图数据接口 
	 * by 小梁
	 */
	public RetKit findFeeByUserIdAndYear(Integer userId, String year);

	/**
	 * 改变订单状态，并根据订单费用扣除用户余额
	 * by 小梁
	 */
	public RetKit changeOrderStatus(Integer orderId);
}
