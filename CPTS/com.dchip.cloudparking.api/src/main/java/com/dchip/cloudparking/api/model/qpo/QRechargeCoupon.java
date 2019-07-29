package com.dchip.cloudparking.api.model.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.api.model.po.RechargeCoupon;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;


/**
 * QRechargeCoupon is a Querydsl query type for RechargeCoupon
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRechargeCoupon extends EntityPathBase<RechargeCoupon> {

    private static final long serialVersionUID = 476216387L;

    public static final QRechargeCoupon rechargeCoupon = new QRechargeCoupon("rechargeCoupon");

    public final NumberPath<Integer> couponId = createNumber("couponId", Integer.class);

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<java.math.BigDecimal> inMoney = createNumber("inMoney", java.math.BigDecimal.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public QRechargeCoupon(String variable) {
        super(RechargeCoupon.class, forVariable(variable));
    }

    public QRechargeCoupon(Path<? extends RechargeCoupon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRechargeCoupon(PathMetadata metadata) {
        super(RechargeCoupon.class, metadata);
    }

}

