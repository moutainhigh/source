package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.model.vo.AccountVo;
import com.dchip.cloudparking.web.model.vo.ParkingUserVo;
import com.dchip.cloudparking.web.model.vo.ParkingVo;
import com.dchip.cloudparking.web.utils.RetKit;

public interface IParkingUserService {
	public Object getParkingUserList(Integer pageSize, Integer pageNum, String sortName, String direction,List<Map<String, Object>> para);
	
	public RetKit save(ParkingUserVo parkingUserVo);
	
	public RetKit del(String userId);

	public RetKit checkUserName(String userName);

	public RetKit reset(String userId);

	public Integer findParkingId(String userName);

}
