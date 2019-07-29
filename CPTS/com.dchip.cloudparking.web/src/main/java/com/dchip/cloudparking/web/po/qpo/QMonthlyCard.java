package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.MonthlyCard;
import com.querydsl.core.types.Path;


/**
 * QMonthlyCard is a Querydsl query type for MonthlyCard
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMonthlyCard extends EntityPathBase<MonthlyCard> {

    private static final long serialVersionUID = -249811731L;

    public static final QMonthlyCard monthlyCard = new QMonthlyCard("monthlyCard");

    public final StringPath carOwnerName = createString("carOwnerName");

    public final TimePath<java.util.Date> endTime = createTime("endTime", java.util.Date.class);

    public final DateTimePath<java.util.Date> expiryTime = createDateTime("expiryTime", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath licensePlate = createString("licensePlate");

    public final NumberPath<Integer> parkingId = createNumber("parkingId", Integer.class);

    public final TimePath<java.util.Date> startTime = createTime("startTime", java.util.Date.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public StringPath getCarOwnerName() {
		return carOwnerName;
	}

	public TimePath<java.util.Date> getEndTime() {
		return endTime;
	}

	public DateTimePath<java.util.Date> getExpiryTime() {
		return expiryTime;
	}

	public NumberPath<Integer> getId() {
		return id;
	}

	public StringPath getLicensePlate() {
		return licensePlate;
	}

	public NumberPath<Integer> getParkingId() {
		return parkingId;
	}

	public TimePath<java.util.Date> getStartTime() {
		return startTime;
	}

	public QMonthlyCard(String variable) {
        super(MonthlyCard.class, forVariable(variable));
    }

    public QMonthlyCard(Path<? extends MonthlyCard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMonthlyCard(PathMetadata metadata) {
        super(MonthlyCard.class, metadata);
    }

}

