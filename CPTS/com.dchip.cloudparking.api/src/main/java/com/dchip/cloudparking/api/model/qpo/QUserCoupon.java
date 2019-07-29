package com.dchip.cloudparking.api.model.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.api.model.po.UserCoupon;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;


/**
 * QUserCoupon is a Querydsl query type for UserCoupon
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserCoupon extends EntityPathBase<UserCoupon> {

    private static final long serialVersionUID = -1389479577L;

    public static final QUserCoupon userCoupon = new QUserCoupon("userCoupon");

    public final NumberPath<Integer> couponId = createNumber("couponId", Integer.class);
    
    public final NumberPath<Integer> endNum = createNumber("endNum", Integer.class);

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final NumberPath<Integer> activityId = createNumber("activityId", Integer.class);

    public final DateTimePath<java.util.Date> useTime = createDateTime("useTime", java.util.Date.class);

    public QUserCoupon(String variable) {
        super(UserCoupon.class, forVariable(variable));
    }

    public QUserCoupon(Path<? extends UserCoupon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserCoupon(PathMetadata metadata) {
        super(UserCoupon.class, metadata);
    }

}

