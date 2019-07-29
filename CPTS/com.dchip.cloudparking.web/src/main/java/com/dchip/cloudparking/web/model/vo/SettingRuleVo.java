package com.dchip.cloudparking.web.model.vo;

public class SettingRuleVo {
	private Integer parkingId;
	private Integer inRuleId;
	private Integer outRuleId;
	private Integer inrule;
	private Integer outrule;
	private Integer startDay;
	private Integer endDay;
	private String startTime;
	private String endTime;
	private Integer isSupportCard;
	private Integer max;
	
	public Integer getParkingId() {
		return parkingId;
	}
	public void setParkingId(Integer parkingId) {
		this.parkingId = parkingId;
	}
	public Integer getInRuleId() {
		return inRuleId;
	}
	public void setInRuleId(Integer inRuleId) {
		this.inRuleId = inRuleId;
	}
	public Integer getOutRuleId() {
		return outRuleId;
	}
	public void setOutRuleId(Integer outRuleId) {
		this.outRuleId = outRuleId;
	}
	public Integer getInrule() {
		return inrule;
	}
	public void setInrule(Integer inrule) {
		this.inrule = inrule;
	}
	public Integer getOutrule() {
		return outrule;
	}
	public void setOutrule(Integer outrule) {
		this.outrule = outrule;
	}
	public Integer getStartDay() {
		return startDay;
	}
	public void setStartDay(Integer startDay) {
		this.startDay = startDay;
	}
	public Integer getEndDay() {
		return endDay;
	}
	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getIsSupportCard() {
		return isSupportCard;
	}
	public void setIsSupportCard(Integer isSupportCard) {
		this.isSupportCard = isSupportCard;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	
}
