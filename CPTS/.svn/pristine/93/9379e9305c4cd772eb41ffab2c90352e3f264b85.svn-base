package com.dchip.cloudparking.api.constant;

public interface BaseConstant {

	/**
	 * 用户状态
	 *
	 */
	enum UserState implements BaseConstant {
		EnabledState("0", "正常"), DisableState("1", "禁用");

		private String typeValue;
		private String typeDescription;

		private UserState(String typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public String getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}
	}
	
	/**
	 * 白名单状态
	 */
	enum WhiteListState implements BaseConstant {
		EnabledState(1, "正常"), DisableState(0, "禁用");

		private Integer typeValue;
		private String typeDescription;

		private WhiteListState(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}
	}
	
	/**
	 * 白名单标签
	 */
	enum WhiteListFlag implements BaseConstant {
		EnabledState(1, "临时"), DisableState(0, "永久");

		private Integer typeValue;
		private String typeDescription;

		private WhiteListFlag(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}
	}

	enum MessageType implements BaseConstant {
		All(1, "所有人"), Specify(2, "指定用户");

		private Integer typeValue;
		private String typeDescription;

		private MessageType(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}
	}

	/**
	 * 用户类型
	 *
	 */
	enum UserType implements BaseConstant {
		FrontUser("0", "普通用户"), AdminUser("1", "管理平台用户");

		private String typeValue;
		private String typeDescription;

		private UserType(String typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public String getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}
	}

	/**
	 * 活动类型
	 *
	 */
	enum ActivityType implements BaseConstant {
		Present("1", "送多少"), Reduced("2", "减多少"), Coupon("3", "送优惠券");

		private String typeValue;
		private String typeDescription;

		private ActivityType(String typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public String getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}
	}

	/**
	 * 预约状态
	 *
	 */
	enum AppointmentStatus implements BaseConstant {
		Normal(0, "正常预约时间内"), NormalParking(1, "已入场"), NotNormalParking(2, "过期"), Cancel(3, "取消");

		private Integer typeValue;
		private String typeDescription;

		private AppointmentStatus(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}
	}

	/**
	 * 订单的支付类型
	 */
	enum OrderType implements BaseConstant {
		AliPay(1, "支付宝"), WeChat(2, "微信"), Balance(3, "余额");

		private Integer typeValue;
		private String typeDescription;

		private OrderType(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public void setTypeValue(Integer typeValue) {
			this.typeValue = typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}

		public void setTypeDescription(String typeDescription) {
			this.typeDescription = typeDescription;
		}

	}

	/**
	 * 用户会员
	 *
	 */
	enum UserGrade implements BaseConstant {
		InitMember(0, "已注册未充值的用户"), OrdinaryMember(1, "普通会员"), BronzeMember(2, "铜牌会员"), SilverMember(3,
				"银牌会员"), GoldMember(4, "金牌会员"), PlatinumMember(5, "白金会员");

		private Integer typeValue;
		private String typeDescription;

		private UserGrade(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}
	}

	/**
	 * 用户是否黑名单
	 *
	 */
	enum UserIsBlack implements BaseConstant {
		NormalUser(0, "正常用户"), BlacklistUser(1, "黑名单用户");

		private Integer typeValue;
		private String typeDescription;

		private UserIsBlack(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}
	}

	/**
	 * 错误码
	 */
	enum ErrorCode implements BaseConstant {
		CODE_501(501, "操作失败"), CODE_502(502, "token过期");
		private int errorCode;
		private String errorMsg;

		private ErrorCode(int errorCode, String errorMsg) {
			this.errorCode = errorCode;
			this.errorMsg = errorMsg;
		}

		public int getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(int errorCode) {
			this.errorCode = errorCode;
		}

		public String getErrorMsg() {
			return errorMsg;
		}

		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}
	}

	/**
	 * 停车状态
	 */
	enum parkingInfoStatus implements BaseConstant {
		finishedStatus('0', "结束停车"), unfinishedStatus('1', "未结束停车"), errorStauts('2', "异常停车-用户手动上报异常");
		private char value;
		private String description;

		private parkingInfoStatus(char value, String description) {
			this.value = value;
			this.description = description;
		}

		public char getValue() {
			return value;
		}

		public String getDescription() {
			return description;
		}
	}

	/**
	 * 推送类型
	 *
	 */
	enum JpushType implements BaseConstant {
		CarOut(1, "已离场"), CarIn(2, "已进场"), Booking(3, "预约"), CloneCar(4,"套牌车"),GiveCoupon(5,"赠送优惠券");
		private Integer typeValue;
		private String typeDescription;

