package com.dchip.cloudparking.api.model.qpo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.dchip.cloudparking.api.model.po.LockCommond;
import com.querydsl.core.types.Path;


/**
 * QLockCommond is a Querydsl query type for LockCommond
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLockCommond extends EntityPathBase<LockCommond> {

    private static final long serialVersionUID = -347753128L;

    public static final QLockCommond lockCommond = new QLockCommond("lockCommond");

    public final StringPath commond = createString("commond");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Integer> groundLockId = createNumber("groundLockId", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

//    public final NumberPath<Integer> isRead = createNumber("isRead", Integer.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public QLockCommond(String variable) {
        super(LockCommond.class, forVariable(variable));
    }

    public QLockCommond(Path<? extends LockCommond> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLockCommond(PathMetadata metadata) {
        super(LockCommond.class, metadata);
    }

}

