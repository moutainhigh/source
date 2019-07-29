package com.dchip.cloudparking.api.model.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.api.model.po.RechargeLog;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QRechargeLog is a Querydsl query type for RechargeLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRechargeLog extends EntityPathBase<RechargeLog> {

    private static final long serialVersionUID = -572474297L;

    public static final QRechargeLog rechargeLog = new QRechargeLog("rechargeLog");

    public final NumberPath<java.math.BigDecimal> discountMoney = createNumber("discountMoney", java.math.BigDecimal.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<java.math.BigDecimal> inMoney = createNumber("inMoney", java.math.BigDecimal.class);

    public final StringPath outTradeNo = createString("outTradeNo");

    public final DateTimePath<java.util.Date> rechargeDate = createDateTime("rechargeDate", java.util.Date.class);

    public final BooleanPath status = createBoolean("status");

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QRechargeLog(String variable) {
        super(RechargeLog.class, forVariable(variable));
    }

    public QRechargeLog(Path<? extends RechargeLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRechargeLog(PathMetadata metadata) {
        super(RechargeLog.class, metadata);
    }

}

