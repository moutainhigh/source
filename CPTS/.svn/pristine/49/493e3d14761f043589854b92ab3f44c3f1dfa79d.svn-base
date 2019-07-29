package com.dchip.cloudparking.api.model.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.api.model.po.Card;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QCard is a Querydsl query type for Card
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCard extends EntityPathBase<Card> {

    private static final long serialVersionUID = 1665184038L;

    public static final QCard card = new QCard("card");

    public final StringPath cardCode = createString("cardCode");

    public final StringPath carOwnerName = createString("carOwnerName");

    public final DateTimePath<java.util.Date> expiryTime = createDateTime("expiryTime", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> parkingId = createNumber("parkingId", Integer.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public QCard(String variable) {
        super(Card.class, forVariable(variable));
    }

    public QCard(Path<? extends Card> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCard(PathMetadata metadata) {
        super(Card.class, metadata);
    }

}