		private JpushType(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public void setTypeValue(Integer typeValue) {
			this.typeValue = typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}

		public void setTypeDescription(String typeDescription) {
			this.typeDescription = typeDescription;
		}
	}

	/**
	 * 订单状态
	 */
	enum OrderStatus implements BaseConstant {
		UnPay(0, "未支付"), AlreadyPay(1, "已支付"), PayFail(2, "支付失败"),AdvanceUnPay(3,"提前为未支付"),AdvanceAlreadyPay(4,"提前已支付");

		private Integer typeValue;
		private String typeDescription;

		private OrderStatus(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}
	}

	enum LockType implements BaseConstant {
		Lock(1, "锁车"), UnLock(0, "不锁车");
		private Integer typeValue;
		private String typeDescription;

		private LockType(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}
	}

	enum PlateType implements BaseConstant {
		Unknow(0, "未知"), Blue(1, "蓝牌"), Black(2, "黑牌"), Yellow(3, "黄牌"), DoubleYellow(4, "双层黄牌"), Police(5,
				"警车牌"), Apolice(6, "武警车牌"), DoubleApolice(7, "双层武警车牌"), SingleArmy(8, "单层军牌"), DoubleArmy(9,
						"双层军牌"), Special(10, "个性车牌"), NewSmallSource(11, "新能源小车牌"), NewBigSource(12,
								"新能源大车牌"), Embassy(13, "使馆车牌"), Consulate(14, "领馆车牌"), Civil(15, "民航车牌");
		private Integer typeValue;
		private String typeDescription;

		private PlateType(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public void setTypeValue(Integer typeValue) {
			this.typeValue = typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}

		public void setTypeDescription(String typeDescription) {
			this.typeDescription = typeDescription;
		}

	}

	enum CarType implements BaseConstant {
		Unknow(0, "未知"), Big(1, "大型车"), Middle(2, "中型车"), Small(3, "小型车"), Moto(4, "摩托车");
		private Integer typeValue;
		private String typeDescription;

		private CarType(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public void setTypeValue(Integer typeValue) {
			this.typeValue = typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}

		public void setTypeDescription(String typeDescription) {
			this.typeDescription = typeDescription;
		}
	}

	enum MemberShip implements BaseConstant {
		UnMember(0, "非会员"), Member(1, "会员"), MonthCard(2, "月卡"), MemberAndCard(3, "既是月卡用户也是会员用户"), SpecialMember(4,
				"特殊用户");
		private Integer typeValue;
		private String typeDescription;

		private MemberShip(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public void setTypeValue(Integer typeValue) {
			this.typeValue = typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}

		public void setTypeDescription(String typeDescription) {
			this.typeDescription = typeDescription;
		}
	}

	enum RoadMarker implements BaseConstant {
		IN(1, "入口"), OUT(2, "出口");
		private Integer typeValue;
		private String typeDescription;

		private RoadMarker(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public void setTypeValue(Integer typeValue) {
			this.typeValue = typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}

		public void setTypeDescription(String typeDescription) {
			this.typeDescription = typeDescription;
		}
	}

	/**
	 * 主控板状态
	 * 
	 * @author leif
	 *
	 */
	enum MainControlStatus implements BaseConstant {
		EnabledState(0, "禁用"), AbleState(1, "启用"), DeleteState(-2, "删除");

		private MainControlStatus(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		private Integer typeValue;
		private String typeDescription;

		public Integer getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}

	}

	enum CouponStatus implements BaseConstant {

		Used(1, "已使用"), UnUsed(0, "未使用");

		private CouponStatus(Integer statusValue, String typeDescription) {
			this.statusValue = statusValue;
			this.typeDescription = typeDescription;
		}

		private Integer statusValue;
		private String typeDescription;

		public Integer getStatusValue() {
			return statusValue;
		}

		public void setStatusValue(Integer statusValue) {
			this.statusValue = statusValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}

		public void setTypeDescription(String typeDescription) {
			this.typeDescription = typeDescription;
		}

	}

	enum TransferStatus implements BaseConstant {
		Extracted(1, "已转账"), UnExtracted(0, "未转账");
		private Integer statusValue;
		private String typeDescription;

		private TransferStatus(Integer statusValue, String typeDescription) {
			this.statusValue = statusValue;
			this.typeDescription = typeDescription;
		}

		public Integer getStatusValue() {
			return statusValue;
		}

