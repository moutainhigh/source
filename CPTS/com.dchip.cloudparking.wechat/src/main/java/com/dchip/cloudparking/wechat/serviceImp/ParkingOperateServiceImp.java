package com.dchip.cloudparking.wechat.serviceImp;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.wechat.constant.BaseConstant;
import com.dchip.cloudparking.wechat.iRepository.ICardRepository;
import com.dchip.cloudparking.wechat.iRepository.ICompanyRepository;
import com.dchip.cloudparking.wechat.iRepository.IFreeStandardRepository;
import com.dchip.cloudparking.wechat.iRepository.IOrderRepository;
import com.dchip.cloudparking.wechat.iRepository.IParkingInfoRepository;
import com.dchip.cloudparking.wechat.iRepository.IParkingRepository;
import com.dchip.cloudparking.wechat.iRepository.IUserRepository;
import com.dchip.cloudparking.wechat.iService.IParkingOperateService;
import com.dchip.cloudparking.wechat.model.po.Card;
import com.dchip.cloudparking.wechat.model.po.Company;
import com.dchip.cloudparking.wechat.model.po.Deduction;
import com.dchip.cloudparking.wechat.model.po.FreeStandard;
import com.dchip.cloudparking.wechat.model.po.Order;
import com.dchip.cloudparking.wechat.model.po.Parking;
import com.dchip.cloudparking.wechat.model.po.ParkingInfo;
import com.dchip.cloudparking.wechat.model.po.ParkingRule;
import com.dchip.cloudparking.wechat.model.po.User;
import com.dchip.cloudparking.wechat.model.qpo.QParkingRule;
import com.dchip.cloudparking.wechat.model.qpo.QParkingRuleRelation;
import com.dchip.cloudparking.wechat.pay.util.PayUtil;
import com.dchip.cloudparking.wechat.utils.DateUtil;
import com.dchip.cloudparking.wechat.utils.ParkingFeeUtil;
import com.dchip.cloudparking.wechat.utils.RetKit;
import com.dchip.cloudparking.wechat.utils.parkingfee.FeeModel;
import com.dchip.cloudparking.wechat.utils.parkingfee.FeeServiceDeduction;
import com.dchip.cloudparking.wechat.utils.parkingfee.FeeServiceKit;
@Service
public class ParkingOperateServiceImp extends BaseService implements IParkingOperateService {
	
