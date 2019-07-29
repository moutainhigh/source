package com.dchip.cloudparking.web.po.qpo;


import com.dchip.cloudparking.web.model.po.ParkingConcession;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.Generated;
import java.math.BigDecimal;
import java.util.Date;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

/**
 * QParkingConcession is a Querydsl query type for ParkingConcession
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QParkingConcession extends EntityPathBase<ParkingConcession> {

    private static final long serialVersionUID = 393027142L;

    public static final QParkingConcession parkingConcession = new QParkingConcession("parkingConcession");

    public final NumberPath<BigDecimal> cost = createNumber("cost", java.math.BigDecimal.class);

    public final StringPath effectDuringDate = createString("effectDuringDate");

    public final StringPath effectDuringTime = createString("effectDuringTime");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath licensePlate = createString("licensePlate");

    public final StringPath tenantPlate = createString("tenantPlate");

    public final NumberPath<Integer> parkingId = createNumber("parkingId", Integer.class);

    public final DateTimePath<Date> publishTime = createDateTime("publishTime", java.util.Date.class);

    public final StringPath remark = createString("remark");

    public final DateTimePath<java.util.Date> rentTime = createDateTime("rentTime", java.util.Date.class);

    public final NumberPath<Integer> spaceNo = createNumber("spaceNo", Integer.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> tenantId = createNumber("tenantId", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QParkingConcession(String variable) {
        super(ParkingConcession.class, forVariable(variable));
    }

    public QParkingConcession(Path<? extends ParkingConcession> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParkingConcession(PathMetadata metadata) {
        super(ParkingConcession.class, metadata);
    }

}


