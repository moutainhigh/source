package com.dchip.cloudparking.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dchip.cloudparking.web.iService.IParkingUserService;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.utils.MyWebSocket;
import com.dchip.cloudparking.web.utils.RetKit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
 
/**
 * @Description:  消息发送类。服务端主动发送
 * @Author: Gentle
 * @date 2018/9/5  19:30
 */
@RestController
public class SocketController {
	@Autowired
	 private IParkingUserService parkingUserService;
    @Resource
    MyWebSocket myWebSocket;
 
    @RequestMapping("many")
    public String helloManyWebSocket(){
       /* 向所有人发送消息
        webSocketSet.sendMessage("你好~！");
 */
        return "发送成功";
    }
 
    @RequestMapping("one")
    public String helloOneWebSocket(HttpServletRequest request,String parkingId) throws IOException {
    	UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer ip = parkingUserService.findParkingId(user.getUserName());
    	request.setAttribute("parkingId", ip);
      /*  向某个人发送消息
        webSocketSet.sendMessage(sessionId,"你好~！，单个用户");*/
 
        return "发送成功";
    }
    
    /**
     * API发送信息过来
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/sendSocketToUser")
    public RetKit sendSocketToUser(HttpServletRequest request) throws IOException {
    	String parkingId = request.getParameter("parkingId");
    	String parkingInfoId = request.getParameter("parkingInfoId");
    	
    	myWebSocket.sendtoAppointUser(parkingInfoId, parkingId);
    	return RetKit.ok();
    }
 
 
}
