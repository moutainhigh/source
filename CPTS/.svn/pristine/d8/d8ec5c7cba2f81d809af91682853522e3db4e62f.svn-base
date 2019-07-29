package com.dchip.cloudparking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@Service
public class TestService {

    @Autowired
    private RestTemplate restTemplate;

    
    @HystrixCommand(fallbackMethod = "error")
    public String test(String name) {
        return restTemplate.getForObject("http://api/test?name="+name,String.class);
    }
    
    public String webTest() {
        return restTemplate.getForObject("http://web/test",String.class);
    }
    
    private String error(String name) {
        return name+" 你error啦!";
    }
}
