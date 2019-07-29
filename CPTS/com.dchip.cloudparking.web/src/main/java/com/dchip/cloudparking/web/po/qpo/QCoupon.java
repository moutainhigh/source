package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.Coupon;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import java.util.Date;


/**
 * QCoupon is a Querydsl query type for Coupon
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCoupon extends EntityPathBase<Coupon> {

    private static final long serialVersionUID = -1767907140L;

    public static final QCoupon coupon = new QCoupon("coupon");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);
    public final NumberPath<Integer> count = createNumber("count", Integer.class);
    public final NumberPath<Integer> endNum = createNumber("endNum", Integer.class);
    public final NumberPath<Integer> endType = createNumber("endType", Integer.class);
    public final DateTimePath endTime = createDateTime("endTime", Date.class);
    public final DateTimePath createTime = createDateTime("createTime", Date.class);
    public final StringPath remark = createString("remark");
    public final StringPath memberIds = createString("memberIds");
    public final NumberPath<Integer> status = createNumber("status", Integer.class);
    public final NumberPath<Integer> deductionType = createNumber("deductionType", Integer.class);
    public final NumberPath<Integer> partDeduction = createNumber("partDeduction", Integer.class);

	public static QCoupon getCoupon() {
		return coupon;
	}

	public NumberPath<Integer> getId() {
		return id;
	}

	public NumberPath<Integer> getCount() {
		return count;
	}

	public StringPath getRemark() {
		return remark;
	}

	public NumberPath<Integer> getStatus() {
		return status;
	}

	public QCoupon(String variable) {
        super(Coupon.class, forVariable(variable));
    }
    public QCoupon(Path<? extends Coupon> path) {
        super(path.getType(), path.getMetadata());
    }
    public QCoupon(PathMetadata metadata) {
        super(Coupon.class, metadata);
    }
	public NumberPath<Integer> getEndNum() {
		return endNum;
	}
	public NumberPath<Integer> getEndType() {
		return endType;
	}
	public DateTimePath getEndTime() {
		return endTime;
	}
	public DateTimePath getCreateTime() {
		return createTime;
	}
	public StringPath getMemberIds() {
		return memberIds;
	}
	public NumberPath<Integer> getDeductionType() {
		return deductionType;
	}
	public NumberPath<Integer> getPartDeduction() {
		return partDeduction;
	}

}

