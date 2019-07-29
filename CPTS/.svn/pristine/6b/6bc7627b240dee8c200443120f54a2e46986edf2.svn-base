package com.dchip.cloudparking.web.serviceImp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.IUserRepository;
import com.dchip.cloudparking.web.iService.IUserService;
import com.dchip.cloudparking.web.model.po.User;
import com.dchip.cloudparking.web.po.qpo.QMemberRule;
import com.dchip.cloudparking.web.po.qpo.QOrder;
import com.dchip.cloudparking.web.po.qpo.QParking;
import com.dchip.cloudparking.web.po.qpo.QParkingInfo;
import com.dchip.cloudparking.web.po.qpo.QPointRecord;
import com.dchip.cloudparking.web.po.qpo.QRechargeLog;
import com.dchip.cloudparking.web.po.qpo.QUser;
import com.dchip.cloudparking.web.utils.HashKit;
import com.dchip.cloudparking.web.utils.RetKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class UserServiceImp extends BaseService implements IUserService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public Object getUserList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para) {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Map<String, Object>> listData = new ArrayList<>();
		
		QUser qUser = QUser.user;
		QMemberRule qMemberRule = QMemberRule.memberRule;
		QPointRecord qPointRecord = QPointRecord.pointRecord;
		QOrder qOrder = QOrder.order;
		QRechargeLog qRechargeLog = QRechargeLog.rechargeLog;
		
		/**
		 * 查询条件的谓语集合(where条件)
		 */
		List<Predicate> predicates = new ArrayList<>();

		if (!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				// 搜索参数
				if (map.get("searchPhone") != null) {
					// 搜索手机号
					predicates.add(qUser.phone.like("%" + map.get("searchPhone").toString() + "%"));
				} else if (map.get("searchLicence") != null) {
					// 搜索车牌号
					predicates.add(qUser.licensePlat.like("%" + map.get("searchLicence").toString() + "%"));
				} else if (map.get("blackSelect") != null) {
					// 是否为黑名单
					if (Integer.parseInt(map.get("blackSelect").toString()) == 1) {
						predicates.add(qUser.isBlack.eq(BaseConstant.UserIsBlack.NormalUser.getTypeValue()));
					} else if (Integer.parseInt(map.get("blackSelect").toString()) == 2) {
						predicates.add(qUser.isBlack.eq(BaseConstant.UserIsBlack.BlacklistUser.getTypeValue()));
					}
				}
			}
		}

		// 查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.selectDistinct(qUser.id, qUser.nickname, qUser.phone, qUser.state, qUser.isBlack, qUser.falseReportNumber, 
						qMemberRule.gradeDescription, qUser.licensePlat, qUser.balance)
				.from(qUser)
				.leftJoin(qMemberRule).on(qMemberRule.id.eq(qUser.memberId))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.groupBy(qUser.id)
				.offset(pageNum * pageSize).limit(pageSize);

		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		// 将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("userId", tuple.get(qUser.id));
			map.put("balance", tuple.get(qUser.balance));
			map.put("licensePlat", tuple.get(qUser.licensePlat));
			map.put("nickname", tuple.get(qUser.nickname));
			map.put("phone", tuple.get(qUser.phone));
			map.put("state", tuple.get(qUser.state));
			map.put("falseReportNumber", tuple.get(qUser.falseReportNumber));
			map.put("isBlack", tuple.get(qUser.isBlack));
			map.put("gradeDescription", tuple.get(qMemberRule.gradeDescription));
			
			// 总积分
			JPAQuery<Integer> jPAQueryPoint = jpaQueryFactory
					.selectDistinct(qPointRecord.score.sum())
					.from(qPointRecord)
					.where(qPointRecord.userId.eq(tuple.get(qUser.id)));
			if (!jPAQueryPoint.fetchResults().getResults().isEmpty()) {
				map.put("scores", jPAQueryPoint.fetchResults().getResults());
			} else {
				map.put("scores", 0);
			}
			
			// 总消费金额
			List<Predicate> predicatesFee = new ArrayList<>();
			predicatesFee.add(qOrder.userId.eq(tuple.get(qUser.id)));
			predicatesFee.add(qOrder.status.eq(BaseConstant.OrderStatus.AlreadyPay.getTypeValue()));
			JPAQuery<BigDecimal> jPAQueryFee = jpaQueryFactory
					.selectDistinct(qOrder.fee.sum())
					.from(qOrder)
					.where(predicatesFee.toArray(new Predicate[predicatesFee.size()]));
			if (!jPAQueryFee.fetchResults().getResults().isEmpty()) {
				map.put("fee", jPAQueryFee.fetchResults().getResults());
			} else {
				map.put("fee", 0.00);
			}
			
			// 总充值金额
			List<Predicate> predicatesAmount = new ArrayList<>();
			predicatesAmount.add(qRechargeLog.userId.eq(tuple.get(qUser.id)));
			predicatesAmount.add(qRechargeLog.status.eq(BaseConstant.RechargeLogStatus.trueFlag.getValue()));
			JPAQuery<BigDecimal> jPAQueryAmount = jpaQueryFactory
					.selectDistinct(qRechargeLog.inMoney.sum().add(qRechargeLog.discountMoney.sum()))
					.from(qRechargeLog)
					.where(predicatesAmount.toArray(new Predicate[predicatesAmount.size()]));
			if (!jPAQueryAmount.fetchResults().getResults().isEmpty()) {
				map.put("amount", jPAQueryAmount.fetchResults().getResults());
			} else {
				map.put("amount", 0.00);
			}
			
			listData.add(map);
		}

		// jqgrid数据拼装
		Map<String, Object> result = new HashMap<>();
		result.put("content", listData);// 添加主体数据
		result.put("totalElements", queryResults.getTotal());// 添加总条数
		result.put("code", 0);
		return result;
	}

	public RetKit getArrearagelist(Integer userId) {
		List<Map<String, Object>> listData = new ArrayList<>();
		
		QUser qUser = QUser.user;
		QOrder qOrder = QOrder.order;
		QParkingInfo qParkingInfo = QParkingInfo.parkingInfo;
		QParking qParking = QParking.parking;

		List<Predicate> predicates = new ArrayList<>();
		predicates.add(qUser.id.eq(userId));
		predicates.add(qParkingInfo.status.ne(BaseConstant.parkingInfoStatus.unfinishedStatus.getValue()));
		predicates.add(qOrder.status.ne(BaseConstant.OrderStatus.AlreadyPay.getTypeValue()));
		// 查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.selectDistinct(qUser.licensePlat, qParking.parkName, qParking.provinceName, qParking.cityName,
						qParking.areaName, qParking.location, qParkingInfo.parkDate, qParkingInfo.outDate,
						qParkingInfo.status, qOrder.status, qOrder.fee, qOrder.parkingTime)
				.from(qUser)
				.leftJoin(qParkingInfo).on(qParkingInfo.userId.eq(qUser.id))
				.leftJoin(qParking).on(qParking.parkCode.eq(qParkingInfo.parkCode))
				.leftJoin(qOrder).on(qOrder.parkingInfoId.eq(qParkingInfo.id))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.orderBy(qOrder.payTime.desc());

		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		// 将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("licensePlate", tuple.get(qUser.licensePlat));
			map.put("parkName", tuple.get(qParking.parkName));
			map.put("provinceName", tuple.get(qParking.provinceName));
			map.put("cityName", tuple.get(qParking.cityName));
			map.put("areaName", tuple.get(qParking.areaName));
			map.put("location", tuple.get(qParking.location));
			map.put("parkDate", tuple.get(qParkingInfo.parkDate));
			map.put("outDate", tuple.get(qParkingInfo.outDate));
			map.put("fee", tuple.get(qOrder.fee));
			map.put("parkingTime", tuple.get(qOrder.parkingTime));
			map.put("parkingInfoStatus", tuple.get(qParkingInfo.status));
			map.put("orderStatus", tuple.get(qOrder.status));
			listData.add(map);
		}
		return RetKit.okData(listData);
	}

	/**
	 * 判断用户名是否存在
	 */
	@Override
	public Boolean hasUserName(String userName) {
		Boolean userFlag = true;
		if (userRepository.getUserLoginNameNum(userName) == 0) {
			userFlag = false;
		}
		return userFlag;
	}

	/**
	 * 校验是否用户名密码合法
	 */
	@Override
	public Boolean pwdIsCorrect(String userName, String pwd) {
		Boolean userFlag = true;
		String pwdmd = HashKit.md5(pwd);
		if (userRepository.getUserLoginSuccessNum(userName, pwdmd) == 0) {
			userFlag = false;
		}
		return userFlag;
	}

	@Override
	public RetKit getChartData() {
		return RetKit.okData(userRepository.getChartData());
	}
	
	public RetKit changeUserStatus(Integer userId, String state) {
		try {
			User user = userRepository.findById(userId).get();
			if (BaseConstant.UserState.EnabledState.getTypeValue().equals(state)) {
				user.setState(BaseConstant.UserState.DisableState.getTypeValue());
			} else if (BaseConstant.UserState.DisableState.getTypeValue().equals(state)) {
				user.setState(BaseConstant.UserState.EnabledState.getTypeValue());
			} else {
				return RetKit.fail();
			}
			userRepository.save(user);
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail();
		}
	}
}
