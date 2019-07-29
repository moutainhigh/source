package com.dchip.cloudparking.api.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.api.iRepository.IHelpRepository;
import com.dchip.cloudparking.api.iService.ITestService;
import com.dchip.cloudparking.api.model.po.Help;

@Service
public class TestServiceImp extends  BaseService implements ITestService{

	@Autowired
	private IHelpRepository iHelpRepository;
	
	@Override
	public String test(String name) {
		// TODO Auto-generated method stub
		Help help = iHelpRepository.findById(1).get();
		return help.getContent();
	}

}
