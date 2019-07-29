package com.dchip.cloudparking.api.model.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.api.model.po.Appointment;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QAppointment is a Querydsl query type for Appointment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAppointment extends EntityPathBase<Appointment> {

    private static final long serialVersionUID = 2039759049L;

    public static final QAppointment appointment = new QAppointment("appointment");

    public final DateTimePath<java.util.Date> appointStartTime = createDateTime("appointStartTime", java.util.Date.class);

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath licensePlate = createString("licensePlate");

    public final NumberPath<Integer> parkingId = createNumber("parkingId", Integer.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QAppointment(String variable) {
        super(Appointment.class, forVariable(variable));
    }

    public QAppointment(Path<? extends Appointment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAppointment(PathMetadata metadata) {
        super(Appointment.class, metadata);
    }

}

