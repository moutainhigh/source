package com.dchip.cloudparking.api.serviceImp;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.constant.ParkingConstant;
import com.dchip.cloudparking.api.controller.SubThread;
import com.dchip.cloudparking.api.iRepository.ICloneCarRepository;
import com.dchip.cloudparking.api.iRepository.IFreeStandardRepository;
import com.dchip.cloudparking.api.iRepository.IGroundLockRepository;
import com.dchip.cloudparking.api.iRepository.IMainControlRepository;
import com.dchip.cloudparking.api.iRepository.IMemberRuleRepository;
import com.dchip.cloudparking.api.iRepository.IMessageRepository;
import com.dchip.cloudparking.api.iRepository.IOrderRepository;
import com.dchip.cloudparking.api.iRepository.IParkingInfoRepository;
import com.dchip.cloudparking.api.iRepository.ISecondPlateNameRepository;
import com.dchip.cloudparking.api.iRepository.IUserCouponRepository;
import com.dchip.cloudparking.api.iRepository.IUserRepository;
import com.dchip.cloudparking.api.iService.IAppointmentService;
import com.dchip.cloudparking.api.iService.IGroundLockService;
import com.dchip.cloudparking.api.iService.IParkingConcessionService;
import com.dchip.cloudparking.api.iService.IParkingInfoService;
import com.dchip.cloudparking.api.model.po.Appointment;
import com.dchip.cloudparking.api.model.po.Card;
import com.dchip.cloudparking.api.model.po.CloneCar;
import com.dchip.cloudparking.api.model.po.Coupon;
import com.dchip.cloudparking.api.model.po.Deduction;
import com.dchip.cloudparking.api.model.po.FreeStandard;
import com.dchip.cloudparking.api.model.po.GroundLock;
import com.dchip.cloudparking.api.model.po.LockCommond;
import com.dchip.cloudparking.api.model.po.MainControl;
import com.dchip.cloudparking.api.model.po.MainRoadway;
import com.dchip.cloudparking.api.model.po.MemberRule;
import com.dchip.cloudparking.api.model.po.Message;
import com.dchip.cloudparking.api.model.po.Order;
import com.dchip.cloudparking.api.model.po.Parking;
import com.dchip.cloudparking.api.model.po.ParkingInfo;
import com.dchip.cloudparking.api.model.po.ParkingRule;
import com.dchip.cloudparking.api.model.po.Roadway;
import com.dchip.cloudparking.api.model.po.SecondPlateName;
import com.dchip.cloudparking.api.model.po.Stock;
import com.dchip.cloudparking.api.model.po.User;
import com.dchip.cloudparking.api.model.po.UserCoupon;
import com.dchip.cloudparking.api.model.qpo.QAppointment;
import com.dchip.cloudparking.api.model.qpo.QCard;
import com.dchip.cloudparking.api.model.qpo.QCardLicensePlate;
import com.dchip.cloudparking.api.model.qpo.QCoupon;
import com.dchip.cloudparking.api.model.qpo.QFirstWhiteList;
import com.dchip.cloudparking.api.model.qpo.QFreeStandard;
import com.dchip.cloudparking.api.model.qpo.QGroundLock;
import com.dchip.cloudparking.api.model.qpo.QLicensePlateName;
import com.dchip.cloudparking.api.model.qpo.QLockCommond;
import com.dchip.cloudparking.api.model.qpo.QMainControl;
import com.dchip.cloudparking.api.model.qpo.QMainRoadway;
import com.dchip.cloudparking.api.model.qpo.QParking;
import com.dchip.cloudparking.api.model.qpo.QParkingInfo;
import com.dchip.cloudparking.api.model.qpo.QParkingRule;
import com.dchip.cloudparking.api.model.qpo.QParkingRuleRelation;
import com.dchip.cloudparking.api.model.qpo.QRoadway;
import com.dchip.cloudparking.api.model.qpo.QSecondPlateName;
import com.dchip.cloudparking.api.model.qpo.QStock;
import com.dchip.cloudparking.api.model.qpo.QUser;
import com.dchip.cloudparking.api.model.qpo.QUserCoupon;
import com.dchip.cloudparking.api.utils.DateUtil;
import com.dchip.cloudparking.api.utils.HttpSendUitl;
import com.dchip.cloudparking.api.utils.MessageUtil;
import com.dchip.cloudparking.api.utils.ParkingFeeUtil;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;
import com.dchip.cloudparking.api.utils.TimeUtils;
import com.dchip.cloudparking.api.utils.parkingfee.FeeModel;
import com.dchip.cloudparking.api.utils.parkingfee.FeeServiceDeduction;
import com.dchip.cloudparking.api.utils.parkingfee.FeeServiceKit;
import com.dchip.parking.api.util.jpush.JGuangPush;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class ParkingInfoServiceImp extends BaseService implements IParkingInfoService, ParkingConstant {

    @Autowired
    private IParkingInfoRepository parkingInfoRepository;

    @Autowired
    private IFreeStandardRepository freeStandardRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IMemberRuleRepository memberRuleRepository;

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private ICloneCarRepository cloneCarRepository;

    @Autowired
    private IMessageRepository messageRepository;

    @Autowired
    private ISecondPlateNameRepository secondPlateNameRepository;

    @Autowired
    private IParkingConcessionService parkingConcessionService;
    
    @Autowired
    private IGroundLockRepository groundLockRepository;
    
    @Autowired
    private IMainControlRepository mainControlRepository;
    
    @Autowired
    private IGroundLockService groundLockService;
    
    @Autowired
	private IUserCouponRepository userCouponRepository;
    
    static Log log = LogFactory.getLog(ParkingInfoServiceImp.class);

    /**
     * 显示用户停车信息，包括停车场名称、地址、车位区域、收费票准，当前产生的费用。 by 小梁
     */
    @SuppressWarnings("deprecation")
    public RetKit getParkingInfoByUserId(Integer userId) {
        // 更新一下过期的预约
        appointmentService.getLastAppointmentCount(userId.toString());
        try {
            List<Map<String, Object>> allParkingInfo = parkingInfoRepository.getParkingInfoByUserId(userId);
            List<Object> list = new ArrayList<>();
            for (Map<String, Object> parkingInfo : allParkingInfo) {
                Map<String, Object> map = new HashMap<>();
                Object parkDate = parkingInfo.get("park_date");
                Date d1 = (Date) parkDate;
                Date d2 = new Date();
                long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别
                long days = diff / (1000 * 60 * 60 * 24);

                long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
                String onlineTime;
                if (days > 0) {
                    onlineTime = "" + days + "天" + hours + "小时" + minutes + "分";
                } else {
                    onlineTime = "" + hours + "小时" + minutes + "分";
                }
                map.put("onlineTime", onlineTime);
                BigDecimal fee = calculateFree((Integer) parkingInfo.get("parkingId"), d1);
                map.put("fee", fee);
                map.put("userId", userId);
                long time = nextCalculateTime(Integer.parseInt(parkingInfo.get("parkingId").toString()), (Date) parkingInfo.get("park_date"));
                Date date = DateUtil.timestampToDate(time);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String circleDateStr = dateFormat.format(date);
                map.put("parDate", parkDate != null ? parkDate.toString().substring(0, 19) : "");
                map.put("nextBillingCycle", circleDateStr);
                map.put("currentDate", DateUtil.format(new Date()));
                map.put("intervalOfNextBillingCycle", DateUtil.calculateMinute(new Date(), new Date(circleDateStr)));
                map.put("allParkingInfo", parkingInfo);
                list.add(map);
            }
            return RetKit.okData(list);
        } catch (Exception e) {
            return RetKit.fail();
        }
    }

    /**
     * 改变车辆状态 锁车或者解锁 0-不锁车，1-锁车 by zyy
     */
    @Override
    public RetKit changeCarStatus(Integer isLock, Integer parkingInfoId) {
        try {
            QParkingInfo parkingInfoEntity = QParkingInfo.parkingInfo;
            Optional<ParkingInfo> parkingInfo = parkingInfoRepository.findById(parkingInfoId);
            if (!parkingInfo.isPresent()) {
                return RetKit.fail("不存在该停车信息");
            }
            ParkingInfo parkingInfos = parkingInfo.get();
            if (parkingInfos.getIsLock() == LockType.Lock.getTypeValue() && isLock == LockType.UnLock.getTypeValue()) {//解锁操作
                if (parkingInfos.getOutRoadWayId() != null) {//当存在outRoadWayId说明车已在出口,进行解锁后发送开闸指令
                    QRoadway roadWayEntity = QRoadway.roadway;
                    SimpleDateFormat parkingDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Roadway roadWayPoO = this.jpaQueryFactory.select(roadWayEntity).from(roadWayEntity)
                            .where(roadWayEntity.id.eq(parkingInfos.getOutRoadWayId())).fetchFirst();
                    RetKit rs = this.parking(roadWayPoO.getCameraMac(), parkingInfos.getLicensePlate(), "",
                            parkingDf.format(parkingInfos.getOutDate()), parkingInfos.getPlateType().toString(),
                            parkingInfos.getCarType().toString());
                    if (rs.success()) {//发送websocket
                        QMainRoadway mainRoadWayEntity = QMainRoadway.mainRoadway;
                        MainRoadway mainRoadWay = this.jpaQueryFactory.select(mainRoadWayEntity).from(mainRoadWayEntity).where(mainRoadWayEntity.roadwayId.eq(roadWayPoO.getId())).fetchFirst();
                        QMainControl mainControlEntity = QMainControl.mainControl;
                        MainControl mainControll = this.jpaQueryFactory.select(mainControlEntity).from(mainControlEntity).where(mainControlEntity.id.eq(mainRoadWay.getMainId())).fetchFirst();

                        CloseableHttpClient httpclient = HttpClients.createDefault();
                        StringBuffer url = new StringBuffer();
                        url.append(MessageUtil.loadMessage("parking.domain") + MessageUtil.loadMessage("parking.websocket.server.host"));
                        url.append("/socketController/sendMessage");
                        HttpPost post = new HttpPost(url.toString());
                        String parms = "controlMac=" + mainControll.getMac()
                                + "&cameraMac=" + roadWayPoO.getCameraMac()
                                + "&command=1";
                        StringEntity s = new StringEntity(parms);
                        s.setContentEncoding("UTF-8");
                        s.setContentType("application/x-www-form-urlencoded");//发送json数据需要设置contentType
                        post.setEntity(s);
                        HttpResponse response = httpclient.execute(post);
                        if (response.getStatusLine().getStatusCode() == 200) {
                            String conResult = EntityUtils.toString(response
                                    .getEntity());
                            JSONObject sobj = new JSONObject();
                            RetKit websocketRs = sobj.parseObject(conResult, RetKit.class);
                            if (websocketRs.getBoolean("success")) {
                                this.jpaQueryFactory.update(parkingInfoEntity).set(parkingInfoEntity.isLock, isLock)
                                        .set(parkingInfoEntity.status, BaseConstant.parkingInfoStatus.finishedStatus.getValue())
                                        .where(parkingInfoEntity.id.eq(parkingInfoId)).execute();
                                return RetKit.ok("解锁成功！");
                            } else {
                                return RetKit.fail("解锁失败！");
                            }
                        }
                    }
                }
                this.jpaQueryFactory.update(parkingInfoEntity).set(parkingInfoEntity.isLock, isLock)
                        .where(parkingInfoEntity.id.eq(parkingInfoId)).execute();
                return RetKit.ok("解锁成功！");
            } else {
                this.jpaQueryFactory.update(parkingInfoEntity).set(parkingInfoEntity.isLock, isLock)
                        .where(parkingInfoEntity.id.eq(parkingInfoId)).execute();
                return RetKit.ok("锁车成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetKit.fail();
        }
    }

    @SuppressWarnings("deprecation")
    public BigDecimal calculateFree(Integer parkingId, Date parkDate) {
        BigDecimal currentDayFree = new BigDecimal(0);
        FreeStandard freeStandard = freeStandardRepository.findFreeStandardByParkingId(parkingId);
        if (freeStandard == null) {
            // return RetKit.fail("请设置该停车场收费标准");
            return currentDayFree;
        }
        try {
            Date parkDateEndTime = new Date();// 停车当天的最大时间
            parkDateEndTime.setYear(parkDate.getYear());
            parkDateEndTime.setMonth(parkDate.getMonth());
            parkDateEndTime.setDate(parkDate.getDate());
            parkDateEndTime.setHours(24);
            parkDateEndTime.setMinutes(0);
            parkDateEndTime.setSeconds(0);
            if (new Date().getTime() > parkDateEndTime.getTime()) {// 停车超过凌晨24点重新计算
                int parkingFirstDayMinute = calculateMinute(parkDate, parkDateEndTime); // 停车超过24点
                int firstTotalHour = parkingFirstDayMinute / 60;// 停了多少小时
                int remainderMinute = parkingFirstDayMinute - firstTotalHour * 60; // 剩余的分钟
                if (remainderMinute >= 0) {
                    firstTotalHour += 1;
                }
                /**
                 * 停车当天
                 */
                if (parkingFirstDayMinute < freeStandard.getFreeTimeLength()) {// 还没超过免费时长
                    currentDayFree = new BigDecimal(0);
                } else if (freeStandard.getMostCost() != null
                        && new BigDecimal(firstTotalHour).multiply(freeStandard.getHourCost())
                        .doubleValue() < freeStandard.getMostCost().doubleValue()) {
                    currentDayFree = new BigDecimal(firstTotalHour).multiply(freeStandard.getHourCost());
                } else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
                    currentDayFree = new BigDecimal(firstTotalHour).multiply(freeStandard.getHourCost());
                } else {
                    currentDayFree = freeStandard.getMostCost();
                }
                /**
                 * 停车当天之后的时间
                 */
                int parkingFirstDayOtherMinute = calculateMinute(parkDateEndTime, new Date()); // 停车当天之后的时间
                int afterFirstDayDays = parkingFirstDayOtherMinute / 60 / 24;// 第一天停车之后 后面停了多少天
                int afterFirstDayMinutes = parkingFirstDayOtherMinute - afterFirstDayDays * 24 * 60; // 剩余的分钟
                if (afterFirstDayDays > 0) {
                    // 超出停车当日的天数
                    if (freeStandard.getMostCost() != null && new BigDecimal(24).multiply(freeStandard.getHourCost())
                            .doubleValue() < freeStandard.getMostCost().doubleValue()) {
                        currentDayFree = currentDayFree
                                .add(new BigDecimal(24 * afterFirstDayDays).multiply(freeStandard.getHourCost()));
                    } else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
                        currentDayFree = currentDayFree
                                .add(new BigDecimal(24 * afterFirstDayDays).multiply(freeStandard.getHourCost()));
                    } else {
                        currentDayFree = currentDayFree
                                .add(freeStandard.getMostCost().multiply(new BigDecimal(afterFirstDayDays)));
                    }
                    // 还要加上余数分钟
                    int endDayHour = afterFirstDayMinutes / 60;// 最后不到一天的小时数
                    int endDayHourMinute = afterFirstDayMinutes - endDayHour * 60;// 最后不到一天的小时数-剩余的余数分钟数
                    if (endDayHourMinute >= 0) {
                        endDayHour += 1;
                    }
                    // 超出停车当日的天数-但是剩余的不够一天的
                    if (freeStandard.getMostCost() != null
                            && new BigDecimal(endDayHour).multiply(freeStandard.getHourCost())
                            .doubleValue() < freeStandard.getMostCost().doubleValue()) {
                        currentDayFree = currentDayFree
                                .add(new BigDecimal(endDayHour).multiply(freeStandard.getHourCost()));
                    } else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
                        currentDayFree = currentDayFree
                                .add(new BigDecimal(endDayHour).multiply(freeStandard.getHourCost()));
                    } else {
                        currentDayFree = currentDayFree.add(freeStandard.getMostCost());
                    }
                } else {
                    int secondDayHours = parkingFirstDayOtherMinute / 60; // 只超了停车当天但是又不满一天
                    int secondDayMinutes = parkingFirstDayOtherMinute - secondDayHours * 60; // 只超了停车当天但是又不满一天-剩余的分钟的余数
                    if (secondDayMinutes >= 0) {
                        secondDayHours += 1;
                    }
                    if (freeStandard.getMostCost() != null
                            && new BigDecimal(secondDayHours).multiply(freeStandard.getHourCost())
                            .doubleValue() < freeStandard.getMostCost().doubleValue()) {
                        currentDayFree = currentDayFree
                                .add(new BigDecimal(secondDayHours).multiply(freeStandard.getHourCost()));
                    } else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
                        currentDayFree = currentDayFree
                                .add(new BigDecimal(secondDayHours).multiply(freeStandard.getHourCost()));
                    } else {
                        currentDayFree = currentDayFree.add(freeStandard.getMostCost());
                    }
                }
            } else {
                int parkingFirstDayMinute = calculateMinute(parkDate, new Date()); // 停车不超过停车当天
                int totalHour = parkingFirstDayMinute / 60;
                int remainderMinute = (int) parkingFirstDayMinute - totalHour * 60 - freeStandard.getFreeTimeLength(); // 剩余的分钟的余数
                if (remainderMinute >= 0) {
                    totalHour += 1;
                }
                if (parkingFirstDayMinute < freeStandard.getFreeTimeLength()) {// 还没超过免费时长
                    currentDayFree = new BigDecimal(0);
                } else if (freeStandard.getMostCost() != null
                        && new BigDecimal(totalHour).multiply(freeStandard.getHourCost()).doubleValue() < freeStandard
                        .getMostCost().doubleValue()) {
                    currentDayFree = new BigDecimal(totalHour).multiply(freeStandard.getHourCost());
                } else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
                    currentDayFree = new BigDecimal(totalHour).multiply(freeStandard.getHourCost());
                } else {
                    currentDayFree = freeStandard.getMostCost();
                }
            }
            return currentDayFree;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentDayFree;
    }

    /**
     * 计算两个时间相差多少分钟
     */
    private Integer calculateMinute(Date startDate, Date endDate) {
        int totalMinute = 0;
        long between = (endDate.getTime() - startDate.getTime()) / 1000;// 除以1000是为了转换成秒
        long min = between / 60;
        totalMinute = Math.toIntExact(min);

        return Math.toIntExact(totalMinute);
    }

    /**
     * 距离下一个计费周期
     */
    @SuppressWarnings("deprecation")
    @Override
    public long nextCalculateTime(Integer parkingId, Date parkDate) {
        FreeStandard freeStandard = freeStandardRepository.findFreeStandardByParkingId(parkingId);
        if (freeStandard == null) {
            return new Date().getTime();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parkDate);
        try {
            Date parkDateEndTime = new Date();// 停车当天的最大时间
            parkDateEndTime.setYear(parkDate.getYear());
            parkDateEndTime.setMonth(parkDate.getMonth());
            parkDateEndTime.setDate(parkDate.getDate());
            parkDateEndTime.setHours(24);
            parkDateEndTime.setMinutes(0);
            parkDateEndTime.setSeconds(0);

            if (new Date().getTime() > parkDateEndTime.getTime()) {// 停车超过凌晨24点重新计算
                int parkingFirstDayMinute = calculateMinute(parkDate, parkDateEndTime); // 停车超过24点
                int firstTotalHour = parkingFirstDayMinute / 60;// 停了多少小时
                int remainderMinute = parkingFirstDayMinute - firstTotalHour * 60; // 剩余的分钟
                if (remainderMinute >= 0) {
                    firstTotalHour += 1;
                }
                /**
                 * 停车当天之后的时间
                 */
                int parkingFirstDayOtherMinute = calculateMinute(parkDateEndTime, new Date()); // 停车当天之后的时间
                int afterFirstDayDays = parkingFirstDayOtherMinute / 60 / 24;// 第一天停车之后 后面停了多少天
                int afterFirstDayMinutes = parkingFirstDayOtherMinute - afterFirstDayDays * 24 * 60; // 剩余的分钟
                if (afterFirstDayDays >= 0) {
                    // 超出停车当日的天数
                    calendar.add(Calendar.DATE, afterFirstDayDays);
                    calendar.add(Calendar.DATE, 1);
                    // 还要加上余数分钟
                    int endDayHour = afterFirstDayMinutes / 60;// 最后不到一天的小时数
                    int endDayHourMinute = afterFirstDayMinutes - endDayHour * 60;// 最后不到一天的小时数-剩余的余数分钟数
                    if (endDayHourMinute >= 0) {
                        endDayHour += 1;
                    }
                    // 超出停车当日的天数-但是剩余的不够一天的
                    calendar.set(Calendar.HOUR_OF_DAY, 0); // 超过后的每一天都是从凌晨开始计算
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    if (freeStandard.getMostCost() != null
                            && new BigDecimal(endDayHour).multiply(freeStandard.getHourCost())
                            .doubleValue() < freeStandard.getMostCost().doubleValue()) {
                        calendar.add(Calendar.HOUR, endDayHour);
                    } else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
                        calendar.add(Calendar.HOUR, endDayHour);
                    } else {
                        calendar.set(Calendar.HOUR_OF_DAY, 24); // 如果已经超了当天的最高 则下一个计费周期是当天凌晨
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                    }
                } else {
                    int secondDayHours = parkingFirstDayOtherMinute / 60; // 只超了停车当天但是又不满一天
                    int secondDayMinutes = parkingFirstDayOtherMinute - secondDayHours * 60; // 只超了停车当天但是又不满一天-剩余的分钟的余数
                    if (secondDayMinutes >= 0) {
                        secondDayHours += 1;
                    }
                    if (freeStandard.getMostCost() != null
                            && new BigDecimal(secondDayHours).multiply(freeStandard.getHourCost())
                            .doubleValue() < freeStandard.getMostCost().doubleValue()) {
                        calendar.add(Calendar.HOUR, secondDayHours);
                    } else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
                        calendar.add(Calendar.HOUR, secondDayHours);
                    } else {
                        calendar.add(Calendar.DATE, 1);// 加一天
                        calendar.set(Calendar.HOUR_OF_DAY, 24);// 如果已经超了当天的最高 则下一个计费周期是当天凌晨
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                    }
                }
            } else {
                int parkingFirstDayMinute = calculateMinute(parkDate, new Date()); // 停车不超过停车当天
                int totalHour = parkingFirstDayMinute / 60;
                int remainderMinute = (int) parkingFirstDayMinute - totalHour * 60 - freeStandard.getFreeTimeLength(); // 剩余的分钟的余数
                if (remainderMinute >= 0) {
                    totalHour += 1;
                }
                if (parkingFirstDayMinute < freeStandard.getFreeTimeLength()) {// 还没超过免费时长
                    calendar.add(Calendar.MINUTE, freeStandard.getFreeTimeLength());
                } else if (freeStandard.getMostCost() != null
                        && new BigDecimal(totalHour).multiply(freeStandard.getHourCost()).doubleValue() < freeStandard
                        .getMostCost().doubleValue()) {
                    calendar.add(Calendar.HOUR, totalHour);
                    calendar.add(Calendar.MINUTE, freeStandard.getFreeTimeLength());
                } else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
                    calendar.add(Calendar.HOUR, totalHour);
                    calendar.add(Calendar.MINUTE, freeStandard.getFreeTimeLength());
                } else {
                    calendar.set(Calendar.HOUR_OF_DAY, 24);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                }
            }
        } catch (Exception e) {
        }

        return calendar.getTime().getTime();

    }

    /**
     * 未支付订单累积到了黑名单条件 则该用户变成黑名单 sjh
     */
    @Override
    public void checkBlack(Integer userId) {
        BigDecimal totalUnPayFree = new BigDecimal(0);// 未支付总钱数
        int unPayCount = 0;// 未支付次数
        int messageFlag = 0;
        User user = userRepository.findUserById(userId);
        MemberRule memberRule = memberRuleRepository.findUserMemberRule(userId);
        List<Map<String, Object>> unPayList = parkingInfoRepository.findUserUnPayRecord(userId);

        for (Map<String, Object> map : unPayList) {
            if (map.get("fee") != null) {
                totalUnPayFree = totalUnPayFree.add(new BigDecimal(map.get("fee").toString()));
            }
        }
        if (memberRule == null) {
            return;
        }
        if (unPayList != null && unPayList.size() > 0) {
            unPayCount = unPayList.size();
        }
        if (totalUnPayFree.compareTo(new BigDecimal(memberRule.getMaximumArrears())) > 1) {// 未支付钱数超过了会员等级限额
            user.setIsBlack(BaseConstant.UserIsBlack.BlacklistUser.getTypeValue());
            messageFlag = 1;
        }
        if (unPayCount > memberRule.getMaximumTimes()) {// 未支付次数超过了会员等级限额
            user.setIsBlack(BaseConstant.UserIsBlack.BlacklistUser.getTypeValue());
            messageFlag = 1;
        }
        if (messageFlag == 1) {
            //生成个人消息
            Message message = new Message();
            String uId = String.valueOf(userId);
            message.setTitle(MessageUtil.loadMessage("parking.black.user.notice"));
            message.setContent(MessageUtil.loadMessage("parking.black.user.messages"));
            message.setType(MessageType.Specify.getTypeValue());//指定用户
            message.setTarget(uId);
            message.setCreateTime(new Date());
            messageRepository.save(message);
        }
        userRepository.save(user);

    }


    /*
     * (non-Javadoc)
     *
     * @see
     * com.dchip.cloudparking.api.iService.IParkingInfoService#parkingIn(java.lang.
     * String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public RetKit parking(String mac, String carNum, String imgUrl, String parkingDate, String plateType, String carType) {
        Map<String, Object> rsData = new HashMap<String, Object>();
        try {
            // 根据mac 地址查找mac 对应的停车场
            // 判断是否是"会员用户"或"月卡用户"或"临时车"
            QRoadway road = QRoadway.roadway;//车道
            QUser user = QUser.user;
            QCard monthlyCard = QCard.card;//月卡
            QParking parking = QParking.parking;
            QFreeStandard freeStand = QFreeStandard.freeStandard;//收费标准
            QParkingInfo parkingInfoQuery = QParkingInfo.parkingInfo;
            QCardLicensePlate cardLinceseEntity = QCardLicensePlate.cardLicensePlate;//月卡车牌绑定表

            Roadway roadPO = this.jpaQueryFactory.select(road).from(road).where(road.cameraMac.eq(mac)).fetchFirst();
            if (roadPO == null) {
                return RetKit.fail("找不到车道信息");
            }
            //查询停车场
            Parking parkingPO = this.jpaQueryFactory.select(parking).from(parking)
                    .where(parking.id.eq(roadPO.getParkingId())).fetchFirst();

            if (roadPO == null || roadPO.getParkingId() == null) {
                return RetKit.fail(MessageUtil.loadMessage("parking.error"));
            }

            //停车场被禁用，车辆无法进入
            if (Integer.parseInt(parkingPO.getStatus()) == (ParkingStatus.DisableStatus.getTypeValue())) {
                return RetKit.fail(MessageUtil.loadMessage("parking.disabled.message"));
            }

            // 查找是否是会员用户：排除已注册未充值的会员用户
            User userPo = this.jpaQueryFactory.select(user).from(user)
                    .where(user.licensePlat.eq(carNum).and(user.memberId.ne(UserGrade.InitMember.getTypeValue())))
                    .fetchFirst();
            // 查找是否是月卡用户
            Card card = this.jpaQueryFactory.select(monthlyCard).from(monthlyCard, cardLinceseEntity)
                    .where(monthlyCard.id.eq(cardLinceseEntity.cardId)
                            .and(cardLinceseEntity.lisencePlate.eq(carNum).or(cardLinceseEntity.moreCarLisencePlate.like("%" + carNum + "%")))
                            .and(monthlyCard.parkingId.eq(parkingPO.getId())))
                    .fetchFirst();

            QFirstWhiteList firstWhiteList = QFirstWhiteList.firstWhiteList;
            QLicensePlateName licensePlateName = QLicensePlateName.licensePlateName1;
            QSecondPlateName secondPlateName = QSecondPlateName.secondPlateName;
            //查询白名单设置
            List<Integer> types = this.jpaQueryFactory.select(licensePlateName.typeNumber).from(licensePlateName)
                    .where(licensePlateName.id.in(this.jpaQueryFactory.select(firstWhiteList.licensePlateTypeId).from(firstWhiteList))
                            .and(licensePlateName.type.eq(roadPO.getCameraType()))).fetch();
            List<String> whitePlates = this.jpaQueryFactory.select(secondPlateName.licensePlate).from(secondPlateName)
                    .where(secondPlateName.parkingId.eq(roadPO.getParkingId())
                            .and(secondPlateName.status.eq(WhiteName.EnabledState.getTypeValue()))).fetch();

            if (roadPO.getInOutMarker() == RoadMarker.OUT.getTypeValue()) {//出口
                //查询停车信息
                FreeStandard freeStandPO = this.jpaQueryFactory.select(freeStand).from(freeStand)
                        .where(freeStand.id.eq(parkingPO.getFreeStandardId())).fetchFirst();
                ParkingInfo parkingInfoPO = this.jpaQueryFactory.select(parkingInfoQuery).from(parkingInfoQuery)
                        .where(parkingInfoQuery.parkCode.eq(parkingPO.getParkCode())
                                .and(parkingInfoQuery.licensePlate.eq(carNum)
                                        .and(parkingInfoQuery.status.eq(parkingInfoStatus.unfinishedStatus.getValue()))))
                        .orderBy(parkingInfoQuery.id.desc()).fetchFirst();

                // 判断是否有入场信息
                if (parkingInfoPO == null) {
                    String twoFiveNum = carNum.substring(1, 6);
                    int carNumLength = carNum.getBytes("UTF-8").length;
                    parkingInfoPO = parkingInfoRepository.findParkingInfoVagueTwoFive(parkingPO.getParkCode(), twoFiveNum, carNumLength);
                    if (parkingInfoPO == null) {
                        String threeFiveNum = carNum.substring(2, 7);
                        parkingInfoPO = parkingInfoRepository.findParkingInfoVagueThreeFive(parkingPO.getParkCode(), threeFiveNum, carNumLength);
                    }
                    if (parkingInfoPO == null) {
                        Map<String, Object> rsNewData = new HashMap<String, Object>();
                        //只支持月卡，不允许临时车
                        if (card != null) {
                            if (userPo != null) {
                                rsNewData.put("isMember", BaseConstant.MemberShip.MemberAndCard.getTypeValue());
                            } else {
                                rsNewData.put("isMember", BaseConstant.MemberShip.MonthCard.getTypeValue());
                            }
                        } else if (userPo != null) {
                            rsNewData.put("isMember", BaseConstant.MemberShip.Member.getTypeValue());
                        } else {
                            rsNewData.put("isMember", BaseConstant.MemberShip.UnMember.getTypeValue());
                        }
						/*if(parkingPO.getIsSupportCard() == ParkingConstant.IsSupportCard.SupportYes.getTypeValue()) {
							rsNewData.put("isAuthorization", ParkingConstant.IsAuthorization.Open.getTypeValue());
							
							rsNewData.put("indentifyMessage", "一路顺风 欢迎再次光临");
							return RetKit.okData(rsNewData);
						}*/
                        //所有车都可以出场
                        rsNewData.put("isAuthorization", ParkingConstant.IsAuthorization.Open.getTypeValue());
                        //rsNewData.put("indentifyMessage", MessageUtil.loadMessage("parking.error.voice"));
                        rsNewData.put("indentifyMessage", "一路顺风 欢迎再次光临");
                        return RetKit.okData(rsNewData);

                    } else {
                        // 查找是否是会员用户
                        userPo = this.jpaQueryFactory.select(user).from(user)
                                .where(user.licensePlat.eq(parkingInfoPO.getLicensePlate()).and(user.memberId.ne(UserGrade.InitMember.getTypeValue())))
                                .fetchFirst();
                        // 查找是否是月卡用户
                        card = this.jpaQueryFactory.select(monthlyCard).from(monthlyCard, cardLinceseEntity)
                                .where(monthlyCard.id.eq(cardLinceseEntity.cardId)
                                        .and(cardLinceseEntity.lisencePlate.eq(parkingInfoPO.getLicensePlate()))
                                        .and(monthlyCard.parkingId.eq(parkingPO.getId())))
                                .fetchFirst();
                    }
                }

                //判断是否锁车
                if (parkingInfoPO.getIsLock() == LockType.Lock.getTypeValue()) {
                    this.jpaQueryFactory.update(parkingInfoQuery).set(parkingInfoQuery.outRoadWayId, roadPO.getId())
                            .set(parkingInfoQuery.outDate, new Date())
                            .where(parkingInfoQuery.id.eq(parkingInfoPO.getId())).execute();
                    rsData.put("isLock", true);
                    rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.error.islock.message"));
                    return RetKit.okData(rsData);
                }


                Appointment appointmentPO = null;
                if (parkingInfoPO.getAppointmentId() != null) {//存在预约
                    QAppointment appointmentQuery = QAppointment.appointment;
                    appointmentPO = this.jpaQueryFactory.select(appointmentQuery).from(appointmentQuery)
                            .where(appointmentQuery.id.eq(parkingInfoPO.getAppointmentId())
                                    .and(appointmentQuery.status.eq(AppointmentStatus.Normal.getTypeValue())))
                            .fetchFirst();
                }
                
                //固定车出场按白名单规则
                if (card != null) {
					if (card.getIsFixedSpace().equals(BaseConstant.IsFixedSpaceStatus.FixedSpace.getTypeValue())) {
						return this.parkingOutAsWhiteName(parkingInfoQuery, parkingDate, parkingInfoPO.getParkDate(), parkingInfoPO.getId(), carNum, roadPO.getId());
					}
				}
                
                //会员租用了车位按白名单出场
                if (userPo != null) {
                	com.dchip.cloudparking.api.model.po.ParkingConcession parkingConcession = parkingConcessionService.findAcceptMsg(parkingPO.getId(), carNum, userPo);
                	if(parkingConcession != null) {
                		return this.parkingOutAsWhiteName(parkingInfoQuery, parkingDate, parkingInfoPO.getParkDate(), parkingInfoPO.getId(), carNum, roadPO.getId());
                	}
				}
                
                //临时车使用支付宝支付后直接出场
                if(card == null && userPo == null) {
                	Order findOrderByInfoAndType = orderRepository.findOrderByInfoAndType(parkingInfoPO.getId());
                	if (findOrderByInfoAndType != null) {
                		if (findOrderByInfoAndType.getType() == BaseConstant.OrderType.AliPay.getTypeValue() && findOrderByInfoAndType.getStatus() ==  BaseConstant.OrderStatus.AdvanceAlreadyPay.getTypeValue()) {
                    		return this.parkingOutAsWhiteName(parkingInfoQuery, parkingDate, parkingInfoPO.getParkDate(), parkingInfoPO.getId(), carNum, roadPO.getId());
    					}
					}                	
                }

                //军车或白名单，直接放行
                if ((StrKit.notBlank(plateType) && types.contains(Integer.parseInt(plateType))) || whitePlates.contains(carNum)) {
                    SecondPlateName secondPlateNameOne = secondPlateNameRepository.getSecondPlateNameByLicensePlate(carNum);
                    if (secondPlateNameOne != null) {//不为空的时候，判断是否为临时白名单，是的话就删除该白名单
                        if (secondPlateNameOne.getTmpFlag() == BaseConstant.WhiteListFlag.EnabledState.getTypeValue()) {
                            secondPlateNameRepository.deleteById(secondPlateNameOne.getId());
                        }
                    }
                    return this.parkingOutAsWhiteName(parkingInfoQuery, parkingDate, parkingInfoPO.getParkDate(), parkingInfoPO.getId(), carNum, roadPO.getId());
                }

                QParkingRule ruleEntity = QParkingRule.parkingRule;
                QParkingRuleRelation ruleRelationEntity = QParkingRuleRelation.parkingRuleRelation;
                ParkingRule rule = this.jpaQueryFactory.select(ruleEntity).from(ruleEntity, ruleRelationEntity)
                        .where(ruleEntity.id.eq(ruleRelationEntity.parkingRuleId).and(ruleRelationEntity.parkingId
                                .eq(parkingPO.getId()).and(ruleEntity.ruleType.eq(RoadMarker.OUT.getTypeValue()))))//出场规则
                        .fetchFirst();
                return this.parkingOut(userPo, card, rule, appointmentPO, carNum, parkingInfoPO, parkingDate, freeStandPO, roadPO.getId(), plateType, carType, parkingInfoPO.getParkCode(), parkingPO.getMax(), parkingPO.getId());
