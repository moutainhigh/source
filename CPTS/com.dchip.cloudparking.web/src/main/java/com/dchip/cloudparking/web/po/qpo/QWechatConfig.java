package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.WechatConfig;
import com.querydsl.core.types.Path;


/**
 * QWechatConfig is a Querydsl query type for WechatConfig
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWechatConfig extends EntityPathBase<WechatConfig> {

    private static final long serialVersionUID = 833177118L;

    public static final QWechatConfig wechatConfig = new QWechatConfig("wechatConfig");

    public final StringPath accessToken = createString("accessToken");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath jsapiTicket = createString("jsapiTicket");

    public final StringPath timeStamp = createString("timeStamp");

    public QWechatConfig(String variable) {
        super(WechatConfig.class, forVariable(variable));
    }

    public QWechatConfig(Path<? extends WechatConfig> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWechatConfig(PathMetadata metadata) {
        super(WechatConfig.class, metadata);
    }

	public StringPath getAccessToken() {
		return accessToken;
	}

	public NumberPath<Integer> getId() {
		return id;
	}

	public StringPath getJsapiTicket() {
		return jsapiTicket;
	}

	public StringPath getTimeStamp() {
		return timeStamp;
	}

}

