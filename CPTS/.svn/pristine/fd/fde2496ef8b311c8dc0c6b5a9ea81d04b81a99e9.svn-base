package com.dchip.cloudparking.web.serviceImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iRepository.IAccountRepository;
import com.dchip.cloudparking.web.iRepository.ISecondPlateNameRepository;
import com.dchip.cloudparking.web.iService.IParkingWhiteListService;
import com.dchip.cloudparking.web.model.po.Account;
import com.dchip.cloudparking.web.model.po.SecondPlateName;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.po.qpo.QParking;
import com.dchip.cloudparking.web.po.qpo.QSecondPlateName;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.dchip.cloudparking.web.utils.StrKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class ParkingWhiteListServiceImp extends BaseService implements IParkingWhiteListService{
	@Autowired
	private ISecondPlateNameRepository secondPlateNameRepository;
	@Autowired
	private IAccountRepository accountRepository;

	@Override
	public Object getFreeParkingLicensePlateList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para) {
		
		List<Map<String, Object>> listData = new ArrayList<>();
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Account> account = accountRepository.findById(user.getAccountId());
		QSecondPlateName qSecondPlateName = QSecondPlateName.secondPlateName;//停车场白名单
		QParking qParking = QParking.parking;//停车场

		List<Predicate> predicates = new ArrayList<>();
		if (account.isPresent()) {
			String userName = account.get().getUserName();
			if (!userName.equals("admin")) {
				Integer companyId = account.get().getCompanyId();
				predicates.add(qParking.companyId.eq(companyId));
			}
		}
		if (!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				if (map.get("searchUserName") != null) {
					predicates.add(qSecondPlateName.licensePlate.like("%" + map.get("searchUserName").toString() + "%"));
				} 
			}
		}
		//动态排序,需要使用自定义的Sort类
		Sort sort = null;
		switch (sortName) {
		case "secondWhiteListId":		
			sort = new Sort("id",direction.toString(),qSecondPlateName);
				break;
			default:
				sort = new Sort(sortName,direction,qSecondPlateName);
				break;
		}
		
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.selectDistinct(qSecondPlateName.id,qSecondPlateName.parkingId,qSecondPlateName.licensePlate,
				 qSecondPlateName.createTime,qSecondPlateName.status,qParking.parkName)
				.from(qSecondPlateName)
				.leftJoin(qParking).on(qParking.id.eq(qSecondPlateName.parkingId))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.orderBy(sort.getOrderSpecifier())
				.groupBy(qSecondPlateName.id)
				.offset(pageNum * pageSize).limit(pageSize);
		
		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("secondWhiteListId", tuple.get(qSecondPlateName.id));
			map.put("parkingId", tuple.get(qSecondPlateName.parkingId));
			map.put("parkName", tuple.get(qParking.parkName));
			map.put("licensePlate", tuple.get(qSecondPlateName.licensePlate));
			map.put("createTime", tuple.get(qSecondPlateName.createTime));
			map.put("status", tuple.get(qSecondPlateName.status));
			listData.add(map);
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("content", listData);
		result.put("totalElements", queryResults.getTotal());
		result.put("code", 0);
		
		return result;
	}
	

	@Override
	public RetKit add(String parkingId,String licensePlate) {
		if (StrKit.isBlank(parkingId)||StrKit.isBlank(licensePlate)) {
			return RetKit.fail();
		}
		Integer pId = Integer.parseInt(parkingId);
		SecondPlateName secondPlateName = new SecondPlateName();
		secondPlateName.setParkingId(pId);
		secondPlateName.setCreateTime(new Date());
		secondPlateName.setLicensePlate(licensePlate);
		secondPlateName.setStatus(1);
		secondPlateNameRepository.save(secondPlateName);
		return RetKit.ok("添加成功");
	}

	@Override
	public Object getParkName() {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Account> account = accountRepository.findById(user.getAccountId());
		if (account.isPresent()) {
			Integer companyId = account.get().getCompanyId();
			List<Map<String, Object>> listData = secondPlateNameRepository.getCompanyParkName(companyId);
			return JSON.toJSON(listData);
		}
		return RetKit.fail();
	}
	

	@Override
	public RetKit delete(Integer secondWhiteListId) {
		if (secondWhiteListId == null) {
			return RetKit.fail();
		}
		secondPlateNameRepository.deleteById(secondWhiteListId);
		return RetKit.ok();
	}


	@Override
	public RetKit edit(Integer parkingId, String licensePlate,Integer secondWhiteListId) {
		if (parkingId == null || licensePlate == null) {
			return RetKit.fail();
		}
		Optional<SecondPlateName> secondPlateName = secondPlateNameRepository.findById(secondWhiteListId);
		if (secondPlateName.isPresent()) {
			SecondPlateName secondPlateName2 = secondPlateName.get();
			secondPlateName2.setLicensePlate(licensePlate);
			secondPlateName2.setParkingId(parkingId);
			secondPlateNameRepository.save(secondPlateName2);
			return RetKit.ok();
		}else {
			return RetKit.fail();
		}
	}


	@Override
	public RetKit changestatus(Integer secondWhiteListId, Integer status) {
		if (secondWhiteListId == null || status == null) {
			return RetKit.fail();
		}
		Optional<SecondPlateName> secondPlateName = secondPlateNameRepository.findById(secondWhiteListId);
		if (secondPlateName.isPresent()) {
			SecondPlateName secondPlateName2 = secondPlateName.get();
			if (status.equals(0)) {
				secondPlateName2.setStatus(0);
			}else {
				secondPlateName2.setStatus(1);
			}
			secondPlateNameRepository.save(secondPlateName2);
			return RetKit.ok();
		}else {
			return RetKit.fail();
		}
	}


	@Override
	public RetKit checkLicensePlate(String licensePlate, String parkingId) {
		Integer count = secondPlateNameRepository.checkRepeat(licensePlate,parkingId);
		if (count > 0) {
			return RetKit.fail("该车牌已存在");
		}
		return RetKit.ok();
	}

}
