package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.UserLicensePlat;
import com.querydsl.core.types.Path;


/**
 * QUserLicensePlat is a Querydsl query type for UserLicensePlat
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserLicensePlat extends EntityPathBase<UserLicensePlat> {

    private static final long serialVersionUID = -876823953L;

    public static final QUserLicensePlat userLicensePlat = new QUserLicensePlat("userLicensePlat");

    public final DateTimePath<java.util.Date> bindTime = createDateTime("bindTime", java.util.Date.class);

    public final StringPath carOwnerName = createString("carOwnerName");

    public final StringPath drivingLicenseNumber = createString("drivingLicenseNumber");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath licensePlat = createString("licensePlat");

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QUserLicensePlat(String variable) {
        super(UserLicensePlat.class, forVariable(variable));
    }

    public QUserLicensePlat(Path<? extends UserLicensePlat> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserLicensePlat(PathMetadata metadata) {
        super(UserLicensePlat.class, metadata);
    }

	public DateTimePath<java.util.Date> getBindTime() {
		return bindTime;
	}

	public StringPath getCarOwnerName() {
		return carOwnerName;
	}

	public StringPath getDrivingLicenseNumber() {
		return drivingLicenseNumber;
	}

	public NumberPath<Integer> getId() {
		return id;
	}

	public StringPath getLicensePlat() {
		return licensePlat;
	}

	public NumberPath<Integer> getUserId() {
		return userId;
	}

}

