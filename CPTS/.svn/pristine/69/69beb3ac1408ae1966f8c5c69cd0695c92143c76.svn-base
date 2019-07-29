package com.dchip.cloudparking.web.serviceImp;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.*;
import com.dchip.cloudparking.web.iService.IParkingInfoManageService;
import com.dchip.cloudparking.web.model.po.*;
import com.dchip.cloudparking.web.po.qpo.QCard;
import com.dchip.cloudparking.web.po.qpo.QCardLicensePlate;
import com.dchip.cloudparking.web.po.qpo.QCoupon;
import com.dchip.cloudparking.web.po.qpo.QFreeStandard;
import com.dchip.cloudparking.web.po.qpo.QParking;
import com.dchip.cloudparking.web.po.qpo.QParkingInfo;
import com.dchip.cloudparking.web.po.qpo.QParkingRule;
import com.dchip.cloudparking.web.po.qpo.QParkingRuleRelation;
import com.dchip.cloudparking.web.po.qpo.QUser;
import com.dchip.cloudparking.web.po.qpo.QUserCoupon;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.utils.DateUtil;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.dchip.cloudparking.web.utils.TimeUtils;
import com.dchip.cloudparking.web.utils.parkingfee.FeeModel;
import com.dchip.cloudparking.web.utils.parkingfee.FeeServiceKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ParkingInfoManageServiceImp extends BaseService implements IParkingInfoManageService {
    @Autowired
    public IParkingInfoRepository parkingInfoRepository;
    @Autowired
    public IParkingRepository parkingRepository;
    @Autowired
    public IUserRepository userRepository;
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IRoadwayRepository roadwayRepository;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    public IFreeStandardRepository freeStandardRepository;

    @Override
    public Object getParkingInfoList(Integer pageSize, Integer pageNum, String sortName, String direction,
                                     List<Map<String, Object>> para) {
        UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Map<String, Object>> data = new ArrayList<>();
        QParking qParking = QParking.parking;//停车场
        QParkingInfo qParkingInfo = QParkingInfo.parkingInfo;//停车场信息
        QUser qUser = QUser.user;//用户详情

        /**
         * 查询条件的谓语集合(where条件)
         */
        List<Predicate> predicates = new ArrayList<>();
        if(user.getRoleType() != BaseConstant.SysRoleType.adminType.getTypeValue()) {
        	//停车场管理员
        	predicates.add(qParking.companyId.eq(user.getCompanyId()));
        }
        
        if (!para.isEmpty()) {
            for (Map<String, Object> map : para) {
                //搜索参数
                if (map.get("searchParkName") != null) {
                    //停车场名称
                    predicates.add(qParking.parkName.like("%" + map.get("searchParkName").toString() + "%"));
                } else if (map.get("searchParkingLicensePlate") != null) {
                    //车牌号码
                    Predicate predicate = qParkingInfo.licensePlate.like("%" + map.get("searchParkingLicensePlate").toString() + "%");
                    predicates.add(predicate);
                } else if (map.get("searchUserPhone") != null) {
                    //用户手机号码
                    Predicate predicate = qUser.phone.like("%" + map.get("searchUserPhone").toString() + "%");
                    predicates.add(predicate);
                }
            }
        }

        //动态排序,需要使用自定义的Sort类
        Sort sort = null;
        switch (sortName) {
            case "parkDate":
                sort = new Sort("parkDate", direction.toString(), qParkingInfo);
                break;
            case "userPhone":
                sort = new Sort("phone", direction.toString(), qUser);
                break;
            case "licensePlate":
                sort = new Sort("licensePlate", direction.toString(), qParkingInfo);
                break;
            case "parkName":
                sort = new Sort("parkName", direction.toString(), qParking);
                break;
            case "outDate":
                sort = new Sort("outDate", direction.toString(), qParkingInfo);
                break;
            default:
                sort = new Sort(sortName, direction, qParkingInfo);
                break;
        }

        //查询结果
        JPAQuery<Tuple> jPAQuery = jpaQueryFactory
                .select(qParking.id, qParking.parkName, qParking.provinceName, qParking.cityName, qParking.areaName, qParking.location,
                        qParkingInfo.id, qParkingInfo.licensePlate, qParkingInfo.parkDate, qParkingInfo.outDate, qUser.phone)
                .from(qParkingInfo)
                .leftJoin(qParking).on(qParking.parkCode.eq(qParkingInfo.parkCode))
                .leftJoin(qUser).on(qUser.id.eq(qParkingInfo.userId))
                .where(predicates.toArray(new Predicate[predicates.size()]))
                .orderBy(sort.getOrderSpecifier())
                .limit(pageSize).offset(pageNum * pageSize);

        QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
        //将元组集合转成map集合
        for (Tuple tuple : queryResults.getResults()) {
            Map<String, Object> map = new HashMap<>();
            map.put("parkingId", tuple.get(qParking.id));
            map.put("parkingInfoId", tuple.get(qParkingInfo.id));
            map.put("parkName", tuple.get(qParking.parkName));
            map.put("locations", tuple.get(qParking.provinceName) + tuple.get(qParking.cityName) + tuple.get(qParking.areaName) + tuple.get(qParking.location));
            map.put("licensePlate", tuple.get(qParkingInfo.licensePlate));
            map.put("parkDate", tuple.get(qParkingInfo.parkDate));
            map.put("outDate", tuple.get(qParkingInfo.outDate));
            map.put("userPhone", tuple.get(qUser.phone));
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
    public Object getParkingInfoDetail(String licensePlate) {
        UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer accountId = user.getAccountId();
        Optional<Account> account = accountRepository.findById(accountId);

        List<Map<String, Object>> listData = new ArrayList<>();

        QParking qParking = QParking.parking;//停车场
        QParkingInfo qParkingInfo = QParkingInfo.parkingInfo;//停车场信息

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(qParkingInfo.licensePlate.eq(licensePlate));
        if (!account.isPresent()) {
            return RetKit.fail();
        } else {
            String userName = account.get().getUserName();
            if (!userName.equals("admin")) {
                Integer companyId = account.get().getCompanyId();
                predicates.add(qParking.companyId.eq(companyId));
            }
        }
        JPAQuery<Tuple> jPAQuery = jpaQueryFactory
                .selectDistinct(
                        qParkingInfo.id,
                        qParking.parkName,
                        qParking.provinceName,
                        qParking.cityName,
                        qParking.areaName,
                        qParking.location,
                        qParkingInfo.licensePlate,
                        qParkingInfo.parkDate,
                        qParkingInfo.outDate)
                .from(qParkingInfo)
                .leftJoin(qParking).on(qParking.parkCode.eq(qParkingInfo.parkCode))
                .where(predicates.toArray(new Predicate[predicates.size()]))
                .orderBy(qParkingInfo.parkDate.desc());

        QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
        // 将元组集合转成map集合
        for (Tuple tuple : queryResults.getResults()) {
            Map<String, Object> map = new HashMap<>();
            map.put("parkingInfoId", tuple.get(qParkingInfo.id));
            map.put("licensePlate", tuple.get(qParkingInfo.licensePlate));
            map.put("parkName", tuple.get(qParking.parkName));
            map.put("provinceName", tuple.get(qParking.provinceName));
            map.put("cityName", tuple.get(qParking.cityName));
            map.put("areaName", tuple.get(qParking.areaName));
            map.put("location", tuple.get(qParking.location));
            map.put("locations", tuple.get(qParking.provinceName) + tuple.get(qParking.cityName) + tuple.get(qParking.areaName) + tuple.get(qParking.location));
            map.put("parkDate", tuple.get(qParkingInfo.parkDate));
            map.put("outDate", tuple.get(qParkingInfo.outDate));
            listData.add(map);
        }
        return RetKit.okData(listData);
    }


    @Override
    public RetKit getOutRoadname(String parkingId) {
        List<Map<String, Object>> OutRoadnameList = roadwayRepository.getOutRoadName(Integer.parseInt(parkingId));
        return RetKit.okData(OutRoadnameList);
    }


    /**
     * 人工计算一定要有：停车信息id、入场时间、出场时间、车牌号码
     * @param parkingInfoId
     * @param inTime
     * @param outTime
     * @param licensePlate
     * @return
     */
    @Override
    public RetKit manualSettlement(Integer parkingInfoId, String inTime, String outTime, String licensePlate) {
        // TODO Auto-generated method stub
        //根据车牌，获取用户id、用户余额
        QUser qUser = QUser.user;
        User userPo = this.jpaQueryFactory
                .select(qUser)
                .from(qUser)
                .where(qUser.licensePlat.eq(licensePlate).and(qUser.memberId.ne(BaseConstant.UserGrade.InitMember.getTypeValue())))
                .fetchFirst();

        //获取停车时间、出场时间、车牌，计算停车时长 start
        Long parkingTimeLen = new Long(0);
        QParkingInfo qParkingInfo = QParkingInfo.parkingInfo;
        ParkingInfo parkingInfo = this.jpaQueryFactory.select(qParkingInfo).from(qParkingInfo).where(qParkingInfo.id.eq(parkingInfoId)).fetchFirst();

        /**
         * 查看是否有月卡
         */
        QCard monthlyCard = QCard.card;
        QCardLicensePlate cardLinceseEntity = QCardLicensePlate.cardLicensePlate;
        Card card =  this.jpaQueryFactory.select(monthlyCard).from(monthlyCard,cardLinceseEntity)
                    .where(monthlyCard.id.eq(cardLinceseEntity.cardId).and(cardLinceseEntity.lisencePlate.eq(parkingInfo.getLicensePlate())))
                    .fetchFirst();

        //入场时间，出场时间，过期时间
//        Date dateIn,dateOut;
        Date expireDate=null;
//        dateIn = parkingInfo.getParkDate();
//        dateOut = parkingInfo.getOutDate();
        if(card != null ){
            expireDate = card.getExpiryTime();
        }
        //获取停车时间、出场时间、计算停车时长 end

        //======================  总费用计算 start
        QFreeStandard qFreeStandard = QFreeStandard.freeStandard;
        QParking qParking = QParking.parking;
        Parking parkingPO = this.jpaQueryFactory.select(qParking).from(qParking).where(qParking.parkCode.eq(parkingInfo.getParkCode())).fetchFirst();
        FreeStandard freeStandard = this.jpaQueryFactory.select(qFreeStandard).from(qFreeStandard).where(qFreeStandard.id.eq(parkingPO.getFreeStandardId())).fetchFirst();
        FeeModel feeModel = new FeeModel();
        feeModel.setHourCost(freeStandard.getHourCost().doubleValue());
        feeModel.setFreeMinute(freeStandard.getFreeTimeLength());
        feeModel.setMostCostDaily(freeStandard.getMostCost().doubleValue());

        //======================  获取 工作日附加参数、工作时附加参数 start
        QParkingRule ruleEntity = QParkingRule.parkingRule;
        QParkingRuleRelation ruleRelationEntity = QParkingRuleRelation.parkingRuleRelation;
        ParkingRule rule = this.jpaQueryFactory.select(ruleEntity).from(ruleEntity, ruleRelationEntity)
                .where(ruleEntity.id.eq(ruleRelationEntity.parkingRuleId)
                        .and(ruleRelationEntity.parkingId.eq(parkingPO.getId())
                                .and(ruleEntity.ruleType.eq(BaseConstant.RoadMarker.IN.getTypeValue()))))
                .fetchFirst();
        //工作日附加参数
        Integer workDayFrom = null;
        Integer workDayTo = null;
        //工作时附加参数
        String fromWorkTimeStr = null;
        String toWorkTimeStr = null;
        if(rule != null){
            workDayFrom = rule.getStartDay();//代表周二
            workDayTo = rule.getEndDay();//代表周五
            fromWorkTimeStr = rule.getStartTime();
              toWorkTimeStr = rule.getEndTime();
        }
        //======================  获取 工作日附加参数、工作时附加参数 end

        FeeServiceKit feeService = null;
        if(card == null){
            feeService = new FeeServiceKit(feeModel, inTime, outTime, null, null);
        }else if(card.getType() == BaseConstant.CardType.Month.getTypeValue()){
//            feeService= new FeeServiceKit( feeModel,  DateUtil.getDateTimeStr(dateIn), DateUtil.getDateTimeStr(dateOut), DateUtil.getDateTimeStr(expireDate),card.getType());
            feeService= new FeeServiceKit( feeModel, inTime, outTime, DateUtil.getDateTimeStr(expireDate),card.getType());
        }else if(card.getType() == BaseConstant.CardType.WorkDay.getTypeValue()){
//            feeService= new FeeServiceKit( feeModel, DateUtil.getDateTimeStr(dateIn), DateUtil.getDateTimeStr(dateOut), DateUtil.getDateTimeStr(expireDate),card.getType(),workDayFrom,workDayTo);
            feeService= new FeeServiceKit( feeModel, inTime, outTime, DateUtil.getDateTimeStr(expireDate),card.getType(),workDayFrom,workDayTo);
        }else if(card.getType() == BaseConstant.CardType.WorkTime.getTypeValue()){
//            feeService = new FeeServiceKit( feeModel,  DateUtil.getDateTimeStr(dateIn), DateUtil.getDateTimeStr(dateOut), DateUtil.getDateTimeStr(expireDate),card.getType(),workDayFrom,workDayTo,fromWorkTimeStr,toWorkTimeStr);
            feeService = new FeeServiceKit( feeModel, inTime, outTime, DateUtil.getDateTimeStr(expireDate),card.getType(),workDayFrom,workDayTo,fromWorkTimeStr,toWorkTimeStr);
        }
//        BigDecimal totalFee = new BigDecimal(feeService.getResultFee());


//        /**
//         * 预约计费才需要计费
//         */
//        QAppointment appointmentEntity = QAppointment.appointment;
//        Appointment appointmentPO = this.jpaQueryFactory.select(appointmentEntity).from(appointmentEntity)
//                .where(appointmentEntity.id.eq(parkingInfo.getAppointmentId())).fetchFirst();
//        if(appointmentPO != null){
//
//        }
//
//
//        //======================  总费用计算 end
//        try{
//            this.generateOrder(userPo.getId(), totalFee, parkingInfoId, parkingTimeLen, userPo.getBalance());
//            return RetKit.ok("操作成功!");
//        }catch (Exception e){
//            return RetKit.fail("操作失败!");
//        }
        Map<String,Object> feeMap = new HashMap<>();
        feeMap.put("cost",feeService.getResultFee());
        return  RetKit.okData(feeMap);
    }

    @Override
    public RetKit getFreeStandardList() {
        return RetKit.okData(freeStandardRepository.findAll());
    }

    @Override
    public FreeStandard getFreeStandardById(String id) {
        try {
            FreeStandard freeStandard = freeStandardRepository.findById(Integer.parseInt(id)).get();
            return freeStandard;
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public RetKit save(String parkingInfoId, String outTime, String roadWayId, String cost) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Optional<ParkingInfo> parkingInfoOptional = parkingInfoRepository.findById(Integer.parseInt(parkingInfoId));
            if (parkingInfoOptional.isPresent()) {
                //更新停车信息表数据
                ParkingInfo parkingInfo = parkingInfoOptional.get();
                parkingInfo.setOutDate(sdf.parse(outTime));
                parkingInfo.setStatus(BaseConstant.parkingInfoStatus.finishedStatus.getValue());
                parkingInfo.setOutRoadWayId(Integer.parseInt(roadWayId));
                parkingInfo = parkingInfoRepository.save(parkingInfo);

                //生成订单
                Order order = orderRepository.findOrder(parkingInfo.getId());
                Date parkDate = parkingInfo.getParkDate();
                Date outDate = parkingInfo.getOutDate();
                // 获得两个时间的毫秒时间差异
                double diff = outDate.getTime() - parkDate.getTime();
                // 计算差多少分钟
                int min = (int) Math.ceil(diff / 1000 / 60);
                if (order == null) {
                    order = new Order();
                    order.setParkingInfoId(parkingInfo.getId());

                    QUser qUser = QUser.user;
                    User user = this.jpaQueryFactory
                            .select(qUser)
                            .from(qUser)
                            .where(qUser.licensePlat.eq(parkingInfo.getLicensePlate())).fetchFirst();
//                    order.setUserId(parkingInfo.getUserId());
//                    order.setUserId(user.getId());
                }
                order.setPayTime(new Date());
                order.setParkingTime(min);
                order.setFinalFee(new BigDecimal(cost));
                orderRepository.save(order);
                //还缺支付状态，支付方式，优惠金额，预约ID(待结算合并后完成)
                return RetKit.ok();
            } else {
                return RetKit.fail();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return RetKit.fail();
        }
    }

    /**
     * 结算
     *
     * @param userId
     * @param totalFee
     * @param parkingInfoId
     * @param parkingTimeLen
     * @param balance
     */
    private void generateOrder(Integer userId, BigDecimal totalFee, Integer parkingInfoId, Long parkingTimeLen, BigDecimal balance) {

        BigDecimal lastFee = new BigDecimal(0);

        //生成订单
        //往order表里生成定单
        Order order = new Order();
        order.setFee(totalFee);
        order.setType(BaseConstant.OrderType.Balance.getTypeValue());
        order.setParkingInfoId(parkingInfoId);
        if (userId != null) {
            order.setUserId(userId);
        }

        order.setIsTransfer(BaseConstant.TransferStatus.UnExtracted.getStatusValue());
        order.setParkingTime(TimeUtils.getMillScondByMunites(parkingTimeLen).intValue());
        //查找优惠券
        //查询相关优惠减免，并去账号扣款写入账单
        QUserCoupon userCoupon = QUserCoupon.userCoupon;
        QCoupon coupon = QCoupon.coupon;
        //按时间排序，取优惠券在最前的
        UserCoupon userCouponPO = this.jpaQueryFactory.select(userCoupon).from(userCoupon, coupon)
                .where(userCoupon.userId.eq(userId)
                        .and(userCoupon.status.eq(BaseConstant.CouponStatus.EnabledStatus.getTypeValue())
                                .and(userCoupon.endDate.after(new Date()))
                                .and(userCoupon.couponId.eq(coupon.id))
                                .andAnyOf(coupon.endTime.isNull(), coupon.endTime.after(new Date()))))
                .orderBy(userCoupon.endDate.asc()).fetchFirst();
        //有优惠券
        if (userCouponPO != null) {
            Coupon couponPO = this.jpaQueryFactory.select(coupon).from(coupon).where(coupon.id.eq(userCouponPO.getCouponId())).fetchOne();
            if (couponPO != null) {
                lastFee = totalFee.subtract(new BigDecimal(couponPO.getCount()));
                order.setDiscountAmount(new BigDecimal(couponPO.getCount()));
                order.setCouponId(couponPO.getId());
                order.setFinalFee(lastFee);
            }
        } else {//无优惠券
            order.setDiscountAmount(new BigDecimal(0));
            order.setFinalFee(totalFee);
        }

        //去账户扣款
        if (balance.compareTo(new BigDecimal(0)) == 1 || balance.compareTo(new BigDecimal(0)) == 0) {
            QUser user = QUser.user;
            this.jpaQueryFactory.update(user).set(user.balance, balance.subtract(order.getFinalFee())).where(user.id.eq(userId)).execute();
            order.setPayTime(new Date());
            order.setStatus(BaseConstant.PayStatus.Pay.getStatusValue());
        } else {//支付失败
            order.setPayTime(new Date());
            order.setStatus(BaseConstant.PayStatus.PayFailed.getStatusValue());
        }
        orderRepository.save(order);
    }




}
