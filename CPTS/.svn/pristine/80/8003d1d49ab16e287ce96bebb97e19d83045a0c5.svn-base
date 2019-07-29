package com.dchip.cloudparking.web.serviceImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iService.IDailyRecordService;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.po.qpo.QAccount;
import com.dchip.cloudparking.web.po.qpo.QDailyRecord;
import com.dchip.cloudparking.web.utils.Sort;
import com.dchip.cloudparking.web.utils.StrKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class DailyRecordServiceImp extends BaseService implements IDailyRecordService {
	
	/**
	 * 获取信息列表
	 */
	public Object getdailyRecordList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para) {
		List<Map<String, Object>> data = new ArrayList<>();
		QDailyRecord qDaliRecord = QDailyRecord.dailyRecord;
		QAccount qAccount = QAccount.account;
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		/**
		 * 查询条件的谓语集合(where条件)
		 */
		List<Predicate> predicates = new ArrayList<>();	
		if(user.getRoleType() != BaseConstant.SysRoleType.adminType.getTypeValue()) {
        	//停车场管理员
        	predicates.add(qAccount.companyId.eq(user.getCompanyId()));
        }
		if(!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				//搜索参数
				if(map.get("searchActType") != null && StrKit.notBlank(map.get("searchActType").toString())) {
					//操作类型
					predicates.add(qDaliRecord.actType.eq(Integer.parseInt(map.get("searchActType").toString())));
					
				}else if(map.get("searchActUserName") != null && StrKit.notBlank(map.get("searchActUserName").toString())) {
					//用户账号
					predicates.add(qAccount.userName.like("%" + map.get("searchActUserName").toString() + "%"));
				}
			}
		}
		
		// 动态排序,需要使用自定义的Sort类
		Sort sort = null;
		switch (sortName) {
		case "userName":
			sort = new Sort(sortName, direction.toString(), qAccount);
			break;
		case "type":
			sort = new Sort(sortName, direction.toString(), qAccount);
			break;
		default:
			sort = new Sort(sortName, direction, qDaliRecord);
			break;
		}
		//查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qDaliRecord.id,qDaliRecord.actName,qDaliRecord.actType,qDaliRecord.actTime,
						qAccount.userName,qAccount.type)
				.from(qDaliRecord)
				.leftJoin(qAccount).on(qAccount.id.eq(qDaliRecord.accountId))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.orderBy(sort.getOrderSpecifier())
				.offset(pageNum*pageSize).limit(pageSize);
		
		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		//将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", tuple.get(qDaliRecord.id));
			map.put("actName", tuple.get(qDaliRecord.actName));
			map.put("actType", tuple.get(qDaliRecord.actType));
			map.put("actTime", tuple.get(qDaliRecord.actTime));
			map.put("userName", tuple.get(qAccount.userName));
			map.put("type", tuple.get(qAccount.type));
			data.add(map);
		}
		//jqgrid数据拼装
		Map<String, Object> result = new HashMap<>();
		result.put("content", data);//添加主体数据
		result.put("totalElements", queryResults.getTotal());//添加总条数
		result.put("code", 0);
        return result;
	}
}