	@Autowired
	private IParkingInfoRepository parkingInfoRepository;	
	@Autowired
	private IParkingRepository parkingRepository;	
	@Autowired
	private IFreeStandardRepository freeStandardRepository;
	@Autowired
	private ICardRepository cardRepository;
	@Autowired
	private IOrderRepository orderRepository;
	@Autowired
	private ICompanyRepository companyRepository;
	@Autowired
	private IUserRepository userRepository;
	
	
	public Map<String,Object> haveACardPayPage(Integer parkingInfoId, Integer type){
		Map<String,Object> returnObj = new HashMap<>();
		Integer payFlag = 0;   //支付标志   0 为未支付     1为已支付
		ParkingInfo parkingInfo = parkingInfoRepository.findById(parkingInfoId).get();
		GregorianCalendar ca = new GregorianCalendar();
		
		if((parkingInfo.getStatus()).equals(new Character(BaseConstant.parkingInfoStatus.finishedStatus.getValue()))) {
			payFlag = 1;
		}
		Parking parkingDetails = parkingRepository.findParkingByParkCode(parkingInfo.getParkCode());
		Integer parkingId = parkingDetails.getId();
		//获取公司信息
		Company company = companyRepository.findCompanyById(parkingDetails.getCompanyId()); 
		FreeStandard freeStandard = freeStandardRepository.findFreeStandardByParkingId(parkingId);
		SimpleDateFormat parkingDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long time = null;

		/* 2019/01/18 start */
		Deduction deduction = FeeServiceDeduction.getDeduction(jpaQueryFactory, parkingInfo.getLicensePlate(), parkingDf.format(parkingInfo.getOutDate()));
		int minusHour = 0;
		if(deduction != null){
			minusHour = deduction.getHourNum();
		}
		FeeModel feeModel = new FeeModel();
		feeModel.setHourCost(freeStandard.getHourCost().doubleValue());
		feeModel.setMostCostDaily(freeStandard.getMostCost().doubleValue());
		feeModel.setFreeMinute(freeStandard.getFreeTimeLength() + minusHour * 60);
		FeeServiceKit feeServiceKit = null;
		/* 2019/01/18 end */


		if(type == BaseConstant.CardType.NonCard.getTypeValue()) { //不是月卡用户，正常计算
			time  = (parkingInfo.getOutDate().getTime())-(parkingInfo.getParkDate().getTime());		
			/* 2019/01/18 start */
			feeServiceKit = new FeeServiceKit(
					feeModel,
					DateUtil.getDateTimeStr(parkingInfo.getParkDate()),
					parkingDf.format(parkingInfo.getOutDate()),
					null,
					null);
			/* 2019/01/18 end */
		} else if(type == BaseConstant.CardType.Month.getTypeValue()) { //自然月-的月卡用户
			Card card = cardRepository.findCardByLisencePlateAndParkingId(parkingInfo.getLicensePlate(), parkingId.toString());
			ca.clear();
			ca.setTime(card.getExpiryTime());
			ca.add(GregorianCalendar.DATE, 1);
			time = (parkingInfo.getOutDate().getTime()) - ca.getTime().getTime();
			/* 2019/01/18 start */
			feeServiceKit = new FeeServiceKit(
					feeModel,
					DateUtil.getDateTimeStr(parkingInfo.getParkDate()),
					parkingDf.format(parkingInfo.getOutDate()),
					DateUtil.getDateTimeStr(card.getExpiryTime()),
					BaseConstant.CardType.Month.getTypeValue());
			/* 2019/01/18 end */
		}else if(type == BaseConstant.CardType.WorkDay.getTypeValue()) { //工作日-的月卡用户
			//查询出场规则
			QParkingRule ruleEntity =  QParkingRule.parkingRule;
			QParkingRuleRelation relationEntity = QParkingRuleRelation.parkingRuleRelation;
			ParkingRule rule = this.jpaQueryFactory.select(ruleEntity).from(ruleEntity, relationEntity)
					.where(ruleEntity.id.eq(relationEntity.parkingRuleId)
							.and(relationEntity.parkingId.eq(parkingInfo.getId())
									.and(ruleEntity.ruleType.eq(BaseConstant.RoadMarker.OUT.getTypeValue())
											.and(ruleEntity.inRule.eq(BaseConstant.CardType.WorkDay.getTypeValue()))))).fetchFirst();

			/* 2019/01/18 start */
			Card card = cardRepository.findCardByLisencePlateAndParkingId(parkingInfo.getLicensePlate(), parkingId.toString());
			feeServiceKit = new FeeServiceKit(
							feeModel,
							DateUtil.getDateTimeStr(parkingInfo.getParkDate()),
							parkingDf.format(parkingInfo.getOutDate()),
							DateUtil.getDateTimeStr(card.getExpiryTime()),
							BaseConstant.CardType.WorkDay.getTypeValue(),
							rule.getStartDay(),
							rule.getEndDay());
			/* 2019/01/18 end */
		}else if(type == BaseConstant.CardType.WorkTime.getTypeValue()) {  //工作时-的月卡用户
			/* 2019/01/18 start */
			//查询出场规则
			QParkingRule ruleEntity =  QParkingRule.parkingRule;
			QParkingRuleRelation relationEntity = QParkingRuleRelation.parkingRuleRelation;
			ParkingRule rule = this.jpaQueryFactory.select(ruleEntity).from(ruleEntity, relationEntity)
					.where(ruleEntity.id.eq(relationEntity.parkingRuleId)
							.and(relationEntity.parkingId.eq(parkingInfo.getId())
									.and(ruleEntity.ruleType.eq(BaseConstant.RoadMarker.OUT.getTypeValue())
											.and(ruleEntity.inRule.eq(BaseConstant.CardType.WorkDay.getTypeValue()))))).fetchFirst();
			Card card = cardRepository.findCardByLisencePlateAndParkingId(parkingInfo.getLicensePlate(), parkingId.toString());
			feeServiceKit = new FeeServiceKit(
					feeModel,
					DateUtil.getDateTimeStr(parkingInfo.getParkDate()),
					parkingDf.format(parkingInfo.getOutDate()),
					DateUtil.getDateTimeStr(card.getExpiryTime()),
					BaseConstant.CardType.WorkTime.getTypeValue(),
					rule.getStartDay(),
					rule.getEndDay(),
					rule.getStartTime(),
					rule.getStartTime());
			/* 2019/01/18 end */
		}
		Map<String,Object> feeAndParkingTime =  ParkingFeeUtil.getParkingFee(time, freeStandard.getHourCost(), freeStandard.getMostCost(), freeStandard.getFreeTimeLength());
		returnObj.put("payFlag", payFlag);
		returnObj.put("licensePlate", parkingInfo.getLicensePlate());
		returnObj.put("outDate", parkingDf.format(parkingInfo.getOutDate()));
		returnObj.put("parkTime", parkingDf.format(parkingInfo.getParkDate()));
		returnObj.put("totalFee",ParkingFeeUtil.getNewParkingFee(parkingInfo.getOutDate(), parkingInfo, freeStandard) );
		Integer parkingLen = (int)(time/1000)/60;
		returnObj.put("parkingLen", parkingLen);
		returnObj.put("showParkingLen", feeAndParkingTime.get("timeLen"));
		returnObj.put("companyName", company.getName());

		/* 2019/01/18 start */
		if(feeServiceKit != null ){
			returnObj.put("totalFee",feeServiceKit.getResultFee());
			FeeServiceDeduction.disableDeduction(jpaQueryFactory,parkingInfo.getUserId(),deduction);
		}
		/* 2019/01/18 end */
		return returnObj;
	}
	
