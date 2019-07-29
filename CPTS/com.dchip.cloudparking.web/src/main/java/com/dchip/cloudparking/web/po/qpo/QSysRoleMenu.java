package com.dchip.cloudparking.web.po.qpo;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.dchip.cloudparking.web.model.po.SysRoleMenu;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;


/**
 * QSysRoleMenu is a Querydsl query type for SysRoleMenu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysRoleMenu extends EntityPathBase<SysRoleMenu> {

    private static final long serialVersionUID = -1180513780L;

    public static final QSysRoleMenu sysRoleMenu = new QSysRoleMenu("sysRoleMenu");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> menuId = createNumber("menuId", Integer.class);

    public final NumberPath<Integer> roleId = createNumber("roleId", Integer.class);

    public QSysRoleMenu(String variable) {
        super(SysRoleMenu.class, forVariable(variable));
    }

    public QSysRoleMenu(Path<? extends SysRoleMenu> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysRoleMenu(PathMetadata metadata) {
        super(SysRoleMenu.class, metadata);
    }

	public NumberPath<Integer> getId() {
		return id;
	}

	public NumberPath<Integer> getMenuId() {
		return menuId;
	}

	public NumberPath<Integer> getRoleId() {
		return roleId;
	}

}

