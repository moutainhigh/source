package com.dchip.cloudparking.api.model.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.api.model.po.UserThrid;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QUserThrid is a Querydsl query type for UserThrid
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserThrid extends EntityPathBase<UserThrid> {

    private static final long serialVersionUID = 386308280L;

    public static final QUserThrid userThrid = new QUserThrid("userThrid");

    public final NumberPath<Integer> flag = createNumber("flag", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath openId = createString("openId");

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QUserThrid(String variable) {
        super(UserThrid.class, forVariable(variable));
    }

    public QUserThrid(Path<? extends UserThrid> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserThrid(PathMetadata metadata) {
        super(UserThrid.class, metadata);
    }

}

