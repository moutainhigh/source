package com.dchip.cloudparking.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.IOrderService;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;
/**
 * 账单
 * @author 鼎芯科技
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private IOrderService iOrderService;
	/**
	 * 根据用户查询所有账单
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOrders")
	public RetKit getOrders(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));
		return iOrderService.getOrders(userId, pageSize, pageNum);
	}

	/**
	 * 账单明细
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOrderDetail")
	public RetKit getOrderDetail(HttpServletRequest request) {
		String orderId = request.getParameter("orderId");
		return iOrderService.getOrderDetail(orderId);
	}

	/**
	 * 按月查询总消费、消费明细接口 
	 * by 小梁
	 */
	@RequestMapping("/findMonthOrder")
	public RetKit findMonthOrder(HttpServletRequest request) {
		try {
			Integer userId = Integer.valueOf(request.getParameter("userId"));
			String payTime = request.getParameter("payTime");
			if (StrKit.isBlank(payTime)) {
				return RetKit.fail("payTime不能为空");
			}
			return iOrderService.findMonthOrder(userId, payTime);
		} catch (Exception e) {
			return RetKit.fail();
		}
	}

	/**
	 * 按月维度查询当前年份消费柱状图数据接口 
	 * by 小梁
	 */
	@RequestMapping("/findFeeByUserIdAndYear")
	public RetKit findFeeByUserIdAndTime(HttpServletRequest request) {
		try {
			Integer userId = Integer.valueOf(request.getParameter("userId"));
			String year = request.getParameter("year");
			if (StrKit.isBlank(year)) {
				return RetKit.fail("year不能为空");
			}
			return iOrderService.findFeeByUserIdAndYear(userId, year);
		} catch (Exception e) {
			return RetKit.fail();
		}
	}

	/**
	 * 改变订单状态，并根据订单费用扣除用户余额
	 * by 小梁
	 */
	@RequestMapping("/changeOrderStatus")
	public RetKit changeOrderStatus(HttpServletRequest request) {
		try {
			Integer orderId = Integer.valueOf(request.getParameter("orderId"));
			return iOrderService.changeOrderStatus(orderId);
		} catch (Exception e) {
			return RetKit.fail();
		}
	}
}
