package com.dchip.cloudparking.socket.constant;


public interface Constant {
	/**
	 * 主控板是否在线
	 */
	enum DeviceOnlineSituation implements Constant{
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
	 * 常量和对应解释
	 */
	enum ValueAndExplian implements Constant{
		LatestHeart("latest_heart","最后一次心跳");
		private String value;
		private String explian;
		
		private ValueAndExplian(String value, String explian) {
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
	 * socket发送command指令解释
	 */
	enum CommandType implements Constant{
		OpenGate("1", "开闸指令"), BindingMainControl("100", "绑定主板"),UpdateMainControl("100","更新主板");
		private String value;
		private String explian;
		
		private CommandType(String value, String explian) {
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
}
