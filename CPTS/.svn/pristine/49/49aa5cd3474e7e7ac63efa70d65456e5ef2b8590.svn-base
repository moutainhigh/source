package com.dchip.cloudparking.web.serviceImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.iRepository.IMemberRuleRepository;
import com.dchip.cloudparking.web.iService.IMemberRuleService;
import com.dchip.cloudparking.web.model.po.MemberRule;
import com.dchip.cloudparking.web.po.qpo.QMemberRule;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;


@Service
public class MemberRuleServiceImp extends BaseService implements IMemberRuleService {

	@Autowired
	private IMemberRuleRepository memberRuleRepository;
	
	/**
	 * 获取会员规则列表
	 */
	@Override
	public Object getMemberRuleList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para) {
		
		List<Map<String, Object>> data = new ArrayList<>();	
		QMemberRule qMemberRule = QMemberRule.memberRule;
		
		Sort sort = new Sort(sortName,direction,qMemberRule);
		
		//查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qMemberRule.id,qMemberRule.grade,qMemberRule.addWay,qMemberRule.gradeDescription,
						qMemberRule.maximumArrears,qMemberRule.maximumTimes,qMemberRule.money)
				.from(qMemberRule)
				.orderBy(sort.getOrderSpecifier())
				.offset(pageNum*pageSize).limit(pageSize);
		
		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		//将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", tuple.get(qMemberRule.id));
			map.put("grade", tuple.get(qMemberRule.grade));
			map.put("gradeDescription", tuple.get(qMemberRule.gradeDescription));
			map.put("addWay", tuple.get(qMemberRule.addWay));
			map.put("maximumArrears", tuple.get(qMemberRule.maximumArrears));
			map.put("maximumTimes", tuple.get(qMemberRule.maximumTimes));
			map.put("money", tuple.get(qMemberRule.money));
			data.add(map);
		}
		//jqgrid数据拼装
		Map<String, Object> result = new HashMap<>();
		result.put("content", data);//添加主体数据
		result.put("totalElements", queryResults.getTotal());//添加总条数
		result.put("code", 0);
        return result;
	}
	
	/**
	 * 保存会员规则表
	 */
	public RetKit save(Integer mrId, String moneyInp, String gradeDescriptionInp, String maximumTimesInp, String maximumArrearsInp) {
		
		try {
			MemberRule memberRule = memberRuleRepository.findById(mrId).get();
			memberRule.setMoney(Integer.parseInt(moneyInp));
			memberRule.setGradeDescription(gradeDescriptionInp);
			memberRule.setMaximumTimes(Integer.parseInt(maximumTimesInp));
			memberRule.setMaximumArrears(Integer.parseInt(maximumArrearsInp));
			memberRule = memberRuleRepository.save(memberRule);
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.ok("保存失败");
		}
	}
	
}
