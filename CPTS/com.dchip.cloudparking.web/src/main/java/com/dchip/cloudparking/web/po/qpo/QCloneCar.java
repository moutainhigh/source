package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.CloneCar;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;


/**
 * QCloneCar is a Querydsl query type for CloneCar
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCloneCar extends EntityPathBase<CloneCar> {

    private static final long serialVersionUID = -988181427L;

    public static final QCloneCar cloneCar = new QCloneCar("cloneCar");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> parkingInfoId = createNumber("parkingInfoId", Integer.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public QCloneCar(String variable) {
        super(CloneCar.class, forVariable(variable));
    }

    public QCloneCar(Path<? extends CloneCar> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCloneCar(PathMetadata metadata) {
        super(CloneCar.class, metadata);
    }

	public DateTimePath<java.util.Date> getCreateTime() {
		return createTime;
	}

	public NumberPath<Integer> getId() {
		return id;
	}

	public NumberPath<Integer> getParkingInfoId() {
		return parkingInfoId;
	}

	public NumberPath<Integer> getStatus() {
		return status;
	}

}

