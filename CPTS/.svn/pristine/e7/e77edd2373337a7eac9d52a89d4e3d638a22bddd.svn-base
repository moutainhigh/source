package com.dchip.cloudparking.web.model.po;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "ground_lock", schema = "cloud_parking", catalog = "")
public class GroundLock {
    private int id;
	private Integer parkingId;
    private String groundUid;
    private String licensePlate;
    private Integer currentState;
    private Integer mainId;
    private Date createTime;
    private String remark;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "parking_id")
    public Integer getParkingId() {
        return parkingId;
    }

    public void setParkingId(Integer parkingId) {
        this.parkingId = parkingId;
    }
    
    @Basic
    @Column(name = "ground_uid")
    public String getGroundUid() {
        return groundUid;
    }

    public void setGroundUid(String groundUid) {
        this.groundUid = groundUid;
    }

    @Basic
    @Column(name = "license_plate")
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Basic
    @Column(name = "current_state")
    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
    }

    @Basic
    @Column(name = "main_id")
    public Integer getMainId() {
        return mainId;
    }

    public void setMainId(Integer mainId) {
        this.mainId = mainId;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroundLock that = (GroundLock) o;
        return id == that.id &&
                Objects.equals(parkingId, that.parkingId) &&
                Objects.equals(groundUid, that.groundUid) &&
                Objects.equals(licensePlate, that.licensePlate) &&
                Objects.equals(currentState, that.currentState) &&
                Objects.equals(mainId, that.mainId) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parkingId, groundUid, licensePlate, currentState, mainId, createTime, remark);
    }
}
