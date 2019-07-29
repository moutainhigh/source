package com.dchip.cloudparking.web.serviceImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.ISysRoleMenuRepository;
import com.dchip.cloudparking.web.iRepository.ISysRoleRepository;
import com.dchip.cloudparking.web.iService.ISysRoleService;
import com.dchip.cloudparking.web.model.po.SysRole;
import com.dchip.cloudparking.web.model.po.SysRoleMenu;
import com.dchip.cloudparking.web.po.qpo.QSysRole;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class SysRoleServiceImp extends BaseService implements ISysRoleService{

	@Autowired
	private ISysRoleRepository sysRoleRepository;
	
	@Autowired
	private ISysRoleMenuRepository sysRoleMenuRepository;
	
	@Override
	public Object getSysRoleList(Integer pageSize, Integer pageNum, String sortName, String direction,List<Map<String, Object>> para) {
		List<Map<String, Object>> listData = new ArrayList<>();
		
		QSysRole qSysRole = QSysRole.sysRole;//角色权限
		
		/**
		 * 查询条件的谓语集合(where条件)
		 */
		List<Predicate> predicates = new ArrayList<>();	
		
		if(!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				//搜索参数
				if(map.get("searchRole")!=null) {
					//角色名称
					predicates.add(qSysRole.name.like("%"+map.get("searchRole").toString()+"%"));
				}
			}
		}
		
		//动态排序,需要使用自定义的Sort类
				Sort sort = null;
				switch (sortName) {
				case "roleId":		
					sort = new Sort("roleId",direction.toString(),qSysRole);
					break;
				case "roleName":		
					sort = new Sort("roleName",direction.toString(),qSysRole);
					break;
				default:
					sort = new Sort(sortName,direction,qSysRole);
					break;
				}

		predicates.add(qSysRole.status.notIn(BaseConstant.SysRoleStatus.DisableStatus.getTypeValue()));

		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (user.getRoleType().equals(BaseConstant.RoleType.sysRole.getTypeValue())) {
			predicates.add(qSysRole.type.ne(BaseConstant.RoleType.parkingRole.getTypeValue()));
		}
		if(user.getCompanyId() != null){
			//停车场管理员
			predicates.add(qSysRole.type.eq(BaseConstant.RoleType.parkingRole.getTypeValue()));
			predicates.add(qSysRole.companyId.eq(user.getCompanyId()));
		}

		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.selectDistinct(qSysRole.id,qSysRole.name,qSysRole.remark,qSysRole.status,qSysRole.type,qSysRole.createTime)
				.from(qSysRole)
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.orderBy(sort.getOrderSpecifier())
				.groupBy(qSysRole.id)
				.offset(pageNum * pageSize).limit(pageSize);
		
		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("roleId", tuple.get(qSysRole.id));
			map.put("roleName", tuple.get(qSysRole.name));
			map.put("roleRemark", tuple.get(qSysRole.remark));
			map.put("roleStatus", tuple.get(qSysRole.status));
			map.put("roleType", tuple.get(qSysRole.type));
			map.put("createTime", tuple.get(qSysRole.createTime));
			listData.add(map);
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("content", listData);
		result.put("totalElements", queryResults.getTotal());
		result.put("code", 0);
		return result;
	}
	
	/**
	 * 保存角色
	 */
	public RetKit saveRole(Integer roleId, String roleName, String remark, Integer roleType, Integer[] menuIdArray) {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer companyId = user.getCompanyId();
		try {
			SysRole sysRole = new SysRole();
			if(roleId == 0) {
				sysRole.setName(roleName);
				sysRole.setType(roleType);
				sysRole.setRemark(remark);
				sysRole.setStatus(BaseConstant.SysRoleStatus.EnabledStatus.getTypeValue());
				Date nowTime = new Date();
				sysRole.setCreateTime(nowTime);
				sysRole.setCompanyId(companyId);
				sysRole = sysRoleRepository.save(sysRole);
			}else {
				sysRole = sysRoleRepository.findById(roleId).get();
				sysRole.setName(roleName);
				sysRole.setType(roleType);
				sysRole.setRemark(remark);
				sysRole = sysRoleRepository.save(sysRole);
				sysRoleMenuRepository.deleteSRMByRoleId(roleId);
			}
			for (int i = 0; i < menuIdArray.length; i++) {
				SysRoleMenu sysRoleMenu = new SysRoleMenu();
				sysRoleMenu.setMenuId(menuIdArray[i]);
				sysRoleMenu.setRoleId(sysRole.getId());
				sysRoleMenuRepository.save(sysRoleMenu);
			}
		} catch (Exception e) {
			RetKit.fail("保存失败");
		}
		return RetKit.ok();
	}
	
	/**
	 * 删除角色（软删除）
	 */
	public RetKit delRole(Integer roleId) {
		try {
			SysRole sysRole = sysRoleRepository.findById(roleId).get();
			sysRole.setStatus(BaseConstant.SysRoleStatus.DisableStatus.getTypeValue());
			sysRoleRepository.save(sysRole);
			return RetKit.ok("删除成功");
		} catch (Exception e) {
			return RetKit.fail("删除失败");
		}
	}
	
	/**
	 * 检查角色名称是否重复
	 */
	public RetKit checkRoleName(String roleName) {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			Integer roleNum = null;
			if(user.getCompanyId() == null){
				roleNum = sysRoleRepository.getRoleNumByRoleName(roleName);
			}else{
				roleNum = sysRoleRepository.getRoleNumByRoleName(roleName,user.getCompanyId());
			}
			if(roleNum != 0) {
				return RetKit.fail("角色名称不能重复");
			}
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail("检查重名失败");
		}
	}
	
	/**
	 * 根据角色类型获取角色列表
	 */
	public List<SysRole> getSysRoleByType(Integer type) {
		List<SysRole> sysRoles = new ArrayList<SysRole>();
		try {
			sysRoles = sysRoleRepository.getRoleListByType(type);
		} catch (Exception e) {
			
		}
		return sysRoles;
	}

	@Override
	public List<SysRole> getSysRoleByType(Integer typeValue, Integer companyId) {
		// TODO Auto-generated method stub
		List<SysRole> sysRoles = new ArrayList<SysRole>();
		try {
			sysRoles = sysRoleRepository.getRoleListByType(typeValue, companyId);
		} catch (Exception e) {
			
		}
		return sysRoles;
	}

}
