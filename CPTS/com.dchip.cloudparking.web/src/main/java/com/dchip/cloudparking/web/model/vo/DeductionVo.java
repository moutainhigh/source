package com.dchip.cloudparking.web.model.vo;

import com.dchip.cloudparking.web.model.po.Deduction;

public class DeductionVo extends Deduction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer deductionId;
	private Integer generateCount;
	private String qiniuCloudRelativeUrl;

	public static Deduction getDeduction(DeductionVo vo) {
		Deduction deduction = new Deduction();
		deduction.setId(vo.getDeductionId());
		deduction.setParkingId(vo.getParkingId());
		deduction.setHourNum(vo.getHourNum());
		deduction.setStatus(vo.getStatus());
		deduction.setDueTime(vo.getDueTime());
		deduction.setUseTime(vo.getUseTime());
		deduction.setLicensePlat(vo.getLicensePlat());
		deduction.setReceiveTime(vo.getReceiveTime());
		deduction.setDeductioinCode(vo.getDeductioinCode());
//		deduction.setConsumptionUrl(vo.getConsumptionUrl());
		deduction.setConsumptionUrl(vo.getQiniuCloudRelativeUrl());
		return deduction;
	}

	public Integer getDeductionId(){
		return deductionId;
	}
	public void setDeductionId(Integer deductionId){
		this.deductionId = deductionId;
	}

	public Integer getGenerateCount(){
		return generateCount;
	}

	public void setGenerateCount(Integer generateCount){
		this.generateCount = generateCount;
	}

	public String getQiniuCloudRelativeUrl(){
		return qiniuCloudRelativeUrl;
	}

	public void setQiniuCloudRelativeUrl(String qiniuCloudRelativeUrl){
		this.qiniuCloudRelativeUrl = qiniuCloudRelativeUrl;
	}

	@Override
	public String toString() {
		return "DeductionVo{" +
				"deductionId=" + deductionId +
				", generateCount=" + generateCount +
				", qiniuCloudRelativeUrl='" + qiniuCloudRelativeUrl + '\'' +
				'}' + super.toString();
	}
}
