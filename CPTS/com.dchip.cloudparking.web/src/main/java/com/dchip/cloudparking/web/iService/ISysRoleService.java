package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.model.po.SysRole;
import com.dchip.cloudparking.web.utils.RetKit;

public interface ISysRoleService {

	Object getSysRoleList(Integer pageSize, Integer pageNum, String sortName, String direction,List<Map<String, Object>> para);
	
	/**
	 * 保存角色
	 * @param roleId
	 * @param roleName
	 * @param remark
	 * @param roleType
	 * @param menuIdArray
	 * @return
	 */
	RetKit saveRole(Integer roleId, String roleName, String remark, Integer roleType, Integer[] menuIdArray);
	
	/**
	 * 删除角色（软删除）
	 * @param roleId
	 * @return
	 */
	RetKit delRole(Integer roleId);
	
	/**
	 * 检查角色名称是否重复
	 * @param roleName
	 * @return
	 */
	RetKit checkRoleName(String roleName);
	
	/**
	 * 根据角色类型获取角色列表
	 * @param type
	 * @return
	 */
	List<SysRole> getSysRoleByType(Integer type);

	List<SysRole> getSysRoleByType(Integer typeValue, Integer companyId);

}