		public void setStatusValue(Integer statusValue) {
			this.statusValue = statusValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}

		public void setTypeDescription(String typeDescription) {
			this.typeDescription = typeDescription;
		}

	}

	enum PayStatus implements BaseConstant {
		UnPay(0, "未支付"), Pay(1, "已支付"), PayFailed(2, "支付失败");
		private Integer statusValue;
		private String typeDescription;

		private PayStatus(Integer statusValue, String typeDescription) {
			this.statusValue = statusValue;
			this.typeDescription = typeDescription;
		}

		public Integer getStatusValue() {
			return statusValue;
		}

		public void setStatusValue(Integer statusValue) {
			this.statusValue = statusValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}

		public void setTypeDescription(String typeDescription) {
			this.typeDescription = typeDescription;
		}

	}

	/**
	 * 用户认证状态
	 * 
	 * @author leif
	 *
	 */
	enum UserAuthenticationStatus implements BaseConstant {
		initState(0, "初始未认证"), successState(1, "已成功认证"), failsState(2, "认证失败");

		private UserAuthenticationStatus(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		private Integer typeValue;
		private String typeDescription;

		public Integer getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}

	}

	/**
	 * 演示帐号
	 * 
	 * @author leif
	 *
	 */
	enum DemoAccounts implements BaseConstant {
		demoLicensePlat("粤C73S57", "车牌号码"), demoPhone("13192266495", "手机号码"), demoUserName("演示帐号",
				"用户昵称"), demoToken("demotokendchip2018adminuser", "演示帐号token");

		private DemoAccounts(String typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		private String typeValue;
		private String typeDescription;

		public String getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}

	}

	enum WhiteName implements BaseConstant {
		EnabledState(1, "正常"), DisableState(0, "禁用");

		private Integer typeValue;
		private String typeDescription;

		private WhiteName(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}
	}

	enum CameraStatus implements BaseConstant {
		OnLine(1, "在线"), OffLine(0, "离线");

		private Integer typeValue;
		private String typeDescription;

		private CameraStatus(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}
	}

	enum HelpType implements BaseConstant {
		Point(1, "积分"), help(2, "使用帮助");

		private Integer typeValue;
		private String typeDescription;

		private HelpType(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}
	}
	
	enum CloneCarStatus implements BaseConstant {
		NotDeal(0,"未处理"),Deal(1,"已处理");
		
		private Integer typeValue;
		private String typeDescription;
		
		private CloneCarStatus(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}
		public String getTypeDescription() {
			return typeDescription;
		}
	}
	
	/**
	 * 成为会员的时候赠送固定50元的面额优惠券，分次使用
	 */
	enum BecomeMemberCoupon implements BaseConstant {
		Coupon(1,"优惠券表第一张优惠券");
		
		private Integer typeValue;
		private String typeDescription;
		
		private BecomeMemberCoupon(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}
		public String getTypeDescription() {
			return typeDescription;
		}
	}
	
	/**
	 * 用户优惠券状态
	 */
	enum UserCouponStatus implements BaseConstant{
		UnUse(0,"未使用"),AlreadyUse(1,"已使用"),Overdue(2,"过期");
		
		private Integer typeValue;
		private String typeDescription;
		
		private UserCouponStatus(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}
		public String getTypeDescription() {
			return typeDescription;
		}
	}
	
	/**
	 * 优惠券截止类型
	 */
	enum UserCouponEndType implements BaseConstant {
		Day(1,"日"),Month(2,"月"),Year(3,"年");
		
		private Integer typeValue;
		private String typeDescription;
		
		private UserCouponEndType(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}
		public String getTypeDescription() {
			return typeDescription;
		}
	}

	enum ParkingConcession implements BaseConstant {
		TenantNoId(0, "无租客承租时id"),CheckPendingStatus(-3,"待审核"),NotPassStatus(-4,"不通过"),NotUsedStatus(0, "未领用"),UsedStatus(1, "已领用"), OverTimeStatus(2, "已过期"),DisabledStatus(-1,"禁用"),DeletedStatus(-2,"软删");
		private Integer typeValue;
		private String typeDescription;

		private ParkingConcession(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public void setTypeValue(Integer typeValue) {
			this.typeValue = typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}

		public void setTypeDescription(String typeDescription) {
			this.typeDescription = typeDescription;
		}
	}

	enum DeductionStatus implements BaseConstant {
		NotUsedStatus(0, "未使用"), UsedStatus(1, "已使用"),OutOfDateStatus(2,"已过期");

