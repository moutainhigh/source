package com.dchip.cloudparking.web.po.qpo;

import com.dchip.cloudparking.web.model.po.LockCommond;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.Generated;

import java.sql.Timestamp;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QLockCommond is a Querydsl query type for LockCommond
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLockCommond extends EntityPathBase<LockCommond> {

    private static final long serialVersionUID = -1247916962L;

    public static final QLockCommond lockCommond = new QLockCommond("lockCommond");

    public final StringPath commond = createString("commond");

    public final DateTimePath<java.sql.Timestamp> createTime = createDateTime("createTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> groundLockId = createNumber("groundLockId", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public StringPath getCommond() {
        return commond;
    }

    public DateTimePath<Timestamp> getCreateTime() {
        return createTime;
    }

    public NumberPath<Integer> getGroundLockId() {
        return groundLockId;
    }

    public NumberPath<Integer> getId() {
        return id;
    }

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

