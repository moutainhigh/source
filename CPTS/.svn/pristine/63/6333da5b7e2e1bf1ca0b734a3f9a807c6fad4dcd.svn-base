package com.dchip.cloudparking.api.serviceImp;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.iRepository.IOrderRepository;
import com.dchip.cloudparking.api.iRepository.IUserRepository;
import com.dchip.cloudparking.api.iService.IOrderService;
import com.dchip.cloudparking.api.model.po.Order;
import com.dchip.cloudparking.api.model.po.User;
import com.dchip.cloudparking.api.utils.RetKit;
@Service
public class OrderServiceImp extends BaseService implements IOrderService {

	@Autowired
	private IOrderRepository iOrderRepository;
	
	@Autowired
	private IUserRepository userRepository;

	@Override
	public RetKit getOrders(String userId, Integer pageSize, Integer pageNum) {
		Integer totalElements = iOrderRepository.findOrdersCount(userId);
		if(totalElements == null) {
			totalElements = 0;
		}
		Integer totalPages = (int) Math.ceil(totalElements / pageSize);
		Map<String, Object> result = new HashMap<>();
		result.put("content", iOrderRepository.findOrders(userId, pageSize, pageNum * pageSize));
		result.put("totalElements", totalElements);
		result.put("totalPages", totalPages);
		return RetKit.okData(result);
	}

	@Override
	public RetKit getOrderDetail(String orderId) {
		return RetKit.okData(iOrderRepository.findOrderDetail(orderId));
	}

	/**
	 * 按月查询总消费、消费明细接口 
	 * by 小梁
	 */
	public RetKit findMonthOrder(Integer userId, String payTime) {
		User user = userRepository.findUserById(userId);
		if(user==null) {
			return RetKit.fail("找不到用户");
		}
		try {
			List<Map<String, Object>> orderList = iOrderRepository.findAllCousumption(userId,user.getLicensePlat(),payTime);
			BigDecimal monthFee=new BigDecimal(iOrderRepository.findMonthFee(userId, payTime).toString());
			Map<String, Object> map = new HashMap<>();
			map.put("monthFee", monthFee);
			map.put("orderList", orderList);
			return RetKit.okData(map);
		} catch (Exception e) {
			e.printStackTrace();
			return RetKit.fail();
		}
	}

	/**
	 * 按月维度查询当前年份消费柱状图数据接口 
	 * by 小梁
	 */
	public RetKit findFeeByUserIdAndYear(Integer userId, String year) {
		try {
			List<Map<String, Object>> feeList = iOrderRepository.findFeeByUserIdAndYear(userId, year);
			return RetKit.okData(feeList);
		} catch (Exception e) {
			return RetKit.fail();
		}
	}

	/**
	 * 改变订单状态，并根据订单费用扣除用户余额
	 * by 小梁
	 */
	public RetKit changeOrderStatus(Integer orderId) {
		try {
			Optional<Order> orderOpt = iOrderRepository.findById(orderId);
			if (!orderOpt.isPresent()) {
				return RetKit.fail("订单id有误");
			}
			Order order = orderOpt.get();
			if (order.getStatus() == BaseConstant.OrderStatus.AlreadyPay.getTypeValue()) {
				return RetKit.fail("该订单已支付");
			}
			Optional<User> userOpt = userRepository.findById(order.getUserId());
			if (!userOpt.isPresent()) {
				return RetKit.fail("订单信息有误");
			}
			User user = userOpt.get();
			BigDecimal fee;
			if (order.getDiscountAmount() == null) {
				fee = order.getFee();
			}else {
				fee = order.getFee().subtract(order.getDiscountAmount());
			}
			BigDecimal balance;
			if (fee == null) {
				balance = user.getBalance();
			}else {
				balance = user.getBalance().subtract(fee);
			}
			if (balance.compareTo(BigDecimal.ZERO) == -1) { // 为true就是负数
				return RetKit.fail("余额不足");
			}
			user.setBalance(balance);
			user = userRepository.save(user);
			order.setStatus(BaseConstant.OrderStatus.AlreadyPay.getTypeValue());
			iOrderRepository.save(order);
			Integer count = iOrderRepository.findUnpayCountByUserId(order.getUserId());
			if (count == 0) {
				if (user.getIsBlack() == BaseConstant.UserIsBlack.BlacklistUser.getTypeValue()) {
					user.setIsBlack(BaseConstant.UserIsBlack.NormalUser.getTypeValue());
					userRepository.save(user);
				}
			}
			return RetKit.ok("支付成功");
		} catch (Exception e) {
			return RetKit.fail();
		}
	}

}
