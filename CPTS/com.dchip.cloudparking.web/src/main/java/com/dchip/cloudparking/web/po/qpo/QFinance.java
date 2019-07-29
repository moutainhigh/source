package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.Finance;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QFinance is a Querydsl query type for Finance
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFinance extends EntityPathBase<Finance> {

    private static final long serialVersionUID = -781690364L;

    public static final QFinance finance = new QFinance("finance");

    public final StringPath calculateAccount = createString("calculateAccount");

    public final StringPath calculateName = createString("calculateName");

    public final StringPath calculatePerson = createString("calculatePerson");

    public final DatePath<java.util.Date> chargeTime = createDate("chargeTime", java.util.Date.class);

    public final NumberPath<Integer> companyBalcanceWayId = createNumber("companyBalcanceWayId", Integer.class);

    public final NumberPath<Integer> companyId = createNumber("companyId", Integer.class);

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath orderId = createString("orderId");

    public final NumberPath<Integer> parkingId = createNumber("parkingId", Integer.class);

    public final NumberPath<java.math.BigDecimal> payMoney = createNumber("payMoney", java.math.BigDecimal.class);

    public final NumberPath<Integer> payPlatform = createNumber("payPlatform", Integer.class);

    public final DateTimePath<java.util.Date> payTime = createDateTime("payTime", java.util.Date.class);

    public final StringPath remark = createString("remark");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<java.math.BigDecimal> totalAmount = createNumber("totalAmount", java.math.BigDecimal.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public QFinance(String variable) {
        super(Finance.class, forVariable(variable));
    }

    public QFinance(Path<? extends Finance> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFinance(PathMetadata metadata) {
        super(Finance.class, metadata);
    }

	public StringPath getCalculateAccount() {
		return calculateAccount;
	}

	public StringPath getCalculateName() {
		return calculateName;
	}

	public StringPath getCalculatePerson() {
		return calculatePerson;
	}

	public DatePath<java.util.Date> getChargeTime() {
		return chargeTime;
	}

	public NumberPath<Integer> getCompanyBalcanceWayId() {
		return companyBalcanceWayId;
	}

	public NumberPath<Integer> getCompanyId() {
		return companyId;
	}

	public DateTimePath<java.util.Date> getCreateTime() {
		return createTime;
	}

	public NumberPath<Integer> getId() {
		return id;
	}

	public StringPath getOrderId() {
		return orderId;
	}

	public NumberPath<Integer> getParkingId() {
		return parkingId;
	}

	public NumberPath<java.math.BigDecimal> getPayMoney() {
		return payMoney;
	}

	public NumberPath<Integer> getPayPlatform() {
		return payPlatform;
	}

	public DateTimePath<java.util.Date> getPayTime() {
		return payTime;
	}

	public StringPath getRemark() {
		return remark;
	}

	public NumberPath<Integer> getStatus() {
		return status;
	}

	public NumberPath<java.math.BigDecimal> getTotalAmount() {
		return totalAmount;
	}

}

