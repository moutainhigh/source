package com.dchip.cloudparking.web.serviceImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.IAccountRepository;
import com.dchip.cloudparking.web.iRepository.IParkingRepository;
import com.dchip.cloudparking.web.iService.IParkingService;
import com.dchip.cloudparking.web.model.po.Account;
import com.dchip.cloudparking.web.model.po.Parking;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.po.qpo.QParking;
import com.dchip.cloudparking.web.utils.RetKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class ParkingServiceImp extends BaseService implements IParkingService {

	@Autowired
	private IParkingRepository parkingRepository;
	@Autowired
	private IAccountRepository accountRepository;

	/**
	 * 系统管理员，获取所有停车场的坐标
	 * 若为停车场管理员，获取该所属公司的所有停车场的坐标
	 */
	@Override
	public List<Map<String, Object>> getAllParkingCoordinate() {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		QParking qParking = QParking.parking;
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qParking.longitude, qParking.latitude, qParking.status, qParking.parkName)
				.from(qParking);
		List<Map<String, Object>> data = new ArrayList<>();
		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		if (user.getRoleType() != BaseConstant.SysRoleType.adminType.getTypeValue()) {
			if (user.getAccountId() != null) {
				Optional<Account> accountOptional = accountRepository.findById(user.getAccountId());
				if (accountOptional.isPresent()) {
					Account account = accountOptional.get();
					Integer companyId = account.getCompanyId();
					if (companyId != null) {
						queryResults = jPAQuery.where(qParking.companyId.eq(companyId)).fetchResults();
					}
				}
			}
		}
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("longitude", tuple.get(qParking.longitude));
			map.put("latitude", tuple.get(qParking.latitude));
			map.put("status", tuple.get(qParking.status));//0:正常, -1：停用
			map.put("parkName", tuple.get(qParking.parkName));
			data.add(map);
		}
		return data;
	}

	@Override
	public RetKit getChartData() {
		return RetKit.okData(parkingRepository.getChartData());
	}

	@Override
	public List<Parking> getAllParking() {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer companyId = user.getCompanyId();
		if (companyId != null) {
			return parkingRepository.findAllByCompanyId(companyId);
		}else {
			return parkingRepository.findAllParking();
		}
	}

	@Override
	public List<Parking> getAllParkName() {
		return parkingRepository.findAll();
	}

}
