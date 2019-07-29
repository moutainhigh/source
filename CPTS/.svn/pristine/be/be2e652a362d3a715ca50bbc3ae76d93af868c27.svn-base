package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.utils.RetKit;

public interface IMemberRuleService {
	
	/**
	 * 获取会员规则列表
	 * @param pageSize
	 * @param pageNum
	 * @param sortName
	 * @param direction
	 * @param para
	 * @return
	 */
	Object getMemberRuleList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para);
	
	/**
	 * 保存会员规则表
	 * @param mrId
	 * @param moneyInp
	 * @param gradeDescriptionInp
	 * @param maximumTimesInp
	 * @param maximumArrearsInp
	 * @return
	 */
	RetKit save(Integer mrId, String moneyInp, String gradeDescriptionInp, String maximumTimesInp, String maximumArrearsInp);
}
