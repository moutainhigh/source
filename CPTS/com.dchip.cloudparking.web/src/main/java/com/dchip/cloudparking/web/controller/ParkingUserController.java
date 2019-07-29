package com.dchip.cloudparking.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iService.IParkingUserService;
import com.dchip.cloudparking.web.model.vo.ParkingUserVo;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;
@Controller
@RequestMapping("/parkingUser")
public class ParkingUserController {
    @Autowired
    private IParkingUserService parkingUserService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
    	UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer parkingId = parkingUserService.findParkingId(user.getUserName());
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
    	request.setAttribute("parkingId", parkingId);
        return "parkingUser/index";
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
        String searchUserName = request.getParameter("userName");
        List<Map<String, Object>> para = new ArrayList<>();
        if (StrKit.notBlank(searchUserName)) {
            Map<String, Object> map = new HashMap<>();
            map.put("searchUserName", searchUserName);
            para.add(map);
        }
        return JSON.toJSON(parkingUserService.getParkingUserList(pageSize, pageNum, sortName, direction, para));
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public RetKit addUser(ParkingUserVo parkingUserVo) {
        return parkingUserService.save(parkingUserVo);
    }

    @RequestMapping("/delUser")
    @ResponseBody
    public RetKit del(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (StrKit.isBlank(userId)) {
            return RetKit.fail("userId不能为空！");
        }
        return parkingUserService.del(userId);
    }
    
    @RequestMapping("/resetUser")
    @ResponseBody
    public RetKit reset(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (StrKit.isBlank(userId)) {
            return RetKit.fail("userId不能为空！");
        }
        return parkingUserService.reset(userId);
    }
    
	/**
	 * 检查角色名称
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkUserName")
	@ResponseBody
	public RetKit checkUserName(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		return parkingUserService.checkUserName(userName);
	}

}
