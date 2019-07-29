package com.dchip.cloudparking.web.serviceImp;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.IDeductionModelRepository;
import com.dchip.cloudparking.web.iRepository.IDeductionRepository;
import com.dchip.cloudparking.web.iRepository.IFreeStandardRepository;
import com.dchip.cloudparking.web.iRepository.IOrderRepository;
import com.dchip.cloudparking.web.iRepository.IParkingInfoRepository;
import com.dchip.cloudparking.web.iRepository.IParkingRepository;
import com.dchip.cloudparking.web.iRepository.IParkingUserRepository;
import com.dchip.cloudparking.web.iRepository.IRoadwayRepository;
import com.dchip.cloudparking.web.iService.ICashService;
import com.dchip.cloudparking.web.model.po.Deduction;
import com.dchip.cloudparking.web.model.po.FreeStandard;
import com.dchip.cloudparking.web.model.po.Order;
import com.dchip.cloudparking.web.model.po.Parking;
import com.dchip.cloudparking.web.model.po.ParkingInfo;
import com.dchip.cloudparking.web.model.po.ParkingUser;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.po.qpo.QMainControl;
import com.dchip.cloudparking.web.po.qpo.QOrder;
import com.dchip.cloudparking.web.po.qpo.QParking;
import com.dchip.cloudparking.web.po.qpo.QParkingInfo;
import com.dchip.cloudparking.web.po.qpo.QParkingUser;
import com.dchip.cloudparking.web.po.qpo.QRoadway;
import com.dchip.cloudparking.web.po.qpo.QUser;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.SocketKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class CashServiceImp extends BaseService implements ICashService {
	@Autowired
	private IRoadwayRepository roadwayRepository;
	@Autowired
	private IParkingUserRepository parkingUserRepository;
	@Autowired
	private IFreeStandardRepository freeStandardRepository;
	@Autowired
	private IOrderRepository orderRepository;
	@Autowired
	private IParkingInfoRepository parkingInfoRepository;
	@Autowired
	private IDeductionModelRepository deductionModelRepository;
	@Autowired
	private IDeductionRepository deductionRepository;
	
	@Autowired
	private IParkingRepository parkingRepository;

	public List<Map<String, Object>> getOutRoadWayListByLoginUser() {
		UserAuthentic loginUser = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Map<String, Object>> OutRoadWayList = null;
		ParkingUser parkingUser = parkingUserRepository.findParkingUserByUserName(loginUser.getUsername());

		if (loginUser.getRoleId() == null) {
			OutRoadWayList = roadwayRepository.getOutRoadName(parkingUser.getParkingId());
		}
		return OutRoadWayList;
	}

	public Object getCashList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para) {

		UserAuthentic loginUser = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ParkingUser parkingUser = parkingUserRepository.findParkingUserByUserName(loginUser.getUsername());
		List<Map<String, Object>> listData = new ArrayList<>();

		QParking qParking = QParking.parking;
		QParkingInfo qParkingInfo = QParkingInfo.parkingInfo;
		QRoadway qRoadway = QRoadway.roadway;
		QUser qUser = QUser.user;
		List<Predicate> predicates = new ArrayList<>();

		if (!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				if (map.get("searchRoadWayId") != null) {
					List<Object> results = JSON.parseArray((String) map.get("searchRoadWayId"));
					List<Integer> resultsInt = new ArrayList<Integer>();
					CollectionUtils.collect(results, new Transformer() {
						@Override
						public Object transform(Object o) {
							return Integer.valueOf(o.toString());
						}
					}, resultsInt);
					predicates.add(qParkingInfo.outRoadWayId.in(resultsInt));
				}
			}
		}

		// 不显示软删除的数据
		predicates.add(qParkingInfo.status.notIn(BaseConstant.parkingInfoStatus.finishedStatus.getValue()));
		predicates.add(qParking.id.eq(parkingUser.getParkingId()));
		predicates.add(qParkingInfo.outRoadWayId.isNotNull());

		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.selectDistinct(qUser.id, qParkingInfo.id, qParkingInfo.inRoadWayId, qParkingInfo.licensePlate,
						qParkingInfo.outDate, qParkingInfo.outRoadWayId, qParkingInfo.parkDate, qParkingInfo.status,
						qParking.parkName, qParking.id, qRoadway.roadName)
				.from(qParkingInfo).leftJoin(qParking).on(qParking.parkCode.eq(qParkingInfo.parkCode))
				.leftJoin(qRoadway).on(qRoadway.id.eq(qParkingInfo.outRoadWayId)).leftJoin(qUser)
				.on(qUser.licensePlat.eq(qParkingInfo.licensePlate))
				.where(predicates.toArray(new Predicate[predicates.size()])).orderBy(qParkingInfo.outDate.desc())
				.offset(pageNum * pageSize).limit(pageSize);

		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();

		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("userId", tuple.get(qUser.id));
			map.put("piId", tuple.get(qParkingInfo.id));
			map.put("inRoadWayId", tuple.get(qParkingInfo.inRoadWayId));
			map.put("licensePlate", tuple.get(qParkingInfo.licensePlate));
			map.put("outDate", tuple.get(qParkingInfo.outDate));
			map.put("outRoadWayId", tuple.get(qParkingInfo.outRoadWayId));
			map.put("parkDate", tuple.get(qParkingInfo.parkDate));
			map.put("status", tuple.get(qParkingInfo.status));
			map.put("parkName", tuple.get(qParking.parkName));
			map.put("parkingId", tuple.get(qParking.id));
			map.put("roadName", tuple.get(qRoadway.roadName));
			listData.add(map);
		}

		Map<String, Object> result = new HashMap<>();
		result.put("content", listData);
		result.put("totalElements", queryResults.getTotal());
		result.put("code", 0);

		return result;

	}

	@Override
	public Object getOrder(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para) {
		UserAuthentic loginUser = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ParkingUser parkingUser = parkingUserRepository.findParkingUserByUserName(loginUser.getUsername());
		List<Map<String, Object>> listData = new ArrayList<>();
		QOrder qOrder = QOrder.order;
		QParkingInfo qParkingInfo = QParkingInfo.parkingInfo;// 停车场信息
		QParkingUser Qparkinguser = QParkingUser.parkingUser;// 停车场保安、用户
		// QUser qUser = QUser.user;// 用户详情
		QParking qParking = QParking.parking;
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(qParking.id.eq(parkingUser.getParkingId()));
		predicates.add(qOrder.type.eq(BaseConstant.OrderType.cash.getTypeValue()));

		// 查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.selectDistinct(qParkingInfo.id, qParkingInfo.parkDate, qParkingInfo.outDate, qOrder.finalFee,
						qParkingInfo.licensePlate, qOrder.staffId, qOrder.status, qOrder.fee, qOrder.parkingTime,
						Qparkinguser.realName, qParking.parkName)
				.from(qParkingInfo).leftJoin(qOrder).on(qOrder.parkingInfoId.eq(qParkingInfo.id)).leftJoin(Qparkinguser)
				.on(Qparkinguser.id.eq(qOrder.staffId)).leftJoin(qParking)
				.on(qParking.parkCode.eq(qParkingInfo.parkCode))
				.where(predicates.toArray(new Predicate[predicates.size()])).orderBy(qOrder.payTime.desc())
				.offset(pageNum * pageSize).limit(pageSize);

		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();

		// 将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("parkingInfoId", tuple.get(qParkingInfo.id));
			map.put("parkDate", tuple.get(qParkingInfo.parkDate));
			map.put("outDate", tuple.get(qParkingInfo.outDate));
			map.put("licensePlate", tuple.get(qParkingInfo.licensePlate));
			map.put("staffName", tuple.get(Qparkinguser.realName));
			map.put("status", tuple.get(qOrder.status));
			map.put("fee", tuple.get(qOrder.fee));
			map.put("finalFee", tuple.get(qOrder.finalFee));
			map.put("parkingTime", tuple.get(qOrder.parkingTime));
			map.put("parkName", tuple.get(qParking.parkName));
			listData.add(map);
		}

		// jqgrid数据拼装
		Map<String, Object> result = new HashMap<>();
		result.put("content", listData);// 添加主体数据
		result.put("totalElements", queryResults.getTotal());// 添加总条数
		result.put("code", 0);
		return result;

	}

	/*
	 * start 计算两个时间相差多少分钟,算出停车时长,计算费用 start
	 */
	@Override
	public RetKit settlement(String licensePlat, Integer parkingId, Date startDate, Date endDate) {
		UserAuthentic loginUser = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ParkingUser parkingUser = parkingUserRepository.findParkingUserByUserName(loginUser.getUsername());
		if (parkingUser == null) {
			return RetKit.fail("商户信息不存在或已被删除");
		}
		FreeStandard freeStandard = freeStandardRepository.findFreeStandardByParkingId(parkingId);
		Parking parking = parkingRepository.findParkingById(parkingId);
		ParkingInfo Parkinginfo = parkingInfoRepository.findParkingInfoByParkCodeAndLicensePlate(parking.getParkCode().toString(), licensePlat);
		BigDecimal payableFee = new BigDecimal(0);
		BigDecimal finalFee = new BigDecimal(0);
		int totalMinute = 0;
		long between = (endDate.getTime() - startDate.getTime()) / 1000;// 除以1000是为了转换成秒
//		long days = between / ( 60 * 60 * 24);  //天数
//		long hour = (between - days * ( 60 * 60 * 24)) / (60 * 60); //小时
//		long minutes = (between - days * ( 60 * 60 * 24) - hour * (60 * 60)) / (60);
//		if (minutes > 0) {
//			hour +=1;
//		}
		
		long min = between / 60; // 分钟
		totalMinute = Math.toIntExact(min);
		long hours = (long) Math.floor(totalMinute / 60);
		long minute = totalMinute % 60;
		if (minute > 0) {
			hours = hours + 1;
		}
		payableFee = new BigDecimal(hours).multiply(freeStandard.getHourCost());
		// end

		// 查询是否有抵扣券 start
		//Integer parkingUserId = parkingUser.getId();
//		 deductionModelRepository.getDeductionModelByParkingId(parkingUser.getParkingId());
		// 未被使用并且还没过期的抵扣券
		Deduction deduction = deductionRepository.getCanUseDeduction(licensePlat, parkingUser.getParkingId());
		int minusHour = 0;
		if(deduction != null){
			minusHour = deduction.getHourNum();
		}
		/* 2018/12/06 start */
        //抵扣卷
        //Deduction deduction = FeeServiceDeduction.getDeduction(jpaQueryFactory, parkingInfo.getLicensePlate(), outDate);
		
/*		if (deduction == null) {
			if (totalMinute < freeStandard.getFreeTimeLength()) {// 还没超过免费时长
				finalFee = new BigDecimal(0);
			} else if (freeStandard.getMostCost() != null && new BigDecimal(hours).multiply(freeStandard.getHourCost())
					.doubleValue() < freeStandard.getMostCost().doubleValue()) {// 没超过当日最高收费
				finalFee = new BigDecimal(hours).multiply(freeStandard.getHourCost());
			} else if (freeStandard.getMostCost() != null && new BigDecimal(hours).multiply(freeStandard.getHourCost())
					.doubleValue() >=freeStandard.getMostCost().doubleValue()) {// 没超过当日最高收费
				finalFee = freeStandard.getMostCost();
			}else if (freeStandard.getMostCost() == null) {// 没有设置当日最高收费
				finalFee = new BigDecimal(hours).multiply(freeStandard.getHourCost());
			} 
		} else {
			long hourNum = deduction.getHourNum();
			BigDecimal deFee = new BigDecimal(hourNum).multiply(freeStandard.getHourCost());
			if (totalMinute < freeStandard.getFreeTimeLength()) {// 还没超过免费时长
				finalFee = new BigDecimal(0);
			} else if (freeStandard.getMostCost() != null && new BigDecimal(hours).multiply(freeStandard.getHourCost())
					.doubleValue() < freeStandard.getMostCost().doubleValue()) {// 没超过当日最高收费
				finalFee = (new BigDecimal(hours).multiply(freeStandard.getHourCost())).subtract(deFee);
//				finalFee=finalFee.subtract(deFee);
			}else if (freeStandard.getMostCost() != null && new BigDecimal(hours).multiply(freeStandard.getHourCost())
					.doubleValue() >= freeStandard.getMostCost().doubleValue()){
				finalFee = freeStandard.getMostCost().subtract(deFee);
				
			}
			else if (freeStandard.getMostCost() == null) {// 没有设置当日最高收费
				finalFee = new BigDecimal(hours).multiply(freeStandard.getHourCost());
				finalFee=finalFee.subtract(deFee);
			} 
		}*/

		BigDecimal totalFee =getNewParkingFee(endDate, Parkinginfo, freeStandard);
		BigDecimal bigDecimalHour = new BigDecimal(minusHour);
		totalFee = totalFee.subtract(bigDecimalHour.multiply(freeStandard.getHourCost()));
		Map<String, Object> map = new HashMap<>();
		map.put("parkingTime", totalMinute);
		map.put("Fee", totalFee);
		return RetKit.okData(map);
	}
	
	public static BigDecimal getNewParkingFee(Date outDate, ParkingInfo parkingInfo, FreeStandard freeStandard) {
		BigDecimal currentDayFree = new BigDecimal(0);
		Date parkDate = parkingInfo.getParkDate();
		Date parkDateEndTime = new Date();// 停车当天的最大时间
		parkDateEndTime.setYear(parkDate.getYear());
		parkDateEndTime.setMonth(parkDate.getMonth());
		parkDateEndTime.setDate(parkDate.getDate());
		parkDateEndTime.setHours(24);
		parkDateEndTime.setMinutes(0);
		parkDateEndTime.setSeconds(0);
		if (outDate.getTime() > parkDateEndTime.getTime()) {// 停车超过凌晨24点重新计算
			int parkingFirstDayMinute = calculateMinute(parkDate, parkDateEndTime); // 停车超过24点
			int firstTotalHour = parkingFirstDayMinute / 60;// 停了多少小时
			int remainderMinute = parkingFirstDayMinute - firstTotalHour * 60; // 剩余的分钟
			if (remainderMinute >= 0) {
				firstTotalHour += 1;
			}
			/**
			 * 停车当天
			 */
			if (parkingFirstDayMinute < freeStandard.getFreeTimeLength()) {// 还没超过免费时长
				currentDayFree = new BigDecimal(0);
			} else if (freeStandard.getMostCost() != null
					&& new BigDecimal(firstTotalHour).multiply(freeStandard.getHourCost())
							.doubleValue() < freeStandard.getMostCost().doubleValue()) {
				currentDayFree = new BigDecimal(firstTotalHour).multiply(freeStandard.getHourCost());
			} else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
				currentDayFree = new BigDecimal(firstTotalHour).multiply(freeStandard.getHourCost());
			} else {
				currentDayFree = freeStandard.getMostCost();
			}
			/**
			 * 停车当天之后的时间
			 */
			int parkingFirstDayOtherMinute = calculateMinute(parkDateEndTime, outDate); // 停车当天之后的时间
			int afterFirstDayDays = parkingFirstDayOtherMinute / 60 / 24;// 第一天停车之后 后面停了多少天
			int afterFirstDayMinutes = parkingFirstDayOtherMinute - afterFirstDayDays * 24 * 60; // 剩余的分钟
			if (afterFirstDayDays > 0) {
				// 超出停车当日的天数
				if (freeStandard.getMostCost() != null
						&& new BigDecimal(24).multiply(freeStandard.getHourCost()).doubleValue() < freeStandard
								.getMostCost().doubleValue()) {
					currentDayFree = currentDayFree
							.add(new BigDecimal(24 * afterFirstDayDays).multiply(freeStandard.getHourCost()));
				} else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
					currentDayFree = currentDayFree
							.add(new BigDecimal(24 * afterFirstDayDays).multiply(freeStandard.getHourCost()));
				} else {
					currentDayFree = currentDayFree
							.add(freeStandard.getMostCost().multiply(new BigDecimal(afterFirstDayDays)));
				}
				// 还要加上余数分钟
				int endDayHour = afterFirstDayMinutes / 60;// 最后不到一天的小时数
				int endDayHourMinute = afterFirstDayMinutes - endDayHour * 60;// 最后不到一天的小时数-剩余的余数分钟数
				if (endDayHourMinute >= 0) {
					endDayHour += 1;
				}
				// 超出停车当日的天数-但是剩余的不够一天的
				if (freeStandard.getMostCost() != null
						&& new BigDecimal(endDayHour).multiply(freeStandard.getHourCost())
								.doubleValue() < freeStandard.getMostCost().doubleValue()) {
					currentDayFree = currentDayFree
							.add(new BigDecimal(endDayHour).multiply(freeStandard.getHourCost()));
				} else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
					currentDayFree = currentDayFree
							.add(new BigDecimal(endDayHour).multiply(freeStandard.getHourCost()));
				} else {
					currentDayFree = currentDayFree.add(freeStandard.getMostCost());
				}
			} else {
				int secondDayHours = parkingFirstDayOtherMinute / 60; // 只超了停车当天但是又不满一天
				int secondDayMinutes = parkingFirstDayOtherMinute - secondDayHours * 60; // 只超了停车当天但是又不满一天-剩余的分钟的余数
				if (secondDayMinutes >= 0) {
					secondDayHours += 1;
				}
				if (freeStandard.getMostCost() != null
						&& new BigDecimal(secondDayHours).multiply(freeStandard.getHourCost())
								.doubleValue() < freeStandard.getMostCost().doubleValue()) {
					currentDayFree = currentDayFree
							.add(new BigDecimal(secondDayHours).multiply(freeStandard.getHourCost()));
				} else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
					currentDayFree = currentDayFree
							.add(new BigDecimal(secondDayHours).multiply(freeStandard.getHourCost()));
				} else {
					currentDayFree = currentDayFree.add(freeStandard.getMostCost());
				}
			}
		} else {
			int parkingFirstDayMinute = calculateMinute(parkDate, outDate); // 停车不超过停车当天
			int totalHour = parkingFirstDayMinute / 60;
			int remainderMinute = (int) parkingFirstDayMinute - totalHour * 60 - freeStandard.getFreeTimeLength(); // 剩余的分钟的余数
			if (remainderMinute >= 0) {
				totalHour += 1;
			}
			if (parkingFirstDayMinute < freeStandard.getFreeTimeLength()) {// 还没超过免费时长
				currentDayFree = new BigDecimal(0);
			} else if (freeStandard.getMostCost() != null
					&& new BigDecimal(totalHour).multiply(freeStandard.getHourCost())
							.doubleValue() < freeStandard.getMostCost().doubleValue()) {
				currentDayFree = new BigDecimal(totalHour).multiply(freeStandard.getHourCost());
			} else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
				currentDayFree = new BigDecimal(totalHour).multiply(freeStandard.getHourCost());
			} else {
				currentDayFree = freeStandard.getMostCost();
			}
		}
		return currentDayFree;
	}
	
	/*
	 * 计算两个时间相差多少分钟
	 */
	private static  Integer calculateMinute(Date startDate, Date endDate) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int totalMinute = 0;
		long between = (endDate.getTime() - startDate.getTime()) / 1000;// 除以1000是为了转换成秒
		long min = between / 60;
		totalMinute = Math.toIntExact(min);

		return Math.toIntExact(totalMinute);
	}

	@Override
	public RetKit generateOrder(BigDecimal Fee, Integer parkingInfoId, Long parkingTime,String licensePlate) {
		try {
			UserAuthentic loginUser = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			ParkingUser parkingUser = parkingUserRepository.findParkingUserByUserName(loginUser.getUsername());
			if (parkingUser != null) {
				Integer parkingId = parkingUser.getParkingId();
				Integer staffId = parkingUser.getId();// 保安ID

				QRoadway qRoadway = QRoadway.roadway;
				QMainControl qMaincontrol = QMainControl.mainControl;

				List<Predicate> predicates = new ArrayList<>();
				predicates.add(qRoadway.parkingId.eq(parkingId));

				Tuple tuple = jpaQueryFactory.selectDistinct(qRoadway.cameraMac, qMaincontrol.mac).from(qRoadway)
						.leftJoin(qMaincontrol).on(qMaincontrol.parkingId.eq(qRoadway.parkingId))
						.where(qRoadway.parkingId.eq(parkingId)).fetchFirst();
				String controlMac = tuple.get(qMaincontrol.mac);
				String cameraMac = tuple.get(qRoadway.cameraMac);
				SocketKit.sendMessage(controlMac, cameraMac, "1");// 通知开闸

				Optional<ParkingInfo> parkingInfoOptional = parkingInfoRepository.findById(parkingInfoId);
				if (parkingInfoOptional.isPresent()) {
					ParkingInfo parkingInfo = parkingInfoOptional.get();
					parkingInfo.setStatus(BaseConstant.parkingInfoStatus.finishedStatus.getValue());
					parkingInfoRepository.save(parkingInfo);
				} else {
					return RetKit.fail("停车信息不存在或已丢失");
				}
				Deduction deduction = deductionRepository.getCanUseDeduction(licensePlate, parkingUser.getParkingId());
				if (deduction != null) {
					deduction.setStatus(BaseConstant.DeductionStatus.UsedStatus.getTypeValue());
					deduction.setUseTime(new Date());
					deductionRepository.save(deduction);
				}
				// 生成订单
				Order order = new Order();
				order.setFee(Fee);
				order.setFinalFee(Fee);
				order.setApplyCash(Fee);
				int parkingTimeStr = Integer.parseInt(String.valueOf(parkingTime));
				order.setParkingTime(parkingTimeStr);
				order.setStaffId(staffId);
				order.setType(BaseConstant.OrderType.cash.getTypeValue());
				order.setStatus(BaseConstant.OrderStatus.AlreadyPay.getTypeValue());
				order.setParkingInfoId(parkingInfoId);
				order.setPayTime(new Date());
				orderRepository.save(order);

				return RetKit.ok("开闸成功	");
			} else {
				return RetKit.fail(loginUser.getUsername() + "账号不存在");
			}
		} catch (Exception e) {
			return RetKit.fail("操作失败");
		}
	}

}
