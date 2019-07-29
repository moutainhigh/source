package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.MainControlVersion;
import com.querydsl.core.types.Path;


/**
 * QMainControlVersion is a Querydsl query type for MainControlVersion
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMainControlVersion extends EntityPathBase<MainControlVersion> {

    private static final long serialVersionUID = 1328694436L;

    public static final QMainControlVersion mainControlVersion = new QMainControlVersion("mainControlVersion");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final StringPath currentVersion = createString("currentVersion");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath installFailReasion = createString("installFailReasion");

    public final StringPath mac = createString("mac");

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public QMainControlVersion(String variable) {
        super(MainControlVersion.class, forVariable(variable));
    }

    public QMainControlVersion(Path<? extends MainControlVersion> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMainControlVersion(PathMetadata metadata) {
        super(MainControlVersion.class, metadata);
    }

    public NumberPath<Integer> getId() {
 		return id;
 	}
}

