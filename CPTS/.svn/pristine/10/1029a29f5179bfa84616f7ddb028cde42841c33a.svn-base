package com.dchip.cloudparking.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dchip.cloudparking.web.model.vo.CardVo;
import com.dchip.cloudparking.web.utils.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iService.IMonthlyCardService;
import com.dchip.cloudparking.web.utils.ExcelImportUtils;
import com.dchip.cloudparking.web.utils.QiniuUtil;
import com.dchip.cloudparking.web.utils.RetKit;

@Controller
@RequestMapping("/monthlyCard")
public class MonthlyCardController {
	
	@Autowired
	private IMonthlyCardService monthlyCardService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		request.setAttribute("domain", QiniuUtil.getDomain());
		request.setAttribute("domainUrl", QiniuUtil.getUrl());
		request.setAttribute("token", QiniuUtil.getUpToken());
		request.setAttribute("bucketZone", QiniuUtil.getBucketZone());
		return "monthlyCard/index";
	}

	@RequestMapping("/rendTable")
	@ResponseBody
	public Object rendTable(HttpServletRequest request) {
		//分页条件
		Integer pageSize = Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = Integer.parseInt(request.getParameter("page"))-1;
		//排序条件
		String sortName = request.getParameter("sortName");
		String direction = request.getParameter("direction");

		//动态搜索参数
		String searchParkingName = request.getParameter("searchParkingName");
		String searchUserName = request.getParameter("searchUserName");
		String searchCardCode = request.getParameter("searchCardCode");
		String searchLicensePlate = request.getParameter("searchLicensePlate");
		String searchExpiryTimeString = request.getParameter("searchExpiryTimeString");

		List<Map<String, Object>> para = new ArrayList<>();
		if (StrKit.notBlank(searchParkingName)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchParkingName", searchParkingName);
			para.add(map);
		}
		if (StrKit.notBlank(searchUserName)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchUserName", searchUserName);
			para.add(map);
		}
		if (StrKit.notBlank(searchCardCode)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchCardCode", searchCardCode);
			para.add(map);
		}
		if (StrKit.notBlank(searchLicensePlate)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchLicensePlate", searchLicensePlate);
			para.add(map);
		}
		if (StrKit.notBlank(searchExpiryTimeString)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchExpiryTimeString", searchExpiryTimeString);
			para.add(map);
		}
		return JSON.toJSON(monthlyCardService.getCardList(pageSize, pageNum, sortName, direction, para));
	}

	//导入月卡信息
	@RequestMapping("/cardImport")
	@ResponseBody
	public RetKit cardImport(
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "parkName", required = false) Integer parkingId,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 判断文件是否为空
		if (file == null) {
			return RetKit.fail("文件不能为空！");
		}
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 验证文件名是否合格
		if (!ExcelImportUtils.validateExcel(fileName)) {
			return RetKit.fail("文件名不合格！");
		}
		// 进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
		long size = file.getSize();
		if (size == 0) {
			return RetKit.fail("文件内容不能为空！");
		}
		return monthlyCardService.cardImport(fileName, file, parkingId,response);
	}

	@RequestMapping("/addCardInfo")
	@ResponseBody
	public RetKit addCardInfo(HttpServletRequest request) {
		CardVo vo = new CardVo();
		String isMain = request.getParameter("isMain");
		String isFixedSpace = request.getParameter("isFixedSpace");
		String secondLicensePlate = request.getParameter("secondLicensePlate");
		String thirdLicensePlate = request.getParameter("thirdLicensePlate");
		String moreCarType = request.getParameter("moreCarType");
		try{
			vo.setParkingId(Integer.parseInt(request.getParameter("parkingId")));
			vo.setCardCode(request.getParameter("licensePlate").toUpperCase());
			vo.setLicensePlate(request.getParameter("licensePlate").toUpperCase());
			vo.setCarOwnerName(request.getParameter("carOwnerName"));
			vo.setExpiryTimeString(request.getParameter("expiryTimeString"));
			if (StrKit.isBlank(isMain)) {
				vo.setIsMain(0);
			}else {
				vo.setIsMain(Integer.parseInt(isMain));
			}
			vo.setMax(Integer.parseInt(request.getParameter("max")));
			vo.setType(Integer.parseInt(request.getParameter("type")));
			vo.setRemark(request.getParameter("remark"));
			vo.setPhone(request.getParameter("phone"));
			vo.setAddress(request.getParameter("address"));
			if (StrKit.isBlank(isFixedSpace)) {
				vo.setIsFixedSpace(0);
			}else {
				vo.setIsFixedSpace(Integer.parseInt(isFixedSpace));
			}
			if(StrKit.isBlank(moreCarType)) {
				vo.setMoreCarType(0);
			}else {
				vo.setMoreCarType(Integer.parseInt(moreCarType));
			}
		}catch (Exception e){
			return RetKit.fail("数据有误，添加失败！");
		}
		return monthlyCardService.addCardInfo(vo,secondLicensePlate,thirdLicensePlate);
	}

	@RequestMapping("/editCardInfo")
	@ResponseBody
	public RetKit editCardInfo(CardVo cardVo,String secondLicensePlate,String thirdLicensePlate) {
		return monthlyCardService.editCardInfo(cardVo, secondLicensePlate, thirdLicensePlate);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public RetKit delete(HttpServletRequest request) {
		Integer cardId = Integer.parseInt(request.getParameter("cardId"));
		String licensePlate = request.getParameter("licensePlate");
		return monthlyCardService.delCardLicensePlate(cardId,licensePlate);
	}
	
	@RequestMapping("/record")
	public String record(HttpServletRequest request) {
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		return "monthlyCard/record";
	}

	@RequestMapping("/recordTable")
	@ResponseBody
	public Object recordTable(HttpServletRequest request) {
		//分页条件
		Integer pageSize = Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = Integer.parseInt(request.getParameter("page"))-1;
		//排序条件
		String sortName = request.getParameter("sortName");
		String direction = request.getParameter("direction");
		List<Map<String, Object>> para = new ArrayList<>();
		
		return JSON.toJSON(monthlyCardService.getMonthlyCardPayList(pageSize, pageNum, sortName, direction, para));
	}
	
	/**
	 * 添加月卡信息时进行月卡车牌查重
	 * @author ZYY
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkRepeat")
	@ResponseBody
	public RetKit checkCardLicensePlate(HttpServletRequest request) {
		String parkingId = request.getParameter("parkingId");
		String licensePlate = request.getParameter("licensePlate");
		if (StrKit.isBlank(parkingId)) {
			return RetKit.fail("停车场ID为空,请先选择停车场");
		}
		if (StrKit.isBlank(licensePlate)) {
			return RetKit.fail("车牌号码为空");
		}
		return monthlyCardService.checkCardLicensePlate(parkingId, licensePlate);
	}

}