//				return this.parkingOut(userPo, card, appointmentPO, carNum, parkingInfoPO, parkingDate, freeStandPO,roadPO.getId(), plateType, carType, parkingInfoPO.getParkCode(), parkingPO.getMax(), parkingPO.getId());

            } else if (roadPO.getInOutMarker() == RoadMarker.IN.getTypeValue()) { //入口

                //入场时判断停车场是否还有余位
                QStock qStock = QStock.stock;
                Stock parkingStock = this.jpaQueryFactory.select(qStock).from(qStock)
                        .where(qStock.parkingId.eq(roadPO.getParkingId())).fetchFirst();
                if (parkingStock.getTotalCount() == parkingStock.getEnterCount()) {//没有空余车位
                    return RetKit.fail(MessageUtil.loadMessage("parking.space.full.message"));
                }

                QAppointment query = QAppointment.appointment;
                Appointment appointment = this.jpaQueryFactory.select(query).from(query)
                        .where(query.status.eq(AppointmentStatus.Normal.getTypeValue())
                                .and(query.parkingId.eq(roadPO.getParkingId())).and(query.licensePlate.eq(carNum)))
                        .fetchFirst();
                QParkingRule ruleEntity = QParkingRule.parkingRule;
                QParkingRuleRelation ruleRelationEntity = QParkingRuleRelation.parkingRuleRelation;
                ParkingRule rule = this.jpaQueryFactory.select(ruleEntity).from(ruleEntity, ruleRelationEntity)
                        .where(ruleEntity.id.eq(ruleRelationEntity.parkingRuleId).and(ruleRelationEntity.parkingId
                                .eq(parkingPO.getId()).and(ruleEntity.ruleType.eq(RoadMarker.IN.getTypeValue()))))
                        .fetchFirst();
                //白名单入场
                if (types.contains(Integer.parseInt(plateType)) || whitePlates.contains(carNum)) {
                    return this.whiteCarParkingIn(userPo, card, appointment, rule, carNum, parkingDate, roadPO.getId(), plateType, carType, parkingPO.getParkCode(), parkingPO.getMax());
                }

                //只支持月卡，不允许临时车
                if (parkingPO.getIsSupportCard() == ParkingConstant.IsSupportCard.SupportYes.getTypeValue()) {

                    Map<String, Object> rsNewData = new HashMap<String, Object>();
                    rsNewData.put("isMember", BaseConstant.MemberShip.UnMember.getTypeValue());
                    rsNewData.put("isAuthorization", ParkingConstant.IsAuthorization.Close.getTypeValue());
                    if (card == null && userPo == null) {
                        rsNewData.put("indentifyMessage", "停车场禁止临时车辆入场");
                        return RetKit.okData(rsNewData);
                    }

                    if (rule != null && rule.getInRule() == ParkingInRule.InRuleAsTemp.getTypeValue()) {
                        QParkingInfo parkingInfoEntity = QParkingInfo.parkingInfo;
                        long hasParkingCount = this.jpaQueryFactory.select(parkingInfoEntity.licensePlate.countDistinct()).from(parkingInfoEntity)
                                .where(parkingInfoEntity.licensePlate.in(carNum)
                                        .and(parkingInfoEntity.status.eq(parkingInfoStatus.unfinishedStatus.getValue()))).fetchCount();
                        if (hasParkingCount >= parkingPO.getMax()) {
                            rsNewData.put("indentifyMessage", "月卡车已超限 禁止入场");
                            return RetKit.okData(rsNewData);
                        }
                    }
                }

                //判断是否租赁了该停车场车位，租赁车相当于白名单入场
                if (userPo != null) {
                	com.dchip.cloudparking.api.model.po.ParkingConcession parkingConcession = parkingConcessionService.findAcceptMsg(parkingPO.getId(), carNum, userPo);
                    if (parkingConcession != null) {
                    	 return this.whiteCarParkingIn(userPo, card, appointment, rule, carNum, parkingDate, roadPO.getId(), plateType, carType, parkingPO.getParkCode(), parkingPO.getMax());
                        //已租让车辆，禁止入场
                        /*if (parkingConcession.getParkingId() == parkingPO.getId()) {
                            return RetKit.fail(MessageUtil.loadMessage("parking.concession.rentout"));
                        } else {
                            return this.whiteCarParkingIn(userPo, card, appointment, rule, carNum, parkingDate, roadPO.getId(), plateType, carType, parkingPO.getParkCode(), parkingPO.getMax());
                        }*/
                    }
				}
                
                return this.parkingIn(userPo, card, appointment, rule, carNum, parkingDate, roadPO.getId(), plateType, carType, parkingPO.getParkCode(), parkingPO.getMax());
            } else {//设备信息丢失
                return RetKit.fail(MessageUtil.loadMessage("parking.error"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetKit.fail();
        }
    }

    /**
     * 白名单入场操作
     */
    private RetKit whiteCarParkingIn(User user, Card card, Appointment appointment, ParkingRule rule, String carNum,
                                     String parkingDate, Integer roadId, String plateType, String carType, Integer parkCode, Integer max) throws ParseException {
        SimpleDateFormat parkingDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> rsData = new HashMap<String, Object>();
        Date nowDate = new Date();
        ParkingInfo parkingInfoNew = parkingInfoRepository.findParkingInfoByBetweenFirstDate(carNum, getCurrentTime(), nowDate);
        if (parkingInfoNew != null) {
            rsData.put("isMember", BaseConstant.MemberShip.SpecialMember.getTypeValue());
            rsData.put("isAuthorization", ParkingConstant.IsAuthorization.Open.getTypeValue());

            return RetKit.okData(rsData);
        }
        ParkingInfo parkingInfo = new ParkingInfo();
        if (user != null) {
            parkingInfo.setUserId(user.getId());
        }
        parkingInfo.setIsLock(LockType.UnLock.getTypeValue());
        parkingInfo.setLicensePlate(carNum);
        parkingInfo.setParkDate(parkingDateFormat.parse(parkingDate));
        parkingInfo.setParkCode(parkCode);
        parkingInfo.setStatus(BaseConstant.parkingInfoStatus.unfinishedStatus.getValue());
        if (appointment != null) { //预约车辆
            parkingInfo.setAppointmentId(appointment.getId());
            rsData.put("isAppointment", true);
        }

        parkingInfo.setInRoadWayId(roadId);

        if (!StringUtils.isEmpty(plateType)) {
            parkingInfo.setPlateType(Integer.valueOf(plateType));
        }
        if (!StringUtils.isEmpty(carType)) {
            parkingInfo.setCarType(Integer.valueOf(carType));
        }
        parkingInfo = parkingInfoRepository.save(parkingInfo);
        rsData.put("isMember", BaseConstant.MemberShip.SpecialMember.getTypeValue());
        rsData.put("isAuthorization", ParkingConstant.IsAuthorization.Open.getTypeValue());

        return RetKit.okData(rsData);
    }


    private RetKit parkingOut(User user, Card card, ParkingRule rule, Appointment appointment, String carNum, ParkingInfo parkingInfo,
                              String parkingDate, FreeStandard freeStandard, Integer roadId, String plateType, String carType,
                              Integer parkCode, Integer max, Integer parkingId) {
        try {
            // 月卡
            if (card != null) {
                return this.parkingOutAsCard(user, card, rule, appointment, carNum, parkingInfo, parkingDate, freeStandard,
                        max, roadId);
            } else if (user != null) {//会员
                return this.parkingOutAsMember(parkingDate, parkingInfo, freeStandard, card, user, roadId);
            } else {//临时用户
                return this.tempParkingOut(parkingInfo.getId(), roadId, WeiXinScanType.PlateOut.getTypeValue(),
                        parkingDate, card, carNum, parkingInfo.getParkDate(), freeStandard);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetKit.fail();
        }
    }

    /**
     * 卡用户出场
     *
     * @param user
     * @param card
     * @param appointment
     * @param carNum
     * @param parkingInfo
     * @param parkingOutDate
     * @param freeStandard
     * @param max
     * @param roadId
     * @return
     */
    private RetKit parkingOutAsCard(User user, Card card, ParkingRule rule, Appointment appointment, String carNum,
                                    ParkingInfo parkingInfo, String parkingOutDate, FreeStandard freeStandard, Integer max, Integer roadId) {
        try {
//			if(card.getType() == CardType.Month.getTypeValue()) {
//				return this.parkingOutAsCardMonth(parkingOutDate, card, roadId, parkingInfo, user, max, carNum, freeStandard);
//			}
//			if(card.getType() == CardType.WorkDay.getTypeValue()) {
//				return this.parkingOutAsCardDay(parkingOutDate, card, roadId, parkingInfo, user, max, carNum, freeStandard);
//			}
//			if(card.getType() == CardType.WorkTime.getTypeValue()) {
//				return this.parkingOutAsCardDayTime(parkingOutDate, card, roadId, parkingInfo, user, max, carNum, freeStandard);
//			}
        	if (card != null && card.getIsFixedSpace().equals(0)) {//非固定车位
        		if (rule.getInRule() == ParkingConstant.ParkingRuleType.Month.getTypeValue()) {
        			card.setType(rule.getInRule());
        			return this.parkingOutAsCardMonth(parkingOutDate, card, roadId, parkingInfo, user, max, carNum, freeStandard);
        		}
        		if (rule.getInRule() == ParkingConstant.ParkingRuleType.WorkDay.getTypeValue()) {
        			card.setType(rule.getInRule());
        			return this.parkingOutAsCardDay(parkingOutDate, card, rule, roadId, parkingInfo, user, max, carNum, freeStandard);
        		}
        		if (rule.getInRule() == ParkingConstant.ParkingRuleType.WorkTime.getTypeValue()) {
        			card.setType(rule.getInRule());
        			return this.parkingOutAsCardDayTime(parkingOutDate, card, roadId, parkingInfo, user, max, carNum, freeStandard);
        		}
			}else {//固定车位
				return this.parkingOutAsFixedSpace(parkingOutDate, card, roadId, parkingInfo, user, max, carNum, freeStandard);
			}
        } catch (Exception e) {
            e.printStackTrace();
            return RetKit.fail();
        }

        return RetKit.fail();
    }

    private RetKit parkingOutAsFixedSpace(String parkingOutDate, Card card, Integer roadId, ParkingInfo parkingInfo,
			User user, Integer max, String carNum, FreeStandard freeStandard) {
    	try {
	    	//更新出场信息
	    	SimpleDateFormat parkingDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.updatePakingOutInfo(parkingDf.parse(parkingOutDate), parkingInfo.getId(), roadId);
	    	Map<String, Object> rsData = new HashMap<String, Object>();
	    	rsData.put("isMember", MemberShip.MonthCard.getTypeValue());
	    	rsData.put("isAuthorization", IsAuthorization.Open.getTypeValue());
	    	rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.out.greeting.message",card.getCarOwnerName()));
	        rsData.put("parkingLen", ParkingFeeUtil.getParkingLen(parkingDf.parse(parkingOutDate).getTime() - parkingInfo.getParkDate().getTime()));
	    	QGroundLock qGroundLock = QGroundLock.groundLock;
	    	GroundLock gLock = this.jpaQueryFactory.select(qGroundLock)
	    			.from(qGroundLock)
	    			.where(qGroundLock.licensePlate.eq(carNum)).fetchFirst();
	    	if (gLock != null) {//多线程的使用
		    	QLockCommond qCommond = QLockCommond.lockCommond;
		    	LockCommond lCommond = this.jpaQueryFactory.select(qCommond)
		    			.from(qCommond)
		    			.where(qCommond.groundLockId.eq(gLock.getId()).and(qCommond.type.eq(101))).fetchFirst();
	    		Thread thread = new SubThread(gLock.getGroundUid(), "101",lCommond.getCommond(), groundLockRepository, mainControlRepository);
	    		thread.start();
			}
	    	return RetKit.okData(rsData);
    	} catch (ParseException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		return RetKit.fail();
    	}
	}

	/**
     * 更新出场信息
     *
     * @param outDate
     * @param parkingInfoId
     * @param outWayId
     */
    private void updatePakingOutInfo(Date outDate, Integer parkingInfoId, Integer outWayId) {
        QParkingInfo parkingInfoEntity = QParkingInfo.parkingInfo;
        this.jpaQueryFactory.update(parkingInfoEntity)
                .set(parkingInfoEntity.outDate, outDate)
                .set(parkingInfoEntity.outRoadWayId, outWayId)
                .set(parkingInfoEntity.status, parkingInfoStatus.finishedStatus.getValue())
                .where(parkingInfoEntity.id.eq(parkingInfoId)).execute();
    }

    /**
     * 更新临时车出场信息
     *
     * @param outDate
     * @param parkingInfoId
     * @param outWayId
     */
    private void updateTmpPakingOutInfo(Date outDate, Integer parkingInfoId, Integer outWayId) {
        QParkingInfo parkingInfoEntity = QParkingInfo.parkingInfo;
        this.jpaQueryFactory.update(parkingInfoEntity)
                .set(parkingInfoEntity.outDate, outDate)
                .set(parkingInfoEntity.outRoadWayId, outWayId)
                .where(parkingInfoEntity.id.eq(parkingInfoId)).execute();
    }

    /**
     * 会员出场
     *
     * @param outDate
     * @param parkingInfo
     * @param freeStandard
     * @param card
     * @param user
     * @param roadId
     * @return
     */
    @SuppressWarnings("deprecation")
	private RetKit parkingOutAsMember(String outDate, ParkingInfo parkingInfo, FreeStandard freeStandard, Card card, User user, Integer roadId) {
        SimpleDateFormat parkingDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> rsData = new HashMap<String, Object>();
        try {
        	QParking qParking = QParking.parking;
        	QParkingInfo qParkingInfo = QParkingInfo.parkingInfo;
        	//查找停车场的"免费时长是否加入收费"字段的值
        	Integer freeTimePayFlag = this.jpaQueryFactory.select(qParking.freeTimePayFlag).from(qParking)
        			.leftJoin(qParkingInfo).on(qParkingInfo.parkCode.eq(qParking.parkCode))
        			.where(qParkingInfo.id.eq(parkingInfo.getId())).fetchFirst();
            //不存在预约的费用计算
            Long time = parkingDf.parse(outDate).getTime() - parkingInfo.getParkDate().getTime();
            Map<String, Object> feeAndParkingTime = ParkingFeeUtil.getParkingFee(time,freeStandard.getHourCost(), 
            		freeStandard.getMostCost(),freeStandard.getFreeTimeLength());
            //存在预约的费用计算
            if (parkingInfo.getAppointmentId() != null) {
                rsData.put("isAppointment", true);
                QAppointment appointmentQuery = QAppointment.appointment;
                Appointment appointmentPO = this.jpaQueryFactory.select(appointmentQuery).from(appointmentQuery)
                        .where(appointmentQuery.id.eq(parkingInfo.getAppointmentId())
                                .and(appointmentQuery.status.eq(AppointmentStatus.NormalParking.getTypeValue())))
                        .fetchFirst();

                time = parkingDf.parse(outDate).getTime() - appointmentPO.getCreateTime().getTime();
                // 如果出场时间在免费时长内出场，只收取预约费用
                if (TimeUtils.getMillScondByMunites((parkingDf.parse(outDate).getTime()
                        - parkingInfo.getParkDate().getTime())) <= freeStandard.getFreeTimeLength()) {
                    time = appointmentPO.getAppointStartTime().getTime()
                            - appointmentPO.getCreateTime().getTime();
                }
                feeAndParkingTime = ParkingFeeUtil.getParkingFee(time,freeStandard.getHourCost(), 
                		freeStandard.getMostCost(),freeStandard.getFreeTimeLength());
            }
            rsData.put("isAuthorization", IsAuthorization.Open.getTypeValue());//开闸
            if (card != null) {
                rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.card.expire.message",
                        card.getCarOwnerName()));
                rsData.put("isMember", MemberShip.MemberAndCard.getTypeValue());//既是月卡用户也是会员用户
            } else {
                rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.out.greeting.message",
                        parkingInfo.getLicensePlate() + "会员"));
                rsData.put("isMember", MemberShip.Member.getTypeValue());//会员用户
            }
            //停车时长:出库时间-入库时间
            rsData.put("parkingLen", ParkingFeeUtil.getParkingLen(parkingDf.parse(outDate).getTime() - parkingInfo.getParkDate().getTime()));
            //生成订单

          //停车费用：用户同时拥有抵扣券和优惠券时，优先用抵扣券，当总费用减去抵扣券还是大于0的时候才用优惠券
            
            FeeModel feeModel = new FeeModel();
            feeModel.setHourCost(freeStandard.getHourCost().doubleValue());
            if (freeTimePayFlag != null && freeTimePayFlag == 1) {//免费时长加入收费
				feeModel.setFreeMinute(0);
			}else {
				feeModel.setFreeMinute(freeStandard.getFreeTimeLength() );
			}
            feeModel.setMostCostDaily(freeStandard.getMostCost().doubleValue());
            FeeServiceKit memberFeeService = new FeeServiceKit(feeModel, DateUtil.getDateTimeStr(parkingInfo.getParkDate()), outDate, null, null);
            double total = memberFeeService.getResultFee();
            /* 2018/12/06 start */
            //抵扣卷
            Deduction deduction = FeeServiceDeduction.getDeduction(jpaQueryFactory, parkingInfo.getLicensePlate(), outDate);
            int minusHour = 0;
            if (deduction != null) {
                minusHour = deduction.getHourNum();
              //停车费用：总费用-抵扣值
                total = total - minusHour * feeModel.getHourCost();
            }
            
