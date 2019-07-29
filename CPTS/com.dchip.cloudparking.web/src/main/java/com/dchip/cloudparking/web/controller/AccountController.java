package com.dchip.cloudparking.web.controller;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.IParkingRepository;
import com.dchip.cloudparking.web.iService.IAccountService;
import com.dchip.cloudparking.web.iService.ICompanyManageService;
import com.dchip.cloudparking.web.iService.IMenuService;
import com.dchip.cloudparking.web.iService.ISysRoleService;
import com.dchip.cloudparking.web.model.po.Company;
import com.dchip.cloudparking.web.model.po.Parking;
import com.dchip.cloudparking.web.model.po.SysRole;
import com.dchip.cloudparking.web.model.vo.AccountVo;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private IAccountService accountService;
	@Autowired
	private ICompanyManageService companyManageService;
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private IParkingRepository parkingRepository;
	@Autowired
	private IMenuService menuService;
	
	/**
	 * 显示管理员列表
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<SysRole> adminRoleList = sysRoleService.getSysRoleByType(BaseConstant.SysRoleType.adminType.getTypeValue());
		List<SysRole> companyRoleList = sysRoleService.getSysRoleByType(BaseConstant.SysRoleType.companyType.getTypeValue());
		List<SysRole> parkingRoleList = sysRoleService.getSysRoleByType(BaseConstant.SysRoleType.parkingType.getTypeValue());
		List<Company> companyList = companyManageService.findAllCompany();
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		request.setAttribute("adminRoleList", adminRoleList);
		request.setAttribute("companyRoleList", companyRoleList);
		request.setAttribute("parkingRoleList", parkingRoleList);
		request.setAttribute("companyList", companyList);

		if(user.getCompanyId() != null){
			parkingRoleList = sysRoleService.getSysRoleByType(BaseConstant.SysRoleType.parkingType.getTypeValue(),user.getCompanyId());
			request.setAttribute("parkingRoleList", parkingRoleList);
			return "account/company";
		}
		return "account/index";
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
		
		// 动态搜索参数
		String searchUserName = request.getParameter("searchUserName");
		String searchManagerType = request.getParameter("searchManagerType");

		List<Map<String, Object>> para = new ArrayList<>();
		
		if (StrKit.notBlank(searchUserName)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchUserName", searchUserName);
			para.add(map);
		}
		if (StrKit.notBlank(searchManagerType)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchManagerType", searchManagerType);
			para.add(map);
		}

		return JSON.toJSON(accountService.getAccountList(pageSize, pageNum, sortName, direction, para));
	}
	
	@RequestMapping("/addAccount")
	@ResponseBody
	public RetKit addAccount(AccountVo accountVo) {
		return accountService.save(accountVo);
	}
	
	@RequestMapping("/editAccount")
	@ResponseBody
	public RetKit editAccount(AccountVo accountVo) {
		return accountService.save(accountVo);
	}

	@RequestMapping("/changeAccountStatus")
	@ResponseBody
	public RetKit changeAccountStatus(HttpServletRequest request) {
		Integer accountId = Integer.parseInt(request.getParameter("accountId"));
		Integer accountStatus = Integer.parseInt(request.getParameter("accountStatus"));
		return accountService.changeAccountStatus(accountId, accountStatus);
	}

	@RequestMapping("/del")
	@ResponseBody
	public RetKit delete(HttpServletRequest request) {
		Integer accountId = Integer.parseInt(request.getParameter("accountId"));
		String userName = request.getParameter("userName");
		Integer accountType = Integer.parseInt(request.getParameter("accountType"));
		return accountService.softlyDel(accountId,userName,accountType);//软删
//		return accountService.del(accountId);//硬删
	}
	
	@RequestMapping("/reset")
	@ResponseBody
	public RetKit reset(HttpServletRequest request) {
		Integer accountId = Integer.parseInt(request.getParameter("accountId"));
		if(accountId == 0) {
			return RetKit.fail("该用户不存在");
		}
		return accountService.reSetCode(accountId);
	}
	
	/**
	 * 检查角色名称
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkAccountName")
	@ResponseBody
	public RetKit checkAccountName(HttpServletRequest request) {
		String accountName = request.getParameter("accountName");
		return accountService.checkAccountName(accountName);
	}
	
	/**
	 * 检查停车场管理员账号
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkParkingUserName")
	@ResponseBody
	public RetKit checkParkingUserName(HttpServletRequest request) {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer roleType = user.getRoleType();
		String parkingUserName = request.getParameter("parkingUserName");
		return accountService.checkParkingUserName(roleType,parkingUserName);
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatePassWord")
	@ResponseBody
	public RetKit updatePassWord(HttpServletRequest request) {
		//Integer sysId = Integer.parseInt(request.getParameter("sysId"));
		String sysOldPWInp = request.getParameter("sysOldPWInp");
		String sysNewPWInp = request.getParameter("sysNewPWInp");
		return accountService.updatePassWord(sysOldPWInp, sysNewPWInp);
	}
	
	@RequestMapping("/getParking")
	@ResponseBody
	public RetKit getParking(HttpServletRequest request) {
		String companyId = request.getParameter("companyId");
		if (StrKit.isBlank(companyId)) {
			return RetKit.fail("companyId不能为空！");
		}else {
			List<Parking> parkingList = parkingRepository.findAllByCompanyId(Integer.parseInt(companyId));
			return RetKit.okData(parkingList);
		}
	}

}
