package com.dchip.cloudparking.web.iRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.SysRole;

@Repository
public interface ISysRoleRepository extends JpaRepository<SysRole, Integer> {
	
	/**
	 * 根据角色名称返回角色数量（可判断重名）
	 * @param roleName
	 * @return
	 */
	@Query(value="select count(*) from sys_role sr where sr.name = :roleName and sr.status = 1", nativeQuery = true)
	Integer getRoleNumByRoleName(@Param("roleName") String roleName);

	/**
	 * 根据角色名称返回角色数量（可判断重名）
	 * @param roleName
	 * @return
	 */
	@Query(value="select count(*) from sys_role sr where sr.name = :roleName and sr.company_id=:companyId and sr.status = 1", nativeQuery = true)
	Integer getRoleNumByRoleName(@Param("roleName") String roleName,@Param("companyId")Integer companyId);
	
	/**
	 * 根据角色类型返回未删除的角色列
	 * @param type
	 * @return
	 */
	@Query(value="select sr.* from sys_role sr where sr.type =:type and sr.status = 1", nativeQuery=true)
	List<SysRole> getRoleListByType(@Param("type") Integer type);

	@Query(value="select sr.* from sys_role sr where sr.type =:typeValue and sr.company_id =:companyId and sr.status = 1", nativeQuery=true)
	List<SysRole> getRoleListByType(@Param("typeValue")Integer typeValue,@Param("companyId") Integer companyId);
	
}
