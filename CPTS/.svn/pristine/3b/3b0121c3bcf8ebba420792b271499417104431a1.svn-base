package com.dchip.cloudparking.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dchip.cloudparking.api.iService.IMerchantService;
import com.dchip.cloudparking.api.iService.IUserService;
import com.dchip.cloudparking.api.model.po.Session;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/merchant")
public class MerchantController {
	@Autowired
	private IMerchantService merchantService;
	@Autowired
	private IUserService userService;

	/**
	 * 商户登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/loginIn")
	@ResponseBody
	public RetKit loginIn(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String pwd =  request.getParameter("pwd");
		try {			
			if (userName == null || StrKit.isBlank(userName)) {
				return RetKit.fail("用户名不能为空！");
			}			
			if (pwd == null || StrKit.isBlank(pwd)) {
				return RetKit.fail("密码不能为空！");
			}
			//用户名或密码不正确
			if( !merchantService.hasLoginName(userName) || !merchantService.pwdIsCorrect(userName, pwd)) {
				return RetKit.fail("用户名或密码不正确，请重新输入");
			}else {
				return merchantService.getParkingUser(request, userName, pwd);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RetKit.fail("用户名或密码不正确，请重新输入");
		}
	}

	/**
	 * 停车场用户退出登录操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/loginOut")
	public RetKit logOut (HttpServletRequest request) {
		String accessToken = request.getHeader("accessToken");
		Session session = userService.findSessionByToken(accessToken);
		return userService.userlogOut(session);
	}

	/**
	 * 商户的停车场
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getParkingList")
	@ResponseBody
	public RetKit getParkingList(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		return merchantService.getParkingList(userName);
	}
}
