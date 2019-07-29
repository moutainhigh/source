package com.dchip.cloudparking.api.serviceImp;

import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.iRepository.IDeductionModelRepository;
import com.dchip.cloudparking.api.iRepository.IDeductionRepository;
import com.dchip.cloudparking.api.iRepository.IParkingUserRepository;
import com.dchip.cloudparking.api.iService.IDeductionService;
import com.dchip.cloudparking.api.model.po.Deduction;
import com.dchip.cloudparking.api.model.po.DeductionModel;
import com.dchip.cloudparking.api.model.po.ParkingUser;
import com.dchip.cloudparking.api.model.qpo.QDeduction;
import com.dchip.cloudparking.api.model.qpo.QDeductionModel;
import com.dchip.cloudparking.api.model.qpo.QParkingUser;
import com.dchip.cloudparking.api.model.qpo.QUser;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeductionServiceImpl extends BaseService implements IDeductionService {

	@Autowired
	private IParkingUserRepository parkingUserRepository;
	@Autowired
	private IDeductionModelRepository deductionModelRepository;
	@Autowired
	private IDeductionRepository deductionRepository;

    @Override
    public Object getDeductionList(Integer pageSize, Integer pageNum,Integer parkingUserId,String yearMonth) {
        List<Map<String, Object>> data = new ArrayList<>();
        QDeduction qDeduction = QDeduction.deduction;
        QUser qUser = QUser.user;
        QDeductionModel qDeductionModel = QDeductionModel.deductionModel;
        QParkingUser qParkingUser = QParkingUser.parkingUser;

        /**
         * 查询条件的谓语集合(where条件)
         */
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(qDeduction.parkingUserId.eq(parkingUserId));
        String year = "";
        String month = "";
        if(StrKit.notBlank(yearMonth)) {
        	year = yearMonth.split("-")[0];
        	month = yearMonth.split("-")[1];
        }
        predicates.add(qDeduction.receiveTime.year().eq(Integer.parseInt(year)).and(qDeduction.receiveTime.month().eq(Integer.parseInt(month))));

        //查询结果
        JPAQuery<Tuple> jPAQuery = jpaQueryFactory
                .select(
                		qDeduction.licensePlat,
                        qDeduction.deductioinCode,//抵扣码
                        qDeduction.id,
                        qDeduction.parkingId,
                        qDeduction.hourNum,
//                        qDeduction.dueTime,
                        qDeduction.useTime,//使用时间
                        qDeduction.receiveTime,//领用时间
//                        qDeduction.parkingUserId,//商户id
//                        qDeduction.deductionModelId,
                        qDeduction.status,//'0-未使用 1-已使用 2-已过期'
                        qDeductionModel.dueTime,
                        qDeductionModel.hourNum
//                        qDeduction.consumptionUrl
                )
                .from(qDeduction)
                .leftJoin(qUser).on(qDeduction.licensePlat.eq(qUser.licensePlat))
                .leftJoin(qDeductionModel).on(qDeduction.deductionModelId.eq(qDeductionModel.id))
                .leftJoin(qParkingUser).on(qDeduction.parkingId.eq(qParkingUser.id))
                .where(predicates.toArray(new Predicate[predicates.size()]))
                .orderBy(qDeduction.receiveTime.desc())
                .offset(pageNum * pageSize).limit(pageSize);
        QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
        //将元组集合转成map集合
        for (Tuple tuple : queryResults.getResults()) {
            Map<String, Object> map = new HashMap<>();
            map.put("licensePlate", tuple.get(qDeduction.licensePlat));
            map.put("deductioinCode", tuple.get(qDeduction.deductioinCode));
            map.put("deductionId", tuple.get(qDeduction.id));
            map.put("parkingId", tuple.get(qDeduction.parkingId));
            map.put("hourNum", tuple.get(qDeduction.hourNum));
//            map.put("dueTime", tuple.get(qDeduction.dueTime));
            map.put("dueTime", tuple.get(qDeductionModel.dueTime));
            map.put("useTime", tuple.get(qDeduction.useTime));
            map.put("receiveTime", tuple.get(qDeduction.receiveTime));
//            map.put("consumptionUrl", tuple.get(qDeduction.consumptionUrl));
//            map.put("parkingUserId", tuple.get(qDeduction.parkingUserId));
//            map.put("deductionModelId", tuple.get(qDeduction.deductionModelId));
            map.put("status", tuple.get(qDeduction.status));
            data.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        Map<String,Object> content = new HashMap<>();
        content.put("content",data);
        content.put("totalElements",queryResults.getTotal());//添加总条数
        if(queryResults.getTotal() % pageSize == 0 ) {
            content.put("totalPages", (queryResults.getTotal() / pageSize));//总页面数
        }else
            content.put("totalPages", (queryResults.getTotal() / pageSize)+1 );//总页面数
        result.put("msg", "操作成功");//添加主体数据
        result.put("code", 200);
        result.put("success",true);
        result.put("data", content);//添加主体数据
        return result;
    }

    /**
     * 2019/3/19 
     * zyy修改
     */
    @Override
    public RetKit bindDeduction(String licensePlat, Integer parkingUserId) {

        /**
         * 1.查询商户是否存在
         * 2.查看该车牌是否有该停车场未使用的抵扣券如果有就不给再绑定了-----------注意是否要判断该车牌在该停车场有未出库记录才可以领取
         * 3.查询商户(含parkingId) 所在的停车场是否有可用的抵扣券模板
         * 4.领券（将抵扣券模板和商户信息和车牌信息新增到deduction表）
         */
        ParkingUser parkingUser = parkingUserRepository.findParkingUser(parkingUserId);
        if (parkingUser == null) {
            return RetKit.fail("商户信息不存在或已被删除");
        }
        DeductionModel dModel = deductionModelRepository.getDeductionModelByParkingId(parkingUser.getParkingId());
        if (dModel == null) {
            return RetKit.fail("该停车场没有可用抵扣券");
        }
        Deduction deduction = deductionRepository.getCanUseDeduction(licensePlat, parkingUser.getParkingId(), parkingUserId);
        if (deduction != null) {
			Date dueTime = deduction.getDueTime();
			if (dueTime != null) {
				if (dueTime.after(new Date())) {
					return RetKit.fail("该车牌已有抵扣券，无需重复绑定");
				}
			}else {
				return RetKit.fail("该车牌已有抵扣券，无需重复绑定");
			}
		}
    	Deduction newDeduction = new Deduction();
    	newDeduction.setParkingId(parkingUser.getParkingId());
    	newDeduction.setHourNum(dModel.getHourNum());
    	newDeduction.setStatus(BaseConstant.DeductionStatus.NotUsedStatus.getTypeValue());
    	newDeduction.setDueTime(dModel.getDueTime());
    	newDeduction.setLicensePlat(licensePlat);
    	newDeduction.setReceiveTime(new Date());
    	newDeduction.setDeductioinCode(StrKit.getRandomUUID());
    	newDeduction.setParkingUserId(parkingUserId);
    	newDeduction.setDeductionModelId(dModel.getId());
    	deductionRepository.save(newDeduction);
        return RetKit.ok();
    }
}

