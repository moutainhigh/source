package com.dchip.cloudparking.wechat.constant;

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
	
	enum MessageType implements BaseConstant{
		All("1", "所有人"),Specify("2", "指定用户");

		private String typeValue;
		private String typeDescription;
		
		private MessageType(String typeValue, String typeDescription) {
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
		Present("1", "送多少"), Reduced("2", "减多少"),Coupon("3","送优惠券");

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
	enum AppointmentType implements BaseConstant{
		Normal(0, "正常预约时间内"),NormalParking(1, "预约到期前入库"),NotNormalParking(2, "预约到期前没有入库"),Cancel(3,"取消");

		private Integer typeValue;
		private String typeDescription;
		
		private AppointmentType(Integer typeValue, String typeDescription) {
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
		AliPay(1, "支付宝"),WeChat(2, "微信"),Balance(3, "余额");

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
		initMember(0, "已注册未充值的用户"), OrdinaryMember(1, "普通会员"), BronzeMember(2, "铜牌会员"), SilverMember(3, "银牌会员"), GoldMember(4,
				"金牌会员"), PlatinumMember(5, "白金会员");

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
	enum parkingInfoStatus implements BaseConstant{
		finishedStatus('0', "结束停车"), unfinishedStatus('1', "未结束停车"), errorStauts('2',"异常停车-用户手动上报异常");
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
		CarOut(1, "车出库");
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
		UnPay(0, "未支付"), AlreadyPay(1, "已支付"),PayFail(2,"支付失败"),AdvanceUnPay(3,"提前为未支付"),AdvanceAlreadyPay(4,"提前已支付");

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

	/**
	 * 微信跳转页面标志
	 */
	enum wechatShowFlag implements BaseConstant {
		UnlicensedEnter("1", "无牌入场"), UnlicensedOut("2", "无牌出场"), HaveACardOut("3", "有牌出场")
		, ScanCodeCheckout("4", "扫码离场");

		private String typeValue;
		private String typeDescription;
		
		
		private wechatShowFlag(String typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}
		public String getTypeValue() {
			return typeValue;
		}
		public void setTypeValue(String typeValue) {
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
	 * 用户认证状态
	 * @author leif
	 *
	 */
	enum UserAuthenticationStatus implements BaseConstant{
		initState(0, "初始未认证"),successState(1, "已成功认证"),failsState(2, "认证失败");
		
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
	 * 月卡类型
	 * @author 
	 *
	 */
	enum CardType implements BaseConstant{
		
		NonCard(0,"非月卡"),Month(1,"自然月"), WorkDay(2,"工作日"), WorkTime(3,"工作时");
		private Integer typeValue;
		private String typeDescription;
		
		private CardType(Integer typeValue, String typeDescription) {
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
	 * 抵扣券状态
	 * hrd
	 */
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
}