/*            if (total > 0) {
            	 //优惠券
                QCoupon qCoupon = QCoupon.coupon;
                QUserCoupon qUserCoupon = QUserCoupon.userCoupon;
                Tuple tupleQuery = this.jpaQueryFactory.select(qCoupon.count,qCoupon.id,qCoupon.partDeduction,qCoupon.deductionType,qCoupon.endNum,qUserCoupon.endNum,qUserCoupon.status)
                		.from(qUserCoupon).leftJoin(qCoupon).on(qCoupon.id.eq(qUserCoupon.couponId))
                		.where(qUserCoupon.status.eq(BaseConstant.UserCouponStatus.UnUse.getTypeValue()).and(qUserCoupon.userId.eq(user.getId()))
                				.and(qCoupon.endTime.after(getCurrentTime()).or(qCoupon.endTime.isNull()))).limit(1).fetchFirst();
                Integer couponCount = 0;
                if (tupleQuery != null) {
                    if (tupleQuery.get(qCoupon.deductionType)  == BaseConstant.DeductionType.AllDeduction.getTypeValue()) {//全抵扣
                    	if (tupleQuery != null ) {
                        	couponCount = tupleQuery.get(qCoupon.count);
                        	UserCoupon userCoupon = userCouponRepository.findUserCouponByUserIdAndCounponIdAndStatus(user.getId(), tupleQuery.get(qCoupon.id));
                        	String userTime = parkingDf.format(new Date()); 
                        	Date userDate = parkingDf.parse(userTime);
                        	userCoupon.setUseTime(userDate);
                        	userCoupon.setStatus(BaseConstant.UserCouponStatus.AlreadyUse.getTypeValue());
                        	userCouponRepository.save(userCoupon);
            			}
        			}else {//多次抵扣
        				if (tupleQuery != null) {
        					if(!tupleQuery.get(qUserCoupon.endNum).equals(0)) {
        					couponCount = tupleQuery.get(qCoupon.partDeduction);
        					this.jpaQueryFactory.update(qUserCoupon)
        					.set(qUserCoupon.endNum, qUserCoupon.endNum.subtract(1))
        					.set(qUserCoupon.useTime, getCurrentTime())
        					.where(qUserCoupon.userId.eq(user.getId())
            						.and(qUserCoupon.couponId.eq(tupleQuery.get(qCoupon.id))))
            				.execute();
        					//在上方那个方法无法保存状态为0
        					UserCoupon userCoupon = userCouponRepository.findUserCouponByUserIdAndCounponId(user.getId(),tupleQuery.get(qCoupon.id));
        					userCoupon.setStatus(BaseConstant.UserCouponStatus.UnUse.getTypeValue());
        					userCouponRepository.save(userCoupon);
        					}else {
        						couponCount = 0;
        						this.jpaQueryFactory.update(qUserCoupon)
        						.set(qUserCoupon.status, BaseConstant.UserCouponStatus.AlreadyUse.getTypeValue())
        						.set(qUserCoupon.useTime, getCurrentTime())
        						.where(qUserCoupon.userId.eq(user.getId())
        	    						.and(qUserCoupon.couponId.eq(tupleQuery.get(qCoupon.id))))
        	    				.execute();
        					}
        				}
        			}
                  //停车费用：总费用-优惠券
                   total = total - couponCount;
    			}
			}*/
           
            if (total >= 0) {
                feeAndParkingTime.put("total", total);
            } else {
                feeAndParkingTime.put("total", 0);
            }
            FeeServiceDeduction.disableDeduction(jpaQueryFactory, user.getId(), deduction);
            /* 2018/12/06 end */
            this.generateOrder(user.getId(), new BigDecimal(feeAndParkingTime.get("total").toString()), parkingInfo.getId(), time, user.getBalance());
