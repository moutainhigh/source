package com.dchip.cloudparking.api.iService;

import java.util.Map;

import com.dchip.cloudparking.api.utils.RetKit;

public interface IGroundLockService {

	RetKit controlGroundLock(String groundUid, String command);

	RetKit checkStatus(String groundUid);

	RetKit reportStatus(String controlMac, String groundUid);

//	RetKit save(String groundUid, JSONArray jsonArray);

	RetKit findGroundLockInfo(String licensePlate);

	RetKit save(Map<String, Object> datas);

}
