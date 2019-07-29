package com.dchip.cloudparking.api.model.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.api.model.po.FreeModel;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QFreeModel is a Querydsl query type for FreeModel
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFreeModel extends EntityPathBase<FreeModel> {

    private static final long serialVersionUID = -392937529L;

    public static final QFreeModel freeModel = new QFreeModel("freeModel");

    public final StringPath freeModelName = createString("freeModelName");

    public final NumberPath<Integer> freeTimeLength = createNumber("freeTimeLength", Integer.class);

    public final NumberPath<java.math.BigDecimal> hourCost = createNumber("hourCost", java.math.BigDecimal.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<java.math.BigDecimal> mostCost = createNumber("mostCost", java.math.BigDecimal.class);

    public QFreeModel(String variable) {
        super(FreeModel.class, forVariable(variable));
    }

    public QFreeModel(Path<? extends FreeModel> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFreeModel(PathMetadata metadata) {
        super(FreeModel.class, metadata);
    }

}

