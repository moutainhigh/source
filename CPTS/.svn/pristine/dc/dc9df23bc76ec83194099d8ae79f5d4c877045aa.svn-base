package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dchip.cloudparking.web.utils.RetKit;

public interface IFinanceService {

	public Object getFinanceList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para);
	
	public RetKit changeFinanceStatus(Integer financeId, String name, String person, String account);
	
	public Object getClearingLogList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para);	
	
	public void financeExcel(List<Map<String, Object>> para, HttpServletRequest request, HttpServletResponse response);
	
	public void clearingLogExport(List<Map<String, Object>> para, HttpServletRequest request, HttpServletResponse response);

}
