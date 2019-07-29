package com.dchip.cloudparking.web.serviceImp;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.IAccountRepository;
import com.dchip.cloudparking.web.iRepository.IParkingUserRepository;
import com.dchip.cloudparking.web.iService.IAccountService;
import com.dchip.cloudparking.web.model.po.Account;
import com.dchip.cloudparking.web.model.po.ParkingUser;
import com.dchip.cloudparking.web.model.vo.AccountVo;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.po.qpo.*;
import com.dchip.cloudparking.web.utils.HashKit;
import com.dchip.cloudparking.web.utils.RetKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountServiceImp extends BaseService implements IAccountService {
	
	@Autowired
	private IAccountRepository accountRepository;
	@Autowired
	private IParkingUserRepository parkingUserRepository;
	
	@Override
	public Object getAccountList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para) {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<Map<String, Object>> listData = new ArrayList<>();
		
		QAccount qAccount = QAccount.account;
		QSysRole qSysRole = QSysRole.sysRole;
		QCompany qCompany = QCompany.company;
		QParking qParking = QParking.parking;
		QParkingUser qParkingUser = QParkingUser.parkingUser;
		
		List<Predicate> predicates = new ArrayList<>();

		if (!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				if (map.get("searchUserName") != null) {
					predicates.add(qAccount.userName.like("%" + map.get("searchUserName").toString() + "%"));
				}else if (map.get("searchManagerType") != null) {
					predicates.add(qAccount.type.eq(Integer.parseInt(map.get("searchManagerType").toString())));
				}
			}
		}
		if(user.getCompanyId() != null){
			predicates.add(qAccount.companyId.eq(user.getCompanyId()));
		}
		if (user.getRoleType().equals(BaseConstant.RoleType.sysRole.getTypeValue())) {
			predicates.add(qSysRole.type.ne(BaseConstant.RoleType.parkingRole.getTypeValue()));
		}

		//不显示软删除的数据
		predicates.add(qAccount.status.notIn(BaseConstant.SoftDelete.delete.getTypeValue()));
		
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.selectDistinct(qAccount.id, qAccount.userName, qAccount.status,qAccount.createTime,
						qCompany.id,qCompany.name, qAccount.type, qSysRole.name, qSysRole.id,qSysRole.type,
						qParkingUser.parkingId,qParkingUser.id,qParkingUser.realName)
				.from(qAccount)
				.leftJoin(qSysRole).on(qSysRole.id.eq(qAccount.roleId))
				.leftJoin(qCompany).on(qCompany.id.eq(qAccount.companyId))
				.leftJoin(qParking).on(qParking.companyId.eq(qCompany.id))
				.leftJoin(qParkingUser).on(qParkingUser.userName.eq(qAccount.userName))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.groupBy(qAccount.id)
				.orderBy(qAccount.createTime.desc())
				.offset(pageNum * pageSize).limit(pageSize);
		
		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("accountId", tuple.get(qAccount.id));
			map.put("userName", tuple.get(qAccount.userName));
			map.put("accountType", tuple.get(qAccount.type));
			map.put("accountStatus", tuple.get(qAccount.status));
			map.put("createTime", tuple.get(qAccount.createTime));
			map.put("sysRoleName", tuple.get(qSysRole.name));
			map.put("sysRoleId", tuple.get(qSysRole.id));
			map.put("sysRoleType", tuple.get(qSysRole.type));
			map.put("companyName", tuple.get(qCompany.name));
			map.put("companyId", tuple.get(qCompany.id));
			map.put("parkingId", tuple.get(qParkingUser.parkingId));
			map.put("parkingUserId", tuple.get(qParkingUser.id));
			map.put("realName", tuple.get(qParkingUser.realName));
			listData.add(map);
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("content", listData);
		result.put("totalElements", queryResults.getTotal());
		result.put("code", 0);
		
		return result;
	}

	/**
	 * 保存管理员
	 */
	@Override
	public RetKit save(AccountVo accountVo) {
		Account account = new Account();
		ParkingUser parkingUser = new ParkingUser();
		try {
			if (accountVo.getAccountId() != null) {//编辑管理员
				account = accountRepository.findById(accountVo.getAccountId()).get();
			} else {//添加管理员
				Account one = accountRepository.checkUserName(accountVo.getAccountName());
				if( one != null ){
					account = one;
				}
				account.setCreateTime(new Date());
				account.setStatus(BaseConstant.AccountStatus.EnabledStatus.getTypeValue());
				account.setPassword(HashKit.md5(BaseConstant.AccountPwd.RESET.getPwd()));
			}
			account.setUserName(accountVo.getAccountName());
			account.setRoleId(accountVo.getRoleId());
			account.setType(accountVo.getRoleType());
			if(accountVo.getRoleType()==(BaseConstant.SysRoleType.adminType.getTypeValue())) {//系统管理员
				account.setCompanyId(null);
			}else {
				account.setCompanyId(accountVo.getCompanyId());
			}
			account = accountRepository.save(account);
			
			if (account.getType() != 1) {//添加或编辑停车场管理员
				if (accountVo.getAccountId() == null) {//添加停车场用户 parking_user accountVo.getAccountName()
					ParkingUser pUser = parkingUserRepository.findParkingUser(accountVo.getAccountName());
					if (pUser != null) {
						parkingUser = pUser;
					}
					parkingUser.setCreateTime(new Date());
					parkingUser.setPassword(HashKit.md5(BaseConstant.AccountPwd.RESET.getPwd()));
					parkingUser.setStatus(BaseConstant.APPParkingUserStatus.EnabledStatus.getTypeValue());
					if(account.getType() == 2) {
						parkingUser.setType(BaseConstant.APPParkingUserType.companyUser.getTypeValue());//公司管理员
					}else if (account.getType() == 3) {
						parkingUser.setType(BaseConstant.APPParkingUserType.ParkingUser.getTypeValue());//停车场管理员
					}
				}else {//编辑停车场管理员
					Integer userId = accountVo.getParkingUserId();
					if (userId != null) {
						Optional<ParkingUser> parkingUserOptional = parkingUserRepository.findById(userId);
						if (parkingUserOptional.isPresent()) {
							parkingUser =  parkingUserOptional.get();
						}
					}else {//当用户不存在的时候，新建一个
						parkingUser.setCreateTime(new Date());
						parkingUser.setPassword(HashKit.md5(BaseConstant.AccountPwd.RESET.getPwd()));
						parkingUser.setStatus(BaseConstant.APPParkingUserStatus.EnabledStatus.getTypeValue());
						if(account.getType() == 2) {
							parkingUser.setType(BaseConstant.APPParkingUserType.companyUser.getTypeValue());//公司管理员
						}else if (account.getType() == 3) {
							parkingUser.setType(BaseConstant.APPParkingUserType.ParkingUser.getTypeValue());//停车场管理员
						}
					}
				}
				parkingUser.setRealName(accountVo.getRealName());
				parkingUser.setUserName(accountVo.getAccountName());
				parkingUser.setParkingId(accountVo.getParkingId());
				parkingUserRepository.save(parkingUser);
			}
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail("保存失败");
		}
	}
	
	public RetKit changeAccountStatus(Integer accountId, Integer accountStatus) {
		try {
			Account account = accountRepository.findById(accountId).get();
			if (BaseConstant.AccountStatus.EnabledStatus.getTypeValue().equals(accountStatus)) {
				account.setStatus(BaseConstant.AccountStatus.DisableStatus.getTypeValue());
			} else if (BaseConstant.AccountStatus.DisableStatus.getTypeValue().equals(accountStatus)) {
				account.setStatus(BaseConstant.AccountStatus.EnabledStatus.getTypeValue());
			} else {
				return RetKit.fail();
			}
			accountRepository.save(account);
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail();
		}
	}
	
	public RetKit del(Integer accountId) {
		try {
			Optional<Account> accountOptional = accountRepository.findById(accountId);
			if (accountOptional.isPresent()) {
				Account account = accountOptional.get();
				String useName = account.getUserName();
				ParkingUser parkingUserName = parkingUserRepository.findParkingUserByUserName(useName);
				if (parkingUserName != null) {
					parkingUserRepository.delete(parkingUserName);
				}
				accountRepository.delete(account);
				return RetKit.ok();
			}else {
				return RetKit.fail("该账号不存在");
			}
		} catch (Exception e) {
			return RetKit.fail();
		}
	}
	
	public RetKit reSetCode(Integer accountId) {
		try {
			Optional<Account> accountOptional = accountRepository.findById(accountId);
			if (accountOptional.isPresent()){
				Account account = accountOptional.get();
				account.setPassword(HashKit.md5(BaseConstant.AccountPwd.RESET.getPwd()));
				return RetKit.ok();
			}else {
				return RetKit.fail("该账号不存在");
			}
		}catch (Exception e){
			return RetKit.fail();
		}
	}
	
	/**
	 * 判断是否有这个用户名
	 */
	public Boolean hasLoginName(String LoginName) {
		Boolean loginFlag = true;
		if (accountRepository.getUserLoginNameNum(LoginName) == 0) {
			loginFlag = false;
		}
		return loginFlag;
	}
	
	/**
	 * 校验是否用户名密码合法
	 */
	public Boolean pwdIsCorrect(String loginName, String pwd) {
		Boolean loginFlag = true;
		String pwdmd = HashKit.md5(pwd);
		if (accountRepository.getUserLoginSuccessNum(loginName, pwdmd) == 0) {
			loginFlag = false;
		}
		return loginFlag;
	}
	
	/**
	 * 修改密码
	 */
	public RetKit updatePassWord(String sysOldPWInp, String sysNewPWInp) {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer sysId = user.getAccountId();  //管理员Id
		try {
			Account account = accountRepository.findById(sysId).get();
			String oldPWMd = HashKit.md5(sysOldPWInp);
			if(!oldPWMd.equals(account.getPassword())) {
				return RetKit.fail("原密码错误，修改失败");
			}
			String newPWMd = HashKit.md5(sysNewPWInp);
			account.setPassword(newPWMd);
			accountRepository.save(account);
			return RetKit.ok();
		} catch (Exception e) {
			// TODO: handle exception
			return RetKit.fail("修改失败");
		}
	}	
	
	/**
	 * 根据id返回登录用户信息
	 */
	public Account getAccountById(Integer aId) {
		return accountRepository.findById(aId).get();
	}

	/**
	 * 软删
	 * @param accountId
	 * @return
	 */
	@Override
	public RetKit softlyDel(Integer accountId,String userName,Integer accountType) {
		try{
			Account account = accountRepository.findById(accountId).get();
			ParkingUser parkingUser = parkingUserRepository.findByStatusAndUserName(userName);
			account.setStatus(BaseConstant.SoftDelete.delete.getTypeValue());
			accountRepository.save(account);
			if(accountType != 1) {
			parkingUser.setStatus(BaseConstant.SoftDelete.delete.getTypeValue());
			parkingUserRepository.save(parkingUser);
			}
			return RetKit.ok("删除管理员用户成功");
		}catch (Exception e){
			return RetKit.fail();
		}
	}

	public RetKit checkAccountName(String accountName) {
		try {
			Integer AccountName = accountRepository.getUserLoginNameNum(accountName);
			if(AccountName != 0) {
				return RetKit.fail("角色名称不能重复");
			}
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail("检查重名失败");
		}
	}

	@Override
	public RetKit checkParkingUserName(Integer roleType, String parkingUserName) {
//		Account account = accountRepository.checkUserName(parkingUserName);
		Account account = accountRepository.findByStatusAndUserName(parkingUserName);
		if (roleType == 1) {
			if (account != null) {
				return RetKit.fail("该账号已被使用");
			}else {
				return RetKit.ok();
			}
		}else if (roleType == 2) {
			if (account != null) {
				return RetKit.fail("该账号已被使用");
			}else {
				return RetKit.ok();
			}
		}
		return RetKit.fail("没有权限！");
	}

}
 