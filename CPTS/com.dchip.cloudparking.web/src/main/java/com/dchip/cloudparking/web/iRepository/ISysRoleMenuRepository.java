package com.dchip.cloudparking.web.iRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dchip.cloudparking.web.model.po.SysRoleMenu;

@Repository
public interface ISysRoleMenuRepository extends JpaRepository<SysRoleMenu, Integer>  {

	/**
	 * 根据权限id获取菜单id
	 * @param roleId
	 * @return
	 */
	@Query(value="select srm.* from sys_role_menu srm where srm.role_id=:roleId",nativeQuery=true)
	List<SysRoleMenu> getMenuIdByRoleId(@Param("roleId") Integer roleId);
	
	/**
	 * 删除关联
	 * @param roleId
	 */
	@Transactional
	@Modifying
	@Query(value="delete from sys_role_menu where role_id = :roleId", nativeQuery=true)
	void deleteSRMByRoleId(@Param("roleId") Integer roleId);
}
