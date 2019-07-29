package com.dchip.cloudparking.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iService.IMenuService;
import com.dchip.cloudparking.web.iService.ISysRoleService;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;

@Controller
@RequestMapping("sysRole")
public class SysRoleController {
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private IMenuService menuService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Object sysMenuData = JSON.toJSON(menuService.getMenuTreeByType(BaseConstant.MenuType.sysMenu.getTypeValue()));
		Object parkingMenuData = JSON.toJSON(menuService.getMenuTreeByType(BaseConstant.MenuType.companyMenu.getTypeValue()));
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		request.setAttribute("sysMenuData", sysMenuData);
		request.setAttribute("parkingMenuData", parkingMenuData);

		if(user.getRoleType().equals(BaseConstant.MenuType.companyMenu.getTypeValue())){
			return "sysRole/companyRole";
		}
		return "sysRole/index";
	}
	
	@RequestMapping("/rendTable")
	@ResponseBody
	public Object rendTable(HttpServletRequest request) {
		// 分页条件
		Integer pageSize = Integer.parseInt(request.getParameter("limit"));
		Integer pageNum = Integer.parseInt(request.getParameter("page")) - 1;
		// 排序条件
		String sortName = request.getParameter("sortName");
		String direction = request.getParameter("direction");
		String searchRole = request.getParameter("searchRole");
		
		List<Map<String, Object>> para = new ArrayList<>();
		if(StrKit.notBlank(searchRole)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchRole", searchRole);
			para.add(map);
		}
		
		return JSON.toJSON(sysRoleService.getSysRoleList(pageSize, pageNum, sortName, direction, para));
		
	}
	
	/**
	 * 根据权限id获取菜单id
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMenuIdByRoleId")
	@ResponseBody
	public Object getMenuIdByRoleId(HttpServletRequest request) {
		Integer roleId = Integer.parseInt(request.getParameter("roleId"));
		return JSON.toJSON(menuService.getMenuIdByRoleId(roleId));
	}
	
	/**
	 * 保存角色信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveRole")
	@ResponseBody
	public RetKit saveRole(HttpServletRequest request) {
		String saveRoleId = request.getParameter("saveRoleId");
		String saveRoleName = request.getParameter("saveRoleName");
		String saveRemark = request.getParameter("saveRemark");
		String saveRoleType = request.getParameter("saveRoleType");
		String menuStr = request.getParameter("saveMenuIdList");
		String[] menuIdStringArray = menuStr.split(",");
		Integer[] menuIdArray = null;
		Integer roleId = 0;
		Integer roleType = 0;
		if(StrKit.notBlank(menuStr)) {
			menuIdArray = StringToIntegerArray(menuIdStringArray);
		}
		if(StrKit.notBlank(saveRoleId)) {
			roleId = Integer.parseInt(saveRoleId);
		}
		if(StrKit.notBlank(saveRoleType)) {
			roleType = Integer.parseInt(saveRoleType);
		}
		return sysRoleService.saveRole(roleId, saveRoleName, saveRemark, roleType, menuIdArray);
	}
	
	@RequestMapping("/addRole")
	@ResponseBody
	public RetKit addRole(HttpServletRequest request) {
		return saveRole(request);
	}
	
	@RequestMapping("/editRole")
	@ResponseBody
	public RetKit editRole(HttpServletRequest request) {
		return saveRole(request);
	}

	/**
	 * 删除角色（软删除）
	 * @param request
	 * @return
	 */
	@RequestMapping("/delRole")
	@ResponseBody
	public RetKit deleteRole(HttpServletRequest request) {
		
		String roleIdStr = request.getParameter("roleId");
		Integer roleId = 0;
		if(StrKit.notBlank(roleIdStr)) {
			roleId = Integer.parseInt(roleIdStr);
		}else {
			return RetKit.fail("删除失败");
		}
		return sysRoleService.delRole(roleId);
	}
	
	/**
	 * 检查角色名称
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkRoleName")
	@ResponseBody
	public RetKit checkRoleName(HttpServletRequest request) {
		String roleName = request.getParameter("roleName");
		return sysRoleService.checkRoleName(roleName);
	}
	
	/**
	 * 将字符串数组转为Integer[]数组
	 */
	public Integer[] StringToIntegerArray(String[] param) {
		List<Integer> LString = new ArrayList<Integer>();
		Integer[] result = null;
		for (int i = 0; i < param.length; i++) {
			LString.add(new Integer(param[i]));
		}
		int size = LString.size();
		result = (Integer[]) LString.toArray(new Integer[size]);
		return result;
	}
	
}
