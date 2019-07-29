package com.dchip.cloudparking.wechat.model.po;
// Generated 2018-11-1 11:18:16 by Hibernate Tools 5.2.10.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MemberRule generated by hbm2java
 */
@Entity
@Table(name = "member_rule", catalog = "cloud_parking")
public class MemberRule implements java.io.Serializable {

	private Integer id;
	private Integer grade;
	private String gradeDescription;
	private Integer addWay;
	private Integer money;
	private Integer maximumArrears;
	private Integer maximumTimes;

	public MemberRule() {
	}

	public MemberRule(Integer grade, String gradeDescription, Integer addWay, Integer money, Integer maximumArrears,
			Integer maximumTimes) {
		this.grade = grade;
		this.gradeDescription = gradeDescription;
		this.addWay = addWay;
		this.money = money;
		this.maximumArrears = maximumArrears;
		this.maximumTimes = maximumTimes;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "grade")
	public Integer getGrade() {
		return this.grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	@Column(name = "grade_description", length = 50)
	public String getGradeDescription() {
		return this.gradeDescription;
	}

	public void setGradeDescription(String gradeDescription) {
		this.gradeDescription = gradeDescription;
	}

	@Column(name = "add_way")
	public Integer getAddWay() {
		return this.addWay;
	}

	public void setAddWay(Integer addWay) {
		this.addWay = addWay;
	}

	@Column(name = "money")
	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	@Column(name = "maximum_arrears")
	public Integer getMaximumArrears() {
		return this.maximumArrears;
	}

	public void setMaximumArrears(Integer maximumArrears) {
		this.maximumArrears = maximumArrears;
	}

	@Column(name = "maximum_times")
	public Integer getMaximumTimes() {
		return this.maximumTimes;
	}

	public void setMaximumTimes(Integer maximumTimes) {
		this.maximumTimes = maximumTimes;
	}

}