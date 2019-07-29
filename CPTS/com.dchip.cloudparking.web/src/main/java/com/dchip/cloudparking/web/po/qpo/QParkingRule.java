package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.ParkingRule;
import com.querydsl.core.types.Path;


/**
 * QParkingRule is a Querydsl query type for ParkingRule
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QParkingRule extends EntityPathBase<ParkingRule> {

    private static final long serialVersionUID = 893820676L;

    public static final QParkingRule parkingRule = new QParkingRule("parkingRule");

    public final NumberPath<Integer> endDay = createNumber("endDay", Integer.class);

    public final StringPath endTime = createString("endTime");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> inRule = createNumber("inRule", Integer.class);

    public final NumberPath<Integer> ruleType = createNumber("ruleType", Integer.class);

    public final NumberPath<Integer> startDay = createNumber("startDay", Integer.class);

    public final StringPath startTime = createString("startTime");

    public QParkingRule(String variable) {
        super(ParkingRule.class, forVariable(variable));
    }

    public QParkingRule(Path<? extends ParkingRule> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParkingRule(PathMetadata metadata) {
        super(ParkingRule.class, metadata);
    }

	public NumberPath<Integer> getEndDay() {
		return endDay;
	}

	public StringPath getEndTime() {
		return endTime;
	}

	public NumberPath<Integer> getId() {
		return id;
	}

	public NumberPath<Integer> getInRule() {
		return inRule;
	}

	public NumberPath<Integer> getRuleType() {
		return ruleType;
	}

	public NumberPath<Integer> getStartDay() {
		return startDay;
	}

	public StringPath getStartTime() {
		return startTime;
	}

}

