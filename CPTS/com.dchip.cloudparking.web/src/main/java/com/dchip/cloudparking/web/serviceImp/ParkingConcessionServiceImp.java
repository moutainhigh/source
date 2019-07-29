package com.dchip.cloudparking.web.serviceImp;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.IParkingConcessionRepository;
import com.dchip.cloudparking.web.iRepository.IUserRepository;
import com.dchip.cloudparking.web.iService.IParkingConcessionService;
import com.dchip.cloudparking.web.po.qpo.QParking;
import com.dchip.cloudparking.web.po.qpo.QParkingConcession;
import com.dchip.cloudparking.web.po.qpo.QUser;
import com.dchip.cloudparking.web.utils.RetKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParkingConcessionServiceImp extends BaseService implements IParkingConcessionService {

	@Autowired
	private IParkingConcessionRepository parkingConcessionrepository;

	@Autowired
	private IUserRepository userRepository;

	@Override
	public Object getParkingConcessionList(Integer pageSize, Integer pageNum , String sortName, String direction,List<Map<String, Object>> para ){
		List<Map<String, Object>> data = new ArrayList<>();
		QParkingConcession qParkingConcession = QParkingConcession.parkingConcession;
		QParking qParking = QParking.parking;
		QUser qUser = QUser.user;
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
				}
				if(map.get("searchLessee")!=null) {
					//出租人姓名
					predicates.add(qUser.carOwnerName.like("%"+map.get("searchLessee").toString()+"%"));
				}
			}
		}

		//查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qParkingConcession.id,
						qParkingConcession.effectDuringTime,
						qParkingConcession.effectDuringDate,
						qParkingConcession.cost,
						qParkingConcession.parkingId,
						qParking.parkName,//停车场名称
						qParking.location,//地点
						qParkingConcession.userId,//发布租赁信息者id
						qUser.phone,//发布租赁信息者 电话
						qUser.carOwnerName,//发布租赁信息者 姓名
						qParkingConcession.tenantId,//承租者id
						qParkingConcession.spaceNo,//车位号
						qParkingConcession.licensePlate,//发布时填写的车牌号
						qParkingConcession.publishTime,//发布时间,时间戳
						qParkingConcession.rentTime,
						qParkingConcession.status)//租赁时间,时间戳
				.from(qParkingConcession)
				.leftJoin(qUser).on(qParkingConcession.userId.eq(qUser.id))
				.leftJoin(qParking).on(qParkingConcession.parkingId.eq(qParking.id))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.orderBy(qParkingConcession.publishTime.desc());

//		List<Integer> tenantIds = new ArrayList<Integer>();
		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		//将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("parkingConcessionId", tuple.get(qParkingConcession.id));
			map.put("effectDuringTime", tuple.get(qParkingConcession.effectDuringTime));
			map.put("effectDuringDate", tuple.get(qParkingConcession.effectDuringDate));
			map.put("cost", tuple.get(qParkingConcession.cost));
			map.put("parkingId", tuple.get(qParkingConcession.parkingId));
			map.put("parkName", tuple.get(qParking.parkName));
			map.put("userId", tuple.get(qParkingConcession.userId));
			map.put("userName", tuple.get(qUser.carOwnerName));
			map.put("phone", tuple.get(qUser.phone));

			Integer tenantId = tuple.get(qParkingConcession.tenantId);//tenantId默认为0，所以不会为空
			map.put("tenantId", tenantId);
//			tenantIds.add(tenantId);

			map.put("spaceNo", tuple.get(qParkingConcession.spaceNo));
			map.put("licensePlate", tuple.get(qParkingConcession.licensePlate));
			map.put("publishTime", tuple.get(qParkingConcession.publishTime));
			map.put("rentTime", tuple.get(qParkingConcession.rentTime));
			map.put("status", tuple.get(qParkingConcession.status));
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
	public RetKit del(String parkingConcessionId) {
		Integer id = Integer.parseInt(parkingConcessionId);
		parkingConcessionrepository.deleteById(id);
		return RetKit.ok();
	}

	/**
	 * 通过审核
	 * @param id
	 * @return
	 */
	@Override
	public RetKit pass(String id){
		parkingConcessionrepository.changeStatus(BaseConstant.ParkingConcession.NotUsedStatus.getTypeValue(), Integer.parseInt(id));
		return RetKit.ok();
	}
	/**
	 * 不通过审核
	 * @param id
	 * @return
	 */
	@Override
	public RetKit notPass(String id){
		parkingConcessionrepository.changeStatus(BaseConstant.ParkingConcession.NotPassStatus.getTypeValue(), Integer.parseInt(id));
		return RetKit.ok();
	}
}
