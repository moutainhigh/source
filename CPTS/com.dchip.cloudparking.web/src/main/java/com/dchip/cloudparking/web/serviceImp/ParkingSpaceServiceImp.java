 package com.dchip.cloudparking.web.serviceImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.constant.SocketConstant;
import com.dchip.cloudparking.web.iRepository.IFreeStandardRepository;
import com.dchip.cloudparking.web.iRepository.IMainControlRepository;
import com.dchip.cloudparking.web.iRepository.IParkingRepository;
import com.dchip.cloudparking.web.iRepository.IParkingRuleRelationRepository;
import com.dchip.cloudparking.web.iRepository.IParkingRuleRepository;
import com.dchip.cloudparking.web.iRepository.IStockRepository;
import com.dchip.cloudparking.web.iService.IParkingSpaceService;
import com.dchip.cloudparking.web.model.po.Account;
import com.dchip.cloudparking.web.model.po.FreeStandard;
import com.dchip.cloudparking.web.model.po.MainControl;
import com.dchip.cloudparking.web.model.po.Parking;
import com.dchip.cloudparking.web.model.po.ParkingRule;
import com.dchip.cloudparking.web.model.po.ParkingRuleRelation;
import com.dchip.cloudparking.web.model.po.Stock;
import com.dchip.cloudparking.web.model.vo.ParkingVo;
import com.dchip.cloudparking.web.model.vo.SettingRuleVo;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.po.qpo.QAccount;
import com.dchip.cloudparking.web.po.qpo.QCompany;
import com.dchip.cloudparking.web.po.qpo.QFreeStandard;
import com.dchip.cloudparking.web.po.qpo.QMainControl;
import com.dchip.cloudparking.web.po.qpo.QParking;
import com.dchip.cloudparking.web.po.qpo.QParkingUser;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.SocketKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.dchip.cloudparking.web.utils.StrKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class ParkingSpaceServiceImp extends BaseService implements IParkingSpaceService {
	
	@Autowired
	private IParkingRepository parkingRepository;
	@Autowired
	private IMainControlRepository mainControlRepository;
	@Autowired
	private IFreeStandardRepository freeStandardRepository;
	@Autowired
	private IStockRepository stockRepository;
	@Autowired
	private IParkingRuleRepository parkingRuleRepository;
	@Autowired
	private IParkingRuleRelationRepository parkingRuleRelationRepository;

	@Override
	public Object getParkingSpaceList(Integer pageSize,Integer pageNum,String sortName,String direction,List<Map<String, Object>> para) {
	
		UserAuthentic loginUser = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<Map<String, Object>> data = new ArrayList<>();	
		QParking qParking = QParking.parking;//停车场
		QCompany qCompany = QCompany.company;//公司
		QMainControl qMainControl = QMainControl.mainControl;//主控板
		QFreeStandard qFreeStandard = QFreeStandard.freeStandard;//收费标准
		/**
		 * 查询条件的谓语集合(where条件)
		 */
		List<Predicate> predicates = new ArrayList<>();	
		
		if(!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				//搜索参数
				if(map.get("searchParking")!=null) {
					//停车场名称
					predicates.add(qParking.parkName.like("%"+map.get("searchParking").toString()+"%"));
				}else if(map.get("searchCompany")!=null) {
					//联系人
					Predicate predicate = qCompany.name.like("%"+map.get("searchCompany").toString()+"%");
					predicates.add(predicate);
				}
			}
		}
		
		//停车场管理员只能查看当前公司下的停车场
		if(loginUser.getRoleType() == BaseConstant.SysRoleType.companyType.getTypeValue()) { //公司管理员
			predicates.add(qCompany.id.eq(loginUser.getCompanyId()));
		}else if (loginUser.getRoleType() == BaseConstant.SysRoleType.parkingType.getTypeValue()) {//停车场管理员
			QParkingUser qParkingUser = QParkingUser.parkingUser;
			Integer parkingId = this.jpaQueryFactory.select(qParkingUser.parkingId)
					.from(qParkingUser)
					.where(qParkingUser.userName.eq(loginUser.getUserName())).fetchFirst();
			if (parkingId != null) {
				predicates.add(qParking.id.eq(parkingId));
			}
		}

		/**
		 * 排除软删
		 */
		predicates.add(qParking.status.notIn(Integer.toString(BaseConstant.SoftDelete.delete.getTypeValue())));

		//动态排序,需要使用自定义的Sort类
		Sort sort = null;
		switch (sortName) {
		case "id":		
			sort = new Sort("id",direction.toString(),qParking);
			break;
		case "companyName":		
			sort = new Sort("name",direction.toString(),qCompany);
			break;
		default:
			sort = new Sort(sortName,direction,qParking);
			break;
		}
		
		
		//查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qParking.id,qParking.parkCode,qParking.parkName,qParking.provinceName,qParking.cityName,qParking.areaName,
						qParking.location,qParking.longitude,qParking.latitude,qParking.status,qParking.freeStandardId,
						qParking.companyId,qParking.comcatName,qParking.comcatPhone,qParking.managerName,qParking.managerPhone,
						qParking.calculateDay,qParking.createTime,qParking.spaceCount,qParking.monthFree,qParking.max,
						qParking.isSupportCard,qParking.freeTimePayFlag,qCompany.id,qCompany.name,qFreeStandard.id,qFreeStandard.freeTimeLength,
						qFreeStandard.hourCost,qFreeStandard.mostCost,qMainControl.id,qMainControl.mac)
				.from(qParking)
				.leftJoin(qCompany).on(qCompany.id.eq(qParking.companyId))
				.leftJoin(qMainControl).on(qMainControl.parkingId.eq(qParking.id))
				.leftJoin(qFreeStandard).on(qFreeStandard.id.eq(qParking.freeStandardId))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.orderBy(sort.getOrderSpecifier())
				.limit(pageSize).offset(pageNum*pageSize);
			
		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		//将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("companyId", tuple.get(qCompany.id));
			map.put("companyName", tuple.get(qCompany.name));
			
			map.put("id", tuple.get(qParking.id));
			map.put("parkCode", tuple.get(qParking.parkCode));
			map.put("parkName", tuple.get(qParking.parkName));
			map.put("provinceName", tuple.get(qParking.provinceName));
			map.put("cityName", tuple.get(qParking.cityName));
			map.put("areaName", tuple.get(qParking.areaName));
			map.put("location", tuple.get(qParking.location));
			map.put("longitude", tuple.get(qParking.longitude));
			map.put("latitude", tuple.get(qParking.latitude));
			map.put("status", tuple.get(qParking.status));
			map.put("freeStandardId", tuple.get(qParking.freeStandardId));
			map.put("locations", tuple.get(qParking.provinceName)+tuple.get(qParking.cityName)+tuple.get(qParking.areaName)+tuple.get(qParking.location));
			map.put("comcatName", tuple.get(qParking.comcatName));
			map.put("comcatPhone", tuple.get(qParking.comcatPhone));
			map.put("managerName", tuple.get(qParking.managerName));
			map.put("managerPhone", tuple.get(qParking.managerPhone));
			map.put("calculateDay", tuple.get(qParking.calculateDay));
			map.put("createTime", tuple.get(qParking.createTime));
			map.put("spaceCount", tuple.get(qParking.spaceCount));
			map.put("monthFree", tuple.get(qParking.monthFree));
			map.put("max", tuple.get(qParking.max));
			map.put("isSupportCard", tuple.get(qParking.isSupportCard));
			map.put("freeTimeStatus", tuple.get(qParking.freeTimePayFlag));
			map.put("freeStandardId", tuple.get(qFreeStandard.id));
			map.put("freetime", tuple.get(qFreeStandard.freeTimeLength));
			map.put("hourCost", tuple.get(qFreeStandard.hourCost));
			map.put("mostCost", tuple.get(qFreeStandard.mostCost));
			
			map.put("mainControlId", tuple.get(qMainControl.id));
			map.put("controlMac", tuple.get(qMainControl.mac));
			data.add(map);
		}
		
		//jqgrid数据拼装
		Map<String, Object> result = new HashMap<>();
		result.put("content", data);//添加主体数据
		result.put("totalElements", queryResults.getTotal());//添加总条数
		result.put("code", 0);
        return result;
	}

	@Override
	public RetKit save(ParkingVo parkingVo) {
			Parking parking = new Parking();
			FreeStandard freeStandard = new FreeStandard();
			Stock stock = new Stock();
		try {
			if (parkingVo.getParkingId() != 0) {//编辑停车场
				Optional<Parking> parkingOptional = parkingRepository.findById(parkingVo.getParkingId());
				if (parkingOptional.isPresent()) {
					parking = parkingOptional.get();
				}
				Optional<FreeStandard> freeStandardOptional = freeStandardRepository.findById(parkingVo.getFreeStandardId());
				if (freeStandardOptional.isPresent()) {
					freeStandard = freeStandardOptional.get();
				}
			}else {//添加停车场
				freeStandard = freeStandardRepository.save(freeStandard);//添加停车场的同时添加收费标准
				parking.setCreateTime(new Date());
				parking.setFreeStandardId(freeStandard.getId());
				parking.setParkCode(0);
				parking.setStatus("0");
				parking.setMax(1);
				parking.setIsSupportCard(0);
			
			}
			parking.setFreeTimePayFlag(parkingVo.getFreeTimePayFlag());
			parking.setCompanyId(parkingVo.getCompanyId());
			parking.setParkName(parkingVo.getParkSpaceName());
			parking.setProvinceName(parkingVo.getProvinceName());
			parking.setCityName(parkingVo.getCityName());
			parking.setAreaName(parkingVo.getAreaName());
			parking.setLocation(parkingVo.getLocation());
			parking.setLongitude(parkingVo.getLongitude());
			parking.setLatitude(parkingVo.getLatitude());
			parking.setComcatName(parkingVo.getComcatName());
			parking.setComcatPhone(parkingVo.getComcatPhone());
			parking.setManagerName(parkingVo.getManagerName());
			parking.setManagerPhone(parkingVo.getManagerPhone());
			parking.setCalculateDay(parkingVo.getCalculateDay());
			parking.setSpaceCount(parkingVo.getSpaceCount());
			parking.setMonthFree(parkingVo.getMonthFree());
			parking = parkingRepository.save(parking);
			//停车场停车位情况
			if (parkingVo.getParkingId() == 0) {
				String timestampStr = String.valueOf((System.currentTimeMillis() / 1000)); //暂时以这个时间戳为停车场唯一编码
				Integer timestampInt = Integer.parseInt(timestampStr);
				timestampInt = parking.getId() + timestampInt;
				parking.setParkCode(timestampInt);
				parkingRepository.save(parking);
				
				stock.setParkingId(parking.getId());
				stock.setEnterCount(0);
				stock.setCreateTime(new Date());
				
				//新增停车场时的默认停车场出入规则设置
				ParkingRule parkingRule = new ParkingRule();
				parkingRule.setInRule(1);
				parkingRule.setRuleType(2);
				parkingRule = parkingRuleRepository.save(parkingRule);
				
				ParkingRule parkingRule2 = new ParkingRule();
				parkingRule2.setInRule(2);
				parkingRule2.setRuleType(1);
				parkingRule2 = parkingRuleRepository.save(parkingRule2);
				
				ParkingRuleRelation parkingRuleRelation = new ParkingRuleRelation();
				parkingRuleRelation.setParkingId(parking.getId());
				parkingRuleRelation.setParkingRuleId(parkingRule.getId());
				parkingRuleRelationRepository.save(parkingRuleRelation);
				
				ParkingRuleRelation parkingRuleRelation2 = new ParkingRuleRelation();
				parkingRuleRelation2.setParkingId(parking.getId());
				parkingRuleRelation2.setParkingRuleId(parkingRule2.getId());
				parkingRuleRelationRepository.save(parkingRuleRelation2);
			}
			if (parkingVo.getParkingId() != 0) {//编辑停车场
				Integer count = stockRepository.findByParkingId(parkingVo.getParkingId());
				if (count == 0) {
					stock.setParkingId(parking.getId());
					stock.setEnterCount(0);
					stock.setCreateTime(new Date());
				}else {
					stock = stockRepository.findAllByParkingId(parkingVo.getParkingId());
				}
			}
			stock.setTotalCount(parking.getSpaceCount());
			stock.setUpdateTime(new Date());
			stockRepository.save(stock);
			//收费标准
			freeStandard.setFreeTimeLength(parkingVo.getFreetime());
			freeStandard.setHourCost(parkingVo.getHourCost());
			freeStandard.setMostCost(parkingVo.getMostCost());
			freeStandardRepository.save(freeStandard);
			
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail("保存失败");
		}
	}

	@Override
	public RetKit delete(Integer parkingId) {
			Integer maincontrol = mainControlRepository.findMainControlByParkingId(parkingId);
			if (maincontrol !=null && maincontrol>0) {
				return RetKit.fail("删除失败，请先解绑主控板");
			}else {
				Parking parking = parkingRepository.findById(parkingId).get();
				Stock stock = stockRepository.findAllByParkingId(parkingId);
				if(stock != null) {
					stockRepository.delete(stock);
				}
				freeStandardRepository.deleteById(parking.getFreeStandardId());
				parkingRepository.delete(parking);
				return RetKit.ok("删除成功");
			}
	}
	
	/**
	 * 软删
	 * @param parkingId
	 * @return
	 */
	@Override
	public RetKit softlyDelete(Integer parkingId) {
		Integer maincontrol = mainControlRepository.findMainControlByParkingId(parkingId);
		if (maincontrol !=null && maincontrol>0) {
			return RetKit.fail("删除失败，请先解绑主控板");
		}else {
			Parking parking = parkingRepository.findById(parkingId).get();
			Stock stock = stockRepository.findAllByParkingId(parkingId);
			if(stock != null) {
				stockRepository.delete(stock);
			}
//			freeStandardRepository.deleteById(parking.getFreeStandardId());
			parkingRepository.softlyDelete(BaseConstant.SoftDelete.delete.getTypeValue(),parkingId);
			return RetKit.ok("删除成功");
		}
	}
	
	@Override
	public RetKit changeStatus(ParkingVo parkingVo) {
		Integer parkingId = parkingVo.getParkingId();
		String status = parkingVo.getStatus();
		if (parkingId != null) {
			Optional<Parking> parkingOptional = parkingRepository.findById(parkingId);
			if (parkingOptional.isPresent()) {
				Parking parking = parkingOptional.get();
				parking.setStatus(status);
				parkingRepository.save(parking);
			}
			List<MainControl> mainControlList = mainControlRepository.findMainControlListByParkingId(parkingId);
			//通知硬件更新绑定情况
			try {
				if(mainControlList.size() != 0) {//主控板不为0，遍历出来然后发送消息
					for(MainControl mainControl : mainControlList) {
						SocketKit.sendMessage(mainControl.getMac(), "cameraMac", SocketConstant.CommandType.BindingMainControl.getValue());
					}
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return RetKit.fail();
			} catch (IOException e) {
				e.printStackTrace();
				return RetKit.fail();
			}
			return RetKit.ok();
		}else {
			return RetKit.fail("停车场ID为空,操作失败");
		}
	}
	
	/**
	 * 获取停车场规则数据
	 */
	@Override
	public RetKit getSettingRuleData(String parkingId) {
		if (StrKit.isBlank(parkingId)) {
			return RetKit.fail("parkingId为空！");
		}
		List<ParkingRule> data = parkingRuleRepository.findParkingRuleDataByPId(Integer.parseInt(parkingId));
		if (!data.isEmpty()) {
			return RetKit.okData(data);
		}else {
			return RetKit.okData(data);
		}
	}
	
	/**
	 * 保存停车场规则设置的数据
	 */
	@Override
	public RetKit saveSettingRuleData(SettingRuleVo settingRuleVo) {//新建停车场的时候已默认新建了出入场规则
		//入场规则()
		if (settingRuleVo.getInRuleId() == null) {//如果入场规则ID为空，则新建一个入场规则
			ParkingRule parkingRule = new ParkingRule();
			parkingRule.setInRule(settingRuleVo.getInrule());
			parkingRule.setRuleType(BaseConstant.parkingRule.InRule.getTypeValue());
			parkingRule = parkingRuleRepository.save(parkingRule);
			//新建规则绑定表数据
			ParkingRuleRelation parkingRuleRelation = new ParkingRuleRelation();
			parkingRuleRelation.setParkingId(settingRuleVo.getParkingId());
			parkingRuleRelation.setParkingRuleId(parkingRule.getId());
			parkingRuleRelationRepository.save(parkingRuleRelation);
		}else {//如果入场规则ID不为空，则修改原来的入场规则
			Optional<ParkingRule> parkingRuleOptional = parkingRuleRepository.findById(settingRuleVo.getInRuleId());
			if (parkingRuleOptional.isPresent()) {//该入场规则存在
				ParkingRule parkingRule = parkingRuleOptional.get();
				parkingRule.setRuleType(BaseConstant.parkingRule.InRule.getTypeValue());
				parkingRule.setInRule(settingRuleVo.getInrule());
				parkingRuleRepository.save(parkingRule);
			}else {//该入场规则数据丢失，新建一个新的入场规则
				ParkingRule parkingRule = new ParkingRule();
				parkingRule.setInRule(settingRuleVo.getInrule());
				parkingRule.setRuleType(BaseConstant.parkingRule.InRule.getTypeValue());
				parkingRule = parkingRuleRepository.save(parkingRule);
				//新建规则绑定表数据
				ParkingRuleRelation parkingRuleRelation = new ParkingRuleRelation();
				parkingRuleRelation.setParkingId(settingRuleVo.getParkingId());
				parkingRuleRelation.setParkingRuleId(parkingRule.getId());
				parkingRuleRelationRepository.save(parkingRuleRelation);
			}
		}
		//出场规则()
		if (settingRuleVo.getOutRuleId() == null) {//如果出场规则ID为空，则新建一个出场规则
			ParkingRule parkingRule2 = new ParkingRule();
			parkingRule2.setInRule(settingRuleVo.getOutrule());
			parkingRule2.setStartDay(settingRuleVo.getStartDay());
			parkingRule2.setEndDay(settingRuleVo.getEndDay());
			parkingRule2.setStartTime(settingRuleVo.getStartTime());
			parkingRule2.setEndTime(settingRuleVo.getEndTime());
			parkingRule2.setRuleType(BaseConstant.parkingRule.OutRule.getTypeValue());
			parkingRule2 = parkingRuleRepository.save(parkingRule2);
			//新建与出场规则关联的规则绑定表数据
			ParkingRuleRelation parkingRuleRelation2 = new ParkingRuleRelation();
			parkingRuleRelation2.setParkingId(settingRuleVo.getParkingId());
			parkingRuleRelation2.setParkingRuleId(parkingRule2.getId());
			parkingRuleRelationRepository.save(parkingRuleRelation2);
		}else {//出场规则数据存在
			Optional<ParkingRule> parkingRuleOptional2 = parkingRuleRepository.findById(settingRuleVo.getOutRuleId());
			if (parkingRuleOptional2.isPresent()) {
				ParkingRule parkingRule2 = parkingRuleOptional2.get();
				parkingRule2.setRuleType(BaseConstant.parkingRule.OutRule.getTypeValue());
				parkingRule2.setInRule(settingRuleVo.getOutrule());
				if(settingRuleVo.getOutrule() == BaseConstant.CardType.Month.getTypeValue()) {
					parkingRule2.setStartDay(null);
					parkingRule2.setEndDay(null);
					parkingRule2.setStartTime(null);
					parkingRule2.setEndTime(null);
				}else if(settingRuleVo.getOutrule() == BaseConstant.CardType.WorkDay.getTypeValue()) {
					parkingRule2.setStartDay(settingRuleVo.getStartDay());
					parkingRule2.setEndDay(settingRuleVo.getEndDay());
					parkingRule2.setStartTime(null);
					parkingRule2.setEndTime(null);
				}else {
					parkingRule2.setStartDay(settingRuleVo.getStartDay());
					parkingRule2.setEndDay(settingRuleVo.getEndDay());
					parkingRule2.setStartTime(settingRuleVo.getStartTime());
					parkingRule2.setEndTime(settingRuleVo.getEndTime());
					parkingRuleRepository.save(parkingRule2);
				}
				
			}else {//出场规则数据丢失，新建一个新的出场规则
				ParkingRule parkingRule2 = new ParkingRule();
				parkingRule2.setInRule(settingRuleVo.getOutrule());
				parkingRule2.setStartDay(settingRuleVo.getStartDay());
				parkingRule2.setEndDay(settingRuleVo.getEndDay());
				parkingRule2.setStartTime(settingRuleVo.getStartTime());
				parkingRule2.setEndTime(settingRuleVo.getEndTime());
				parkingRule2.setRuleType(BaseConstant.parkingRule.OutRule.getTypeValue());
				parkingRule2 = parkingRuleRepository.save(parkingRule2);
				//新建与出场规则关联的规则绑定表数据
				ParkingRuleRelation parkingRuleRelation2 = new ParkingRuleRelation();
				parkingRuleRelation2.setParkingId(settingRuleVo.getParkingId());
				parkingRuleRelation2.setParkingRuleId(parkingRule2.getId());
				parkingRuleRelationRepository.save(parkingRuleRelation2);
			}
		}
		if (settingRuleVo.getParkingId() != null) {
			Optional<Parking> parkingOptional = parkingRepository.findById(settingRuleVo.getParkingId());
			if (parkingOptional.isPresent()) {//数据正常，则修改停车场的数据
				Parking parking = parkingOptional.get();
				parking.setIsSupportCard(settingRuleVo.getIsSupportCard());//是否只支持月卡
				parking.setMax(settingRuleVo.getMax());//同组车支持最大数
				parkingRepository.save(parking);
			}
		}else {
			return RetKit.fail("停车场ID为空！");
		}
		return RetKit.ok();
	}

	@Override
	public RetKit checkRepeat(String companyId, String parkSpaceName) {
		Integer count = parkingRepository.checkRepeat(companyId, parkSpaceName);
		if (count>0) {
			return RetKit.fail("同公司不能有重复的停车场");
		}else {
			return RetKit.ok();
		}
	}

	@Override
	public RetKit getManagerByCompanyId(String companyId) {
		QAccount qAccount = QAccount.account;
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(qAccount.companyId.eq(Integer.parseInt(companyId)));

		List<Account> accounts = jpaQueryFactory
				.select(qAccount)
				.from(qAccount)
				.where(predicates.toArray(new Predicate[predicates.size()])).fetch();
		return RetKit.okData(accounts);
	}

	@Override
	public RetKit openGate(String controlMac, String cameraMac) {
		//通知硬件开闸
		try {
			RetKit rs = SocketKit.sendMessage(controlMac, cameraMac, SocketConstant.CommandType.OpenGate.getValue());
			if (!rs.success()) {
				return RetKit.fail("设备不在线,发送请求失败");
			}else {
				return RetKit.ok("开闸成功");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return RetKit.fail();
		} catch (IOException e) {
			e.printStackTrace();
			return RetKit.fail();
		}
	}

}
