package com.dchip.cloudparking.web.constant;

public interface BaseConstant {

	/**
	 * 主控板是否在线
	 */
	enum DeviceOnlineSituation implements BaseConstant{
		DeviceOnline("device_online","在线"),DeviceOffline("device_offline","离线");
		private String value;
		private String explian;
		
		
		private DeviceOnlineSituation(String value, String explian) {
			this.value = value;
			this.explian = explian;
		}
		public String getValue() {
			return value;
		}
		public String getExplian() {
			return explian;
		}		
		
	}

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
		AliPay(1, "支付宝"),WeChat(2, "微信"),Balance(3, "余额"),cash(4,"现金");

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
		InitMember(0, "已注册未充值的用户"),OrdinaryMember(1, "普通会员"), BronzeMember(2, "铜牌会员"), SilverMember(3, "银牌会员"), GoldMember(4,
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
		UnPay(0, "未支付"), AlreadyPay(1, "已支付"),PayFail(2,"支付失败"),AdvanceUnPay(3,"提前为未支付"),AdvanceAlreadyPay(4,"提前已支付");;

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
	 * 充值状态
	 */
	enum RechargeLogStatus implements BaseConstant {
		trueFlag(true, "已支付"), falseFlag(false, "未支付");

		private boolean value;
		private String valueDescription;

		private RechargeLogStatus(boolean value, String valueDescription) {
			this.value = value;
			this.valueDescription = valueDescription;
		}

		public boolean getValue() {
			return value;
		}

		public String getValueDescription() {
			return valueDescription;
		}
	}

	
	/**
	 * 财务状态
	 */
	enum FinanceStatus implements BaseConstant {
		// 1-成功 0-失败 2-待处理
		falseFlag(0, "失败"), trueFlag(1, "成功"), pendingFlag(2, "待处理");

		private Integer typeValue;
		private String typeDescription;

		private FinanceStatus(Integer typeValue, String typeDescription) {
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
		nomal(0,"正常"), delete(-2, "软删除");

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
	 * 默认密码123456
	 */
	enum AccountPwd implements BaseConstant {
		RESET(1, "123456");
		private Integer id;
		private String pwd;

		private AccountPwd(Integer id, String pwd) {
			this.id = id;
			this.pwd = pwd;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getPwd() {
			return pwd;
		}

		public void setPwd(String pwd) {
			this.pwd = pwd;
		}
	}

	/**
	 * 用户状态
	 *
	 */
	enum AccountStatus implements BaseConstant {
		DisableStatus(0, "禁用"), EnabledStatus(1, "正常");

		private Integer typeValue;
		private String typeDescription;

		private AccountStatus(Integer typeValue, String typeDescription) {
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
	 * APP停车场人员状态
	 *
	 */
	enum APPParkingUserStatus implements BaseConstant {
		DisableStatus(0, "删除"), EnabledStatus(1, "正常");
		
		private Integer typeValue;
		private String typeDescription;
		
		private APPParkingUserStatus(Integer typeValue, String typeDescription) {
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
	 * APP停车场用户类型
	 *
	 */
	enum APPParkingUserType implements BaseConstant {
		Business(1, "商户"), Security(2, "保安"), ParkingUser(3,"停车场管理员"), companyUser(4,"公司管理员");
		
		private Integer typeValue;
		private String typeDescription;
		
		private APPParkingUserType(Integer typeValue, String typeDescription) {
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
	 * 停车场用户
	 */
	enum ParkingUserStatus implements BaseConstant {
		DisableStatus(0, "禁用"), EnabledStatus(1, "正常");

		private Integer typeValue;
		private String typeDescription;

		private ParkingUserStatus(Integer typeValue, String typeDescription) {
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
	 * 菜单类型  
	 * @author leif
	 *
	 */
	enum MenuType implements BaseConstant {
		sysMenu(1, "系统管理员菜单"),companyMenu(2, "公司管理员菜单"),publicMenu(3, "公共菜单");

		private Integer typeValue;
		private String typeDescription;
		
		
		private MenuType(Integer typeValue, String typeDescription) {
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
	 * 角色类型  
	 * @author zyy
	 *
	 */
	enum RoleType implements BaseConstant {
		sysRole(1, "系统管理员"),companyRole(2, "公司管理员"),parkingRole(3, "停车场管理员");

		private Integer typeValue;
		private String typeDescription;
		
		
		private RoleType(Integer typeValue, String typeDescription) {
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
	 * 角色状态  
	 * @author leif
	 *
	 */
	enum SysRoleStatus implements BaseConstant {
		DisableStatus(0, "删除"), EnabledStatus(1, "正常");

		private Integer typeValue;
		private String typeDescription;
		
		
		private SysRoleStatus(Integer typeValue, String typeDescription) {
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
	 * Order是否生成了Finance表数据
	 */
	enum OrderIsTransferStauts implements BaseConstant {
		UnTransfer(0, "未生成"), AlreadyTransfer(1, "已生成");

		private Integer typeValue;
		private String typeDescription;
		
		
		private OrderIsTransferStauts(Integer typeValue, String typeDescription) {
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
	 * Order是否生成了Finance表数据
	 */
	enum MonthlyCardIsTransferStauts implements BaseConstant {
		UnTransfer(0, "未生成"), AlreadyTransfer(1, "已生成");

		private Integer typeValue;
		private String typeDescription;
		
		
		private MonthlyCardIsTransferStauts(Integer typeValue, String typeDescription) {
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
	 * 角色状态  
	 * @author leif
	 *
	 */
	enum SysRoleType implements BaseConstant {
		adminType(1, "系统管理员"), companyType(2, "公司管理员"),parkingType(3,"停车场管理员"),securityType(4,"保安");

		private Integer typeValue;
		private String typeDescription;
		
		
		private SysRoleType(Integer typeValue, String typeDescription) {
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
	 * 商户-保安  
	 * @author leif
	 *
	 */
	enum ParkingUserAccountType implements BaseConstant {
		businessmanType(1, "商户"), securityType(2, "保安");

		private Integer typeValue;
		private String typeDescription;
		
		
		private ParkingUserAccountType(Integer typeValue, String typeDescription) {
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
	 * Order是否生成了Finance表数据
	 */
	enum FinanceType implements BaseConstant {
		orderType(1, "停车消费"), monthlyCardType(2, "月卡缴费");

		private Integer typeValue;
		private String typeDescription;
		
		
		private FinanceType(Integer typeValue, String typeDescription) {
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

	enum CouponStatus implements BaseConstant {
		DisableStatus(1, "禁用"), EnabledStatus(0, "正常");

		private Integer typeValue;
		private String typeDescription;

		private CouponStatus(Integer typeValue, String typeDescription) {
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

	/**
	 * 主控板版本类型
	 */
	enum MainControlType implements BaseConstant{
		Type100(100, "100版本号");
		private Integer typeValue;
		private String typeDescription;

		private MainControlType(Integer typeValue, String typeDescription) {
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

	enum SocketType implements BaseConstant{
		updateVersion(98,"版本更新");
		private Integer typeValue;
		private String typeDescription;
		private SocketType(Integer typeValue, String typeDescription) {
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
	 * 停车场保安-商户用户状态
	 *
	 */
	enum ParkingUserStatusS implements BaseConstant {
		DisableStatus(0, "删除"), EnabledStatus(1, "正常");

		private Integer typeValue;
		private String typeDescription;

		private ParkingUserStatusS(Integer typeValue, String typeDescription) {
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
	
	enum parkingRule implements BaseConstant{
		InRule(1,"入场规则"), OutRule(2,"出场规则");
		private Integer typeValue;
		private String typeDescription;

		private parkingRule(Integer typeValue, String typeDescription) {
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
	
	//操作日志的操作类型
	enum DailyRecordType implements BaseConstant{
		Add(1, "添加"),Delete(2, "删除"),Edit(3, "编辑"),UnEnabled(4, "禁用"),Enabled(5, "启用"),SettingRule(6, "规则设置"),
		updatePassWord(7,"修改密码"),Import(8,"月卡信息导入"),pass(9,"审核通过"),notPass(10,"审核不通过"),Notify(11,"提示更新"),
		Apply(12,"申请提现"),TransferAccounts(13,"确认转账"),AgreeToApply(14,"同意申请");

		private Integer typeValue;
		private String typeDescription;
		
		private DailyRecordType(Integer typeValue, String typeDescription) {
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
	
	enum MainControlActTo implements BaseConstant{
		Gate(1,"道闸"),GroundLock(2,"地锁"),Geomagnetism(3,"地磁"),Ultrasonic(4,"超声波");
		private Integer typeValue;
		private String typeDescription;

		private MainControlActTo(Integer typeValue, String typeDescription) {
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
	
	enum GroundLockState implements BaseConstant{
		Open(1,"打开"),Close(2,"关闭");
		private Integer typeValue;
		private String typeDescription;

		private GroundLockState(Integer typeValue, String typeDescription) {
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
