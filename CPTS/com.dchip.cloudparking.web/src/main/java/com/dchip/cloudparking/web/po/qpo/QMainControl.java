package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.MainControl;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QMainControl is a Querydsl query type for MainControl
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMainControl extends EntityPathBase<MainControl> {

    private static final long serialVersionUID = -713708914L;

    public static final QMainControl mainControl = new QMainControl("mainControl");

    public final NumberPath<Integer> areaId = createNumber("areaId", Integer.class);

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath mac = createString("mac");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> networkType = createNumber("networkType", Integer.class);

    public final NumberPath<Integer> parkingId = createNumber("parkingId", Integer.class);

    public final StringPath remark = createString("remark");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);
    
    public final StringPath actTo = createString("actTo");

    public QMainControl(String variable) {
        super(MainControl.class, forVariable(variable));
    }

    public QMainControl(Path<? extends MainControl> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMainControl(PathMetadata metadata) {
        super(MainControl.class, metadata);
    }

	public NumberPath<Integer> getAreaId() {
		return areaId;
	}

	public DateTimePath<java.util.Date> getCreateTime() {
		return createTime;
	}

	public NumberPath<Integer> getId() {
		return id;
	}

	public StringPath getMac() {
		return mac;
	}

	public StringPath getName() {
		return name;
	}

	public NumberPath<Integer> getNetworkType() {
		return networkType;
	}

	public NumberPath<Integer> getParkingId() {
		return parkingId;
	}

	public StringPath getRemark() {
		return remark;
	}

	public NumberPath<Integer> getStatus() {
		return status;
	}

	public StringPath getActTo() {
		return actTo;
	}

}

