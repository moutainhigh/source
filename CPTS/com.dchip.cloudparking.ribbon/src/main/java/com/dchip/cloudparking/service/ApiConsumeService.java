package com.dchip.cloudparking.service;


import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;


@Service
public class ApiConsumeService {
 
	/**
	 * 将HttpServletRequest中头部信息取出来，生成新的HttpEntity，主要是为了保存HttpHeaders
	 * @param request
	 * @return
	 */
	public HttpEntity<String> getHttpEntityByRequst(HttpServletRequest request){
		HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("platform", request.getHeader("platform"));
        headers.add("accessToken", request.getHeader("accessToken"));
        return new HttpEntity<String>("", headers);
	}
}
