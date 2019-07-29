package com.dchip.cloudparking.web.serviceImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.po.qpo.QCompany;
import com.dchip.cloudparking.web.po.qpo.QParking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.IDeductionModelRepository;
import com.dchip.cloudparking.web.iRepository.IParkingUserRepository;
import com.dchip.cloudparking.web.iService.IDeductionModelService;
import com.dchip.cloudparking.web.model.po.DeductionModel;
import com.dchip.cloudparking.web.model.po.ParkingUser;

import com.dchip.cloudparking.web.model.vo.DeductionModelVo;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.po.qpo.QDeductionModel;
import com.dchip.cloudparking.web.utils.RetKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class DeductionModelServiceImpl extends BaseService implements IDeductionModelService {

	@Autowired
	private IDeductionModelRepository deductionModelRepository;
	@Autowired
	private IParkingUserRepository parkingUserRepository;

	@Override
	public Object getDeductionModelList(Integer pageSize, Integer pageNum) {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer roleType = user.getRoleType();
		List<Map<String, Object>> data = new ArrayList<>();
		QDeductionModel qDeductionModel = QDeductionModel.deductionModel;
		QParking qParking = QParking.parking;
		QCompany qCompany = QCompany.company;

		/**
		 * 查询条件的谓语集合(where条件)
		 */
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(qDeductionModel.status.notIn(BaseConstant.SoftDelete.delete.getTypeValue()));
		if (roleType.equals(BaseConstant.RoleType.parkingRole.getTypeValue())) {
			ParkingUser parkingUser = parkingUserRepository.findParkingUserByUserName(user.getUserName());
			predicates.add(qDeductionModel.parkingId.eq(parkingUser.getParkingId()));
		}else if (roleType.equals(BaseConstant.RoleType.companyRole.getTypeValue())) {
			Integer companyId = user.getCompanyId();
			predicates.add(qCompany.id.eq(companyId));
		}
		
		// 查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qDeductionModel.id, qDeductionModel.name, qDeductionModel.parkingId, qDeductionModel.hourNum,
						qDeductionModel.status, qDeductionModel.dueTime, qParking.parkName)
				.from(qDeductionModel)
				.leftJoin(qParking).on(qParking.id.eq(qDeductionModel.parkingId))
				.leftJoin(qCompany).on(qCompany.id.eq(qParking.companyId))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.offset(pageNum * pageSize).limit(pageSize);
		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		// 将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", tuple.get(qDeductionModel.id));
			map.put("name", tuple.get(qDeductionModel.name));
			map.put("parkingId", tuple.get(qDeductionModel.parkingId));
			map.put("hourNum", tuple.get(qDeductionModel.hourNum));
			map.put("dueTime", tuple.get(qDeductionModel.dueTime));
			map.put("status", tuple.get(qDeductionModel.status));
			map.put("parkName", tuple.get(qParking.parkName));
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
	public RetKit save(DeductionModelVo vo) {
		try {
			DeductionModel deductionModel = new DeductionModel();
			if (vo.getId() != null) {
				deductionModel = deductionModelRepository.findById(vo.getId()).get();
			}
			deductionModel.setDueTime(vo.getDueTime());
			deductionModel.setHourNum(vo.getHourNum());
			deductionModel.setName(vo.getName());
			deductionModel.setParkingId(vo.getParkingId());
			deductionModel.setStatus(vo.getStatus());

			deductionModelRepository.save(deductionModel);
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail("操作失败");
		}
	}

	@Override
	public RetKit del(Integer deductionModelId) {
		deductionModelRepository.deleteById(deductionModelId);
		return RetKit.ok();
	}

	@Override
	public RetKit softlyDel(Integer deductionModelId) {
		Integer integer = deductionModelRepository.changeStatus(BaseConstant.SoftDelete.delete.getTypeValue(),
				deductionModelId);
		if (integer == null || integer == 0) {
			return RetKit.fail();
		}
		return RetKit.ok();
	}

	@Override
	public RetKit changeStatus(Integer deductionModelId, Integer status) {
		deductionModelRepository.changeStatus(status, deductionModelId);
		return RetKit.ok();
	}

	@Override
	public RetKit isOnlyOne(Integer parkingId) {
		DeductionModel deductionModel = deductionModelRepository.isOnlyOne(parkingId);
		if (deductionModel == null) {
			return RetKit.ok();
		} else {
			return RetKit.fail("同一个停车场只能有一个可用的抵扣券模板");
		}
	}
	
	
	/**
	 * 根据当前登录的后台账号account表查询parking_user表对应停车场
	 * @param userName
	 * @return
	 */
	public ParkingUser findParkingUserByUserName(String userName) {
		return parkingUserRepository.findParkingUserByUserName(userName);
	}
}
