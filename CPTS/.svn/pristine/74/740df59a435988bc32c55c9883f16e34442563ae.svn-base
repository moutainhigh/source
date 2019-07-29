package com.dchip.cloudparking.web.serviceImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.iRepository.IFirstWhiteListRepository;
import com.dchip.cloudparking.web.iService.IFirstWhiteListService;
import com.dchip.cloudparking.web.model.po.FirstWhiteList;
import com.dchip.cloudparking.web.po.qpo.QFirstWhiteList;
import com.dchip.cloudparking.web.po.qpo.QLicensePlateName;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class FirstWhiteListServiceImp extends BaseService implements IFirstWhiteListService{
	
	@Autowired
	private IFirstWhiteListRepository firstWhiteListRepository;
	
	@Override
	public Object getFirstWhiteList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para) {

		List<Map<String, Object>> listData = new ArrayList<>();
		QFirstWhiteList qFirstWhiteList = QFirstWhiteList.firstWhiteList;//一级白名单
		QLicensePlateName qLicensePlateName = QLicensePlateName.licensePlateName1;//车牌类型名称
		
		List<Predicate> predicates = new ArrayList<>();

		if (!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				if (map.get("searchLicensePlateName") != null) {
					predicates.add(qLicensePlateName.licensePlateName.like("%" + map.get("searchLicensePlateName").toString() + "%"));
				} 
			}
		}
		
		//动态排序,需要使用自定义的Sort类
		Sort sort = null;
		switch (sortName) {
		case "firstWhiteListId":		
			sort = new Sort("id",direction.toString(),qFirstWhiteList);
				break;
			default:
				sort = new Sort(sortName,direction,qFirstWhiteList);
				break;
		}
		
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.selectDistinct(qFirstWhiteList.id,qFirstWhiteList.createTime,qFirstWhiteList.licensePlateTypeId
						,qLicensePlateName.licensePlateName,qLicensePlateName.typeNumber,qLicensePlateName.type,qLicensePlateName.id)
				.from(qFirstWhiteList)
				.leftJoin(qLicensePlateName).on(qLicensePlateName.id.eq(qFirstWhiteList.licensePlateTypeId))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.orderBy(sort.getOrderSpecifier())
				.offset(pageNum * pageSize).limit(pageSize);
		
		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("firstWhiteListId", tuple.get(qFirstWhiteList.id));
			map.put("licensePlateName", tuple.get(qLicensePlateName.licensePlateName));
			map.put("createTime", tuple.get(qFirstWhiteList.createTime));
			map.put("licensePlateTypeId", tuple.get(qFirstWhiteList.licensePlateTypeId));
			map.put("typeNumber", tuple.get(qLicensePlateName.typeNumber));
			map.put("type", tuple.get(qLicensePlateName.type));
			map.put("id", tuple.get(qLicensePlateName.id));
			listData.add(map);
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("content", listData);
		result.put("totalElements", queryResults.getTotal());
		result.put("code", 0);
		
		return result;
	}
	
	@Override
	public RetKit save(Integer firstWhiteListId,Integer licensePlateTypeId) {
		FirstWhiteList firstWhiteList = new FirstWhiteList();
		if (firstWhiteListId != 0) {
			firstWhiteList = firstWhiteListRepository.findById(firstWhiteListId).get();
		}
		firstWhiteList.setCreateTime(new Date());
		firstWhiteList.setLicensePlateTypeId(licensePlateTypeId);
		firstWhiteListRepository.save(firstWhiteList);
		return RetKit.ok();
	}
	
	@Override
	public RetKit delete(Integer firstWhiteListId) {
		if (firstWhiteListId == null) {
			return RetKit.fail();
		}else {
			firstWhiteListRepository.deleteById(firstWhiteListId);
			return RetKit.ok();
		}
	}

	@Override
	public FirstWhiteList findByLicensePlateId(Integer licensePlateTypeId) {
		try{
			return firstWhiteListRepository.findByLicensePlateId(licensePlateTypeId);
		}catch (Exception e){
			return null;
		}
	}

	@Override
	public Integer findFirstWhiteList(Integer licensePlateTypeId) {
		try{
			return firstWhiteListRepository.findFirstWhiteList(licensePlateTypeId);
		}catch (Exception e){
			return null;
		}
	}

}
