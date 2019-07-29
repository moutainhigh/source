package com.dchip.cloudparking.web.model.po;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "parking_concession", catalog = "cloud_parking")
public class ParkingConcession implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String licensePlate;//50
    private Integer parkingId;//11
    private Integer spaceNo;//11
    private Integer userId;//11,出租者id
    private Integer tenantId;//11,承接者id
    private BigDecimal cost;
    private String effectDuringTime;
    private String effectDuringDate;

	private Date beginTime;
    private Date endTime;

    private Date beginDate;
    private Date endDate;

    private Integer dateType;//1-小时,2-天,3-月
    private Integer dateTypeCount;
    private BigDecimal dateTypeCost;

    private Date rentTime;
    
    


	private Date publishTime;
    private String remark;//备注

    private Integer status;
    
    private String parkingName;

	private String phoneNumber;
    



	public ParkingConcession(){

    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "user_id", nullable = false )
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "tenant_id", nullable = false )
    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    @Column(name = "license_plate", length = 50)
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Column(name = "parking_id", length = 11)
    public Integer getParkingId() {
        return parkingId;
    }

    public void setParkingId(Integer parkingId) {
        this.parkingId = parkingId;
    }

    @Column(name = "space_no", length = 11)
    public Integer getSpaceNo() {
        return spaceNo;
    }

    public void setSpaceNo(Integer spaceNo) {
        this.spaceNo = spaceNo;
    }

    @Column(name = "cost", precision = 10)
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    
    @Column(name = "effect_during_time", precision = 255)
    public String getEffectDuringTime() {
		return effectDuringTime;
	}

	public void setEffectDuringTime(String effectDuringTime) {
		this.effectDuringTime = effectDuringTime;
	}
	
	@Column(name = "effect_during_date", precision = 255)
	public String getEffectDuringDate() {
		return effectDuringDate;
	}

	public void setEffectDuringDate(String effectDuringDate) {
		this.effectDuringDate = effectDuringDate;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "begin_time", length = 19)
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time", length = 19)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "begin_date", length = 19)
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", length = 19)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "date_type", length = 2)
    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    @Column(name = "date_type_count", length = 10)
    public Integer getDateTypeCount() {
        return dateTypeCount;
    }

    public void setDateTypeCount(Integer dateTypeCount) {
        this.dateTypeCount = dateTypeCount;
    }

    @Column(name = "date_type_cost", precision = 10)
    public BigDecimal getDateTypeCost() {
        return dateTypeCost;
    }

    public void setDateTypeCost(BigDecimal dateTypeCost) {
        this.dateTypeCost = dateTypeCost;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rent_time", length = 19)
    public Date getRentTime() {
        return rentTime;
    }

    public void setRentTime(Date rentTime) {
        this.rentTime = rentTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publish_time", length = 19)
    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }


    @Column(name = "remark", length = 254)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Column(name = "status", length = 2 )
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    @Column(name = "parkingName", length = 50 )
        public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	
	@Column(name = "phoneNumber", length = 50 )
    public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
