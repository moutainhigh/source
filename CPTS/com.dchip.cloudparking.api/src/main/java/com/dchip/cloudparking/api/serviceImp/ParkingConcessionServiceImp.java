package com.dchip.cloudparking.api.serviceImp;

import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.iRepository.IOrderRepository;
import com.dchip.cloudparking.api.iRepository.IParkingConcessionRepository;
import com.dchip.cloudparking.api.iRepository.IUserRepository;
import com.dchip.cloudparking.api.iService.IParkingConcessionService;
import com.dchip.cloudparking.api.iService.IParkingService;
import com.dchip.cloudparking.api.model.po.*;
import com.dchip.cloudparking.api.model.qpo.QCardLicensePlate;
import com.dchip.cloudparking.api.model.qpo.QCoupon;
import com.dchip.cloudparking.api.model.qpo.QFreeStandard;
import com.dchip.cloudparking.api.model.qpo.QParking;
import com.dchip.cloudparking.api.model.qpo.QParkingConcession;
import com.dchip.cloudparking.api.model.qpo.QParkingInfo;
import com.dchip.cloudparking.api.model.qpo.QUser;
import com.dchip.cloudparking.api.model.qpo.QUserCoupon;
import com.dchip.cloudparking.api.utils.*;
import com.dchip.cloudparking.api.utils.parkingfee.FeeModel;
import com.dchip.cloudparking.api.utils.parkingfee.FeeServiceKit;
import com.dchip.cloudparking.api.utils.parkingfee.FeeServiceUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ParkingConcessionServiceImp extends BaseService implements IParkingConcessionService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IParkingConcessionRepository parkingConcessionRepository;
    @Autowired
    private IOrderRepository orderRepository;

    /**
     * 车位租让 信息发布
     * @param parkingConcession
     * @return
     */
    @Override
    public RetKit publishConcessionMsg(ParkingConcession parkingConcession) {
        ParkingConcession one = parkingConcessionRepository.findSpace(parkingConcession.getParkingId(),parkingConcession.getLicensePlate());

        if(one != null ){
            RetKit.ok("不能重复发布信息!");
        }
        parkingConcessionRepository.save(parkingConcession);
        return RetKit.ok("发布车位共享信息成功!");
    }

    /**
     * 查找共享车位的停车场 - 模糊查询停车场名称
     *
     * @param keyword
     * @return
     */
    @Override
    public RetKit search(String keyword){
        List<Map<String, Object>> maps = parkingConcessionRepository.searchParkingSpace(keyword);
        List<Map<String, Object>> resultMaps = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> item : maps) {
            item.put("effectDuring", item.get("effectDuringTime") + " " + item.get("effectDuringDate"));
            if ((item.get("parkName") !=null && item.get("parkName").toString().contains(keyword) )|| (item.get("cost") !=null && item.get("cost").toString().contains(keyword))) {
                resultMaps.add(item);
            }
        }
        return RetKit.okData(resultMaps);
    }

    /**
     * 认领操作
     * @param parkingConcessionId
     * @param licensePlate
     * @param tenantId
     * @return
     */
    @Override
    public RetKit accept(Integer parkingConcessionId,String licensePlate,String tenantId) {
    	Optional<ParkingConcession> parkingConcessionOptional = parkingConcessionRepository.findById(parkingConcessionId);
    	if (parkingConcessionOptional.isPresent()) {
            ParkingConcession parkingConcession = parkingConcessionOptional.get();
            /**
             * 计算费用
             */
            String[] effectDuringDates = parkingConcession.getEffectDuringDate().trim().replaceAll(" ", "").split("~");
            String[] effectDuringTimes = parkingConcession.getEffectDuringTime().trim().replaceAll(" ", "").split("~");

            int diffDays = DateUtil.getDiffDays(DateUtil.dateToStamp(effectDuringDates[0],"yyyy-MM-dd"),DateUtil.dateToStamp(effectDuringDates[1],"yyyy-MM-dd"));
            Long diffTimePerDay = (DateUtil.dateToStamp(effectDuringTimes[1],"HH:mm:ss").getTime()-DateUtil.dateToStamp(effectDuringTimes[0],"HH:mm:ss").getTime());
            Long parkingTimeLen = diffTimePerDay * diffDays;
//            BigDecimal totalFee = new BigDecimal(parkingTimeLen/FeeServiceUtil.ONE_HOUR_TIME).multiply(parkingConcession.getCost());
//            if(parkingTimeLen%FeeServiceUtil.ONE_HOUR_TIME != 0){
//                totalFee.add(parkingConcession.getCost());
//            }

            /**
             * 会员
             */
            if(StrKit.notBlank(tenantId)){
                if(parkingConcession.getUserId() == Integer.parseInt(tenantId)){
                    return RetKit.fail("不能认领自己发布的信息");
                }
                /**
                 * 余额
                 */
                User user = null;
                Optional<User> optUser = userRepository.findById(Integer.parseInt(tenantId));
                if(!optUser.isPresent()){
                    return RetKit.fail("不是会员车牌");
                }else{
                    user=optUser.get();
                }
//                double totalDouble = totalFee.doubleValue();
                double userDouble = user.getBalance().doubleValue();
//                if( totalDouble > userDouble){
//                    return RetKit.fail("余额不足");
//                }
                if( parkingConcession.getCost().doubleValue() > userDouble){
                    return RetKit.fail("余额不足");
                }
                user.getLicensePlat();
                parkingConcessionRepository.accept(parkingConcessionId, Integer.parseInt(tenantId),licensePlate);
                generateOrder(Integer.parseInt(tenantId), parkingConcession.getCost(), parkingTimeLen,  user.getBalance(),parkingConcessionId);
//              parkingConcessionRepository.accept(Integer.parseInt(parkingConcessionId), Integer.parseInt(tenantId));
//              generateOrder( Integer.parseInt(tenantId), totalFee, parkingTimeLen,  user.getBalance());
            }else{
                /**
                 * 临时用户
                 */
//                parkingConcessionRepository.accept(Integer.parseInt(parkingConcessionId), Integer.parseInt(tenantId),licensePlate);
//                generateOrder( null, totalFee, parkingTimeLen,  new BigDecimal(0));
            }
    		return RetKit.ok("认领成功！");
		}else {
			return RetKit.fail("该出租车位信息不存在！");
		}
    }

    /**
     * 我的认领/我的发布/包含距离的列表
     * @param tenantId
     * @return
     */
    @Override
    public Object queryList(String userId,String tenantId,Integer pageSize,Integer pageNum,List<Map<String,Object>> para) {
        List<Map<String, Object>> data = new ArrayList<>();
        QParkingConcession qParkingConcession = QParkingConcession.parkingConcession;
        QParking qParking = QParking.parking;
        QUser qUser = QUser.user;
        /**
         * 查询条件的谓语集合(where条件)
         */
        List<Predicate> predicates = new ArrayList<>();
        if(userId != null && !userId.equals("")){
            predicates.add(qParkingConcession.userId.eq(Integer.parseInt(userId)));
        }
        if(tenantId != null && !tenantId.equals("")){
            predicates.add(qParkingConcession.tenantId.eq(Integer.parseInt(tenantId)));
        }

        Map<String,Double> positionMap = new HashMap<String,Double>();
        if(para !=null && !para.isEmpty()) {
            for (Map<String, Object> map : para) {
                //搜索参数
                if(map.get("keyword")!=null) {
                    //停车场名称
                    predicates.add(QParking.parking.parkName.like("%"+map.get("keyword").toString()+"%"));
                }
                if(map.get("longitude")!=null) {
                    positionMap.put("longitude",Double.parseDouble(map.get("longitude").toString()));
                }
                if(map.get("latitude")!=null) {
                    positionMap.put("latitude",Double.parseDouble(map.get("latitude").toString()));
                }
            }
        }


        predicates.add(qParkingConcession.status.notIn(
//                BaseConstant.ParkingConcession.CheckPendingStatus.getTypeValue(),//排除：待审核
//                BaseConstant.ParkingConcession.NotPassStatus.getTypeValue(),       //排除：不通过
                BaseConstant.ParkingConcession.DeletedStatus.getTypeValue())       //排除：软删除
        );
        //排除：停车场id为0
        predicates.add(qParkingConcession.parkingId.notIn(0));

        //查询结果
        JPAQuery<Tuple> jPAQuery = jpaQueryFactory
                .select(qParkingConcession.id,
                        qParkingConcession.effectDuringTime,
                        qParkingConcession.effectDuringDate,
                        qParkingConcession.cost,

                        qParkingConcession.parkingId,
                        qParking.parkName,//停车场名称
                        qParking.provinceName,//省市
                        qParking.cityName,//城市
                        qParking.areaName,//地区名称
                        qParking.location,//地点
                        qParking.longitude,//经度
                        qParking.latitude,//纬度

                        qParkingConcession.userId,//发布租赁信息者id
                        qParkingConcession.tenantId,//承租者id
                        qUser.carOwnerName,//发布租赁信息者 姓名

                        qParkingConcession.spaceNo,//车位号
                        qParkingConcession.licensePlate,//发布时填写的车牌号
                        qParkingConcession.publishTime,//发布时间,时间戳
                        qParkingConcession.rentTime,
                        qParkingConcession.status)//租赁车位的状态,0-可用,1-过期,(-1)-禁用,(-2)-软删
                .from(qParkingConcession)
                .leftJoin(qUser).on(qParkingConcession.userId.eq(qUser.id))
                .leftJoin(qParking).on(qParkingConcession.parkingId.eq(qParking.id))
                .where(predicates.toArray(new Predicate[predicates.size()]))
                .orderBy(qParkingConcession.publishTime.desc())
                .offset(pageNum * pageSize).limit(pageSize);

        QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
        //将元组集合转成map集合
        for (Tuple tuple : queryResults.getResults()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", tuple.get(qParkingConcession.id));
            map.put("effectDuringTime", tuple.get(qParkingConcession.effectDuringTime));
            map.put("effectDuringDate", tuple.get(qParkingConcession.effectDuringDate));
            map.put("hourCost", tuple.get(qParkingConcession.cost));

            /**
             * 计算费用
             */
            String[] effectDuringDates = tuple.get(qParkingConcession.effectDuringDate).trim().replaceAll(" ", "").split("~");
            String[] effectDuringTimes = tuple.get(qParkingConcession.effectDuringTime).trim().replaceAll(" ", "").split("~");
            int diffDays = DateUtil.getDiffDays(DateUtil.dateToStamp(effectDuringDates[0],"yyyy-MM-dd"),DateUtil.dateToStamp(effectDuringDates[1],"yyyy-MM-dd"));
            Long diffTimePerDay = (DateUtil.dateToStamp(effectDuringTimes[1],"HH:mm:ss").getTime()-DateUtil.dateToStamp(effectDuringTimes[0],"HH:mm:ss").getTime());
            Long parkingTimeLen = diffTimePerDay * diffDays;
            BigDecimal totalFee = new BigDecimal(parkingTimeLen/FeeServiceUtil.ONE_HOUR_TIME).multiply(tuple.get(qParkingConcession.cost));
            if(parkingTimeLen%FeeServiceUtil.ONE_HOUR_TIME != 0){
                totalFee.add(tuple.get(qParkingConcession.cost));
            }
            map.put("totalCost", totalFee);

            map.put("parkingId", tuple.get(qParkingConcession.parkingId));
            map.put("userId", tuple.get(qParkingConcession.userId));
            map.put("tenantId", tuple.get(qParkingConcession.tenantId));

            map.put("carOwnerName", tuple.get(qUser.carOwnerName));
            map.put("spaceNo", tuple.get(qParkingConcession.spaceNo));
            map.put("licensePlate", tuple.get(qParkingConcession.licensePlate));

            map.put("parkName", tuple.get(qParking.parkName));
            map.put("provinceName", tuple.get(qParking.provinceName));
            map.put("cityName", tuple.get(qParking.cityName));
            map.put("areaName", tuple.get(qParking.areaName));
            map.put("location", tuple.get(qParking.location));
            map.put("longitude", tuple.get(qParking.longitude));
            map.put("latitude", tuple.get(qParking.latitude));
            map.put("managerName", tuple.get(qParking.managerName));

            /**
             * 包含距离
             */
            try {
                if(positionMap.size() == 2){
                    double distance = DistanceUtil.GetDistance(positionMap.get("longitude"), positionMap.get("latitude"), Double.parseDouble(tuple.get(qParking.longitude)), Double.parseDouble(tuple.get(qParking.latitude)));
                    map.put("distance", distance);
                }
            }catch (Exception e){
                map.put("distance", "");
            }

            Date date = tuple.get(qParkingConcession.publishTime);
            if(date != null){
                map.put("publishTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            }else{
                map.put("publishTime", "");
            }
            if (userId != null && !userId.equals("")) {
	            if (tuple.get(qParkingConcession.status) == 1) {
					Integer tId = tuple.get(qParkingConcession.tenantId);
					if (tId != null) {
						Optional<User> userOptional = userRepository.findById(tId);
						if (userOptional.isPresent()) {
							User user = userOptional.get();
							String licensePlat = user.getLicensePlat();
							map.put("tenantLicensePlat", licensePlat);
						}
					}
				}
            }

            Date rentTime = tuple.get(qParkingConcession.rentTime);
            if(rentTime != null){
                map.put("rentTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rentTime));
            }else{
                map.put("rentTime", "");
            }
            map.put("status", tuple.get(qParkingConcession.status));
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
     * 我的发布 硬删
     * @param id
     * @return
     */
    @Override
    public RetKit delMsg(Integer id){
        try {
            parkingConcessionRepository.deleteById(id);
            return RetKit.ok();
        }catch (Exception e){
            return RetKit.fail();
        }
    }
    
    /**
     * 共享车位平台列表
     */
	@Override
	public RetKit getAllPublishList(Integer pageSize, Integer pageNum,List<Map<String,Object>> para) {
		List<Map<String, Object>> data = new ArrayList<>();
        QParkingConcession qParkingConcession = QParkingConcession.parkingConcession;
        QParking qParking = QParking.parking;
        QUser qUser = QUser.user;
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(qParkingConcession.status.eq(0));
        if(para !=null && !para.isEmpty()) {
            for (Map<String, Object> map : para) {
                //搜索参数
                if(map.get("searchParkName")!=null) {
                    //停车场名称
                    predicates.add(qParking.parkName.like("%"+map.get("searchParkName").toString()+"%"));
                }
            }
        }
        //查询结果
        JPAQuery<Tuple> jPAQuery = jpaQueryFactory
                .select(qParkingConcession.id,
                        qParkingConcession.effectDuringTime,//有效时间段
                        qParkingConcession.effectDuringDate,//有效年月日段
                        qParkingConcession.cost,//金额
                        qParkingConcession.parkingId,
                        qParking.parkName,//停车场名称
                        qParking.provinceName,//省市
                        qParking.cityName,//城市
                        qParking.areaName,//地区名称
                        qParking.location,//地点
                        qParking.longitude,//经度
                        qParking.latitude,//纬度
                        qParkingConcession.userId,//发布租赁信息者id
                        qUser.carOwnerName,//发布租赁信息者 姓名
                        qParkingConcession.spaceNo,//车位号
                        qParkingConcession.licensePlate,//发布时填写的车牌号
                        qParkingConcession.publishTime,//发布时间,时间戳
                        qParkingConcession.rentTime,//承租时间
                        qParkingConcession.status)//租赁车位的状态,0-可用,1-过期,(-1)-禁用,(-2)-软删
                .from(qParkingConcession)
                .leftJoin(qUser).on(qParkingConcession.userId.eq(qUser.id))
                .leftJoin(qParking).on(qParkingConcession.parkingId.eq(qParking.id))
                .where(predicates.toArray(new Predicate[predicates.size()]))
                .orderBy(qParkingConcession.publishTime.desc())
                .offset(pageSize * pageNum).limit(pageSize);
        
        QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
        //将元组集合转成map集合
        for (Tuple tuple : queryResults.getResults()) {
            Map<String, Object> map = new HashMap<>();
            map.put("parkingConcessionId", tuple.get(qParkingConcession.id));
            map.put("effectDuringTime", tuple.get(qParkingConcession.effectDuringTime));
            map.put("effectDuringDate", tuple.get(qParkingConcession.effectDuringDate));
            map.put("cost", tuple.get(qParkingConcession.cost));
            map.put("parkingId", tuple.get(qParkingConcession.parkingId));
            map.put("userId", tuple.get(qParkingConcession.userId));

            map.put("carOwnerName", tuple.get(qUser.carOwnerName));
            map.put("spaceNo", tuple.get(qParkingConcession.spaceNo));
            map.put("licensePlate", tuple.get(qParkingConcession.licensePlate));

            map.put("parkName", tuple.get(qParking.parkName));
            map.put("provinceName", tuple.get(qParking.provinceName));
            map.put("cityName", tuple.get(qParking.cityName));
            map.put("areaName", tuple.get(qParking.areaName));
            map.put("location", tuple.get(qParking.location));
            map.put("longitude", tuple.get(qParking.longitude));
            map.put("latitude", tuple.get(qParking.latitude));

            Date date = tuple.get(qParkingConcession.publishTime);
            if(date != null){
                map.put("publishTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            }else{
                map.put("publishTime", "");
            }
            map.put("rentTime", tuple.get(qParkingConcession.rentTime));
            map.put("status", tuple.get(qParkingConcession.status));
            data.add(map);
        }

        Map<String,Object> content = new HashMap<>();
        content.put("content",data);//添加主体数据
        content.put("totalElements",queryResults.getTotal());//添加总条数

        if(queryResults.getTotal() % pageSize == 0 ) {
            content.put("totalPages", (queryResults.getTotal() / pageSize));//总页面数
        }else {
            content.put("totalPages", (queryResults.getTotal() / pageSize)+1 );//总页面数
        }
        return RetKit.okData(content);
    }
	
	/**
	 * 取消租赁车位
	 */
	@Override
	public RetKit cancelRent(String parkingConcessionIdStr) {
//		try {
//			String startDate = effectDuringDate.substring(0, 10);
//			String startTime = effectDuringTime.substring(0, 8);
//			String dateTime = startDate+" "+startTime;
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date date = new Date();
//			if (date.before(sdf.parse(dateTime)) ) {
//				Optional<ParkingConcession> parkingConcessionOptional = parkingConcessionRepository.findById(Integer.parseInt(parkingConcessionId));
//				if (parkingConcessionOptional.isPresent()) {
//					parkingConcessionRepository.changeStatus(parkingConcessionId);
//					return RetKit.ok();
//				}else {
//					return RetKit.fail("该出租车位信息不存在！");
//				}
//			}else {
//				return RetKit.fail("已超过时间范围，取消失败！"); 
//			}
//		} catch (Exception e) {
//			return RetKit.fail();
//		}
		try {
			Integer parkingConcessionId = Integer.parseInt(parkingConcessionIdStr);
			Optional<ParkingConcession> parkingConcessionOptional = parkingConcessionRepository.findById(parkingConcessionId);
			if (parkingConcessionOptional.isPresent()) {
				ParkingConcession pConcession = parkingConcessionOptional.get();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateTime = sdf.format(new Date());
				Date cancelTime = sdf.parse(dateTime);
				if (cancelTime.getTime()-pConcession.getRentTime().getTime() <= 600000) {
					User leaseUser = userRepository.findUserByCarNum(pConcession.getLicensePlate());//出租车位的会员
					User rentUser = userRepository.findUserByCarNum(pConcession.getTenantPlate());//承租车位的会员
					if (leaseUser != null) {
						leaseUser.setBalance(leaseUser.getBalance().subtract(pConcession.getCost()));
						userRepository.save(leaseUser);
					}else {
						return RetKit.fail(pConcession.getLicensePlate()+"用户不存在,取消操作失败！");
					}
					if (rentUser != null) {
						rentUser.setBalance(rentUser.getBalance().add(pConcession.getCost()));
						userRepository.save(rentUser);
					}else {
						return RetKit.fail(pConcession.getTenantPlate()+"用户不存在,取消操作失败！");
					}
					pConcession.setRentTime(null);
					pConcession.setStatus(BaseConstant.ParkingConcession.NotUsedStatus.getTypeValue());
					pConcession.setTenantId(0);
					pConcession.setTenantPlate(null);
					parkingConcessionRepository.save(pConcession);
				}else {
					return RetKit.fail("已超过10分钟，不能取消车位！");
				}
			}else {
				return RetKit.fail("该出租车位信息不存在！");
			}
			return RetKit.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return RetKit.fail();
		}
	}

    /**
     * 根据车牌获取是否已发布信息
     * @param licensePlate
     * @return
     */
    @Override
    public Integer getPublishCountByPlate(String licensePlate, Integer parkingId){
        return parkingConcessionRepository.getPublishCountByPlate(licensePlate,parkingId);
    }

    /**
     * 查找发布消息中的停车位
     * @param parkingId
     * @param spaceNo
     * @return
     */
    @Override
    public Integer findParkingSpaceNo(Integer parkingId, String spaceNo){
        return parkingConcessionRepository.findParkingSpaceNo(parkingId, spaceNo);
    }


    /**
     * 根据停车场id和用户id查找租赁信息
     * @param parkingId
     * @param carNum
     * @param tenant
     * @return
     */
    @Override
    public ParkingConcession findAcceptMsg(Integer parkingId, String carNum,User tenant){
        /**
         * 若为会员，判断是否租让了车位
         */
            return parkingConcessionRepository.findAcceptMsg(parkingId,tenant.getId());
        /**
         * 若为临时车，判断是否租用了车位
         */
/*        else {
            return parkingConcessionRepository.findAcceptMsg(parkingId,carNum.toUpperCase());
        }*/
    }

    /**
     * 计费
     * @param userId
     * @param totalFee
     * @param parkingTimeLen
     * @param balance
     * @param parkingConcessionId 
     */
    private void generateOrder(Integer userId, BigDecimal totalFee, Long parkingTimeLen, BigDecimal balance, Integer parkingConcessionId) {
        //生成订单
        //往order表里生成定单
        Order order = new Order();
        order.setFee(totalFee);
        order.setFinalFee(totalFee);
//      order.setFinalFee(totalFee);
        order.setType(BaseConstant.OrderType.Balance.getTypeValue());
        if(userId != null) {
            order.setUserId(userId);
        }
        order.setParkingConcessionId(parkingConcessionId);
        order.setIsTransfer(BaseConstant.TransferStatus.UnExtracted.getStatusValue());
        order.setParkingTime(TimeUtils.getMillScondByMunites(parkingTimeLen).intValue());
        //查找优惠券
        //查询相关优惠减免，并去账号扣款写入账单
/*        QUserCoupon userCoupon = QUserCoupon.userCoupon;
        QCoupon coupon = QCoupon.coupon;
        //按时间排序，取优惠券在最前的
        UserCoupon userCouponPO = this.jpaQueryFactory.select(userCoupon).from(userCoupon, coupon)
                .where(userCoupon.userId.eq(userId)
                        .and(userCoupon.status.eq(BaseConstant.CouponStatus.UnUsed.getStatusValue())
                                .and(userCoupon.endDate.after(new Date()))
                                .and(userCoupon.couponId.eq(coupon.id))
                                .andAnyOf(coupon.endTime.isNull(), coupon.endTime.after(new Date()))))
                .orderBy(userCoupon.endDate.asc()).fetchFirst();
        //有优惠券
        if(userCouponPO != null) {
            Coupon couponPO = this.jpaQueryFactory.select(coupon).from(coupon).where(coupon.id.eq(userCouponPO.getCouponId())).fetchOne();
            if(couponPO != null) {
                BigDecimal lastFee = new BigDecimal(0);
                lastFee = totalFee.subtract(new BigDecimal(couponPO.getCount()));
                order.setDiscountAmount(new BigDecimal(couponPO.getCount()));
                order.setCouponId(couponPO.getId());
                order.setFinalFee(lastFee);
            }
        }else {//无优惠券
            order.setDiscountAmount(new BigDecimal(0));
            order.setFinalFee(totalFee);
        }*/

        //去账户扣款
        if(balance.compareTo(new BigDecimal(0)) == 1 || balance.compareTo(new BigDecimal(0)) == 0) {
            QUser user = QUser.user;
            this.jpaQueryFactory.update(user).set(user.balance, balance.subtract(order.getFinalFee())).where(user.id.eq(userId)).execute();
            order.setPayTime(new Date());
            order.setStatus(BaseConstant.PayStatus.Pay.getStatusValue());
        }else {//支付失败
            order.setPayTime(new Date());
            order.setStatus(BaseConstant.PayStatus.PayFailed.getStatusValue());
        }
        orderRepository.save(order);
    }
}
