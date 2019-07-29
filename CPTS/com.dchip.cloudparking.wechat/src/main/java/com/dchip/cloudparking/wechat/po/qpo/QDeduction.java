package com.dchip.cloudparking.wechat.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.dchip.cloudparking.wechat.model.po.Deduction;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;


/**
 * QDeduction is a Querydsl query type for Deduction
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDeduction extends EntityPathBase<Deduction> {

    private static final long serialVersionUID = 280651925L;

    public static final QDeduction deduction = new QDeduction("deduction");

    public final StringPath consumptionUrl = createString("consumptionUrl");

    public final StringPath deductioinCode = createString("deductioinCode");

    public final NumberPath<Integer> deductionModelId = createNumber("deductionModelId", Integer.class);

    public final DateTimePath<java.util.Date> dueTime = createDateTime("dueTime", java.util.Date.class);

    public final NumberPath<Integer> hourNum = createNumber("hourNum", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final StringPath licensePlat = createString("licensePlat");

    public final NumberPath<Integer> parkingId = createNumber("parkingId", Integer.class);

    public final NumberPath<Integer> parkingUserId = createNumber("parkingUserId", Integer.class);

    public final DateTimePath<java.util.Date> receiveTime = createDateTime("receiveTime", java.util.Date.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final DateTimePath<java.util.Date> useTime = createDateTime("useTime", java.util.Date.class);

    public QDeduction(String variable) {
        super(Deduction.class, forVariable(variable));
    }

    public QDeduction(Path<? extends Deduction> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeduction(PathMetadata metadata) {
        super(Deduction.class, metadata);
    }

}

