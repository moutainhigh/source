package com.dchip.cloudparking.api.serviceImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.iRepository.IParkingRepository;
import com.dchip.cloudparking.api.iRepository.ISecondPlateNameRepository;
import com.dchip.cloudparking.api.iService.IWhiteListService;
import com.dchip.cloudparking.api.model.po.Parking;
import com.dchip.cloudparking.api.model.po.SecondPlateName;
import com.dchip.cloudparking.api.model.qpo.QParking;
import com.dchip.cloudparking.api.model.qpo.QSecondPlateName;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class WhiteListServiceImp extends BaseService implements IWhiteListService{
	@Autowired
	private ISecondPlateNameRepository secondPlateNameRepository;
	@Autowired
	private IParkingRepository parkingRepository;
	
	/**
	 * 添加白名单
	 */
	@Override
	public RetKit addWhiteList(String tmpFlag, String parkingId, String licencePlate) {
		try {
			if (StrKit.isBlank(tmpFlag)) {
				return RetKit.fail("tmpFlag不能为空！");
			}
			if (StrKit.isBlank(parkingId)) {
				return RetKit.fail("parkingId不能为空！");
			}
			if (StrKit.isBlank(licencePlate)) {
				return RetKit.fail("licencePlate不能为空！");
			}
			Optional<Parking> parkingOptional = parkingRepository.findById(Integer.parseInt(parkingId));
			if (parkingOptional.isPresent()) {
				SecondPlateName spn = new SecondPlateName();
				spn.setCreateTime(new Date());
				spn.setLicensePlate(licencePlate);
				spn.setParkingId(Integer.parseInt(parkingId));
				spn.setStatus(BaseConstant.WhiteListState.EnabledState.getTypeValue());
				spn.setTmpFlag(Integer.parseInt(tmpFlag));
				secondPlateNameRepository.save(spn);
				return RetKit.ok();
			}else {
				return RetKit.fail("该停车场不存在！");
			}
		} catch (Exception e) {
			return RetKit.fail();
		}
	}

	@Override
	public RetKit getWhiteList(String parkingId, Integer pageSize, Integer pageNum) {
		List<Map<String, Object>> data = new ArrayList<>();
		QSecondPlateName qSecondPlateName = QSecondPlateName.secondPlateName;
		QParking qParking = QParking.parking;
		/**
         * 查询条件的谓语集合(where条件)
         */
        List<Predicate> predicates = new ArrayList<>();
        if(parkingId != null && !parkingId.equals("")){
            predicates.add(qParking.id.eq(Integer.parseInt(parkingId)));
        }
		//查询结果
        JPAQuery<Tuple> jPAQuery = jpaQueryFactory.select(qSecondPlateName.id,qSecondPlateName.createTime,
        		qSecondPlateName.licensePlate,qSecondPlateName.parkingId,qSecondPlateName.status,
        		qSecondPlateName.tmpFlag,qParking.parkName)
        		.from(qSecondPlateName)
        		.leftJoin(qParking).on(qParking.id.eq(qSecondPlateName.parkingId))
        		.where(predicates.toArray(new Predicate[predicates.size()]))
        		.orderBy(qSecondPlateName.createTime.desc())
        		.offset(pageNum * pageSize).limit(pageSize);
        QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
        //将元组集合转成map集合
        for (Tuple tuple : queryResults.getResults()) {
        	Map<String, Object> map = new HashMap<>();
        	map.put("id", tuple.get(qSecondPlateName.id));
        	map.put("createTime", tuple.get(qSecondPlateName.createTime));
        	map.put("licensePlate", tuple.get(qSecondPlateName.licensePlate));
        	map.put("parkingId", tuple.get(qSecondPlateName.parkingId));
        	map.put("status", tuple.get(qSecondPlateName.status));
        	map.put("tmpFlag", tuple.get(qSecondPlateName.tmpFlag));
        	map.put("parkName", tuple.get(qParking.parkName));
        	data.add(map);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("content",data);
        result.put("totalElements",queryResults.getTotal());//添加总条数
        if(queryResults.getTotal() % pageSize == 0 ) {
        	result.put("totalPages", (queryResults.getTotal() / pageSize));//总页面数
        }else {
        	result.put("totalPages", (queryResults.getTotal() / pageSize)+1 );//总页面数
        }
		return RetKit.okData(result);
	}

	@Override
	public RetKit delWhiteList(String whiteListId) {
		Integer wId = Integer.parseInt(whiteListId);
		Optional<SecondPlateName> secondPlateNameOptional = secondPlateNameRepository.findById(wId);
		if (secondPlateNameOptional.isPresent()) {
			secondPlateNameRepository.deleteById(Integer.parseInt(whiteListId));
			return RetKit.ok();
		}else {
			return RetKit.fail();
		}
	}
	
}
