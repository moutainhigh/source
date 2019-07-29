package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.PointRecord;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QPointRecord is a Querydsl query type for PointRecord
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPointRecord extends EntityPathBase<PointRecord> {

    private static final long serialVersionUID = -566876373L;

    public static final QPointRecord pointRecord = new QPointRecord("pointRecord");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath remark = createString("remark");

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QPointRecord(String variable) {
        super(PointRecord.class, forVariable(variable));
    }

    public QPointRecord(Path<? extends PointRecord> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPointRecord(PathMetadata metadata) {
        super(PointRecord.class, metadata);
    }

	public DateTimePath<java.util.Date> getCreateTime() {
		return createTime;
	}

	public NumberPath<Integer> getId() {
		return id;
	}

	public StringPath getRemark() {
		return remark;
	}

	public NumberPath<Integer> getScore() {
		return score;
	}

	public NumberPath<Integer> getUserId() {
		return userId;
	}

}

