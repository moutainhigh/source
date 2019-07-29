package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.MonthlyCardPay;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;


/**
 * QMonthlyCardPay is a Querydsl query type for MonthlyCardPay
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMonthlyCardPay extends EntityPathBase<MonthlyCardPay> {

    private static final long serialVersionUID = 42108225L;

    public static final QMonthlyCardPay monthlyCardPay = new QMonthlyCardPay("monthlyCardPay");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> monthlyCardId = createNumber("monthlyCardId", Integer.class);

    public final NumberPath<java.math.BigDecimal> paymentMoney = createNumber("paymentMoney", java.math.BigDecimal.class);

    public final DateTimePath<java.util.Date> paymentTime = createDateTime("paymentTime", java.util.Date.class);

    public final NumberPath<Integer> paymentWay = createNumber("paymentWay", Integer.class);

    public QMonthlyCardPay(String variable) {
        super(MonthlyCardPay.class, forVariable(variable));
    }

    public QMonthlyCardPay(Path<? extends MonthlyCardPay> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMonthlyCardPay(PathMetadata metadata) {
        super(MonthlyCardPay.class, metadata);
    }

	public NumberPath<Integer> getId() {
		return id;
	}

	public NumberPath<Integer> getMonthlyCardId() {
		return monthlyCardId;
	}

	public NumberPath<java.math.BigDecimal> getPaymentMoney() {
		return paymentMoney;
	}

	public DateTimePath<java.util.Date> getPaymentTime() {
		return paymentTime;
	}

	public NumberPath<Integer> getPaymentWay() {
		return paymentWay;
	}

}

