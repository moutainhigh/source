package com.dchip.cloudparking.web.iService;

import com.dchip.cloudparking.web.model.po.ParkingUser;
import com.dchip.cloudparking.web.model.vo.DeductionModelVo;
import com.dchip.cloudparking.web.utils.RetKit;

public interface IDeductionModelService {
	public Object getDeductionModelList(Integer pageSize, Integer pageNum);

	public RetKit save(DeductionModelVo vo);

	public RetKit del(Integer deductionModelId);

	public RetKit softlyDel(Integer deductionModelId);

	public RetKit changeStatus(Integer deductionModelId, Integer status);
	
	/**
	 * 判断该停车场只能有一个抵扣券模板可用
	 */
	public RetKit isOnlyOne(Integer parkingId);
	
	
	/**
	 * 根据登录名查找对应停车场id
	 * @param userName
	 * @return
	 */
	public ParkingUser findParkingUserByUserName(String userName);
}
