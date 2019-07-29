package com.dchip.cloudparking.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.IWhiteListService;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;

@RestController
@RequestMapping("/whiteList")
public class WhiteListController {
	@Autowired
	private IWhiteListService whiteListService;
	
	/**
	 * 添加白名单
	 * @param request
	 * @return
	 */
	@RequestMapping("/addWhiteList")
	public RetKit addWhiteList(HttpServletRequest request) {
		String tmpFlag = request.getParameter("tmpFlag");
		String parkingId = request.getParameter("parkingId");
		String licencePlate = request.getParameter("licencePlate");
		return whiteListService.addWhiteList(tmpFlag,parkingId,licencePlate);
	}
	
	/**
	 * 获取白名单列表
	 * @author ZYY
	 * @param request
	 * @return
	 */
	@RequestMapping("/getWhiteList")
	public RetKit getWhiteList(HttpServletRequest request) {
		String parkingId = request.getParameter("parkingId");
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		Integer pageNum = Integer.parseInt(request.getParameter("pageNum")) - 1;
		return whiteListService.getWhiteList(parkingId,pageSize,pageNum);
	}
	
	/**
	 * 删除白名单
	 * @param request
	 * @return
	 */
	@RequestMapping("/delWhiteList")
	public RetKit delWhiteList(HttpServletRequest request) {
		String whiteListId = request.getParameter("whiteListId");
		if (StrKit.isBlank(whiteListId)) {
			return RetKit.fail("whiteListId不能为空！");
		}else {
			return whiteListService.delWhiteList(whiteListId);
		}
	}
}
