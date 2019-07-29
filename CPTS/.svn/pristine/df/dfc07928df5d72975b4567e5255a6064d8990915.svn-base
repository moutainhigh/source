package com.dchip.cloudparking.api.constant;

public interface ParkingConstant extends BaseConstant {

	enum ParkingStatus implements ParkingConstant {
		EnabledStatus(0,"正常"), DisableStatus(-1,"禁用");
		private Integer typeValue;
		private String typeDescription;
		private ParkingStatus(Integer typeValue, String typeDescription) {
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

	enum ParkingInRule implements ParkingConstant {
		
		InRuleAsAmount(1,"入场车辆超限情禁止入场"), InRuleAsTemp(2,"入场车辆超限情临时车入场");

		private Integer typeValue;
		private String typeDescription;

		private ParkingInRule(Integer typeValue, String typeDescription) {
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
	
	enum IsAuthorization implements ParkingConstant{
		
		Open(1,"开闸"), Close(0,"关闸");
		private Integer typeValue;
		private String typeDescription;
		
		private IsAuthorization(Integer typeValue, String typeDescription) {
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
	
	enum WeiXinScanType implements ParkingConstant{
		NoPlateIn("1","无牌入场"), NoPlateOut("2","无牌出场"),PlateOut("3","有牌车出场");
		private String typeValue;
		private String typeDescription;
		
		private WeiXinScanType(String typeValue, String typeDescription) {
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
	
	enum CardType implements ParkingConstant{
		
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

	enum ParkingRuleType implements ParkingConstant{
		NonCard(0,"非月卡"),Month(1,"全天"), WorkDay(2,"工作日"), WorkTime(3,"工作时");
		private Integer typeValue;
		private String typeDescription;

		private ParkingRuleType(Integer typeValue, String typeDescription) {
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

	enum IsSupportCard implements ParkingConstant{
		
		SupportNot(0,"否、可支持临时车"),SupportYes(1,"是、不支持临时车");
		private Integer typeValue;
		private String typeDescription;
		
		private IsSupportCard(Integer typeValue, String typeDescription) {
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
