package com.dchip.cloudparking.web.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.ICompanyBalanceWayRepository;
import com.dchip.cloudparking.web.iRepository.IFinanceRepository;
import com.dchip.cloudparking.web.iRepository.IMonthlyCardPayRepository;
import com.dchip.cloudparking.web.iRepository.IOrderRepository;
import com.dchip.cloudparking.web.iRepository.IParkingRepository;
import com.dchip.cloudparking.web.model.po.CompanyBalanceWay;
import com.dchip.cloudparking.web.model.po.Finance;
import com.dchip.cloudparking.web.model.po.MonthlyCardPay;
import com.dchip.cloudparking.web.model.po.Order;
import com.dchip.cloudparking.web.model.po.Parking;

@Component
public class ScheduledTask {

	@Autowired
	private IParkingRepository parkingRepository;
	
	@Autowired
	private ICompanyBalanceWayRepository companyBalanceWayRepository;
	
	@Autowired
	private IOrderRepository orderRepository;
	
	@Autowired
	private IFinanceRepository financeRepository;
	
	@Autowired
	private IMonthlyCardPayRepository monthlyCardPayRepository;

	/**
	 * 月初清理 order 表
	 * by 小梁
	 */
	@Scheduled(cron="0 0 0 1 * ?")
	public void orderLiquidation() {
		List<Parking> parkingList = parkingRepository.findAll();
		for (Parking parking : parkingList) {
			List<Order> orderList = orderRepository.orderLiquidation(parking.getId());
			BigDecimal sumFinalFee = new BigDecimal(0);
			for (Order order : orderList) {
				if (order.getDiscountAmount() != null) {
					order.setIsTransfer(BaseConstant.OrderIsTransferStauts.AlreadyTransfer.getTypeValue());
					if (orderRepository.save(order) != null) {
						sumFinalFee = sumFinalFee.add(order.getFinalFee());
					}
				}
			}
			CompanyBalanceWay companyBalanceWay = companyBalanceWayRepository.findIsFirst(parking.getCompanyId());
			if (companyBalanceWay == null) {
				companyBalanceWay = companyBalanceWayRepository.findNewWay(parking.getCompanyId());
			}
			Finance finance = new Finance();
			finance.setStatus(BaseConstant.FinanceStatus.pendingFlag.getTypeValue());
			finance.setChargeTime(new Date());
			finance.setCreateTime(new Date());
			finance.setParkingId(parking.getId());
			finance.setTotalAmount(sumFinalFee);
			finance.setCompanyId(parking.getCompanyId());
			finance.setType(BaseConstant.FinanceType.orderType.getTypeValue());
			if (companyBalanceWay != null) {
				finance.setCompanyBalcanceWayId(companyBalanceWay.getId());
			}
			financeRepository.save(finance);
		}
	}

	/**
	 * 月初清理 monthlyCard 表
	 * by 小梁
	 */
	@Scheduled(cron="0 0 0 1 * ?")
	public void monthlyCardPayLiquidation() {
		List<Parking> parkingList = parkingRepository.findAll();
		for (Parking parking : parkingList) {
			List<MonthlyCardPay> monthlyCardPayList = monthlyCardPayRepository.findNoPay(parking.getId());
			BigDecimal sumMoney = new BigDecimal(0);
			for (MonthlyCardPay monthlyCardPay : monthlyCardPayList) {
				if (monthlyCardPay.getPaymentMoney() != null) {
					monthlyCardPay.setIsTransfer(BaseConstant.MonthlyCardIsTransferStauts.AlreadyTransfer.getTypeValue());
					if (monthlyCardPayRepository.save(monthlyCardPay) != null) {
						sumMoney = sumMoney.add(monthlyCardPay.getPaymentMoney());
					}
				}
			}
			CompanyBalanceWay companyBalanceWay = companyBalanceWayRepository.findIsFirst(parking.getCompanyId());
			if (companyBalanceWay == null) {
				companyBalanceWay = companyBalanceWayRepository.findNewWay(parking.getCompanyId());
			}
			Finance finance = new Finance();
			finance.setStatus(BaseConstant.FinanceStatus.pendingFlag.getTypeValue());
			finance.setChargeTime(new Date());
			finance.setCreateTime(new Date());
			finance.setParkingId(parking.getId());
			finance.setTotalAmount(sumMoney);
			finance.setCompanyId(parking.getCompanyId());
			finance.setType(BaseConstant.FinanceType.monthlyCardType.getTypeValue());
			if (companyBalanceWay != null) {
				finance.setCompanyBalcanceWayId(companyBalanceWay.getId());
			}
			financeRepository.save(finance);
		}
	}
	
}
