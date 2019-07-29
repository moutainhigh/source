package com.dchip.cloudparking.web.serviceImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.iRepository.ILoginLogRepository;
import com.dchip.cloudparking.web.iService.ILoginLogService;
import com.dchip.cloudparking.web.po.qpo.QAccount;
import com.dchip.cloudparking.web.po.qpo.QLoginLog;
import com.dchip.cloudparking.web.utils.Sort;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class LoginLogServiceImp extends BaseService implements ILoginLogService {
	
	@Autowired
	private ILoginLogRepository loginLogRepository;
	
	/**
	 * 获取用户信息列表
	 */
	public Object getloginLogList(Integer pageSize, Integer pageNum, String sortName, String direction) {
		List<Map<String, Object>> data = new ArrayList<>();
		QLoginLog qLoginLog = QLoginLog.loginLog;
		QAccount qAccount = QAccount.account;
		
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
			sort = new Sort(sortName, direction, qLoginLog);
			break;
		}
		
		//查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qLoginLog.id,qLoginLog.userId,qLoginLog.ip,qLoginLog.loginAt,
						qLoginLog.sourceFlag,qAccount.id,qAccount.userName,qAccount.type)
				.from(qLoginLog)
				.leftJoin(qAccount).on(qAccount.id.eq(qLoginLog.userId))
				.where(qAccount.id.isNotNull())
				.orderBy(sort.getOrderSpecifier())
				.offset(pageNum*pageSize).limit(pageSize);
		
		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		//将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", tuple.get(qLoginLog.id));
			map.put("userId", tuple.get(qLoginLog.userId));
			map.put("ip", tuple.get(qLoginLog.ip));
			map.put("loginAt", tuple.get(qLoginLog.loginAt));
			map.put("sourceFlag", tuple.get(qLoginLog.sourceFlag));
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
