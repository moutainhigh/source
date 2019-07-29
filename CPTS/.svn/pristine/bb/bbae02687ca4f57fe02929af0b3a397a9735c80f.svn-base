package com.dchip.cloudparking.web.controller;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iRepository.ICashApplyRecordRepository;
import com.dchip.cloudparking.web.iService.IOrderService;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ICashApplyRecordRepository cashApplyRecordRepository;
	
	/**
	 * 显示财务报表
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		Map<String, Object> data = orderService.getData();
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		request.setAttribute("fee", data.get("fee"));
		request.setAttribute("type", ((UserAuthentic)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRoleType());
		return "order/index";
	}
	
	//提现列表
	@RequestMapping("/withdraw")
	public String withdraw(HttpServletRequest request) {
		List<String> companyName = cashApplyRecordRepository.findCompanyName();
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		request.setAttribute("companyName", companyName);
		request.setAttribute("type", ((UserAuthentic)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRoleType());
		return "order/withdraw";
	}
	
	//提现列表的数据
	@RequestMapping("/getWithdrawData")
	@ResponseBody
	public Object getWithdrawData(HttpServletRequest request) {
		// 分页条件
		Integer pageSize = Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = Integer.parseInt(request.getParameter("page")) - 1;
		// 排序条件
		String sortName = request.getParameter("sortName");
		String direction = request.getParameter("direction");
		String searchCompany = request.getParameter("searchCompany");
		List<Map<String, Object>> para = new ArrayList<>();
		if (StrKit.notBlank(searchCompany)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchCompany", searchCompany);
			para.add(map);
		}
		return orderService.getWithdrawData(pageSize, pageNum, sortName, direction, para);
	}
	
	/**
	 * 财务报表的数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/rending")
	@ResponseBody
	public Object rending(HttpServletRequest request) {
		// 分页条件
		Integer pageSize = Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = Integer.parseInt(request.getParameter("page")) - 1;
		// 排序条件
		String sortName = request.getParameter("sortName");
		String direction = request.getParameter("direction");
		// 动态搜索参数
		String searchParking = request.getParameter("searchParking");
		String searchStartTime = request.getParameter("searchStartTime");
		String searchEndTime = request.getParameter("searchEndTime");
		String payType = request.getParameter("payType");
		String payStatus = request.getParameter("payStatus");
		String isTransfer = request.getParameter("isTransfer");

		List<Map<String, Object>> para = new ArrayList<>();
		if (StrKit.notBlank(searchParking)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchParking", searchParking);
			para.add(map);
		}
		if (StrKit.notBlank(searchStartTime)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchStartTime", searchStartTime);
			para.add(map);
		}
		if (StrKit.notBlank(searchEndTime)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchEndTime", searchEndTime);
			para.add(map);
		}
		if (StrKit.notBlank(payType)) {
			Map<String, Object> map = new HashMap<>();
			map.put("payType", payType);
			para.add(map);
		}
		if (StrKit.notBlank(payStatus)) {
			Map<String, Object> map = new HashMap<>();
			map.put("payStatus", payStatus);
			para.add(map);
		}
		if (StrKit.notBlank(isTransfer)) {
			Map<String, Object> map = new HashMap<>();
			map.put("isTransfer", isTransfer);
			para.add(map);
		}

		return JSON.toJSON(orderService.getOrderList(pageSize, pageNum, sortName, direction, para));
	}
	
	@RequestMapping("/applyWithdrawal")
	@ResponseBody
	public RetKit applyWithdrawal(HttpServletRequest request) {
		String orderIds = request.getParameter("orderIds");
		String fee = request.getParameter("fee");
		return orderService.applyWithdrawal(orderIds, fee);
	}
	
	@RequestMapping("/getWithdrawalInfo")
	@ResponseBody
	public RetKit getWithdrawalInfo() {
		return orderService.getWithdrawalInfo();
	}
	
	/**
	 * 同意申请
	 * @param request
	 * @return
	 */
	@RequestMapping("/agreeToApply")
	@ResponseBody
	public RetKit agreeToApply(HttpServletRequest request) {
		return checkWithdraw(request);
	}
	
	/**
	 * 确认转账
	 * @param request
	 * @return
	 */
	@RequestMapping("/transferAccounts")
	@ResponseBody
	public RetKit transferAccounts(HttpServletRequest request) {
		return checkWithdraw(request);
	}
	
	@RequestMapping("/checkWithdraw")
	@ResponseBody
	public RetKit checkWithdraw(HttpServletRequest request) {
		String cashApplyRecordId = request.getParameter("id");
		String moneyStatus = request.getParameter("moneyStatus");
		String applyStatus = request.getParameter("applyStatus");
		return orderService.checkWithdraw(cashApplyRecordId, moneyStatus, applyStatus);
	}
	
	@RequestMapping("/getDetails")
	@ResponseBody
	public Object getDetail(HttpServletRequest request) {
		String orderIds = request.getParameter("orderIds");
		// 分页条件
		Integer pageSize = Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = Integer.parseInt(request.getParameter("page")) - 1;
		// 排序条件
		String sortName = request.getParameter("sortName");
		String direction = request.getParameter("direction");
		return JSON.toJSON(orderService.getDetail(orderIds,pageSize,pageNum,sortName,direction));
	}
	
	@RequestMapping("/getOrderData")
	@ResponseBody
	public RetKit getOrderData(HttpServletRequest request) {
		String companyId = request.getParameter("companyId");
		if (companyId == null) {
			return RetKit.fail("公司ID为空！");
		}else {
			return orderService.getParkingIncomeData(companyId);
		}
	}
	
	@RequestMapping("/getCardData")
	@ResponseBody
	public RetKit getCardData(HttpServletRequest request) {
		String companyId = request.getParameter("companyId");
		if (companyId == null) {
			return RetKit.fail("公司ID为空！");
		}else {
			return orderService.getCardIncomeData(companyId);
		}
	}

	/**
	 * 导出提现列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportApplyCashExcel")
	public Integer exportApplyCashExcel(HttpServletRequest request, HttpServletResponse response) {
		String searchCompany = request.getParameter("searchCompany");

		List<Map<String, Object>> para = new ArrayList<>();
		if (StrKit.notBlank(searchCompany)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchCompany", searchCompany);
			para.add(map);
		}
		orderService.exportApplyCashExcel(para, request, response);
		return null;
	}
	
	@RequestMapping("/exportExcelCompany")
	public Integer exportExcelCompany(HttpServletRequest request, HttpServletResponse response) {
		String orderIds = request.getParameter("orderIds");
		orderService.exportExcelCompany(orderIds, request, response);
		return null;
	}


}
