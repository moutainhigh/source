package com.dchip.cloudparking.web.controller;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.ICompanyRepository;
import com.dchip.cloudparking.web.iRepository.ILoginLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iService.IAccountService;
import com.dchip.cloudparking.web.iService.IMenuService;
import com.dchip.cloudparking.web.iService.IParkingService;
import com.dchip.cloudparking.web.model.po.Account;
import com.dchip.cloudparking.web.model.po.Company;
import com.dchip.cloudparking.web.model.po.LoginLog;
import com.dchip.cloudparking.web.model.vo.MenuVo;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.utils.IpKit;
import com.dchip.cloudparking.web.utils.MessageUtil;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
@EnableAutoConfiguration
public class IndexController {
	
	@Autowired
	private IParkingService parkingService;
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private ILoginLogRepository loginLogRepository;
	@Autowired
	private ICompanyRepository companyRepository;
	
	@Resource(name="authenticationManager")
	private AuthenticationManager authenticationManager;
	
	/**
	 * 显示主页框架
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {		
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = accountService.getAccountById(user.getAccountId());
		if(user.getCompanyId() != null) {
			Company companyName = companyRepository.findById(user.getCompanyId()).get();
			request.setAttribute("companyName", companyName.getName());
		}
		List<MenuVo> menuVos = menuService.getMenuDetails();
		request.setAttribute("menuList", JSON.toJSONString(menuVos));
		request.setAttribute("accountId", user.getAccountId());
		request.setAttribute("accountName", user.getUserName());
		request.setAttribute("accountRoleId", user.getRoleId());
		request.setAttribute("accountType", account.getType());
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		Locale locale1 = LocaleContextHolder.getLocale();
		System.out.println(locale1.toLanguageTag());
		request.setAttribute("localeLanguage",locale.toLanguageTag());
		return "indexFrame";
	}
	
	/**
	 * 显示后台主页内容页面
	 * @return
	 */
	@RequestMapping("/homepage")
	public String homepage(HttpServletRequest request) {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n1", locale);
		request.setAttribute("type", user.getRoleType());
		request.setAttribute("companyId", user.getCompanyId());
		request.setAttribute("points", parkingService.getAllParkingCoordinate());
		return "homepage";
	}
	
	/**
	 * 跳转到登录页
	 * @return
	 */
	@RequestMapping("/login")
	public String login() {		
		return "login";
	}
	
	/**
	 * 转发到登录页
	 * @param request
	 * @return
	 */
	@RequestMapping("/")
	public String defaultHome(HttpServletRequest  request) {
		return "forward:/login";
	}
	
	/**
	 * 登录方法
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/loginIn")
	@ResponseBody
	public RetKit loginIn(HttpServletRequest request, HttpServletResponse response) {
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		String userName = request.getParameter("userName");
		String pwd =  request.getParameter("pwd");
		try {			
			//参数校验
			if (userName == null || StrKit.isBlank(userName)) {
				throw new RuntimeException(MessageUtil.loadMessage("backend.username.error"));
			}			
			if (pwd == null || StrKit.isBlank(pwd)) {
				throw new RuntimeException(MessageUtil.loadMessage("backend.pwd.error"));
			}	
			
			//用户名或密码不正确
			if( !accountService.hasLoginName(userName) || !accountService.pwdIsCorrect(userName, pwd)) {
				throw new RuntimeException("用户名或密码不正确，请重新输入");
			}
			
			//账号密码验证通过后，将登录信息写入security上下文
			UsernamePasswordAuthenticationToken authRequest =  new UsernamePasswordAuthenticationToken(userName, pwd);
			Authentication authentication = authenticationManager.authenticate(authRequest); 
			SecurityContextHolder.getContext().setAuthentication(authentication); 
			HttpSession session = request.getSession();  
			session.setMaxInactiveInterval(60*60*3);//单位秒
			session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
			if(authentication != null && authentication.isAuthenticated()) {
				request.authenticate(response);
				UserAuthentic userAuthentic = (UserAuthentic)authentication.getPrincipal();
				Account account = accountService.getAccountById(userAuthentic.getAccountId());
				if(account.getStatus() == BaseConstant.AccountStatus.SoftDelete.delete.getTypeValue() || account.getStatus() == BaseConstant.AccountStatus.DisableStatus.getTypeValue()){
					return RetKit.fail("该账号被禁止登陆");
				}
				
				LoginLog loginLog = new LoginLog();
				loginLog.setLoginAt(new Date());
				loginLog.setSourceFlag(BaseConstant.LoginSourceFlag.WebFlag.getTypeValue());
				loginLog.setUserId(account.getId());
				String loginIp = IpKit.getRealIp(request);
				if(loginIp.equals("0:0:0:0:0:0:0:1")) {
					loginIp = "127.0.0.1";
				}
				loginLog.setIp(loginIp);
				loginLogRepository.save(loginLog);
				request.getSession().setAttribute("loginUser", userAuthentic);
				return RetKit.ok().set("returnUrl", request.getParameter("returnUrl"));
			}	
			
			return RetKit.fail("权限不足");
		} catch (Exception e) {
			e.printStackTrace();
			return RetKit.fail("用户名或密码不正确，请重新输入");
		}
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/logOut")
	public String logOut(HttpServletRequest  request,HttpServletResponse response) {
		// 清除session
		Enumeration<String> em = request.getSession().getAttributeNames();
		while (em.hasMoreElements()) {
			request.getSession().removeAttribute(em.nextElement().toString());
		}
		request.getSession().removeAttribute("loginUser");
		request.getSession().invalidate();
		return "redirect:/login";
	}
	
	/**
	 * 保存跳转的链接
	 * @param request
	 */
	@RequestMapping("/setLinkUrl")
	@ResponseBody
	public void setLinkUrl(HttpServletRequest request) {
		request.getSession().setAttribute("jumpLinkUrl", request.getParameter("linkUrl"));
	}
	
	/**
	 * 返回跳转的链接
	 * @param request
	 * @return
	 */
	@RequestMapping("/getLinkUrl")
	@ResponseBody
	public String getLinkUrl(HttpServletRequest request) {
		return request.getSession().getAttribute("jumpLinkUrl") == null?"":request.getSession().getAttribute("jumpLinkUrl").toString();
	}
	
}
