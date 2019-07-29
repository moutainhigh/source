package com.dchip.cloudparking.wechat.model.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.wechat.model.po.ParkingRuleRelation;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;


/**
 * QParkingRuleRelation is a Querydsl query type for ParkingRuleRelation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QParkingRuleRelation extends EntityPathBase<ParkingRuleRelation> {

    private static final long serialVersionUID = 2058061850L;

    public static final QParkingRuleRelation parkingRuleRelation = new QParkingRuleRelation("parkingRuleRelation");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> parkingId = createNumber("parkingId", Integer.class);

    public final NumberPath<Integer> parkingRuleId = createNumber("parkingRuleId", Integer.class);

    public QParkingRuleRelation(String variable) {
        super(ParkingRuleRelation.class, forVariable(variable));
    }

    public QParkingRuleRelation(Path<? extends ParkingRuleRelation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParkingRuleRelation(PathMetadata metadata) {
        super(ParkingRuleRelation.class, metadata);
    }

}

