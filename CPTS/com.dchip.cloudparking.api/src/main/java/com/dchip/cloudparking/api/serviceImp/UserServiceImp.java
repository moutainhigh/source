package com.dchip.cloudparking.api.serviceImp;

import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.iRepository.*;
import com.dchip.cloudparking.api.iService.IAppointmentService;
import com.dchip.cloudparking.api.iService.IRechargeLogService;
import com.dchip.cloudparking.api.iService.IUserService;
import com.dchip.cloudparking.api.model.po.*;
import com.dchip.cloudparking.api.model.qpo.QCoupon;
import com.dchip.cloudparking.api.model.qpo.QUserCoupon;
import com.dchip.cloudparking.api.utils.HashKit;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;
import com.dchip.parking.api.util.jpush.JGuangPush;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImp extends BaseService implements IUserService {

	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private ILoginLogRepository loginLogRepository;
	@Autowired
	private ISessionRepository sessionRepository;
	@Autowired
	private IOrderRepository orderRepository;
	@Autowired
	private ICouponRepository couponRepository;
	@Autowired
	private IRechargeLogService rechangeBillService;
	@Autowired
	private IMemberRuleRepository memberRuleRepository;
	@Autowired
	private IParkingInfoRepository parkingInfoRepository;
	@Autowired
	private IUserCouponRepository userCouponRepository;
	@Autowired
	private IAppointmentService appointmentService;
	@Autowired
	private IParkingRepository parkingRepository;
	@Autowired
	private IRechargeLogRepository rechargeLogRepository;
	@Autowired
	private IActivityRepository activityRepository;
	/**
	 * 普通用户登录操作
	 */
	public RetKit userLoginOperation(String phone, String ip, String registrationId, Integer platform,
			String licensePlat) {
		String token = StrKit.getRandomUUID();
		try {
			String stateFlag = userRepository.findUserByState(licensePlat);
			Date nowTime = new Date();
			User user = new User();
			Integer initUserFalseReportNumber = 0; // 用户初始化谎报次数
			if (StrKit.notBlank(stateFlag)) { // 有这个用户，执行登录操作
				if (stateFlag.equals(BaseConstant.UserState.DisableState.getTypeValue())) { // 用户是禁用状态，不能登录
					return RetKit.fail("此用户被禁用，无法登录");
				}

//				if (Integer.parseInt(stateFlag) == BaseConstant.SoftDelete.delete.getTypeValue()) { // 用户被删除
//					return RetKit.fail("此用户被删除，无法登录");
//				}

				user = userRepository.findUserDetailsByLicensePlat(licensePlat);
				// 判断手机号码和车牌
				if (!user.getPhone().equals(phone)) {
					return RetKit.fail("手机号码不匹配，用户已注册");
				}
				user.setLatestLoginTime(nowTime);
				user.setRegistrationId(registrationId);
				user = userRepository.save(user);

				Session session = sessionRepository.findSessionByUserId(user.getId());
				if (session != null) {
					session.setPlatform(platform);
					session.setToken(token);
					sessionRepository.save(session);
				} else {
					// 写入SessionId
					session = new Session();
					session.setPlatform(platform);
					session.setToken(token);
					session.setUserId(user.getId());
					session.setType(BaseConstant.SessionUserType.NormalUser.getTypeValue());
					sessionRepository.save(session);
				}
			} else { // 没有这个用户，执行注册操作 ,APP新增用户的字段有手机号，密码，类型，注册时间，最后登录时间
				String nickname = "用户" + (phone.toString()); // 手机端注册，默认用户名为“用户+手机号+车牌”，如：用户13XXXXXXXXX
				user.setLicensePlat(licensePlat); // 保存车牌号
				user.setNickname(nickname.getBytes());
				user.setPhone(phone);
				user.setPwd(HashKit.md5(phone));
				user.setCreateTime(nowTime); // 注册时间
				user.setLatestLoginTime(nowTime); // 最后登录时间
				user.setType(BaseConstant.UserType.FrontUser.getTypeValue()); // 用户类型
				user.setState(BaseConstant.UserState.EnabledState.getTypeValue()); // 用户启用状态
				user.setFalseReportNumber(initUserFalseReportNumber); // 用户谎报次数
				user.setRegistrationId(registrationId); // 极光推送
				user.setBalance(new BigDecimal(0)); // 余额
				user.setMemberId(BaseConstant.UserGrade.InitMember.getTypeValue()); // 用户会员等级 --初始为“已注册未充值用户”
				user.setIsBlack(BaseConstant.UserIsBlack.NormalUser.getTypeValue()); // 用户是否为黑名单 -- 初始为正常用户
				user.setIsAuthentication(BaseConstant.UserAuthenticationStatus.initState.getTypeValue()); // 认证状态，初始化为未认证
				user = userRepository.save(user);

				// 写入SessionId
				Session session = new Session();
				session.setPlatform(platform);
				session.setToken(token);
				session.setUserId(user.getId());
				sessionRepository.save(session);
			}
			// 写入登录日志
			LoginLog log = new LoginLog();
			log.setUserId(user.getId());
			log.setLoginAt(new Date());
			log.setIp(ip);
			log.setSourceFlag(BaseConstant.LoginSourceFlag.AppFlag.getTypeValue());
			loginLogRepository.save(log);

			// 注册环信账号

			Map<String, Object> userLoginDetails = new HashMap<>();
			userLoginDetails.put("accessToken", token);
			userLoginDetails.put("userId", user.getId());
			return RetKit.okData(userLoginDetails);
		} catch (Exception e) {
			return RetKit.fail("登录失败");
		}
	}

	/**
	 * 演示帐号登录操作
	 */
	public RetKit DemoUserLoginOperation(String phone, String ip, String registrationId, Integer platform,
			String licensePlat) {
		String token = BaseConstant.DemoAccounts.demoToken.getTypeValue();
		try {
			String stateFlag = userRepository.findUserByState(licensePlat);
			Date nowTime = new Date();
			User user = new User();
			Integer initUserFalseReportNumber = 0; // 用户初始化谎报次数
			if (StrKit.notBlank(stateFlag)) { // 有这个用户，执行登录操作
				if (stateFlag.equals(BaseConstant.UserState.DisableState.getTypeValue())) { // 用户是禁用状态，不能登录
					return RetKit.fail("此用户被禁用，无法登录");
				}

				user = userRepository.findUserDetailsByLicensePlat(licensePlat);
				user.setLatestLoginTime(nowTime);
				user.setRegistrationId(registrationId);
				user = userRepository.save(user);
				Session session = sessionRepository.findSessionByUserId(user.getId());
				if (session == null) {
					// 写入SessionId
					session = new Session();
					session.setPlatform(platform);
					session.setToken(token);
					session.setUserId(user.getId());
					session.setType(BaseConstant.SessionUserType.NormalUser.getTypeValue());
					sessionRepository.save(session);
				}

			} else { // 没有这个用户，执行注册操作 ,APP新增用户的字段有手机号，密码，类型，注册时间，最后登录时间
				String nickname = BaseConstant.DemoAccounts.demoUserName.getTypeValue(); // 用户名为“演示帐号”
				user.setLicensePlat(licensePlat); // 保存车牌号
				user.setNickname(nickname.getBytes());
				user.setPhone(BaseConstant.DemoAccounts.demoPhone.getTypeValue()); // 演示帐号的号码
				user.setPwd(HashKit.md5(BaseConstant.DemoAccounts.demoPhone.getTypeValue()));
				user.setCreateTime(nowTime); // 注册时间
				user.setLatestLoginTime(nowTime); // 最后登录时间
				user.setType(BaseConstant.UserType.FrontUser.getTypeValue()); // 用户类型
				user.setState(BaseConstant.UserState.EnabledState.getTypeValue()); // 用户启用状态
				user.setFalseReportNumber(initUserFalseReportNumber); // 用户谎报次数
				user.setRegistrationId(registrationId); // 极光推送
				user.setBalance(new BigDecimal(0)); // 余额
				user.setMemberId(BaseConstant.UserGrade.InitMember.getTypeValue()); // 用户会员等级 --初始为“已注册未充值用户”
				user.setIsBlack(BaseConstant.UserIsBlack.NormalUser.getTypeValue()); // 用户是否为黑名单 -- 初始为正常用户
				user.setIsAuthentication(BaseConstant.UserAuthenticationStatus.initState.getTypeValue()); // 认证状态，初始化为未认证
				user = userRepository.save(user);

				// 写入SessionId
				Session session = new Session();
				session.setPlatform(platform);
				session.setToken(token);
				session.setUserId(user.getId());
				sessionRepository.save(session);
			}
			// 写入登录日志
			LoginLog log = new LoginLog();
			log.setUserId(user.getId());
			log.setLoginAt(new Date());
			log.setIp(ip);
			loginLogRepository.save(log);

			// 注册环信账号

			Map<String, Object> userLoginDetails = new HashMap<>();
			userLoginDetails.put("accessToken", token);
			userLoginDetails.put("userId", user.getId());
			return RetKit.okData(userLoginDetails);
		} catch (Exception e) {
			return RetKit.fail("登录失败");
		}
	}

	@Override
	public RetKit getTotalScore(String userId) {
		if (StrKit.isBlank(userId)) {
			return RetKit.fail("用户id不能为空");
		}
		return RetKit.okData(userRepository.findTotalScore(userId));
	}

	@Override
	public RetKit getScoreDetail(String userId, String year, String month) {
		if (StrKit.isBlank(userId)) {
			return RetKit.fail("用户id不能为空");
		}
		if (StrKit.isBlank(year)) {
			return RetKit.fail("年份不能为空");
		}
		if (StrKit.isBlank(month)) {
			return RetKit.fail("月份不能为空");
		}
		Map<String, Object> result = new HashMap<>();
		result.put("detail", userRepository.findScoreDetail(userId, year, month));
		result.put("totalScore", userRepository.findTotalScore(userId, year, month));
		return RetKit.okData(result);
	}

	/**
	 * 查看登录token是否过期
	 */
	@Override
	public Session findSessionByToken(String token) {
		return sessionRepository.findSessionByToken(token);
	}
    /**
     * 查看登录token是否过期
     */
    @Override
    public Session findSessionByTokenAndType(String token,int type){
	    return sessionRepository.findSessionByTokenAndType(token,type);
    }
	/**
	 * 用户退出登录操作
	 */
	public RetKit userlogOut(Session session) {
		try {
			// sessionRepository.delete(session);
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail("退出登录失败");
		}
	}

	@Override
	public RetKit checkDiscounts(String userId, String amount) {
		List<Map<String, Object>> datas = userRepository.checkDiscounts(userId);
		List<Map<String, Object>> result = new ArrayList<>();
		for (Map<String, Object> map : datas) {
			Map<String, Object> m = new HashMap<>();
			// 优惠券id
			m.put("userCouponId", map.get("userCouponId"));
			// 应付款
			m.put("payable", amount);
			if (map.get("type").toString().equals(BaseConstant.ActivityType.Present.getTypeValue())) {
				// 备注，送多少
				m.put("remark", "送" + map.get("discount"));
				// 实付款
				m.put("actualPayment", amount);
				BigDecimal obtain = (new BigDecimal(amount)).add(new BigDecimal(map.get("discount").toString()));
				// 实际得到的
				m.put("obtain", obtain);

			} else if (map.get("type").toString().equals(BaseConstant.ActivityType.Reduced.getTypeValue())) {
				// 备注，减多少
				m.put("remark", "减" + map.get("discount"));
				// 实付款
				BigDecimal actualPayment = (new BigDecimal(amount))
						.subtract(new BigDecimal(map.get("discount").toString()));
				actualPayment = actualPayment.max(new BigDecimal(0.00));
				m.put("actualPayment", actualPayment);
				m.put("obtain", amount);
			} else {
				// 备注,送优惠券
				m.put("remark", "送优惠券");
				// 实付款
				m.put("actualPayment", amount);
				m.put("obtain", amount);

			}
			result.add(m);
		}
		return RetKit.okData(result);
	}

	/**
	 * 账户余额查询接口 by 小梁
	 */
	public RetKit getBalance(Integer userId) {
		try {
			Optional<User> userOpt = userRepository.findById(userId);
			Map<String, Object> map = new HashMap<>();
			if (userOpt.get().getBalance() != null) {
				map.put("balance", userOpt.get().getBalance());
			} else {
				map.put("balance", "0.00");
			}
			Map<String, Object> sumFee = orderRepository.sumFee(userOpt.get().getId());
			if (sumFee.get("sumFee") != null) {
				map.put("sumFee", sumFee.get("sumFee"));
			} else {
				map.put("sumFee", 0.00);
			}
			BigDecimal allMoney = rechangeBillService.findAllMoneyByUserId(userOpt.get().getId());
			if (allMoney != null) {
				map.put("allMoney", allMoney);
			} else {
				map.put("allMoney", 0.00);
			}
			return RetKit.okData(map);
		} catch (Exception e) {
			return RetKit.fail();
		}
	}

	/**
	 * 获取用户详情
	 */
	public RetKit getUserDetails(String userId) {
		try {
			Optional<User> UserOpt = userRepository.findById(Integer.parseInt(userId));
			if (UserOpt.isPresent()) {
				User user = UserOpt.get();
				HashMap<String, Object> map = new HashMap<>();
				map.put("userDetails", user); // 保存用户信息
				MemberRule memberRule = new MemberRule();
				if (user.getMemberId() != 0) {
					memberRule = memberRuleRepository.findById(user.getMemberId()).get();
				}
				map.put("memberRule", memberRule); // 保存会员信息

				Integer parkingStateFlag = 0; // 停车状态 0-目前用户没有停车， 1-目前用户在停车中

				String managerPhone = null; // 停车场管理中心电话

				List<ParkingInfo> parkingInfoList = parkingInfoRepository
						.findParkingInfoByUserId(Integer.parseInt(userId));
				if (parkingInfoList != null && !parkingInfoList.isEmpty()) {
					parkingStateFlag = 1;
					ParkingInfo parkingInfo = parkingInfoList.get(0);
					managerPhone = parkingRepository.findPhoneByParkingCode(parkingInfo.getParkCode());
				}
				map.put("parkingStateFlag", parkingStateFlag);
				map.put("managerPhone", managerPhone);
				// 预约状态 大于0就是有预约，等于0就没预约
				Integer appointmentFlag = appointmentService.getLastAppointmentCount(userId);
				map.put("appointmentFlag", appointmentFlag);

				return RetKit.okData(map);
			} else {
				return RetKit.fail("查无此用户");
			}
		} catch (Exception e) {
			return RetKit.fail("操作失败");
		}
	}

	@Override
	public RetKit changePhone(String userId, String newPhone) {
		User user = null;
		try {
			user = userRepository.findById(Integer.parseInt(userId)).get();
		} catch (NumberFormatException e) {
			return RetKit.fail("数字格式转换错误");
		} catch (NoSuchElementException ee) {
			return RetKit.fail("没有该用户");
		}
		user.setPhone(newPhone);
		userRepository.save(user);
		return RetKit.ok();
	}

	/**
	 * 获取用户是否认证的flag 0-未认证 1-认证通过 2-认证不通过
	 */
	public RetKit getAuthenticationFlag(String userId) {
		Integer userIdInp = Integer.parseInt(userId);
		try {
			User user = userRepository.findById(userIdInp).get();
			HashMap<String, Object> map = new HashMap<>();
			map.put("isAuthentication", user.getIsAuthentication());
			map.put("authenticationFailReason", user.getAuthenticationFailReason());
			return RetKit.okData(map);
		} catch (Exception e) {
			return RetKit.fail("操作失败");
		}
	}

	@Override
	public RetKit recharge(String out_trade_no) {
		try {
			rechargeLogRepository.changeRechargeLog(out_trade_no);
			RechargeLog rechargeLog = rechargeLogRepository.findByOutTradeNo(out_trade_no);
			BigDecimal money = rechargeLog.getInMoney().add(rechargeLog.getDiscountMoney());
			User user = userRepository.findUserById(rechargeLog.getUserId());
			BigDecimal addmoney = user.getBalance().add(money);
			userRepository.changeBalance(rechargeLog.getUserId(), addmoney);
			chargeToUpdateMemberRule(rechargeLog.getUserId());// 根据充值的金额更改等级
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail();
		}
	}

	/**
	 * 根据充值的金额更改等级
	 */
	public RetKit chargeToUpdateMemberRule(Integer userId) {
		double rechargeSum = memberRuleRepository.findUserRechargeSum(userId);
		int myMember = BaseConstant.UserGrade.InitMember.getTypeValue();
		if (rechargeSum > 0) {
			List<MemberRule> memberRuleList = memberRuleRepository.findAllMemberRuleList();
			if (memberRuleList != null && memberRuleList.size() > 0) {
				for (MemberRule item : memberRuleList) {
					if (rechargeSum - item.getMoney() >= 0) {
						myMember = item.getId();
					}
				}
				rechargeSendCoupon(userId);//判断是否符合赠送优惠券
				userRepository.updateUserMember(userId, myMember);
				return RetKit.ok();
			} else {
				return RetKit.fail();
			}
		} else {
			return RetKit.fail();
		}
	}

	/**
	 * 第一次成为会员的时候送一张特殊优惠券
	 */
	public void rechargeSendCoupon(Integer userId) {
		//////
		User user = userRepository.findUserById(userId);
		// 如果是开始不是会员，成为会员
		if (user != null && user.getMemberId().equals(BaseConstant.UserGrade.InitMember.getTypeValue())) {
			double rechargeSum = memberRuleRepository.findUserRechargeSum(userId);
			Integer myMember = BaseConstant.UserGrade.InitMember.getTypeValue();
			if (rechargeSum > 0) {
				List<MemberRule> memberRuleList = memberRuleRepository.findAllMemberRuleList();
				if (memberRuleList != null && memberRuleList.size() > 0) {
					for (MemberRule item : memberRuleList) {
						if (rechargeSum - item.getMoney() >= 0) {
							myMember = item.getId();
						}
					}
					if (!myMember.equals(BaseConstant.UserGrade.InitMember.getTypeValue())) {// 如果第一次成为会员就赠送优惠券
						Coupon becomeMemeberCoupon = couponRepository.findCouponById(BaseConstant.BecomeMemberCoupon.Coupon.getTypeValue());
						if (becomeMemeberCoupon != null) {
							UserCoupon userCoupon = new UserCoupon();
							userCoupon.setCouponId(BaseConstant.BecomeMemberCoupon.Coupon.getTypeValue());
							userCoupon.setCreateTime(new Date());
							userCoupon.setStatus(BaseConstant.UserCouponStatus.UnUse.getTypeValue());
							userCoupon.setUserId(userId);
							if (becomeMemeberCoupon.getEndTime() != null) {// 具体到某一天某个时间截止
								userCoupon.setEndDate(becomeMemeberCoupon.getEndTime());
							} else {//领到券后开始计时
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(new Date());
								switch (becomeMemeberCoupon.getEndType()) {
								case 1: //日
									calendar.add(Calendar.DATE, 1);
									break;
								case 2: //月
									calendar.add(Calendar.MONTH, 1);
									break;
								case 3://年
									calendar.add(Calendar.YEAR, 1);
									break;
								default:
									break;
								}
								userCoupon.setEndDate(calendar.getTime());
							}
							userCouponRepository.save(userCoupon);
							JGuangPush.jiguangPush(user.getRegistrationId(), "尊敬的会员您好，本次充值赠送您价值50元的停车优惠券，请注意查收，谢谢。", 0, BaseConstant.JpushType.GiveCoupon.getTypeValue());
						}
					}
				}
			}
		}
	}

	@Override
	public RetKit getUserCoupons(String userId, String status) {
        QUserCoupon qUserCoupon = QUserCoupon.userCoupon;
        QCoupon qCoupon = QCoupon.coupon;
        List<Tuple> tList = this.jpaQueryFactory.select(qUserCoupon.id,qCoupon.id,qCoupon.endTime)
        		.from(qUserCoupon).leftJoin(qCoupon).on(qCoupon.id.eq(qUserCoupon.couponId))
        		.where(qUserCoupon.userId.eq(Integer.parseInt(userId))).fetch();
        for(Tuple tuple : tList) {
        	Integer couponId = tuple.get(qCoupon.id);
        	if (tuple.get(qCoupon.endTime).before(new Date())) {
				this.jpaQueryFactory.update(qUserCoupon)
				.set(qUserCoupon.status, BaseConstant.UserCouponStatus.Overdue.getTypeValue())
				.where(qUserCoupon.couponId.eq(couponId)
						.and(qUserCoupon.status.eq(BaseConstant.UserCouponStatus.UnUse.getTypeValue()))).execute();
			}
        }
        SimpleDateFormat aDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Predicate> predicates = new ArrayList<>();
		//where条件查询
		predicates.add(qUserCoupon.userId.eq(Integer.parseInt(userId)));
		predicates.add(qUserCoupon.status.eq(Integer.parseInt(status)));
//		if (status.equals(BaseConstant.UserCouponStatus.AlreadyUse.getTypeValue().toString())) {//用户的优惠券已使用
//			predicates.add(qUserCoupon.endDate.before(new Date()).and(qUserCoupon.status.isNull()));
//		} else {
//			predicates.add(qUserCoupon.status.eq(Integer.parseInt(status)));
//		}
		List<Tuple> tuples = jpaQueryFactory.select(
		        qUserCoupon.id,
		        qUserCoupon.activityId,
				qUserCoupon.couponId,
				qCoupon.count,
				qUserCoupon.endDate,
				qUserCoupon.status,
				qUserCoupon.endNum)
				.from(qUserCoupon)
                .leftJoin(qCoupon).on(qUserCoupon.couponId.eq(qCoupon.id))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.fetchResults().getResults();
		List<Map<String, Object>> list = new ArrayList<>();
		for (Tuple tuple : tuples) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", tuple.get(qUserCoupon.id));
			map.put("actId", tuple.get(qUserCoupon.activityId));
			map.put("couponId", tuple.get(qUserCoupon.couponId));
			map.put("endDate", aDate.format(tuple.get(qUserCoupon.endDate)));
			map.put("feeCount", tuple.get(qCoupon.count));
			map.put("status", tuple.get(qUserCoupon.status));
			map.put("endNum", tuple.get(qUserCoupon.endNum));
			list.add(map);
		}
		return RetKit.okData(list);
	}

	@Override
	public Integer findIdByLicensePlate(String licensePlate) {
		return userRepository.findIdByLicensePlate(licensePlate);
	}

	/**
	 * 活动优惠券列表
	 * hrd
	 * @return
	 */
	@Override
	public Object getActivityCoupons(long userId,Integer pageSize, Integer pageNum, List<Map<String,Object>> para){
		long totalElements = activityRepository.getActivityCouponsCount(userId);
		long totalPages = totalElements/pageSize;
		if(totalPages % pageSize != 0){
			totalPages++;
		}
		List<Map<String, Object>> data = activityRepository.getActivityCoupons(userId,pageSize*pageNum,pageSize*(pageNum+1));
		Map<String, Object> result = new HashMap<>();
		Map<String,Object> content = new HashMap<>();
		content.put("content",data);
		content.put("totalElements",totalElements);//添加总条数
		content.put("totalPages", totalPages );//总页面数
		result.put("msg", "操作成功");//添加主体数据
		result.put("code", 200);
		result.put("success",true);
		result.put("data", content);//添加主体数据
		return result;
	}
	
	/**
	 * 绑定活动优惠券
	 * hrd
	 * @param userId
	 * @return
	 * @throws ParseException 
	 */
	@Override
	public RetKit bindActivityCoupon(Integer userId,Integer activityId) throws ParseException{
		Activity optActivity = activityRepository.findById(activityId).get();
		if(optActivity!=null){
			UserCoupon userCoupon = userCouponRepository.findActCoupon(userId,activityId);
			if(userCoupon !=null ){
				return RetKit.fail("不能重复领取活动优惠券");
			}
			Optional<Coupon> optCoupon = couponRepository.findById(optActivity.getCouponId());
			if(!optCoupon.isPresent()){
				return RetKit.fail("活动优惠券不存在");
			}
			if (optActivity.getCouponCount() != 0 ) {//判断优惠券数量是否为0
			Coupon coupon = optCoupon.get();

			userCoupon = new UserCoupon();
			if (coupon.getDeductionType() == BaseConstant.DeductionType.ManyTimesDeduction.getTypeValue()) {
				userCoupon.setEndNum(coupon.getCount()/coupon.getPartDeduction());
			}
			Integer endNum= coupon.getEndNum();
			userCoupon.setUserId(userId);
			userCoupon.setCouponId(optActivity.getCouponId());
			userCoupon.setActivityId(activityId);
			userCoupon.setCreateTime(new Date());
			Date date = new Date();
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(date);                             //领取优惠券时间
			if(coupon.getEndTime() !=null ) {
				userCoupon.setEndDate(coupon.getEndTime());//结束时间不能为空
			}else {                                        //领取优惠券开始计时
				if(coupon.getEndType()==BaseConstant.UserCouponEndType.Day.getTypeValue()) { //加上天数
				    cal.add(Calendar.DATE, endNum);         //加上天数
				    Date newdate=cal.getTime();             //优惠券到期时间
				    userCoupon.setEndDate(newdate);
				}else if(coupon.getEndType()==BaseConstant.UserCouponEndType.Month.getTypeValue()) {
				    cal.add(Calendar.MONTH, endNum);         //加上月数
				    Date newdate=cal.getTime();
				    userCoupon.setEndDate(newdate);
				}else if(coupon.getEndType()==BaseConstant.UserCouponEndType.Year.getTypeValue()){
				    cal.add(Calendar.YEAR, endNum);           //加上年数
				    Date newdate=cal.getTime();
				    userCoupon.setEndDate(newdate); 
				}
				     
			}
			
			
			userCoupon.setStatus(BaseConstant.UserCouponStatus.UnUse.getTypeValue());
			userCouponRepository.save(userCoupon);
			
			
			optActivity.setCouponCount(optActivity.getCouponCount()-1);
			activityRepository.save(optActivity);
			}else {
				return RetKit.fail("该优惠券领取完毕");
			}
			
			return RetKit.ok("领取成功");
		}
		return RetKit.fail("活动不存在");
	}

}
