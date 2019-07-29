package com.dchip.cloudparking.web.model.po;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "parking_lot", schema = "cloud_parking", catalog = "")
public class ParkingLot {
    private int id;
    private Integer mainId;
    private String uid;
    private String version;
    private Integer online;
    private Integer initState;
    private String smartCustom;
    private Integer upHeight;
    private Integer downHeight;
    private Integer areaId;
    private String number;
    private Integer status;
    private Integer state;

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
    @Column(name = "main_id")
    public Integer getMainId() {
        return mainId;
    }

    public void setMainId(Integer mainId) {
        this.mainId = mainId;
    }

    @Basic
    @Column(name = "uid")
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Basic
    @Column(name = "online")
    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    @Basic
    @Column(name = "init_state")
    public Integer getInitState() {
        return initState;
    }

    public void setInitState(Integer initState) {
        this.initState = initState;
    }

    @Basic
    @Column(name = "smart_custom")
    public String getSmartCustom() {
        return smartCustom;
    }

    public void setSmartCustom(String smartCustom) {
        this.smartCustom = smartCustom;
    }

    @Basic
    @Column(name = "up_height")
    public Integer getUpHeight() {
        return upHeight;
    }

    public void setUpHeight(Integer upHeight) {
        this.upHeight = upHeight;
    }

    @Basic
    @Column(name = "down_height")
    public Integer getDownHeight() {
        return downHeight;
    }

    public void setDownHeight(Integer downHeight) {
        this.downHeight = downHeight;
    }

    @Basic
    @Column(name = "area_id")
    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    @Basic
    @Column(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingLot that = (ParkingLot) o;
        return id == that.id &&
                mainId == that.mainId &&
                Objects.equals(uid, that.uid) &&
                Objects.equals(version, that.version) &&
                Objects.equals(online, that.online) &&
                Objects.equals(initState, that.initState) &&
                Objects.equals(smartCustom, that.smartCustom) &&
                Objects.equals(upHeight, that.upHeight) &&
                Objects.equals(downHeight, that.downHeight) &&
                Objects.equals(areaId, that.areaId) &&
                Objects.equals(number, that.number) &&
                Objects.equals(status, that.status) &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mainId, uid, version, online, initState, smartCustom, upHeight, downHeight, areaId, number, status, state);
    }
}
