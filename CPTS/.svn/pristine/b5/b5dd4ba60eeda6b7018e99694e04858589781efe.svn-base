package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.SysRole;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QSysRole is a Querydsl query type for SysRole
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysRole extends EntityPathBase<SysRole> {

    private static final long serialVersionUID = -1666806003L;

    public static final QSysRole sysRole = new QSysRole("sysRole");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> companyId = createNumber("companyId", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath remark = createString("remark");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);
    
    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public QSysRole(String variable) {
        super(SysRole.class, forVariable(variable));
    }

    public QSysRole(Path<? extends SysRole> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysRole(PathMetadata metadata) {
        super(SysRole.class, metadata);
    }

	public DateTimePath<java.util.Date> getCreateTime() {
		return createTime;
	}

	public NumberPath<Integer> getId() {
		return id;
	}

	public StringPath getName() {
		return name;
	}

	public StringPath getRemark() {
		return remark;
	}

	public NumberPath<Integer> getStatus() {
		return status;
	}

}

