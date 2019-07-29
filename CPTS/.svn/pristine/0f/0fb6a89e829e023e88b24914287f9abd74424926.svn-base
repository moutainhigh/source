package com.dchip.cloudparking.web.controller;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iService.IParkingLotService;
import com.dchip.cloudparking.web.model.po.GroundLock;
import com.dchip.cloudparking.web.model.po.ParkingLot;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/parkingLot")
public class ParkingLotController {

    @Autowired
    public IParkingLotService parkingLotService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
    	Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
        return "parkingLot/index";
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
        List<Map<String, Object>> para = new ArrayList<>();

        return JSON.toJSON(parkingLotService.getList(pageSize,pageNum,sortName,direction,para));
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(HttpServletRequest request) {
        if (StrKit.isBlank(request.getParameter("voData"))) {
            return RetKit.fail("参数不存在");
        }
        Object voData = JSON.parse(request.getParameter("voData"));
        ParkingLot vo = JSON.toJavaObject(JSON.parseObject(voData.toString()), ParkingLot.class);
        try{
            parkingLotService.save(vo);
            return RetKit.ok();
        }catch (Exception e){
            return RetKit.fail();
        }
    }

}
