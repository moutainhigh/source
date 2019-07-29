package com.dchip.cloudparking.web.serviceImp;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.IDeductionRepository;
import com.dchip.cloudparking.web.iRepository.IParkingUserRepository;
import com.dchip.cloudparking.web.iRepository.ISysRoleRepository;
import com.dchip.cloudparking.web.iService.IDeductionManageService;
import com.dchip.cloudparking.web.model.po.Deduction;
import com.dchip.cloudparking.web.model.po.ParkingUser;
import com.dchip.cloudparking.web.model.vo.DeductionVo;
import com.dchip.cloudparking.web.po.qpo.QDeduction;
import com.dchip.cloudparking.web.po.qpo.QParking;
import com.dchip.cloudparking.web.po.qpo.QParkingUser;
import com.dchip.cloudparking.web.utils.ExcelImportUtils;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.dchip.cloudparking.web.utils.StrKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class DeductionManageServiceImpl extends BaseService implements IDeductionManageService {

    @Autowired
    private IDeductionRepository deductionRepository;
    @Autowired
    private ISysRoleRepository sysRoleRepository;
    @Autowired
    private IParkingUserRepository parkingUserRepository;

    @Override
    public Object getDeductionList(Integer pageSize, Integer pageNum, String sortName, String direction,
                                   List<Map<String, Object>> para) {
        List<Map<String, Object>> data = new ArrayList<>();
        QDeduction qDeduction = QDeduction.deduction;
        QParkingUser qParkingUser = QParkingUser.parkingUser;
        QParking qParking = QParking.parking;

        QueryResults<Tuple> queryResults = getDeductionListTuple(pageSize, pageNum, sortName, direction, para)
                .fetchResults();

        // 将元组集合转成map集合
        for (Tuple tuple : queryResults.getResults()) {
            Map<String, Object> map = new HashMap<>();
            map.put("realName", tuple.get(qParkingUser.realName));
            map.put("deductionId", tuple.get(qDeduction.id));
            map.put("parkingId", tuple.get(qDeduction.parkingId));
            map.put("hourNum", tuple.get(qDeduction.hourNum));
            map.put("dueTime", tuple.get(qDeduction.dueTime));
            map.put("useTime", tuple.get(qDeduction.useTime));
            map.put("licensePlat", tuple.get(qDeduction.licensePlat));
            map.put("receiveTime", tuple.get(qDeduction.receiveTime));
            map.put("deductioinCode", tuple.get(qDeduction.deductioinCode));
//			map.put("consumptionUrl", tuple.get(qDeduction.consumptionUrl));
            map.put("deductionStatus", tuple.get(qDeduction.status));
            map.put("parkName", tuple.get(qParking.parkName));
            data.add(map);
        }
        // jqgrid数据拼装
        Map<String, Object> result = new HashMap<>();
        result.put("content", data);// 添加主体数据
        result.put("totalElements", queryResults.getTotal());// 添加总条数
        result.put("code", 0);
        return result;
    }

    @Override
    public RetKit save(DeductionVo vo) {
        try {
            Deduction deduction = DeductionVo.getDeduction(vo);
            if (deduction.getId() == null) {// 新增
                // for(int i = 0; i< vo.getGenerateCount();i++){
                Deduction entity = new Deduction();
                entity.setHourNum(deduction.getHourNum());
                entity.setDueTime(deduction.getDueTime());
                entity.setConsumptionUrl(deduction.getConsumptionUrl());
                entity.setDeductioinCode(StrKit.getRandomUUID());
                deductionRepository.saveAndFlush(entity);
                // entity.setId(null);
                // }
            } else {// 编辑
                Deduction entity = deductionRepository.findById(deduction.getId()).get();
                entity.setDueTime(deduction.getDueTime());
                if (StrKit.notBlank(deduction.getConsumptionUrl())) {
                    entity.setConsumptionUrl(deduction.getConsumptionUrl());
                }
                deductionRepository.save(entity);
            }
            return RetKit.ok();
        } catch (Exception e) {
            return RetKit.fail("操作失败");
        }
    }

    @Override
    public RetKit del(Integer deductionId) {
        deductionRepository.deleteById(deductionId);
        return RetKit.ok();
    }

    @Override
    public RetKit softlyDel(Integer deductionId) {
        Integer integer = deductionRepository.changeStatus(BaseConstant.SoftDelete.delete.getTypeValue(), deductionId);
        if (integer == null || integer == 0) {
            return RetKit.fail();
        }
        return RetKit.ok();
    }

    @Override
    public RetKit changeStatus(Integer deductionId, Integer status) {
        deductionRepository.changeStatus(status, deductionId);
        return RetKit.ok();
    }

    @Override
    public ParkingUser findParkingUserByUserName(String userName) {
        return parkingUserRepository.findParkingUserByUserName(userName);
    }

    private JPAQuery<Tuple> getDeductionListTuple(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para) {
        QDeduction qDeduction = QDeduction.deduction;
        QParkingUser qParkingUser = QParkingUser.parkingUser;
        QParking qParking = QParking.parking;

        /**
         * 查询条件的谓语集合(where条件)
         */
        List<Predicate> predicates = new ArrayList<>();
        // 动态排序,需要使用自定义的Sort类
        Sort sort = null;
        if (StrKit.notBlank(sortName)) {
            switch (sortName) {
                case "deductionStatus":
                    sort = new Sort("status", direction.toString(), qDeduction);
                    break;
                case "realName":
                    sort = new Sort(sortName, direction.toString(), qParkingUser);
                    break;
                case "parkName":
                    sort = new Sort(sortName, direction.toString(), qParking);
                    break;
                default:
                    sort = new Sort(sortName, direction, qDeduction);
                    break;
            }
        } else {
            sort = new Sort("id", "desc", qDeduction);
        }

        if (!para.isEmpty()) {
            for (Map<String, Object> map : para) {
                // 搜索参数
                if (map.get("strRealName") != null) {
                    // 商户全名
                    predicates.add(qParkingUser.realName.like("%" + map.get("strRealName").toString() + "%"));
                }
                if (map.get("parkingId") != null) {
                    predicates.add(qDeduction.parkingId.eq(Integer.parseInt(map.get("parkingId").toString())));
                }
                if (map.get("strLicensePlate") != null) {
                    // 停车场名称
                    predicates.add(qDeduction.licensePlat.like("%" + map.get("strLicensePlate").toString() + "%"));
                }
                if (map.get("status") != null) {
                    if (Integer.parseInt(map.get("status").toString()) == BaseConstant.DeductionStatus.NotUsedStatus
                            .getTypeValue()) {
                        predicates.add(qDeduction.status.eq(BaseConstant.DeductionStatus.NotUsedStatus.getTypeValue()));
                    } else if (Integer
                            .parseInt(map.get("status").toString()) == BaseConstant.DeductionStatus.OutOfDateStatus
                            .getTypeValue()) {
                        predicates
                                .add(qDeduction.status.eq(BaseConstant.DeductionStatus.OutOfDateStatus.getTypeValue()));
                    } else if (Integer.parseInt(map.get("status").toString()) == BaseConstant.DeductionStatus.UsedStatus
                            .getTypeValue()) {
                        predicates.add(qDeduction.status.eq(BaseConstant.DeductionStatus.UsedStatus.getTypeValue()));
                    }
                }
            }
        }
        predicates.add(qDeduction.status.notIn(BaseConstant.SoftDelete.delete.getTypeValue()));

        // 查询结果
        JPAQuery<Tuple> jPAQuery = jpaQueryFactory
                .select(qDeduction.id, qDeduction.parkingId, qDeduction.hourNum, qDeduction.status, qDeduction.dueTime,
                        qDeduction.licensePlat, qDeduction.useTime, qDeduction.receiveTime, qDeduction.deductioinCode,
                        qDeduction.consumptionUrl, qParkingUser.realName, qParking.parkName)
                .from(qDeduction)
                .leftJoin(qParkingUser).on(qDeduction.parkingUserId.eq(qParkingUser.id))
                .leftJoin(qParking).on(qParking.id.eq(qDeduction.parkingId))
                .where(predicates.toArray(new Predicate[predicates.size()]))
                .orderBy(sort.getOrderSpecifier());
        if (pageSize == null && pageNum == null) {
            return jPAQuery;
        } else {
            return jPAQuery.offset(pageNum * pageSize).limit(pageSize);
        }
    }

    /**
     * 导出用户抵扣券列表
     */
    @Override
    public void deductionExport(List<Map<String, Object>> para, HttpServletRequest request,
                                HttpServletResponse response) {
        List<LinkedList<Object>> dataList = new ArrayList<>();

        QDeduction qDeduction = QDeduction.deduction;
        QParkingUser qParkingUser = QParkingUser.parkingUser;
        QParking qParking = QParking.parking;

        QueryResults<Tuple> queryResults = getDeductionListTuple(null, null, null, null, para).fetchResults();

        String[] headers = {"发券商户", "停车场名称", "车牌号码", "抵扣小时数", "使用状态", "领用时间", "使用时间", "到期时间", "抵扣券码"};
        LinkedList<Object> linkedList;

        // 将元组集合转成map集合
        for (Tuple tuple : queryResults.getResults()) {
            linkedList = new LinkedList<>();
            linkedList.add(tuple.get(qParkingUser.realName));
            linkedList.add(tuple.get(qParking.parkName));            //停车场名称
            linkedList.add(tuple.get(qDeduction.licensePlat));
            linkedList.add(tuple.get(qDeduction.hourNum));

//			使用状态
            if (tuple.get(qDeduction.status) == BaseConstant.DeductionStatus.NotUsedStatus.getTypeValue()) {
                linkedList.add(BaseConstant.DeductionStatus.NotUsedStatus.getTypeDescription());
            } else if (tuple.get(qDeduction.status) == BaseConstant.DeductionStatus.OutOfDateStatus.getTypeValue()) {
                linkedList.add(BaseConstant.DeductionStatus.OutOfDateStatus.getTypeDescription());
            } else if (tuple.get(qDeduction.status) == BaseConstant.DeductionStatus.UsedStatus.getTypeValue()) {
                linkedList.add(BaseConstant.DeductionStatus.UsedStatus.getTypeDescription());
            } else {
                linkedList.add("");
            }

            linkedList.add(tuple.get(qDeduction.receiveTime));
            linkedList.add(tuple.get(qDeduction.useTime));
            linkedList.add(tuple.get(qDeduction.dueTime));
            linkedList.add(tuple.get(qDeduction.deductioinCode));

            dataList.add(linkedList);
        }
        String sheetName = "抵扣券列表";
        String fileName = "停车场抵扣券列表";

        ExcelImportUtils.excelExport(headers, dataList, sheetName, fileName).export(request, response);
    }

}
