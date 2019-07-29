package com.dchip.cloudparking.api.model.qpo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.dchip.cloudparking.api.model.po.Help;
import com.querydsl.core.types.Path;


/**
 * QHelp is a Querydsl query type for Help
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QHelp extends EntityPathBase<Help> {

    private static final long serialVersionUID = 1665336663L;

    public static final QHelp help = new QHelp("help");

    public final StringPath content = createString("content");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> power = createNumber("power", Integer.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public final DateTimePath<java.util.Date> uploadTime = createDateTime("uploadTime", java.util.Date.class);

    public QHelp(String variable) {
        super(Help.class, forVariable(variable));
    }

    public QHelp(Path<? extends Help> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHelp(PathMetadata metadata) {
        super(Help.class, metadata);
    }

	public NumberPath<Integer> getPower() {
		return power;
	}
}

