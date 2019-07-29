package com.dchip.cloudparking.api.model.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.api.model.po.UserMessage;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QUserMessage is a Querydsl query type for UserMessage
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserMessage extends EntityPathBase<UserMessage> {

    private static final long serialVersionUID = -127154458L;

    public static final QUserMessage userMessage = new QUserMessage("userMessage");

    public final StringPath hasRead = createString("hasRead");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QUserMessage(String variable) {
        super(UserMessage.class, forVariable(variable));
    }

    public QUserMessage(Path<? extends UserMessage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserMessage(PathMetadata metadata) {
        super(UserMessage.class, metadata);
    }

}

