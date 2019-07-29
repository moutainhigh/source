package com.dchip.cloudparking.api.model.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.api.model.po.CompanyBalanceWay;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QCompanyBalanceWay is a Querydsl query type for CompanyBalanceWay
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCompanyBalanceWay extends EntityPathBase<CompanyBalanceWay> {

    private static final long serialVersionUID = -978557830L;

    public static final QCompanyBalanceWay companyBalanceWay = new QCompanyBalanceWay("companyBalanceWay");

    public final StringPath balanceAccount = createString("balanceAccount");

    public final NumberPath<Integer> balanceWay = createNumber("balanceWay", Integer.class);

    public final StringPath bankCard = createString("bankCard");

    public final StringPath bankHolder = createString("bankHolder");

    public final StringPath bankName = createString("bankName");

    public final StringPath bankNum = createString("bankNum");

    public final NumberPath<Integer> companyId = createNumber("companyId", Integer.class);

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> isFirst = createNumber("isFirst", Integer.class);

    public QCompanyBalanceWay(String variable) {
        super(CompanyBalanceWay.class, forVariable(variable));
    }

    public QCompanyBalanceWay(Path<? extends CompanyBalanceWay> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompanyBalanceWay(PathMetadata metadata) {
        super(CompanyBalanceWay.class, metadata);
    }

}

