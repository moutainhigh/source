package com.dchip.cloudparking.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.constant.SocketConstant;
import com.dchip.cloudparking.web.iService.IMainControlService;
import com.dchip.cloudparking.web.iService.IMainControlVersionService;
import com.dchip.cloudparking.web.iService.IVersionService;
import com.dchip.cloudparking.web.model.po.MainControl;
import com.dchip.cloudparking.web.model.po.Version;
import com.dchip.cloudparking.web.utils.HttpUtil;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.SocketKit;
import com.dchip.cloudparking.web.utils.StrKit;



@Controller
@RequestMapping("/mainapkversion")
public class MainControlVersionController {
	@Autowired
	private IMainControlVersionService mainControlVersionService;
	@Autowired
	private IVersionService versionService;
	@Autowired
	private IMainControlService mainControlService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		String versionCode = "";
		Map<String, Object> latestVersion = versionService.findLatestVersionMap(BaseConstant.MainControlType.Type100.getTypeValue());
		if(!latestVersion.isEmpty()) {
			versionCode = latestVersion.get("version_code").toString();
		}
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		request.setAttribute("versionCode",versionCode);
		return "mainapkversion/index";
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
		//动态搜索参数
		String searchMAC = request.getParameter("searchMAC");
	
		List<Map<String, Object>> para = new ArrayList<>();
		if(StrKit.notBlank(searchMAC)) {
			Map<String, Object> map = new HashMap<>();
			map.put("searchMAC", searchMAC);
			para.add(map);
		}
		return JSON.toJSON(mainControlVersionService.getMainControlVersionList(pageSize, pageNum, sortName, direction, para));
	}
	
	
	@RequestMapping("/notifyToUpdate")
	@ResponseBody
	public RetKit notifyToUpdate(HttpServletRequest request) {
		String mac = request.getParameter("mac");
		String type = request.getParameter("type");
		if (StrKit.isBlank(mac) || (StrKit.notBlank(mac) && mac.equals("undefined"))) {
			return RetKit.fail("主控板不能为空");
		}
		if (StrKit.isBlank(type) || (StrKit.notBlank(type) && type.equals("undefined"))) {
			return RetKit.fail("type不能为空");
		}

		notifyToUpdateSingle(request,mac,type);

		return RetKit.ok();
	}
	
	/**
	 * 通知某一类型的主控板更新
	 */
	@RequestMapping("/notifyToUpdateByType")
	@ResponseBody
	public RetKit notifyToUpdateByType(HttpServletRequest request) {
		String type = request.getParameter("type");
		if (StrKit.isBlank(type) || (StrKit.notBlank(type) && type.equals("undefined"))) {
			return RetKit.fail("type不能为空");
		}
		
		java.util.List<MainControl> mainControlList = mainControlService.findMainControlsByType(Integer.parseInt(type));
		if(mainControlList != null && mainControlList.size() > 0) {
			for(MainControl item :mainControlList) {
				notifyToUpdateSingle(request,item.getMac(),type);
			}
		}

		return RetKit.ok();

	}
	
	/**
	 * 通知某个Mac更新
	 * @return
	 */
	private RetKit notifyToUpdateSingle(HttpServletRequest request,String mac,String type) {
		Map<String, Object> latestVersionByType = versionService.findLatestVersionMap(Integer.parseInt(type));
		if (latestVersionByType.isEmpty()) {
			return RetKit.fail("还没有上传对应版本");
		}
		
		try {
			return SocketKit.sendMessage(mac, "cameraMac", SocketConstant.CommandType.UpdateMainControl.getValue());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return RetKit.fail();
		} catch (IOException e) {
			e.printStackTrace();
			return RetKit.fail();
		}
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public RetKit delete(HttpServletRequest request) {
		return mainControlVersionService.delete(Integer.parseInt(request.getParameter("id")));
	}
	
}
