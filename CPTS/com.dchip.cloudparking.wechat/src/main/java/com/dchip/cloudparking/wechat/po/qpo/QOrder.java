package com.dchip.cloudparking.wechat.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.dchip.cloudparking.wechat.model.po.Order;
import com.querydsl.core.types.Path;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = 92673016L;

    public static final QOrder order = new QOrder("order1");

    public final NumberPath<Integer> couponId = createNumber("couponId", Integer.class);

    public final NumberPath<java.math.BigDecimal> discountAmount = createNumber("discountAmount", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> fee = createNumber("fee", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> finalFee = createNumber("finalFee", java.math.BigDecimal.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> isTransfer = createNumber("isTransfer", Integer.class);

    public final NumberPath<Integer> parkingInfoId = createNumber("parkingInfoId", Integer.class);

    public final NumberPath<Integer> parkingTime = createNumber("parkingTime", Integer.class);

    public final DateTimePath<java.util.Date> payTime = createDateTime("payTime", java.util.Date.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);
    
    public final StringPath outTradeNo = createString("outTradeNo");

    public QOrder(String variable) {
        super(Order.class, forVariable(variable));
    }

    public QOrder(Path<? extends Order> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrder(PathMetadata metadata) {
        super(Order.class, metadata);
    }

}

