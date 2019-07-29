package com.dchip.cloudparking.web.serviceImp;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.constant.SocketConstant;
import com.dchip.cloudparking.web.iRepository.IMainControlRepository;
import com.dchip.cloudparking.web.iRepository.IMainRoadwayRepository;
import com.dchip.cloudparking.web.iRepository.IRoadwayRepository;
import com.dchip.cloudparking.web.iService.IEquipmentService;
import com.dchip.cloudparking.web.model.po.MainControl;
import com.dchip.cloudparking.web.model.po.MainRoadway;
import com.dchip.cloudparking.web.model.po.Roadway;
import com.dchip.cloudparking.web.model.vo.RoadwayVo;
import com.dchip.cloudparking.web.po.qpo.QMainControl;
import com.dchip.cloudparking.web.po.qpo.QMainRoadway;
import com.dchip.cloudparking.web.po.qpo.QParking;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.SocketKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class EquipmentServiceImp extends BaseService implements IEquipmentService{
	
	@Autowired
	private IMainControlRepository mainControlRepository;
	@Autowired
	private IMainRoadwayRepository mainRoadwayRepository;
	@Autowired
	private IRoadwayRepository roadwayRepository;
	@Autowired
	private StringRedisTemplate stringTemplate;

	@Override
	public Map<String, Object> getEquipmentList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para) {
		
		List<Map<String, Object>> data = new ArrayList<>();	
		QParking qParking = QParking.parking;//停车场
		QMainControl qMainControl = QMainControl.mainControl;//设备
		QMainRoadway qMainRoadway = QMainRoadway.mainRoadway;//主控板和车道绑定表
		/**
		 * 查询条件的谓语集合(where条件)
		 */
		List<Predicate> predicates = new ArrayList<>();	
		predicates.add(qMainControl.status.gt(BaseConstant.SoftDelete.delete.getTypeValue()));
		if(!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				//搜索参数
				if(map.get("searchMAC")!=null) {
					//停车场名称
					predicates.add(qMainControl.mac.like("%"+map.get("searchMAC").toString()+"%"));
				}
			}
		}
		
		//动态排序,需要使用自定义的Sort类
		Sort sort = null;
		switch (sortName) {
		case "createTime":		
			sort = new Sort(sortName,direction.toString(),qMainControl);
			break;
		default:
			sort = new Sort(sortName,direction,qParking);
			break;
		}
				
		//查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qMainControl.id,qMainControl.mac,qMainControl.name,qMainControl.status,qMainControl.actTo,
						qMainControl.createTime,qParking.parkName,qParking.id,qParking.provinceName,qParking.cityName,
						qParking.areaName,qParking.location,JPAExpressions.select(qMainRoadway.mainId.count()).from(qMainRoadway).where(qMainControl.id.eq(qMainRoadway.mainId)))
				.from(qMainControl)
				.leftJoin(qParking).on(qParking.id.eq(qMainControl.parkingId))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.orderBy(sort.getOrderSpecifier())
				.offset(pageNum*pageSize).limit(pageSize);
		
		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		//将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", tuple.get(qMainControl.id));
			map.put("mac", tuple.get(qMainControl.mac));
			if(stringTemplate.opsForHash().get(
					BaseConstant.DeviceOnlineSituation.DeviceOnline.getValue().toString(),
					tuple.get(qMainControl.mac)) != null) {
				map.put("online", true);
			}else if(stringTemplate.opsForHash().get(
					BaseConstant.DeviceOnlineSituation.DeviceOffline.getValue().toString(),
					tuple.get(qMainControl.mac)) != null) {
				map.put("online", false);
			}
			map.put("actTo", tuple.get(qMainControl.actTo));
			map.put("macName", tuple.get(qMainControl.name));
			map.put("status", tuple.get(qMainControl.status));
			map.put("createTime", tuple.get(qMainControl.createTime));
			map.put("parkingId", tuple.get(qParking.id));
			map.put("parkName", tuple.get(qParking.parkName));
			map.put("location", tuple.get(qParking.provinceName)+tuple.get(qParking.cityName)
					+tuple.get(qParking.areaName)+tuple.get(qParking.location));
			map.put("roadWayNum", tuple.get(JPAExpressions.select(qMainRoadway.mainId.count()).from(qMainRoadway).where(qMainControl.id.eq(qMainRoadway.mainId))));
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
	public RetKit delete(Integer mainControlId) {
		if (mainControlId == null) {
			return RetKit.fail("主控板ID为空，删除失败");
		}
		Optional<MainControl> mainControlOptional = mainControlRepository.findById(mainControlId);
		if (mainControlOptional.isPresent()) {
			MainControl mainControl = mainControlOptional.get();
			mainControl.setStatus(BaseConstant.SoftDelete.delete.getTypeValue());
			mainControlRepository.save(mainControl);
			return RetKit.ok("删除成功");
		}
		return RetKit.fail("删除失败");
	}


	@Override
	public RetKit getRoadways(String mainControlId) {
		return RetKit.okData(roadwayRepository.findRoadways(mainControlId));
	}

	@Override
	public RetKit binding(String mainControlId, List<Object> roadwaysData, Integer parkingId, String actionType) {
		QMainControl qmainControl = QMainControl.mainControl;
		MainControl mainControl = jpaQueryFactory.select(qmainControl).from(qmainControl).where(qmainControl.id.eq(Integer.parseInt(mainControlId))).fetchFirst();
		if(mainControl==null) {
			return RetKit.fail("找不到主控板");
		}
		mainControl.setParkingId(parkingId);
		mainControl.setActTo(actionType);
		mainControl = mainControlRepository.save(mainControl);
		for (Object object : roadwaysData) {
			RoadwayVo vo = JSON.toJavaObject(JSON.parseObject(object.toString()), RoadwayVo.class);
			String status = vo.getStatus();
			if(status.equals("add") || status.equals("update")) {
				Roadway roadway = new Roadway();;
				if(status.equals("update")) {
					Optional<Roadway> optionalRoadway = roadwayRepository.findById(vo.getId());				
					if(!optionalRoadway.isPresent()) {
						break;
					}
					roadway = optionalRoadway.get();
				}		
				roadway.setParkingId(parkingId);
				roadway.setRoadName(vo.getRoadName());
				roadway.setGateNumber(vo.getGateNumber());
				roadway.setAddress(vo.getAddress());
				roadway.setCameraIp(vo.getCameraIp());
				roadway.setCameraMac(vo.getCameraMac());
				roadway.setInOutMarker(vo.getInOutMarker());
				roadway.setCameraType(vo.getCameraType());
				roadway = roadwayRepository.save(roadway);
				if(status.equals("add")) {
					MainRoadway mainRoadway = new MainRoadway();
					mainRoadway.setCreateTime(new Date());
					mainRoadway.setMainId(Integer.parseInt(mainControlId));
					mainRoadway.setRoadwayId(roadway.getId());
					mainRoadwayRepository.save(mainRoadway);
				} 
			}else if(status.equals("delete")) {
				MainRoadway mainRoadway = mainRoadwayRepository.findByRoadwayId(vo.getId());
				mainRoadwayRepository.delete(mainRoadway);
			}
		}//for
		
		//通知硬件更新绑定情况
		try {
			SocketKit.sendMessage(mainControl.getMac(), "cameraMac", SocketConstant.CommandType.BindingMainControl.getValue());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return RetKit.fail();
		} catch (IOException e) {
			e.printStackTrace();
			return RetKit.fail();
		}
		return RetKit.ok();
	}
	
	@Override
	public RetKit checkCameraMac(String cameraMac) {
		Integer count = roadwayRepository.checkCameraMac(cameraMac);
		if (count>0) {
			return RetKit.fail("摄像机mac已存在");
		}else {
			return RetKit.ok();
		}
	}

	@Override
	public RetKit getEntryRoadways(String mainControlId) {
		List<Roadway> EntryRoadways = roadwayRepository.findEntryRoadways(mainControlId);
		return RetKit.okData(EntryRoadways);
	}

	@Override
	public RetKit getExitRoadways(String mainControlId) {
		List<Roadway> ExitRoadways = roadwayRepository.findExitRoadways(mainControlId);
		return RetKit.okData(ExitRoadways);
	}

}
