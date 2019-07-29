package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.MainRoadway;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;


/**
 * QMainRoadway is a Querydsl query type for MainRoadway
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMainRoadway extends EntityPathBase<MainRoadway> {

    private static final long serialVersionUID = -298533632L;

    public static final QMainRoadway mainRoadway = new QMainRoadway("mainRoadway");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> mainId = createNumber("mainId", Integer.class);

    public final NumberPath<Integer> roadwayId = createNumber("roadwayId", Integer.class);

    public QMainRoadway(String variable) {
        super(MainRoadway.class, forVariable(variable));
    }

    public QMainRoadway(Path<? extends MainRoadway> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMainRoadway(PathMetadata metadata) {
        super(MainRoadway.class, metadata);
    }

	public DateTimePath<java.util.Date> getCreateTime() {
		return createTime;
	}

	public NumberPath<Integer> getId() {
		return id;
	}

	public NumberPath<Integer> getMainId() {
		return mainId;
	}

	public NumberPath<Integer> getRoadwayId() {
		return roadwayId;
	}

}

