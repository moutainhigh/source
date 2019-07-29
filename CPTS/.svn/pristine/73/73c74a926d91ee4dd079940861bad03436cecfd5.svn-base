package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import java.util.Date;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.Version;
import com.querydsl.core.types.Path;


/**
 * QVersion is a Querydsl query type for Version
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVersion extends EntityPathBase<Version> {

    private static final long serialVersionUID = -892194552L;

    public static final QVersion version = new QVersion("version");

    public final StringPath address = createString("address");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath md5 = createString("md5");

    public final StringPath remark = createString("remark");

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public final NumberPath<Integer> updateType = createNumber("updateType", Integer.class);

    public final StringPath versionCode = createString("versionCode");

    public QVersion(String variable) {
        super(Version.class, forVariable(variable));
    }

    public QVersion(Path<? extends Version> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVersion(PathMetadata metadata) {
        super(Version.class, metadata);
    }
    
    public NumberPath<Integer> getId() {
		return id;
	}
    
    public DateTimePath<java.util.Date> getCreateTime() {
		return createTime;
	}

}

