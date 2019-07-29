package com.dchip.cloudparking.api.model.qpo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.dchip.cloudparking.api.model.po.SecondPlateName;
import com.querydsl.core.types.Path;


/**
 * QSecondPlateName is a Querydsl query type for SecondPlateName
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSecondPlateName extends EntityPathBase<SecondPlateName> {

    private static final long serialVersionUID = 559599837L;

    public static final QSecondPlateName secondPlateName = new QSecondPlateName("secondPlateName");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath licensePlate = createString("licensePlate");

    public final NumberPath<Integer> parkingId = createNumber("parkingId", Integer.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> tmpFlag = createNumber("tmpFlag", Integer.class);

    public QSecondPlateName(String variable) {
        super(SecondPlateName.class, forVariable(variable));
    }

    public QSecondPlateName(Path<? extends SecondPlateName> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSecondPlateName(PathMetadata metadata) {
        super(SecondPlateName.class, metadata);
    }

}

