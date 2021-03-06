package com.dchip.cloudparking.api.model.po;
// Generated 2018-11-30 17:46:32 by Hibernate Tools 5.2.10.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * UserLicensePlat generated by hbm2java
 */
@Entity
@Table(name = "user_license_plat", catalog = "cloud_parking")
public class UserLicensePlat implements java.io.Serializable {

	private Integer id;
	private Integer userId;
	private String licensePlat;
	private Date bindTime;
	private String carOwnerName;
	private String drivingLicenseNumber;

	public UserLicensePlat() {
	}

	public UserLicensePlat(Integer userId, String licensePlat, Date bindTime, String carOwnerName,
			String drivingLicenseNumber) {
		this.userId = userId;
		this.licensePlat = licensePlat;
		this.bindTime = bindTime;
		this.carOwnerName = carOwnerName;
		this.drivingLicenseNumber = drivingLicenseNumber;
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

	@Column(name = "user_id")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "license_plat", length = 20)
	public String getLicensePlat() {
		return this.licensePlat;
	}

	public void setLicensePlat(String licensePlat) {
		this.licensePlat = licensePlat;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "bind_time", length = 19)
	public Date getBindTime() {
		return this.bindTime;
	}

	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}

	@Column(name = "car_owner_name", length = 50)
	public String getCarOwnerName() {
		return this.carOwnerName;
	}

	public void setCarOwnerName(String carOwnerName) {
		this.carOwnerName = carOwnerName;
	}

	@Column(name = "driving_license_number", length = 100)
	public String getDrivingLicenseNumber() {
		return this.drivingLicenseNumber;
	}

	public void setDrivingLicenseNumber(String drivingLicenseNumber) {
		this.drivingLicenseNumber = drivingLicenseNumber;
	}

}
