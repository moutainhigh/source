package com.dchip.cloudparking.web.model.vo;

public class RoadwayVo implements java.io.Serializable {

	private Integer id; //车道id
	private Integer parkingId;//停车场id
	private Integer inOutMarker;//出入口标记
	private String roadName;//车道名称
	private String roadSign;//停车场规模
	private Integer gateNumber;//闸机机号
	private Integer voiceLed;//语音与LED
	private String ledModel;//LED卡型号
	private Integer ledScreenRowNum;//LED屏行数
	private Integer showWay;//显示方式
	private Integer communication;//LED屏通讯
	private String ledIp;//LED屏IP
	private String address;//显示屏地址
	private Integer cameraType;//摄像机类型
	private String cameraIp;//摄像机IP
	private String cameraMac;//摄像机mac
	private String status;//是否是新增的车道
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParkingId() {
		return parkingId;
	}
	public void setParkingId(Integer parkingId) {
		this.parkingId = parkingId;
	}
	public Integer getInOutMarker() {
		return inOutMarker;
	}
	public void setInOutMarker(Integer inOutMarker) {
		this.inOutMarker = inOutMarker;
	}
	public String getRoadName() {
		return roadName;
	}
	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}
	public String getRoadSign() {
		return roadSign;
	}
	public void setRoadSign(String roadSign) {
		this.roadSign = roadSign;
	}
	public Integer getGateNumber() {
		return gateNumber;
	}
	public void setGateNumber(Integer gateNumber) {
		this.gateNumber = gateNumber;
	}
	public Integer getVoiceLed() {
		return voiceLed;
	}
	public void setVoiceLed(Integer voiceLed) {
		this.voiceLed = voiceLed;
	}
	public String getLedModel() {
		return ledModel;
	}
	public void setLedModel(String ledModel) {
		this.ledModel = ledModel;
	}
	public Integer getLedScreenRowNum() {
		return ledScreenRowNum;
	}
	public void setLedScreenRowNum(Integer ledScreenRowNum) {
		this.ledScreenRowNum = ledScreenRowNum;
	}
	public Integer getShowWay() {
		return showWay;
	}
	public void setShowWay(Integer showWay) {
		this.showWay = showWay;
	}
	public Integer getCommunication() {
		return communication;
	}
	public void setCommunication(Integer communication) {
		this.communication = communication;
	}
	public String getLedIp() {
		return ledIp;
	}
	public void setLedIp(String ledIp) {
		this.ledIp = ledIp;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getCameraType() {
		return cameraType;
	}
	public void setCameraType(Integer cameraType) {
		this.cameraType = cameraType;
	}
	public String getCameraIp() {
		return cameraIp;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCameraIp(String cameraIp) {
		this.cameraIp = cameraIp;
	}
	public String getCameraMac() {
		return cameraMac;
	}
	public void setCameraMac(String cameraMac) {
		this.cameraMac = cameraMac;
	}
	
	

}