	/**
	 * 微信结账操作
	 */
	public RetKit rechoningOpearte(String parkingId, String licensePlate) {
		try {
			Parking parking = parkingRepository.findParkingById(parkingId);
			SimpleDateFormat parkingDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			GregorianCalendar ca = new GregorianCalendar();
			if(parking == null) {
				return RetKit.fail("查无此停车场");
			}
			
			ParkingInfo parkingInfo = parkingInfoRepository.findParkingInfoByParkCodeAndLicensePlate(Integer.toString((parking.getParkCode())), licensePlate);
			if(parkingInfo == null) {
				return RetKit.fail("查无该车未缴费停车记录，请检查车牌是否输入正确，车辆是否停在此停车场("+ parking.getParkName() +")内");
			}
			
			Order getOrder = orderRepository.findOrderByParkingInfoId(parkingInfo.getId());
			if(getOrder != null) {
				long freeTime = (new Date()).getTime() - getOrder.getPayTime().getTime();
				Integer freeTimeInt = (int) (freeTime/(1000 * 60));
				if(freeTimeInt <= 15) { //15分钟内离场可以不收费
					return RetKit.fail("该车辆已结账，请尽快离场");			
				}
				else {
					parkingInfo.setStatus(BaseConstant.parkingInfoStatus.finishedStatus.getValue());
					parkingInfo.setOutDate(getOrder.getPayTime());
					parkingInfoRepository.save(parkingInfo);
					ParkingInfo newParkingInfo = new ParkingInfo();
					BeanUtils.copyProperties(newParkingInfo, parkingInfo);
					newParkingInfo.setId(null);
					newParkingInfo.setStatus(BaseConstant.parkingInfoStatus.unfinishedStatus.getValue());
					newParkingInfo.setParkDate(getOrder.getPayTime());
					newParkingInfo.setOutDate(null);
					newParkingInfo = parkingInfoRepository.save(newParkingInfo);
					parkingInfo = newParkingInfo;
				}				
			}
			
			User user = userRepository.findUserBylicensePlat(parkingInfo.getLicensePlate());
			if(user != null) {
				return RetKit.fail("该车为会员车辆，可直接离场，享受无感支付体验");
			}
			
			Card allCard = cardRepository.findCardByLisencePlateAndParkingId(licensePlate, parkingId);
			if(allCard!=null) {
				if(allCard.getExpiryTime().getTime() >= (new Date()).getTime()) {
					return RetKit.fail("该车为本停车场的月卡车辆，可直接离场");
				}
			}
			
			//保存结账时间
			parkingInfo.setOutDate(new Date());
			parkingInfo = parkingInfoRepository.save(parkingInfo);
			//计算费用			
			
			Integer type = allCard == null ? BaseConstant.CardType.NonCard.getTypeValue():allCard.getType();  //类型
			FreeStandard freeStandard = freeStandardRepository.findFreeStandardByParkingId(Integer.parseInt(parkingId));
			Long time = null;
			
			/* 2019/01/18 start */
			Deduction deduction = FeeServiceDeduction.getDeduction(jpaQueryFactory, parkingInfo.getLicensePlate(), parkingDf.format(parkingInfo.getOutDate()));
			int minusHour = 0;
			if(deduction != null){
				minusHour = deduction.getHourNum();
			}
			FeeModel feeModel = new FeeModel();
			feeModel.setHourCost(freeStandard.getHourCost().doubleValue());
			feeModel.setMostCostDaily(freeStandard.getMostCost().doubleValue());
			feeModel.setFreeMinute(freeStandard.getFreeTimeLength() + minusHour * 60);
			FeeServiceKit feeServiceKit = null;
			/* 2019/01/18 end */
			
			if(type == BaseConstant.CardType.NonCard.getTypeValue()) { //不是月卡用户，正常计算
				time  = (parkingInfo.getOutDate().getTime())-(parkingInfo.getParkDate().getTime());
				/* 2019/01/18 start */
				feeServiceKit = new FeeServiceKit(
						feeModel,
						DateUtil.getDateTimeStr(parkingInfo.getParkDate()),
						parkingDf.format(parkingInfo.getOutDate()),
						null,
						null);
				/* 2019/01/18 end */
			} else if(type == BaseConstant.CardType.Month.getTypeValue()) { //自然月-的月卡用户
				Card card = cardRepository.findCardByLisencePlateAndParkingId(parkingInfo.getLicensePlate(), parkingId);
				ca.clear();
				ca.setTime(card.getExpiryTime());
				ca.add(GregorianCalendar.DATE, 1);
				time = (parkingInfo.getOutDate().getTime()) - ca.getTime().getTime();
				/* 2019/01/18 start */
				feeServiceKit = new FeeServiceKit(
						feeModel,
						DateUtil.getDateTimeStr(parkingInfo.getParkDate()),
						parkingDf.format(parkingInfo.getOutDate()),
						DateUtil.getDateTimeStr(card.getExpiryTime()),
						BaseConstant.CardType.Month.getTypeValue());
				/* 2019/01/18 end */
			}else if(type == BaseConstant.CardType.WorkDay.getTypeValue()) { //工作日-的月卡用户
				//查询出场规则
				QParkingRule ruleEntity =  QParkingRule.parkingRule;
				QParkingRuleRelation relationEntity = QParkingRuleRelation.parkingRuleRelation;
				ParkingRule rule = this.jpaQueryFactory.select(ruleEntity).from(ruleEntity, relationEntity)
						.where(ruleEntity.id.eq(relationEntity.parkingRuleId)
								.and(relationEntity.parkingId.eq(parkingInfo.getId())
										.and(ruleEntity.ruleType.eq(BaseConstant.RoadMarker.OUT.getTypeValue())
												.and(ruleEntity.inRule.eq(BaseConstant.CardType.WorkDay.getTypeValue()))))).fetchFirst();

				/* 2019/01/18 start */
				Card card = cardRepository.findCardByLisencePlateAndParkingId(parkingInfo.getLicensePlate(), parkingId);
				feeServiceKit = new FeeServiceKit(
								feeModel,
								DateUtil.getDateTimeStr(parkingInfo.getParkDate()),
								parkingDf.format(parkingInfo.getOutDate()),
								DateUtil.getDateTimeStr(card.getExpiryTime()),
								BaseConstant.CardType.WorkDay.getTypeValue(),
								rule.getStartDay(),
								rule.getEndDay());
				/* 2019/01/18 end */
			}else if(type == BaseConstant.CardType.WorkTime.getTypeValue()) {  //工作时-的月卡用户
				/* 2019/01/18 start */
				//查询出场规则
				QParkingRule ruleEntity =  QParkingRule.parkingRule;
				QParkingRuleRelation relationEntity = QParkingRuleRelation.parkingRuleRelation;
				ParkingRule rule = this.jpaQueryFactory.select(ruleEntity).from(ruleEntity, relationEntity)
						.where(ruleEntity.id.eq(relationEntity.parkingRuleId)
								.and(relationEntity.parkingId.eq(parkingInfo.getId())
										.and(ruleEntity.ruleType.eq(BaseConstant.RoadMarker.OUT.getTypeValue())
												.and(ruleEntity.inRule.eq(BaseConstant.CardType.WorkDay.getTypeValue()))))).fetchFirst();
				Card card = cardRepository.findCardByLisencePlateAndParkingId(parkingInfo.getLicensePlate(), parkingId);
				feeServiceKit = new FeeServiceKit(
						feeModel,
						DateUtil.getDateTimeStr(parkingInfo.getParkDate()),
						parkingDf.format(parkingInfo.getOutDate()),
						DateUtil.getDateTimeStr(card.getExpiryTime()),
						BaseConstant.CardType.WorkTime.getTypeValue(),
						rule.getStartDay(),
						rule.getEndDay(),
						rule.getStartTime(),
						rule.getStartTime());
				/* 2019/01/18 end */
			}
			
			
			Map<String,Object> feeAndParkingTime =  ParkingFeeUtil.getParkingFee(time, 
					freeStandard.getHourCost(), freeStandard.getMostCost(), freeStandard.getFreeTimeLength());
			
			//返回结果
			Map<String, Object> returnObj = new HashMap<String, Object>();
			BigDecimal bigDecimalHour = new BigDecimal(minusHour);
			BigDecimal totalFee = (ParkingFeeUtil.getNewParkingFee(parkingInfo.getOutDate(), parkingInfo, freeStandard)).subtract(bigDecimalHour.multiply(freeStandard.getHourCost()));
			if (totalFee.compareTo(BigDecimal.ZERO) == -1) {
				totalFee = BigDecimal.ZERO;
			}
			returnObj.put("outDate", parkingDf.format(parkingInfo.getOutDate()));
			returnObj.put("totalFee", totalFee);
			Integer parkingLen = (int)(time/1000)/60;
			returnObj.put("parkingLen", parkingLen);
			returnObj.put("showParkingLen", feeAndParkingTime.get("timeLen"));
			returnObj.put("licensePlate", parkingInfo.getLicensePlate());
			returnObj.put("parkingInfoId", parkingInfo.getId());
			returnObj.put("freeTime", freeStandard.getFreeTimeLength());
			
			/* 2019/01/18 start */
			if(feeServiceKit != null ){
				returnObj.put("totalFee",feeServiceKit.getResultFee());
				FeeServiceDeduction.disableDeduction(jpaQueryFactory,parkingInfo.getUserId(),deduction);
			}
			/* 2019/01/18 end */
			
			if(parkingLen <= freeStandard.getFreeTimeLength()) {
				returnObj.put("showfree", true);  //免费时长内
			}else {
				returnObj.put("showfree", false);
				Order order = new Order();//保存订单
				order.setParkingInfoId(parkingInfo.getId());
				order.setStatus(BaseConstant.OrderStatus.AdvanceUnPay.getTypeValue());
				order = orderRepository.save(order);
			}
			
			return RetKit.okData(returnObj);
		} catch (Exception e) {
			
		}
		return RetKit.ok();
	}
	/**
	 * 支付宝结账操作
	 */
	public RetKit alipayRechoningOpearte(String parkingId, String licensePlate) {
		try {
			Parking parking = parkingRepository.findParkingById(parkingId);
			SimpleDateFormat parkingDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			GregorianCalendar ca = new GregorianCalendar();
			if(parking == null) {
				return RetKit.fail("查无此停车场");
			}
			
			ParkingInfo parkingInfo = parkingInfoRepository.findParkingInfoByParkCodeAndLicensePlate(Integer.toString((parking.getParkCode())), licensePlate);
			if(parkingInfo == null) {
				return RetKit.fail("查无该车未缴费停车记录，请检查车牌是否输入正确，车辆是否停在此停车场("+ parking.getParkName() +")内");
			}
			
			Order getOrder = orderRepository.findOrderByParkingInfoId(parkingInfo.getId());
			if(getOrder != null) {
				long freeTime = (new Date()).getTime() - getOrder.getPayTime().getTime();
				Integer freeTimeInt = (int) (freeTime/(1000 * 60));
				if(freeTimeInt <= 15) { //15分钟内离场可以不收费
					return RetKit.fail("该车辆已结账，请尽快离场");			
				}
				else {
					parkingInfo.setStatus(BaseConstant.parkingInfoStatus.finishedStatus.getValue());
					parkingInfo.setOutDate(getOrder.getPayTime());
					parkingInfoRepository.save(parkingInfo);
					ParkingInfo newParkingInfo = new ParkingInfo();
					BeanUtils.copyProperties(newParkingInfo, parkingInfo);
					newParkingInfo.setId(null);
					newParkingInfo.setStatus(BaseConstant.parkingInfoStatus.unfinishedStatus.getValue());
					newParkingInfo.setParkDate(getOrder.getPayTime());
					newParkingInfo.setOutDate(null);
					newParkingInfo = parkingInfoRepository.save(newParkingInfo);
					parkingInfo = newParkingInfo;
				}				
			}
			
			User user = userRepository.findUserBylicensePlat(parkingInfo.getLicensePlate());
			if(user != null) {
				return RetKit.fail("该车为会员车辆，可直接离场，享受无感支付体验");
			}
			
			Card allCard = cardRepository.findCardByLisencePlateAndParkingId(licensePlate, parkingId);
			if(allCard!=null) {
				if(allCard.getExpiryTime().getTime() >= (new Date()).getTime()) {
					return RetKit.fail("该车为本停车场的月卡车辆，可直接离场");
				}
			}
			
			//保存结账时间
			parkingInfo.setOutDate(new Date());
			parkingInfo = parkingInfoRepository.save(parkingInfo);
			//计算费用			
			
			Integer type = allCard == null ? BaseConstant.CardType.NonCard.getTypeValue():allCard.getType();  //类型
			FreeStandard freeStandard = freeStandardRepository.findFreeStandardByParkingId(Integer.parseInt(parkingId));
			Long time = null;
			
			/* 2019/01/18 start */
			Deduction deduction = FeeServiceDeduction.getDeduction(jpaQueryFactory, parkingInfo.getLicensePlate(), parkingDf.format(parkingInfo.getOutDate()));
			int minusHour = 0;
			if(deduction != null){
				minusHour = deduction.getHourNum();
			}
			FeeModel feeModel = new FeeModel();
			feeModel.setHourCost(freeStandard.getHourCost().doubleValue());
			feeModel.setMostCostDaily(freeStandard.getMostCost().doubleValue());
			feeModel.setFreeMinute(freeStandard.getFreeTimeLength() + minusHour * 60);
			FeeServiceKit feeServiceKit = null;
			/* 2019/01/18 end */
			
			if(type == BaseConstant.CardType.NonCard.getTypeValue()) { //不是月卡用户，正常计算
				time  = (parkingInfo.getOutDate().getTime())-(parkingInfo.getParkDate().getTime());
				/* 2019/01/18 start */
				feeServiceKit = new FeeServiceKit(
						feeModel,
						DateUtil.getDateTimeStr(parkingInfo.getParkDate()),
						parkingDf.format(parkingInfo.getOutDate()),
						null,
						null);
				/* 2019/01/18 end */
			} else if(type == BaseConstant.CardType.Month.getTypeValue()) { //自然月-的月卡用户
				Card card = cardRepository.findCardByLisencePlateAndParkingId(parkingInfo.getLicensePlate(), parkingId);
				ca.clear();
				ca.setTime(card.getExpiryTime());
				ca.add(GregorianCalendar.DATE, 1);
				time = (parkingInfo.getOutDate().getTime()) - ca.getTime().getTime();
				/* 2019/01/18 start */
				feeServiceKit = new FeeServiceKit(
						feeModel,
						DateUtil.getDateTimeStr(parkingInfo.getParkDate()),
						parkingDf.format(parkingInfo.getOutDate()),
						DateUtil.getDateTimeStr(card.getExpiryTime()),
						BaseConstant.CardType.Month.getTypeValue());
				/* 2019/01/18 end */
			}else if(type == BaseConstant.CardType.WorkDay.getTypeValue()) { //工作日-的月卡用户
				//查询出场规则
				QParkingRule ruleEntity =  QParkingRule.parkingRule;
				QParkingRuleRelation relationEntity = QParkingRuleRelation.parkingRuleRelation;
				ParkingRule rule = this.jpaQueryFactory.select(ruleEntity).from(ruleEntity, relationEntity)
						.where(ruleEntity.id.eq(relationEntity.parkingRuleId)
								.and(relationEntity.parkingId.eq(parkingInfo.getId())
										.and(ruleEntity.ruleType.eq(BaseConstant.RoadMarker.OUT.getTypeValue())
												.and(ruleEntity.inRule.eq(BaseConstant.CardType.WorkDay.getTypeValue()))))).fetchFirst();

				/* 2019/01/18 start */
				Card card = cardRepository.findCardByLisencePlateAndParkingId(parkingInfo.getLicensePlate(), parkingId);
				feeServiceKit = new FeeServiceKit(
								feeModel,
								DateUtil.getDateTimeStr(parkingInfo.getParkDate()),
								parkingDf.format(parkingInfo.getOutDate()),
								DateUtil.getDateTimeStr(card.getExpiryTime()),
								BaseConstant.CardType.WorkDay.getTypeValue(),
								rule.getStartDay(),
								rule.getEndDay());
				/* 2019/01/18 end */
			}else if(type == BaseConstant.CardType.WorkTime.getTypeValue()) {  //工作时-的月卡用户
				/* 2019/01/18 start */
				//查询出场规则
				QParkingRule ruleEntity =  QParkingRule.parkingRule;
				QParkingRuleRelation relationEntity = QParkingRuleRelation.parkingRuleRelation;
				ParkingRule rule = this.jpaQueryFactory.select(ruleEntity).from(ruleEntity, relationEntity)
						.where(ruleEntity.id.eq(relationEntity.parkingRuleId)
								.and(relationEntity.parkingId.eq(parkingInfo.getId())
										.and(ruleEntity.ruleType.eq(BaseConstant.RoadMarker.OUT.getTypeValue())
												.and(ruleEntity.inRule.eq(BaseConstant.CardType.WorkDay.getTypeValue()))))).fetchFirst();
				Card card = cardRepository.findCardByLisencePlateAndParkingId(parkingInfo.getLicensePlate(), parkingId);
				feeServiceKit = new FeeServiceKit(
						feeModel,
						DateUtil.getDateTimeStr(parkingInfo.getParkDate()),
						parkingDf.format(parkingInfo.getOutDate()),
						DateUtil.getDateTimeStr(card.getExpiryTime()),
						BaseConstant.CardType.WorkTime.getTypeValue(),
						rule.getStartDay(),
						rule.getEndDay(),
						rule.getStartTime(),
						rule.getStartTime());
				/* 2019/01/18 end */
			}
			
			
			Map<String,Object> feeAndParkingTime =  ParkingFeeUtil.getParkingFee(time, 
					freeStandard.getHourCost(), freeStandard.getMostCost(), freeStandard.getFreeTimeLength());
			
			//返回结果
			Map<String, Object> returnObj = new HashMap<String, Object>();
			BigDecimal bigDecimalHour = new BigDecimal(minusHour);
			BigDecimal totalFee = (ParkingFeeUtil.getNewParkingFee(parkingInfo.getOutDate(), parkingInfo, freeStandard)).subtract(bigDecimalHour.multiply(freeStandard.getHourCost()));
			if (totalFee.compareTo(BigDecimal.ZERO) == -1) {
				totalFee = BigDecimal.ZERO;
			}
			returnObj.put("outDate", parkingDf.format(parkingInfo.getOutDate()));
			returnObj.put("totalFee", totalFee);
			Integer parkingLen = (int)(time/1000)/60;
			returnObj.put("parkingLen", parkingLen);
			returnObj.put("showParkingLen", feeAndParkingTime.get("timeLen"));
			returnObj.put("licensePlate", parkingInfo.getLicensePlate());
			returnObj.put("parkingInfoId", parkingInfo.getId());
			returnObj.put("freeTime", freeStandard.getFreeTimeLength());
			
			/* 2019/01/18 start */
			if(feeServiceKit != null ){
				returnObj.put("totalFee",feeServiceKit.getResultFee());
				FeeServiceDeduction.disableDeduction(jpaQueryFactory,parkingInfo.getUserId(),deduction);
			}
			/* 2019/01/18 end */
			
			Order orderAdvanceUnPay = orderRepository.findOrderByinfoIsAdvanceUnPay(parkingInfo.getId());
			if (orderAdvanceUnPay == null) {
				if(parkingLen <= freeStandard.getFreeTimeLength()) {
					returnObj.put("showfree", true);  //免费时长内
				}else {
					returnObj.put("showfree", false);
					Order order = new Order();//保存订单
					order.setParkingInfoId(parkingInfo.getId());
					order.setStatus(BaseConstant.OrderStatus.AdvanceUnPay.getTypeValue());
					order.setFinalFee(totalFee);
					order.setOutTradeNo(PayUtil.createOutTradeNo());
					order.setType(BaseConstant.OrderType.AliPay.getTypeValue());
					order.setUserId(parkingInfo.getUserId());
					if(feeServiceKit != null ) {
						order.setFinalFee(new BigDecimal(feeServiceKit.getResultFee()));
					}
					if (deduction !=null) {
						order.setDeductionId(deduction.getId());
					}
					order = orderRepository.save(order);
				}
			}/*else {
				return RetKit.ok("该车还没完成支付");
			}*/
			
			return RetKit.okData(returnObj);
		} catch (Exception e) {
			
		}
		return RetKit.ok();
	}
	public String findCompanyNameByParkingId(String parkingId) {
		Company company = null;
		try {
			
			Parking parkingDetails = parkingRepository.findParkingById(parkingId);
			//获取公司信息
			company = companyRepository.findCompanyById(parkingDetails.getCompanyId());
		} catch (Exception e) {
			// TODO: handle exception
		}
		 
		return company.getName();
	}
}
