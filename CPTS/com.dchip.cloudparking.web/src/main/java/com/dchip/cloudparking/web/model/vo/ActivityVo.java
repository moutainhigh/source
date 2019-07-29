package com.dchip.cloudparking.web.model.vo;

import com.dchip.cloudparking.web.model.po.Activity;

public class ActivityVo  extends Activity {
	private Integer activityId;
	private String activityRemark;
	private String effectiveTimeString;

	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getEffectiveTimeString() {
		return effectiveTimeString;
	}
	public void setEffectiveTimeString(String effectiveTimeString){
		this.effectiveTimeString = effectiveTimeString;
	}

	public String getActivityRemark() {
		return activityRemark;
	}
	public void setActivityRemark(String activityRemark){
		this.activityRemark = activityRemark;
	}


}
