package com.dchip.cloudparking.api.model.po;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "parking_concession", catalog = "cloud_parking")
public class ParkingConcession implements java.io.Serializable {

    private Integer id;
    private String licensePlate;//
    private Integer parkingId;//
    private String spaceNo;//
    private Integer userId;//出租者id
    private Integer tenantId;//承租者id
    private String tenantPlate;//承租者车牌
    private BigDecimal cost;//每小时花费
    private String effectDuringTime;//有效时间段
    private String effectDuringDate;//有效年月日段
    private Date rentTime;
    private Date publishTime;
    private String remark;//备注
    private Integer status;
    
//    private Integer dateType;//1-小时,2-天,3-月
//    private Integer dateTypeCount;
//    private BigDecimal dateTypeCost;

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

    @Column(name = "tenant_plate", length = 50)
    public String getTenantPlate() {
        return tenantPlate;
    }

    public void setTenantPlate(String tenantPlate) {
        this.tenantPlate = tenantPlate;
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

    @Column(name = "space_no", length = 50)
    public String getSpaceNo() {
        return spaceNo;
    }

    public void setSpaceNo(String spaceNo) {
        this.spaceNo = spaceNo;
    }

    @Column(name = "cost", precision = 10)
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

//    public void setDateType(Integer dateType) {
//        this.dateType = dateType;
//    }
//    @Column(name = "date_type_count", length = 10)
//    public Integer getDateTypeCount() {
//        return dateTypeCount;
//    }
//    public void setDateTypeCount(Integer dateTypeCount) {
//        this.dateTypeCount = dateTypeCount;
//    }
//    @Column(name = "date_type_cost", precision = 10)
//    public BigDecimal getDateTypeCost() {
//        return dateTypeCost;
//    }
//    public void setDateTypeCost(BigDecimal dateTypeCost) {
//        this.dateTypeCost = dateTypeCost;
//    }

    @Column(name = "effect_during_time")
    public String getEffectDuringTime() {
        return effectDuringTime;
    }

    public void setEffectDuringTime(String effectDuringTime) {
        this.effectDuringTime = effectDuringTime;
    }

    @Column(name = "effect_during_date")
    public String getEffectDuringDate() {
        return effectDuringDate;
    }

    public void setEffectDuringDate(String effectDuringDate) {
        this.effectDuringDate = effectDuringDate;
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
}
