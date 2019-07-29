package com.dchip.cloudparking.api.model.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.api.model.po.ParkingInfo;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QParkingInfo is a Querydsl query type for ParkingInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QParkingInfo extends EntityPathBase<ParkingInfo> {

    private static final long serialVersionUID = 1793709488L;

    public static final QParkingInfo parkingInfo = new QParkingInfo("parkingInfo");

    public final NumberPath<Integer> appointmentId = createNumber("appointmentId", Integer.class);

    public final NumberPath<Integer> carType = createNumber("carType", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final NumberPath<Integer> inRoadWayId = createNumber("inRoadWayId", Integer.class);

    public final NumberPath<Integer> isLock = createNumber("isLock", Integer.class);

    public final StringPath licensePlate = createString("licensePlate");

    public final DateTimePath<java.util.Date> outDate = createDateTime("outDate", java.util.Date.class);

    public final NumberPath<Integer> outRoadWayId = createNumber("outRoadWayId", Integer.class);

    public final NumberPath<Integer> parkCode = createNumber("parkCode", Integer.class);

    public final DateTimePath<java.util.Date> parkDate = createDateTime("parkDate", java.util.Date.class);

    public final NumberPath<Integer> plateType = createNumber("plateType", Integer.class);

    public final ComparablePath<Character> status = createComparable("status", Character.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QParkingInfo(String variable) {
        super(ParkingInfo.class, forVariable(variable));
    }

    public QParkingInfo(Path<? extends ParkingInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParkingInfo(PathMetadata metadata) {
        super(ParkingInfo.class, metadata);
    }

}