		private Integer typeValue;
		private String typeDescription;

		private DeductionStatus(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}
	}
	
	/**
	 * 登录来源
	 * @author myuser
	 *
	 */
	enum LoginSourceFlag implements BaseConstant {
		AppFlag(1, "手机端"), WebFlag(2, "web端");

		private Integer typeValue;
		private String typeDescription;

		private LoginSourceFlag(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}
	}

	/**
	 * 软删除
	 *
	 */
	enum SoftDelete implements BaseConstant {
		delete(-2, "软删除");

		private Integer typeValue;
		private String typeDescription;

		private SoftDelete(Integer typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}

		public Integer getTypeValue() {
			return typeValue;
		}

		public String getTypeDescription() {
			return typeDescription;
		}
	}


    /**
     * 登陆用户类型
     */
    enum SessionUserType implements BaseConstant{
        NormalUser(1, "用户"), ParkingUser(2, "停车场用户");

        private Integer typeValue;
        private String typeDescription;

        private SessionUserType(Integer typeValue, String typeDescription) {
            this.typeValue = typeValue;
            this.typeDescription = typeDescription;
        }

        public Integer getTypeValue() {
            return typeValue;
        }

        public String getTypeDescription() {
            return typeDescription;
        }   
    }
    
    enum GroundLockType implements BaseConstant{
        Open(102, "开"), Close(101, "关");

        private Integer typeValue;
        private String typeDescription;

        private GroundLockType(Integer typeValue, String typeDescription) {
            this.typeValue = typeValue;
            this.typeDescription = typeDescription;
        }

        public Integer getTypeValue() {
            return typeValue;
        }

        public String getTypeDescription() {
            return typeDescription;
        }   
    }
    
    enum groundLockStatus implements BaseConstant{
    	Close(1, "关闭"), Open(2, "打开");
    	
    	private Integer typeValue;
        private String typeDescription;

        private groundLockStatus(Integer typeValue, String typeDescription) {
            this.typeValue = typeValue;
            this.typeDescription = typeDescription;
        }

        public Integer getTypeValue() {
            return typeValue;
        }

        public String getTypeDescription() {
            return typeDescription;
        }
    }
    
    enum parkingUserStatus implements BaseConstant{
    	normal(1, "正常"), delete(0, "删除");
    	private Integer typeValue;
        private String typeDescription;

        private parkingUserStatus(Integer typeValue, String typeDescription) {
            this.typeValue = typeValue;
            this.typeDescription = typeDescription;
        }

        public Integer getTypeValue() {
            return typeValue;
        }

        public String getTypeDescription() {
            return typeDescription;
        }
    }
    
    enum parkingLotState implements BaseConstant{
    	NoCar(0, "无车"), HasCar(1, "有车");
    	private Integer typeValue;
        private String typeDescription;

        private parkingLotState(Integer typeValue, String typeDescription) {
            this.typeValue = typeValue;
            this.typeDescription = typeDescription;
        }

        public Integer getTypeValue() {
            return typeValue;
        }

        public String getTypeDescription() {
            return typeDescription;
        }
    }
    
    enum parkingLotStatus implements BaseConstant{
    	Hide(0, "隐藏"), Open(1, "开放");
    	private Integer typeValue;
        private String typeDescription;

        private parkingLotStatus(Integer typeValue, String typeDescription) {
            this.typeValue = typeValue;
            this.typeDescription = typeDescription;
        }

        public Integer getTypeValue() {
            return typeValue;
        }

        public String getTypeDescription() {
            return typeDescription;
        }
    }

	enum IsFixedSpaceStatus implements BaseConstant{
		NotFixedSpace(0, "非固定车位"), FixedSpace(1, "固定车位");
		private Integer typeValue;
        private String typeDescription;

        private IsFixedSpaceStatus(Integer typeValue, String typeDescription) {
            this.typeValue = typeValue;
            this.typeDescription = typeDescription;
        }

        public Integer getTypeValue() {
            return typeValue;
        }

        public String getTypeDescription() {
            return typeDescription;
        }
	}
	
	enum DeductionType implements BaseConstant{
		ManyTimesDeduction(2, "多次抵扣"), AllDeduction(1, "全抵扣");
		private Integer typeValue;
        private String typeDescription;

        private DeductionType(Integer typeValue, String typeDescription) {
            this.typeValue = typeValue;
            this.typeDescription = typeDescription;
        }

        public Integer getTypeValue() {
            return typeValue;
        }

        public String getTypeDescription() {
            return typeDescription;
        }
	}

	
}
