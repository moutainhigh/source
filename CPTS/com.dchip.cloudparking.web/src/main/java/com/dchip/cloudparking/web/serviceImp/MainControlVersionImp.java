package com.dchip.cloudparking.web.serviceImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.iRepository.IMainControlVersionRepository;
import com.dchip.cloudparking.web.iService.IMainControlVersionService;
import com.dchip.cloudparking.web.po.qpo.QMainControlVersion;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class MainControlVersionImp extends BaseService implements IMainControlVersionService {

	@Autowired
	private IMainControlVersionRepository mainControlVersionRepository;

	@Override
	public Object getMainControlVersionList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para) {
		List<Map<String, Object>> data = new ArrayList<>();
		QMainControlVersion qMainControlVersion = QMainControlVersion.mainControlVersion;

		/**
		 * 查询条件的谓语集合(where条件)
		 */
		List<Predicate> predicates = new ArrayList<>();
		if (!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				// 搜索参数
				if (map.get("searchMAC") != null) {
					// 停车场名称
					predicates.add(qMainControlVersion.mac.like("%" + map.get("searchMAC").toString() + "%"));
				}
			}
		}
		Sort sort = new Sort(sortName, direction, qMainControlVersion);
		// 查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qMainControlVersion.id, qMainControlVersion.mac, qMainControlVersion.currentVersion,
						qMainControlVersion.createTime, qMainControlVersion.installFailReasion,
						qMainControlVersion.type)
				.where(predicates.toArray(new Predicate[predicates.size()])).from(qMainControlVersion)
				.orderBy(sort.getOrderSpecifier()).offset(pageNum * pageSize).limit(pageSize);

		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		// 将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", tuple.get(qMainControlVersion.id));
			map.put("mac", tuple.get(qMainControlVersion.mac));
			map.put("currentVersion", tuple.get(qMainControlVersion.currentVersion));
			map.put("createTime", tuple.get(qMainControlVersion.createTime));
			map.put("installFailReasion", tuple.get(qMainControlVersion.installFailReasion));
			map.put("type", tuple.get(qMainControlVersion.type));
			data.add(map);
		}
		// jqgrid数据拼装
		Map<String, Object> result = new HashMap<>();
		result.put("content", data);// 添加主体数据
		result.put("totalElements", queryResults.getTotal());// 添加总条数
		result.put("code", 0);
		return result;
	}

	@Override
	public RetKit delete(Integer id) {
		mainControlVersionRepository.deleteById(id);
		return RetKit.ok();
	}

}
