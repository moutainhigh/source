package com.dchip.cloudparking.api.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.api.iService.IGroundLockService;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;

/**2019年2月21号
 * @author ZYY
 */
@RestController
@RequestMapping("/groundLock")
public class GroundLockController {

	@Autowired
	private IGroundLockService groundLockService;
	
	/**
	 * 地锁的学习
	 * @param jsonBody
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
//	@RequestMapping("/studyCommond")
//	public RetKit OpenAndCloseCommand(HttpServletRequest request,@RequestBody String jsonBodyStr) throws UnsupportedEncodingException {
//		JSONObject jsonBody = JSON.parseObject(jsonBodyStr);
//		String groundUid = jsonBody.getString("groundUid");
//		if (StrKit.isBlank(groundUid)) {
//			return RetKit.fail("groundUid不能为空");
//		}
//		String groundlist = jsonBody.getString("groundlist");	
//		JSONArray jsonArray = JSON.parseArray(groundlist);
//		if (jsonArray.isEmpty()||jsonArray.size()<1) {
//			return RetKit.fail("groundlist的内容不能为空");
//		}
//		return groundLockService.save(groundUid, jsonArray);
//	}
	
	/**
	 * 地锁学习接口
	 * @param request
	 * @return
	 */
	@RequestMapping("/studyCommond")
	public RetKit OpenAndCloseCommand(HttpServletRequest request) {
		String groundUid = request.getParameter("groundUid");
		String commond1 = request.getParameter("commond1");
		String type1 = request.getParameter("type1");
		String commond2 = request.getParameter("commond2");
		String type2 = request.getParameter("type2");
		Map<String, Object> datas = new HashMap<>();
		datas.put("groundUid", groundUid);
		datas.put("commond1", commond1);
		datas.put("type1", type1);
		datas.put("commond2", commond2);
		datas.put("type2", type2);
		return groundLockService.save(datas);
	}
	
	/**
	 * 控制地锁的开与关
	 * @param request
	 * @return
	 */
	@RequestMapping("/controlGroundLock")
	public RetKit controlGroundLock(HttpServletRequest request) {
		String groundUid = request.getParameter("groundUid");
		String type = request.getParameter("type");//101-关闭  102-打开
		return groundLockService.controlGroundLock(groundUid, type);
	}
	
	/**
	 * 确认地锁的状态
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkStatus")
	public RetKit checkStatus(HttpServletRequest request) {
		String groundUid = request.getParameter("groundUid");
		return groundLockService.checkStatus(groundUid);
	}
	
	/**
	 * 上报操作状态
	 * @param request
	 * @return
	 */
	@RequestMapping("/reportStatus")
	public RetKit reportStatus(HttpServletRequest request) {
		String groundUid = request.getParameter("groundUid");
		String type = request.getParameter("type");
		if (StrKit.isBlank(groundUid)) {
			return RetKit.fail("地锁唯一识别码不能为空");
		}
		if (StrKit.isBlank(type)) {
			return RetKit.fail("type不能为空");
		}
		return groundLockService.reportStatus(groundUid, type);
	}
	
	/**
	 * 获取地锁信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/findGroundLockInfo")
	public RetKit findGroundLockInfo(HttpServletRequest request) {
		String licensePlate = request.getParameter("licensePlate"); 
		return groundLockService.findGroundLockInfo(licensePlate);
	}
	
}
