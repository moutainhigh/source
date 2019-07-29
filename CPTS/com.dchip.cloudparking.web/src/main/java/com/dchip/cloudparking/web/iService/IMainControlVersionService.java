package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.utils.RetKit;

public interface IMainControlVersionService {

	/**
	 * 获取主控板当前版本列表
	 * @param pageSize
	 * @param pageNum
	 * @param sortName
	 * @param direction
	 * @param para
	 * @return
	 */
	Object getMainControlVersionList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para);
	
	RetKit delete(Integer id);
}
