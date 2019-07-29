package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.LicensePlateName;
import com.querydsl.core.types.Path;


/**
 * QLicensePlateName is a Querydsl query type for LicensePlateName
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLicensePlateName extends EntityPathBase<LicensePlateName> {

    private static final long serialVersionUID = 632079696L;

    public static final QLicensePlateName licensePlateName1 = new QLicensePlateName("licensePlateName1");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath licensePlateName = createString("licensePlateName");

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public final NumberPath<Integer> typeNumber = createNumber("typeNumber", Integer.class);

    public QLicensePlateName(String variable) {
        super(LicensePlateName.class, forVariable(variable));
    }

    public QLicensePlateName(Path<? extends LicensePlateName> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLicensePlateName(PathMetadata metadata) {
        super(LicensePlateName.class, metadata);
    }

	public NumberPath<Integer> getId() {
		return id;
	}

	public StringPath getLicensePlateName() {
		return licensePlateName;
	}

	public NumberPath<Integer> getTypeNumber() {
		return typeNumber;
	}

}

