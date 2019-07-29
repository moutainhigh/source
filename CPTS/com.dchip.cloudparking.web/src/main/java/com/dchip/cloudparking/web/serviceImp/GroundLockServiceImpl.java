package com.dchip.cloudparking.web.serviceImp;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.IGroundLockRepository;
import com.dchip.cloudparking.web.iRepository.IMainControlRepository;
import com.dchip.cloudparking.web.iService.IGroundLockService;
import com.dchip.cloudparking.web.model.po.GroundLock;
import com.dchip.cloudparking.web.model.po.MainControl;
import com.dchip.cloudparking.web.po.qpo.QGroundLock;
import com.dchip.cloudparking.web.po.qpo.QMainControl;
import com.dchip.cloudparking.web.po.qpo.QParking;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.dchip.cloudparking.web.utils.StrKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GroundLockServiceImpl extends BaseService implements IGroundLockService {

    @Autowired
    private IGroundLockRepository groundLockRepository;
    @Autowired 
    private IMainControlRepository mainControlRepository;

    @Override
    public Object getList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para) {
        List<Map<String, Object>> listData = new ArrayList<>();

        QGroundLock qGroundLock = QGroundLock.groundLock;
        QMainControl qMainControl = QMainControl.mainControl;
        QParking qParking = QParking.parking;

        List<Predicate> predicates = new ArrayList<>();
        if (!para.isEmpty()) {
            for (Map<String, Object> map : para) {
                if (map.get("searchUId") != null) {
                	predicates.add(qGroundLock.groundUid.like("%"+map.get("searchUId").toString()+"%"));
                }
            }
        }

        Sort sort = null;
        if(StrKit.notBlank(sortName) && StrKit.notBlank(direction) ){
            sort = new Sort("createTime", direction, qGroundLock);
        }else{
            sort = new Sort("createTime", "DESC", qGroundLock);
        }

        JPAQuery<Tuple> jPAQuery = jpaQueryFactory
                .select(qGroundLock.id,qGroundLock.parkingId,qGroundLock.groundUid,qGroundLock.licensePlate,
                		qGroundLock.createTime,qGroundLock.remark,
                		qGroundLock.currentState,qMainControl.mac,
                		qMainControl.id,qParking.parkName)
                .from(qGroundLock)
                .leftJoin(qMainControl).on(qMainControl.id.eq(qGroundLock.mainId))
                .leftJoin(qParking).on(qMainControl.parkingId.eq(qParking.id))
                .where(predicates.toArray(new Predicate[predicates.size()]))
                .orderBy(sort.getOrderSpecifier())
                .offset(pageNum * pageSize)
                .limit(pageSize);

        QueryResults<Tuple> queryResults = jPAQuery.fetchResults();

        for (Tuple tuple : queryResults.getResults()) {
            Map<String, Object> map = new HashMap<>();

            map.put("id", tuple.get(qGroundLock.id));
            map.put("parkingId", tuple.get(qGroundLock.parkingId));
            map.put("groundUid", tuple.get(qGroundLock.groundUid));
            map.put("licensePlate", tuple.get(qGroundLock.licensePlate));
            map.put("currentState", tuple.get(qGroundLock.currentState));
            map.put("mainId", tuple.get(qMainControl.id));
            map.put("createTime", tuple.get(qGroundLock.createTime));
            map.put("remark", tuple.get(qGroundLock.remark));
            map.put("mac",tuple.get(qMainControl.mac));
            map.put("parkName",tuple.get(qParking.parkName));

            listData.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("content", listData);
        result.put("totalElements", queryResults.getTotal());
        result.put("code", 0);
        return result;
    }

    @Override
    public RetKit save(Integer mainControlId, String licensePlate, String groundUid) {
    	try {
	        Optional<MainControl> mainControlOptional = mainControlRepository.findById(mainControlId);
	    	if(mainControlOptional.isPresent()) {
	    		GroundLock groundLock = groundLockRepository.findByUid(groundUid);
	    		if (groundLock != null) {
	    			groundLock.setLicensePlate(licensePlate);
	    			groundLock.setParkingId(mainControlOptional.get().getParkingId());
	    			groundLock.setMainId(mainControlId);
	    			groundLock.setCurrentState(BaseConstant.GroundLockState.Close.getTypeValue());
	    			groundLock.setCreateTime(new Date());
	    			groundLockRepository.save(groundLock);
	    			return RetKit.ok();
				}else {
					return RetKit.fail("地锁信息丢失");
				}
	    	}else {
				return RetKit.fail("主控板不存在");
			}
    	}catch (Exception e) {
    		return RetKit.fail("保存失败");
		}
    }
    
    @Override
    public RetKit delete(Integer groundId) {
    	if (groundId == null) {
			return RetKit.fail("唯一识别码为空");
		}else {
			groundLockRepository.deleteById(groundId);
			return RetKit.ok("删除成功");
		}
    	
    }
    
    public List<GroundLock> findAllGroundLockUId(){
    	return groundLockRepository.findAll();
    }
    
    @Override
    public RetKit checkgroundLockName(String licensePlate) {
    	Integer count = groundLockRepository.checkRepeat(licensePlate);
    	if(count > 0) {
    		return RetKit.fail("该车牌已存在");
    	}
    	return RetKit.ok();
    }

}
