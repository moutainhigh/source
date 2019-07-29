package com.dchip.cloudparking.web.iService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.utils.RetKit;

public interface ICashService {

	/**
	 * 根据登录的用户查找出场车道信息
	 * @return
	 */
	List<Map<String, Object>> getOutRoadWayListByLoginUser();
	
	Object getCashList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para);
	
	/**
	 * 获取收费流水列表
	 * @param pageSize
	 * @param pageNum
	 * @param sortName
	 * @param direction
	 * @param para
	 * @return
	 */
	Object getOrder(Integer pageSize, Integer pageNum, String sortName, String direction,List<Map<String, Object>> para);
	
    /**
     * 计算停车时长、费用
     * @param userId
     * @param parkingId
     * @param startDate
     * @param endDate
     * @return
     */
	RetKit settlement(String licensePlat, Integer parkingId, Date startDate, Date endDate);
	
	/**
	 * 结算
	 * @param userId
	 * @param parkingInfoId
	 * @return
	 */
	

	RetKit generateOrder(BigDecimal Fee, Integer parkingInfoId, Long parkingTime,String licensePlate);
	
}
