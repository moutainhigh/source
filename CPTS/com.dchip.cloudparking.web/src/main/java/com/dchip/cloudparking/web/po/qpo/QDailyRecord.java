package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.DailyRecord;
import com.querydsl.core.types.Path;


/**
 * QDailyRecord is a Querydsl query type for DailyRecord
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDailyRecord extends EntityPathBase<DailyRecord> {

    private static final long serialVersionUID = -1070177036L;

    public static final QDailyRecord dailyRecord = new QDailyRecord("dailyRecord");

    public final NumberPath<Integer> accountId = createNumber("accountId", Integer.class);

    public final StringPath actName = createString("actName");

    public final DateTimePath<java.util.Date> actTime = createDateTime("actTime", java.util.Date.class);

    public final NumberPath<Integer> actType = createNumber("actType", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QDailyRecord(String variable) {
        super(DailyRecord.class, forVariable(variable));
    }

    public QDailyRecord(Path<? extends DailyRecord> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDailyRecord(PathMetadata metadata) {
        super(DailyRecord.class, metadata);
    }

	public NumberPath<Integer> getAccountId() {
		return accountId;
	}

	public StringPath getActName() {
		return actName;
	}

	public DateTimePath<java.util.Date> getActTime() {
		return actTime;
	}

	public NumberPath<Integer> getActType() {
		return actType;
	}

	public NumberPath<Integer> getId() {
		return id;
	}

}

