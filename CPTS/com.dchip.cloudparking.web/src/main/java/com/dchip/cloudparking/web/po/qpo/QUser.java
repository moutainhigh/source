package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.User;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.ArrayPath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1665737185L;

    public static final QUser user = new QUser("user");

    public final NumberPath<java.math.BigDecimal> balance = createNumber("balance", java.math.BigDecimal.class);

    public final StringPath carOwnerName = createString("carOwnerName");

    public final StringPath city = createString("city");

    public final StringPath country = createString("country");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final StringPath driverLicenseNumber = createString("driverLicenseNumber");

    public final StringPath drivingLicenseNumber = createString("drivingLicenseNumber");

    public final NumberPath<Integer> falseReportNumber = createNumber("falseReportNumber", Integer.class);

    public final StringPath headimgurl = createString("headimgurl");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath idCard = createString("idCard");

    public final NumberPath<Integer> isBlack = createNumber("isBlack", Integer.class);

    public final DateTimePath<java.util.Date> latestLoginTime = createDateTime("latestLoginTime", java.util.Date.class);

    public final StringPath licensePlat = createString("licensePlat");

    public final NumberPath<Integer> memberId = createNumber("memberId", Integer.class);

    public final ArrayPath<byte[], Byte> nickname = createArray("nickname", byte[].class);

    public final StringPath openid = createString("openid");

    public final StringPath phone = createString("phone");

    public final StringPath privilege = createString("privilege");

    public final StringPath province = createString("province");

    public final StringPath pwd = createString("pwd");

    public final StringPath registrationId = createString("registrationId");

    public final StringPath sex = createString("sex");

    public final StringPath state = createString("state");

    public final StringPath type = createString("type");

    public final StringPath unionid = createString("unionid");

    public NumberPath<java.math.BigDecimal> getBalance() {
		return balance;
	}

	public StringPath getCarOwnerName() {
		return carOwnerName;
	}

	public StringPath getCity() {
		return city;
	}

	public StringPath getCountry() {
		return country;
	}

	public DateTimePath<java.util.Date> getCreateTime() {
		return createTime;
	}

	public StringPath getDriverLicenseNumber() {
		return driverLicenseNumber;
	}

	public StringPath getDrivingLicenseNumber() {
		return drivingLicenseNumber;
	}

	public NumberPath<Integer> getFalseReportNumber() {
		return falseReportNumber;
	}

	public StringPath getHeadimgurl() {
		return headimgurl;
	}

	public NumberPath<Integer> getId() {
		return id;
	}

	public StringPath getIdCard() {
		return idCard;
	}

	public NumberPath<Integer> getIsBlack() {
		return isBlack;
	}

	public DateTimePath<java.util.Date> getLatestLoginTime() {
		return latestLoginTime;
	}

	public StringPath getLicensePlat() {
		return licensePlat;
	}

	public NumberPath<Integer> getMemberId() {
		return memberId;
	}

	public ArrayPath<byte[], Byte> getNickname() {
		return nickname;
	}

	public StringPath getOpenid() {
		return openid;
	}

	public StringPath getPhone() {
		return phone;
	}

	public StringPath getPrivilege() {
		return privilege;
	}

	public StringPath getProvince() {
		return province;
	}

	public StringPath getPwd() {
		return pwd;
	}

	public StringPath getRegistrationId() {
		return registrationId;
	}

	public StringPath getSex() {
		return sex;
	}

	public StringPath getState() {
		return state;
	}



	public StringPath getUnionid() {
		return unionid;
	}

	public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

