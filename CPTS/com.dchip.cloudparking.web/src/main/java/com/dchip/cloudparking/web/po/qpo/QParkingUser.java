package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.ParkingUser;
import com.querydsl.core.types.Path;


/**
 * QParkingUser is a Querydsl query type for ParkingUser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QParkingUser extends EntityPathBase<ParkingUser> {

    private static final long serialVersionUID = 893907923L;

    public static final QParkingUser parkingUser = new QParkingUser("parkingUser");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> parkingId = createNumber("parkingId", Integer.class);

    public final StringPath password = createString("password");

    public final StringPath realName = createString("realName");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public final StringPath userName = createString("userName");

    public QParkingUser(String variable) {
        super(ParkingUser.class, forVariable(variable));
    }

    public QParkingUser(Path<? extends ParkingUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParkingUser(PathMetadata metadata) {
        super(ParkingUser.class, metadata);
    }

	public DateTimePath<java.util.Date> getCreateTime() {
		return createTime;
	}

	public NumberPath<Integer> getId() {
		return id;
	}

	public NumberPath<Integer> getParkingId() {
		return parkingId;
	}

	public StringPath getPassword() {
		return password;
	}

	public StringPath getRealName() {
		return realName;
	}

	public NumberPath<Integer> getStatus() {
		return status;
	}

	public StringPath getUserName() {
		return userName;
	}

}