//			this.generateOrder(user.getId(), ParkingFeeUtil.getNewParkingFee(parkingDf.parse(outDate), parkingInfo, freeStandard),parkingInfo.getId(), time, user.getBalance());

            // 写入出场时间
            this.updatePakingOutInfo(parkingDf.parse(outDate), parkingInfo.getId(), roadId);
            //检查是否黑名单
            if (user != null) {
                checkBlack(user.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetKit.fail();
        }
        //通知客户端车已出场
        if (user != null && user.getRegistrationId() != null) {
            this.pushMessageToClient(user, parkingInfo.getLicensePlate(), BaseConstant.JpushType.CarOut.getTypeValue());
        }
        return RetKit.okData(rsData);
    }

    /**
     * 按正常天月卡
     *
     * @param outDate
     * @param card
     * @param roadId
     * @param parkingInfo
     * @param user
     * @param max
     * @param carNum
     * @param freeStandard
     * @return
     */
    private RetKit parkingOutAsCardMonth(String outDate, Card card, Integer roadId, ParkingInfo parkingInfo, User user,
                                         Integer max, String carNum, FreeStandard freeStandard) {
        Map<String, Object> rsData = new HashMap<String, Object>();
        SimpleDateFormat parkingDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dayFomat = new SimpleDateFormat("yyyy-MM-dd");
        QParkingInfo parkingInfoEntity = QParkingInfo.parkingInfo;
        QCardLicensePlate cardLicensePlateEntity = QCardLicensePlate.cardLicensePlate;
        GregorianCalendar ca = new GregorianCalendar();

        try {
            //一张月卡绑定多张车牌：查找出绑定在该月卡下的所有车牌
            List<String> carNums = this.jpaQueryFactory.select(cardLicensePlateEntity.lisencePlate)
                    .from(cardLicensePlateEntity).where(cardLicensePlateEntity.cardId.eq(card.getId())).fetch();
            //按入场顺序查找指定限额内的车牌
            List<String> admits = this.jpaQueryFactory.select(parkingInfoEntity.licensePlate)
                    .from(parkingInfoEntity)
                    .where(parkingInfoEntity.licensePlate.in(carNums)
                            .and(parkingInfoEntity.status.eq(parkingInfoStatus.unfinishedStatus.getValue())))
                    .limit(max).fetch();

            if (!admits.contains(carNum) || card.getExpiryTime().before(parkingInfo.getParkDate())) {//限额外的车牌或入场前已过期（完全过期）
                if (user != null) {//会员
                    return this.parkingOutAsMember(outDate, parkingInfo, freeStandard, card, user, roadId);
                } else {//临时
                    return this.tempParkingOut(parkingInfo.getId(), roadId, WeiXinScanType.PlateOut.getTypeValue(), outDate, card, carNum, parkingInfo.getParkDate(), freeStandard);
                }
            } else if (parkingInfo.getParkDate().before(card.getExpiryTime())
                    && card.getExpiryTime().before(parkingDf.parse(outDate))) {//入场时未过期出场时已过期
                if (user != null) {//会员
                    if (parkingInfo.getAppointmentId() != null) {//有预约
                        rsData.put("isAppointment", true);
                        QAppointment appointmentEntity = QAppointment.appointment;
                        Appointment appointmentPO = this.jpaQueryFactory.select(appointmentEntity).from(appointmentEntity)
                                .where(appointmentEntity.id.eq(parkingInfo.getAppointmentId())).fetchFirst();
                        //计算时间：（预约时间-预约提交时间）+（出场时间-截止日期后的第一天）
                        ca.setTime(card.getExpiryTime());
                        ca.add(GregorianCalendar.DATE, 1);
                        Long time = (appointmentPO.getAppointStartTime().getTime() - appointmentPO.getCreateTime().getTime())
                                + (parkingDf.parse(outDate).getTime() - ca.getTime().getTime());
                        Long parkingTimeLen = parkingDf.parse(outDate).getTime() - ca.getTime().getTime();
                        //生成订单
                        Map<String, Object> feeAndParkingTime = ParkingFeeUtil.getParkingFee(time,
                                freeStandard.getHourCost(), freeStandard.getMostCost(),
                                freeStandard.getFreeTimeLength());

                        /* 2018/12/06 start */
                        Deduction deduction = FeeServiceDeduction.getDeduction(jpaQueryFactory, carNum, outDate);
                        int minusHour = 0;
                        if (deduction != null) {
                            minusHour = deduction.getHourNum();
                        }
                        FeeModel feeModel = new FeeModel();
                        feeModel.setHourCost(freeStandard.getHourCost().doubleValue());
                        feeModel.setMostCostDaily(freeStandard.getMostCost().doubleValue());
                        feeModel.setFreeMinute(freeStandard.getFreeTimeLength() );
                        FeeServiceKit feeServiceKit = new FeeServiceKit(
                                feeModel,
                                DateUtil.getDateTimeStr(appointmentPO.getCreateTime()),
                                outDate,
                                DateUtil.getDateTimeStr(card.getExpiryTime()),
                                CardType.Month.getTypeValue());
                        double total = feeServiceKit.getResultFee() - minusHour * feeModel.getHourCost();
                        if (total >= 0) {
                            feeAndParkingTime.put("total", total);
                        } else {
                            feeAndParkingTime.put("total", 0);
                        }
                        FeeServiceDeduction.disableDeduction(jpaQueryFactory, user.getId(), deduction);
                        /* 2018/12/06 end */

                        // 如果出场时间在免费时长内出场，只收取预约费用
                        if (TimeUtils.getMillScondByMunites(parkingTimeLen) <= freeStandard.getFreeTimeLength()) {
                            time = appointmentPO.getAppointStartTime().getTime() - appointmentPO.getCreateTime().getTime();
                        }
                        this.generateOrder(user.getId(), ParkingFeeUtil.getNewParkingFee(parkingDf.parse(outDate), parkingInfo, freeStandard),
                                parkingInfo.getId(), parkingTimeLen, user.getBalance());
                        rsData.put("parkingLen", feeAndParkingTime.get("timeLen"));

                    } else {//无预约
                        //生成订单
                        //计算时间：出场时间-截止日期后的第一天
                        ca.clear();
                        ca.setTime(card.getExpiryTime());
                        ca.add(GregorianCalendar.DATE, 1);
                        Long time = parkingDf.parse(outDate).getTime() - ca.getTime().getTime();
                        Map<String, Object> feeAndParkingTime = ParkingFeeUtil.getParkingFee(time,
                                freeStandard.getHourCost(), freeStandard.getMostCost(),
                                freeStandard.getFreeTimeLength());

                        /* 2018/12/06 start */
                        Deduction deduction = FeeServiceDeduction.getDeduction(jpaQueryFactory, carNum, outDate);
                        int minusHour = 0;
                        if (deduction != null) {
                            minusHour = deduction.getHourNum();
                        }
                        FeeModel feeModel = new FeeModel();
                        feeModel.setHourCost(freeStandard.getHourCost().doubleValue());
                        feeModel.setMostCostDaily(freeStandard.getMostCost().doubleValue());
//                        feeModel.setFreeMinute(freeStandard.getFreeTimeLength() + minusHour * 60);
                        feeModel.setFreeMinute(freeStandard.getFreeTimeLength());
                        FeeServiceKit feeServiceKit = new FeeServiceKit(feeModel,
                                DateUtil.getDateTimeStr(ca.getTime()),
                                outDate,
                                DateUtil.getDateTimeStr(card.getExpiryTime()),
                                CardType.Month.getTypeValue());
                        double total = feeServiceKit.getResultFee() - minusHour * feeModel.getHourCost();
                        if (total >= 0) {
                            feeAndParkingTime.put("total", total);
                        } else {
                            feeAndParkingTime.put("total", 0);
                        }
                        FeeServiceDeduction.disableDeduction(jpaQueryFactory, user.getId(), deduction);
                        /* 2018/12/06 end */

                        // 如果出场时间在免费时长内出场，无费用产生，不生成订单只写入停车信息
                        if (!(TimeUtils.getMillScondByMunites(time) <= freeStandard.getFreeTimeLength())) {
                            this.generateOrder(user.getId(), new BigDecimal(feeAndParkingTime.get("total").toString()),
                                    parkingInfo.getId(), time, user.getBalance());
                        }
                        rsData.put("parkingLen", feeAndParkingTime.get("timeLen"));
                    }
                    //更新出场信息
                    this.updatePakingOutInfo(parkingDf.parse(outDate), parkingInfo.getId(), roadId);

                    rsData.put("isAuthorization", IsAuthorization.Open.getTypeValue());
                    rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.card.expire.message",
                            card.getCarOwnerName()));
                    rsData.put("isMember", MemberShip.MonthCard.getTypeValue());
                    if (user != null) {
                        rsData.put("isMember", MemberShip.MemberAndCard.getTypeValue());
                    }

                    //通知客户端车已出场
                    if (user.getRegistrationId() != null) {
                        this.pushMessageToClient(user, parkingInfo.getLicensePlate(), BaseConstant.JpushType.CarOut.getTypeValue());
                    }

                    return RetKit.okData(rsData);
                } else {//临时
                    return this.tempParkingOut(parkingInfo.getId(), roadId, WeiXinScanType.PlateOut.getTypeValue(), outDate, card, carNum, parkingInfo.getParkDate(), freeStandard);
                }
            } else if (parkingInfo.getParkDate().before(card.getExpiryTime())
                    && parkingDf.parse(outDate).before(card.getExpiryTime())) {//有效期内
                StringBuffer message = new StringBuffer();
                message.append("月卡有效期至");
                message.append(dayFomat.format(card.getExpiryTime()));

                //更新出场信息
                this.updatePakingOutInfo(parkingDf.parse(outDate), parkingInfo.getId(), roadId);

                rsData.put("isAuthorization", IsAuthorization.Open.getTypeValue());
                rsData.put("indentifyMessage", message);
                rsData.put("isMember", MemberShip.MemberAndCard.getTypeValue());
                rsData.put("parkingLen", ParkingFeeUtil.getParkingLen(parkingDf.parse(outDate).getTime() - parkingInfo.getParkDate().getTime()));
                rsData.put("isMember", MemberShip.MonthCard.getTypeValue());
                if (user != null) {
                    rsData.put("isMember", MemberShip.MemberAndCard.getTypeValue());
                }
                //通知客户端车已出场
                if (user != null && user.getRegistrationId() != null) {
                    this.pushMessageToClient(user, parkingInfo.getLicensePlate(), BaseConstant.JpushType.CarOut.getTypeValue());
                }
                return RetKit.okData(rsData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetKit.fail();
        }
        return RetKit.fail();
    }

    /**
     * 月卡工作日
     *
     * @param outDate
     * @param card
     * @param roadId
     * @param parkingInfo
     * @param user
     * @param max
     * @param carNum
     * @param freeStandard
     * @return
     */
    private RetKit parkingOutAsCardDay(String outDate, Card card, ParkingRule rule, Integer roadId, ParkingInfo parkingInfo, User user,
                                       Integer max, String carNum, FreeStandard freeStandard) {
        Map<String, Object> rsData = new HashMap<String, Object>();
        SimpleDateFormat parkingDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dayFomat = new SimpleDateFormat("yyyy-MM-dd");

        QParkingInfo parkingInfoEntity = QParkingInfo.parkingInfo;
        QCardLicensePlate cardLicensePlateEntity = QCardLicensePlate.cardLicensePlate;
        GregorianCalendar ca = new GregorianCalendar();


        try {
            //月卡下绑定的多张车片
            List<String> carNums = this.jpaQueryFactory.select(cardLicensePlateEntity.lisencePlate)
                    .from(cardLicensePlateEntity).where(cardLicensePlateEntity.cardId.eq(card.getId())).fetch();
            //按入场顺序查找指定限额内的车牌
            List<String> admits = this.jpaQueryFactory.select(parkingInfoEntity.licensePlate)
                    .from(parkingInfoEntity)
                    .where(parkingInfoEntity.licensePlate.in(carNums)
                            .and(parkingInfoEntity.status.eq(parkingInfoStatus.unfinishedStatus.getValue())))
                    .limit(max).fetch();
            if (!admits.contains(carNum) || card.getExpiryTime().before(parkingInfo.getParkDate())) {//限额外的车牌或入场前已过期（完全过期）
                if (user != null) {//会员
                    return this.parkingOutAsMember(outDate, parkingInfo, freeStandard, card, user, roadId);
                } else {//临时
                    return this.tempParkingOut(parkingInfo.getId(), roadId, WeiXinScanType.PlateOut.getTypeValue(), outDate, card, carNum, parkingInfo.getParkDate(), freeStandard);
                }
            } else if (parkingInfo.getParkDate().before(card.getExpiryTime())
                    && card.getExpiryTime().before(parkingDf.parse(outDate))) {//部分过期
                //查询出场规则
//				QParkingRule ruleEntity =  QParkingRule.parkingRule;
//				QParkingRuleRelation relationEntity = QParkingRuleRelation.parkingRuleRelation;
//				ParkingRule rule = this.jpaQueryFactory.select(ruleEntity).from(ruleEntity, relationEntity)
//						.where(ruleEntity.id.eq(relationEntity.parkingRuleId)
//								.and(relationEntity.parkingId.eq(parkingInfo.getId())
//										.and(ruleEntity.ruleType.eq(RoadMarker.OUT.getTypeValue())
//												.and(ruleEntity.inRule.eq(CardType.WorkDay.getTypeValue()))))).fetchFirst();
                if (user != null) {//会员
                    if (parkingInfo.getAppointmentId() != null) {//有预约
                        rsData.put("isAppointment", true);
                        QAppointment appointmentEntity = QAppointment.appointment;
                        Appointment appointmentPO = this.jpaQueryFactory.select(appointmentEntity).from(appointmentEntity)
                                .where(appointmentEntity.id.eq(parkingInfo.getAppointmentId())).fetchFirst();

                        //计算时间：（预约时间-预约提交时间）+ （截止日期与入场日期的非工作日+（出场时间-截止日期后的第一天））

                        Long time = appointmentPO.getAppointStartTime().getTime() - appointmentPO.getCreateTime().getTime();

                        Date recursiveDay = parkingInfo.getParkDate();

                        Long workTime = new Long(0);

                        while (recursiveDay.getTime() <= card.getExpiryTime().getTime()) {//找出入场时间与有效时间之间的工作时间
                            ca.clear();
                            ca.setTime(recursiveDay);
                            int dayOfWeek = ca.get(GregorianCalendar.DAY_OF_WEEK);
                            if (dayOfWeek >= rule.getStartDay() && dayOfWeek <= rule.getEndDay()) {// 当前日期时间是工作日
                                if (recursiveDay.compareTo(parkingInfo.getParkDate()) == 0) {//当前循环日期是入场日
                                    workTime += parkingDf.parse(dayFomat.format(parkingInfo.getParkDate()) + "23:59:59")
                                            .getTime() - parkingInfo.getParkDate().getTime();
                                } else {
                                    workTime += parkingDf.parse(dayFomat.format(recursiveDay) + "23:59:59")
                                            .getTime() - parkingDf.parse(dayFomat.format(recursiveDay) + "00:00:00").getTime();
                                }
                            }
                        }
                        //（有效时间-入场时间）-工作时间=入场时间与有效时间之间的非工作时间
                        time += (card.getExpiryTime().getTime() - parkingInfo.getParkDate().getTime()) - workTime;
                        time += parkingDf.parse(outDate).getTime() - card.getExpiryTime().getTime();

                        Long parkingTimeLen = time;

                        //生成订单
                        Map<String, Object> feeAndParkingTime = ParkingFeeUtil.getParkingFee(time,
                                freeStandard.getHourCost(), freeStandard.getMostCost(),
                                freeStandard.getFreeTimeLength());

                        /* 2018/12/06 start */
                        Deduction deduction = FeeServiceDeduction.getDeduction(jpaQueryFactory, carNum, outDate);
                        int minusHour = 0;
                        if (deduction != null) {
                            minusHour = deduction.getHourNum();
                        }
                        FeeModel feeModel = new FeeModel();
                        feeModel.setHourCost(freeStandard.getHourCost().doubleValue());
                        feeModel.setMostCostDaily(freeStandard.getMostCost().doubleValue());
                        feeModel.setFreeMinute(freeStandard.getFreeTimeLength());
                        FeeServiceKit feeServiceKit = new FeeServiceKit(
                                feeModel,
                                DateUtil.getDateTimeStr(appointmentPO.getCreateTime()),
                                outDate,
                                DateUtil.getDateTimeStr(card.getExpiryTime()),
                                ParkingConstant.CardType.WorkDay.getTypeValue(),
                                rule.getStartDay(),
                                rule.getEndDay());
                        double total = feeServiceKit.getResultFee() - minusHour * feeModel.getHourCost();
                        if (total >= 0) {
                            feeAndParkingTime.put("total", total);
                        } else {
                            feeAndParkingTime.put("total", 0);
                        }
                        FeeServiceDeduction.disableDeduction(jpaQueryFactory, user.getId(), deduction);
                        /* 2018/12/06 end */

                        // 如果出场时间在免费时长内出场，只收取预约费用
                        if (TimeUtils.getMillScondByMunites(parkingTimeLen) <= freeStandard.getFreeTimeLength()) {
                            time = appointmentPO.getAppointStartTime().getTime() - appointmentPO.getCreateTime().getTime();
                        }
                        this.generateOrder(user.getId(), new BigDecimal(feeAndParkingTime.get("total").toString()),
                                parkingInfo.getId(), parkingTimeLen, user.getBalance());
                        rsData.put("parkingLen", ParkingFeeUtil.getParkingLen(parkingTimeLen));

                    } else {//无预约
                        //生成订单
                        //计算时间：截止日期与入场日期的非工作日+（出场时间-截止日期后的第一天））
                        Date recursiveDay = parkingInfo.getParkDate();

                        Long workTime = new Long(0);

                        while (recursiveDay.getTime() <= card.getExpiryTime().getTime()) {//找出入场时间与有效时间之间的工作时间
                            ca.clear();
                            ca.setTime(recursiveDay);
                            int dayOfWeek = ca.get(GregorianCalendar.DAY_OF_WEEK);
                            if (dayOfWeek >= rule.getStartDay() && dayOfWeek <= rule.getEndDay()) {// 当前日期时间是工作日
                                if (recursiveDay.compareTo(parkingInfo.getParkDate()) == 0) {//当前循环日期是入场日
                                    workTime += parkingDf.parse(dayFomat.format(parkingInfo.getParkDate()) + "23:59:59")
                                            .getTime() - parkingInfo.getParkDate().getTime();
                                } else {
                                    workTime += parkingDf.parse(dayFomat.format(recursiveDay) + "23:59:59")
                                            .getTime() - parkingDf.parse(dayFomat.format(recursiveDay) + "00:00:00").getTime();
                                }
                            }
                        }
                        //（有效时间-入场时间）-工作时间=入场时间与有效时间之间的非工作时间
                        Long time = (card.getExpiryTime().getTime() - parkingInfo.getParkDate().getTime()) - workTime;
                        time += parkingDf.parse(outDate).getTime() - card.getExpiryTime().getTime();

                        //生成订单
                        Map<String, Object> feeAndParkingTime = ParkingFeeUtil.getParkingFee(time,
                                freeStandard.getHourCost(), freeStandard.getMostCost(),
                                freeStandard.getFreeTimeLength());

                        /* 2018/12/06 start */
                        Deduction deduction = FeeServiceDeduction.getDeduction(jpaQueryFactory, carNum, outDate);
                        int minusHour = 0;
                        if (deduction != null) {
                            minusHour = deduction.getHourNum();
                        }
                        FeeModel feeModel = new FeeModel();
                        feeModel.setHourCost(freeStandard.getHourCost().doubleValue());
                        feeModel.setMostCostDaily(freeStandard.getMostCost().doubleValue());
                        feeModel.setFreeMinute(freeStandard.getFreeTimeLength());
                        FeeServiceKit feeServiceKit =
                                new FeeServiceKit(
                                        feeModel,
                                        DateUtil.getDateTimeStr(parkingInfo.getParkDate()),
                                        outDate,
                                        DateUtil.getDateTimeStr(card.getExpiryTime()),
                                        ParkingConstant.CardType.WorkDay.getTypeValue(),
                                        rule.getStartDay(),
                                        rule.getEndDay());
                        double total = feeServiceKit.getResultFee() - minusHour * feeModel.getHourCost();
                        if (total >= 0) {
                            feeAndParkingTime.put("total", total);
                        } else {
                            feeAndParkingTime.put("total", 0);
                        }
                        FeeServiceDeduction.disableDeduction(jpaQueryFactory, user.getId(), deduction);
                        /* 2018/12/06 end */

                        // 如果出场时间在免费时长内出场，不收取任何费用
                        if (!(TimeUtils.getMillScondByMunites(time) <= freeStandard.getFreeTimeLength())) {
                            this.generateOrder(user.getId(), new BigDecimal(feeAndParkingTime.get("total").toString()),
                                    parkingInfo.getId(), time, user.getBalance());
                        }
                        rsData.put("parkingLen", feeAndParkingTime.get("timeLen"));
                    }
                    //更新出场信息
                    this.updatePakingOutInfo(parkingDf.parse(outDate), parkingInfo.getId(), roadId);

                    rsData.put("isAuthorization", IsAuthorization.Open.getTypeValue());
                    rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.card.expire.message",
                            card.getCarOwnerName()));
                    rsData.put("isMember", MemberShip.MonthCard.getTypeValue());
                    if (user != null) {
                        rsData.put("isMember", MemberShip.MemberAndCard.getTypeValue());
                    }
                    //通知客户端车已出场
                    if (user.getRegistrationId() != null) {
                        this.pushMessageToClient(user, parkingInfo.getLicensePlate(), BaseConstant.JpushType.CarOut.getTypeValue());
                    }
                    return RetKit.okData(rsData);
                } else {//非会员
                    return this.tempParkingOut(parkingInfo.getId(), roadId, WeiXinScanType.PlateOut.getTypeValue(), outDate, card, carNum, parkingInfo.getParkDate(), freeStandard);
                }
            } else if (parkingInfo.getParkDate().before(card.getExpiryTime())
                    && parkingDf.parse(outDate).before(card.getExpiryTime())) {//未过期

                StringBuffer message = new StringBuffer();
                message.append("月卡有效期至");
                message.append(dayFomat.format(card.getExpiryTime()));

                rsData.put("isAuthorization", IsAuthorization.Open.getTypeValue());
                rsData.put("indentifyMessage", message);
                rsData.put("isMember", MemberShip.MonthCard.getTypeValue());
                if (user != null) {
                    rsData.put("isMember", MemberShip.MemberAndCard.getTypeValue());
                }
                rsData.put("parkingLen", ParkingFeeUtil.getParkingLen(parkingDf.parse(outDate).getTime() - parkingInfo.getParkDate().getTime()));


                /* 2019/01/21 start */
                //生成订单
                QUser qUser = QUser.user;
                User userPo = jpaQueryFactory.select(qUser).from(qUser).where(qUser.licensePlat.eq(parkingInfo.getLicensePlate())).fetchFirst();
                Map<String, Object> feeAndParkingTime = ParkingFeeUtil.getParkingFee(parkingDf.parse(outDate).getTime() - parkingInfo.getParkDate().getTime(),
                        freeStandard.getHourCost(), freeStandard.getMostCost(),
                        freeStandard.getFreeTimeLength());
                Deduction deduction = FeeServiceDeduction.getDeduction(jpaQueryFactory, carNum, outDate);
                int minusHour = 0;
                if (deduction != null) {
                    minusHour = deduction.getHourNum();
                }
                FeeModel feeModel = new FeeModel();
                feeModel.setHourCost(freeStandard.getHourCost().doubleValue());
                feeModel.setMostCostDaily(freeStandard.getMostCost().doubleValue());
                feeModel.setFreeMinute(freeStandard.getFreeTimeLength());
                FeeServiceKit feeServiceKit =
                        new FeeServiceKit(
                                feeModel,
                                DateUtil.getDateTimeStr(parkingInfo.getParkDate()),
                                outDate,
                                DateUtil.getDateTimeStr(card.getExpiryTime()),
                                ParkingConstant.ParkingRuleType.WorkDay.getTypeValue(),
                                rule.getStartDay(),
                                rule.getEndDay());
                double total = feeServiceKit.getResultFee() - minusHour * feeModel.getHourCost();
                if (total >= 0) {
                    feeAndParkingTime.put("total", total);
                } else {
                    feeAndParkingTime.put("total", 0);
                }
                if (user != null) {
                    this.generateOrder(userPo.getId(), new BigDecimal(feeAndParkingTime.get("total").toString()), parkingInfo.getId(), parkingDf.parse(outDate).getTime() - parkingInfo.getParkDate().getTime(), userPo.getBalance());
                    FeeServiceDeduction.disableDeduction(jpaQueryFactory, userPo.getId(), deduction);
                } else {
                    if (feeAndParkingTime.get("total").toString() == "0") {
                        rsData.put("chargeCard", true);
                    }
                }

                /* 2019/01/21 end */

                //更新出场信息
                this.updatePakingOutInfo(parkingDf.parse(outDate), parkingInfo.getId(), roadId);

                //通知客户端车已出场
                if (user != null && user.getRegistrationId() != null) {
                    this.pushMessageToClient(user, parkingInfo.getLicensePlate(), BaseConstant.JpushType.CarOut.getTypeValue());
                }

                return RetKit.okData(rsData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetKit.fail();
        }
        return RetKit.fail();
    }

    /**
     * 工作时
     *
     * @param outDate
     * @param card
     * @param roadId
     * @param parkingInfo
     * @param user
     * @param max
     * @param carNum
     * @param freeStandard
     * @return
     */
    private RetKit parkingOutAsCardDayTime(String outDate, Card card, Integer roadId, ParkingInfo parkingInfo, User user,
                                           Integer max, String carNum, FreeStandard freeStandard) {
        Map<String, Object> rsData = new HashMap<String, Object>();
        SimpleDateFormat parkingDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dayFomat = new SimpleDateFormat("yyyy-MM-dd");

        QParkingInfo parkingInfoEntity = QParkingInfo.parkingInfo;
        QCardLicensePlate cardLicensePlateEntity = QCardLicensePlate.cardLicensePlate;
        GregorianCalendar ca = new GregorianCalendar();

        try {
            //月卡下绑定的多张车片
            List<String> carNums = this.jpaQueryFactory.select(cardLicensePlateEntity.lisencePlate)
                    .from(cardLicensePlateEntity).where(cardLicensePlateEntity.cardId.eq(card.getId())).fetch();
            //按入场顺序查找指定限额内的车牌
            List<String> admits = this.jpaQueryFactory.select(parkingInfoEntity.licensePlate)
                    .from(parkingInfoEntity)
                    .where(parkingInfoEntity.licensePlate.in(carNums)
                            .and(parkingInfoEntity.status.eq(parkingInfoStatus.unfinishedStatus.getValue())))
                    .limit(max).fetch();

            if (!admits.contains(carNum) || card.getExpiryTime().before(parkingInfo.getParkDate())) {//限额外的车牌或入场前已过期（完全过期）
                if (user != null) {//会员
                    return this.parkingOutAsMember(outDate, parkingInfo, freeStandard, card, user, roadId);
                } else {//临时
                    return this.tempParkingOut(parkingInfo.getId(), roadId, WeiXinScanType.PlateOut.getTypeValue(), outDate, card, carNum, parkingInfo.getParkDate(), freeStandard);
                }
            } else if (parkingInfo.getParkDate().before(card.getExpiryTime())
                    && card.getExpiryTime().before(parkingDf.parse(outDate))) {//部分过期
                //规则
                QParkingRule ruleEntity = QParkingRule.parkingRule;
                QParkingRuleRelation relationEntity = QParkingRuleRelation.parkingRuleRelation;
                ParkingRule rule = this.jpaQueryFactory.select(ruleEntity).from(ruleEntity, relationEntity)
                        .where(ruleEntity.id.eq(relationEntity.parkingRuleId)
                                .and(relationEntity.parkingId.eq(parkingInfo.getId())
                                        .and(ruleEntity.ruleType.eq(RoadMarker.OUT.getTypeValue())
                                                .and(ruleEntity.inRule.eq(CardType.WorkTime.getTypeValue()))))).fetchFirst();
                if (user != null) {//会员
                    if (parkingInfo.getAppointmentId() != null) {//有预约
                        rsData.put("isAppointment", true);
                        QAppointment appointmentEntity = QAppointment.appointment;
                        Appointment appointmentPO = this.jpaQueryFactory.select(appointmentEntity).from(appointmentEntity)
                                .where(appointmentEntity.id.eq(parkingInfo.getAppointmentId())).fetchFirst();

                        //计算时间：（预约时间-预约提交时间）+ （截止日期与入场日期间的非工作时+（出场时间-截止日期后的第一天））

                        Long time = appointmentPO.getAppointStartTime().getTime() - appointmentPO.getCreateTime().getTime();

                        Date recursiveDay = parkingInfo.getParkDate();

                        Long workTime = new Long(0);

                        while (recursiveDay.getTime() <= card.getExpiryTime().getTime()) {//找出入场时间与有效时间之间的工作小时
                            ca.clear();
                            ca.setTime(recursiveDay);
                            int dayOfWeek = ca.get(GregorianCalendar.DAY_OF_WEEK);
                            Date startDateTime = parkingDf.parse(dayFomat.format(recursiveDay) + rule.getStartTime());
                            Date endDateTime = parkingDf.parse(dayFomat.format(recursiveDay) + rule.getEndTime());
                            if (dayOfWeek >= rule.getStartDay() && dayOfWeek <= rule.getEndDay()) {// 当前日期时间是工作时间内
                                if (recursiveDay.compareTo(parkingInfo.getParkDate()) == 0) {// 当前循环日期是入场日
                                    if ((startDateTime.getTime() - parkingInfo.getParkDate().getTime()) > 0
                                            && endDateTime.getTime() - parkingDf.parse(outDate).getTime() > 0
                                            && endDateTime.getTime() > parkingDf.parse(outDate).getTime()) {
                                        workTime += parkingDf.parse(outDate).getTime() - startDateTime.getTime();
                                    }
                                    if ((startDateTime.getTime() - parkingInfo.getParkDate().getTime()) < 0
                                            && endDateTime.getTime() - parkingDf.parse(outDate).getTime() < 0
                                            && endDateTime.getTime() > startDateTime.getTime()) {
                                        workTime += endDateTime.getTime() - parkingInfo.getParkDate().getTime();
                                    }
                                    if ((startDateTime.getTime() - parkingInfo.getParkDate().getTime()) < 0
                                            && endDateTime.getTime() - parkingDf.parse(outDate).getTime() > 0) {
                                        workTime += (parkingInfo.getParkDate().getTime() - parkingInfo.getParkDate().getTime())
                                                + (endDateTime.getTime() - parkingDf.parse(outDate).getTime());
                                    }
                                    if ((startDateTime.getTime() - parkingInfo.getParkDate().getTime()) > 0
                                            && endDateTime.getTime() - parkingDf.parse(outDate).getTime() < 0) {
                                        workTime += endDateTime.getTime() - startDateTime.getTime();
                                    }
                                } else {
                                    workTime += parkingDf.parse(dayFomat.format(recursiveDay) + rule.getEndTime())
                                            .getTime()
                                            - parkingDf.parse(dayFomat.format(recursiveDay) + rule.getStartTime())
                                            .getTime();
                                }
                            }
                        }
                        //（预约时间-预约提交时间）+ （截止日期与入场日期间的非工作时+（出场时间-截止日期后的第一天））
                        time += (card.getExpiryTime().getTime() - parkingInfo.getParkDate().getTime()) - workTime;
                        time += parkingDf.parse(outDate).getTime() - card.getExpiryTime().getTime();

                        Long parkingTimeLen = time;

                        //生成订单
                        Map<String, Object> feeAndParkingTime = ParkingFeeUtil.getParkingFee(parkingTimeLen,
                                freeStandard.getHourCost(), freeStandard.getMostCost(),
                                freeStandard.getFreeTimeLength());

                        /* 2018/12/06 start */
                        Deduction deduction = FeeServiceDeduction.getDeduction(jpaQueryFactory, carNum, outDate);
                        int minusHour = 0;
                        if (deduction != null) {
                            minusHour = deduction.getHourNum();
                        }
                        FeeModel feeModel = new FeeModel();
                        feeModel.setHourCost(freeStandard.getHourCost().doubleValue());
                        feeModel.setMostCostDaily(freeStandard.getMostCost().doubleValue());
                        feeModel.setFreeMinute(freeStandard.getFreeTimeLength());
                        FeeServiceKit feeServiceKit =
                                new FeeServiceKit(
                                        feeModel,
                                        DateUtil.getDateTimeStr(parkingInfo.getParkDate()),
                                        outDate,
                                        DateUtil.getDateTimeStr(card.getExpiryTime()),
                                        CardType.WorkTime.getTypeValue(),
                                        rule.getStartDay(),
                                        rule.getEndDay(),
                                        rule.getStartTime(),
                                        rule.getStartTime());
                        double total = feeServiceKit.getResultFee() - minusHour * feeModel.getHourCost();
                        if (total >= 0) {
                            feeAndParkingTime.put("total", total);
                        } else {
                            feeAndParkingTime.put("total", 0);
                        }
                        FeeServiceDeduction.disableDeduction(jpaQueryFactory, user.getId(), deduction);
                        /* 2018/12/06 end */


                        // 如果出场时间在免费时长内出场，只收取预约费用
                        if (TimeUtils.getMillScondByMunites(parkingTimeLen) <= freeStandard.getFreeTimeLength()) {
                            time = appointmentPO.getAppointStartTime().getTime() - appointmentPO.getCreateTime().getTime();
                        }
                        this.generateOrder(user.getId(), new BigDecimal(feeAndParkingTime.get("total").toString()),
                                parkingInfo.getId(), parkingTimeLen, user.getBalance());
                        rsData.put("parkingLen", feeAndParkingTime.get("timeLen"));

                    } else {//无预约
                        //生成订单
                        //计算时间：截止日期与入场日期的非工作日+（出场时间-截止日期后的第一天））

                        Date recursiveDay = parkingInfo.getParkDate();

                        Long workTime = new Long(0);

                        while (recursiveDay.getTime() <= card.getExpiryTime().getTime()) {//找出入场时间与有效时间之间的工作小时
                            ca.clear();
                            ca.setTime(recursiveDay);
                            int dayOfWeek = ca.get(GregorianCalendar.DAY_OF_WEEK);
                            Date startDateTime = parkingDf.parse(dayFomat.format(recursiveDay) + rule.getStartTime());
                            Date endDateTime = parkingDf.parse(dayFomat.format(recursiveDay) + rule.getEndTime());
                            if (dayOfWeek >= rule.getStartDay() && dayOfWeek <= rule.getEndDay()) {// 当前日期时间是工作时间内
                                if (recursiveDay.compareTo(parkingInfo.getParkDate()) == 0) {// 当前循环日期是入场日
                                    if ((startDateTime.getTime() - parkingInfo.getParkDate().getTime()) > 0
                                            && endDateTime.getTime() - parkingDf.parse(outDate).getTime() > 0
                                            && endDateTime.getTime() > parkingDf.parse(outDate).getTime()) {
                                        workTime += parkingDf.parse(outDate).getTime() - startDateTime.getTime();
                                    }
                                    if ((startDateTime.getTime() - parkingInfo.getParkDate().getTime()) < 0
                                            && endDateTime.getTime() - parkingDf.parse(outDate).getTime() < 0
                                            && endDateTime.getTime() > startDateTime.getTime()) {
                                        workTime += endDateTime.getTime() - parkingInfo.getParkDate().getTime();
                                    }
                                    if ((startDateTime.getTime() - parkingInfo.getParkDate().getTime()) < 0
                                            && endDateTime.getTime() - parkingDf.parse(outDate).getTime() > 0) {
                                        workTime += (parkingInfo.getParkDate().getTime() - parkingInfo.getParkDate().getTime())
                                                + (endDateTime.getTime() - parkingDf.parse(outDate).getTime());
                                    }
                                    if ((startDateTime.getTime() - parkingInfo.getParkDate().getTime()) > 0
                                            && endDateTime.getTime() - parkingDf.parse(outDate).getTime() < 0) {
                                        workTime += endDateTime.getTime() - startDateTime.getTime();
                                    }
                                } else {
                                    workTime += parkingDf.parse(dayFomat.format(recursiveDay) + rule.getEndTime())
                                            .getTime()
                                            - parkingDf.parse(dayFomat.format(recursiveDay) + rule.getStartTime())
                                            .getTime();
                                }
                            }
                        }
                        //（预约时间-预约提交时间）+ （截止日期与入场日期间的非工作时+（出场时间-截止日期后的第一天））
                        Long time = (card.getExpiryTime().getTime() - parkingInfo.getParkDate().getTime()) - workTime;
                        time += parkingDf.parse(outDate).getTime() - card.getExpiryTime().getTime();

                        //生成订单
                        Map<String, Object> feeAndParkingTime = ParkingFeeUtil.getParkingFee(time,
                                freeStandard.getHourCost(), freeStandard.getMostCost(),
                                freeStandard.getFreeTimeLength());

                        /* 2018/12/06 start */
                        Deduction deduction = FeeServiceDeduction.getDeduction(jpaQueryFactory, carNum, outDate);
                        int minusHour = 0;
                        if (deduction != null) {
                            minusHour = deduction.getHourNum();
                        }
                        FeeModel feeModel = new FeeModel();
                        feeModel.setHourCost(freeStandard.getHourCost().doubleValue());
                        feeModel.setMostCostDaily(freeStandard.getMostCost().doubleValue());
                        feeModel.setFreeMinute(freeStandard.getFreeTimeLength());
                        FeeServiceKit feeServiceKit = new FeeServiceKit(feeModel,
                                DateUtil.getDateTimeStr(parkingInfo.getParkDate()),
                                outDate,
                                DateUtil.getDateTimeStr(card.getExpiryTime()),
                                CardType.WorkTime.getTypeValue(),
                                rule.getStartDay(),
                                rule.getEndDay(),
                                rule.getStartTime(),
                                rule.getStartTime());
                        double total = feeServiceKit.getResultFee() - minusHour * feeModel.getHourCost();
                        if (total >= 0) {
                            feeAndParkingTime.put("total", total);
                        } else {
                            feeAndParkingTime.put("total", 0);
                        }
                        FeeServiceDeduction.disableDeduction(jpaQueryFactory, user.getId(), deduction);
                        /* 2018/12/06 end */

                        // 如果出场时间在免费时长内出场，不收取任何费用
                        if (!(TimeUtils.getMillScondByMunites(time) <= freeStandard.getFreeTimeLength())) {
                            this.generateOrder(user.getId(), new BigDecimal(feeAndParkingTime.get("total").toString()),
                                    parkingInfo.getId(), time, user.getBalance());
                        }
                        rsData.put("parkingLen", feeAndParkingTime.get("timeLen"));
                    }
                    //更新出场信息
                    this.updatePakingOutInfo(parkingDf.parse(outDate), parkingInfo.getId(), roadId);

                    rsData.put("isAuthorization", IsAuthorization.Open.getTypeValue());
                    rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.card.expire.message",
                            card.getCarOwnerName()));
                    rsData.put("isMember", MemberShip.MonthCard.getTypeValue());
                    if (user != null) {
                        rsData.put("isMember", MemberShip.MemberAndCard.getTypeValue());
                    }


                    //通知客户端车已出场
                    if (user.getRegistrationId() != null) {
                        this.pushMessageToClient(user, parkingInfo.getLicensePlate(), BaseConstant.JpushType.CarOut.getTypeValue());
                    }
                    return RetKit.okData(rsData);
                }
            } else if (parkingInfo.getParkDate().before(card.getExpiryTime())
                    && parkingDf.parse(outDate).before(card.getExpiryTime())) {//未过期
                StringBuffer message = new StringBuffer();
                message.append("月卡有效期至");
                message.append(dayFomat.format(card.getExpiryTime()));

                //更新出场信息
                this.updatePakingOutInfo(parkingDf.parse(outDate), parkingInfo.getId(), roadId);

                rsData.put("isAuthorization", IsAuthorization.Open.getTypeValue());
                rsData.put("indentifyMessage", message);
                rsData.put("isMember", MemberShip.MonthCard.getTypeValue());
                if (user != null) {
                    rsData.put("isMember", MemberShip.MemberAndCard.getTypeValue());
                }
                rsData.put("parkingLen", ParkingFeeUtil.getParkingLen(parkingDf.parse(outDate).getTime() - parkingInfo.getParkDate().getTime()));
                //通知客户端车已出场
                if (user != null && user.getRegistrationId() != null) {
                    this.pushMessageToClient(user, parkingInfo.getLicensePlate(), BaseConstant.JpushType.CarOut.getTypeValue());
                }
                return RetKit.okData(rsData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetKit.fail();
        }
        return RetKit.fail();
    }

    /**
     * 临时用户扫码出场
     *
     * @param parkingInfoId
     * @param roadId
     * @param showFlag
     * @param outDate
     * @param card
     * @param carNum
     * @param parkingInDate
     * @return
     * @throws ParseException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    private RetKit tempParkingOut(Integer parkingInfoId, Integer roadId, String showFlag, String outDate, Card card, String carNum, Date parkingInDate, FreeStandard freeStandard) throws ParseException, IllegalAccessException, InvocationTargetException, ClientProtocolException, IOException {
        Map<String, Object> rsData = new HashMap<String, Object>();
        SimpleDateFormat parkingDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = parkingDf.parse(outDate).getTime() - parkingInDate.getTime();
        Order order = orderRepository.findOrder(parkingInfoId);
        Boolean showFreeFlag = false;
        //新建一个parkingInfo,如果扫固定码出场超过15分钟，那么就要重新计费
        ParkingInfo saveParkingInfo = parkingInfoRepository.findParkingInfoById(parkingInfoId);
        ParkingInfo newParkingInfo = new ParkingInfo();
        if ((order != null)
                && (order.getStatus() == BaseConstant.OrderStatus.AdvanceAlreadyPay.getTypeValue())) {
            long freeTime = parkingDf.parse(outDate).getTime() - order.getPayTime().getTime();
            Integer freeTimeInt = (int) (freeTime / (1000 * 60));
            if (freeTimeInt <= 15) { //15分钟内离场可以不收费
                showFreeFlag = true;
            } else {
                time = parkingDf.parse(outDate).getTime() - order.getPayTime().getTime();
                saveParkingInfo.setOutDate(order.getPayTime());
                saveParkingInfo.setStatus(BaseConstant.parkingInfoStatus.finishedStatus.getValue());
                parkingInfoRepository.save(saveParkingInfo);
                BeanUtils.copyProperties(newParkingInfo, saveParkingInfo);
                newParkingInfo.setId(null);
                newParkingInfo.setParkDate(order.getPayTime());
                newParkingInfo.setOutDate(parkingDf.parse(outDate));
                newParkingInfo.setOutRoadWayId(roadId);
                newParkingInfo.setStatus(BaseConstant.parkingInfoStatus.unfinishedStatus.getValue());
                newParkingInfo = parkingInfoRepository.save(newParkingInfo);
                parkingInfoId = newParkingInfo.getId();
                parkingInDate = order.getPayTime();
            }
            order.setStatus(BaseConstant.OrderStatus.AlreadyPay.getTypeValue());
            orderRepository.save(order);
        }else {
        	this.updateTmpPakingOutInfo(parkingDf.parse(outDate), parkingInfoId, roadId);
        }


        StringBuffer url = new StringBuffer();
        url.append(MessageUtil.loadMessage("parking.domain") + MessageUtil.loadMessage("parking.out.plate.QRCode.url"));
        url.append("?parkingInfoId=");
        url.append(parkingInfoId);
        url.append("&showFlag=");
        url.append(showFlag);
        url.append("&type=");
        url.append(card == null ? CardType.NonCard.getTypeValue() : card.getType());
        String tmpCardMessage = "临时车" + carNum;
        rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.temp.message", tmpCardMessage));
        rsData.put("isMember", MemberShip.UnMember.getTypeValue());

        Integer timeInt = (int) (time / (1000 * 60));
        ParkingInfo parkingInfo = parkingInfoRepository.findById(parkingInfoId).get();
       // 临时车抵扣券
        Deduction deduction = FeeServiceDeduction.getDeduction(jpaQueryFactory, parkingInfo.getLicensePlate(), outDate);
        int deductionHour = 0;
        if (deduction != null) {
        	deductionHour = deduction.getHourNum();
		}
        BigDecimal bigDecimalHour = new BigDecimal(deductionHour);
        //费用=总费用-抵扣时长乘以每小时的停车费用
        BigDecimal parkingFee = ParkingFeeUtil.getNewParkingFee(parkingDf.parse(outDate), parkingInfo, freeStandard).subtract(bigDecimalHour.multiply(freeStandard.getHourCost()));
        if (deduction != null) {
			deduction.setStatus(BaseConstant.DeductionStatus.UsedStatus.getTypeValue());
		}
        BigDecimal zeroFee = new BigDecimal(0);
        if ((timeInt <= freeStandard.getFreeTimeLength()) || (parkingFee.compareTo(zeroFee) == 0) || (parkingFee.compareTo(zeroFee) == -1)) {
            //免费时长内，金额为0或者小于0的时候--直接出场
            showFreeFlag = true;
        }
        if (showFreeFlag) {     //直接出场（不用再次扫码）--  修改停车状态
            rsData.put("isAuthorization", IsAuthorization.Open.getTypeValue());
            parkingInfo.setStatus(BaseConstant.parkingInfoStatus.finishedStatus.getValue());
            parkingInfo = parkingInfoRepository.save(parkingInfo);
            rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.out.greeting.message", carNum));
        } else {  //超出免费时长  -- 显示收费二维码
            rsData.put("QCcodeUrl", url);
            parkingInfo.setStatus(BaseConstant.parkingInfoStatus.unfinishedStatus.getValue());
            parkingInfo = parkingInfoRepository.save(parkingInfo);
            rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.temp.message", carNum));
            //this.updateTmpPakingOutInfo(parkingDf.parse(outDate), parkingInfoId, roadId);
            QRoadway roadway = QRoadway.roadway;
            Roadway roadwayPO = this.jpaQueryFactory.select(roadway).from(roadway)
                    .where(roadway.id.eq(roadId)).fetchFirst();
            //发送socket给web端   parkingId前后需加上*号
            String sendUrl = MessageUtil.loadMessage("parking.domain") +":9002/cloudParkingWeb/sendSocketToUser";
            String Param = "?parkingId=*"+ roadwayPO.getParkingId() +"*&parkingInfoId="+parkingInfoId;
            log.warn("testURL="+sendUrl + Param);
            HttpSendUitl.doGetJson(sendUrl + Param);
        }
        try {
            rsData.put("parkingLen", ParkingFeeUtil.getParkingLen(parkingDf.parse(outDate).getTime() - parkingInDate.getTime()));
            //写入出场时间
            //this.updateTmpPakingOutInfo(parkingDf.parse(outDate), parkingInfoId, roadId);
        } catch (Exception e) {
            e.printStackTrace();
            RetKit.fail();
        }
        return RetKit.okData(rsData);
    }

    /**
     * 白名单用户出场
     *
     * @param parkingInforEntity
     * @param parkingDate
     * @param parkingInfoId
     * @return
     */
    private RetKit parkingOutAsWhiteName(QParkingInfo parkingInforEntity, String parkingDate, Date parkingInDate, Integer parkingInfoId, String carNum, Integer roadId) {
        Map<String, Object> rsData = new HashMap<String, Object>();
        SimpleDateFormat parkingDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        rsData.put("isMember", MemberShip.SpecialMember.getTypeValue());
        rsData.put("isAuthorization", IsAuthorization.Open.getTypeValue());
        rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.out.greeting.message", carNum));
        try {
            rsData.put("parkingLen", ParkingFeeUtil.getParkingLen(parkingDf.parse(parkingDate).getTime() - parkingInDate.getTime()));
            // 写入出场时间
            this.updatePakingOutInfo(parkingDf.parse(parkingDate), parkingInfoId, roadId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RetKit.okData(rsData);
    }

    /**
     * 入场停车信息
     *
     * @param user
     * @param card
     * @param appointment
     * @param rule
     * @param carNum
     * @param parkingDate
     * @param roadId
     * @param plateType
     * @param carType
     * @param parkCode
     * @return
     */
    private RetKit parkingIn(User user, Card card, Appointment appointment, ParkingRule rule, String carNum,
                             String parkingDate, Integer roadId, String plateType, String carType, Integer parkCode, Integer max) {
        Map<String, Object> rsData = new HashMap<String, Object>();
        SimpleDateFormat parkingDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar ca = new GregorianCalendar();
        try {
            Integer appointmentId = null;

            if (appointment != null) {
                appointmentId = appointment.getId();
                rsData.put("isAppointment", true);  //增加预约字段
            }

            //月卡会员
            if (card != null) {
                //入场超过上限禁止入场规则
                QCardLicensePlate cardLicensePlateEntity = QCardLicensePlate.cardLicensePlate;
                QParkingInfo parkingInfoEntity = QParkingInfo.parkingInfo;

                List<String> licensePlates = this.jpaQueryFactory.select(cardLicensePlateEntity.lisencePlate)
                        .from(cardLicensePlateEntity).where(cardLicensePlateEntity.cardId.eq(card.getId())).fetch();

                //查找指定月卡已停车数量
                long hasParkingCount = this.jpaQueryFactory.select(parkingInfoEntity.licensePlate.countDistinct()).from(parkingInfoEntity)
                        .where(parkingInfoEntity.licensePlate.in(licensePlates)
                                .and(parkingInfoEntity.status.eq(parkingInfoStatus.unfinishedStatus.getValue()))).fetchCount();

                if (card.getIsFixedSpace() != null && card.getIsFixedSpace().equals(0)) {//不是固定车位
                	
	                if (rule != null && rule.getInRule() == ParkingInRule.InRuleAsAmount.getTypeValue()) {
	                    //按超限设置禁止入场规则，查找上限设置
	                    rsData.put("isMember", MemberShip.MonthCard.getTypeValue());
	                    //超过限定上限
	                    if (hasParkingCount >= max) {
	                        rsData.put("isAuthorization", IsAuthorization.Close.getTypeValue());
	                        rsData.put("indentifyMessage", "月卡车已超限 禁止入场");
	                    } else if (card.getExpiryTime().before(new Date())) {
	                        rsData.put("isAuthorization", IsAuthorization.Open.getTypeValue());
	                        rsData.put("indentifyMessage", "月卡有效期至" + dayFormat.format(card.getExpiryTime()) + ",月卡已过期");
	                        Integer userId = null;
	                        if (user != null) {
	                            userId = user.getId();
	                            if (user.getIsBlack() == UserIsBlack.BlacklistUser.getTypeValue()) {//黑名单
	                                rsData.put("isAuthorization", IsAuthorization.Close.getTypeValue());
	                                rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.in.warning.message", carNum, "此车为黑名单车辆"));
	                            } else if (user.getState().equals(UserState.DisableState.getTypeValue())) {//会员禁用
	                                rsData.put("isAuthorization", IsAuthorization.Close.getTypeValue());
	                                rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.in.warning.message", carNum, "账户已被禁用"));
	                            } else {
	                                this.saveParkingInfo(userId, parkCode, appointmentId, carNum,
	                                        parkingInfoStatus.unfinishedStatus.getValue(), parkingDf.parse(parkingDate), LockType.UnLock.getTypeValue(),
	                                        roadId, plateType, carType);
	                            }
	                            rsData.put("isMember", MemberShip.MemberAndCard.getTypeValue());
	                        } else {
	                            this.saveParkingInfo(userId, parkCode, appointmentId, carNum,
	                                    parkingInfoStatus.unfinishedStatus.getValue(), parkingDf.parse(parkingDate), LockType.UnLock.getTypeValue(),
	                                    roadId, plateType, carType);
	                        }
	                    } else {
	                        //未超写入停车信息
	                        StringBuffer message = new StringBuffer();
	
	                        message.append("月卡车");
	                        message.append(carNum);
	                        rsData.put("isAuthorization", IsAuthorization.Open.getTypeValue());
	                        rsData.put("indentifyMessage", "月卡有效期至" + dayFormat.format(card.getExpiryTime()));
	                        Integer userId = null;
	                        if (user != null) { //没有超出显示的话，那就当成月卡车，不用判断黑名单等信息
	                            userId = user.getId();
	                            rsData.put("isMember", MemberShip.MemberAndCard.getTypeValue());
	                        }
	                        this.saveParkingInfo(userId, parkCode, appointmentId, carNum,
	                                parkingInfoStatus.unfinishedStatus.getValue(), parkingDf.parse(parkingDate),
	                                LockType.UnLock.getTypeValue(), roadId, plateType, carType);
	                        
	                        //判断月卡是否过期
	                        //未过期
	                        if (card.getExpiryTime().before(new Date())) {
	                            if (user != null) {//判断过期月卡用户是否是会员
	                                rsData.put("isMember", BaseConstant.MemberShip.MemberAndCard.getTypeValue());
	                                rsData.put("isAuthorization", ParkingConstant.IsAuthorization.Open.getTypeValue());
	                                rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.in.greeting.message", new GregorianCalendar().get(GregorianCalendar.AM_PM) == 0 ? "上午好" : "下午好", "会员车" + carNum, ""));
	
	                                SimpleDateFormat parkingDf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                                if (appointment != null) {
	                                    appointmentId = appointment.getId();
	                                }
	                                this.saveParkingInfo(user.getId(),
	                                        parkCode,
	                                        appointmentId,
	                                        carNum,
	                                        parkingInfoStatus.unfinishedStatus.getValue(),
	                                        parkingDf2.parse(parkingDate),
	                                        LockType.UnLock.getTypeValue(),
	                                        roadId,
	                                        plateType,
	                                        carType);
	
	                                return RetKit.okData(rsData);
	                            } else {//非会员 月卡过期
	                                rsData.put("isMember", MemberShip.MonthCard.getTypeValue());
	                                rsData.put("indentifyMessage", "月卡已过期 禁止入场");
	                                return RetKit.okData(rsData);
	                            }
	                        }
	                    }
	                } else if (rule != null && rule.getInRule() == ParkingInRule.InRuleAsTemp.getTypeValue()) {
	                    //超上限按临时车
	                    if (hasParkingCount >= max || card.getExpiryTime().before(new Date())) {
	                        rsData.put("isMember", MemberShip.UnMember.getTypeValue());
	                        rsData.put("isAuthorization", IsAuthorization.Open.getTypeValue());
	                        rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.in.greeting.message", ca.get(GregorianCalendar.AM_PM) == 0 ? "上午好" : "下午好", "临时车" + carNum, ""));
	                    } else {
	                        rsData.put("isMember", MemberShip.MonthCard.getTypeValue());
	                        rsData.put("isAuthorization", IsAuthorization.Open.getTypeValue());
	                        rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.in.greeting.message", ca.get(GregorianCalendar.AM_PM) == 0 ? "上午好" : "下午好", "月卡车" + carNum, ""));
	                    }
	
	                    Integer userId = null;
	                    if (user != null) {
	                        userId = user.getId();
	                        if (user.getIsBlack() == UserIsBlack.BlacklistUser.getTypeValue()) {//黑名单
	                            rsData.put("isAuthorization", IsAuthorization.Close.getTypeValue());
	                            rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.in.warning.message", carNum, "此车为黑名单车辆"));
	                        } else if (user.getState().equals(UserState.DisableState.getTypeValue())) {//会员禁用
	                            rsData.put("isAuthorization", IsAuthorization.Close.getTypeValue());
	                            rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.in.warning.message", carNum, "账户已被禁用"));
	                        } else {
	                            this.saveParkingInfo(userId, parkCode, appointmentId, carNum,
	                                    parkingInfoStatus.unfinishedStatus.getValue(), parkingDf.parse(parkingDate), LockType.UnLock.getTypeValue(),
	                                    roadId, plateType, carType);
	                        }
	                        rsData.put("isMember", MemberShip.MemberAndCard.getTypeValue());
	                    } else {
	                        this.saveParkingInfo(userId, parkCode, appointmentId, carNum,
	                                parkingInfoStatus.unfinishedStatus.getValue(), parkingDf.parse(parkingDate), LockType.UnLock.getTypeValue(),
	                                roadId, plateType, carType);
	                    }
                    }
                }else {//是固定车位
                	rsData.put("isMember", MemberShip.MonthCard.getTypeValue());
                	rsData.put("isAuthorization", IsAuthorization.Open.getTypeValue());
                	rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.in.greeting.message", new GregorianCalendar().get(GregorianCalendar.AM_PM) == 0 ? "上午好" : "下午好", "月卡车" + carNum, ""));
                	Integer userId = null;
                	if (user != null) {
                		userId = user.getId();
					}
                	this.saveParkingInfo(userId, parkCode, appointmentId, carNum,
                            parkingInfoStatus.unfinishedStatus.getValue(), parkingDf.parse(parkingDate), LockType.UnLock.getTypeValue(),
                            roadId, plateType, carType);
                	QGroundLock qGroundLock = QGroundLock.groundLock;
                	GroundLock groundLock = this.jpaQueryFactory.select(qGroundLock)
                			.from(qGroundLock).where(qGroundLock.licensePlate.eq(carNum)).fetchFirst();
                	if (groundLock != null) {
                		QLockCommond qCommond = QLockCommond.lockCommond;
                		LockCommond lCommond = this.jpaQueryFactory.select(qCommond)
                				.from(qCommond)
                				.where(qCommond.groundLockId.eq(groundLock.getId()).and(qCommond.type.eq(102))).fetchFirst();
                		if (lCommond != null) {//使用多线程打开地锁
                			Thread thread = new SubThread(groundLock.getGroundUid(), "102", lCommond.getCommond(), groundLockRepository, mainControlRepository);
                			thread.start();
                		}
					}
                }
            } else if (user != null) {//会员
                rsData.put("isMember", MemberShip.Member.getTypeValue());
                if (user.getIsBlack() == UserIsBlack.BlacklistUser.getTypeValue()) {//黑名单
                    rsData.put("isAuthorization", IsAuthorization.Close.getTypeValue());
                    rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.in.warning.message", carNum, "此车为黑名单车辆"));
                } else if (user.getState().equals(UserState.DisableState.getTypeValue())) {//会员禁用
                    rsData.put("isAuthorization", IsAuthorization.Close.getTypeValue());
                    rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.in.warning.message", carNum, "账户已被禁用"));
                } else {
                    rsData.put("isAuthorization", IsAuthorization.Open.getTypeValue());
                    rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.in.greeting.message", ca.get(GregorianCalendar.AM_PM) == 0 ? "上午好" : "下午好", "会员车" + carNum, ""));
                    this.saveParkingInfo(user.getId(), parkCode, appointmentId, carNum,
                            parkingInfoStatus.unfinishedStatus.getValue(), parkingDf.parse(parkingDate), LockType.UnLock.getTypeValue(),
                            roadId, plateType, carType);
                }


            } else {
                rsData.put("isAuthorization", IsAuthorization.Open.getTypeValue());
                rsData.put("isMember", MemberShip.UnMember.getTypeValue());
                rsData.put("indentifyMessage", MessageUtil.loadMessage("parking.in.greeting.message", ca.get(GregorianCalendar.AM_PM) == 0 ? "上午好" : "下午好", "临时车" + carNum, ""));
                this.saveParkingInfo(null, parkCode, appointmentId, carNum,
                        parkingInfoStatus.unfinishedStatus.getValue(), parkingDf.parse(parkingDate), LockType.UnLock.getTypeValue(),
                        roadId, plateType, carType);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return RetKit.fail();
        }
        //通知客户端车已入场
        if ((user != null) && (user.getRegistrationId() != null)
                && (user.getIsBlack() != UserIsBlack.BlacklistUser.getTypeValue())
                && (!user.getState().equals(UserState.DisableState.getTypeValue()))) {
            //黑名单用户与禁用用户不发送
            this.pushMessageToClient(user, carNum, BaseConstant.JpushType.CarIn.getTypeValue());
        }
        
        return RetKit.okData(rsData);
    }

    private void pushMessageToClient(User user, String carNum, Integer accessType) {
        try {
            //查询是否存在套牌车
            Integer cloneCarCount = parkingInfoRepository.findCloneCar(carNum);
            if (cloneCarCount == 2 || cloneCarCount > 2) {
                //通知客户端存在套牌车
                //JGuangPush.jiguangPush(user.getRegistrationId(), carNum+"车主，您好！您的车牌出现套牌车，这将对您的财产造成损失，更多详情请咨询客服。", 0, BaseConstant.JpushType.CloneCar.getTypeValue());
                Integer userId = user.getId();
                Integer parkingInfoId = parkingInfoRepository.findIdByUserId(userId);

                CloneCar cloneCar = new CloneCar();
                cloneCar.setParkingInfoId(parkingInfoId);
                cloneCar.setCreateTime(new Date());
                cloneCar.setStatus(CloneCarStatus.NotDeal.getTypeValue());
                cloneCarRepository.save(cloneCar);
            }
            //用户余额不足10元，通知客户端进行推送
            BigDecimal balance = user.getBalance();
            if (balance.intValue() < 10) {
                Message message = new Message();
                String userId = String.valueOf(user.getId());
                message.setTitle("温馨提示");
                message.setContent("您好！您的余额已不足10元，为了您能够正常使用，请及时充值。");
                message.setType(BaseConstant.MessageType.Specify.getTypeValue());
                message.setTarget(userId);
                message.setCreateTime(new Date());
                messageRepository.save(message);
                JGuangPush.jiguangPush(user.getRegistrationId(), "您好！您的余额已不足10元，为了您能够正常使用，请及时充值。", 0, 0);
            }

            //通知客户端
            if (accessType == BaseConstant.JpushType.CarIn.getTypeValue()) {//车已入场
                JGuangPush.jiguangPush(user.getRegistrationId(), carNum+"车牌"+BaseConstant.JpushType.CarIn.getTypeDescription(), 0, BaseConstant.JpushType.CarIn.getTypeValue());
            } else if (accessType == BaseConstant.JpushType.CarOut.getTypeValue()) {//车离场
                JGuangPush.jiguangPush(user.getRegistrationId(), carNum+"车牌"+BaseConstant.JpushType.CarOut.getTypeDescription(), 0, BaseConstant.JpushType.CarOut.getTypeValue());
            }
        } catch (Exception e) {
            throw new RuntimeException("registrationId is not null");
        }

    }

    /**
     * 保存停车信息
     *
     * @param userId
     * @param parkCode
     * @param appointId
     * @param licensePlate
     * @param status
     * @param parkDate
     * @param isLock
     * @param inRoadWayId
     * @param plateType
     * @param carType
     */
    public void saveParkingInfo(Integer userId, Integer parkCode, Integer appointId, String licensePlate,
                                Character status, Date parkDate, Integer isLock, Integer inRoadWayId, String plateType, String carType) {
        Date nowDate = new Date();
        ParkingInfo parkingInfoNew = parkingInfoRepository.findParkingInfoByBetweenFirstDate(licensePlate, getCurrentTime(), nowDate);
        if (parkingInfoNew == null) {
            ParkingInfo parkingInfo = new ParkingInfo();
            if (userId != null) {
                parkingInfo.setUserId(userId);
            }

            if (appointId != null) {
                parkingInfo.setAppointmentId(appointId);
                //更新预约状态
                QAppointment appointmentEntity = QAppointment.appointment;
                this.jpaQueryFactory.update(appointmentEntity).set(appointmentEntity.status,
                        AppointmentStatus.NormalParking.getTypeValue()).where(appointmentEntity.id.eq(appointId)).execute();
            }
            parkingInfo.setParkCode(parkCode);
            parkingInfo.setLicensePlate(licensePlate);
            parkingInfo.setStatus(status);
            parkingInfo.setParkDate(parkDate);
            parkingInfo.setIsLock(isLock);
            parkingInfo.setInRoadWayId(inRoadWayId);
            if (!StringUtils.isEmpty(plateType)) {
                parkingInfo.setPlateType(Integer.valueOf(plateType));
            }
            if (!StringUtils.isEmpty(carType)) {
                parkingInfo.setCarType(Integer.valueOf(carType));
            }
            // 存入场信息
            parkingInfoRepository.save(parkingInfo);
        }
    }

    /**
     * 计费
     *
     * @param userId
     * @param totalFee
     * @param parkingInfoId
     * @param parkingTimeLen
     * @param balance
     */
    private void generateOrder(Integer userId, BigDecimal totalFee, Integer parkingInfoId, Long parkingTimeLen, BigDecimal balance) {
        BigDecimal lastFee = new BigDecimal(0);
        SimpleDateFormat parkingDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //生成订单
        //往order表里生成定单
        Order order = new Order();
        order.setFee(totalFee);
//		order.setFinalFee(totalFee);
        order.setType(OrderType.Balance.getTypeValue());
        order.setParkingInfoId(parkingInfoId);
        if (userId != null) {
            order.setUserId(userId);
        }

        order.setIsTransfer(TransferStatus.UnExtracted.getStatusValue());
        order.setParkingTime(TimeUtils.getMillScondByMunites(parkingTimeLen).intValue());
        
/*        //查找优惠券
        //查询相关优惠减免，并去账号扣款写入账单
        QUserCoupon userCoupon = QUserCoupon.userCoupon;
        QCoupon coupon = QCoupon.coupon;
        //按时间排序，取优惠券在最前的
        UserCoupon userCouponPO = this.jpaQueryFactory.select(userCoupon).from(userCoupon, coupon)
                .where(userCoupon.userId.eq(userId)
                        .and(userCoupon.status.eq(CouponStatus.UnUsed.getStatusValue())
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
        }*/
        
      //优惠券
        QCoupon qCoupon = QCoupon.coupon;
        QUserCoupon qUserCoupon = QUserCoupon.userCoupon;
        Tuple tupleQuery = this.jpaQueryFactory.select(qCoupon.count,qCoupon.id,qCoupon.partDeduction,qCoupon.deductionType,
        		qCoupon.endNum,qUserCoupon.endNum,qUserCoupon.status,qUserCoupon.id)
        		.from(qUserCoupon).leftJoin(qCoupon).on(qCoupon.id.eq(qUserCoupon.couponId))
        		.where(qUserCoupon.status.eq(BaseConstant.UserCouponStatus.UnUse.getTypeValue()).and(qUserCoupon.userId.eq(userId))
        				.and(qCoupon.endTime.after(getCurrentTime()).or(qCoupon.endTime.isNull()))).limit(1).fetchFirst();
        Integer couponCount = 0;
        if (tupleQuery != null) {
            if (tupleQuery.get(qCoupon.deductionType)  == BaseConstant.DeductionType.AllDeduction.getTypeValue()) {//全抵扣
            	if (tupleQuery != null ) {
                	couponCount = tupleQuery.get(qCoupon.count);
                	UserCoupon userCoupon = userCouponRepository.findUserCouponByUserIdAndCounponIdAndStatus(userId, tupleQuery.get(qCoupon.id));
                	userCoupon.setUseTime(new Date());
                	userCoupon.setStatus(BaseConstant.UserCouponStatus.AlreadyUse.getTypeValue());
                	userCouponRepository.save(userCoupon);
                	
                	order.setDiscountAmount(new BigDecimal(tupleQuery.get(qCoupon.count)));
                    order.setCouponId(tupleQuery.get(qCoupon.id));
    			}
			}else {//多次抵扣
				if (tupleQuery != null) {
					if(tupleQuery.get(qUserCoupon.endNum).compareTo(0)==1) {
					couponCount = tupleQuery.get(qCoupon.partDeduction);
					this.jpaQueryFactory.update(qUserCoupon)
					.set(qUserCoupon.endNum, qUserCoupon.endNum.subtract(1))
					.set(qUserCoupon.useTime, getCurrentTime())
					.where(qUserCoupon.userId.eq(userId)
    						.and(qUserCoupon.couponId.eq(tupleQuery.get(qCoupon.id))
    								.and(qUserCoupon.id.eq(tupleQuery.get(qUserCoupon.id)))))
    				.execute();
					//在上方那个方法无法保存状态为0
					UserCoupon userCoupon = userCouponRepository.findById(tupleQuery.get(qUserCoupon.id)).get();
					userCoupon.setStatus(BaseConstant.UserCouponStatus.UnUse.getTypeValue());
					userCouponRepository.save(userCoupon);
					}else {
						couponCount = 0;
						this.jpaQueryFactory.update(qUserCoupon)
						.set(qUserCoupon.status, BaseConstant.UserCouponStatus.AlreadyUse.getTypeValue())
						.set(qUserCoupon.useTime, getCurrentTime())
						.where(qUserCoupon.userId.eq(userId)
	    						.and(qUserCoupon.couponId.eq(tupleQuery.get(qCoupon.id))
	    								.and(qUserCoupon.id.eq(tupleQuery.get(qUserCoupon.id)))))
	    				.execute();
					}
				}
				
				order.setDiscountAmount(new BigDecimal(tupleQuery.get(qCoupon.count)));
                order.setCouponId(tupleQuery.get(qCoupon.id));
			}
            BigDecimal last = new BigDecimal(couponCount);
          //停车费用：总费用-优惠券
            totalFee = totalFee.subtract(last);
		}
        order.setFinalFee(totalFee);

        //去账户扣款
        if (balance.compareTo(new BigDecimal(0)) == 1 || balance.compareTo(new BigDecimal(0)) == 0) {
            QUser user = QUser.user;
            if(order.getFinalFee().compareTo(BigDecimal.ZERO) == 1) {
            	BigDecimal nowBalance = balance.subtract(order.getFinalFee());
            	long effect = this.jpaQueryFactory.update(user).set(user.balance, nowBalance).where(user.id.eq(userId)).execute();
            }else {
            	BigDecimal nowBalance = balance.subtract(BigDecimal.ZERO);
            	long effect = this.jpaQueryFactory.update(user).set(user.balance, nowBalance).where(user.id.eq(userId)).execute();
            }
            /*if (userCouponPO != null) {
                this.jpaQueryFactory.update(userCoupon).set(userCoupon.status, UserCouponStatus.AlreadyUse.getTypeValue()).where(userCoupon.id.eq(userCouponPO.getId())).execute();
            }*/
            order.setPayTime(new Date());
            order.setStatus(PayStatus.Pay.getStatusValue());
        } else {//支付失败
            order.setPayTime(new Date());
            order.setStatus(PayStatus.PayFailed.getStatusValue());
        }
        orderRepository.save(order);
    }


    /**
     * 获取某停车场的所有车辆停车记录
     */
    @Override
    public RetKit getParkingInfoList(String parkingId, Integer pageSize, Integer pageNum, String licensePlate) {
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String, Object>> data = new ArrayList<>();
        QParkingInfo qParkingInfo = QParkingInfo.parkingInfo;
        QParking qParking = QParking.parking;
        List<Predicate> predicates = new ArrayList<>();
        //搜索参数
        if (!StrKit.isBlank(licensePlate)) {
            //车牌号码
            predicates.add(qParkingInfo.licensePlate.like("%" + licensePlate.toString() + "%"));
        }
        if (!StrKit.isBlank(parkingId)) {
            //车牌号码
            predicates.add(qParking.id.eq(Integer.parseInt(parkingId)));
        }
        //查询结果
        JPAQuery<Tuple> jPAQuery = jpaQueryFactory
                .select(qParkingInfo.licensePlate, qParkingInfo.parkDate, qParkingInfo.outDate, qParkingInfo.status, qParkingInfo.appointmentId)
                .from(qParkingInfo)
                .leftJoin(qParking).on(qParking.parkCode.eq(qParkingInfo.parkCode))
                .where(predicates.toArray(new Predicate[predicates.size()]))
                .orderBy(qParkingInfo.parkDate.desc())
                .offset(pageSize * pageNum).limit(pageSize);
        QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
        //将元组集合转成map集合
        for (Tuple tuple : queryResults.getResults()) {
            Map<String, Object> map = new HashMap<>();
            map.put("licensePlate", tuple.get(qParkingInfo.licensePlate));
            map.put("parkDate", spf.format(tuple.get(qParkingInfo.parkDate)));
            map.put("outDate", spf.format(tuple.get(qParkingInfo.outDate)));
            map.put("status", tuple.get(qParkingInfo.status));
            map.put("appointmentId", tuple.get(qParkingInfo.appointmentId));
            data.add(map);
        }
        Map<String, Object> content = new HashMap<>();
        content.put("content", data);//添加主体数据
        content.put("totalElements", queryResults.getTotal());//添加总条数
        if (queryResults.getTotal() % pageSize == 0) {
            content.put("totalPages", (queryResults.getTotal() / pageSize));//总页面数
        } else {
            content.put("totalPages", (queryResults.getTotal() / pageSize) + 1);//总页面数
        }
        return RetKit.okData(content);
    }

    /**
     * 获取当前时间的前一分钟
     *
     * @return
     */
    public Date getCurrentTime() {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -1);// 1分钟之前的时间
        Date beforeD = beforeTime.getTime();
        //String time = sdf.format(beforeD);
        return beforeD;
    }

    @Override
    public RetKit getParkingInfoList(Integer pageSize, Integer pageNum, Integer companyId, List<Map<String, Object>> para) {

        List<Map<String, Object>> data = new ArrayList<>();
        QParking qParking = QParking.parking;//停车场
        QParkingInfo qParkingInfo = QParkingInfo.parkingInfo;//停车场信息
        QUser qUser = QUser.user;//用户详情

        /**
         * 查询条件的谓语集合(where条件)
         */
        List<Predicate> predicates = new ArrayList<>();
        
        predicates.add(qParking.companyId.eq(companyId));
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

        //查询结果
        JPAQuery<Tuple> jPAQuery = jpaQueryFactory
                .select(qParking.id, qParking.parkName, qParking.provinceName, qParking.cityName, qParking.areaName, qParking.location,
                        qParkingInfo.id, qParkingInfo.licensePlate, qParkingInfo.parkDate, qParkingInfo.outDate, qUser.phone)
                .from(qParkingInfo)
                .leftJoin(qParking).on(qParking.parkCode.eq(qParkingInfo.parkCode))
                .leftJoin(qUser).on(qUser.id.eq(qParkingInfo.userId))
                .where(predicates.toArray(new Predicate[predicates.size()]))
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
        result.put("data", data);//添加主体数据
        result.put("totalElements", queryResults.getTotal());//添加总条数
        return RetKit.okData(result);
    }
    
}
