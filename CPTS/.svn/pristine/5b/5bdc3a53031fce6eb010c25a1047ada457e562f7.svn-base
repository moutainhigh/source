package com.dchip.cloudparking.web.iRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.Menu;

@Repository
public interface IMenuRepository extends JpaRepository<Menu, Integer> {

	/**
	 * 根据角色id返回菜单列表
	 * @param roleId
	 * @return
	 */
	@Query(value="select m.* from menu m where m.pid is null and m.id in (select srm.menu_id from sys_role_menu srm where srm.role_id = :roleId) order by m.sequence", nativeQuery = true)
	List<Menu> getMenuListByRoleId(@Param("roleId") Integer roleId);
	
	/**
	 * 根据pid和角色Id返回菜单列表
	 * @param pid
	 * @param roleId
	 * @return
	 */
	@Query(value="select m.* from menu m where m.pid = :pid and m.id in (select srm.menu_id from sys_role_menu srm where srm.role_id = :roleId) order by m.sequence", nativeQuery = true)
	List<Menu> getMenuListByPidAndRoleId(@Param("pid") Integer pid, @Param("roleId") Integer roleId);
	
	/**
	 * 根据菜单类型获取菜单树
	 * @return
	 */
	@Query(value="select m.* from menu m where m.type= :menuType or m.type = 3 order by m.sequence", nativeQuery=true)
	List<Menu> getMenuTreeByType(@Param("menuType") Integer menuType);

	@Query(value =" SELECT * FROM menu where url like %:url% limit 1",nativeQuery = true)
	Menu findByUrl(@Param("url") String url);
	
	/**
	 * 获取保安的父菜单
	 * @param roleId
	 * @return
	 */
	@Query(value="select m.* from menu m where m.pid is null and m.type = 4 order by m.sequence", nativeQuery = true)
	List<Menu> getMenuListByStaff();
	
	/**
	 * 获取保安的子菜单
	 * @param pid
	 * @return
	 */
	@Query(value="select m.* from menu m where m.pid = :pid order by m.sequence", nativeQuery = true)
	List<Menu> getMenuListByStaffPid(@Param("pid") Integer pid);

}
