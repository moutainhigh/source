package com.dchip.cloudparking.web.iService;

import com.dchip.cloudparking.web.model.vo.ParkingVo;
import com.dchip.cloudparking.web.utils.RetKit;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.model.vo.SettingRuleVo;

public interface IParkingSpaceService {

	Object getParkingSpaceList(Integer pageSize,Integer pageNum,String sortName,String direction,List<Map<String, Object>> para);

	RetKit delete(Integer parkingId);

	RetKit save(ParkingVo parkingVo);

	RetKit changeStatus(ParkingVo parkingVo);

    RetKit softlyDelete(Integer parkingId);
    
//	RetKit settingParkingRule(String parkingId, SettingRuleVo settingRuleVo);

	RetKit getSettingRuleData(String parkingId);

	RetKit saveSettingRuleData(SettingRuleVo settingRuleVo);

	RetKit checkRepeat(String companyId, String parkSpaceName);

    RetKit getManagerByCompanyId(String companyId);

	RetKit openGate(String controlMac, String cameraMac);
}
