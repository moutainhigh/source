package com.dchip.cloudparking.web.iService;

import com.dchip.cloudparking.web.model.po.Activity;
import com.dchip.cloudparking.web.model.vo.ActivityVo;
import com.dchip.cloudparking.web.utils.RetKit;

import java.util.List;
import java.util.Map;

public interface IActivityManageService {

	public Object getActivityList(Integer pageSize, Integer pageNum, String sortName, String direction);

	public RetKit save(ActivityVo activityVo);

	public RetKit del(Integer activityId);

	public RetKit softlyDel(Integer activityId);
	
	public RetKit checkActivityName(String activityName);

	Activity findByActivityRemark(String remark);

	public Object getRecordList(Integer pageSize, Integer pageNum, List<Map<String, Object>> para,String direction,String sortName);

}
