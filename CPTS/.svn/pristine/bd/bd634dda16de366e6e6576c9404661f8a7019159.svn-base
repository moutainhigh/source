package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.Session;
import com.querydsl.core.types.Path;


/**
 * QSession is a Querydsl query type for Session
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSession extends EntityPathBase<Session> {

    private static final long serialVersionUID = 2056555712L;

    public static final QSession session = new QSession("session");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> platform = createNumber("platform", Integer.class);

    public final StringPath token = createString("token");

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QSession(String variable) {
        super(Session.class, forVariable(variable));
    }

    public QSession(Path<? extends Session> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSession(PathMetadata metadata) {
        super(Session.class, metadata);
    }

	public NumberPath<Integer> getId() {
		return id;
	}

	public NumberPath<Integer> getPlatform() {
		return platform;
	}

	public StringPath getToken() {
		return token;
	}

	public NumberPath<Integer> getUserId() {
		return userId;
	}

}

