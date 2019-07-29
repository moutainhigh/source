package com.dchip.cloudparking.api.model.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.api.model.po.LoginLog;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QLoginLog is a Querydsl query type for LoginLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLoginLog extends EntityPathBase<LoginLog> {

    private static final long serialVersionUID = -53620175L;

    public static final QLoginLog loginLog = new QLoginLog("loginLog");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath ip = createString("ip");

    public final DateTimePath<java.util.Date> loginAt = createDateTime("loginAt", java.util.Date.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QLoginLog(String variable) {
        super(LoginLog.class, forVariable(variable));
    }

    public QLoginLog(Path<? extends LoginLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLoginLog(PathMetadata metadata) {
        super(LoginLog.class, metadata);
    }

}

