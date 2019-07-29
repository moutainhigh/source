package com.dchip.cloudparking.web.controller;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iService.IGroundLockService;
import com.dchip.cloudparking.web.iService.IMainControlService;
import com.dchip.cloudparking.web.model.po.GroundLock;
import com.dchip.cloudparking.web.model.po.MainControl;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
@RequestMapping("/groundLock")
public class GroundLockController {

    @Autowired
    private IGroundLockService groundLockService;
    @Autowired
    private IMainControlService mainControlService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
    	Integer actTo = BaseConstant.MainControlActTo.GroundLock.getTypeValue();
		List<MainControl> mainControlList = mainControlService.findMainControl(actTo);
    	List<GroundLock> groundLockUIdList = groundLockService.findAllGroundLockUId();
    	Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
    	request.setAttribute("mainControlList", mainControlList);
    	request.setAttribute("groundLockUIdList", groundLockUIdList);
        return "groundLock/index";
    }

    @RequestMapping("/rendering")
    @ResponseBody
    public Object rendering(HttpServletRequest request) {
        //分页条件
        Integer pageSize = Integer.parseInt(request.getParameter("limit"));
        Integer pageNum = Integer.parseInt(request.getParameter("page"))-1;
        //排序条件
        String sortName =  request.getParameter("sortName");
        String direction = request.getParameter("direction");

        // 动态搜索参数
        String searchUId = request.getParameter("searchUId");
        
        List<Map<String, Object>> para = new ArrayList<>();
        if(StrKit.notBlank(searchUId)) {
        	Map<String, Object> map = new HashMap<>();
        	map.put("searchUId",searchUId);
        	para.add(map);
        }

        return JSON.toJSON(groundLockService.getList(pageSize,pageNum,sortName,direction,para));
    }

//    @RequestMapping("/add")
//    @ResponseBody
//    public RetKit save(HttpServletRequest request) {
//        if (StrKit.isBlank(request.getParameter("mainControlMac"))) {
//            return RetKit.fail("主控板为空");
//        }
//        if (StrKit.isBlank(request.getParameter("lockLicensePlate"))) {
//			return RetKit.fail("车牌号为空");
//		}
//        Integer groundLockId = Integer.parseInt(request.getParameter("groundLockId"));
//        Integer mainControlId = Integer.parseInt(request.getParameter("mainControlMac"));
//        String lockLicensePlate = request.getParameter("lockLicensePlate");
//        try{
//            groundLockService.save(mainControlId,lockLicensePlate,groundLockId);
//            return RetKit.ok();
//        }catch (Exception e){
//            return RetKit.fail();
//        }
//    }
    
    @RequestMapping("/delete")
    @ResponseBody
    public RetKit delete(HttpServletRequest request) {
        if (StrKit.isBlank(request.getParameter("groundId"))) {
            return RetKit.fail("参数不存在");
        }
        Integer groundId = Integer.parseInt(request.getParameter("groundId"));
        return groundLockService.delete(groundId);
    }
    
    @RequestMapping("/edit")
    @ResponseBody
    public RetKit edit(HttpServletRequest request) {
    	String groundUid = request.getParameter("groundUid");
    	Integer mainControlId = Integer.parseInt(request.getParameter("mainControlId"));
    	String licensePlate = request.getParameter("licensePlate");
    	if (StrKit.isBlank(request.getParameter("mainControlId"))) {
            return RetKit.fail("主控板不能为空");
        }
        if (StrKit.isBlank(licensePlate)) {
			return RetKit.fail("车牌号码不能为空");
		}
        if (StrKit.isBlank(groundUid)) {
			return RetKit.fail("地锁唯一识别码不能为空");
		}
    	return groundLockService.save(mainControlId, licensePlate, groundUid);
    }
    
    @RequestMapping("/checkgroundLockName")
    @ResponseBody
    public RetKit checkgroundLockName(HttpServletRequest request) {
    	String licensePlate = request.getParameter("licensePlate");
    	if (StrKit.isBlank(licensePlate)) {
			return RetKit.fail("车牌号码不能为空");
		}
    	return groundLockService.checkgroundLockName(licensePlate);
    }
}
