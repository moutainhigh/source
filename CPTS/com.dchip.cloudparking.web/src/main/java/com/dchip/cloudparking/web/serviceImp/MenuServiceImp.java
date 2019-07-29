package com.dchip.cloudparking.web.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.iRepository.IMenuRepository;
import com.dchip.cloudparking.web.iRepository.ISysRoleMenuRepository;
import com.dchip.cloudparking.web.iService.IMenuService;
import com.dchip.cloudparking.web.model.po.Menu;
import com.dchip.cloudparking.web.model.po.SysRoleMenu;
import com.dchip.cloudparking.web.model.vo.MenuTreeVo;
import com.dchip.cloudparking.web.model.vo.MenuVo;
import com.dchip.cloudparking.web.model.vo.SubMenuVo;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;

@Service
public class MenuServiceImp implements IMenuService {

	@Autowired
	IMenuRepository menuRepository;
	
	@Autowired
	ISysRoleMenuRepository sysRoleMenuRepository;
	
	/**
	 * 获取用户菜单
	 */
	public List<MenuVo> getMenuDetails() {
		
		List<MenuVo> menuVos = new ArrayList<MenuVo>();
		
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer roleId = user.getRoleId(); //获取登录用户权限id
		List<Menu> menuList;
		if(roleId == null) {//显示保安菜单
			menuList = menuRepository.getMenuListByStaff();
		}else {
			menuList = menuRepository.getMenuListByRoleId(roleId);
		}
		
		for (Menu menu : menuList) {
			MenuVo menuVo = new MenuVo();
			menuVo.setId(menu.getId());
			menuVo.setName(menu.getMenuName());
			menuVo.setHref(menu.getUrl());
			menuVo.setIconcss(menu.getMenuImgUrl());
			menuVo.setMenuCss(menu.getMenuCss());
			//子菜单
			List<SubMenuVo> childrens = new ArrayList< SubMenuVo>();
			List<Menu> subList;
			if(roleId == null) {
				subList = menuRepository.getMenuListByStaffPid(menu.getId());
			}else {
				subList = menuRepository.getMenuListByPidAndRoleId(menu.getId(), roleId);
			}
			for (Menu subMenu : subList) {
				SubMenuVo childrenMenu = new SubMenuVo();
				childrenMenu.setId(subMenu.getId());
				childrenMenu.setHref(subMenu.getUrl());
				childrenMenu.setName(subMenu.getMenuName());
				childrenMenu.setIconcss(subMenu.getMenuImgUrl());
				childrenMenu.setMenuCss(subMenu.getMenuCss());
				childrens.add(childrenMenu);
			}
			menuVo.setChildren(childrens);
			menuVos.add(menuVo);
		}		
		return menuVos;
	}
	
	/**
	 * 根据菜单类型获取菜单树
	 */
	public List<MenuTreeVo> getMenuTreeByType(Integer menuType){
		List<MenuTreeVo> menuTreeVos = new ArrayList<MenuTreeVo>();
		List<Menu> menuList = menuRepository.getMenuTreeByType(menuType);
		Integer pidNullFlag = 0; //没有pid就要赋值为0
		for (Menu menu : menuList) {
			MenuTreeVo menuTreeVo = new MenuTreeVo();
			menuTreeVo.setId(menu.getId());
			menuTreeVo.setName(menu.getMenuName());
			if(menu.getPid() == null) {
				menuTreeVo.setpId(pidNullFlag);
			}else {
				menuTreeVo.setpId(menu.getPid());
			}
			menuTreeVos.add(menuTreeVo);
		}
		return menuTreeVos;
	}
	
	/**
	 * 根据权限id获取菜单id
	 */
	public List<SysRoleMenu> getMenuIdByRoleId(Integer roleId){
		return sysRoleMenuRepository.getMenuIdByRoleId(roleId);
	}
}
