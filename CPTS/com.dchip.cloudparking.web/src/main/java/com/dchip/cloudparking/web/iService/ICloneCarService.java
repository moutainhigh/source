package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.utils.RetKit;

public interface ICloneCarService {

	Object getCloneCarList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para);

    RetKit delete(Integer cloneCarId);

    RetKit findCloneCarDetailInfo(String licensePlate);

}
