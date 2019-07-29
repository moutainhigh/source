package com.dchip.cloudparking.web.iService;

import com.dchip.cloudparking.web.model.po.GroundLock;
import com.dchip.cloudparking.web.utils.RetKit;

import java.util.List;
import java.util.Map;

public interface IGroundLockService {

    Object getList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para);

    RetKit delete(Integer groundId);

    RetKit save(Integer mainControlId, String licensePlate, String groundUid);

	List<GroundLock> findAllGroundLockUId();

	RetKit checkgroundLockName(String licensePlate);

//	RetKit getGroundLockInfo(Integer controlId);

}
