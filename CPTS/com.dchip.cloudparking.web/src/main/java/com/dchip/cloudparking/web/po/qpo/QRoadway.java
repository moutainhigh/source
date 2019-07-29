package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.Roadway;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QRoadway is a Querydsl query type for Roadway
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRoadway extends EntityPathBase<Roadway> {

    private static final long serialVersionUID = 1438286329L;

    public static final QRoadway roadway = new QRoadway("roadway");

    public final StringPath address = createString("address");

    public final StringPath cameraIp = createString("cameraIp");

    public final StringPath cameraMac = createString("cameraMac");

    public final NumberPath<Integer> cameraType = createNumber("cameraType", Integer.class);

    public final NumberPath<Integer> communication = createNumber("communication", Integer.class);

    public final NumberPath<Integer> gateNumber = createNumber("gateNumber", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> inOutMarker = createNumber("inOutMarker", Integer.class);

    public final StringPath ledIp = createString("ledIp");

    public final StringPath ledModel = createString("ledModel");

    public final NumberPath<Integer> ledScreenRowNum = createNumber("ledScreenRowNum", Integer.class);

    public final NumberPath<Integer> parkingId = createNumber("parkingId", Integer.class);

    public final StringPath roadName = createString("roadName");

    public final StringPath roadSign = createString("roadSign");

    public final NumberPath<Integer> showWay = createNumber("showWay", Integer.class);

    public final NumberPath<Integer> voiceLed = createNumber("voiceLed", Integer.class);

    public QRoadway(String variable) {
        super(Roadway.class, forVariable(variable));
    }

    public QRoadway(Path<? extends Roadway> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoadway(PathMetadata metadata) {
        super(Roadway.class, metadata);
    }

	public StringPath getAddress() {
		return address;
	}

	public StringPath getCameraIp() {
		return cameraIp;
	}

	public StringPath getCameraMac() {
		return cameraMac;
	}

	public NumberPath<Integer> getCameraType() {
		return cameraType;
	}

	public NumberPath<Integer> getCommunication() {
		return communication;
	}

	public NumberPath<Integer> getGateNumber() {
		return gateNumber;
	}

	public NumberPath<Integer> getId() {
		return id;
	}

	public NumberPath<Integer> getInOutMarker() {
		return inOutMarker;
	}

	public StringPath getLedIp() {
		return ledIp;
	}

	public StringPath getLedModel() {
		return ledModel;
	}

	public NumberPath<Integer> getLedScreenRowNum() {
		return ledScreenRowNum;
	}

	public NumberPath<Integer> getParkingId() {
		return parkingId;
	}

	public StringPath getRoadName() {
		return roadName;
	}

	public StringPath getRoadSign() {
		return roadSign;
	}

	public NumberPath<Integer> getShowWay() {
		return showWay;
	}

	public NumberPath<Integer> getVoiceLed() {
		return voiceLed;
	}

}

