package com.dchip.cloudparking.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iService.IDeductionModelService;
import com.dchip.cloudparking.web.model.po.ParkingUser;
import com.dchip.cloudparking.web.model.vo.DeductionModelVo;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;

@Controller
@RequestMapping("/deductionModel")
public class DeductionModelController {

	@Autowired
	private IDeductionModelService deductionModelService;

	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ParkingUser parkingUser = deductionModelService.findParkingUserByUserName(user.getUserName());
		Integer parkingId = -1;
		if(parkingUser != null) {
			parkingId = parkingUser.getParkingId();
		}
		request.setAttribute("roleType", user.getRoleType());
		request.setAttribute("parkingId", parkingId);
		return "deductionModel/index";
	}

	@RequestMapping("/rendering")
	@ResponseBody
	public Object rendering(HttpServletRequest request) {
		Integer pageSize = Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = Integer.parseInt(request.getParameter("page")) - 1;
		return JSON.toJSON(deductionModelService.getDeductionModelList(pageSize, pageNum));
	}

	@RequestMapping("/add")
	@ResponseBody
	public RetKit add(HttpServletRequest request) {
		if (StrKit.isBlank(request.getParameter("voData"))) {
			return RetKit.fail("参数不存在");
		}
		Object voData = JSON.parse(request.getParameter("voData"));
		DeductionModelVo vo = JSON.toJavaObject(JSON.parseObject(voData.toString()), DeductionModelVo.class);
		return deductionModelService.save(vo);
	}

	@RequestMapping("/edit")
	@ResponseBody
	public RetKit edit(HttpServletRequest request) {
		if (StrKit.isBlank(request.getParameter("voData"))) {
			return RetKit.fail("参数不存在");
		}
		Object voData = JSON.parse(request.getParameter("voData"));
		DeductionModelVo vo = JSON.toJavaObject(JSON.parseObject(voData.toString()), DeductionModelVo.class);

		System.out.println(vo.getHourNum());
		System.out.println(vo.getDueTime());
		// System.out.println(vo.getQiniuCloudRelativeUrl());
		return deductionModelService.save(vo);
	}

	/**
	 * 判断该停车场只能有一个可用
	 */
	@RequestMapping("/isOnlyOne")
	@ResponseBody
	public RetKit isOnlyOne(HttpServletRequest request) {
		Integer parkingId = Integer.parseInt(request.getParameter("parkingId"));
		return deductionModelService.isOnlyOne(parkingId);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public RetKit del(HttpServletRequest request) {
		Integer id = Integer.parseInt(request.getParameter("id"));
		// return deductionModelService.del(deductionId);
		return deductionModelService.softlyDel(id);
	}

	@RequestMapping("/changeStatus")
	@ResponseBody
	public RetKit changeStatus(HttpServletRequest request) {
		Integer deductionId = Integer.parseInt(request.getParameter("deductionId"));
		Integer deductionStatus = Integer.parseInt(request.getParameter("deductionStatus"));
		if (BaseConstant.DeductionStatus.NotUsedStatus.getTypeValue() == deductionStatus) {
			deductionModelService.changeStatus(deductionId,
					BaseConstant.DeductionStatus.OutOfDateStatus.getTypeValue());
		}
		return RetKit.ok();
	}

}
