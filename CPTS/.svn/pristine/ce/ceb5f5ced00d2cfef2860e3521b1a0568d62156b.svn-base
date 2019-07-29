package com.dchip.cloudparking.web.model.vo;

import com.dchip.cloudparking.web.model.po.MonthlyCard;

public class MonthlyCardVo extends MonthlyCard{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String expiryTimeString;
	private String startTimeString;
	private String endTimeString;
	
	public static MonthlyCard getMonthlyCardByVo(MonthlyCardVo vo) {
		MonthlyCard card = new MonthlyCard();
		card.setCarOwnerName(vo.getCarOwnerName());
		card.setId(vo.getId());
		card.setEndTime(vo.getEndTime());
		card.setStartTime(vo.getStartTime());
		card.setExpiryTime(vo.getExpiryTime());
		card.setLicensePlate(vo.getLicensePlate());
		card.setType(vo.getType());
		return card;
	}
	
	public String getExpiryTimeString() {
		return expiryTimeString;
	}
	public void setExpiryTimeString(String expiryTimeString) {
		this.expiryTimeString = expiryTimeString;
	}
	public String getStartTimeString() {
		return startTimeString;
	}
	public void setStartTimeString(String startTimeString) {
		this.startTimeString = startTimeString;
	}
	public String getEndTimeString() {
		return endTimeString;
	}
	public void setEndTimeString(String endTimeString) {
		this.endTimeString = endTimeString;
	}
	@Override
	public String toString() {
		return "MonthlyCardVo [expiryTimeString=" + expiryTimeString + ", startTimeString=" + startTimeString
				+ ", endTimeString=" + endTimeString + "]" + super.toString();
	}


	
}
