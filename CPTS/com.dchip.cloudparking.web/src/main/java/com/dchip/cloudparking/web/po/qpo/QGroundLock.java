package com.dchip.cloudparking.web.po.qpo;

import com.dchip.cloudparking.web.model.po.GroundLock;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.Generated;

import java.sql.Timestamp;
import java.util.Date;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QGroundLock is a Querydsl query type for GroundLock
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGroundLock extends EntityPathBase<GroundLock> {

    private static final long serialVersionUID = -211012318L;

    public static final QGroundLock groundLock = new QGroundLock("groundLock");

    public final DateTimePath<java.sql.Date> createTime = createDateTime("createTime", java.sql.Date.class);

    public final NumberPath<Integer> currentState = createNumber("currentState", Integer.class);

    public final StringPath groundUid = createString("groundUid");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath licensePlate = createString("licensePlate");

    public final NumberPath<Integer> mainId = createNumber("mainId", Integer.class);

    public final NumberPath<Integer> parkingId = createNumber("parkingId", Integer.class);

    public DateTimePath<java.sql.Date> getCreateTime() {
        return createTime;
    }

    public NumberPath<Integer> getCurrentState() {
        return currentState;
    }

    public StringPath getGroundUid() {
        return groundUid;
    }

    public NumberPath<Integer> getId() {
        return id;
    }

    public StringPath getLicensePlate() {
        return licensePlate;
    }

    public NumberPath<Integer> getMainId() {
        return mainId;
    }

    public NumberPath<Integer> getParkingId() {
        return parkingId;
    }

    public StringPath getRemark() {
        return remark;
    }

    public final StringPath remark = createString("remark");

    public QGroundLock(String variable) {
        super(GroundLock.class, forVariable(variable));
    }

    public QGroundLock(Path<? extends GroundLock> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroundLock(PathMetadata metadata) {
        super(GroundLock.class, metadata);
    }

}

