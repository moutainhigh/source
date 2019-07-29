package com.dchip.cloudparking.web.model.po;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "lock_commond", schema = "cloud_parking", catalog = "")
public class LockCommond {
    private int id;
    private Integer type;
    private String commond;
    private int groundLockId;
    private Timestamp createTime;

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
    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "commond")
    public String getCommond() {
        return commond;
    }

    public void setCommond(String commond) {
        this.commond = commond;
    }

    @Basic
    @Column(name = "ground_lock_id")
    public int getGroundLockId() {
        return groundLockId;
    }

    public void setGroundLockId(int groundLockId) {
        this.groundLockId = groundLockId;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LockCommond that = (LockCommond) o;
        return id == that.id &&
                groundLockId == that.groundLockId &&
                Objects.equals(type, that.type) &&
                Objects.equals(commond, that.commond) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, commond, groundLockId, createTime);
    }
}
