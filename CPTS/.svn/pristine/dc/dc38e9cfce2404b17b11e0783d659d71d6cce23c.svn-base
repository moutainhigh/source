package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.LoginLog;
import com.querydsl.core.types.Path;


/**
 * QLoginLog is a Querydsl query type for LoginLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLoginLog extends EntityPathBase<LoginLog> {

    private static final long serialVersionUID = 2119567595L;

    public static final QLoginLog loginLog = new QLoginLog("loginLog");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath ip = createString("ip");

    public final DateTimePath<java.util.Date> loginAt = createDateTime("loginAt", java.util.Date.class);

    public final NumberPath<Integer> sourceFlag = createNumber("sourceFlag", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);
    
    public NumberPath<Integer> getId(){
    	return id;
    }

    public QLoginLog(String variable) {
        super(LoginLog.class, forVariable(variable));
    }

    public QLoginLog(Path<? extends LoginLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLoginLog(PathMetadata metadata) {
        super(LoginLog.class, metadata);
    }

	public StringPath getIp() {
		return ip;
	}

	public DateTimePath<java.util.Date> getLoginAt() {
		return loginAt;
	}

	public NumberPath<Integer> getSourceFlag() {
		return sourceFlag;
	}

	public NumberPath<Integer> getUserId() {
		return userId;
	}

}

