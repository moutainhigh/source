package com.dchip.cloudparking.web.iService;

import java.util.List;

import com.dchip.cloudparking.web.model.po.SysRoleMenu;
import com.dchip.cloudparking.web.model.vo.MenuTreeVo;
import com.dchip.cloudparking.web.model.vo.MenuVo;

public interface IMenuService {
	
	/**
	 * 获取用户菜单
	 * @return
	 */
	List<MenuVo> getMenuDetails();
	
	/**
	 * 根据菜单类型获取菜单树
	 * @return
	 */
	List<MenuTreeVo> getMenuTreeByType(Integer MenuType);
	
	/**
	 * 根据权限id获取菜单id
	 * @param roleId
	 * @return
	 */
	List<SysRoleMenu> getMenuIdByRoleId(Integer roleId);
}
