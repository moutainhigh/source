package com.dchip.cloudparking.web.serviceImp;

import java.util.*;

import com.dchip.cloudparking.web.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.iRepository.ICloneCarRepository;
import com.dchip.cloudparking.web.iRepository.IParkingInfoRepository;
import com.dchip.cloudparking.web.iService.ICloneCarService;
import com.dchip.cloudparking.web.po.qpo.QCloneCar;
import com.dchip.cloudparking.web.po.qpo.QOrder;
import com.dchip.cloudparking.web.po.qpo.QParking;
import com.dchip.cloudparking.web.po.qpo.QParkingInfo;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.dchip.cloudparking.web.utils.StrKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class CloneCarServiceImp extends BaseService implements ICloneCarService{
	@Autowired
	public ICloneCarRepository cloneCarRepository;
	@Autowired
	public IParkingInfoRepository parkingInfoRepository;

	@Override
	public Object getCloneCarList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para) {
		
		List<Map<String, Object>> data = new ArrayList<>();	
		QParking qParking = QParking.parking;//停车场
		QCloneCar qCloneCar = QCloneCar.cloneCar;//套牌车记录
		QParkingInfo qParkingInfo = QParkingInfo.parkingInfo;//停车信息
		/**
		 * 查询条件的谓语集合(where条件)
		 */
		List<Predicate> predicates = new ArrayList<>();	
		if(!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				//搜索参数
				if(map.get("searchLicensePlate")!=null) {
					//停车场名称
					predicates.add(qParkingInfo.licensePlate.like("%"+map.get("searchLicensePlate").toString()+"%"));
				}
				if(map.get("searchStartTime")!=null && map.get("searchEndTime")!=null) {
					//记录时间范围
					predicates.add(qCloneCar.createTime.between(DateUtil.dateToStamp(map.get("searchStartTime").toString(),"yyyy-MM-dd HH:mm:ss"),DateUtil.dateToStamp(map.get("searchEndTime").toString(),"yyyy-MM-dd HH:mm:ss")));
				}
				if(map.get("searchStatus")!=null) {
					//状态
					predicates.add(qCloneCar.status.eq(Integer.parseInt(map.get("searchStatus").toString())));
				}
			}
		}
		//动态排序,需要使用自定义的Sort类
		Sort sort = null;
		switch (sortName) {
		case "licensePlate":		
			sort = new Sort("licensePlate",direction.toString(),qParkingInfo);
			break;
		default:
			sort = new Sort(sortName,direction,qCloneCar);
			break;
		}		
				
		//查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qParking.parkCode,qParking.parkName,qParkingInfo.licensePlate,qParkingInfo.parkDate
						,qParkingInfo.userId,qParkingInfo.outDate,qParkingInfo.status,qCloneCar.id
						,qCloneCar.parkingInfoId,qCloneCar.createTime,qCloneCar.status)
				.from(qCloneCar)
				.leftJoin(qParkingInfo).on(qParkingInfo.id.eq(qCloneCar.parkingInfoId))
				.leftJoin(qParking).on(qParking.parkCode.eq(qParkingInfo.parkCode))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.orderBy(sort.getOrderSpecifier())
				.offset(pageNum*pageSize).limit(pageSize);
		
		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		//将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("parkCode", tuple.get(qParking.parkCode));
			map.put("parkName", tuple.get(qParking.parkName));
			map.put("licensePlate", tuple.get(qParkingInfo.licensePlate));
			map.put("parkDate", tuple.get(qParkingInfo.parkDate));
			map.put("outDate", tuple.get(qParkingInfo.outDate));
			map.put("cloneCarId", tuple.get(qCloneCar.id));
			map.put("createTime", tuple.get(qCloneCar.createTime));
			map.put("status", tuple.get(qCloneCar.status));
			map.put("parkingInfostatus", tuple.get(qParkingInfo.status));
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
	public RetKit delete(Integer cloneCarId) {
		if (cloneCarId == null) {
			return RetKit.fail("删除失败");
		} else {
			cloneCarRepository.deleteById(cloneCarId);
			return RetKit.ok("删除成功");
		}
	}

	@Override
	public RetKit findCloneCarDetailInfo(String licensePlate) {
		if (StrKit.isBlank(licensePlate)) {
			return RetKit.fail("数据丢失");
		}
		List<Map<String, Object>> listData = new ArrayList<>();
		
		QParking qParking = QParking.parking;//停车场
		QParkingInfo qParkingInfo = QParkingInfo.parkingInfo;//停车场信息
		QOrder qOrder = QOrder.order;//订单信息
		
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(qParkingInfo.licensePlate.eq(licensePlate));

		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.selectDistinct( qParking.parkName, qParking.provinceName, qParking.cityName,
						qParking.areaName, qParking.location, qParkingInfo.licensePlate, 
						qParkingInfo.parkDate, qParkingInfo.outDate,qParkingInfo.status, 
						qOrder.fee,qOrder.parkingTime)
				.from( qParkingInfo )
				.leftJoin( qParking ).on( qParking.parkCode.eq(qParkingInfo.parkCode) )
				.leftJoin(qOrder).on(qOrder.parkingInfoId.eq(qParkingInfo.id))
				.where( predicates.toArray(new Predicate[predicates.size()]) )
				.orderBy(qParkingInfo.parkDate.desc())
				.limit(2);
		
		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		// 将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("licensePlate", tuple.get(qParkingInfo.licensePlate));
			map.put("parkName", tuple.get(qParking.parkName));
			map.put("provinceName", tuple.get(qParking.provinceName));
			map.put("cityName", tuple.get(qParking.cityName));
			map.put("areaName", tuple.get(qParking.areaName));
			map.put("location", tuple.get(qParking.location));
			map.put("locations", tuple.get(qParking.provinceName)+tuple.get(qParking.cityName)+tuple.get(qParking.areaName)+tuple.get(qParking.location));
			map.put("parkDate", tuple.get(qParkingInfo.parkDate));
			map.put("outDate", tuple.get(qParkingInfo.outDate));
			map.put("status", tuple.get(qParkingInfo.status));
			map.put("fee", tuple.get(qOrder.fee));
			map.put("parkingTime", tuple.get(qOrder.parkingTime));
			listData.add(map);
		}
		return RetKit.okData(listData);	
	}

}
