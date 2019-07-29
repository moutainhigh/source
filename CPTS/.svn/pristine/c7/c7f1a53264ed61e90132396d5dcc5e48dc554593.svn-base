package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.utils.RetKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IOrderService {

	Object getOrderList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para);

	RetKit applyWithdrawal(String orderIds,String fee);
	
	RetKit getWithdrawalInfo();
	
	Object getWithdrawData(Integer pageSize, Integer pageNum, String sortName, String direction,List<Map<String, Object>> para);

	RetKit checkWithdraw(String cashApplyRecordId, String moneyStatus, String applyStatus);

	Map<String, Object> getData();
	
	RetKit getParkingIncomeData(String companyId);

	RetKit getCardIncomeData(String companyId);

	Object getDetail(String orderIds, Integer pageSize, Integer pageNum, String sortName, String direction);

	void exportApplyCashExcel(List<Map<String, Object>> para, HttpServletRequest request, HttpServletResponse response);

	void exportExcelCompany(String orderIds, HttpServletRequest request, HttpServletResponse response);

}
