package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dchip.cloudparking.web.model.po.ParkingUser;
import com.dchip.cloudparking.web.model.vo.DeductionVo;
import com.dchip.cloudparking.web.utils.RetKit;

public interface IDeductionManageService {

	public Object getDeductionList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para);

	public RetKit save(DeductionVo vo);

	public RetKit del(Integer deductionId);

	public RetKit softlyDel(Integer deductionId);

	public RetKit changeStatus(Integer deductionId, Integer status);

	/**
	 * 根据登录名查找对应停车场id
	 * 
	 * @param userName
	 * @return
	 */
	public ParkingUser findParkingUserByUserName(String userName);
	
	
	/**
	 * 导出抵扣券
	 * @param para
	 * @param request
	 * @param response
	 */
	public void deductionExport(List<Map<String, Object>> para, HttpServletRequest request,
			HttpServletResponse response);
}
