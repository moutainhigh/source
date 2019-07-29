package com.dchip.cloudparking.api.model.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.api.model.po.Area;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QArea is a Querydsl query type for Area
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QArea extends EntityPathBase<Area> {

    private static final long serialVersionUID = 1665140387L;

    public static final QArea area = new QArea("area");

    public final NumberPath<Integer> areaCode = createNumber("areaCode", Integer.class);

    public final StringPath areaName = createString("areaName");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath latitude = createString("latitude");

    public final StringPath longitude = createString("longitude");

    public final NumberPath<Integer> parkCode = createNumber("parkCode", Integer.class);

    public final NumberPath<Integer> parkingId = createNumber("parkingId", Integer.class);

    public final NumberPath<Integer> spaceCount = createNumber("spaceCount", Integer.class);

    public QArea(String variable) {
        super(Area.class, forVariable(variable));
    }

    public QArea(Path<? extends Area> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArea(PathMetadata metadata) {
        super(Area.class, metadata);
    }

}

