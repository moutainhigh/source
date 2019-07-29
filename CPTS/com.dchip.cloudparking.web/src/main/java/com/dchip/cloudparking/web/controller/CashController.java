package com.dchip.cloudparking.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iService.ICashService;
import com.dchip.cloudparking.web.iService.IParkingUserService;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.SocketKit;
import com.dchip.cloudparking.web.utils.StrKit;


@Controller
@RequestMapping("/cash")
public class CashController {
	 @Autowired
	 private IParkingUserService parkingUserService;

	@Autowired
	private ICashService cashService;
	
	/**
	 * 保安现金收费页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer parkingId = parkingUserService.findParkingId(user.getUserName());
    	
		List<Map<String, Object>> roadwayList = cashService.getOutRoadWayListByLoginUser();
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		request.setAttribute("roadwayList", roadwayList);
		request.setAttribute("parkingId", parkingId);
		return "cash/index";
	}
	
	@RequestMapping("/rendTable")
	@ResponseBody
	public Object rendTable(HttpServletRequest request) {

		// 分页条件
		Integer pageSize = Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = Integer.parseInt(request.getParameter("page")) - 1;
		
		// 排序条件
		String sortName = request.getParameter("sortName");
		String direction = request.getParameter("direction");
		
		// 动态搜索参数
		String searchRoadWayId = request.getParameter("searchRoadWayId");
		
		List<Map<String, Object>> para = new ArrayList<>();
		
		if (StrKit.notBlank(searchRoadWayId)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchRoadWayId", searchRoadWayId);
			para.add(map);
		}

		return JSON.toJSON(cashService.getCashList(pageSize, pageNum, sortName, direction, para));
	}
	
	/**
	 * 保安收费流水列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/runningAccount")
	public String runningAccount(HttpServletRequest request) {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer parkingId = parkingUserService.findParkingId(user.getUserName());
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
    	request.setAttribute("parkingId", parkingId);
    	
		return "cash/runningAccount";
	}
	
	@RequestMapping("/rendering")
	@ResponseBody
	public Object rendering(HttpServletRequest request) {
		//分页条件
		Integer pageSize = Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = Integer.parseInt(request.getParameter("page"))-1;
		//排序条件
		String sortName =  request.getParameter("sortName");
		String direction = request.getParameter("direction");
		List<Map<String, Object>> para = new ArrayList<>();
		
		
		return JSON.toJSON(cashService.getOrder(pageSize, pageNum, sortName, direction, para));
	}
	
	/**
	 * 结算停车费用
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/settleCost")
	@ResponseBody
	public RetKit manualSettlement(HttpServletRequest request) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String parkDateStr = request.getParameter("parkDate");
		  if (StrKit.isBlank(parkDateStr)) {
			  return RetKit.fail("入场时间为空");
		  }
		Date parktDate =  formatter.parse(parkDateStr);
		
		String outDateStr = request.getParameter("outDate");
		  if (StrKit.isBlank(outDateStr)) {
			  return RetKit.fail("出场时间为空");
		  }
		Date outDate =  formatter.parse(outDateStr);
		
		String licensePlat = request.getParameter("licensePlate");
		  if(StrKit.isBlank(licensePlat)) {
			 return RetKit.fail("车牌为空");
		  }
		 
		
		String parkingIdStr = request.getParameter("parkingId");
		  if(StrKit.isBlank(parkingIdStr)) {
			 return RetKit.fail("停车场ID为空");
		  }
		Integer parkingId = Integer.parseInt(parkingIdStr);
        
		return cashService.settlement(licensePlat, parkingId, parktDate, outDate);
	}
	/**
	 * 保存人工结算操作的数据
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@RequestMapping("/generateOrder")
	@ResponseBody
	public RetKit saveManualSettleAction(HttpServletRequest request) throws ClientProtocolException, IOException {
		String parkingInfoIdStr = request.getParameter("parkingInfoId");
		if(StrKit.isBlank(parkingInfoIdStr)) {
			return RetKit.fail("停车信息ID为空！");
		}
		Integer parkingInfoId =Integer.parseInt(parkingInfoIdStr);
		
		String FeeStr = request.getParameter("Fee");
		BigDecimal Fee =new BigDecimal(FeeStr);
		
		String licensePlate = request.getParameter("licensePlate");
		
		long parkingTime = Long.valueOf(request.getParameter("parkingTime"));
		return cashService.generateOrder(Fee, parkingInfoId, parkingTime,licensePlate);
	}
	
}
