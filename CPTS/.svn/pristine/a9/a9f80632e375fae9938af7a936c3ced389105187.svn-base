package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.model.po.Version;
import com.dchip.cloudparking.web.utils.RetKit;

public interface IVersionService {
	/**
	 * 获取版本列表
	 * @param pageSize
	 * @param pageNum
	 * @param sortName
	 * @param direction
	 * @param para
	 * @return
	 */
	Object getVersionList(Integer pageSize,Integer pageNum,String sortName,String direction,List<Map<String, Object>> para);
	
	RetKit save(Version  vo);
	
	RetKit delete(Integer versionId);
	
	/**
	 * 获取某种类型最新的版本
	 */
	Map<String,Object> findLatestVersionMap(Integer type);
}
