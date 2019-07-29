package com.dchip.cloudparking.web.serviceImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dchip.cloudparking.web.iRepository.ISessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.constant.BaseConstant.APPParkingUserType;
import com.dchip.cloudparking.web.iRepository.IAccountRepository;
import com.dchip.cloudparking.web.iRepository.IParkingUserRepository;
import com.dchip.cloudparking.web.iService.IParkingUserService;
import com.dchip.cloudparking.web.model.po.Account;
import com.dchip.cloudparking.web.model.po.ParkingUser;
import com.dchip.cloudparking.web.model.vo.ParkingUserVo;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.po.qpo.QParking;
import com.dchip.cloudparking.web.po.qpo.QParkingUser;
import com.dchip.cloudparking.web.utils.HashKit;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class ParkingUserServiceImp extends BaseService implements IParkingUserService {
	
	@Autowired
	private IParkingUserRepository parkingUserRepository;
	@Autowired
	private ISessionRepository sessionRepository;
	@Autowired
	private IAccountRepository accountRepository;

	public Object getParkingUserList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para) {
		List<Map<String, Object>> data = new ArrayList<>();
		QParkingUser qParkingUser = QParkingUser.parkingUser;
		QParking qParking = QParking.parking;
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = user.getUserName();
		ParkingUser userParkingMsg = parkingUserRepository.findParkingUserByUserName(userName);

//		if(userParkingMsg == null){
//			Map<String, Object> result = new HashMap<>();
//			result.put("content", data);// 添加主体数据
//			result.put("totalElements", 0);// 添加总条数
//			result.put("code", 0);
//			return result;
//		}
		
		/**
		 * 查询条件的谓语集合(where条件)
		 */
		List<Predicate> predicates = new ArrayList<>();
		if (!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				// 搜索参数
				if (map.get("searchUserName") != null) {
					// 用户账号
					predicates.add(qParkingUser.userName.like("%" + map.get("searchUserName").toString() + "%"));
				}
			}
		}
		predicates.add(qParkingUser.type.eq(APPParkingUserType.Business.getTypeValue())
				.or(qParkingUser.type.eq(APPParkingUserType.Security.getTypeValue())));
		if(user.getRoleType() == BaseConstant.SysRoleType.parkingType.getTypeValue()) {
			predicates.add(qParkingUser.parkingId.eq(userParkingMsg.getParkingId()));
		}else if(user.getRoleType() == BaseConstant.SysRoleType.companyType.getTypeValue()){
			predicates.add(qParkingUser.parkingId.in(jpaQueryFactory.select(qParking.id)
					.from(qParking).where(qParking.companyId.eq(user.getCompanyId()))));
		}
		// 动态排序,需要使用自定义的Sort类
		Sort sort = null;
		switch (sortName) {
		case "id":
			sort = new Sort(sortName, direction.toString(), qParkingUser);
			break;
		default:
			sort = new Sort(sortName, direction, qParkingUser);
			break;
		}
		
		//不显示软删除的数据
		predicates.add(qParkingUser.status.notIn(BaseConstant.SoftDelete.delete.getTypeValue()));

		// 查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qParkingUser.id, qParkingUser.realName, qParkingUser.userName, qParkingUser.password,qParkingUser.status,qParkingUser.createTime
						,qParkingUser.type,qParkingUser.parkingId, qParking.parkName)
				.from(qParkingUser)
				.leftJoin(qParking).on(qParking.id.eq(qParkingUser.parkingId))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.orderBy(qParkingUser.createTime.desc())
				.offset(pageNum * pageSize).limit(pageSize);

		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		// 将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", tuple.get(qParkingUser.id));
			map.put("realName", tuple.get(qParkingUser.realName));
			map.put("userName", tuple.get(qParkingUser.userName));
			map.put("password", tuple.get(qParkingUser.password));
			map.put("status", tuple.get(qParkingUser.status));
			map.put("createTime", tuple.get(qParkingUser.createTime));
			map.put("type", tuple.get(qParkingUser.type));
			map.put("parkingId", tuple.get(qParkingUser.parkingId));
			map.put("parkName", tuple.get(qParking.parkName));
			data.add(map);
		}

		// jqgrid数据拼装
		Map<String, Object> result = new HashMap<>();
		result.put("content", data);// 添加主体数据
		result.put("totalElements", queryResults.getTotal());// 添加总条数
		result.put("code", 0);
		return result;
	}
	
	@SuppressWarnings("null")
	@Override
	public RetKit save(ParkingUserVo parkingUserVo) {
		ParkingUser parkingUser = new ParkingUser();
		Account account = new Account();
/*		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = user.getUsername();*/
		try {
			if (parkingUserVo.getId() == 0) {
				parkingUser.setId(parkingUserVo.getId());
				parkingUser.setCreateTime(new Date());
				parkingUser.setStatus(BaseConstant.ParkingUserStatus.EnabledStatus.getTypeValue());
				parkingUser.setPassword(HashKit.md5(BaseConstant.AccountPwd.RESET.getPwd()));
				Optional<ParkingUser> parkingOptional = parkingUserRepository.findById(parkingUserVo.getId());
				if (parkingOptional.isPresent()) {
					parkingUser = parkingOptional.get();
				}
				
			} else {
				Integer userId = parkingUserVo.getId();
				parkingUser = parkingUserRepository.findById(userId).get();
			}
			parkingUser.setUserName(parkingUserVo.getUserName());
			parkingUser.setRealName(parkingUserVo.getRealName());
			parkingUser.setType(parkingUserVo.getType());
			parkingUser.setParkingId(parkingUserVo.getParkingId());
/*			ParkingUser userParkingSel = parkingUserRepository.findParkingUserByUserName(userName);
			if(userParkingSel != null) {
				parkingUser.setParkingId(userParkingSel.getParkingId());
			}*/
			parkingUserRepository.save(parkingUser);
			

			UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(parkingUserVo.getType() == BaseConstant.ParkingUserAccountType.securityType.getTypeValue() && parkingUserVo.getId() == 0) {//新增保安
				account.setCreateTime(new Date());
				account.setStatus(BaseConstant.ParkingUserStatus.EnabledStatus.getTypeValue());
				account.setPassword(HashKit.md5(BaseConstant.AccountPwd.RESET.getPwd()));
				account.setType(BaseConstant.SysRoleType.securityType.getTypeValue());
				account.setCompanyId(user.getCompanyId());
				account.setUserName(parkingUserVo.getUserName());
				accountRepository.save(account);
			}else if(parkingUserVo.getType() == BaseConstant.ParkingUserAccountType.businessmanType.getTypeValue() && parkingUserVo.getId() != 0){//编辑保安变商户
				String userName = parkingUserVo.getUserName();
				Account accountName =  accountRepository.findByUserName(userName);
				if(accountName != null) {
					accountName.setStatus(BaseConstant.SoftDelete.delete.getTypeValue());
				}
			}else if(parkingUserVo.getType() == BaseConstant.ParkingUserAccountType.securityType.getTypeValue() && parkingUserVo.getId() != 0) {//编辑商户变保安
				String userName = parkingUserVo.getUserName();
				Account accountName =  accountRepository.findByUserName(userName);
				if (accountName == null) {
					account.setCreateTime(new Date());
					account.setStatus(BaseConstant.ParkingUserStatus.EnabledStatus.getTypeValue());
					account.setPassword(HashKit.md5(BaseConstant.AccountPwd.RESET.getPwd()));
					account.setType(BaseConstant.SysRoleType.securityType.getTypeValue());
					account.setCompanyId(user.getCompanyId());
					account.setUserName(parkingUserVo.getUserName());
					accountRepository.save(account);
				}else {
					accountName.setStatus(BaseConstant.ParkingUserStatus.EnabledStatus.getTypeValue());
					accountName.setType(BaseConstant.SysRoleType.securityType.getTypeValue());
				}
				
			}
			
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail("保存失败");
		}
	}
	
	
	public RetKit del(String userId) {
		try {
			Optional<ParkingUser> parkingUserOptional = parkingUserRepository.findById(Integer.parseInt(userId));
			if (parkingUserOptional.isPresent()) {
				parkingUserRepository.deleteById(Integer.parseInt(userId));
				sessionRepository.delParkingUserSession(userId, BaseConstant.SessionUserType.ParkingUser.getTypeValue());
			}else {
				return RetKit.fail("该条数据不存在");
			}
			ParkingUser user = parkingUserOptional.get();
			String userName = user.getUserName();
			Account account = accountRepository.findByUserName(userName);
			if(account != null) {
				account.setStatus(BaseConstant.SoftDelete.delete.getTypeValue());
			}
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail();
		}
	}
	
	public RetKit reset(String userId) {
		ParkingUser parkingUser = new ParkingUser();
		try {
			Optional<ParkingUser> parkingUserOptional = parkingUserRepository.findById(Integer.parseInt(userId));
			if (parkingUserOptional.isPresent()) {
				parkingUser = parkingUserOptional.get();
				parkingUser.setPassword(HashKit.md5(BaseConstant.AccountPwd.RESET.getPwd()));;
				parkingUserRepository.save(parkingUser);
			}else {
				return RetKit.fail("该条数据不存在");
			}
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail();
		}
	}
	
	
	
	/**
	 * 检查角色名称是否重复
	 */
	public RetKit checkUserName(String userName) {
		try {
			Integer userNum = parkingUserRepository.getUserNumByRoleName(userName);
			if(userNum != 0) {
				return RetKit.fail("登录账号不能重复");
			}
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail("检查重名失败");
		}
	}

	@Override
	public Integer findParkingId(String userName) {
		QParkingUser qParkingUser = QParkingUser.parkingUser;
		Integer parkingId = this.jpaQueryFactory.select(qParkingUser.parkingId)
				.from(qParkingUser)
				.where(qParkingUser.userName.eq(userName)).fetchFirst();
		return parkingId;
	}

}
