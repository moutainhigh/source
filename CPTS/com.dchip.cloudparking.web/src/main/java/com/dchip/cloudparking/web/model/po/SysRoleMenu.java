package com.dchip.cloudparking.web.model.po;
// Generated 2018-11-1 11:18:16 by Hibernate Tools 5.2.10.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysRoleMenu generated by hbm2java
 */
@Entity
@Table(name = "sys_role_menu", catalog = "cloud_parking")
public class SysRoleMenu implements java.io.Serializable {

	private Integer id;
	private int roleId;
	private Integer menuId;

	public SysRoleMenu() {
	}

	public SysRoleMenu(int roleId) {
		this.roleId = roleId;
	}

	public SysRoleMenu(int roleId, Integer menuId) {
		this.roleId = roleId;
		this.menuId = menuId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "role_id", nullable = false)
	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Column(name = "menu_id")
	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

}
