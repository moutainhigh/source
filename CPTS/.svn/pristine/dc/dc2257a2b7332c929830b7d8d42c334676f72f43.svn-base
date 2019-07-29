package com.dchip.cloudparking.web.constant;


public interface SocketConstant {
	
	/**
	 * socket发送command指令解释
	 */
	enum CommandType implements SocketConstant{
		OpenGate("1", "开闸指令"), BindingMainControl("100", "绑定主板"),UpdateMainControl("101","更新主板");
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
