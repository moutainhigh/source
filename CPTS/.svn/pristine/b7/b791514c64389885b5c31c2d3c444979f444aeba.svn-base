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
import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.IVersionRepository;
import com.dchip.cloudparking.web.iService.IMainControlService;
import com.dchip.cloudparking.web.iService.IVersionService;
import com.dchip.cloudparking.web.model.po.MainControl;
import com.dchip.cloudparking.web.model.po.Version;
import com.dchip.cloudparking.web.utils.QiniuUtil;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;

@Controller
@RequestMapping("/version")
public class VersionController {
	
	@Autowired
	private IVersionService versionService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
		request.setAttribute("domain", QiniuUtil.getDomain());
		request.setAttribute("token", QiniuUtil.getUpToken());
		request.setAttribute("bucketZone", QiniuUtil.getBucketZone());
		//以下是指定类型主控板最新版本
		String versionCode100 = "";
		Map<String, Object> latestVersion = versionService.findLatestVersionMap(BaseConstant.MainControlType.Type100.getTypeValue());
		if(!latestVersion.isEmpty()) {
			versionCode100 = latestVersion.get("version_code").toString();
		}
		request.setAttribute("versionCode100",versionCode100);
		return "version/index";
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
		String VersionNum = request.getParameter("VersionNum");
	
		List<Map<String, Object>> para = new ArrayList<>();
		if(StrKit.notBlank(VersionNum)) {
			Map<String, Object> map = new HashMap<>();
			map.put("VersionNum", VersionNum);
			para.add(map);
		}
		
		return JSON.toJSON(versionService.getVersionList(pageSize, pageNum, sortName, direction, para));
	}
	
	/**
	 * 新增和编辑版本
	 */
	@RequestMapping("/save")
	@ResponseBody
	public RetKit save(Version vo) {
		return versionService.save(vo);
	}
	
	@RequestMapping("/addVersion")
	@ResponseBody
	public RetKit addVersion(Version vo) {
		return save(vo);
	}
	
	@RequestMapping("/editVersion")
	@ResponseBody
	public RetKit editVersion(Version vo) {
		return save(vo);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public RetKit delete(HttpServletRequest request) {
		return versionService.delete(Integer.parseInt(request.getParameter("id")));
	}
	

}
