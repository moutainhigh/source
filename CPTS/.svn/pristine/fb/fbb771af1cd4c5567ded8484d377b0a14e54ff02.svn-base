package com.dchip.cloudparking.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dchip.cloudparking.web.iService.IEquipmentService;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private IEquipmentService equipmentService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
    	Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
        return "equipment/index";
    }

    @RequestMapping("/rendering")
    @ResponseBody
    public Object rendering(HttpServletRequest request) {

        //分页条件
        Integer pageSize = Integer.parseInt(request.getParameter("limit"));
        Integer pageNum = Integer.parseInt(request.getParameter("page")) - 1;
        //排序条件
        String sortName = request.getParameter("sortName");
        String direction = request.getParameter("direction");
        //动态搜索参数
        String searchMAC = request.getParameter("searchMAC");

        List<Map<String, Object>> para = new ArrayList<>();
        if (StrKit.notBlank(searchMAC)) {
            Map<String, Object> map = new HashMap<>();
            map.put("searchMAC", searchMAC);
            para.add(map);
        }

        return JSON.toJSON(equipmentService.getEquipmentList(pageSize, pageNum, sortName, direction, para));
    }

    @RequestMapping("/getRoadways")
    @ResponseBody
    public RetKit getRoadways(HttpServletRequest request) {
        return equipmentService.getRoadways(request.getParameter("mainControlId"));
    }
    
    @RequestMapping("/getEntryRoadways")
    @ResponseBody
    public RetKit getEntryRoadways(HttpServletRequest request) {
    	String mainControlId = request.getParameter("mainControlId");
    	if (StrKit.isBlank(mainControlId)) {
			return RetKit.fail("主控板ID为空");
		}
    	return equipmentService.getEntryRoadways(mainControlId);
    }
    
    @RequestMapping("/getExitRoadways")
    @ResponseBody
    public RetKit getExitRoadways(HttpServletRequest request) {
    	String mainControlId = request.getParameter("mainControlId");
    	if (StrKit.isBlank(mainControlId)) {
    		return RetKit.fail("主控板ID为空,请先绑定主控板！");
    	}
    	return equipmentService.getExitRoadways(mainControlId);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public RetKit delete(HttpServletRequest request) {
        Integer mainControlId = Integer.parseInt(request.getParameter("mainControlId"));
        return equipmentService.delete(mainControlId);
    }

    /**
     * 编辑：主控板绑定设备
     *
     * @param request
     * @return
     */
    @RequestMapping("/binding")
    @ResponseBody
    public RetKit editBinding(HttpServletRequest request) {
    	String actionType = request.getParameter("actionType");
        if (StrKit.isBlank(request.getParameter("roadwaysData"))) {
            return RetKit.fail();
        }
        List<Object> roadwaysData = JSON.parseArray(request.getParameter("roadwaysData"));

        String mainControlId = request.getParameter("mainControlId");
        if (mainControlId == null) {
            return RetKit.fail("没有找到主控板");
        }
        String parkingIdStr = request.getParameter("parkingId");
        if (StrKit.isBlank(parkingIdStr)) {
            return RetKit.fail("停车场不能为空");
        }
        Integer parkingId = Integer.parseInt(parkingIdStr);
        return equipmentService.binding(mainControlId, roadwaysData, parkingId, actionType);
    }

    @RequestMapping("/checkCameraMac")
    @ResponseBody
    public RetKit checkCameraMac(HttpServletRequest request) {
        String cameraMacs = request.getParameter("cameraMacs");
        if (StrKit.notBlank(cameraMacs)) {
            String macs[] = cameraMacs.replaceAll("\"", "").replaceFirst("\\[", "").replaceFirst("\\]", "").split(",");
            for (String mac : macs) {
                RetKit retKit = equipmentService.checkCameraMac(mac);
                if (retKit.get("code").toString().equals("501")) {
                    return retKit;
                }
            }
            if (macs.length == 0) {
                return RetKit.fail("摄像机mac不能为空");
            }
            return RetKit.ok();
        }

        String cameraMac = request.getParameter("cameraMac");
        if (StrKit.isBlank(cameraMac)) {
            return RetKit.fail("摄像机mac不能为空");
        }
        return equipmentService.checkCameraMac(cameraMac);
    }

}
