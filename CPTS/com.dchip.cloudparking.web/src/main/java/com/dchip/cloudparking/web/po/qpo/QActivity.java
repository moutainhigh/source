package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.Activity;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import java.util.Date;


/**
 * QActivity is a Querydsl query type for Activity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QActivity extends EntityPathBase<Activity> {

    private static final long serialVersionUID = 562638373L;

    public static final QActivity activity = new QActivity("activity");

    public final NumberPath<Integer> couponId = createNumber("couponId", Integer.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final DateTimePath<java.util.Date> effectiveTime = createDateTime("effectiveTime", java.util.Date.class);

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Integer> effectiveType = createNumber("effectiveType", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath remark = createString("remark");

    public final NumberPath<Integer> couponCount = createNumber("couponCount", Integer.class);

    public QActivity(String variable) {
        super(Activity.class, forVariable(variable));
    }

    public QActivity(Path<? extends Activity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActivity(PathMetadata metadata) {
        super(Activity.class, metadata);
    }

    public static QActivity getActivity() {
        return activity;
    }

    public NumberPath<Integer> getCouponId() {
        return couponId;
    }

    public NumberPath<Integer> getStatus() {
        return status;
    }

    public DateTimePath<Date> getEffectiveTime() {
        return effectiveTime;
    }

    public DateTimePath<Date> getCreateTime() {
        return createTime;
    }

    public NumberPath<Integer> getEffectiveType() {
        return effectiveType;
    }

    public NumberPath<Integer> getId() {
        return id;
    }

    public StringPath getRemark() {
        return remark;
    }

    public NumberPath<Integer> getCouponCount() {
        return couponCount;
    }
}

